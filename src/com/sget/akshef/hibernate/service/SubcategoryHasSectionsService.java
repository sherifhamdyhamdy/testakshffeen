package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.beans.SubcategoryHasSectionsBean;
import com.sget.akshef.hibernate.dao.SubcategoryHasSectionsDAO;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsEntity;

/**
 * 
 * @author JDeeb
 * SubcategoryHasSections Service
 */
public class SubcategoryHasSectionsService {
	SubcategoryHasSectionsDAO dao = null ;
	
	public SubcategoryHasSectionsService(){
		dao = new SubcategoryHasSectionsDAO();
	}
	
	public void insert(SubcategoryHasSectionsBean bean){
		if(bean == null)
			return;
		SubcategoryHasSectionsEntity entity = new SubcategoryHasSectionsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(SubcategoryHasSectionsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        SubcategoryHasSectionsEntity entity = new SubcategoryHasSectionsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(SubcategoryHasSectionsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        SubcategoryHasSectionsEntity entity = new SubcategoryHasSectionsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public SubcategoryHasSectionsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	SubcategoryHasSectionsBean bean = new SubcategoryHasSectionsBean();
    	SubcategoryHasSectionsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    
    
    public List<SubcategoryHasSectionsBean> getAllBySubcategoryID(int subCat) {
    	List<SubcategoryHasSectionsEntity> entities = dao.getAllBySubcategoryID(subCat);
    	List<SubcategoryHasSectionsBean> beans = new ArrayList<SubcategoryHasSectionsBean>();
    	SubcategoryHasSectionsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(SubcategoryHasSectionsEntity entity : entities){
	    		bean = new SubcategoryHasSectionsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    // Get All
    public List<SubcategoryHasSectionsBean> getAll() {
    	List<SubcategoryHasSectionsEntity> entities = dao.getAll();
    	List<SubcategoryHasSectionsBean> beans = new ArrayList<SubcategoryHasSectionsBean>();
    	SubcategoryHasSectionsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(SubcategoryHasSectionsEntity entity : entities){
	    		bean = new SubcategoryHasSectionsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    public List<SubcategoryHasSectionsBean> getAllByCategory(int catId){
    	List<SubcategoryHasSectionsEntity> entities = dao.getAllByCategory(catId);
    	List<SubcategoryHasSectionsBean> beans = new ArrayList<SubcategoryHasSectionsBean>();
    	SubcategoryHasSectionsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(SubcategoryHasSectionsEntity entity : entities){
	    		bean = new SubcategoryHasSectionsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
	// Fill Bean From Entity
	public void fillBean(SubcategoryHasSectionsBean bean , SubcategoryHasSectionsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new SubcategoryHasSectionsBean();
		// Basics Data
		bean.setId(entity.getId());
		SectionsBean sect=new SectionsBean();
		SubcategoryBean subcat=new SubcategoryBean();
		
		if(entity.getSections()!=null)
		{
			sect.setId(entity.getSections().getId());
			sect.setNameAr(entity.getSections().getNameAr());
			sect.setNameEn(entity.getSections().getNameEn());
			sect.setActive(entity.getSections().getActive());
		}
	
		
		if(entity.getSubcategory()!=null)
		{
			subcat.setId(entity.getSubcategory().getId());
			subcat.setNameAr(entity.getSubcategory().getNameAr());
			subcat.setNameEn(entity.getSubcategory().getNameEn());
			subcat.setActive(entity.getSubcategory().getActive());
		}
		
	
		
		bean.setSections(sect);
		bean.setSubcategory(subcat);
		
		
	
	}
	// Fill Entity From Bean
	public void fillEntity(SubcategoryHasSectionsEntity entity,SubcategoryHasSectionsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new SubcategoryHasSectionsEntity();
		// Basics Data
		entity.setId(bean.getId());
		
		if(entity.getSubcategory() == null)
			entity.setSubcategory(new SubcategoryEntity());
		entity.getSubcategory().setId(bean.getSubcategory().getId());
		
		if(entity.getSections() == null)
			entity.setSections(new SectionsEntity());
		entity.getSections().setId(bean.getSections().getId());
	}
	
	
	public boolean deleteBysubCategory(int section, int subcat) {
		return dao.deleteBysubCategory(section, subcat);
		
   }
}
