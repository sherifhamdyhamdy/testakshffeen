package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.UserRateBranchBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.UserRateBranchDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.UserRateBranchEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * User Rate Branch Service
 */
public class UserRateBranchService {
	UserRateBranchDAO dao = null ;
	
	public UserRateBranchService(){
		dao = new UserRateBranchDAO();
	}
	
	public void insert(UserRateBranchBean bean){
		if(bean == null)
			return;
		UserRateBranchEntity entity = new UserRateBranchEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(UserRateBranchBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        UserRateBranchEntity entity = new UserRateBranchEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(UserRateBranchBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        UserRateBranchEntity entity = new UserRateBranchEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public UserRateBranchBean getById(int id) {
    	if(id < 1)
    		return null ;
    	UserRateBranchBean bean = new UserRateBranchBean();
    	UserRateBranchEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<UserRateBranchBean> getAll() {
    	List<UserRateBranchEntity> entities = dao.getAll();
    	List<UserRateBranchBean> beans = new ArrayList<UserRateBranchBean>();
    	UserRateBranchBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(UserRateBranchEntity entity : entities){
	    		bean = new UserRateBranchBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(UserRateBranchBean bean , UserRateBranchEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new UserRateBranchBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setRate(entity.getRate());
		if(bean.getBranch() == null)
			bean.setBranch(new BranchBean());
		bean.getBranch().setId(entity.getBranch().getId());
		if(bean.getUsers() == null)
			bean.setUsers(new UsersBean());
		bean.getUsers().setId(entity.getUsers().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(UserRateBranchEntity entity,UserRateBranchBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new UserRateBranchEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setRate(bean.getRate());
		if(entity.getBranch() == null)
			entity.setBranch(new BranchEntity());
		entity.getBranch().setId(bean.getBranch().getId());
		if(entity.getUsers() == null)
			entity.setUsers(new UsersEntity());
		entity.getUsers().setId(bean.getUsers().getId());
	}
}
