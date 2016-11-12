package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.UserFavoritiesEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * User Favorites DAO
 */
public class UserFavoritiesDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public UserFavoritiesDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(UserFavoritiesEntity entity){
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
	public boolean update(UserFavoritiesEntity entity) {
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

    public boolean delete(UserFavoritiesEntity entity) {
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
    
    public List<UserFavoritiesEntity> getAll() {
        List<UserFavoritiesEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT user_favorities.* FROM user_favorities user_favorities ")
                    .addEntity("user_favorities.", UserFavoritiesEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public UserFavoritiesEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	UserFavoritiesEntity entity = null;
        try {
        	entity = (UserFavoritiesEntity) hiber.getSession().get(UserFavoritiesEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
 // get user favorities 
 	public List<UserFavoritiesEntity> getAllByUser(int userId) {
 		List<UserFavoritiesEntity> entities = null;
 		try {
 			Query query = hiber.getSession().createSQLQuery("SELECT user_favorities.* FROM user_favorities user_favorities WHERE users_id = :userId")
 					.addEntity("user_favorities.", UserFavoritiesEntity.class);
 			query.setInteger("userId", userId);
 			entities = query.list();
// 			hiber.getSession().getTransaction().commit();
 		} catch (Exception ex) {
 			 System.out.println("Get Ex : " + ex.getMessage());
 		} finally {
 			hiber.closeSession();
 		}
 		return entities;
 	}
    
    
}
