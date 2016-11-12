package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.HospitalDataEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * HospitalData DAO
 */
public class HospitalDataDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public HospitalDataDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(HospitalDataEntity entity){
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
	public boolean update(HospitalDataEntity entity) {
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

    public boolean delete(HospitalDataEntity entity) {
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
    
    public List<HospitalDataEntity> getAll() {
        List<HospitalDataEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT hospital_data.* FROM hospital_data hospital_data ")
                    .addEntity("hospital_data.", HospitalDataEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public HospitalDataEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	HospitalDataEntity entity = null;
        try {
            entity = (HospitalDataEntity) hiber.getSession().get(HospitalDataEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
