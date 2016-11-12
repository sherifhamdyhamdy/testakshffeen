package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.dao.CategoryDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.CategoryHasSubcategoryEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsEntity;

/**
 * 
 * @author JDeeb
 * Category Service
 */
public class CategoryService {
	CategoryDAO dao = null ;
	public CategoryService(){
		dao = new CategoryDAO();
	}
	
	public void insert(CategoryBean bean){
		if(bean == null)
			return;
		CategoryEntity entity = new CategoryEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(CategoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        CategoryEntity entity = new CategoryEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(CategoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        CategoryEntity entity = new CategoryEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public CategoryBean getById(int id) {
    	if(id < 1)
    		return null ;
    	CategoryBean bean = new CategoryBean();
    	CategoryEntity entity = dao.getCategory(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<CategoryBean> getAll() {
    	List<CategoryEntity> entities = dao.getAll();
    	List<CategoryBean> beans = new ArrayList<CategoryBean>();
    	CategoryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CategoryEntity entity : entities){
	    		bean = new CategoryBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    public List<CategoryBean> getCategoryName(String name_en,String name_ar)
    {
    	List<CategoryEntity> entities = dao.getCategoryName( name_en, name_ar);
    	List<CategoryBean> beans = new ArrayList<CategoryBean>();
    	CategoryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CategoryEntity entity : entities){
	    		bean = new CategoryBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    
    
    // Get All
    public List<CategoryBean> getAllForMobile(int insuranceCompany) {
    	List<CategoryEntity> entities = null ;
    	if(insuranceCompany > 0)
    		entities = dao.getCategoryInsuranceCompany(insuranceCompany);
    	else
    		entities = dao.getAll();
    	List<CategoryBean> beans = new ArrayList<CategoryBean>();
    	CategoryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CategoryEntity entity : entities){
	    		bean = new CategoryBean();
	    		fillBean(bean, entity);
	    		
	    		if(bean.getSubCategories() != null && bean.getSubCategories().size() > 0)
	    			beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    
    public List<CategoryBean>  getAllByUserId(int user_id)
    {
       List<CategoryBean> beans=new ArrayList<CategoryBean>();
       CategoryBean bean;
    if(dao==null)     
    {
       dao=new  CategoryDAO();
      List<CategoryEntity> categories= dao.getAllByUserId(user_id);
      for(CategoryEntity entity:categories)
      {
    	  bean = new CategoryBean();
  		fillBean(bean, entity);
  		beans.add(bean);
      }
      
    }
    return beans;
    }
    
    public static void main (String a [])
    {
    	// Test Function
    }
	// Fill Bean From Entity
	public void fillBean(CategoryBean bean , CategoryEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new CategoryBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		
		if(entity.getCategoryHasSubcategorySet() != null && entity.getCategoryHasSubcategorySet().size() > 0 && bean.getSubCategories() == null)
			bean.setSubCategories(new ArrayList<SubcategoryBean>());
		Iterator<CategoryHasSubcategoryEntity> subcats = entity.getCategoryHasSubcategorySet().iterator();
		
		while(subcats.hasNext()){
			CategoryHasSubcategoryEntity relas = subcats.next();
			SubcategoryBean subBean = fillSubCategory(relas.getSubcategory(), null);
			if(subBean != null && subBean.getSections() != null && subBean.getSections().size() > 0)
				bean.getSubCategories().add(subBean);
		}
	}
	// Fill Entity From Bean
	public void fillEntity(CategoryEntity entity,CategoryBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new CategoryEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
		
	}
	private SubcategoryBean fillSubCategory(SubcategoryEntity entity , SubcategoryBean bean){
		if(bean == null)
			bean = new SubcategoryBean();
		bean.setActive(entity.getActive()!=null?entity.getActive():0);
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		if(entity.getSubcategoryHasSectionsSet() != null && entity.getSubcategoryHasSectionsSet().size() > 0 && bean.getSections() == null)
			bean.setSections(new ArrayList<SectionsBean>());
		
		Iterator<SubcategoryHasSectionsEntity> iterator = entity.getSubcategoryHasSectionsSet().iterator();

		while(iterator.hasNext()){
			SubcategoryHasSectionsEntity rela = iterator.next();
			bean.getSections().add(fillSections(rela.getSections(), null));
		}
		return bean;
	}
	private SectionsBean fillSections(SectionsEntity entity , SectionsBean bean){
		if(bean == null)
			bean = new SectionsBean();
		if(entity==null)
		{
			bean = new SectionsBean();
			return bean;
		}
			
		bean.setActive(entity.getActive()!=null?entity.getActive():0);
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		return bean ;
	}
}