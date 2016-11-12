package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.MedicalTourismEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * MedicalTourism DAO
 */
public class MedicalTourismDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public MedicalTourismDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(MedicalTourismEntity entity){
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
	public boolean update(MedicalTourismEntity entity) {
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

    public boolean delete(MedicalTourismEntity entity) {
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
    
    public List<MedicalTourismEntity> getAll() {
        List<MedicalTourismEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT medical_tourism.* FROM medical_tourism medical_tourism ")
                    .addEntity("medical_tourism.", MedicalTourismEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public MedicalTourismEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	MedicalTourismEntity entity = null;
        try {
            entity = (MedicalTourismEntity) hiber.getSession().get(MedicalTourismEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
