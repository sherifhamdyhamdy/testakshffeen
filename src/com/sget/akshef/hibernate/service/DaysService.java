package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.sget.akshef.hibernate.beans.DaysBean;
import com.sget.akshef.hibernate.beans.HourBean;
import com.sget.akshef.hibernate.dao.DaysDAO;
import com.sget.akshef.hibernate.entities.DaysEntity;

/**
 * 
 * @author JDeeb
 * Days Service
 */
public class DaysService {
	DaysDAO dao = null ;
	
	public DaysService(){
		dao = new DaysDAO();
	}
	
	public void insert(DaysBean bean){
		if(bean == null)
			return;
		DaysEntity entity = new DaysEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(DaysBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        DaysEntity entity = new DaysEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(DaysBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        DaysEntity entity = new DaysEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public DaysBean getById(int id) {
    	if(id < 1)
    		return null ;
    	DaysBean bean = new DaysBean();
    	DaysEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<DaysBean> getAll() {
    	List<DaysEntity> entities = dao.getAll();
    	List<DaysBean> beans = new ArrayList<DaysBean>();
    	DaysBean bean ;
    	if(entities != null && entities.size() > 0){
    		for(DaysEntity entity : entities){
	    		bean = new DaysBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(DaysBean bean , DaysEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new DaysBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setName_ar(entity.getName_ar());
		bean.setName_en(entity.getName_en());
	}
	// Fill Entity From Bean
	public void fillEntity(DaysEntity entity,DaysBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new DaysEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setName_ar(bean.getName_ar());
		entity.setName_en(bean.getName_en());
	}
	
	
	 public List<HourBean> getAllHours() {
	        List<Object> entities = null;
	       
	        	entities = dao.getAllHours();
	        	List<HourBean> beans = new ArrayList<HourBean>();
	        	Object arr[];
	        	HourBean bean;
	        	if(entities != null && entities.size() > 0){
	        		for(Object entity : entities){
	        			bean=new HourBean();
	        			 arr=(Object[]) entity;
	        		bean.setId(Integer.parseInt(arr[0].toString()))	 ;
	        		bean.setHour(arr[1].toString())	;
	        		beans.add(bean);
	        		}
	        		
	        	}
	        
	        return beans;
	    }
	
	
	
	
	
	
	
	
	
	
	
}
