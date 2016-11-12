package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.ApprovalMsgEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * ApprovalMsg DAO
 */
public class ApprovalMsgDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public ApprovalMsgDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(ApprovalMsgEntity entity){
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
	public boolean update(ApprovalMsgEntity entity) {
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

    public boolean delete(ApprovalMsgEntity entity) {
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
    
    public List<ApprovalMsgEntity> getAll() {
        List<ApprovalMsgEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT approval_msg.* FROM approval_msg approval_msg ")
                    .addEntity("approval_msg.", ApprovalMsgEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public ApprovalMsgEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	ApprovalMsgEntity entity = null;
        try {
            entity = (ApprovalMsgEntity) hiber.getSession().get(ApprovalMsgEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
