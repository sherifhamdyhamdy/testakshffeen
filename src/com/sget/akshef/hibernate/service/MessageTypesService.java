package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.MessageTypesBean;
import com.sget.akshef.hibernate.dao.MessageTypesDAO;
import com.sget.akshef.hibernate.entities.MessageTypesEntity;

/**
 * 
 * @author JDeeb
 * MessageTypes Service
 */
public class MessageTypesService {
	MessageTypesDAO dao = null ;
	
	public MessageTypesService(){
		dao = new MessageTypesDAO();
	}
	
	public void insert(MessageTypesBean bean){
		if(bean == null)
			return;
		MessageTypesEntity entity = new MessageTypesEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(MessageTypesBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        MessageTypesEntity entity = new MessageTypesEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(MessageTypesBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        MessageTypesEntity entity = new MessageTypesEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public MessageTypesBean getById(int id) {
    	if(id < 1)
    		return null ;
    	MessageTypesBean bean = new MessageTypesBean();
    	MessageTypesEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<MessageTypesBean> getAll() {
    	List<MessageTypesEntity> entities = dao.getAll();
    	List<MessageTypesBean> beans = new ArrayList<MessageTypesBean>();
    	MessageTypesBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(MessageTypesEntity entity : entities){
	    		bean = new MessageTypesBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(MessageTypesBean bean , MessageTypesEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new MessageTypesBean();
		// Basics Data
		bean.setId(entity.getId());
		entity.setName(bean.getName());
	}
	// Fill Entity From Bean
	public void fillEntity(MessageTypesEntity entity,MessageTypesBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new MessageTypesEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setName(bean.getName());
	}
}
