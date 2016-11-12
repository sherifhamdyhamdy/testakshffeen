package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.BranchGroupsBean;
import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.beans.UsersGroupsBean;
import com.sget.akshef.hibernate.dao.BranchGroupsDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.BranchGroupsEntity;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.entities.UsersGroupsEntity;

/**
 * 
 * @author JDeeb
 * Branch Groups Service
 */
public class BranchGroupsService {
	BranchGroupsDAO dao = null ;
	
	public BranchGroupsService(){
		dao = new BranchGroupsDAO();
	}
	
	public void insert(BranchGroupsBean bean){
		if(bean == null)
			return;
		BranchGroupsEntity entity = new BranchGroupsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(BranchGroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        BranchGroupsEntity entity = new BranchGroupsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(BranchGroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        BranchGroupsEntity entity = new BranchGroupsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public BranchGroupsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	BranchGroupsBean bean = new BranchGroupsBean();
    	BranchGroupsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<BranchGroupsBean> getAll() {
    	List<BranchGroupsEntity> entities = dao.getAll();
    	List<BranchGroupsBean> beans = new ArrayList<BranchGroupsBean>();
    	BranchGroupsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(BranchGroupsEntity entity : entities){
	    		bean = new BranchGroupsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(BranchGroupsBean bean , BranchGroupsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new BranchGroupsBean();
		// Basics Data
		bean.setId(entity.getId());
		
		if(bean.getBranch() == null)
			bean.setBranch(new BranchBean());
		bean.getBranch().setId(entity.getBranch().getId());
		
		if(bean.getUsersGroups() == null)
			bean.setUsersGroups(new UsersGroupsBean());
		if(bean.getUsersGroups().getGroups() == null)
			bean.getUsersGroups().setGroups(new GroupsBean());
		
		if(bean.getUsersGroups().getUsers() == null)
			bean.getUsersGroups().setUsers(new UsersBean());
		bean.getUsersGroups().getUsers().setId(entity.getUsersGroups().getUsers().getId());
		
	}
	// Fill Entity From Bean
	public void fillEntity(BranchGroupsEntity entity,BranchGroupsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new BranchGroupsEntity();
		// Basics Data
		entity.setId(bean.getId());
		// Branch
		if(entity.getBranch() == null)
			entity.setBranch(new BranchEntity());
		entity.getBranch().setId(bean.getBranch().getId());
		// UsersGroups
		if(entity.getUsersGroups() == null)
			entity.setUsersGroups(new UsersGroupsEntity());
		entity.getUsersGroups().setId(bean.getUsersGroups().getId());

		if( entity.getUsersGroups().getUsers() == null )
			entity.getUsersGroups().setUsers(new UsersEntity());
		entity.getUsersGroups().getUsers().setId(bean.getUsersGroups().getUsers().getId());
	}
}
