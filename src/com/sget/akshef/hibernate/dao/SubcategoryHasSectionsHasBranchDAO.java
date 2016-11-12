package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsHasBranchEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * SubcategoryHasSectionsHasBranch DAO
 */
public class SubcategoryHasSectionsHasBranchDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public SubcategoryHasSectionsHasBranchDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(SubcategoryHasSectionsHasBranchEntity entity){
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
	public boolean update(SubcategoryHasSectionsHasBranchEntity entity) {
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

    public boolean delete(SubcategoryHasSectionsHasBranchEntity entity) {
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
    
    public List<SubcategoryHasSectionsHasBranchEntity> getAll() {
        List<SubcategoryHasSectionsHasBranchEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT subcategory_has_sections_has_branch.* FROM subcategory_has_sections_has_branch subcategory_has_sections_has_branch ")
                    .addEntity("subcategory_has_sections_has_branch.", SubcategoryHasSectionsHasBranchEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public List<SubcategoryHasSectionsHasBranchEntity> getBYBranch( int branchId) {
        List<SubcategoryHasSectionsHasBranchEntity> entities = null;
        try {
        	String sql = " SELECT * FROM subcategory_has_sections_has_branch s WHERE branch_id = :branchId ";
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SubcategoryHasSectionsHasBranchEntity.class);
			query.setInteger("branchId", branchId);
			
			entities = query.list();
        } catch (Exception ex) {
             System.out.println("SubcategoryHasSectionsHasBranchDAO Get Ex : " + ex.getMessage());
            ex.printStackTrace();
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public SubcategoryHasSectionsHasBranchEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	SubcategoryHasSectionsHasBranchEntity entity = null;
        try {
        	entity = (SubcategoryHasSectionsHasBranchEntity) hiber.getSession().get(SubcategoryHasSectionsHasBranchEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }


	public List getSectionsPrices(int branchId, int subCategoryId)
	{
		List<BranchEntity> list = null;
		try
		{
			list = hiber.getSession().createCriteria(SubcategoryHasSectionsHasBranchEntity.class)
					.createCriteria("this.subcategoryHasSections", "subcategoryHasSections")
					.createCriteria("subcategoryHasSections.sections", "section")
					.add(Restrictions.eq("subcategoryHasSections.subcategory.id", subCategoryId))
					.add(Restrictions.eq("this.branch.id", branchId))
					.add(Restrictions.isNotNull("this.price"))
					.add(Restrictions.gt("this.price",0d))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return list;
	}

	public List getDoctorsPrices(int branchId, int doctorId)
	{
		List<BranchEntity> list = null;
		try
		{
			list = hiber.getSession().createCriteria(SubcategoryHasSectionsHasBranchEntity.class)
					.createCriteria("this.subcategoryHasSections", "subcategoryHasSections")
					.createCriteria("subcategoryHasSections.sections", "section")
					.createCriteria("section.sectionsHasSpecialistSet", "sectionsHasSpecialistSet")
					.add(Restrictions.eq("this.branch.id", branchId))					
					.add(Restrictions.eq("sectionsHasSpecialistSet.specialist.id", doctorId))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return list;
	}
}