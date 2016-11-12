package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.DrugsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Drugs DAO
 */
public class DrugsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public DrugsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(DrugsEntity entity){
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
	public boolean update(DrugsEntity entity) {
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

    public boolean delete(DrugsEntity entity) {
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
    
    public List<DrugsEntity> getAll() {
        List<DrugsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT drugs.* FROM drugs drugs ")
                    .addEntity("drugs.", DrugsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public DrugsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	DrugsEntity entity = null;
        try {
        	entity = (DrugsEntity) hiber.getSession().get(DrugsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
