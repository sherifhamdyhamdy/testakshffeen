package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.RadiolgyCenterEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * RadiolgyCenter DAO
 */
public class RadiolgyCenterDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public RadiolgyCenterDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(RadiolgyCenterEntity entity){
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
	public boolean update(RadiolgyCenterEntity entity) {
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

    public boolean delete(RadiolgyCenterEntity entity) {
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
    
    public List<RadiolgyCenterEntity> getAll() {
        List<RadiolgyCenterEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT radiolgy_center.* FROM radiolgy_center radiolgy_center ")
                    .addEntity("radiolgy_center.", RadiolgyCenterEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public RadiolgyCenterEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	RadiolgyCenterEntity entity = null;
        try {
            entity = (RadiolgyCenterEntity) hiber.getSession().get(RadiolgyCenterEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
