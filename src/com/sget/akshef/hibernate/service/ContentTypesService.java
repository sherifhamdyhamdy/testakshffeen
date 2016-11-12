package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.ContentTypesBean;
import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.dao.ContentTypesDAO;
import com.sget.akshef.hibernate.entities.ContentTypesEntity;
import com.sget.akshef.hibernate.entities.GroupsEntity;

/**
 * 
 * @author JDeeb
 * ContentTypes Service
 */
public class ContentTypesService {
	ContentTypesDAO dao = null ;
	
	public ContentTypesService(){
		dao = new ContentTypesDAO();
	}
	
	public void insert(ContentTypesBean bean){
		if(bean == null)
			return;
		ContentTypesEntity entity = new ContentTypesEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(ContentTypesBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        ContentTypesEntity entity = new ContentTypesEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(ContentTypesBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        ContentTypesEntity entity = new ContentTypesEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public ContentTypesBean getById(int id) {
    	if(id < 1)
    		return null ;
    	ContentTypesBean bean = new ContentTypesBean();
    	ContentTypesEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<ContentTypesBean> getAll() {
    	List<ContentTypesEntity> entities = dao.getAll();
    	List<ContentTypesBean> beans = new ArrayList<ContentTypesBean>();
    	ContentTypesBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(ContentTypesEntity entity : entities){
	    		bean = new ContentTypesBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    
 // Get All
    public List<ContentTypesBean> getAllByRequest(String url) {
    	List<ContentTypesEntity> entities = dao.getAll();
    	List<ContentTypesBean> beans = new ArrayList<ContentTypesBean>();
    	ContentTypesBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(ContentTypesEntity entity : entities){
	    		bean = new ContentTypesBean();
	    		fillBeanByRequest(bean, entity,url);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    public void fillBeanByRequest(ContentTypesBean bean , ContentTypesEntity entity,String url){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ContentTypesBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setNameAr(entity.getNameAr());
		bean.setNameEn(entity.getNameEn());

		
	}
    
	// Fill Bean From Entity
	public void fillBean(ContentTypesBean bean , ContentTypesEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ContentTypesBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setNameAr(entity.getNameAr());
		bean.setNameEn(entity.getNameEn());
		//if(entity.getSelected_img())
		
		/*if(entity.getGroups() != null && entity.getGroups().getId() > 0){
			if(bean.getGroups() == null)
				bean.setGroups(new GroupsBean());
			bean.getGroups().setId(entity.getGroups().getId());
			bean.getGroups().setName(entity.getGroups().getName());
		}*/
		
	}
	// Fill Entity From Bean
	public void fillEntity(ContentTypesEntity entity,ContentTypesBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new ContentTypesEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setActive(bean.getActive());
		entity.setNameAr(bean.getNameAr());
		entity.setNameEn(bean.getNameEn());
		/*if(entity.getGroups() == null)
			entity.setGroups(new GroupsEntity());
		entity.getGroups().setId(bean.getGroups().getId());*/
	}
}
