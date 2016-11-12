package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.akshffeen.mapper.Mapping;
import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UnitHasCategory;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Unit DAO
 */
public class UnitDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	private Utils utils = Utils.getInstance();
	
	public UnitDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(UnitEntity entity){
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
	public boolean update(UnitEntity entity) {
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

    public boolean delete(UnitEntity entity) {
        if(entity == null || entity.getId() < 1)
        	return false;
    	try {
    		List<BranchEntity> branchLists=new BranchDAO().getAllByUnit( entity.getId());
    		for(BranchEntity branch:branchLists)
    		{
    			hiber.getSession().delete(branch);
    		}
    		
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
    
    public List<UnitEntity> getAll() {
        List<UnitEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT unit.* FROM unit unit ")
                    .addEntity("unit.", UnitEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public UnitEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	UnitEntity entity = null;
        try {
            entity = (UnitEntity) hiber.getSession().get(UnitEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    // check if unit exist or not
    public int checkIfUnitExist(String nameAr , String nameEn){
    	List<UnitEntity> entities = null;
        try {
        	String sql = " SELECT * FROM unit WHERE name_ar = :nameAr or name_en = :nameEn ";
        	
        	Query query = hiber.getSession().createSQLQuery(sql)
                    .addEntity("unit.", UnitEntity.class);
        	query.setString("nameAr", nameAr);
        	query.setString("nameEn", nameEn);
        	
            entities = query.list();
            
            if(entities != null && entities.size() > 0)
            	return 1 ;
        } catch (Exception ex) {
             System.out.println("UnitDAO : Get Ex : " + ex.getMessage());
            return 0;
        } finally{
            hiber.closeSession();
        }
        return 2;
    }
    // Get Units in Category
    public List<UnitEntity> getAllUnitsByCat(int catId) {
        List<UnitEntity> entities = null;
        if(catId > 0){
	        try {
	            Query query = hiber.getSession().createSQLQuery("SELECT unit.* FROM unit unit where category_id = :catID")
	                    .addEntity("unit.", UnitEntity.class);
	            query.setInteger("catID", catId);
	            
	            entities = query.list();
	            
	        } catch (Exception ex) {
	             System.out.println("UnitDAO : Get Ex : " + ex.getMessage());
	        } finally{
	            hiber.closeSession();
	        }
        }
        return entities;
    }

	public Map<Integer, List<CategoryBean>> getUnitCategories(List<Integer> selectedUnits)
	{		
		Map<Integer, List<CategoryBean>> categoriesMap = new HashMap<Integer, List<CategoryBean>>();
		List<UnitHasCategory> list = null;
		
		try
		{
			Criteria criteria = hiber.getSession()
					.createCriteria(UnitHasCategory.class)
					.createCriteria("this.category","category")
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			
			if(utils.hasValue(selectedUnits))
				criteria = criteria.add(Restrictions.in("this.unit.id", selectedUnits));
			
			list = criteria.list();
			
			if(utils.hasValue(list))
			{
				for(UnitHasCategory unitHasCategory : list)
				{
					if(categoriesMap.containsKey(unitHasCategory.getUnit().getId()))				
						categoriesMap.get(unitHasCategory.getUnit().getId()).add(Mapping.mapCategoryEntity(unitHasCategory.getCategory()));
					else
					{
						List<CategoryBean> categories = new ArrayList<CategoryBean>();
						
						categories.add(Mapping.mapCategoryEntity(unitHasCategory.getCategory()));
						
						categoriesMap.put(unitHasCategory.getUnit().getId(), categories);
					}
				}
			}
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return categoriesMap;
	}


	
}
