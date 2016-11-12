package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.InsuranceCompanyBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.beans.UnitHasInsuranceCompanyBean;
import com.sget.akshef.hibernate.dao.UnitHasInsuranceCompanyDAO;
import com.sget.akshef.hibernate.entities.InsuranceCompanyEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UnitHasInsuranceCompanyEntity;

/**
 * 
 * @author JDeeb
 * UnitHasInsuranceCompany Service
 */
public class UnitHasInsuranceCompanyService {
	UnitHasInsuranceCompanyDAO dao = null ;
	
	public UnitHasInsuranceCompanyService(){
		dao = new UnitHasInsuranceCompanyDAO();
	}
	
	public void insert(UnitHasInsuranceCompanyBean bean){
		if(bean == null)
			return;
		UnitHasInsuranceCompanyEntity entity = new UnitHasInsuranceCompanyEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(UnitHasInsuranceCompanyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        UnitHasInsuranceCompanyEntity entity = new UnitHasInsuranceCompanyEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(UnitHasInsuranceCompanyBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        UnitHasInsuranceCompanyEntity entity = new UnitHasInsuranceCompanyEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public UnitHasInsuranceCompanyBean getById(int id) {
    	if(id < 1)
    		return null ;
    	UnitHasInsuranceCompanyBean bean = new UnitHasInsuranceCompanyBean();
    	UnitHasInsuranceCompanyEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<UnitHasInsuranceCompanyBean> getAll() {
    	List<UnitHasInsuranceCompanyEntity> entities = dao.getAll();
    	List<UnitHasInsuranceCompanyBean> beans = new ArrayList<UnitHasInsuranceCompanyBean>();
    	UnitHasInsuranceCompanyBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(UnitHasInsuranceCompanyEntity entity : entities){
	    		bean = new UnitHasInsuranceCompanyBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(UnitHasInsuranceCompanyBean bean , UnitHasInsuranceCompanyEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new UnitHasInsuranceCompanyBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getInsuranceCompany() == null)
			bean.setInsuranceCompany(new InsuranceCompanyBean());
		bean.getInsuranceCompany().setId(entity.getInsuranceCompany().getId());
		if(bean.getUnit() == null)
			bean.setUnit(new UnitBean());
		bean.getUnit().setId(entity.getUnit().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(UnitHasInsuranceCompanyEntity entity,UnitHasInsuranceCompanyBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new UnitHasInsuranceCompanyEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getInsuranceCompany() == null)
			entity.setInsuranceCompany(new InsuranceCompanyEntity());
		entity.getInsuranceCompany().setId(bean.getInsuranceCompany().getId());
		if(entity.getUnit() == null)
			entity.setUnit(new UnitEntity());
		entity.getUnit().setId(bean.getUnit().getId());
	}
}
