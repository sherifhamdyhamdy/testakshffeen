package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.DrugsCompanyBean;
import com.sget.akshef.hibernate.dao.DrugsCompanyDAO;
import com.sget.akshef.hibernate.entities.DrugsCompanyEntity;

/**
 * 
 * @author JDeeb
 * DrugsCompany Service
 */
public class DrugsCompanyService {
	DrugsCompanyDAO dao = null ;
	
	public DrugsCompanyService(){
		dao = new DrugsCompanyDAO();
	}
	
	public void insert(DrugsCompanyBean bean){
		if(bean == null)
			return;
		DrugsCompanyEntity entity = new DrugsCompanyEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(DrugsCompanyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        DrugsCompanyEntity entity = new DrugsCompanyEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(DrugsCompanyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        DrugsCompanyEntity entity = new DrugsCompanyEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public DrugsCompanyBean getById(int id) {
    	if(id < 1)
    		return null ;
    	DrugsCompanyBean bean = new DrugsCompanyBean();
    	DrugsCompanyEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<DrugsCompanyBean> getAll() {
    	List<DrugsCompanyEntity> entities = dao.getAll();
    	List<DrugsCompanyBean> beans = new ArrayList<DrugsCompanyBean>();
    	DrugsCompanyBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(DrugsCompanyEntity entity : entities){
	    		bean = new DrugsCompanyBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(DrugsCompanyBean bean , DrugsCompanyEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new DrugsCompanyBean();
		// Basics Data
		bean.setId(entity.getId());
		entity.setName(bean.getName());
	}
	// Fill Entity From Bean
	public void fillEntity(DrugsCompanyEntity entity,DrugsCompanyBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new DrugsCompanyEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setName(bean.getName());
	}
}
