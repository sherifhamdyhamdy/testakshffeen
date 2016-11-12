package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

 
import com.sget.akshef.hibernate.entities.CategoryHasSubcategoryEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

 
/**
 * @author JDeeb
 * Category-SubCategory Relation Table DAO
 */
public class CategoryHasSubcategoryDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public CategoryHasSubcategoryDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(CategoryHasSubcategoryEntity entity){
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
	public boolean update(CategoryHasSubcategoryEntity entity) {
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

    public boolean delete(CategoryHasSubcategoryEntity entity) {
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
    public boolean deleteByCategory(CategoryHasSubcategoryEntity entity) {
  
        if(entity == null )
        	return false;
    	try {  
    		String hql = "delete from category_has_subcategory c where c.category = :name1 AND  c.subcategory = :name2  ";
    		Query query = hiber.getSession().createQuery(hql);
    		query.setInteger("name1",entity.getCategory().getId());
    		query.setInteger("name2",entity.getSubcategory().getId());
    		query.executeUpdate();

    		hiber.getSession().getTransaction().commit();
            return true ;
        } catch (Exception ex) {
			ex.printStackTrace();        
			} finally{
            hiber.closeSession();
        }
        return false;
    }
    
    public List<CategoryHasSubcategoryEntity> getAll() {
        List<CategoryHasSubcategoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT category_has_subcategory.* FROM category_has_subcategory category_has_subcategory ")
                    .addEntity("category_has_subcategory.", CategoryHasSubcategoryEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public CategoryHasSubcategoryEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	CategoryHasSubcategoryEntity entity = null;
        try {
            entity = (CategoryHasSubcategoryEntity) hiber.getSession().get(CategoryHasSubcategoryEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
}
