package com.sget.akshf.mobile.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.NotificationBean;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.NotificationService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.searchcriteria.BranchesCriteria;

/**
 * 
 * @author JDeeb
 *
 */
@Path("/getNotificationDetails")
public class getNotificationDetails {
	private Gson gson = new Gson();
	private NotificationService service = new NotificationService();


	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getNotificationDetails(@FormParam("id") int id ){
		
		if( id > 0){
			
			try{
    			NotificationBean notification =service.getById(id);
    			List<NotificationBean> notificationsList=new ArrayList<NotificationBean>();
    			notificationsList.add(notification);
    			if(notification != null ){
        			ResponseCollectFormatData<NotificationBean> respo = new ResponseCollectFormatData<NotificationBean>();
        			respo.setData(notificationsList);
        			respo.setCode(ResponseFactory.CODE_SUCCESS);
        			respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
        			return gson.toJson(respo);
    			}else
    				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA , ResponseFactory.MESSAGE_NO_DATA));
			}catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX , ResponseFactory.MESSAGE_FAIL_EX));
			}
		}else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT , ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
