package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Comments DAO
 */
public class CommentsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public CommentsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(CommentsEntity entity){
		try{
		Serializable ser = hiber.getSession().save(entity);
		entity.setId(ser.hashCode());
		hiber.getSession().getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			hiber.closeSession();
		}
	}
	public boolean update(CommentsEntity entity) {
        if(entity == null)
        	return false;
		try {
            hiber.getSession().update(entity);
            hiber.getSession().getTransaction().commit();
            return true;
        } catch (Exception ex) {
             System.out.println("Inesrt Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return false;
    }

    public boolean delete(CommentsEntity entity) {
        if(entity == null || entity.getId() < 1)
        	return false;
    	try {
            hiber.getSession().delete(entity);
            hiber.getSession().getTransaction().commit();
            return true ;
        } catch (Exception ex) {
             System.out.println("Delete Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return false;
    }
    
    public List<CommentsEntity> getAll() {
        List<CommentsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT comments.* FROM comments comments ")
                    .addEntity("comments.", CommentsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public CommentsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	CommentsEntity entity = null;
        try {
            entity = (CommentsEntity) hiber.getSession().get(CommentsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    
    public List<CommentsEntity> getAllCommentsByContent(int contentId ) {
    	List<CommentsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT comments.* FROM comments comments   where " +
            		" " +
            		"  content_details_id=:content_details_id ")
                    .addEntity("comments.", CommentsEntity.class)
                 .setInteger("content_details_id", contentId);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public List<CommentsEntity> getBranchComments(int branchId){
    	List<CommentsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("select * from comments where branch_id = :branchId")
                    .addEntity("comments.", CommentsEntity.class).setInteger("branchId", branchId);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public List<CommentsEntity> getSpecialistComments(int specialist_id){
    	List<CommentsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("select * from comments where specialist_id = :specialist_id")
                    .addEntity("comments.", CommentsEntity.class).setInteger("specialist_id", specialist_id);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
}
