package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.ProfissionaldataEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Profissionaldata DAO
 */
public class ProfissionaldataDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public ProfissionaldataDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(ProfissionaldataEntity entity){
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
	public boolean update(ProfissionaldataEntity entity) {
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

    public boolean delete(ProfissionaldataEntity entity) {
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
    
    public List<ProfissionaldataEntity> getAll() {
        List<ProfissionaldataEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT profissionaldata.* FROM profissionaldata profissionaldata ")
                    .addEntity("profissionaldata.", ProfissionaldataEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public ProfissionaldataEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	ProfissionaldataEntity entity = null;
        try {
            entity = (ProfissionaldataEntity) hiber.getSession().get(ProfissionaldataEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
