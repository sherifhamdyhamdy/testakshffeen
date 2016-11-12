package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.dao.CountryDAO;
import com.sget.akshef.hibernate.entities.CountryEntity;

/**
 * 
 * @author JDeeb
 * Country Service
 */
public class CountryService {
	CountryDAO dao = null ;
	
	public CountryService(){
		dao = new CountryDAO();
	}
	
	public void insert(CountryBean bean){
		if(bean == null)
			return;
		CountryEntity entity = new CountryEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(CountryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        CountryEntity entity = new CountryEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(CountryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        CountryEntity entity = new CountryEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public CountryBean getById(int id) {
    	if(id < 1)
    		return null ;
    	CountryBean bean = new CountryBean();
    	CountryEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<CountryBean> getAll() {
    	List<CountryEntity> entities = dao.getAll();
    	List<CountryBean> beans = new ArrayList<CountryBean>();
    	CountryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CountryEntity entity : entities){
	    		bean = new CountryBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    
    public List<CountryBean> getCountryName(String name_en,String name_ar)
    {
    	List<CountryEntity> entities = dao.getCountryName(name_en, name_ar);
    	List<CountryBean> beans = new ArrayList<CountryBean>();
    	CountryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CountryEntity entity : entities){
	    		bean = new CountryBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    
	// Fill Bean From Entity
	public void fillBean(CountryBean bean , CountryEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new CountryBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setCode(entity.getCode());
	}
	// Fill Entity From Bean
	public void fillEntity(CountryEntity entity,CountryBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new CountryEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setCode(bean.getCode());
	}
}
