package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.DrugsCompanyEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * DrugsCompany DAO
 */
public class DrugsCompanyDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public DrugsCompanyDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(DrugsCompanyEntity entity){
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
	public boolean update(DrugsCompanyEntity entity) {
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

    public boolean delete(DrugsCompanyEntity entity) {
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
    
    public List<DrugsCompanyEntity> getAll() {
        List<DrugsCompanyEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT drugs_company.* FROM drugs_company drugs_company ")
                    .addEntity("drugs_company.", DrugsCompanyEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public DrugsCompanyEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	DrugsCompanyEntity entity = null;
        try {
            entity = (DrugsCompanyEntity) hiber.getSession().get(DrugsCompanyEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
