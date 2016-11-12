package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.RadiolgyCenterBean;
import com.sget.akshef.hibernate.dao.RadiolgyCenterDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.RadiolgyCenterEntity;

/**
 * 
 * @author JDeeb
 * RadiolgyCenter Service
 */
public class RadiolgyCenterService {
	RadiolgyCenterDAO dao = null ;
	
	public RadiolgyCenterService(){
		dao = new RadiolgyCenterDAO();
	}
	
	public void insert(RadiolgyCenterBean bean){
		if(bean == null)
			return;
		RadiolgyCenterEntity entity = new RadiolgyCenterEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(RadiolgyCenterBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        RadiolgyCenterEntity entity = new RadiolgyCenterEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(RadiolgyCenterBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        RadiolgyCenterEntity entity = new RadiolgyCenterEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public RadiolgyCenterBean getById(int id) {
    	if(id < 1)
    		return null ;
    	RadiolgyCenterBean bean = new RadiolgyCenterBean();
    	RadiolgyCenterEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<RadiolgyCenterBean> getAll() {
    	List<RadiolgyCenterEntity> entities = dao.getAll();
    	List<RadiolgyCenterBean> beans = new ArrayList<RadiolgyCenterBean>();
    	RadiolgyCenterBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(RadiolgyCenterEntity entity : entities){
	    		bean = new RadiolgyCenterBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(RadiolgyCenterBean bean , RadiolgyCenterEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new RadiolgyCenterBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getCategory() == null)
			bean.setCategory(new CategoryBean());
		bean.getCategory().setId(entity.getCategory().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(RadiolgyCenterEntity entity,RadiolgyCenterBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new RadiolgyCenterEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getCategory() == null)
			entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCategory().getId());
	}
}
