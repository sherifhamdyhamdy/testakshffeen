package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.InsuranceCompanyEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Insurance Company DAO
 */
public class InsuranceCompanyDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public InsuranceCompanyDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(InsuranceCompanyEntity entity){
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
	public boolean update(InsuranceCompanyEntity entity) {
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

    public boolean delete(InsuranceCompanyEntity entity) {
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
    
    public List<InsuranceCompanyEntity> getAll() {
        List<InsuranceCompanyEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT insurance_company.* FROM insurance_company insurance_company ")
                    .addEntity("insurance_company.", InsuranceCompanyEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public InsuranceCompanyEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	InsuranceCompanyEntity entity = null;
        try {
            entity = (InsuranceCompanyEntity) hiber.getSession().get(InsuranceCompanyEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
