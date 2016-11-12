package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.entities.UsersGroupsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Users DAO
 */
public class UsersGroupsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public UsersGroupsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(UsersGroupsEntity entity){
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
	public boolean update(UsersGroupsEntity entity) {
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

    public boolean delete(UsersGroupsEntity entity) {
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
    
    public List<UsersGroupsEntity> getAll() {
        List<UsersGroupsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT users_groups.* FROM users_groups users_groups ")
                    .addEntity("users_groups.", UsersGroupsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public UsersGroupsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	UsersGroupsEntity entity = null;
        try {
        	entity = (UsersGroupsEntity) hiber.getSession().get(UsersGroupsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    /**
     * Remove List Of Users from group
     * @param entity
     * @return
     */
    public boolean deleteUsersFromGroup(int usersId , int groupId) {
    	try {
    		String sql = " DELETE FROM users_groups WHERE users_id = :userId AND groups_id = :groupId " ;
    		Query query = hiber.getSession().createSQLQuery(sql);
    		
    		query.setInteger("userId", usersId);
    		query.setInteger("groupId", groupId);
    		
            query.executeUpdate();
    		return true;
        } catch (Exception ex) {
             System.out.println("deleteUsersFromGroup Ex : " + ex.getMessage());
        } finally{
        	hiber.getSession().getTransaction().commit();
        	hiber.closeSession();
        }
        return false;
    }
    /**
     * InsertUserToGroup
     * @param usersId
     * @param groupId
     * @return
     */
    public boolean insertUserToGroup(int usersId , int groupId) {
    	try {
    		String sql = " INSERT INTO users_groups (users_id, groups_id) VALUES (:userId, :groupId)" ;
    		Query query = hiber.getSession().createSQLQuery(sql);
    		
    		query.setInteger("userId", usersId);
    		query.setInteger("groupId", groupId);
    		
            query.executeUpdate();
    		return true;
        } catch (Exception ex) {
             System.out.println("deleteUsersFromGroup Ex : " + ex.getMessage());
        } finally{
        	hiber.getSession().getTransaction().commit();
        	hiber.closeSession();
        }
        return false;
    }
}
