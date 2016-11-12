package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.MedicalTourismBean;
import com.sget.akshef.hibernate.dao.MedicalTourismDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.MedicalTourismEntity;

/**
 * 
 * @author JDeeb
 * MedicalTourism Service
 */
public class MedicalTourismService {
	MedicalTourismDAO dao = null ;
	
	public MedicalTourismService(){
		dao = new MedicalTourismDAO();
	}
	
	public void insert(MedicalTourismBean bean){
		if(bean == null)
			return;
		MedicalTourismEntity entity = new MedicalTourismEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(MedicalTourismBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        MedicalTourismEntity entity = new MedicalTourismEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(MedicalTourismBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        MedicalTourismEntity entity = new MedicalTourismEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public MedicalTourismBean getById(int id) {
    	if(id < 1)
    		return null ;
    	MedicalTourismBean bean = new MedicalTourismBean();
    	MedicalTourismEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<MedicalTourismBean> getAll() {
    	List<MedicalTourismEntity> entities = dao.getAll();
    	List<MedicalTourismBean> beans = new ArrayList<MedicalTourismBean>();
    	MedicalTourismBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(MedicalTourismEntity entity : entities){
	    		bean = new MedicalTourismBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(MedicalTourismBean bean , MedicalTourismEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new MedicalTourismBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getCategory() == null)
			bean.setCategory(new CategoryBean());
		bean.getCategory().setId(bean.getCategory().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(MedicalTourismEntity entity,MedicalTourismBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new MedicalTourismEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getCategory() == null)
			entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCategory().getId());
	}
}
