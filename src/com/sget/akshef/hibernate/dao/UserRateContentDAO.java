package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.UserRateContentEntity;
import com.sget.akshef.hibernate.entities.UserRateContentEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * UserRateBranch DAO
 */
public class UserRateContentDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public UserRateContentDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(UserRateContentEntity entity){
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
	
	
	
	public boolean update(UserRateContentEntity entity) {
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
	

    public boolean delete(UserRateContentEntity entity) {
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
    
    public List<UserRateContentEntity> getAll() {
        List<UserRateContentEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT user_rate_content.* FROM user_rate_content user_rate_content ")
                    .addEntity("user_rate_content.", UserRateContentEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public UserRateContentEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	UserRateContentEntity entity = null;
        try {
        	entity = (UserRateContentEntity) hiber.getSession().get(UserRateContentEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    
    
    public List<Object> getRatingToBesaved(int content_id)
    {
    	 List<Object> entities = null;
         try {
             Query query = hiber.getSession().createSQLQuery("select SUM(rate) ,count(users_id) ,content_id  from user_rate_content " +
             		" where content_id=:id" +
             		" GROUP BY content_id " +
             		"  ").setInteger("id", content_id);
             entities = query.list();
             
         } catch (Exception ex) {
              System.out.println("Get Ex : " + ex.getMessage());
         } finally{
             hiber.closeSession();
         }
         return entities;	
    	
    	
    	
    }
    
    
    
    
    
    
 
    
    public List<UserRateContentEntity> checkIfUserRateContent(int content_id,int userId)
    {
    	 List<UserRateContentEntity> entities = null;
         try {
             Query query = hiber.getSession().createSQLQuery("select * from user_rate_content where content_id=:content_id and users_id=:userId " ).
            		 addEntity("user_rate_content.", UserRateContentEntity.class)
            		 .setInteger("content_id", content_id).setInteger("userId", userId);
;
             entities = query.list();
             
         } catch (Exception ex) {
              System.out.println("Get Ex : " + ex.getMessage());
         } finally{
             hiber.closeSession();
         }
         return entities;	
    	
    	
    	
    }
    
    
}
