package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.InsuranceCompanyBean;
import com.sget.akshef.hibernate.dao.InsuranceCompanyDAO;
import com.sget.akshef.hibernate.entities.InsuranceCompanyEntity;

/**
 * 
 * @author JDeeb
 * Insurance Company Service
 */
public class InsuranceCompanyService {
	InsuranceCompanyDAO dao = null ;
	
	Utils utils = Utils.getInstance();
	
	public InsuranceCompanyService(){
		dao = new InsuranceCompanyDAO();
	}
	
	public void insert(InsuranceCompanyBean bean){
		if(bean == null)
			return;
		InsuranceCompanyEntity entity = new InsuranceCompanyEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(InsuranceCompanyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        InsuranceCompanyEntity entity = new InsuranceCompanyEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(InsuranceCompanyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        InsuranceCompanyEntity entity = new InsuranceCompanyEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public InsuranceCompanyBean getById(int id) {
    	if(id < 1)
    		return null ;
    	InsuranceCompanyBean bean = new InsuranceCompanyBean();
    	InsuranceCompanyEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<InsuranceCompanyBean> getAll() {
    	List<InsuranceCompanyEntity> entities = dao.getAll();
    	List<InsuranceCompanyBean> beans = new ArrayList<InsuranceCompanyBean>();
    	InsuranceCompanyBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(InsuranceCompanyEntity entity : entities){
	    		bean = new InsuranceCompanyBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(InsuranceCompanyBean bean , InsuranceCompanyEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new InsuranceCompanyBean();
		// Basics Data
		bean.setActive(utils.hasValue(entity.getActive())?entity.getActive():0);
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setAddress(entity.getAddress());
	}
	// Fill Entity From Bean
	public void fillEntity(InsuranceCompanyEntity entity,InsuranceCompanyBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new InsuranceCompanyEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setAddress(bean.getAddress());
	}
}
