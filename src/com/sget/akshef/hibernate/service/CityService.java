package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.dao.CityDAO;
import com.sget.akshef.hibernate.entities.CityEntity;
import com.sget.akshef.hibernate.entities.CountryEntity;

/**
 * 
 * @author JDeeb
 * City Service
 */
public class CityService {
	CityDAO dao = null ;
	
	public CityService(){
		dao = new CityDAO();
	}
	
	public void insert(CityBean bean){
		if(bean == null)
			return;
		CityEntity entity = new CityEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(CityBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        CityEntity entity = new CityEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(CityBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        CityEntity entity = new CityEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public CityBean getById(int id) {
    	if(id < 1)
    		return null ;
    	CityBean bean = new CityBean();
    	CityEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<CityBean> getAll() {
    	List<CityEntity> entities = dao.getAll();
    	List<CityBean> beans = new ArrayList<CityBean>();
    	CityBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CityEntity entity : entities){
	    		bean = new CityBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    // check if City exist or not
    public int checkIfCityExist(String nameAr, String nameEn){
    	return dao.checkIfCityExist(nameAr, nameEn);
    }
	// Fill Bean From Entity
	public void fillBean(CityBean bean , CityEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new CityBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		
		if(entity.getCountry() != null){
			CountryBean countryBean=new CountryBean();
			countryBean .setId(entity.getCountry().getId());
			countryBean.setNameAr(entity.getCountry().getNameAr());
			countryBean.setNameEn(entity.getCountry().getNameEn());
			bean.setCountry(countryBean);
		}
		
	}
	// Fill Entity From Bean
	public void fillEntity(CityEntity entity,CityBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new CityEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		
		if(entity.getCountry() == null)
			entity.setCountry(new CountryEntity());
		entity.getCountry().setId(bean.getCountry().getId());
		entity.getCountry().setNameAr(bean.getCountry().getNameAr());
		entity.getCountry().setNameEn(bean.getCountry().getNameEn());
	}
	
	public List<CityBean> getAllCitiesByCountry(int country_id) {
    	List<CityEntity> entities = dao.getAllCitiesByCountry(country_id);
    	List<CityBean> beans = new ArrayList<CityBean>();
    	CityBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CityEntity entity : entities){
	    		bean = new CityBean();
	    		//bean.setActive(entity.getActive());
	    		bean.setId(entity.getId());
	    		//bean.setNameEn(entity.getNameEn());
	    		bean.setNameAr(entity.getNameAr());
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
}
