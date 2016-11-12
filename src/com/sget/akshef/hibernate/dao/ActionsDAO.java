package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.ActionsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Actions DAO
 */
public class ActionsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public ActionsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(ActionsEntity entity){
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
	public boolean update(ActionsEntity entity) {
        if(entity == null)
        	return false;
		try {
            hiber.getSession().update(entity);
            hiber.getSession().getTransaction().commit();
            return true;
        } catch (Exception ex) {
        } finally{
            hiber.closeSession();
        }
        return false;
    }

    public boolean delete(ActionsEntity entity) {
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
    
    public List<ActionsEntity> getAll() {
        List<ActionsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT actions.* FROM actions actions ")
                    .addEntity("actions.", ActionsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public ActionsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	ActionsEntity entity = null;
        try {
            entity = (ActionsEntity) hiber.getSession().get(ActionsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
