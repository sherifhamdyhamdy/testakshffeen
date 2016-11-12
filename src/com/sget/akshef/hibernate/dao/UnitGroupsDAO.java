package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.UnitGroupsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Unit Groups DAO
 */
public class UnitGroupsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public UnitGroupsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(UnitGroupsEntity entity){
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
	public boolean update(UnitGroupsEntity entity) {
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

    public boolean delete(UnitGroupsEntity entity) {
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
    
    public List<UnitGroupsEntity> getAll() {
        List<UnitGroupsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT unit_groups.* FROM unit_groups unit_groups ")
                    .addEntity("unit_groups.", UnitGroupsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public UnitGroupsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	UnitGroupsEntity entity = null;
        try {
            entity = (UnitGroupsEntity) hiber.getSession().get(UnitGroupsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
