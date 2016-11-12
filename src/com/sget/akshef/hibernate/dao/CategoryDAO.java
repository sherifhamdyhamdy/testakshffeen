package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Category DAO
 */
public class CategoryDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public CategoryDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(CategoryEntity entity){
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
	public boolean update(CategoryEntity entity) {
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

    public boolean delete(CategoryEntity entity) {
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
    
    public List<CategoryEntity> getAll() {
        List<CategoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT category.* FROM category category ")
                    .addEntity("category.", CategoryEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public List<CategoryEntity> getCategoryInsuranceCompany(int insuranceCompany) {
        List<CategoryEntity> entities = null;
        if(insuranceCompany > 0){
	        try {
	        	String sql = " SELECT DISTINCT category.* FROM category category INNER JOIN unit unit ON unit.category_id = category.id "
	        			+ " INNER JOIN unit_has_insurance_company ins ON (ins.unit_id = unit.id and ins.insurance_company_id = :insuranceCompany) ";
	            Query query = hiber.getSession().createSQLQuery(sql).addEntity("category.", CategoryEntity.class);
	            query.setInteger("insuranceCompany", insuranceCompany);
	            
	            entities = query.list();
	            
	        } catch (Exception ex) {
	             System.out.println("Get Ex : " + ex.getMessage());
	        } finally{
	            hiber.closeSession();
	        }
        }	
        return entities;
    }
    
    public List<CategoryEntity> getAllByUserId(int user_id) {
    	String sql="select category.* from category,unit,unit_has_insurance_company,company,company_users where category.id=unit.category_id "
    			+ " and unit.id=unit_has_insurance_company.unit_id and "
    			+ " unit_has_insurance_company.insurance_company_id=company.insurance_company_id and company_users.user_id=:id";
    	
    	
        List<CategoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery(sql)
                    .addEntity("category.", CategoryEntity.class).setInteger("id", user_id);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    
    public List<CategoryEntity> getCategoryName(String name_en,String name_ar) {
        List<CategoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT category.* FROM category category where name_en=:name_en  or  name_ar=:name_ar")
                    .addEntity("category.", CategoryEntity.class).setString("name_en", name_en).setString("name_ar", name_ar);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public CategoryEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	CategoryEntity entity = null;
        try {
            entity = (CategoryEntity) hiber.getSession().get(CategoryEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    

    
    
    public CategoryEntity getCategory(int id)
    {		
    	System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
    	
		if (id < 1)
			return null;
		
		CategoryEntity entity = null;
		try
		{
			entity = (CategoryEntity) hiber.getSession()
						.createCriteria(CategoryEntity.class)
						.add(Restrictions.eq("this.id", id))
						.uniqueResult();
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
	

}
