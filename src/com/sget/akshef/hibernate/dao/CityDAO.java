package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CityEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * City DAO
 */
public class CityDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public CityDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(CityEntity entity){
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
	public boolean update(CityEntity entity) {
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

    public boolean delete(CityEntity entity) {
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
    
    public List<CityEntity> getAll() {
        List<CityEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT city.* FROM city city ")
                    .addEntity("city.", CityEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public CityEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	CityEntity entity = null;
        try {
        	entity = (CityEntity) hiber.getSession().get(CityEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    public List<CityEntity> getAllCitiesByCountry(int country_id) {
        List<CityEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT city.* FROM city city where country_id=:1")
                    .addEntity("city.", CityEntity.class).setInteger("1",country_id);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("CityDAO : Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    // check if City exist or not
    public int checkIfCityExist(String nameAr, String nameEn){
    	List<CityEntity> entities = null;
        try {
        	String sql = " SELECT * FROM city WHERE name_ar = :nameAr OR name_en = :nameEn ";
        	
        	Query query = hiber.getSession().createSQLQuery(sql)
                    .addEntity(CityEntity.class);
        	query.setString("nameAr", nameAr);
        	query.setString("nameEn", nameEn);
        	
            entities = query.list();
            
            if(entities != null && entities.size() > 0)
            	return 1 ;
        } catch (Exception ex) {
             System.out.println("CityDAO : Get Ex : " + ex.getMessage());
            ex.printStackTrace();
            return 0;
        } finally{
            hiber.closeSession();
        }
        return 2;
    }
}
