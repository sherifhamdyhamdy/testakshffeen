package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.InsteadRemedyEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * InsteadRemedy DAO
 */
public class InsteadRemedyDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public InsteadRemedyDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(InsteadRemedyEntity entity){
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
	public boolean update(InsteadRemedyEntity entity) {
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

    public boolean delete(InsteadRemedyEntity entity) {
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
    
    public List<InsteadRemedyEntity> getAll() {
        List<InsteadRemedyEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT instead_remedy.* FROM instead_remedy instead_remedy ")
                    .addEntity("instead_remedy.", InsteadRemedyEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public InsteadRemedyEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	InsteadRemedyEntity entity = null;
        try {
            entity = (InsteadRemedyEntity) hiber.getSession().get(InsteadRemedyEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
