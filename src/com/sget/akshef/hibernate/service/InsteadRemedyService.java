package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.InsteadRemedyBean;
import com.sget.akshef.hibernate.dao.InsteadRemedyDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.InsteadRemedyEntity;

/**
 * 
 * @author JDeeb
 * InsteadRemedy Service
 */
public class InsteadRemedyService {
	InsteadRemedyDAO dao = null ;
	
	public InsteadRemedyService(){
		dao = new InsteadRemedyDAO();
	}
	
	public void insert(InsteadRemedyBean bean){
		if(bean == null)
			return;
		InsteadRemedyEntity entity = new InsteadRemedyEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(InsteadRemedyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        InsteadRemedyEntity entity = new InsteadRemedyEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(InsteadRemedyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        InsteadRemedyEntity entity = new InsteadRemedyEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public InsteadRemedyBean getById(int id) {
    	if(id < 1)
    		return null ;
    	InsteadRemedyBean bean = new InsteadRemedyBean();
    	InsteadRemedyEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<InsteadRemedyBean> getAll() {
    	List<InsteadRemedyEntity> entities = dao.getAll();
    	List<InsteadRemedyBean> beans = new ArrayList<InsteadRemedyBean>();
    	InsteadRemedyBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(InsteadRemedyEntity entity : entities){
	    		bean = new InsteadRemedyBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(InsteadRemedyBean bean , InsteadRemedyEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new InsteadRemedyBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getCategory() == null)
			bean.setCategory(new CategoryBean());
		bean.getCategory().setId(bean.getCategory().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(InsteadRemedyEntity entity,InsteadRemedyBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new InsteadRemedyEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getCategory() == null)
			entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCategory().getId());
	}
}
