package com.sget.akshf.mobile.notifications;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import com.sget.akshef.beans.NotificationContent;
import com.sget.akshef.beans.NotificationElement;
import com.sget.akshef.hibernate.beans.NotificationBean;
import com.sget.akshef.hibernate.service.MobileService;
import com.sget.akshef.hibernate.service.NotificationService;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author JDeeb
 *
 */
public class GCMNotification {
	
	private static final String SENDER_ID = "AIzaSyCVARbUlE9o8mMVbgnA7RChIyluwZbTG2o";	
	private List<String> androidTargets = new ArrayList<String>();
	NotificationService notserv=new NotificationService();

	public GCMNotification(){
		prepareAndroidTargets();
	}
	
	private void prepareAndroidTargets(){
		MobileService service = new MobileService();
		androidTargets = service.getAllAndroidDeviceID();
	}
	
	public boolean sendAndroidNotification(String msg,String title,Date date){
		boolean status = false ;
		// Instance of com.android.gcm.server.Sender, that does the
		// transmission of a Message to the Google Cloud Messaging service.
		Sender sender = new Sender(SENDER_ID);
		// This Message object will hold the data that is being transmitted
		// to the Android client devices.  For this demo, it is a simple text
		// string, but could certainly be a JSON object.
		String test = "";
		try{
			test = new String(msg.getBytes("UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
		}
		//get the json data
		 Gson gson = new Gson();
		List<NotificationBean>   notificationsList=new ArrayList<NotificationBean>();
		NotificationBean not=new NotificationBean();
		not.setDescription(msg);
		not.setTitle(title);
		not.setDate(date);
		notserv.insert(not);
		notificationsList.add(not);
		
		
		ResponseCollectFormatData<NotificationBean> respo = new ResponseCollectFormatData<NotificationBean>();
		respo.setData(notificationsList);
		respo.setCode(ResponseFactory.CODE_SUCCESS);
		respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
		String messageTxt=	 gson.toJson(respo);
	
		
		NotificationContent notificationContent  = gson.fromJson(messageTxt, NotificationContent.class);
		boolean sendNotification = false;

		if(notificationContent.getData()!=null && notificationContent.getData().size()>0)
		{			
			for(NotificationElement notificationElement : notificationContent.getData())
			{
				try 
				{
					notificationElement.setTitle(URLEncoder.encode(notificationElement.getTitle(), "UTF-8"));
					
//					notificationElement.setDescription(URLEncoder.encode(notificationElement.getDescription(), "UTF-8"));
					
//					System.out.println("before");
//					System.out.println(messageTxt);
					messageTxt  = gson.toJson(notificationElement);
//					System.out.println("after");
//					System.out.println(messageTxt);
					
					sendNotification = true;
					
					break;
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				
		if(!sendNotification)
			return false;
		
		Message message = new Message.Builder()
		// If multiple messages are sent using the same .collapseKey()
		// the android target device, if it was offline during earlier message
		// transmissions, will only receive the latest message for that key when
		// it goes back on-line.
			.collapseKey("MSG_AkshfFeen")
			.timeToLive(30)
			.delayWhileIdle(true)
			.addData("message",messageTxt)
			.build();
		
		try {
			// use this for multicast messages.  The second parameter
			// of sender.send() will need to be an array of register ids.
			MulticastResult result = sender.send(message, androidTargets, 1);
			
			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {
				}else{
				}
			} else {
				int error = result.getFailure();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status ;
	}
	
}
