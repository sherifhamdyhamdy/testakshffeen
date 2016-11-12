package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



import com.sget.akshef.hibernate.beans.PermisionBean;
import com.sget.akshef.hibernate.beans.RoleBean;
import com.sget.akshef.hibernate.dao.RoleDAO;
import com.sget.akshef.hibernate.entities.RoleEntity;
import com.sget.akshef.hibernate.entities.RoleHasPermissionEntity;

/**
 * 
 * @author JDeeb
 * Role Service
 */
public class RoleService {
	RoleDAO dao = null ;
	
	public RoleService(){
		dao = new RoleDAO();
	}
	
	public void insert(RoleBean bean){
		if(bean == null)
			return;
		RoleEntity entity = new RoleEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(RoleBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        RoleEntity entity = new RoleEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(RoleBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        RoleEntity entity = new RoleEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public RoleBean getById(int id) {
    	if(id < 1)
    		return null ;
    	RoleBean bean = new RoleBean();
    	RoleEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<RoleBean> getAll() {
    	List<RoleEntity> entities = dao.getAll();
    	List<RoleBean> beans = new ArrayList<RoleBean>();
    	RoleBean bean ;
    	for(RoleEntity entity : entities){
    		bean = new RoleBean();
    		fillBean(bean, entity);
    		beans.add(bean);
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(RoleBean bean , RoleEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new RoleBean();
		// Basics Data
		if(entity.getActive() != null)
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setName(entity.getName());
		 
		if(entity.getRoleHasPermissionEntityEntities() != null){
		Set<RoleHasPermissionEntity> roleHasPermissionEntities =	entity.getRoleHasPermissionEntityEntities();
		if(roleHasPermissionEntities != null){
			Iterator<RoleHasPermissionEntity>  iter=  roleHasPermissionEntities.iterator();
			while (iter.hasNext()) {
				RoleHasPermissionEntity roleHasPermissionEntity = (RoleHasPermissionEntity) iter
						.next();
				PermisionBean permisionBean   =  new  PermisionBean();
//				permisionBean.setId(roleHasPermissionEntity.getId());
//				permisionBean.setPermission_name(roleHasPermissionEntity.getPermission().getPermission_name());
				bean.getRolesHasPermissions().add(permisionBean);
					
			}
		}
		}
	}
	// Fill Entity From Bean
	public void fillEntity(RoleEntity entity,RoleBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new RoleEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setName(bean.getName());
	}
}
