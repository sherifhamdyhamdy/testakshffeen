package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.GroupsHasRoleBean;
import com.sget.akshef.hibernate.beans.PermisionBean;
import com.sget.akshef.hibernate.beans.RoleBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.dao.GroupsHasRoleDAO;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.GroupsHasRoleEntity;
import com.sget.akshef.hibernate.entities.RoleHasPermissionEntity;

/**
 * 
 * @author JDeeb
 * Groups Has Role Service
 */
public class GroupsHasRoleService {
	GroupsHasRoleDAO dao = null ;
	
	public GroupsHasRoleService(){
		dao = new GroupsHasRoleDAO();
	}
	
	public void insert(GroupsHasRoleBean bean){
		if(bean == null)
			return;
		GroupsHasRoleEntity entity = new GroupsHasRoleEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(GroupsHasRoleBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        GroupsHasRoleEntity entity = new GroupsHasRoleEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(GroupsHasRoleBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        GroupsHasRoleEntity entity = new GroupsHasRoleEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    public boolean deleteByGroup(GroupsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        GroupsEntity entity = new GroupsEntity();
		entity.setId(bean.getId());
		return dao.deleteByGroup(entity);
		
    }
    public List<GroupsHasRoleBean>  getByGroup(GroupsHasRoleBean bean) {
    	List<GroupsHasRoleBean> beans = new ArrayList<GroupsHasRoleBean>();
        if(bean == null || bean.getId() < 1)
        	return beans;
        GroupsEntity entity = new GroupsEntity();
        entity.setId(bean.getId());
    	List<GroupsHasRoleEntity> entities = dao.getByGroup(entity);
     	GroupsHasRoleBean bean1 = new  GroupsHasRoleBean();
    	for(GroupsHasRoleEntity entity1 : entities){
    		bean1 = new GroupsHasRoleBean();
    		fillBean(bean1, entity1);
    		beans.add(bean1);
    	}
    	return beans;
    		
    }
    // Get By ID
    public GroupsHasRoleBean getById(int id) {
    	if(id < 1)
    		return null ;
    	GroupsHasRoleBean bean = new GroupsHasRoleBean();
    	GroupsHasRoleEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<GroupsHasRoleBean> getAll() {
    	List<GroupsHasRoleEntity> entities = dao.getAll();
    	List<GroupsHasRoleBean> beans = new ArrayList<GroupsHasRoleBean>();
    	GroupsHasRoleBean bean ;
    	for(GroupsHasRoleEntity entity : entities){
    		bean = new GroupsHasRoleBean();
    		fillBean(bean, entity);
    		beans.add(bean);
    	}
    	return beans;
    }
    
    // JDeeb Get Permissions for branch group
    public List<RoleHasPermissionBean> getBranchGroupPermission(){
    	List<RoleHasPermissionEntity> entities = dao.getBranchGroupPermission();
    	List<RoleHasPermissionBean> beans = new ArrayList<RoleHasPermissionBean>();
    	RoleHasPermissionBean bean ;
    	if(entities != null){
	    	for(RoleHasPermissionEntity entity : entities){
	    		bean = new RoleHasPermissionBean();
	    		fillRolePermissionBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
	// Fill Bean From Entity
	public void fillBean(GroupsHasRoleBean bean , GroupsHasRoleEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new GroupsHasRoleBean();
		// Basics Data
		bean.setId(entity.getId());
		
		if( bean.getGroups() == null )
			bean.setGroups(new GroupsBean());
		bean.getGroups().setId(entity.getGroups().getId());
		
		if( bean.getRoleHasPermission() == null )
			bean.setRoleHasPermission(new RoleHasPermissionBean());
		
		if( bean.getRoleHasPermission().getRole() == null )
			bean.getRoleHasPermission().setRole(new  RoleBean());
		
		
		
 
	}
	// Fill Entity From Bean
	public void fillEntity(GroupsHasRoleEntity entity,GroupsHasRoleBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new GroupsHasRoleEntity();
		// Basics Data
		entity.setId(bean.getId());
		
		if( entity.getGroups() == null )
			entity.setGroups(new GroupsEntity());
		entity.getGroups().setId(bean.getGroups().getId());
		
		if( entity.getRoleHasPermission() == null )
			entity.setRoleHasPermission(new RoleHasPermissionEntity());
//		entity.getRoleHasPermission().setId(bean.getRoleHasPermission().getId());
	}
	
	// Fill fillRolePermissionBean
	public void fillRolePermissionBean(RoleHasPermissionBean bean , RoleHasPermissionEntity entity){
		if(entity != null){
			if(bean == null)
				bean = new RoleHasPermissionBean();
//			bean.setId(entity.getId());
//			if(entity.getPermission() != null){
//				bean.setPermision(new PermisionBean());
//				bean.getPermision().setId(entity.getPermission().getId());
//				bean.getPermision().setPermission_name(entity.getPermission().getPermission_name());
//			}
//			if(entity.getRole() != null){
//				bean.setRole(new RoleBean());
//				bean.getRole().setId(entity.getRole().getId());
//				bean.getRole().setName(entity.getRole().getName());
//			}
			//bean.setPageName(entity.getPagename());
		}
	}
}
