package com.sget.akshef.hibernate.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.TempBranchBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.dao.TempBranchDAO;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.TempBranchEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb

 */
public class TempBranchService {
	TempBranchDAO dao = null ;
	
	public TempBranchService(){
		dao = new TempBranchDAO();
	}
	
	
	
	public boolean insert(TempBranchBean bean, UsersEntity user_recommend)
	{
		if (bean == null)
			return false;
		
		TempBranchEntity entity = new TempBranchEntity();
		fillEntity(entity, bean);
		boolean rs = dao.insert(entity,user_recommend);
		bean.setId(entity.getId());
		return rs;
	}
	
	public boolean update(TempBranchBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        TempBranchEntity entity = new TempBranchEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(TempBranchBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        TempBranchEntity entity = new TempBranchEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public TempBranchBean getById(int id) {
    	if(id < 1)
    		return null ;
    	TempBranchBean bean = new TempBranchBean();
    	TempBranchEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<TempBranchBean> getAll() {
    	List<TempBranchEntity> entities = dao.getAll();
    	List<TempBranchBean> beans = new ArrayList<TempBranchBean>();
    	TempBranchBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(TempBranchEntity entity : entities){
	    		bean = new TempBranchBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(TempBranchBean bean , TempBranchEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new TempBranchBean();
		// Basics Data
		bean.setAddress(entity.getAddress());
		bean.setId(entity.getId());
		bean.setLat(entity.getLat());
		bean.setLng(entity.getLng());
		bean.setName(entity.getName());
		bean.setTel_num(entity.getTel_num());
		bean.setUser_tel(entity.getUser_tel());
		bean.setEmail(entity.getEmail());
		bean.setUser_type(entity.getUser_type());
		bean.setUser_tel(entity.getUser_tel());
		
		
		if(entity.getCategory()!= null && entity.getCategory().getId() > 0){
			if(bean.getCatBean() == null)
				bean.setCatBean(new CategoryBean());
			bean.getCatBean().setId(entity.getCategory().getId());
			bean.getCatBean().setNameAr(entity.getCategory().getNameAr());
			bean.getCatBean().setNameEn(entity.getCategory().getNameEn());
		}
		
	}
		
		
		
		
		
		
	
	// Fill Entity From Bean
	public void fillEntity(TempBranchEntity entity,TempBranchBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new TempBranchEntity();
		// Basics Data
		entity.setAddress(bean.getAddress());
		entity.setId(bean.getId());
		entity.setLat(bean.getLat());
		entity.setLng(bean.getLng());
		entity.setName(bean.getName());
		entity.setTel_num(bean.getTel_num());
		// fill category
		if(entity.getCategory() == null)
		{
		entity.setCategory(new CategoryEntity());
		entity.getCategory().setId(bean.getCatBean().getId());
		entity.getCategory().setNameAr(bean.getCatBean().getNameAr());
		entity.getCategory().setNameEn(bean.getCatBean().getNameEn());
		
		entity.setEmail(bean.getEmail());
		entity.setUser_type(bean.getUser_type());
		entity.setUser_tel(bean.getUser_tel());
//		entity.setUser_recommend(bean.getUser_recommend());

	}
	}
}
