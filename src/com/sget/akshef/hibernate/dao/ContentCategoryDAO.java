package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.ContentCategoryEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * ContentCategory DAO
 */
public class ContentCategoryDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public ContentCategoryDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(ContentCategoryEntity entity){
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
	public boolean update(ContentCategoryEntity entity) {
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

    public boolean delete(ContentCategoryEntity entity) {
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
    
    public List<ContentCategoryEntity> getAll() {
        List<ContentCategoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT content_category.* FROM content_category content_category ")
                    .addEntity("content_category.", ContentCategoryEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public ContentCategoryEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	ContentCategoryEntity entity = null;
        try {
            entity = (ContentCategoryEntity) hiber.getSession().get(ContentCategoryEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    public List<ContentCategoryEntity> getAllByContentType(String  contentTypeName) {
        List<ContentCategoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT content_category.* FROM content_category content_category,content_types content_types" +
            		"  where content_category.content_types_id=content_types.id  and content_types.name_en=:contentTypeName ")
                    .addEntity("content_category.", ContentCategoryEntity.class).setParameter("contentTypeName", contentTypeName);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    
    
    
}
