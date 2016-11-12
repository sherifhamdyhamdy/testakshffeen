package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CityEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Distric DAO
 */
public class DistricDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public DistricDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(DistricEntity entity){
		try{
		Serializable ser = hiber.getSession().save(entity);
		entity.setId(ser.hashCode());
		hiber.getSession().getTransaction().commit();
		}catch (Exception e) {
			 System.out.println("DistricDAO : EX " + e.getMessage());
			e.printStackTrace();
		}finally{
			hiber.closeSession();
		}
	}
	public boolean update(DistricEntity entity) {
        if(entity == null)
        	return false;
		try {
            hiber.getSession().update(entity);
            hiber.getSession().getTransaction().commit();
            return true;
        } catch (Exception ex) {
             System.out.println("DistricDAO : Inesrt Ex : " + ex.getMessage());
            ex.printStackTrace();
        } finally{
            hiber.closeSession();
        }
        return false;
    }

    public boolean delete(DistricEntity entity) {
        if(entity == null || entity.getId() < 1)
        	return false;
    	try {
    		/*Set<BranchEntity> set=entity.getBranchSet();
    		for(BranchEntity branch:set)
    		{
    			branch.setDistric(null);
    		}*/
            hiber.getSession().delete(entity);
            hiber.getSession().getTransaction().commit();
            return true ;
        } catch (Exception ex) {
             System.out.println("DistricDAO : Delete Ex : " + ex.getMessage());
            ex.printStackTrace();
        } finally{
            hiber.closeSession();
        }
        return false;
    }
    
    public List<DistricEntity> getAll() {
        List<DistricEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT distric.* FROM distric distric ")
                    .addEntity("distric.", DistricEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
        	ex.printStackTrace();
             System.out.println("DistricDAO : Get Ex : " + ex.getMessage());
            ex.printStackTrace();
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public DistricEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	DistricEntity entity = null;
        try {
        	entity = (DistricEntity) hiber.getSession().get(DistricEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("DistricDAO : Get Row : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    public List<DistricEntity> getDistrictName(String name_en,String name_ar) {
        List<DistricEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT distric.* FROM distric distric where name_en=:name_en  or  name_ar=:name_ar")
                    .addEntity("distric.", DistricEntity.class).setString("name_en", name_en).setString("name_ar", name_ar);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("DistricDAO : Get Ex : " + ex.getMessage());
            ex.printStackTrace();
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    public List<Object> getAllDistrictByCity(int city_id) {
        List<Object> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT ID,name_en,name_ar FROM distric distric  where city_id=:1")
                    .setInteger("1", city_id);
            entities = query.list();
            
        } catch (Exception ex) {
        	ex.printStackTrace();
             System.out.println("DistricDAO : Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    // check if City exist or not
    public int checkIfDistrictExist(String nameAr, String nameEn){
    	List<DistricEntity> entities = null;
        try {
        	String sql = " SELECT * FROM distric WHERE name_ar = :nameAr OR name_en = :nameEn ";
        	
        	Query query = hiber.getSession().createSQLQuery(sql)
                    .addEntity(DistricEntity.class);
        	query.setString("nameAr", nameAr);
        	query.setString("nameEn", nameEn);
        	
            entities = query.list();
            
            if(entities != null && entities.size() > 0)
            	return 1 ;
        } catch (Exception ex) {
             System.out.println("DistricDAO : Get Ex : " + ex.getMessage());
            ex.printStackTrace();
            return 0;
        } finally{
            hiber.closeSession();
        }
        return 2;
    }
}
