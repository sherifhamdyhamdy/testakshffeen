package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.UserRateSpecEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * UserRateSpec DAO
 */
public class UserRateSpecDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public UserRateSpecDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(UserRateSpecEntity entity){
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
	public boolean update(UserRateSpecEntity entity) {
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

    public boolean delete(UserRateSpecEntity entity) {
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
    
    public List<UserRateSpecEntity> getAll() {
        List<UserRateSpecEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT user_rate_spec.* FROM user_rate_spec user_rate_spec ")
                    .addEntity("user_rate_spec.", UserRateSpecEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public UserRateSpecEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	UserRateSpecEntity entity = null;
        try {
        	entity = (UserRateSpecEntity) hiber.getSession().get(UserRateSpecEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    
    
    public List<Object> checkIfUserRateSpec(int specialist_id,int userId)
    {
    	 List<Object> entities = null;
         try {
             Query query = hiber.getSession().createSQLQuery("select * from user_rate_spec where " +
             		" specialist_id=:specialist_id and users_id=:userId " ).setInteger("specialist_id", specialist_id).setInteger("userId", userId);
             entities = query.list();
             
         } catch (Exception ex) {
              System.out.println("Get Ex : " + ex.getMessage());
         } finally{
             hiber.closeSession();
         }
         return entities;	
    	
    	
    	
    }
    
    
    
    public List<Object> getRatingToBesavedSpec(int specialist_id)
    {
    	 List<Object> entities = null;
         try {
             Query query = hiber.getSession().createSQLQuery("select SUM(rate) ,count(users_id) ,specialist_id  from user_rate_spec " +
             		" where specialist_id=:id" +
             		" GROUP BY specialist_id " +
             		"  ").setInteger("id", specialist_id);
             entities = query.list();
             
         } catch (Exception ex) {
              System.out.println("Get Ex : " + ex.getMessage());
         } finally{
             hiber.closeSession();
         }
         return entities;	
    	
    	
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
