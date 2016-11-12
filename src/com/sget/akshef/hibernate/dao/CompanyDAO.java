package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sget.akshef.hibernate.entities.CompanyEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.MessagesEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;

/**
 * @author JDeeb company DAO
 */
public class CompanyDAO {
	// Hibernate Session Class
	HibernateSession hiber;

	public CompanyDAO() {
		hiber = new HibernateSession();
	}

	
	public int checkIfUserInCompany(int user_id)
	{
		int size=0;
		List<CompanyEntity> entities = null;
		try {
			Query query = hiber.getSession().createSQLQuery("select * from company_users  where user_id=:id ").setInteger("id", user_id);
			entities = query.list();
			if(entities!=null && !entities.isEmpty())
			{
				size=1;
			}
			else
			{
				size=0;
			}
		} catch (Exception ex) {
			 System.out.println("companyDAO : Get Ex : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return size;
	}
	
	
	public void insert(CompanyEntity entity) {
		try {
			Serializable ser = hiber.getSession().save(entity);
			entity.setId(ser.hashCode());
			hiber.getSession().getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hiber.closeSession();
		}
	}

	public boolean update(CompanyEntity entity) {
		if (entity == null)
			return false;
		try {
			hiber.getSession().update(entity);
			hiber.getSession().getTransaction().commit();
			return true;
		} catch (Exception ex) {
			 System.out.println("companyDAO : Inesrt Ex : " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			hiber.closeSession();
		}
		return false;
	}

	public boolean delete(CompanyEntity entity) {
		if (entity == null || entity.getId() < 1)
			return false;
		try {
			hiber.getSession().delete(entity);
			hiber.getSession().getTransaction().commit();
			return true;
		} catch (Exception ex) {
			 System.out.println("companyDAO : Delete Ex : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return false;
	}

	public List<CompanyEntity> getAll() {
		List<CompanyEntity> entities = null;
		try {
			Query query = hiber.getSession().createSQLQuery("SELECT company.* FROM company company ").addEntity("company.", CompanyEntity.class);
			entities = query.list();
			/*for (CompanyEntity entity : entities) {
				 System.out.println("companyDAO : Get Ex   entity.getUnit().getId(): " + entity.getUnit().getId());
			}*/

		} catch (Exception ex) {
			 System.out.println("companyDAO : Get Ex : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return entities;
	}


	public CompanyEntity getById(int id) {
		if (id < 1)
			return null;

		CompanyEntity entity = null;
		try {
			entity = (CompanyEntity) hiber.getSession().get(CompanyEntity.class, id);
		} catch (HibernateException ex) {
			 System.out.println("companyDAO : Get Row : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return entity;
	}


	

	public CompanyEntity getcompanyById(int id) {
		if (id < 1)
			return null;

		CompanyEntity entity = null;
		try {
			String sql = " SELECT * FROM company WHERE id = :ID " ;
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(CompanyEntity.class);
			query.setInteger("ID", id);
			entity = (CompanyEntity) query.uniqueResult();
		} catch (HibernateException ex) {
			 System.out.println("companyDAO : Get Row : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return entity;
	}
}
