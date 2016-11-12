package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sget.akshef.hibernate.beans.MessagesBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.dao.MessagesDAO;
import com.sget.akshef.hibernate.entities.MessageTypesEntity;
import com.sget.akshef.hibernate.entities.MessagesEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshf.mobile.api.MobileMessagesBean;

/**
 * 
 * @author JDeeb
 * Messages Service
 */
public class MessagesService {
	MessagesDAO dao = null ;
	
	public MessagesService(){
		dao = new MessagesDAO();
	}
	
	public void insert(MessagesBean bean){
		if(bean == null)
			return;
		MessagesEntity entity = new MessagesEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(MessagesBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        MessagesEntity entity = new MessagesEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(MessagesBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        MessagesEntity entity = new MessagesEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public MessagesBean getById(int id) {
    	if(id < 1)
    		return null ;
    	MessagesBean bean = new MessagesBean();
    	MessagesEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<MessagesBean> getAll() {
    	List<MessagesEntity> entities = dao.getAll();
    	List<MessagesBean> beans = new ArrayList<MessagesBean>();
    	MessagesBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(MessagesEntity entity : entities){
	    		bean = new MessagesBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    // Get All message either complains or suggessions By type
    public List<MessagesBean> getAllMessagesByType(int typeID) {
    	List<MessagesEntity> entities = dao.getAllMessagesByType(typeID);
    	List<MessagesBean> beans = new ArrayList<MessagesBean>();
    	MessagesBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(MessagesEntity entity : entities){
	    		bean = new MessagesBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    
    // Get All message either complains or suggessions By type
    public List<MessagesBean> getBranchMessages(int branchMangId) {
    	BranchDAO branchdao=new BranchDAO();
    	List<MessagesEntity> entities = branchdao.getBranchMessages(branchMangId);
    	List<MessagesBean> beans = new ArrayList<MessagesBean>();
    	MessagesBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(MessagesEntity entity : entities){
	    		bean = new MessagesBean();
	    		fillMessagesBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    public void fillMessagesBean(MessagesBean bean , MessagesEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new MessagesBean();
		// Basics Data
		bean.setMsgBody(entity.getMsgBody());
		bean.setNotes(entity.getNotes());
		bean.setTitle(entity.getNotes());
		bean.setFrom_username(entity.getFromUser().getNameEn());
	}
    
	// Fill Bean From Entity
	public void fillBean(MessagesBean bean , MessagesEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new MessagesBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setMsgBody(entity.getMsgBody());
		bean.setNotes(entity.getNotes());
		bean.setMsg_date(entity.getMsg_date());
		if(bean.getFromUser() == null)
			bean.setFromUser(new UsersBean());
		bean.getFromUser().setId(entity.getFromUser().getId());
		if(bean.getToUser() == null)
			bean.setToUser(new UsersBean());
		bean.getToUser().setId(entity.getToUser().getId());
		
		
		bean.setFrom_username(entity.getFromUser().getNameEn());
		bean.setTo_username(entity.getToUser().getNameEn());
		
		
		/*if(bean.getMessageTypes() == null)
			bean.setMessageTypes(new MessageTypesBean());
		bean.getMessageTypes().setId(entity.getMessageTypes().getId());*/
	}
	// Fill Entity From Bean
	public void fillEntity(MessagesEntity entity,MessagesBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new MessagesEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setMsgBody(bean.getMsgBody());
		entity.setNotes(bean.getNotes());
		entity.setMsg_date(bean.getMsg_date());
		if(entity.getFromUser() == null)
			entity.setFromUser(new UsersEntity());
		entity.getFromUser().setId(bean.getFromUser().getId());
		if(entity.getToUser() == null)
			entity.setToUser(new UsersEntity());
		entity.getToUser().setId(bean.getToUser().getId());
	}

    /**
     *  JDeeb get all messages by sender and receive
     */
    public List<MobileMessagesBean> getAllMessagesBetweenUsers(int userId , int oppositeID , int start , int limit,String url) {
    	List<MessagesEntity> entities = dao.getAllMessagesBetweenUsers(userId, oppositeID, start, limit);
    	List<MobileMessagesBean> beans = new ArrayList<MobileMessagesBean>();
    	MobileMessagesBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(MessagesEntity entity : entities){
	    		bean = new MobileMessagesBean();
	    		fillBeanForMobile(bean, entity,url);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	
	
	
	// Fill Bean From Entity
	public void fillBeanForMobile(MobileMessagesBean bean, MessagesEntity entity, String url)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new MobileMessagesBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setMsgBody(entity.getMsgBody());
		bean.setNotes(entity.getNotes());
		bean.setMsg_date(entity.getMsg_date());
		if (entity.getFromUser() != null)
		{
			bean.setFromUserId(entity.getFromUser().getId());
			bean.setFromUsername(entity.getFromUser().getUsername());
			bean.setFromUserImg(url + DBConstants.USERS_IMAGES_UPLOADS + entity.getFromUser().getProfile_img());
		}
	}
	
	
	
	// For ( addDoctorMessage ) Web Service
	public MobileMessagesBean addDoctorMessage(int doctorId, int fromUser, String text, String url)
	{
		MobileMessagesBean bean = new MobileMessagesBean();
		MessagesEntity entity = new MessagesEntity();
		entity.setToUser(new UsersEntity());
		entity.getToUser().setId(dao.getUserIdByDoctor(doctorId));
		entity.setFromUser(new UsersEntity());
		entity.getFromUser().setId(fromUser);
		entity.setMsgBody(text);
		entity.setMsg_date(Calendar.getInstance().getTimeInMillis());
		
		dao.insert(entity);
		entity = dao.getById(entity.getId());
		fillBeanForMobile(bean, entity, url);
		return bean;
	}
	
	public boolean addUnitMessage(int unitId, int fromUser, String text, String url)
	{
		return dao.insertUnitMessage(unitId, fromUser, text, url);
	}
	
	// For ( addDoctorMessage ) Web Service
	public MobileMessagesBean addBranchMessage(int branchid, int fromUser, String text, String url, int type_id)
	{
		MobileMessagesBean bean = new MobileMessagesBean();
		MessagesEntity entity = new MessagesEntity();
		entity.setToUser(new UsersEntity());
		entity.getToUser().setId(dao.getUserIdByBranch(branchid));
		if (entity.getToUser().getId() == 0)
			entity.getToUser().setId(DBConstants.ADMIN_TYPE);
		entity.setFromUser(new UsersEntity());
		entity.getFromUser().setId(fromUser);
		entity.setMsgBody(text);
		entity.setMsg_date(Calendar.getInstance().getTimeInMillis());

		dao.insert(entity);
		entity = dao.getById(entity.getId());
		fillBeanForMobile(bean, entity, url);
		return bean;
	}


}
