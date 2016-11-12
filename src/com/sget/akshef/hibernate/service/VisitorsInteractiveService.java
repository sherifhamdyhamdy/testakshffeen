package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.beans.VisitorsInteractiveBean;
import com.sget.akshef.hibernate.dao.VisitorsInteractiveDAO;
import com.sget.akshef.hibernate.entities.InteractiveTypesEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.entities.VisitorsInteractiveEntity;

/**
 * 
 * @author JDeeb
 * Messages Service
 */
public class VisitorsInteractiveService {
	VisitorsInteractiveDAO dao = null ;
	
	public VisitorsInteractiveService(){
		dao = new VisitorsInteractiveDAO();
	}
	
	public void insert(VisitorsInteractiveBean bean){
		if(bean == null)
			return;
		VisitorsInteractiveEntity entity = new VisitorsInteractiveEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(VisitorsInteractiveBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        VisitorsInteractiveEntity entity = new VisitorsInteractiveEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(VisitorsInteractiveBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        VisitorsInteractiveEntity entity = new VisitorsInteractiveEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public VisitorsInteractiveBean getById(int id) {
    	if(id < 1)
    		return null ;
    	VisitorsInteractiveBean bean = new VisitorsInteractiveBean();
    	VisitorsInteractiveEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<VisitorsInteractiveBean> getAll() {
    	List<VisitorsInteractiveEntity> entities = dao.getAll();
    	List<VisitorsInteractiveBean> beans = new ArrayList<VisitorsInteractiveBean>();
    	VisitorsInteractiveBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(VisitorsInteractiveEntity entity : entities){
	    		bean = new VisitorsInteractiveBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(VisitorsInteractiveBean bean , VisitorsInteractiveEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new VisitorsInteractiveBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setName(entity.getName());
		bean.setEmail(entity.getEmail());
		bean.setDesc(entity.getDesc());
		bean.setFollow_no(entity.getFollow_no());
		bean.setOccupation(entity.getOccupation());
		bean.setTitle(entity.getTitle());
		if(bean.getToUser() == null)
			bean.setToUser(new UsersBean());
		bean.getToUser().setId(entity.getToUser().getId());
		
	}
	// Fill Entity From Bean
	public void fillEntity(VisitorsInteractiveEntity entity,VisitorsInteractiveBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new VisitorsInteractiveEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setName(bean.getName());
		entity.setEmail(bean.getEmail());
		entity.setDesc(bean.getDesc());
		entity.setFollow_no(bean.getFollow_no());
		entity.setOccupation(bean.getOccupation());
		entity.setTitle(bean.getTitle());
		
		if(entity.getToUser() == null)
			entity.setToUser(new UsersEntity());
		entity.getToUser().setId(bean.getToUser().getId());
		if(entity.getInteractiveType() == null)
			entity.setInteractiveType(new InteractiveTypesEntity());
		entity.getInteractiveType().setId(bean.getInteractiveType().getId());
	}
}
