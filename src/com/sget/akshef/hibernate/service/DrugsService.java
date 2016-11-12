package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.DrugsBean;
import com.sget.akshef.hibernate.beans.DrugsCompanyBean;
import com.sget.akshef.hibernate.dao.DrugsDAO;
import com.sget.akshef.hibernate.entities.DrugsCompanyEntity;
import com.sget.akshef.hibernate.entities.DrugsEntity;

/**
 * 
 * @author JDeeb
 * Drugs Service
 */
public class DrugsService {
	DrugsDAO dao = null ;
	
	public DrugsService(){
		dao = new DrugsDAO();
	}
	
	public void insert(DrugsBean bean){
		if(bean == null)
			return;
		DrugsEntity entity = new DrugsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(DrugsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        DrugsEntity entity = new DrugsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(DrugsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        DrugsEntity entity = new DrugsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public DrugsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	DrugsBean bean = new DrugsBean();
    	DrugsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<DrugsBean> getAll() {
    	List<DrugsEntity> entities = dao.getAll();
    	List<DrugsBean> beans = new ArrayList<DrugsBean>();
    	DrugsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(DrugsEntity entity : entities){
	    		bean = new DrugsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(DrugsBean bean , DrugsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new DrugsBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setStorage(entity.getStorage());
		bean.setSideEffects(entity.getSideEffects());
		bean.setShelfLife(entity.getShelfLife());
		bean.setQuantity(entity.getQuantity());
		bean.setProductionCompany(entity.getProductionCompany());
		bean.setPrice(entity.getPrice());
		bean.setPregnancyLacations(entity.getPregnancyLacations());
		bean.setPharmaceticalForm(entity.getPharmaceticalForm());
		bean.setIndications(entity.getIndications());
		bean.setIllness(entity.getIllness());
		bean.setDrugNum(entity.getDrugNum());
		bean.setDrugImage(entity.getDrugImage());
		bean.setDosages(entity.getDosages());
		bean.setDescription(entity.getDescription());
		bean.setConflictDrugs(entity.getConflictDrugs());
		bean.setAvalibilty(entity.getAvalibilty());
		bean.setActivePrincible(entity.getActivePrincible());
		bean.setActiveIngredients(entity.getActiveIngredients());
		if(entity.getDrugsCompany() == null)
			return;
		if(bean.getDrugsCompany() == null)
			bean.setDrugsCompany(new DrugsCompanyBean());
		bean.getDrugsCompany().setId(entity.getDrugsCompany().getId());
		bean.getDrugsCompany().setName(entity.getDrugsCompany().getName());
	}
	// Fill Entity From Bean
	public void fillEntity(DrugsEntity entity,DrugsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new DrugsEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		entity.setStorage(bean.getStorage());
		entity.setSideEffects(bean.getSideEffects());
		entity.setShelfLife(bean.getShelfLife());
		entity.setQuantity(bean.getQuantity());
		entity.setProductionCompany(bean.getProductionCompany());
		entity.setPrice(bean.getPrice());
		entity.setPregnancyLacations(bean.getPregnancyLacations());
		entity.setPharmaceticalForm(bean.getPharmaceticalForm());
		entity.setIndications(bean.getIndications());
		entity.setIllness(bean.getIllness());
		entity.setDrugNum(bean.getDrugNum());
		entity.setDrugImage(bean.getDrugImage());
		entity.setDosages(bean.getDosages());
		entity.setDescription(bean.getDescription());
		entity.setConflictDrugs(bean.getConflictDrugs());
		entity.setAvalibilty(bean.getAvalibilty());
		entity.setActivePrincible(bean.getActivePrincible());
		entity.setActiveIngredients(bean.getActiveIngredients());
		if(bean.getDrugsCompany() == null)
			return;
		if(entity.getDrugsCompany() == null)
			entity.setDrugsCompany(new DrugsCompanyEntity());
		entity.getDrugsCompany().setId(bean.getDrugsCompany().getId());
		entity.getDrugsCompany().setName(bean.getDrugsCompany().getName());
	}
}
