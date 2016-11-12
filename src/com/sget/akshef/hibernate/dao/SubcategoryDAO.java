package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * SubCategory DAO
 */
public class SubcategoryDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public SubcategoryDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(SubcategoryEntity entity){
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
	public boolean update(SubcategoryEntity entity) {
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

    public boolean delete(SubcategoryEntity entity) {
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
    
    public List<SubcategoryEntity> getAll() {
        List<SubcategoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT subcategory.* FROM subcategory subcategory ")
                    .addEntity("subcategory.", SubcategoryEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public SubcategoryEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	SubcategoryEntity entity = null;
        try {
        	entity = (SubcategoryEntity) hiber.getSession().get(SubcategoryEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    @SuppressWarnings("unchecked")
  	public List<SubcategoryEntity> getAllByCategory(CategoryEntity  categoryEntity) {
      	List<SubcategoryEntity> subcategoryEntities =null;
       try {
    	   String jpql = "Select DISTINCT p FROM subcategory p, IN ( p.categoryHasSubcategorySet ) t WHERE t.category like '"
    			   + categoryEntity.getId() + "'  ";        	   
    		Query query = hiber.getSession().createQuery(jpql);
    		subcategoryEntities	= query.list();
    		
  			if (subcategoryEntities.size() > 0) {
  				 return  subcategoryEntities;
  			}
          } catch (Exception ex) {
               System.out.println("SubCattegoryDAO : Get Ex : " + ex.getMessage());
          } finally{
              hiber.closeSession();
          }
          return subcategoryEntities;
      }
    
    @SuppressWarnings("unchecked")
  	public List<SubcategoryEntity> getAllByBranch(BranchEntity branchEntity) {
      	List<SubcategoryEntity> subcategoryEntities =null;
           try {        	          	      			
       			String jpql = "Select DISTINCT N FROM subcategory N , IN ( N.subcategoryHasSectionsSet )  p, IN ( p.subcategoryHasSectionsHasBranchSet ) t WHERE t.branch like '"
       					+ branchEntity.getId() + "'  ";
   	        	Query query = hiber.getSession().createQuery(jpql);
   	           	subcategoryEntities	= query.list();
	  			if (subcategoryEntities.size() > 0) {
	  				 return  subcategoryEntities;
	  			}
          } catch (Exception ex) {
        	  ex.printStackTrace();
               System.out.println("SubCattegoryDAO : Get Ex : " + ex.getMessage());
          } finally{
              hiber.closeSession();
          }
          return subcategoryEntities;
      }
    
    @SuppressWarnings("unchecked")
   	public List<SubcategoryEntity> getAllByBranch1(BranchEntity branchEntity) {
       	List<SubcategoryEntity> subcategoryEntities =null;
        try {   
        	Criteria itemCriteria = hiber.getSession().createCriteria(
	     			   SubcategoryEntity.class);
			Criteria bidCriteria = itemCriteria
					.createCriteria("subcategoryHasSectionsSet");
			Criteria ccCriteria = bidCriteria
					.createCriteria("subcategoryHasSectionsHasBranchSet");
				
			ccCriteria.add(Restrictions.like("branch", branchEntity));
			subcategoryEntities = itemCriteria.list();
	   			
			if (subcategoryEntities.size() > 0) {
	   			 return  subcategoryEntities;
	   		}
       } catch (Exception ex) {
            System.out.println("SubCattegoryDAO : Get Ex : " + ex.getMessage());
       } finally{
           hiber.closeSession();
       }
       return subcategoryEntities;
    }
    
    public List<Object> getSubCategoryByCatId(int id) {
    	  List<Object> entities = null;
          try {
              Query query = hiber.getSession().createSQLQuery("SELECT subcategory.*,category.id catId FROM subcategory subcategory," +
              		"category_has_subcategory category_has_subcategory,category category " +
              		"where subcategory.id=category_has_subcategory.subcategory_id " +
              		"and category.id=category_has_subcategory.category_id " +
              		"and category_has_subcategory.category_id=:1 ");
           
              entities = query.setInteger("1", id).list();
               System.out.println("entity listed  "+entities);      
              
          } catch (Exception ex) {
               System.out.println("SubCattegoryDAO : Get Ex : " + ex.getMessage());
          } finally{
              hiber.closeSession();
          }
          return entities;
    } 
     
    public List<SubcategoryEntity> getSubcategoryName(String name_en,String name_ar) {
        List<SubcategoryEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT subcategory.* FROM subcategory subcategory where name_en=:name_en  or  name_ar=:name_ar")
                    .addEntity("subcategory.", SubcategoryEntity.class).setString("name_en", name_en).setString("name_ar", name_ar);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }  
    /**
     * 
     * To delete subCategory delete from table 
     * (subcategory_has_sections_has_branch,subcategory_has_sections,
     * category_has_subcategory,subCategory)
     *  
     */
    public boolean deleteSubcategory(int subcatId){
		if(subcatId > 0){
			try {
				// delete from subcategory_has_sections_has_branch
				String sql = " DELETE sbran FROM subcategory_has_sections_has_branch sbran INNER JOIN "
						+ " subcategory_has_sections sect ON sbran.subcategorysection_id = sect.id "
						+ " WHERE sect.subcategory_subcategory_id= :subcat ";
				Query query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("subcat", subcatId);
				query.executeUpdate();
				// delete from subcategory_has_sections
				sql = " DELETE FROM subcategory_has_sections WHERE subcategory_subcategory_id= :subcat ";
				query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("subcat", subcatId);
				query.executeUpdate();
				// delete from category_has_subcategory
				sql = " DELETE FROM category_has_subcategory WHERE subcategory_id = :subcat";
				query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("subcat", subcatId);
				query.executeUpdate();
				// delete from category_has_subcategory
				sql = " DELETE FROM subcategory where id = :subcat";
				query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("subcat", subcatId);
				query.executeUpdate();
				return true;
			} catch (HibernateException ex) {
				 System.out.println("SubCategoryDAO : Get Row : " + ex.getMessage());
				ex.printStackTrace();
				return false;
			} finally {
				hiber.getSession().getTransaction().commit();
				hiber.closeSession();
			}
		}
		return false;
	}
}
