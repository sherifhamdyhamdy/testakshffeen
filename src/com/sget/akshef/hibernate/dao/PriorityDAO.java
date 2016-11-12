package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.PriorityEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Priority DAO
 */
public class PriorityDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public PriorityDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(PriorityEntity entity){
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
	public boolean update(PriorityEntity entity) {
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

    public boolean delete(PriorityEntity entity) {
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
    
    public List<PriorityEntity> getAll() {
        List<PriorityEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT priority.* FROM priority priority ")
                    .addEntity("priority.", PriorityEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public PriorityEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	PriorityEntity entity = null;
        try {
            entity = (PriorityEntity) hiber.getSession().get(PriorityEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
