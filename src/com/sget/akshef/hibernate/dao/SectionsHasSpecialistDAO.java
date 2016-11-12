package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SectionsHasSpecialistEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * SectionsHasSpecialist DAO
 */
public class SectionsHasSpecialistDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public SectionsHasSpecialistDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(SectionsHasSpecialistEntity entity){
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
	public boolean update(SectionsHasSpecialistEntity entity) {
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

    public boolean delete(SectionsHasSpecialistEntity entity) {
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
    
    public List<SectionsHasSpecialistEntity> getAll() {
        List<SectionsHasSpecialistEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT sections_has_specialist.* FROM sections_has_specialist sections_has_specialist ")
                    .addEntity("sections_has_specialist.", SectionsHasSpecialistEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    
    public List<SectionsEntity> getSpecialistCategory() {
        List<SectionsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT distinct sections.* FROM sections_has_specialist sections_has_specialist,sections  sections where  sections_has_specialist.sections_id=sections.id   ")
                    .addEntity("sections.", SectionsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public SectionsHasSpecialistEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	SectionsHasSpecialistEntity entity = null;
        try {
        	entity = (SectionsHasSpecialistEntity) hiber.getSession().get(SectionsHasSpecialistEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
