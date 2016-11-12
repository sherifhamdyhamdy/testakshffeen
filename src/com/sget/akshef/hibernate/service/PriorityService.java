package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.dao.PriorityDAO;
import com.sget.akshef.hibernate.entities.PriorityEntity;

/**
 * 
 * @author JDeeb
 * Priority Service
 */
public class PriorityService {
	PriorityDAO dao = null ;
	
	public PriorityService(){
		dao = new PriorityDAO();
	}
	
	public void insert(PriorityBean bean){
		if(bean == null)
			return;
		PriorityEntity entity = new PriorityEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(PriorityBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        PriorityEntity entity = new PriorityEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(PriorityBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        PriorityEntity entity = new PriorityEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public PriorityBean getById(int id) {
    	if(id < 1)
    		return null ;
    	PriorityBean bean = new PriorityBean();
    	PriorityEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<PriorityBean> getAll() {
    	List<PriorityEntity> entities = dao.getAll();
    	List<PriorityBean> beans = new ArrayList<PriorityBean>();
    	PriorityBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(PriorityEntity entity : entities){
	    		bean = new PriorityBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(PriorityBean bean , PriorityEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new PriorityBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setCode(entity.getCode());
		bean.setNameAr(entity.getNameAr());
		bean.setNameEn(entity.getNameEn());
	}
	// Fill Entity From Bean
	public void fillEntity(PriorityEntity entity,PriorityBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new PriorityEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setActive(bean.getActive());
		entity.setCode(bean.getCode());
		entity.setNameAr(bean.getNameAr());
		entity.setNameEn(bean.getNameEn());
	}
}
