package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.MedicalhistoryEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Medicalhistory DAO
 */
public class MedicalhistoryDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public MedicalhistoryDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(MedicalhistoryEntity entity){
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
	public boolean update(MedicalhistoryEntity entity) {
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

    public boolean delete(MedicalhistoryEntity entity) {
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
    
    public List<MedicalhistoryEntity> getAll() {
        List<MedicalhistoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT medicalhistory.* FROM medicalhistory medicalhistory ")
                    .addEntity("medicalhistory.", MedicalhistoryEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public MedicalhistoryEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	MedicalhistoryEntity entity = null;
        try {
            entity = (MedicalhistoryEntity) hiber.getSession().get(MedicalhistoryEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
