package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.MedicalInstrumentsBean;
import com.sget.akshef.hibernate.dao.MedicalInstrumentsDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.MedicalInstrumentsEntity;

/**
 * 
 * @author JDeeb
 * MedicalInstruments Service
 */
public class MedicalInstrumentsService {
	MedicalInstrumentsDAO dao = null ;
	
	public MedicalInstrumentsService(){
		dao = new MedicalInstrumentsDAO();
	}
	
	public void insert(MedicalInstrumentsBean bean){
		if(bean == null)
			return;
		MedicalInstrumentsEntity entity = new MedicalInstrumentsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(MedicalInstrumentsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        MedicalInstrumentsEntity entity = new MedicalInstrumentsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(MedicalInstrumentsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        MedicalInstrumentsEntity entity = new MedicalInstrumentsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public MedicalInstrumentsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	MedicalInstrumentsBean bean = new MedicalInstrumentsBean();
    	MedicalInstrumentsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<MedicalInstrumentsBean> getAll() {
    	List<MedicalInstrumentsEntity> entities = dao.getAll();
    	List<MedicalInstrumentsBean> beans = new ArrayList<MedicalInstrumentsBean>();
    	MedicalInstrumentsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(MedicalInstrumentsEntity entity : entities){
	    		bean = new MedicalInstrumentsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(MedicalInstrumentsBean bean , MedicalInstrumentsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new MedicalInstrumentsBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getCategory() == null)
			bean.setCategory(new CategoryBean());
		bean.getCategory().setId(bean.getCategory().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(MedicalInstrumentsEntity entity,MedicalInstrumentsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new MedicalInstrumentsEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getCategory() == null)
			entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCategory().getId());
	}
}
