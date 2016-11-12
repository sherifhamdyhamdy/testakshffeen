package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CompanyBean;
import com.sget.akshef.hibernate.beans.CompanyBean;
import com.sget.akshef.hibernate.dao.CompanyDAO;
import com.sget.akshef.hibernate.entities.CompanyEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;
import com.sget.akshef.hibernate.entities.InsuranceCompanyEntity;

/**
 * 
 * @author JDeeb
 * Insurance Company Service
 */
public class CompanyService {
	CompanyDAO dao = null ;
	
	public CompanyService(){
		dao = new CompanyDAO();
	}
	
	public void insert(CompanyBean bean){
		if(bean == null)
			return;
		CompanyEntity entity = new CompanyEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(CompanyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        CompanyEntity entity = new CompanyEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(CompanyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        CompanyEntity entity = new CompanyEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public CompanyBean getById(int id) {
    	if(id < 1)
    		return null ;
    	CompanyBean bean = new CompanyBean();
    	CompanyEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<CompanyBean> getAll() {
    	List<CompanyEntity> entities = dao.getAll();
    	List<CompanyBean> beans = new ArrayList<CompanyBean>();
    	CompanyBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CompanyEntity entity : entities){
	    		bean = new CompanyBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(CompanyBean bean , CompanyEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new CompanyBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setAddress(entity.getAddress());
	}
	// Fill Entity From Bean
	public void fillEntity(CompanyEntity entity,CompanyBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new CompanyEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setAddress(bean.getAddress());
		entity.setTel(bean.getTel());
		entity.setMobile(bean.getMobile());
		entity.setLat(bean.getLat());
		entity.setLng(bean.getLng());
		entity.setEstablishdate(bean.getEstablishdate().getTime()!=0?bean.getEstablishdate():new java.sql.Date(new java.util.Date().getTime()));
		entity.setBiography_en(bean.getBiography_en());
		entity.setBiography_ar(bean.getBiography_ar());
		entity.setWebsite(bean.getWebsite());

		if(bean.getDistric()!=null && bean.getDistric().getId()!=0)
		{
			DistricEntity disEntity=new DistricEntity();
			disEntity.setId(bean.getInBean().getId());
		    entity.setDistric(disEntity);
		}
		
		
		
		
		
		
		
	}
	
	
	public int checkIfUserInCompany(int user_id)
	{
		
		
		return dao.checkIfUserInCompany(user_id);	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
