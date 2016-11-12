package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.ProfissionaldataBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.ProfissionaldataDAO;
import com.sget.akshef.hibernate.entities.ProfissionaldataEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * Profissionaldata Service
 */
public class ProfissionaldataService {
	ProfissionaldataDAO dao = null ;
	
	public ProfissionaldataService(){
		dao = new ProfissionaldataDAO();
	}
	
	public void insert(ProfissionaldataBean bean){
		if(bean == null)
			return;
		ProfissionaldataEntity entity = new ProfissionaldataEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(ProfissionaldataBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        ProfissionaldataEntity entity = new ProfissionaldataEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(ProfissionaldataBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        ProfissionaldataEntity entity = new ProfissionaldataEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public ProfissionaldataBean getById(int id) {
    	if(id < 1)
    		return null ;
    	ProfissionaldataBean bean = new ProfissionaldataBean();
    	ProfissionaldataEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<ProfissionaldataBean> getAll() {
    	List<ProfissionaldataEntity> entities = dao.getAll();
    	List<ProfissionaldataBean> beans = new ArrayList<ProfissionaldataBean>();
    	ProfissionaldataBean bean ;
    	if(entities != null && entities.size() > 0){	
    		for(ProfissionaldataEntity entity : entities){
	    		bean = new ProfissionaldataBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(ProfissionaldataBean bean , ProfissionaldataEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ProfissionaldataBean();
		// Basics Data
		bean.setId(entity.getId());
//		bean.setDegree(entity.getDegree().getDegree());
		if(bean.getUsers() == null)
			bean.setUsers(new UsersBean());
		bean.getUsers().setId(bean.getUsers().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(ProfissionaldataEntity entity,ProfissionaldataBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new ProfissionaldataEntity();
		// Basics Data
		entity.setId(bean.getId());
//		entity.setDegree(bean.getDegree());
		if(entity.getUsers() == null)
			entity.setUsers(new UsersEntity());
		entity.getUsers().setId(bean.getUsers().getId());
	}
}
