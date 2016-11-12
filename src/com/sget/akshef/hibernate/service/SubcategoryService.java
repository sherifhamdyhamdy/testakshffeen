package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;


import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.dao.SubcategoryDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;

/**
 * 
 * @author JDeeb
 * SubCategory Service
 */
public class SubcategoryService {
	SubcategoryDAO dao = null ;
	
	public SubcategoryService(){
		dao = new SubcategoryDAO();
	}
	
	public void insert(SubcategoryBean bean){
		if(bean == null)
			return;
		SubcategoryEntity entity = new SubcategoryEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(SubcategoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        SubcategoryEntity entity = new SubcategoryEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(SubcategoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        SubcategoryEntity entity = new SubcategoryEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public SubcategoryBean getById(int id) {
    	if(id < 1)
    		return null ;
    	SubcategoryBean bean = new SubcategoryBean();
    	SubcategoryEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<SubcategoryBean> getAll() {
    	List<SubcategoryEntity> entities = dao.getAll();
    	List<SubcategoryBean> beans = new ArrayList<SubcategoryBean>();
    	SubcategoryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(SubcategoryEntity entity : entities){
	    		bean = new SubcategoryBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    // getAllByCategory
    public List<SubcategoryBean> getAllByCategory( CategoryBean categoryBean) {
    	if(categoryBean  ==  null  )
    		return null;
    	 CategoryEntity  categoryEntity  = new  CategoryEntity();
    	 categoryEntity.setId(categoryBean.getId());
    	List<SubcategoryEntity> entities = dao.getAllByCategory(categoryEntity);
    	 
    	List<SubcategoryBean> beans = new ArrayList<SubcategoryBean>();
    	SubcategoryBean bean ;
    	for(SubcategoryEntity entity : entities){
    		bean = new SubcategoryBean();
    		fillBean(bean, entity);
    		beans.add(bean);
    	}
    	return beans;
    }
    
    
    // getAllByCategory
    public List<SubcategoryBean> getAllByBranch( BranchBean BranchBean) {
    	if(BranchBean  ==  null  )
    		return null;
    	 BranchEntity  branchEntity  = new  BranchEntity();
    	 branchEntity.setId(BranchBean.getId());
    	List<SubcategoryEntity> entities = dao.getAllByBranch(branchEntity);
    	List<SubcategoryBean> beans = new ArrayList<SubcategoryBean>();
    	SubcategoryBean bean ;
    	for(SubcategoryEntity entity : entities){
    		bean = new SubcategoryBean();
    		fillBean(bean, entity);
    		beans.add(bean);
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(SubcategoryBean bean , SubcategoryEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new SubcategoryBean();
		// Basics Data
		if(entity.getActive() != null)
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		
	}
	// Fill Entity From Bean
	public void fillEntity(SubcategoryEntity entity,SubcategoryBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new SubcategoryEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setNameEn(bean.getNameEn());
		entity.setNameAr(bean.getNameAr());
	}
	
	
	public List<SubcategoryBean> getSubcategoryName(String name_en,String name_ar) {
		List<SubcategoryEntity> entities = dao.getSubcategoryName(name_en,name_ar);
    	List<SubcategoryBean> beans = new ArrayList<SubcategoryBean>();
    	SubcategoryBean bean ;
    	for(SubcategoryEntity entity : entities){
    		bean = new SubcategoryBean();
    		fillBean(bean, entity);
    		beans.add(bean);
    	}
    	return beans;
	}
	public List<SubcategoryBean>  getAllSubCategories(int categoryId){
		
		List<Object> entities = dao.getSubCategoryByCatId(categoryId);
		SubcategoryBean bean;
		List<SubcategoryBean>  beans=new ArrayList<SubcategoryBean>();
		for(Object ent:entities){
      	  	Object[] arr=(Object[])ent;  
            bean=new SubcategoryBean();
            bean.setId((Integer)arr[0]);
            bean.setNameAr(arr[1].toString());
            bean.setNameEn(arr[2].toString());
           // bean.setCategoryId(categoryId);;
            beans.add(bean);
        }
      return beans;
	}
	
	// delete subCategory
	public boolean deleteSubcategory(int subcatId){
		return dao.deleteSubcategory(subcatId);
	}
}