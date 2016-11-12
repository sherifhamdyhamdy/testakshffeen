package com.sget.akshef.beans;

import java.util.ArrayList;
import java.util.List;

public class NotificationContent 
{
	private List<NotificationElement> data = new ArrayList<NotificationElement>();
	private String code;
	private String message;
	
	public List<NotificationElement> getData() {
		return data;
	}
	public void setData(List<NotificationElement> data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
