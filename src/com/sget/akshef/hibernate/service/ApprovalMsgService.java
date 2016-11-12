package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.ActionsBean;
import com.sget.akshef.hibernate.beans.ApprovalMsgBean;
import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.ApprovalMsgDAO;
import com.sget.akshef.hibernate.entities.ActionsEntity;
import com.sget.akshef.hibernate.entities.ApprovalMsgEntity;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * ApprovalMsg Service
 */
public class ApprovalMsgService {
	ApprovalMsgDAO dao = null ;
	
	public ApprovalMsgService(){
		dao = new ApprovalMsgDAO();
	}
	
	public void insert(ApprovalMsgBean bean){
		if(bean == null)
			return;
		ApprovalMsgEntity entity = new ApprovalMsgEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(ApprovalMsgBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        ApprovalMsgEntity entity = new ApprovalMsgEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(ApprovalMsgBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        ApprovalMsgEntity entity = new ApprovalMsgEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public ApprovalMsgBean getById(int id) {
    	if(id < 1)
    		return null ;
    	ApprovalMsgBean bean = new ApprovalMsgBean();
    	ApprovalMsgEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<ApprovalMsgBean> getAll() {
    	List<ApprovalMsgEntity> entities = dao.getAll();
    	List<ApprovalMsgBean> beans = new ArrayList<ApprovalMsgBean>();
    	ApprovalMsgBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(ApprovalMsgEntity entity : entities){
	    		bean = new ApprovalMsgBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(ApprovalMsgBean bean , ApprovalMsgEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ApprovalMsgBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setMessgae(entity.getMessgae());
		bean.setNotes(entity.getNotes());
		bean.setTitle(entity.getTitle());
		if(bean.getActions() == null)
			bean.setActions(new ActionsBean());
		bean.getActions().setId(entity.getActions().getId());
		if(bean.getGroups() == null)
			bean.setGroups(new GroupsBean());
		bean.getGroups().setId(entity.getGroups().getId());
		if(bean.getUsers() == null)
			bean.setUsers(new UsersBean());
		bean.getUsers().setId(entity.getUsers().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(ApprovalMsgEntity entity,ApprovalMsgBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new ApprovalMsgEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setMessgae(bean.getMessgae());
		entity.setNotes(bean.getNotes());
		entity.setTitle(bean.getTitle());
		if(entity.getActions() == null)
			entity.setActions(new ActionsEntity());
		entity.getActions().setId(bean.getActions().getId());
		if(entity.getGroups() == null)
			entity.setGroups(new GroupsEntity());
		entity.getGroups().setId(bean.getGroups().getId());
		if(entity.getUsers() == null)
			entity.setUsers(new UsersEntity());
		entity.getUsers().setId(bean.getUsers().getId());
	}
}
