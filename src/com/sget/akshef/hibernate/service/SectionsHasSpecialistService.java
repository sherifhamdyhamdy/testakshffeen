package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SectionsHasSpecialistBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.dao.SectionsHasSpecialistDAO;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SectionsHasSpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;

/**
 * 
 * @author JDeeb
 * Insurance Company Service
 */
public class SectionsHasSpecialistService {
	SectionsHasSpecialistDAO dao = null ;
	
	public SectionsHasSpecialistService(){
		dao = new SectionsHasSpecialistDAO();
	}
	
	public void insert(SectionsHasSpecialistBean bean){
		if(bean == null)
			return;
		SectionsHasSpecialistEntity entity = new SectionsHasSpecialistEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(SectionsHasSpecialistBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        SectionsHasSpecialistEntity entity = new SectionsHasSpecialistEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(SectionsHasSpecialistBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        SectionsHasSpecialistEntity entity = new SectionsHasSpecialistEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public SectionsHasSpecialistBean getById(int id) {
    	if(id < 1)
    		return null ;
    	SectionsHasSpecialistBean bean = new SectionsHasSpecialistBean();
    	SectionsHasSpecialistEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<SectionsHasSpecialistBean> getAll() {
    	List<SectionsHasSpecialistEntity> entities = dao.getAll();
    	List<SectionsHasSpecialistBean> beans = new ArrayList<SectionsHasSpecialistBean>();
    	SectionsHasSpecialistBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(SectionsHasSpecialistEntity entity : entities){
	    		bean = new SectionsHasSpecialistBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(SectionsHasSpecialistBean bean , SectionsHasSpecialistEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new SectionsHasSpecialistBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getSections() == null)
			bean.setSections(new SectionsBean());
		bean.getSections().setId(entity.getSections().getId());
		if(bean.getSpecialist() == null)
			bean.setSpecialist(new SpecialistBean());
		bean.getSpecialist().setId(entity.getSpecialist().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(SectionsHasSpecialistEntity entity,SectionsHasSpecialistBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new SectionsHasSpecialistEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getSections() == null)
			entity.setSections(new SectionsEntity());
		entity.getSections().setId(bean.getSections().getId());
		if(entity.getSpecialist() == null)
			entity.setSpecialist(new SpecialistEntity());
		entity.getSpecialist().setId(bean.getSpecialist().getId());
	}
}
