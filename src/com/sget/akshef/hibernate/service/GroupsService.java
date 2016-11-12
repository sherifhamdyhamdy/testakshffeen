package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.beans.UsersGroupsBean;
import com.sget.akshef.hibernate.dao.GroupsDAO;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.UsersGroupsEntity;

/**
 * 
 * @author JDeeb
 * Groups Service
 */
public class GroupsService {
	GroupsDAO dao = null ;
	
	public GroupsService(){
		dao = new GroupsDAO();
	}
	
	public void insert(GroupsBean bean){
		if(bean == null)
			return;
		GroupsEntity entity = new GroupsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(GroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        GroupsEntity entity = new GroupsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(GroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        GroupsEntity entity = new GroupsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public GroupsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	GroupsBean bean = new GroupsBean();
    	GroupsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<GroupsBean> getAll() {
    	List<GroupsEntity> entities = dao.getAll();
    	List<GroupsBean> beans = new ArrayList<GroupsBean>();
    	GroupsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(GroupsEntity entity : entities){
	    		bean = new GroupsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    // JDeeb get All Groups For Branch
    public List<GroupsBean> getBranchGroups(int branchId) {
    	List<GroupsEntity> entities = dao.getBranchGroups(branchId);
    	List<GroupsBean> beans = new ArrayList<GroupsBean>();
    	GroupsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(GroupsEntity entity : entities){
	    		bean = new GroupsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
	// Fill Bean From Entity
 // Fill Bean From Entity
 	public void fillBean(GroupsBean bean , GroupsEntity entity){
 		if(entity == null)
 			return;
 		if(bean == null)
 			bean = new GroupsBean();
 		// Basics Data
 		bean.setId(entity.getId());
 		bean.setName(entity.getName());
 		
 		if(entity.getActive() != null)
 			bean.setActive(entity.getActive());
 		


 	}
	// Fill Entity From Bean
	public void fillEntity(GroupsEntity entity,GroupsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new GroupsEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setName(bean.getName());

	}
	public void fillUsersGroupsBean(UsersGroupsBean bean , UsersGroupsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new UsersGroupsBean();
		// Basics Data
		bean.setId(entity.getId());

		if(entity.getUsers() != null){
			if(bean.getUsers() == null)
				bean.setUsers(new UsersBean());
			bean.getUsers().setId(entity.getUsers().getId());
			bean.getUsers().setNameAr(entity.getUsers().getNameAr());
			bean.getUsers().setNameEn(entity.getUsers().getNameEn());
		}
	}
}
