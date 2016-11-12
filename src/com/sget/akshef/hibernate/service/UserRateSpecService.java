package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.UserRateSpecBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.UserRateSpecDAO;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.UserRateSpecEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * UserRateSpec Service
 */
public class UserRateSpecService {
	UserRateSpecDAO dao = null ;
	
	public UserRateSpecService(){
		dao = new UserRateSpecDAO();
	}
	
	public void insert(UserRateSpecBean bean){
		if(bean == null)
			return;
		UserRateSpecEntity entity = new UserRateSpecEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(UserRateSpecBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        UserRateSpecEntity entity = new UserRateSpecEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(UserRateSpecBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        UserRateSpecEntity entity = new UserRateSpecEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public UserRateSpecBean getById(int id) {
    	if(id < 1)
    		return null ;
    	UserRateSpecBean bean = new UserRateSpecBean();
    	UserRateSpecEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<UserRateSpecBean> getAll() {
    	List<UserRateSpecEntity> entities = dao.getAll();
    	List<UserRateSpecBean> beans = new ArrayList<UserRateSpecBean>();
    	UserRateSpecBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(UserRateSpecEntity entity : entities){
	    		bean = new UserRateSpecBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(UserRateSpecBean bean , UserRateSpecEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new UserRateSpecBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setRate(entity.getRate());
		if(bean.getSpecialist() == null)
			bean.setSpecialist(new SpecialistBean());
		bean.getSpecialist().setId(entity.getSpecialist().getId());
		if(bean.getUsers() == null)
			bean.setUsers(new UsersBean());
		bean.getUsers().setId(entity.getUsers().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(UserRateSpecEntity entity,UserRateSpecBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new UserRateSpecEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setRate(bean.getRate());
		if(entity.getSpecialist() == null)
			entity.setSpecialist(new SpecialistEntity());
		entity.getSpecialist().setId(bean.getSpecialist().getId());
		if(entity.getUsers() == null)
			entity.setUsers(new UsersEntity());
		entity.getUsers().setId(bean.getUsers().getId());
	}
}
