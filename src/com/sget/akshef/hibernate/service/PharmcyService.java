package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.PharmcyBean;
import com.sget.akshef.hibernate.dao.PharmcyDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.PharmcyEntity;

/**
 * 
 * @author JDeeb
 * Pharmcy Service
 */
public class PharmcyService {
	PharmcyDAO dao = null ;
	
	public PharmcyService(){
		dao = new PharmcyDAO();
	}
	
	public void insert(PharmcyBean bean){
		if(bean == null)
			return;
		PharmcyEntity entity = new PharmcyEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(PharmcyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        PharmcyEntity entity = new PharmcyEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(PharmcyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        PharmcyEntity entity = new PharmcyEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public PharmcyBean getById(int id) {
    	if(id < 1)
    		return null ;
    	PharmcyBean bean = new PharmcyBean();
    	PharmcyEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<PharmcyBean> getAll() {
    	List<PharmcyEntity> entities = dao.getAll();
    	List<PharmcyBean> beans = new ArrayList<PharmcyBean>();
    	PharmcyBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(PharmcyEntity entity : entities){
	    		bean = new PharmcyBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(PharmcyBean bean , PharmcyEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new PharmcyBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getCategory() == null)
			bean.setCategory(new CategoryBean());
		bean.getCategory().setId(bean.getCategory().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(PharmcyEntity entity,PharmcyBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new PharmcyEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getCategory() == null)
			entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCategory().getId());
	}
}
