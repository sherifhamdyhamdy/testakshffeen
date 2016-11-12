package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Group DAO
 */
public class GroupsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public GroupsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(GroupsEntity entity){
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
	public boolean update(GroupsEntity entity) {
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

    public boolean delete(GroupsEntity entity) {
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
    
    public List<GroupsEntity> getAll() {
        List<GroupsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT groups.* FROM groups groups ")
                    .addEntity("groups.", GroupsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public GroupsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	GroupsEntity entity = null;
        try {
        	entity = (GroupsEntity) hiber.getSession().get(GroupsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    // JDeeb get All Groups For Branch
    @SuppressWarnings("unchecked")
	public List<GroupsEntity> getBranchGroups(int branchId) {
    	List<GroupsEntity> entities = null;
    	if(branchId > 0){
	        try {
	            Query query = hiber.getSession().createSQLQuery("SELECT groups.* FROM groups groups WHERE branch_id = :branchId")
	                    .addEntity("groups.", GroupsEntity.class).setInteger("branchId", branchId);
	            entities = query.list();
	        } catch (Exception ex) {
	             System.out.println("Get Ex : " + ex.getMessage());
	        } finally{
	            hiber.closeSession();
	        }
    	}
        return entities;
    }
}
