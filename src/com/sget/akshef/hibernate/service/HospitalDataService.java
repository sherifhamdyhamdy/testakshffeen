package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.HospitalDataBean;
import com.sget.akshef.hibernate.dao.HospitalDataDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.HospitalDataEntity;

/**
 * 
 * @author JDeeb
 * HospitalData Service
 */
public class HospitalDataService {
	HospitalDataDAO dao = null ;
	
	public HospitalDataService(){
		dao = new HospitalDataDAO();
	}
	
	public void insert(HospitalDataBean bean){
		if(bean == null)
			return;
		HospitalDataEntity entity = new HospitalDataEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(HospitalDataBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        HospitalDataEntity entity = new HospitalDataEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(HospitalDataBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        HospitalDataEntity entity = new HospitalDataEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public HospitalDataBean getById(int id) {
    	if(id < 1)
    		return null ;
    	HospitalDataBean bean = new HospitalDataBean();
    	HospitalDataEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<HospitalDataBean> getAll() {
    	List<HospitalDataEntity> entities = dao.getAll();
    	List<HospitalDataBean> beans = new ArrayList<HospitalDataBean>();
    	HospitalDataBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(HospitalDataEntity entity : entities){
	    		bean = new HospitalDataBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(HospitalDataBean bean , HospitalDataEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new HospitalDataBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setName(entity.getName());
		if(bean.getCategory() == null)
			bean.setCategory(new CategoryBean());
		bean.getCategory().setId(bean.getCategory().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(HospitalDataEntity entity,HospitalDataBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new HospitalDataEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setName(bean.getName());
		if(entity.getCategory() == null)
			entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCategory().getId());
	}
}
