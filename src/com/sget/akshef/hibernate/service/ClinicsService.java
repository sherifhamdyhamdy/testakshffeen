package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.ClinicsBean;
import com.sget.akshef.hibernate.dao.ClinicsDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.ClinicsEntity;

/**
 * 
 * @author JDeeb
 * Clinics Service
 */
public class ClinicsService {
	ClinicsDAO dao = null ;
	
	public ClinicsService(){
		dao = new ClinicsDAO();
	}
	
	public void insert(ClinicsBean bean){
		if(bean == null)
			return;
		ClinicsEntity entity = new ClinicsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(ClinicsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        ClinicsEntity entity = new ClinicsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(ClinicsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        ClinicsEntity entity = new ClinicsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public ClinicsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	ClinicsBean bean = new ClinicsBean();
    	ClinicsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<ClinicsBean> getAll() {
    	List<ClinicsEntity> entities = dao.getAll();
    	List<ClinicsBean> beans = new ArrayList<ClinicsBean>();
    	ClinicsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(ClinicsEntity entity : entities){
	    		bean = new ClinicsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(ClinicsBean bean , ClinicsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ClinicsBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getCategory() == null)
			bean.setCategory(new CategoryBean());
		bean.getCategory().setId(bean.getCategory().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(ClinicsEntity entity,ClinicsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new ClinicsEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getCategory() == null)
			entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCategory().getId());
	}
}
