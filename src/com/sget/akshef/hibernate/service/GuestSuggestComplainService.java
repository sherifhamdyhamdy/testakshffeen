package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.GuestSuggestComplainBean;
import com.sget.akshef.hibernate.dao.GuestSuggestComplainDAO;
import com.sget.akshef.hibernate.entities.GuestSuggestComplainEntity;

/**
 * 
 * @author JDeeb
 * GuestSuggestComplain Service
 */
public class GuestSuggestComplainService {
	GuestSuggestComplainDAO dao = null ;
	
	public GuestSuggestComplainService(){
		dao = new GuestSuggestComplainDAO();
	}
	
	/**
	 * Insert New Suggest Or Complain
	 * @param bean
	 */
	public int insert(GuestSuggestComplainBean bean){
		if(bean == null)
			return -1;
		GuestSuggestComplainEntity entity = new GuestSuggestComplainEntity();
		fillEntity(entity, bean);
		int id = dao.insert(entity);
		bean.setId(entity.getId());
		
		return id;
	}
	/**
	 * Get All Suggest And Complain
	 * @return
	 */
	public List<GuestSuggestComplainBean> getAll() {
    	List<GuestSuggestComplainEntity> entities = dao.getAll();
    	List<GuestSuggestComplainBean> beans = new ArrayList<GuestSuggestComplainBean>();
    	GuestSuggestComplainBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(GuestSuggestComplainEntity entity : entities){
	    		bean = new GuestSuggestComplainBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	/**
	 * Get All Suggest and Complain By Type
	 * @param type
	 * @return
	 */
	public List<GuestSuggestComplainBean> getAllByType(int type) {
		List<GuestSuggestComplainEntity> entities = dao.getAllByType(type);
    	List<GuestSuggestComplainBean> beans = new ArrayList<GuestSuggestComplainBean>();
    	GuestSuggestComplainBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(GuestSuggestComplainEntity entity : entities){
	    		bean = new GuestSuggestComplainBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	/*
	 * Functions Util
	 */
	// Fill Entity From Bean
	public void fillEntity(GuestSuggestComplainEntity entity,GuestSuggestComplainBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new GuestSuggestComplainEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setType(bean.getType());
		entity.setName(bean.getName());
		entity.setJob(bean.getJob());
		entity.setEmail(bean.getEmail());
		entity.setTitle(bean.getTitle());
		entity.setDetails(bean.getDetails());
		entity.setImage(bean.getImage());
	}
	// Fill Bean From Bean
	public void fillBean(GuestSuggestComplainBean bean, GuestSuggestComplainEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new GuestSuggestComplainBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setType(entity.getType());
		bean.setName(entity.getName());
		bean.setJob(entity.getJob());
		bean.setEmail(entity.getEmail());
		bean.setTitle(entity.getTitle());
		bean.setDetails(entity.getDetails());
	}
}