package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.ActionsBean;
import com.sget.akshef.hibernate.dao.ActionsDAO;
import com.sget.akshef.hibernate.entities.ActionsEntity;

/**
 * 
 * @author JDeeb
 * Actions Service
 */
public class ActionsService {
	ActionsDAO dao = null ;
	
	public ActionsService(){
		dao = new ActionsDAO();
	}
	
	public void insert(ActionsBean bean){
		if(bean == null)
			return;
		ActionsEntity entity = new ActionsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(ActionsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        ActionsEntity entity = new ActionsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(ActionsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        ActionsEntity entity = new ActionsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public ActionsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	ActionsBean bean = new ActionsBean();
    	ActionsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<ActionsBean> getAll() {
    	List<ActionsEntity> entities = dao.getAll();
    	List<ActionsBean> beans = new ArrayList<ActionsBean>();
    	ActionsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(ActionsEntity entity : entities){
	    		bean = new ActionsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(ActionsBean bean , ActionsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ActionsBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setName(entity.getName());
		bean.setDescription(entity.getDescription());
	}
	// Fill Entity From Bean
	public void fillEntity(ActionsEntity entity,ActionsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new ActionsEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setName(bean.getName());
		entity.setDescription(bean.getDescription());
	}
}
