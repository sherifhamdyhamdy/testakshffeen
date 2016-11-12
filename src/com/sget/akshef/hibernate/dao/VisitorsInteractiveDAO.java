package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sget.akshef.hibernate.entities.VisitorsInteractiveEntity;
import com.sget.akshef.hibernate.utils.HibernateUtil;

/**
 * @author JDeeb
 * Messages DAO
 */
public class VisitorsInteractiveDAO {
	
	public void insert(VisitorsInteractiveEntity entity){
		try{
		Serializable ser = getSession().save(entity);
		entity.setId(ser.hashCode());
		getSession().getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeSession();
		}
	}
	public boolean update(VisitorsInteractiveEntity entity) {
        if(entity == null)
        	return false;
		try {
            getSession().update(entity);
            getSession().getTransaction().commit();
            return true;
        } catch (Exception ex) {
             System.out.println("Inesrt Ex : " + ex.getMessage());
        } finally{
            closeSession();
        }
        return false;
    }

    public boolean delete(VisitorsInteractiveEntity entity) {
        if(entity == null || entity.getId() < 1)
        	return false;
    	try {
            getSession().delete(entity);
            getSession().getTransaction().commit();
            return true ;
        } catch (Exception ex) {
             System.out.println("Delete Ex : " + ex.getMessage());
        } finally{
            closeSession();
        }
        return false;
    }
    
    public List<VisitorsInteractiveEntity> getAll() {
        List<VisitorsInteractiveEntity> entities = null;
        try {
            Query query = getSession().createSQLQuery("SELECT visitors_interactive.* FROM visitors_interactive visitors_interactive ")
                    .addEntity("visitors_interactive.", VisitorsInteractiveEntity.class);
            entities = query.list();
            getSession().getTransaction().commit();
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            closeSession();
        }
        return entities;
    }
    
    public VisitorsInteractiveEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	VisitorsInteractiveEntity entity = null;
        try {
            entity = (VisitorsInteractiveEntity) getSession().get(VisitorsInteractiveEntity.class, id);
            getSession().getTransaction().commit();
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            closeSession();
        }
        return entity;
    }
    // Session Control
	private Session session = null;

	public synchronized Session getSession() {
		if (session == null || !session.isOpen()) {
			return makeSession();
		}
		return session;
	}

	public Session makeSession() {
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (session == null || !session.isOpen()){
			session = HibernateUtil.getSessionFactory().openSession();
			 System.out.println("Can't get Current Session so Open Session status : " + session.isOpen());
		}
		if (session != null && session.isOpen())
			session.beginTransaction();
		return session;
	}

	public void closeSession() {
		if (getSession() != null || getSession().isOpen())
			getSession().close();
	}
}