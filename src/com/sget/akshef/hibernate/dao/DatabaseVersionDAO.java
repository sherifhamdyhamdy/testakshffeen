package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sget.akshef.hibernate.entities.DataBaseVersionEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb DataBaseVesrion DAO
 */
public class DatabaseVersionDAO
{
	// Hibernate Session Class
	HibernateSession hiber;
	
	
	
	public DatabaseVersionDAO()
	{
		hiber = new HibernateSession();
	}
	
	
	
	public boolean insert(DataBaseVersionEntity entity)
	{
		try
		{
			Serializable ser = hiber.getSession().save(entity);
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
	
	
	
	public boolean update(DataBaseVersionEntity entity)
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
			System.out.println("Inesrt Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	public boolean delete(DataBaseVersionEntity entity)
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
			System.out.println("Delete Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	public List<DataBaseVersionEntity> getAll()
	{
		List<DataBaseVersionEntity> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT database_version.* FROM database_version database_version ").addEntity("database_version.", DataBaseVersionEntity.class);
			entities = query.list();
			
		}
		catch (Exception ex)
		{
			System.out.println("Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	

	
	
	public DataBaseVersionEntity getLastVersion()
	{
		Session session = null;
		Transaction tx = null;
		
		try 
		{				
			session = new HibernateSession().getSession();
			tx = session.getTransaction();
			
			DataBaseVersionEntity dataBaseVersion = (DataBaseVersionEntity) session.createCriteria(DataBaseVersionEntity.class)
													.addOrder(Order.desc("this.id"))
													.setMaxResults(1)
													.uniqueResult();
					
		      tx.commit();
		      
		      return dataBaseVersion;
		} catch (RuntimeException re) {
			throw re;
		}
		finally{		
			if(session.isOpen()) session.close();
			tx = null;
		}
	}
	
	
	
	/**
	 * Check if this version is the last or not
	 * 
	 * @return 1 if it is the last version or 0 if it is not the last version
	 */
	public int isVersionLast(int version)
	{
		int res = 0;
		try
		{
			System.out.println("DatabaseVersionDAO.isVersionLast()");
			
			DataBaseVersionEntity dataBaseVersion = (DataBaseVersionEntity) 
													hiber.getSession().createCriteria(DataBaseVersionEntity.class)
													.addOrder(Order.desc("this.id"))
													.setMaxResults(1)
													.uniqueResult();
			
			if(dataBaseVersion.getVersion().intValue()==version)
				return 1;
			else
				return 0;
		}
		catch (Exception ex)
		{
			System.out.println("DatabaseVersionDAO Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return res;
	}
	
	public DataBaseVersionEntity isVersionLastNew(int version)
	{
		int result = 0;
		DataBaseVersionEntity dataBaseVersion = null;
		
		try
		{
			
			String sql = " SELECT EXISTS(SELECT * FROM database_version " + " WHERE version = :dbVersion and id=(select max(id) from database_version ) ) resul ";
			Query query = hiber.getSession().createSQLQuery(sql);
			query.setInteger("dbVersion", version);
			result = ((Number) query.uniqueResult()).intValue();
			
			if (result == 1)
			{
				dataBaseVersion = (DataBaseVersionEntity) 
									hiber.getSession().createCriteria(DataBaseVersionEntity.class).
									add(Restrictions.eq("this.version", version)).
									uniqueResult();
			}
			
		}
		catch (Exception ex)
		{
			System.out.println("DatabaseVersionDAO Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		
		return dataBaseVersion;
	}
	
	
	public DataBaseVersionEntity getById(int id)
	{
		if (id < 1)
			return null;
		
		DataBaseVersionEntity entity = null;
		try
		{
			entity = (DataBaseVersionEntity) hiber.getSession().get(DataBaseVersionEntity.class, id);
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
