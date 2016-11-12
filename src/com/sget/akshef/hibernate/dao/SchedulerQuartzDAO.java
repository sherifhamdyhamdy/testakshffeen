package com.sget.akshef.hibernate.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sget.akshef.hibernate.entities.SchedulerQuartz;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * A data access object (DAO) providing persistence and search support for
 * SchedulerQuartz entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.SchedulerQuartzBean.MainSchedulerQuartz.db.SchedulerQuartz
 * @author MyEclipse Persistence Tools
 */

public class SchedulerQuartzDAO {
	// property constants
	public static final String TITLE = "title";

	private static SchedulerQuartzDAO uniqInstance;

	private SchedulerQuartzDAO() {
		// TODO Auto-generated constructor stub
	}

	public static SchedulerQuartzDAO getInstance() {
		if (uniqInstance == null) {
			uniqInstance = new SchedulerQuartzDAO();
		}

		return uniqInstance;
	}

	public boolean save(SchedulerQuartz transientInstance) {

		Session session = null;
		Transaction tx = null;

		try {
			System.out.println("SchedulerQuartzDAO.save()");
			
			session = new HibernateSession().getSession();
			tx = session.getTransaction();

			session.save(transientInstance);

			tx.commit();
			
			return true;

		} catch (RuntimeException re) {
//			throw re;
			
			return false;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public void delete(SchedulerQuartz persistentInstance) {

		Session session = null;
		Transaction tx = null;

		try {

			session = new HibernateSession().getSession();
			tx = session.getTransaction();

			System.out.println("SchedulerQuartzDAO.delete()");

			session.delete(persistentInstance);

			tx.commit();

		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	public SchedulerQuartz findById(java.lang.Integer id) {

		Session session = null;
		Transaction tx = null;

		try {
			session = new HibernateSession().getSession();
			tx = session.getTransaction();

			SchedulerQuartz instance = (SchedulerQuartz) session.get(
					SchedulerQuartz.class, id);

			tx.commit();

			return instance;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

	
	
	public List getAllSchedulers()
	{
		Session session = null;
		Transaction tx = null;
		
		try
		{
			session = new HibernateSession().getSession();
			tx = session.getTransaction();
			
			System.out.println("SchedulerQuartzDAO.getAllSchedulers()");
			
			List<SchedulerQuartz> list = session.createCriteria(SchedulerQuartz.class)
										.addOrder(Order.desc("id"))
										.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
										.list();
			
			tx.commit();
			
			return list;
		}
		catch (RuntimeException re)
		{
			throw re;
		}
		finally
		{
			if (session.isOpen())
				session.close();
			tx = null;
		}
	}

}
