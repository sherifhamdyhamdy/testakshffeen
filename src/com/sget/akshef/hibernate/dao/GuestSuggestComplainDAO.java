package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.GuestSuggestComplainEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb Branch DAO
 */
public class GuestSuggestComplainDAO {

	HibernateSession hiber;
	
	public GuestSuggestComplainDAO() {
		hiber = new HibernateSession();
	}

	/**
	 * Insert New Suggest or Complain
	 * @param entity
	 */
	public int insert(GuestSuggestComplainEntity entity){
		try{
			Serializable ser = hiber.getSession().save(entity);
			entity.setId(ser.hashCode());
			hiber.getSession().getTransaction().commit();
			
			return ser.hashCode();
		}catch (Exception e) {
//			e.printStackTrace();
			
			return -1;
		}finally{
			hiber.closeSession();
		}
	}
	@SuppressWarnings("unchecked")
	public List<GuestSuggestComplainEntity> getAll() {
        List<GuestSuggestComplainEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT suggCom.* FROM guest_sugestcomplain suggCom ")
                    .addEntity("suggCom.", GuestSuggestComplainEntity.class);
            entities = query.list();
        } catch (Exception ex) {
             System.out.println("GuestSuggestComplainDAO Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
	@SuppressWarnings("unchecked")
	public List<GuestSuggestComplainEntity> getAllByType(int type) {
        List<GuestSuggestComplainEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT suggCom.* FROM guest_sugestcomplain suggCom WHERE type = : type")
                    .addEntity("suggCom.", GuestSuggestComplainEntity.class);
            query.setInteger("type", type);
            entities = query.list();
        } catch (Exception ex) {
             System.out.println("GuestSuggestComplainDAO Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
}
