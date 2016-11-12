package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb Sections DAO
 */
public class SectionsDAO
{
	// Hibernate Session Class
	HibernateSession hiber;
	
	
	
	public SectionsDAO()
	{
		hiber = new HibernateSession();
	}
	
	
	
	public void insert(SectionsEntity entity)
	{
		try
		{
			Serializable ser = hiber.getSession().save(entity);
			entity.setId(ser.hashCode());
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
	}
	
	
	
	public boolean update(SectionsEntity entity)
	{
		if (entity == null)
			return false;
		try
		{
			hiber.getSession().update(entity);
			hiber.getSession().getTransaction().commit();
			return true;
		}
		catch (Exception ex)
		{
			System.out.println("SectionsDAO : Inesrt Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	public boolean delete(SectionsEntity entity)
	{
		if (entity == null || entity.getId() < 1)
			return false;
		try
		{
			hiber.getSession().delete(entity);
			hiber.getSession().getTransaction().commit();
			return true;
		}
		catch (Exception ex)
		{
			System.out.println("SectionsDAO : Delete Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	public List<SectionsEntity> getAll()
	{
		List<SectionsEntity> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT sections.* FROM sections sections ").addEntity("sections.", SectionsEntity.class);
			entities = query.list();
			
		}
		catch (Exception ex)
		{
			System.out.println("SectionsDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	public List<SectionsEntity> getSectionName(String name_en, String name_ar)
	{
		List<SectionsEntity> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT sections.* FROM sections sections where name_en=:name_en  or  name_ar=:name_ar").addEntity("sections.", SectionsEntity.class).setString("name_en", name_en).setString("name_ar", name_ar);
			entities = query.list();
			
		}
		catch (Exception ex)
		{
			System.out.println("SectionsDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	public SectionsEntity getById(int id)
	{
		if (id < 1)
			return null;
		
		SectionsEntity entity = null;
		try
		{
			entity = (SectionsEntity) hiber.getSession().get(SectionsEntity.class, id);
		}
		catch (HibernateException ex)
		{
			System.out.println("SectionsDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entity;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<SectionsEntity> getAllBySubCategory(SubcategoryEntity subcategoryEntity)
	{
		List<SectionsEntity> sectionsEntities = null;
		try
		{
			/*
			 * Criteria itemCriteria = hiber.getSession().createCriteria(
			 * SectionsEntity.class); Criteria bidCriteria = itemCriteria
			 * .createCriteria("subcategoryHasSectionsSet");
			 * bidCriteria.add(Restrictions.like("subcategory",
			 * subcategoryEntity)); sectionsEntities = itemCriteria.list();
			 */
			String sql = " SELECT se.* FROM sections se INNER join subcategory_has_sections ss " + " ON se.id = ss.sections_section_id WHERE ss.subcategory_subcategory_id = :subCatId ";
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SectionsEntity.class);
			query.setInteger("subCatId", subcategoryEntity.getId());
			
			sectionsEntities = query.list();
			if (sectionsEntities.size() > 0)
			{
				return sectionsEntities;
			}
			
		}
		catch (Exception ex)
		{
			System.out.println("SectionsDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return sectionsEntities;
	}
	
	
	
	public List<Object> getAllSectionsBySubCategory(int categoryId, int subcategory)
	{
		List<Object> entities = null;
		String sqlString = "select sections.name_ar,sections.name_en,sections.id sectionID,category.id categoryId," + "subcategory.id subcategoryId from sections sections,subcategory subcategory," + "subcategory_has_sections subcategory_has_sections ,category category,category_has_subcategory category_has_subcategory " + "where subcategory_has_sections.sections_section_id=sections.id " + "and subcategory_has_sections.subcategory_subcategory_id=subcategory.id "
				+ "and category_has_subcategory.category_id=category.id " + "and category_has_subcategory.subcategory_id=subcategory.id " + "and category.id=:1 and subcategory.id=:2";
		try
		{
			Query query = hiber.getSession().createSQLQuery(sqlString).setInteger("1", categoryId).setInteger("2", subcategory);
			
			entities = query.list();
			
		}
		catch (Exception ex)
		{
			System.out.println("SectionsDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<SectionsEntity> getAllByBranch(BranchEntity branchEntity, SubcategoryEntity subcategoryEntity)
	{
		List<SectionsEntity> sectionsEntities = null;
		try
		{
			String jpql = "Select DISTINCT N FROM sections N , IN ( N.subcategoryHasSectionsSet )  p, IN ( p.subcategoryHasSectionsHasBranchSet ) t WHERE  p.subcategory like '" + subcategoryEntity.getId() + "'";
			if (branchEntity != null && branchEntity.getId() != 0)
			{
				jpql += " and  t.branch like '" + branchEntity.getId() + "' ";
			}
			Query query = hiber.getSession().createQuery(jpql);
			sectionsEntities = query.list();
			if (sectionsEntities.size() > 0)
			{
				return sectionsEntities;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("SectionsDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return sectionsEntities;
	}
	
	
	
	// Check Sections Exist Or Not
	public int checkSectionExist(String nameAr, String nameEn)
	{
		List<BranchEntity> entities = null;
		try
		{
			String sql = " SELECT * FROM sections WHERE name_ar = :nameAr OR name_en = :nameEn ";
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SectionsEntity.class);
			query.setString("nameAr", nameAr);
			query.setString("nameEn", nameEn);
			
			entities = query.list();
			if (entities != null && entities.size() > 0)
				return 1;
		}
		catch (Exception ex)
		{
			System.out.println("SectionsDAO : Get Ex : " + ex.getMessage());
			return 0;
		}
		finally
		{
			hiber.closeSession();
		}
		return 2;
	}
	
	
	
	public List<SectionsEntity> getSpecialistSections()
	{
		List<SectionsEntity> list = null;
		
		try
		{
			System.out.println("SectionsDAO.getSpecialistSections()");
			
			list = hiber.getSession()
					.createCriteria(SectionsEntity.class)
//					.createCriteria("this.sectionsHasSpecialistSet","sectionsHasSpecialistSet")
//					.createCriteria("this.subcategoryHasSectionsSet","subcategoryHasSectionsSet")
					.createCriteria("this.subcategoryHasSectionsSet.subcategory","subcategory")
					.createCriteria("subcategory.categoryHasSubcategorySet","categoryHasSubcategorySet")
					.add(Restrictions.eq("categoryHasSubcategorySet.category.id", DBConstants.CATEGORY_Clinics))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
			
			return list;
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
			
			return null;
		}
		finally
		{
			hiber.closeSession();
		}
	}
	

}
