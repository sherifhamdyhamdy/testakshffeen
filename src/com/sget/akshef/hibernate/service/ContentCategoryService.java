package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentTypesBean;
import com.sget.akshef.hibernate.dao.ContentCategoryDAO;
import com.sget.akshef.hibernate.entities.ContentCategoryEntity;
import com.sget.akshef.hibernate.entities.ContentTypesEntity;

/**
 * 
 * @author JDeeb
 * ContentCategory Service
 */
public class ContentCategoryService {
	ContentCategoryDAO dao = null ;
	
	public ContentCategoryService(){
		dao = new ContentCategoryDAO();
	}
	
	public void insert(ContentCategoryBean bean){
		if(bean == null)
			return;
		ContentCategoryEntity entity = new ContentCategoryEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(ContentCategoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        ContentCategoryEntity entity = new ContentCategoryEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(ContentCategoryBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        ContentCategoryEntity entity = new ContentCategoryEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public ContentCategoryBean getById(int id) {
    	if(id < 1)
    		return null ;
    	ContentCategoryBean bean = new ContentCategoryBean();
    	ContentCategoryEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<ContentCategoryBean> getAll() {
    	List<ContentCategoryEntity> entities = dao.getAll();
    	List<ContentCategoryBean> beans = new ArrayList<ContentCategoryBean>();
    	ContentCategoryBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(ContentCategoryEntity entity : entities){
	    		bean = new ContentCategoryBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(ContentCategoryBean bean , ContentCategoryEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ContentCategoryBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setNameAr(entity.getNameAr());
		bean.setNameEn(entity.getNameEn());
		bean.setCat_image(entity.getCat_image());
		if(entity.getContentTypes() != null){
			if(bean.getContentTypes() == null)
				bean.setContentTypes(new ContentTypesBean());
			bean.getContentTypes().setId(entity.getContentTypes().getId());
			bean.getContentTypes().setNameAr(entity.getContentTypes().getNameAr());
		}
	}
	// Fill Entity From Bean
	public void fillEntity(ContentCategoryEntity entity,ContentCategoryBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new ContentCategoryEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setActive(bean.getActive());
		entity.setNameAr(bean.getNameAr());
		entity.setNameEn(bean.getNameEn());
		entity.setCat_image(bean.getCat_image());
		if(entity.getContentTypes() == null)
			entity.setContentTypes(new ContentTypesEntity());
		entity.getContentTypes().setId(bean.getContentTypes().getId());
	}
}
