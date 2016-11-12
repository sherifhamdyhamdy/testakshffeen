package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.MessageTypesEntity;
import com.sget.akshef.hibernate.entities.MessagesEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Messages DAO
 */
public class MessagesDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public MessagesDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(MessagesEntity entity){
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
	public boolean update(MessagesEntity entity) {
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

    public boolean delete(MessagesEntity entity) {
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
    
    public List<MessagesEntity> getAll() {
        List<MessagesEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT messages.* FROM messages messages ")
                    .addEntity("messages.", MessagesEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    
    public List<MessagesEntity> getAllMessagesByType(int messageType) {
        List<MessagesEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT messages.* FROM messages messages where type_id=:messageType ")
                    .addEntity("messages.", MessagesEntity.class);
            query.setInteger("messageType", messageType);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public MessagesEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	MessagesEntity entity = null;
        try {
        	entity = (MessagesEntity) hiber.getSession().createSQLQuery("SELECT * FROM messages WHERE id = ?")
        			.addEntity(MessagesEntity.class).setInteger(0, id).uniqueResult();
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }

    /**
     *  JDeeb get all messages by sender and receive
     */
    public List<MessagesEntity> getAllMessagesBetweenUsers(int userId , int oppositeID , int start , int limit) {
        List<MessagesEntity> entities = null;
        if(userId > 0 && oppositeID > 0 && start >= 0 && limit >= 0){
	        try {
	            Query query = hiber.getSession().createSQLQuery(" SELECT messages.* FROM messages messages WHERE "
	            		+ " ( from_user = :userId AND to_user = :oppositeID ) OR ( to_user = :userId AND from_user = :oppositeID ) ORDER BY msg_date desc")
	                    .addEntity(MessagesEntity.class);
	            
	            query.setInteger("userId", userId);
	            query.setInteger("oppositeID", oppositeID);
	            
	            query.setFirstResult(start);
	            query.setMaxResults(limit);
	            
	            entities = query.list();
	            
	        } catch (Exception ex) {
	             System.out.println("Get Ex getAllMessagesBetweenUsers : " + ex.getMessage());
	        } finally{
	            hiber.closeSession();
	        }
        }
        return entities;
    }
    // JDeeb Get User ID  In Specified Doctor
    public int getUserIdByDoctor(int doctorId){
		int result = 0;
		if(doctorId > 0){
			try{				
				String sql="SELECT users_id from specialist  where id=:doctor";
				Query query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("doctor", doctorId);
				
				List<Integer> li = query.list();
				if(li != null && li.size() > 0)
					result = li.get(0);
				
			}catch (Exception e) {
				 System.out.println("Ex in getUserIdByDoctor " + e.getMessage());
				e.printStackTrace();
			}finally{
				hiber.closeSession();
			}
		}
		return result;
	}
    // JDeeb Get User ID  In Specified Doctor
    public int getUserIdByBranch(int branchid){
		int result = 0;
		if(branchid > 0){
			try{
				
				String sql="SELECT users_id from branch  where id=:branch";
				Query query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("branch", branchid);
				
				List<Integer> li = query.list();
				if(li != null && li.size() > 0)
				{
					result = li.get(0)!=null &&!li.get(0).equals("")?li.get(0):0;	
				}
					
				
			}catch (Exception e) {
				 System.out.println("Ex in getUserIdByBranch " + e.getMessage());
				e.printStackTrace();
			}finally{
				hiber.closeSession();
			}
		}
		return result;
	}
	
	
	
	public MessageTypesEntity getMessageTypesEntity(int type_id)
	{
		if (type_id < 1)
			return null;
		
		MessageTypesEntity entity = null;
		try
		{
			entity = (MessageTypesEntity) hiber.getSession().get(MessageTypesEntity.class, type_id);
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entity;
	}

	
	// Insert Multi SubcategorySection to Branch
	public boolean insertUnitMessage(int unitId, int fromUser, String text, String url)
	{
		
			try
			{
				String sql = " INSERT INTO messages_unit (msg_body, from_user, to_unit, msg_date, sent_message_id) VALUES " +
							"  ('"+text+"','"+fromUser+"','"+unitId+"','"+Calendar.getInstance().getTimeInMillis()+"',0) ";
				
				Query query = hiber.getSession().createSQLQuery(sql);
				int result = query.executeUpdate();
				if (result > 0)
					return true;
			}
			catch (HibernateException ex)
			{
				System.out.println("insertUnitMessage : " + ex.getMessage());
				ex.printStackTrace();
				return false;
			}
			finally
			{
				hiber.getSession().getTransaction().commit();
				hiber.closeSession();
			}
		
			return false;
	}
}