package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.SpecialistHasBranchBean;
import com.sget.akshef.hibernate.dao.SpecialistHasBranchDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistHasBranchEntity;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

/**
 * 
 * @author JDeeb
 * Specialist Has Branch Service
 */
public class SpecialistHasBranchService {
	SpecialistHasBranchDAO dao = null ;
	
	public SpecialistHasBranchService(){
		dao = new SpecialistHasBranchDAO();
	}
	
	public void insert(SpecialistHasBranchBean bean){
		if(bean == null)
			return;
		SpecialistHasBranchEntity entity = new SpecialistHasBranchEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(SpecialistHasBranchBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        SpecialistHasBranchEntity entity = new SpecialistHasBranchEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(SpecialistHasBranchBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        SpecialistHasBranchEntity entity = new SpecialistHasBranchEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public SpecialistHasBranchBean getById(int id) {
    	if(id < 1)
    		return null ;
    	SpecialistHasBranchBean bean = new SpecialistHasBranchBean();
    	SpecialistHasBranchEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<SpecialistHasBranchBean> getAll() {
    	List<SpecialistHasBranchEntity> entities = dao.getAll();
    	List<SpecialistHasBranchBean> beans = new ArrayList<SpecialistHasBranchBean>();
    	SpecialistHasBranchBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(SpecialistHasBranchEntity entity : entities){
	    		bean = new SpecialistHasBranchBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(SpecialistHasBranchBean bean , SpecialistHasBranchEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new SpecialistHasBranchBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getBranch() == null)
			bean.setBranch(new BranchBean());
		bean.getBranch().setId(entity.getBranch().getId());
		if(bean.getSpecialist() == null)
			bean.setSpecialist(new SpecialistBean());
		bean.getSpecialist().setId(entity.getSpecialist().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(SpecialistHasBranchEntity entity,SpecialistHasBranchBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new SpecialistHasBranchEntity();
		// Basics Data
		entity.setId(bean.getId());
		if( entity.getBranch() == null )
			entity.setBranch(new BranchEntity());
		entity.getBranch().setId(bean.getBranch().getId());
		if( entity.getSpecialist() == null )
			entity.setSpecialist(new SpecialistEntity());
		entity.getSpecialist().setId(bean.getSpecialist().getId());
	}
	
	
    public List<SpecialistHasBranchBean> searchDoctorsClinics(SearchDoctorsCriteria search,String url){
    	List<SpecialistHasBranchEntity> entities = dao.searchDoctorsClinics(search);
    	List<SpecialistHasBranchBean> beans = new ArrayList<SpecialistHasBranchBean>();
    	SpecialistHasBranchBean bean = null ;
    	if(entities != null && entities.size() > 0){
	    	for(SpecialistHasBranchEntity entity : entities){
	    		bean = new SpecialistHasBranchBean();
	    		fillBean(bean, entity);
//	    		getSpecialistRating(bean);
//	    		bean.getUsers().setProfile_img(url+bean.getUsers().getProfile_img());
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
}
