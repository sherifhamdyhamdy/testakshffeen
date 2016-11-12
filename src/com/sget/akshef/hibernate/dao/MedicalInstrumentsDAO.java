package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.MedicalInstrumentsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * MedicalInstruments DAO
 */
public class MedicalInstrumentsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public MedicalInstrumentsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(MedicalInstrumentsEntity entity){
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
	public boolean update(MedicalInstrumentsEntity entity) {
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

    public boolean delete(MedicalInstrumentsEntity entity) {
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
    
    public List<MedicalInstrumentsEntity> getAll() {
        List<MedicalInstrumentsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT medical_instruments.* FROM medical_instruments medical_instruments ")
                    .addEntity("medical_instruments.", MedicalInstrumentsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public MedicalInstrumentsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	MedicalInstrumentsEntity entity = null;
        try {
            entity = (MedicalInstrumentsEntity) hiber.getSession().get(MedicalInstrumentsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
