package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sget.akshef.hibernate.beans.MedicalhistoryBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.MedicalhistoryDAO;
import com.sget.akshef.hibernate.entities.MedicalhistoryEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * Medicalhistory Service
 */
public class MedicalhistoryService {
	MedicalhistoryDAO dao = null ;
	
	public MedicalhistoryService(){
		dao = new MedicalhistoryDAO();
	}
	
	public void insert(MedicalhistoryBean bean){
		if(bean == null)
			return;
		MedicalhistoryEntity entity = new MedicalhistoryEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(MedicalhistoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        MedicalhistoryEntity entity = new MedicalhistoryEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(MedicalhistoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        MedicalhistoryEntity entity = new MedicalhistoryEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public MedicalhistoryBean getById(int id) {
    	if(id < 1)
    		return null ;
    	MedicalhistoryBean bean = new MedicalhistoryBean();
    	MedicalhistoryEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<MedicalhistoryBean> getAll() {
    	List<MedicalhistoryEntity> entities = dao.getAll();
    	List<MedicalhistoryBean> beans = new ArrayList<MedicalhistoryBean>();
    	MedicalhistoryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(MedicalhistoryEntity entity : entities){
	    		bean = new MedicalhistoryBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(MedicalhistoryBean bean , MedicalhistoryEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new MedicalhistoryBean();
		// Basics Data
		bean.setId(entity.getId());
	
		bean.setDate_from(entity.getDate_from()!=null?new java.util.Date(entity.getDate_from().getTime()) :new java.util.Date());
		bean.setDate_to(entity.getDate_to()!=null?new java.util.Date(entity.getDate_to().getTime()) :new java.util.Date());
		bean.setSpec_name(entity.getSpec_name());
		bean.setSickness(entity.getSickness());
		bean.setSick_type(entity.getSick_type());
		bean.setReport_type(entity.getReport_type());
		bean.setReport_details(entity.getReport_details());
		bean.setDetails(entity.getDetails());
		bean.setReport_attach(entity.getReport_attach());
	    
		
		if(entity.getUsers() == null)
			bean.setUsers(new UsersBean());
		bean.getUsers().setId(entity.getUsers().getId());
	}
	// Fill Entity From Bean
	public MedicalhistoryEntity fillEntity(MedicalhistoryEntity entity,MedicalhistoryBean bean){
		if(bean == null)
			return null;
		if(entity == null)
			entity = new MedicalhistoryEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setDate_from(bean.getDate_from()!=null?new java.sql.Date(bean.getDate_from().getTime()) :new java.sql.Date(new java.util.Date().getTime()));
		entity.setDate_to(bean.getDate_to()!=null?new java.sql.Date(bean.getDate_to().getTime()) :new java.sql.Date(new java.util.Date().getTime()));
		entity.setSpec_name(bean.getSpec_name());
		entity.setSickness(bean.getSickness());
		entity.setSick_type(bean.getSick_type());
		entity.setReport_type(bean.getReport_type());
		entity.setReport_details(bean.getReport_details());
		entity.setDetails(bean.getDetails());
		entity.setReport_attach(bean.getReport_attach());
		
		if(entity.getUsers() == null)
			entity.setUsers(new UsersEntity());
	entity.getUsers().setId(bean.getUsers().getId());
		
		return entity;
	}
}
