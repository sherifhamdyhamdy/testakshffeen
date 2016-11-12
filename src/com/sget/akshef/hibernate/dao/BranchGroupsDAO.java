package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.BranchGroupsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Branch Groups DAO
 */
public class BranchGroupsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public BranchGroupsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(BranchGroupsEntity entity){
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
	public boolean update(BranchGroupsEntity entity) {
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

    public boolean delete(BranchGroupsEntity entity) {
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
    
    public List<BranchGroupsEntity> getAll() {
        List<BranchGroupsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT branch_groups.* FROM branch_groups branch_groups ")
                    .addEntity("branch_groups.", BranchGroupsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public BranchGroupsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	BranchGroupsEntity entity = null;
        try {
        	entity = (BranchGroupsEntity) hiber.getSession().get(BranchGroupsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
