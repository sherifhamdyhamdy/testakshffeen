package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.beans.UsersGroupsBean;
import com.sget.akshef.hibernate.dao.UsersGroupsDAO;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.entities.UsersGroupsEntity;

/**
 * 
 * @author JDeeb
 * Users Groups Service
 */
public class UsersGroupsService {
	UsersGroupsDAO dao = null ;
	
	public UsersGroupsService(){
		dao = new UsersGroupsDAO();
	}
	
	public void insert(UsersGroupsBean bean){
		if(bean == null)
			return;
		UsersGroupsEntity entity = new UsersGroupsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(UsersGroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        UsersGroupsEntity entity = new UsersGroupsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(UsersGroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        UsersGroupsEntity entity = new UsersGroupsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public UsersGroupsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	UsersGroupsBean bean = new UsersGroupsBean();
    	UsersGroupsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<UsersGroupsBean> getAll() {
    	List<UsersGroupsEntity> entities = dao.getAll();
    	List<UsersGroupsBean> beans = new ArrayList<UsersGroupsBean>();
    	UsersGroupsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(UsersGroupsEntity entity : entities){
	    		bean = new UsersGroupsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    /**
     * Delete Users From Group
     * @param usersId
     * @param groupId
     * @return boolean
     */
    public boolean deleteUsersFromGroup(int usersId , int groupId) {
    	return dao.deleteUsersFromGroup(usersId, groupId); 
    }
    public boolean insertUserToGroup(int usersId , int groupId) {
    	return dao.insertUserToGroup(usersId, groupId);
    }
	// Fill Bean From Entity
	public void fillBean(UsersGroupsBean bean , UsersGroupsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new UsersGroupsBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getGroups() == null)
			bean.setGroups(new GroupsBean());
//		bean.getGroups().setId(entity.getGroups().getId());
		if(bean.getUsers() == null)
			bean.setUsers(new UsersBean());
		bean.getUsers().setId(entity.getUsers().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(UsersGroupsEntity entity,UsersGroupsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new UsersGroupsEntity();
		// Basics Data
		entity.setId(bean.getId());
//		if(entity.getGroups() == null)
//			entity.setGroups(new GroupsEntity());
//		entity.getGroups().setId(bean.getGroups().getId());
		if(entity.getUsers() == null)
			entity.setUsers(new UsersEntity());
		entity.getUsers().setId(bean.getUsers().getId());
	}
}
