package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.UserRateBranchEntity;
import com.sget.akshef.hibernate.entities.UserRateContentEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * UserRateBranch DAO
 */
public class UserRateBranchDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public UserRateBranchDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(UserRateBranchEntity entity){
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
	
	
	public void insertContentRate(UserRateContentEntity  entity){
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
	public boolean update(UserRateBranchEntity entity) {
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
	
	public boolean updatecontent(UserRateContentEntity entity) {
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

    public boolean delete(UserRateBranchEntity entity) {
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
    
    public List<UserRateBranchEntity> getAll() {
        List<UserRateBranchEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT user_rate_branch.* FROM user_rate_branch user_rate_branch ")
                    .addEntity("user_rate_branch.", UserRateBranchEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public UserRateBranchEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	UserRateBranchEntity entity = null;
        try {
        	entity = (UserRateBranchEntity) hiber.getSession().get(UserRateBranchEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    
    public UserRateContentEntity getContentById(int id) {
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
    
    public List<Object> getRatingToBesaved(int branchid)
    {
    	 List<Object> entities = null;
         try {
             Query query = hiber.getSession().createSQLQuery("select SUM(rate) ,count(users_id) ,branch_id  from user_rate_branch " +
             		" where branch_id=:id" +
             		" GROUP BY branch_id " +
             		"  ").setInteger("id", branchid);
             entities = query.list();
             
         } catch (Exception ex) {
              System.out.println("Get Ex : " + ex.getMessage());
         } finally{
             hiber.closeSession();
         }
         return entities;	
    	
    	
    	
    }
    
    
    
    public List<Object> getRatingToBesavedContent(int contentId)
    {
    	 List<Object> entities = null;
         try {
             Query query = hiber.getSession().createSQLQuery("select SUM(rate) ,count(users_id) ,content_id  from user_rate_content " +
             		" where content_id=:id" +
             		" GROUP BY content_id " +
             		"  ").setInteger("id", contentId);
             entities = query.list();
             
         } catch (Exception ex) {
              System.out.println("Get Ex : " + ex.getMessage());
         } finally{
             hiber.closeSession();
         }
         return entities;	
    	
    	
    	
    }
    
    
    
    public List<UserRateBranchEntity> checkIfUserRate(int branchid,int userId)
    {
    	 List<UserRateBranchEntity> entities = null;
         try {
             Query query = hiber.getSession().createSQLQuery("select * from user_rate_branch where branch_id=:branchId and users_id=:userId " ).addEntity("user_rate_branch",UserRateBranchEntity.class).setInteger("branchId", branchid).setInteger("userId", userId);
             entities = query.list();
             
         } catch (Exception ex) {
              System.out.println("Get Ex : " + ex.getMessage());
         } finally{
             hiber.closeSession();
         }
         return entities;	
    	
    	
    	
    }
    
    public List<Object> checkIfUserRateContent(int content_id,int userId)
    {
    	 List<Object> entities = null;
         try {
             Query query = hiber.getSession().createSQLQuery("select * from user_rate_content where content_id=:content_id and users_id=:userId " ).setInteger("content_id", content_id).setInteger("userId", userId);
             entities = query.list();
             
         } catch (Exception ex) {
              System.out.println("Get Ex : " + ex.getMessage());
         } finally{
             hiber.closeSession();
         }
         return entities;	
    	
    	
    	
    }
    
    
}
