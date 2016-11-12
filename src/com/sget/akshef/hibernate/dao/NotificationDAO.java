package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.NotificationEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Days DAO
 */
public class NotificationDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public NotificationDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(NotificationEntity entity){
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
	public boolean update(NotificationEntity entity) {
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

    public boolean delete(NotificationEntity entity) {
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
    
    public List<NotificationEntity> getAll(int start ,int limit) {
        List<NotificationEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("select notification.* FROM notification notification     order by date desc      LIMIT :start,:limit     ")
                    .addEntity("days.", NotificationEntity.class).setInteger("start", start).setInteger("limit", limit);
            entities = query.list();
        
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    

    
    public NotificationEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	NotificationEntity entity = null;
        try {
        	entity = (NotificationEntity) hiber.getSession().get(NotificationEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
