package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.TempBranchEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * TEMP_BRANCH DAO
 */
public class TempBranchDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public TempBranchDAO(){
		hiber = new HibernateSession();
	}
	
	
	
	public boolean insert(TempBranchEntity entity, UsersEntity user_recommend)
	{
		try
		{
			Serializable ser = null;
			
			//save recommend user
			if(user_recommend!=null)
			{
				ser = hiber.getSession().save(user_recommend);
				entity.setUser_recommend(ser.hashCode());
			}
			
			ser = hiber.getSession().save(entity);
			entity.setId(ser.hashCode());
			
			hiber.getSession().getTransaction().commit();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			hiber.closeSession();
		}
	}
	
	public boolean update(TempBranchEntity entity) {
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

    public boolean delete(TempBranchEntity entity) {
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
    
    public List<TempBranchEntity> getAll() {
        List<TempBranchEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT temp_branch.* FROM temp_branch temp_branch ")
                    .addEntity("temp_branch.", TempBranchEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public TempBranchEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	TempBranchEntity entity = null;
        try {
        	entity = (TempBranchEntity) hiber.getSession().get(TempBranchEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
