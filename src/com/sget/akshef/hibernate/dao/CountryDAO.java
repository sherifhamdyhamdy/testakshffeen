package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.CountryEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Country DAO
 */
public class CountryDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public CountryDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(CountryEntity entity){
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
	public boolean update(CountryEntity entity) {
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

    public boolean delete(CountryEntity entity) {
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
    
    public List<CountryEntity> getCountryName(String name_en,String name_ar) {
        List<CountryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT country.* FROM country country where name_en=:name_en  or  name_ar=:name_ar")
                    .addEntity("country.", CountryEntity.class).setString("name_en", name_en).setString("name_ar", name_ar);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    } 
    
    
    public List<CountryEntity> getAll() {
        List<CountryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT country.* FROM country country ")
                    .addEntity("country.", CountryEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public CountryEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	CountryEntity entity = null;
        try {
        	entity = (CountryEntity) hiber.getSession().get(CountryEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
