package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.CategoryHasSubcategoryBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.dao.CategoryHasSubcategoryDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.CategoryHasSubcategoryEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;

/**
 * 
 * @author JDeeb
 * Category-SubCategory Relation Table Service
 */
public class CategoryHasSubcategoryService  {
	CategoryHasSubcategoryDAO dao = null ;
	
	public CategoryHasSubcategoryService(){
		dao = new CategoryHasSubcategoryDAO();
	}
	
	public void insert(CategoryHasSubcategoryBean bean){
		if(bean == null)
			return;
		CategoryHasSubcategoryEntity entity = new CategoryHasSubcategoryEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(CategoryHasSubcategoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        CategoryHasSubcategoryEntity entity = new CategoryHasSubcategoryEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(CategoryHasSubcategoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        CategoryHasSubcategoryEntity entity = new CategoryHasSubcategoryEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public CategoryHasSubcategoryBean getById(int id) {
    	if(id < 1)
    		return null ;
    	CategoryHasSubcategoryBean bean = new CategoryHasSubcategoryBean();
    	CategoryHasSubcategoryEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<CategoryHasSubcategoryBean> getAll() {
    	List<CategoryHasSubcategoryEntity> entities = dao.getAll();
    	List<CategoryHasSubcategoryBean> beans = new ArrayList<CategoryHasSubcategoryBean>();
    	CategoryHasSubcategoryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CategoryHasSubcategoryEntity entity : entities){
	    		bean = new CategoryHasSubcategoryBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(CategoryHasSubcategoryBean bean , CategoryHasSubcategoryEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new CategoryHasSubcategoryBean();
		// Basics Data
		bean.setId(entity.getId());
		
		if(bean.getCategory() == null)
			bean.setCategory(new CategoryBean());
		bean.getCategory().setId(entity.getCategory().getId());
		bean.getCategory().setNameAr(entity.getCategory().getNameAr());
		bean.getCategory().setNameEn(entity.getCategory().getNameEn());
		
		if(bean.getSubcategory() == null)
			bean.setSubcategory(new SubcategoryBean());
		bean.getSubcategory().setId(entity.getSubcategory().getId());
		bean.getSubcategory().setNameAr(entity.getSubcategory().getNameAr());
		bean.getSubcategory().setNameEn(entity.getSubcategory().getNameEn());
	}
	// Fill Entity From Bean
	public void fillEntity(CategoryHasSubcategoryEntity entity,CategoryHasSubcategoryBean bean){
		if(bean == null || bean.getCategory() == null || bean.getSubcategory() == null 
				|| bean.getCategory().getId() < 1 || bean.getSubcategory().getId() < 1)
			return;
		if(entity == null)
			entity = new CategoryHasSubcategoryEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getCategory() == null)
			entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCategory().getId());
		
		if(entity.getSubcategory() == null)
			entity.setSubcategory(new SubcategoryEntity());
		entity.getSubcategory().setId(bean.getSubcategory().getId());
	}
	
	   public boolean deleteByCategory(CategoryHasSubcategoryBean bean) {
	        if(bean == null )
	        	return false;
	        CategoryHasSubcategoryEntity entity = new CategoryHasSubcategoryEntity();
			fillEntity(entity, bean);
			return dao.deleteByCategory(entity);
			
	    }
}
