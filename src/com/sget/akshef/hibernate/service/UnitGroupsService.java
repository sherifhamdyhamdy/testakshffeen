package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.beans.UnitGroupsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.beans.UsersGroupsBean;
import com.sget.akshef.hibernate.dao.UnitGroupsDAO;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UnitGroupsEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.entities.UsersGroupsEntity;

/**
 * 
 * @author JDeeb
 * Unit Groups Service
 */
public class UnitGroupsService {
	UnitGroupsDAO dao = null ;
	
	public UnitGroupsService(){
		dao = new UnitGroupsDAO();
	}
	
	public void insert(UnitGroupsBean bean){
		if(bean == null)
			return;
		UnitGroupsEntity entity = new UnitGroupsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(UnitGroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        UnitGroupsEntity entity = new UnitGroupsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(UnitGroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        UnitGroupsEntity entity = new UnitGroupsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public UnitGroupsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	UnitGroupsBean bean = new UnitGroupsBean();
    	UnitGroupsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<UnitGroupsBean> getAll() {
    	List<UnitGroupsEntity> entities = dao.getAll();
    	List<UnitGroupsBean> beans = new ArrayList<UnitGroupsBean>();
    	UnitGroupsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(UnitGroupsEntity entity : entities){
	    		bean = new UnitGroupsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(UnitGroupsBean bean , UnitGroupsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new UnitGroupsBean();
		// Basics Data
		bean.setId(entity.getId());
		if(bean.getUnit() == null)
			bean.setUnit(new UnitBean());
		bean.getUnit().setId(bean.getUnit().getId());
		if(bean.getUsersGroups() == null)
			bean.setUsersGroups(new UsersGroupsBean());
		if(bean.getUsersGroups().getGroups() == null)
			bean.getUsersGroups().setGroups(new GroupsBean());
		if(bean.getUsersGroups().getUsers() == null)
			bean.getUsersGroups().setUsers(new UsersBean());
		bean.getUsersGroups().getUsers().setId(entity.getUsersGroups().getUsers().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(UnitGroupsEntity entity,UnitGroupsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new UnitGroupsEntity();
		// Basics Data
		entity.setId(bean.getId());
		if(entity.getUnit() == null)
			entity.setUnit(new UnitEntity());
		entity.getUnit().setId(bean.getUnit().getId());
		if(entity.getUsersGroups() == null)
			entity.setUsersGroups(new UsersGroupsEntity());
		
		if(entity.getUsersGroups().getUsers() == null)
			entity.getUsersGroups().setUsers(new UsersEntity());
		entity.getUsersGroups().getUsers().setId(bean.getUsersGroups().getUsers().getId());
	}
}
