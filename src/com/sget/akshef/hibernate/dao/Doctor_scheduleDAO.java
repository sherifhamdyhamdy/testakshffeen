package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.entities.Doctor_scheduleEntity;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb Messages DAO
 */
public class Doctor_scheduleDAO
{
	// Hibernate Session Class
	HibernateSession hiber;
	
	private Utils utils = Utils.getInstance();
	
	
	
	public Doctor_scheduleDAO()
	{
		hiber = new HibernateSession();
	}
	
	
	
	public void save(Doctor_scheduleEntity entity)
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
	
	public boolean update(Doctor_scheduleEntity entity)
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
	
	
	
	public boolean delete(Doctor_scheduleEntity entity)
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
	
	
	
	public List<Doctor_scheduleEntity> getAll()
	{
		List<Doctor_scheduleEntity> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT messages.* FROM messages messages ").addEntity("messages.", Doctor_scheduleEntity.class);
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
	
	
	
	public List<Doctor_scheduleEntity> getAllMessagesByType(int messageType)
	{
		List<Doctor_scheduleEntity> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT messages.* FROM messages messages where type_id=:messageType ").addEntity("messages.", Doctor_scheduleEntity.class);
			query.setInteger("messageType", messageType);
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
	
	
	
	public Doctor_scheduleEntity getById(int id)
	{
		if (id < 1)
			return null;
		
		Doctor_scheduleEntity entity = null;
		try
		{
			entity = (Doctor_scheduleEntity) hiber.getSession().createSQLQuery("SELECT * FROM messages WHERE id = ?").addEntity(Doctor_scheduleEntity.class).setInteger(0, id).uniqueResult();
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
	
	
	
	/**
	 * JDeeb get all messages by sender and receive
	 */
	public List<Doctor_scheduleEntity> getAllMessagesBetweenUsers(int userId, int oppositeID, int start, int limit)
	{
		List<Doctor_scheduleEntity> entities = null;
		if (userId > 0 && oppositeID > 0 && start >= 0 && limit >= 0)
		{
			try
			{
				Query query = hiber.getSession().createSQLQuery(" SELECT messages.* FROM messages messages WHERE " + " ( from_user = :userId AND to_user = :oppositeID ) OR ( to_user = :userId AND from_user = :oppositeID ) ORDER BY msg_date desc").addEntity(Doctor_scheduleEntity.class);
				
				query.setInteger("userId", userId);
				query.setInteger("oppositeID", oppositeID);
				
				query.setFirstResult(start);
				query.setMaxResults(limit);
				
				entities = query.list();
				
			}
			catch (Exception ex)
			{
				System.out.println("Get Ex getAllMessagesBetweenUsers : " + ex.getMessage());
			}
			finally
			{
				hiber.closeSession();
			}
		}
		return entities;
	}
	
	
	
	// JDeeb Get User ID In Specified Doctor
	public int getUserIdByDoctor(int doctorId)
	{
		int result = 0;
		if (doctorId > 0)
		{
			try
			{
				String sql = "SELECT users_id from specialist  where id=:doctor";
				Query query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("doctor", doctorId);
				
				List<Integer> li = query.list();
				if (li != null && li.size() > 0)
					result = li.get(0);
				
			}
			catch (Exception e)
			{
				System.out.println("Ex in getUserIdByDoctor " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				hiber.closeSession();
			}
		}
		return result;
	}
	
	
	
	// JDeeb Get User ID In Specified Doctor
	public int getUserIdByBranch(int branchid)
	{
		int result = 0;
		if (branchid > 0)
		{
			try
			{
				
				String sql = "SELECT users_id from branch  where id=:branch";
				Query query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("branch", branchid);
				
				List<Integer> li = query.list();
				if (li != null && li.size() > 0)
					result = li.get(0);
				
			}
			catch (Exception e)
			{
				System.out.println("Ex in getUserIdByBranch " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				hiber.closeSession();
			}
		}
		return result;
	}
	
	
	public SpecialistEntity getDoctor(int doctorId)
	{
		SpecialistEntity doctor = null;
		try
		{
			doctor = (SpecialistEntity)hiber.getSession()
					.createCriteria(SpecialistEntity.class)
					.add(Restrictions.eq("this.id", doctorId))
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
		return doctor;
	}
	
	public List<Doctor_scheduleEntity> getDoctorBranchSchedules(int doctorId, int branchId)
	{
		List<Doctor_scheduleEntity> list = null;
		try
		{
			list = hiber.getSession()
					.createCriteria(Doctor_scheduleEntity.class)
					.add(Restrictions.eq("this.doctor.id", doctorId))
					.add(Restrictions.eq("this.branch.id", branchId))
					.addOrder(Order.asc("this.from_hour"))
					.addOrder(Order.asc("this.to_hour"))
					.addOrder(Order.asc("this.day_id"))
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
