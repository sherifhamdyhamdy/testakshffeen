package com.sget.akshef.hibernate.dao;

import java.util.List;

import org.hibernate.Query;

import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.ContentCategoryEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb Content DAO For Mobile Services
 */
public class ContentForMobileDAO {

	HibernateSession hiber;

	public ContentForMobileDAO() {
		hiber = new HibernateSession();
	}

	// get Content News Categories
	public List<ContentCategoryEntity> getNewsCategory() {
		List<ContentCategoryEntity> entities = null;
		try {
			Query query = hiber.getSession().createSQLQuery("SELECT content_category.* FROM content_category content_category WHERE content_types_id = :contentType").addEntity("content_category.", ContentCategoryEntity.class);
			query.setInteger("contentType", DBConstants.CONTENT_TYPE_NEWS);

			entities = query.list();

		} catch (Exception ex) {
			 System.out.println("Get Ex ContentForMobileDAO : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return entities;
	}

	// get Content Tips Categories
	public List<ContentCategoryEntity> getTipsCategory() {
		List<ContentCategoryEntity> entities = null;
		try {
			Query query = hiber.getSession().createSQLQuery("SELECT content_category.* FROM content_category content_category WHERE content_types_id = :contentType").addEntity("content_category.", ContentCategoryEntity.class);
			query.setInteger("contentType", DBConstants.CONTENT_TYPE_TIPS);

			entities = query.list();
		} catch (Exception ex) {
			 System.out.println("Get Ex ContentForMobileDAO : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return entities;
	}

	// get Content Offers Categories
	public List<ContentCategoryEntity> getOffersCategory() {
		List<ContentCategoryEntity> entities = null;
		try {
			Query query = hiber.getSession().createSQLQuery("SELECT content_category.* FROM content_category content_category WHERE content_types_id = :contentType").addEntity("content_category.", ContentCategoryEntity.class);
			query.setInteger("contentType", DBConstants.CONTENT_TYPE_OFFERS);

			entities = query.list();
		} catch (Exception ex) {
			 System.out.println("Get Ex ContentForMobileDAO : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return entities;
	}
	// get Content Offers Categories
	public List<ContentCategoryEntity> getContTypeCategories(int contType) {
		List<ContentCategoryEntity> entities = null;
		try {
			Query query = hiber.getSession().createSQLQuery("SELECT content_category.* FROM content_category content_category WHERE content_types_id = :contentType").addEntity("content_category.", ContentCategoryEntity.class);
			query.setInteger("contentType", contType);

			entities = query.list();
		} catch (Exception ex) {
			 System.out.println("Get Ex ContentForMobileDAO : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return entities;
	}


	
	// get Content News Categories
	public List<ContentDetailsEntity> getContentDetails(int categoryId, int start, int limit, boolean rating)
	{
		List<ContentDetailsEntity> entities = null;
		try
		{
			String sql = 
						" SELECT content_details.* \r\n" +
						"FROM content_details content_details \r\n" + 
						"inner join content_category con_cat on (con_cat.id=content_details.content_category_id) \r\n "+
						"WHERE con_cat.content_types_id = :contentcat  \r\n"+
						"LIMIT :start,:limit   \r\n";
			
			if (rating)
				sql += " order by content_rating desc \r\n";
			
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("content_details.", ContentDetailsEntity.class)
							.setInteger("contentcat", categoryId)
							.setInteger("start", start)
							.setInteger("limit", limit);
			
			System.out.println(sql);

			entities = query.list();
		}
		catch (Exception ex)
		{
			System.out.println("Get Ex ContentForMobileDAO : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		System.out.println("entities gotten  content ----   " + entities);
		return entities;
	}
	
	public List<ContentDetailsEntity> getAllDetailsByCatId(int categoryId, String name, boolean sorting, int start, int limit)
	{
		List<ContentDetailsEntity> entities = null;
		try
		{
			String sqlString = 
								"SELECT content_details.* \r\n" +
								"FROM content_details content_details \r\n" +
								"inner join content_category con_cat on (con_cat.id=content_details.content_category_id) \r\n "+
								"WHERE con_cat.content_types_id = :contentcat  \r\n"+
								"";
			
			if (name != null && !name.equals(""))
			{
				sqlString += "and (title_ar LIKE '%" + name + "%' or title_en LIKE '%" + name + "%' or content_ar LIKE '%" + name + "%' or content_en LIKE '%" + name + "%') \r\n";
			}
			
			if (!sorting)
				sqlString += " order by content_rating asc   LIMIT :start,:limit   \r\n ";
			else
				sqlString += " order by content_rating desc    LIMIT :start,:limit   \r\n ";
			
			System.out.println(sqlString);
			
			Query query = hiber.getSession().createSQLQuery(sqlString)
							.addEntity("content_details.", ContentDetailsEntity.class)
							.setInteger("catId", categoryId)
							.setInteger("start", start)
							.setInteger("limit", limit);
			
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
	

	
}
