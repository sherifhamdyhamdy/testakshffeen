package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sget.akshef.hibernate.entities.CategoryHasSubcategoryEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * SubcategoryHasSections DAO
 */
public class SubcategoryHasSectionsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public SubcategoryHasSectionsDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(SubcategoryHasSectionsEntity entity){
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
	public boolean update(SubcategoryHasSectionsEntity entity) {
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

    public boolean delete(SubcategoryHasSectionsEntity entity) {
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
    
    
    public boolean deleteBysubCategory(int section, int subcat) {
    	  
        if(section < 1 || subcat < 1 )
        	return false;
    	try {  
    		String hql = "delete from subcategory_has_sections c where c.sections = :section AND  c.subcategory = :subcat  ";
    		Query query = hiber.getSession().createQuery(hql);
    		query.setInteger("section", section);
    		query.setInteger("subcat", subcat);
    		
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
    
    public List<SubcategoryHasSectionsEntity> getAll() {
        List<SubcategoryHasSectionsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT city.* FROM subcategory_has_sections subcategory_has_sections ")
                    .addEntity("subcategory_has_sections.", SubcategoryHasSectionsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    
    public List<SubcategoryHasSectionsEntity> getAllBySubcategoryID(int subCat) {
        List<SubcategoryHasSectionsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT subcategory_has_sections.* FROM subcategory_has_sections subcategory_has_sections where " +
            		"subcategory_has_sections.subcategory_subcategory_id=:id ") 
                    .addEntity("subcategory_has_sections.", SubcategoryHasSectionsEntity.class)
                    .setInteger("id",subCat);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public SubcategoryHasSectionsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	SubcategoryHasSectionsEntity entity = null;
        try {
        	entity = (SubcategoryHasSectionsEntity) hiber.getSession().get(SubcategoryHasSectionsEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("SubcategoryHasSection : Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    // getAllByCategory
    public List<SubcategoryHasSectionsEntity> getAllByCategory(int catId) {
        List<SubcategoryHasSectionsEntity> entities = null;
        try {
        	String sql = " SELECT sub.* FROM subcategory_has_sections sub "
        				+ " INNER JOIN category_has_subcategory cat ON cat.subcategory_id = sub.subcategory_subcategory_id "
        				+ " WHERE cat.category_id = :catID" ;
            Query query = hiber.getSession().createSQLQuery(sql).addEntity("sub.", SubcategoryHasSectionsEntity.class);
            query.setInteger("catID", catId);
            entities = query.list();
        } catch (Exception ex) {
             System.out.println("SubcategoryHasSectionsDAO : Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
}
