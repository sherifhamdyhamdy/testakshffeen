package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.UnitHasInsuranceCompanyEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * UnitHasInsuranceCompany DAO
 */
public class UnitHasInsuranceCompanyDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public UnitHasInsuranceCompanyDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(UnitHasInsuranceCompanyEntity entity){
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
	public boolean update(UnitHasInsuranceCompanyEntity entity) {
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

    public boolean delete(UnitHasInsuranceCompanyEntity entity) {
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
    
    public List<UnitHasInsuranceCompanyEntity> getAll() {
        List<UnitHasInsuranceCompanyEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT unit_has_insurance_company.* FROM unit_has_insurance_company unit_has_insurance_company ")
                    .addEntity("unit_has_insurance_company.", UnitHasInsuranceCompanyEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public UnitHasInsuranceCompanyEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	UnitHasInsuranceCompanyEntity entity = null;
        try {
            entity = (UnitHasInsuranceCompanyEntity) hiber.getSession().get(UnitHasInsuranceCompanyEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
