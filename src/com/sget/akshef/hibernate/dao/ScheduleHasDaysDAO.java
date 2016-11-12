package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.ScheduleHasDaysEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Schedule Has Days DAO
 */
public class ScheduleHasDaysDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public ScheduleHasDaysDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(ScheduleHasDaysEntity entity){
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
	public boolean update(ScheduleHasDaysEntity entity) {
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

    public boolean delete(ScheduleHasDaysEntity entity) {
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
    
    public List<ScheduleHasDaysEntity> getAll() {
        List<ScheduleHasDaysEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT schedule_has_days.* FROM schedule_has_days schedule_has_days ")
                    .addEntity("schedule_has_days.", ScheduleHasDaysEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public ScheduleHasDaysEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	ScheduleHasDaysEntity entity = null;
        try {
        	entity = (ScheduleHasDaysEntity) hiber.getSession().get(ScheduleHasDaysEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
