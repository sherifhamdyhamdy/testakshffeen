package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.CommentsDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * Comments Service
 */
public class CommentsService {
	CommentsDAO dao = null ;
	
	public CommentsService(){
		dao = new CommentsDAO();
	}
	
	public void insert(CommentsBean bean){
		if(bean == null)
			return;
		CommentsEntity entity = new CommentsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(CommentsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        CommentsEntity entity = new CommentsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(CommentsBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        CommentsEntity entity = new CommentsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public CommentsBean getById(int id) {
    	if(id < 1)
    		return null ;
    	CommentsBean bean = new CommentsBean();
    	CommentsEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<CommentsBean> getAll() {
    	List<CommentsEntity> entities = dao.getAll();
    	List<CommentsBean> beans = new ArrayList<CommentsBean>();
    	CommentsBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(CommentsEntity entity : entities){
	    		bean = new CommentsBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(CommentsBean bean , CommentsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new CommentsBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setComment(entity.getComment());
		bean.setComment_date(entity.getComment_date());
		bean.setCommentDate(new java.util.Date(entity.getComment_date()));
		if(entity.getBranch() != null){	
			if(bean.getBranch() == null)
				bean.setBranch(new BranchBean());
			bean.getBranch().setId(entity.getBranch().getId());
		}
		if(entity.getContentDetails() != null){
			if(bean.getContentDetails() == null)
				bean.setContentDetails(new ContentDetailsBean());
			bean.getContentDetails().setId(entity.getContentDetails().getId());
		}
		if(entity.getSpecialist() != null){
			if(bean.getSpecialist() == null)
				bean.setSpecialist(new SpecialistBean());
			bean.getSpecialist().setId(entity.getSpecialist().getId());
		}
		if(bean.getUsers() == null)
			bean.setUsers(new UsersBean());
		bean.getUsers().setId(entity.getUsers().getId());
		bean.setUsers_name(entity.getUsers().getUsername());
	}
	// Fill Entity From Bean
	public void fillEntity(CommentsEntity entity,CommentsBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new CommentsEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setComment(bean.getComment());
		entity.setComment_date(bean.getComment_date());
		if(entity.getBranch() == null)
			entity.setBranch(new BranchEntity());
		entity.getBranch().setId(bean.getBranch().getId());
		if(entity.getContentDetails() == null)
			entity.setContentDetails(new ContentDetailsEntity());
		entity.getContentDetails().setId(bean.getContentDetails().getId());
		if(entity.getSpecialist() == null)
			entity.setSpecialist(new SpecialistEntity());
		entity.getSpecialist().setId(bean.getSpecialist().getId());
		if(entity.getUsers() == null)
			entity.setUsers(new UsersEntity());
		entity.getUsers().setId(bean.getUsers().getId());
	}
	// JDeeb mobile
	// For ( addBranchComment ) Web Service 
	public CommentsBean addBranchComment(int branchid , int userid , String text){
		CommentsBean bean = new CommentsBean() ;
		CommentsEntity entity = new CommentsEntity();
		entity.setBranch(new BranchEntity());
		entity.getBranch().setId(branchid);
		entity.setUsers(new UsersEntity());
		entity.getUsers().setId(userid);
		entity.setComment(text);
		entity.setComment_date(Calendar.getInstance().getTimeInMillis());
		dao.insert(entity);
		entity = dao.getById(entity.getId());
		fillCommentForMobile(bean, entity);
		return bean;
	}
	
	// For ( addSpecialistComment ) Web Service 
	public CommentsBean addSpecialistComment(int specid , int userid , String text){
		CommentsBean bean = new CommentsBean() ;
		CommentsEntity entity = new CommentsEntity();
		entity.setSpecialist(new SpecialistEntity());
		entity.getSpecialist().setId(specid);
		entity.setUsers(new UsersEntity());
		entity.getUsers().setId(userid);
		entity.setComment(text);
		entity.setComment_date(Calendar.getInstance().getTimeInMillis());
		dao.insert(entity);
		entity = dao.getById(entity.getId());
		fillCommentForMobile(bean, entity);
		return bean;
	}
	// For ( addContentComment ) Web Service 
	public CommentsBean addContentComment(int contid , int userid , String text){
		CommentsBean bean = new CommentsBean() ;
		CommentsEntity entity = new CommentsEntity();
		entity.setContentDetails(new ContentDetailsEntity());
		entity.getContentDetails().setId(contid);
		entity.setUsers(new UsersEntity());
		entity.getUsers().setId(userid);
		entity.setComment(text);
		entity.setComment_date(Calendar.getInstance().getTimeInMillis());
		dao.insert(entity);
		entity = dao.getById(entity.getId());
		fillCommentForMobile(bean, entity);
		return bean;
	}
	private void fillCommentForMobile(CommentsBean bean , CommentsEntity entity){
		if(entity != null){
			if(bean == null)
				bean = new CommentsBean();
			
		fillBean(bean, entity);
		}
	}
	
	// get Branch Comments
	public List<CommentsBean> getAllBranchComments(int branchId){
		List<CommentsEntity> entities = dao.getBranchComments(branchId);
		List<CommentsBean> beans = new ArrayList<CommentsBean>();
		if(entities != null && entities.size() > 0){
			CommentsBean bean = null ;
			for(CommentsEntity entity : entities){
	    		bean = new CommentsBean();
	    		fillCommentForMobile(bean, entity);
	    		beans.add(bean);
	    	}
		}
		return beans;
	}
	
	
	
	
	// get Content Comments
		public List<CommentsBean> getAllContentComments(int contentId){
			List<CommentsEntity> entities = dao.getAllCommentsByContent(contentId);
			List<CommentsBean> beans = new ArrayList<CommentsBean>();
			if(entities != null && entities.size() > 0){
				CommentsBean bean = null ;
				for(CommentsEntity entity : entities){
		    		bean = new CommentsBean();
		    		fillCommentForMobile(bean, entity);
		    		beans.add(bean);
		    	}
			}
			return beans;
		}
		
		
		
		// get Content Comments
				public List<CommentsBean> getSpecialistComments(int specialist){
					List<CommentsEntity> entities = dao.getSpecialistComments(specialist);
					List<CommentsBean> beans = new ArrayList<CommentsBean>();
					if(entities != null && entities.size() > 0){
						CommentsBean bean = null ;
						for(CommentsEntity entity : entities){
				    		bean = new CommentsBean();
				    		fillCommentForMobile(bean, entity);
				    		beans.add(bean);
				    	}
					}
					return beans;
				}
		
		
	
}
