package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.sget.akshef.hibernate.beans.NotificationBean;
import com.sget.akshef.hibernate.beans.HourBean;
import com.sget.akshef.hibernate.dao.NotificationDAO;
import com.sget.akshef.hibernate.entities.NotificationEntity;

/**
 * 
 * @author JDeeb
 */
public class NotificationService {
	NotificationDAO dao = null ;
	
	public NotificationService(){
		dao = new NotificationDAO();
	}
	
	public void insert(NotificationBean bean){
		if(bean == null)
			return;
		NotificationEntity entity = new NotificationEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(NotificationBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        NotificationEntity entity = new NotificationEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(NotificationBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        NotificationEntity entity = new NotificationEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public NotificationBean getById(int id) {
    	if(id < 1)
    		return null ;
    	NotificationBean bean = new NotificationBean();
    	NotificationEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<NotificationBean> getAll(int start ,int limit) {
    	List<NotificationEntity> entities = dao.getAll( start , limit);
    	List<NotificationBean> beans = new ArrayList<NotificationBean>();
    	NotificationBean bean ;
    	if(entities != null && entities.size() > 0){
    		for(NotificationEntity entity : entities){
	    		bean = new NotificationBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(NotificationBean bean , NotificationEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new NotificationBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setTitle(entity.getTitle());
		bean.setDescription(entity.getDescription());
		bean.setDate(entity.getDate()!=null?entity.getDate():new java.sql.Date(new java.util.Date().getTime()));
	}
	// Fill Entity From Bean
	public void fillEntity(NotificationEntity entity,NotificationBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new NotificationEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setTitle(bean.getTitle());
		entity.setDescription(bean.getDescription());
		entity.setDate(bean.getDate()!=null?new java.sql.Date(bean.getDate().getTime()):new java.sql.Date(new java.util.Date().getTime()));
	}
	

	
	
	
	
	
	
	
	
	
	
	
}
