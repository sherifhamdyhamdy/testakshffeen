/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sget.akshef.managedbeans;



import com.sget.akshef.hibernate.beans.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RateEvent;

import com.sget.akshef.beans.ContentAdviceBean;
import com.sget.akshef.beans.ContentNewsBean;
import com.sget.akshef.beans.ContentOffersBean;
import com.sget.akshef.beans.NewsCategoryBean;
import com.sget.akshef.buisness.BranchFactory;

import com.sget.akshef.buisness.UnitsFactory;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.service.MessageTypesService;
import com.sget.akshef.hibernate.service.MessagesService;
import com.sget.akshef.hibernate.service.MobileService;
import com.sget.akshef.hibernate.service.UsersService;
 

public class MedComplainBean implements Serializable{
    
    /**
	 * 
	 */
	public MedComplainBean()
	{
		HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	user=	(UsersBean) session.getAttribute("userBean");
	}
	MessagesBean messageBean=new MessagesBean();
	UsersBean user;
	String type="1";
	String name;
	String job;
	String email;
	String message;

	public MessagesBean getMessageBean() {
		return messageBean;
	}

	public void setMessageBean(MessagesBean messageBean) {
		this.messageBean = messageBean;
	}

	public UsersBean getUser() {
		return user;
	}

	public void setUser(UsersBean user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

public String addComplainOrsugg()
{
	MessagesService serv=new MessagesService();
	int id=user.getId();
	UsersService servUser=new UsersService();
	UsersBean userFrom= servUser.getById(id);
	UsersBean userTo= servUser.getById(3);
	messageBean.setFromUser(userFrom);
	messageBean.setToUser(userTo);
	MessageTypesService servType=new MessageTypesService();
	MessageTypesBean messageTypebean = servType.getById(Integer.parseInt(type));
	messageBean.setMessageTypes(messageTypebean);
	serv.insert(messageBean);
	messageBean=new MessagesBean();
	email="";
	job="";
	name="";
	message="تم الأضافة بنجاح";
	return"";
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}
	
	
	
	
    
}
