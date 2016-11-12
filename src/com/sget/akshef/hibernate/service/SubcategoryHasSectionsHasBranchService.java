package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.beans.SubcategoryHasSectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryHasSectionsHasBranchBean;
import com.sget.akshef.hibernate.dao.SubcategoryHasSectionsHasBranchDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsHasBranchEntity;

/**
 * 
 * @author JDeeb
 * SubcategoryHasSections Service
 */
public class SubcategoryHasSectionsHasBranchService {
	SubcategoryHasSectionsHasBranchDAO dao = null ;
	
	public SubcategoryHasSectionsHasBranchService(){
		dao = new SubcategoryHasSectionsHasBranchDAO();
	}
	
	public void insert(SubcategoryHasSectionsHasBranchBean bean){
		if(bean == null)
			return;
		SubcategoryHasSectionsHasBranchEntity entity = new SubcategoryHasSectionsHasBranchEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(SubcategoryHasSectionsHasBranchBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        SubcategoryHasSectionsHasBranchEntity entity = new SubcategoryHasSectionsHasBranchEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(SubcategoryHasSectionsHasBranchBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        SubcategoryHasSectionsHasBranchEntity entity = new SubcategoryHasSectionsHasBranchEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public SubcategoryHasSectionsHasBranchBean getById(int id) {
    	if(id < 1)
    		return null ;
    	SubcategoryHasSectionsHasBranchBean bean = new SubcategoryHasSectionsHasBranchBean();
    	SubcategoryHasSectionsHasBranchEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<SubcategoryHasSectionsHasBranchBean> getBYBranch(int branchId) {
    	if(branchId != 0){
	    	List<SubcategoryHasSectionsHasBranchEntity> entities = dao.getBYBranch(branchId);
	    	List<SubcategoryHasSectionsHasBranchBean> beans = new ArrayList<SubcategoryHasSectionsHasBranchBean>();
	    	SubcategoryHasSectionsHasBranchBean bean ;
	    	if(entities != null && entities.size() > 0){
		    	for(SubcategoryHasSectionsHasBranchEntity entity : entities){
		    		bean = new SubcategoryHasSectionsHasBranchBean();
		    		fillBean(bean, entity);
		    		beans.add(bean);
		    	}
	    	}
	    	return  beans;
    	}
    	return new ArrayList<SubcategoryHasSectionsHasBranchBean>();
    }
    
 // Get All
    public List<SubcategoryHasSectionsHasBranchBean> getAll() {
    	List<SubcategoryHasSectionsHasBranchEntity> entities = dao.getAll();
    	List<SubcategoryHasSectionsHasBranchBean> beans = new ArrayList<SubcategoryHasSectionsHasBranchBean>();
    	SubcategoryHasSectionsHasBranchBean bean ;
    	for(SubcategoryHasSectionsHasBranchEntity entity : entities){
    		bean = new SubcategoryHasSectionsHasBranchBean();
    		fillBean(bean, entity);
    		beans.add(bean);
    	}
    	return beans;
    }
    
	// Fill Bean From Entity
	public void fillBean(SubcategoryHasSectionsHasBranchBean bean , SubcategoryHasSectionsHasBranchEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new SubcategoryHasSectionsHasBranchBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setPrice(entity.getPrice());
		if( bean.getBranch() == null )
			bean.setBranch(new BranchBean());
		bean.getBranch().setId(entity.getBranch().getId());
		
		if( bean.getSubcategoryHasSections() == null)
			bean.setSubcategoryHasSections(new SubcategoryHasSectionsBean());
		if(entity.getSubcategoryHasSections() != null)
			bean.getSubcategoryHasSections().setId(entity.getSubcategoryHasSections().getId());
	
		if(bean.getSubcategoryHasSections().getSections() == null)
			bean.getSubcategoryHasSections().setSections(new SectionsBean());
		if(entity.getSubcategoryHasSections() != null  &&  entity.getSubcategoryHasSections().getSections() != null)
			bean.getSubcategoryHasSections().getSections().setId(entity.getSubcategoryHasSections().getSections().getId());
		
		if(bean.getSubcategoryHasSections().getSubcategory() == null)
			bean.getSubcategoryHasSections().setSubcategory(new SubcategoryBean());
		bean.getSubcategoryHasSections().getSubcategory().setId(entity.getSubcategoryHasSections().getSubcategory().getId());
		
	}
	// Fill Entity From Bean
	public void fillEntity(SubcategoryHasSectionsHasBranchEntity entity,SubcategoryHasSectionsHasBranchBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new SubcategoryHasSectionsHasBranchEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setPrice(bean.getPrice());
		if( entity.getBranch() == null )
			entity.setBranch(new BranchEntity());
		entity.getBranch().setId(bean.getBranch().getId());
		
		
		if( entity.getBranch() == null)
			entity.setBranch(new BranchEntity());
		entity.getBranch().setId(bean.getBranch().getId() );
		
		
		
		if( entity.getSubcategoryHasSections() == null)
			entity.setSubcategoryHasSections(new SubcategoryHasSectionsEntity());
		
		entity.getSubcategoryHasSections().setId(bean.getSubcategoryHasSections().getId());
		
		
		if(entity.getSubcategoryHasSections().getSections() == null)
			entity.getSubcategoryHasSections().setSections(new SectionsEntity());
		
    if(bean.getSubcategoryHasSections() != null  &&  bean.getSubcategoryHasSections().getSections() != null)
     	entity.getSubcategoryHasSections().getSections().setId(bean.getSubcategoryHasSections().getSections().getId());
   if(entity.getSubcategoryHasSections().getSubcategory() == null)
		entity.getSubcategoryHasSections().setSubcategory(new SubcategoryEntity());
		
   if(bean.getSubcategoryHasSections() != null  &&  bean.getSubcategoryHasSections().getSubcategory() != null)
		entity.getSubcategoryHasSections().getSubcategory().setId(bean.getSubcategoryHasSections().getSubcategory().getId());
		
	}
}
