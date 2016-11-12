package com.sget.akshef.view;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

import com.sget.akshf.mobile.notifications.GCMNotification;

/**
 * 
 * @author JDeeb
 * 
 */

public class SendAndroidNotifications implements Serializable{
	
	private static final long serialVersionUID = 7318891009307707574L;
	
	private String message ;
	private String title ;
	private Date date ;

	// send Notification
	public void sendNotification(){
		GCMNotification notification = new GCMNotification();
		boolean status = notification.sendAndroidNotification(message,title,date);
		if(status){
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
 					"Success", "Send Done !!!"));
		}else{
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 					"FAIL", "Send Fail !!!"));
		}
	}
	
	// Setters & Getters
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}