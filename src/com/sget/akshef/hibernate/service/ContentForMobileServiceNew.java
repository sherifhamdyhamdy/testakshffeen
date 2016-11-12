package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import com.akshffeen.utils.Constants;
import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.Akshffeen;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.ContentForMobileDAO;
import com.sget.akshef.hibernate.dao.SpecialistDAO;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.DoctorSectionBranchCriteria;

/**
 * 
 * @author JDeeb
 * 
 */

public class ContentForMobileServiceNew
{	
	private ContentDetailsService contentDetailsService = new ContentDetailsService();

	private Utils utils = Utils.getInstance();
	SpecialistDAO specialistDAO = new SpecialistDAO();
	HibernateSession hiber;

	public ContentForMobileServiceNew() 
	{
		hiber = new HibernateSession();
	}

	
	public List<ContentDetailsBean> getContentDetails(int categoryId, String name, boolean sorting, String url, int start, int limit, int cat_type)
	{
		List<ContentDetailsBean> catBeanList = new ArrayList<ContentDetailsBean>();
		ContentDetailsBean bean;
		
		
//		List<ContentDetailsEntity> contents = getContentDetails_all(categoryId, name, sorting, start, limit);
		List<ContentDetailsEntity> contents = new ArrayList<ContentDetailsEntity>();
		
		int max_results = Constants.SPONSORED_LIMIT;
		
		if(utils.hasValue(name))
			contents.addAll(getContentDetails_sponsored_names(categoryId, name, sorting, start, max_results, null, cat_type));
		
		max_results = Constants.SPONSORED_LIMIT - getIds(contents).size();
		if(max_results>0 && utils.hasValue(name))
			contents.addAll(getContentDetails_sponsored_keywords(categoryId, name, sorting, start, max_results, getIds(contents), cat_type));	
		
		max_results = limit - getIds(contents).size();
		if(max_results>0)
			contents.addAll(getContentDetails_all(categoryId, name, sorting, start, max_results, getIds(contents), cat_type));
		
		
//		max_results = limit - getIds(contents).size();
//		if(max_results>0)
//			contents.addAll(getContentDetails_names(categoryId, name, sorting, start, max_results, getIds(contents)));
//		
//		max_results = limit - getIds(contents).size();
//		if(max_results>0 && utils.hasValue(name))
//			contents.addAll(getContentDetails_keywords(categoryId, name, sorting, start, max_results, getIds(contents)));	

		UsersBean usersBean = null;
		UnitBean unitBean = null;
		Set<SpecialistEntity> doctors = null;
		SpecialistEntity doctor = null;
		Akshffeen akshffeen = null;
		boolean has_logo = false;

		for (ContentDetailsEntity content : contents)
		{
			bean = new ContentDetailsBean();
			bean.setId(content.getId());
			
			bean.setTitleAr(content.getTitleAr());
			bean.setTitleEn(content.getTitleEn());
			
			bean.setContentEn(content.getContentEn());
			bean.setContentAr(content.getContentAr());
			bean.setSponsored(utils.hasValue(content.getSponsored())?content.getSponsored():0);
			
			
			if (content.getSmallImage() != null && !content.getSmallImage().equals(""))
			{
				bean.setSmallImage(url + DBConstants.CONTENT_IMAGES_UPLOADS + content.getSmallImage());
			}
			else
				bean.setSmallImage("");

			
			if (content.getLargeImage() != null && !content.getLargeImage().equals(""))
			{
				bean.setLargeImage(url + DBConstants.CONTENT_IMAGES_UPLOADS + content.getLargeImage());
			}
			else
				bean.setLargeImage("");

			has_logo = false;
			
			try
			{
				if(content.getUnit()!=null && content.getUnit().getId()!=null)
				{
					unitBean = new UnitBean();
					unitBean.setId(content.getUnit().getId());
					unitBean.setNameAr(content.getUnit().getNameAr());
					unitBean.setNameEn(content.getUnit().getNameEn());

					if(content.getUnit().getUnitlogo()!=null)
						unitBean.setUnitlogo(url + DBConstants.UNIT_IMAGES_UPLOADS + content.getUnit().getUnitlogo());					
					else
						unitBean.setUnitlogo("");	
					
					
					unitBean.setBranches(null);
					unitBean.setUnitGroupses(null);
					unitBean.setUnitHasInsuranceCompanies(null);

					bean.setUnit(unitBean);		
					bean.setSponsor_type(2);

					has_logo = true;
				}
			}
			catch(Exception e)
			{
				System.out.println("Exception content unit "+e.getMessage());
			}
			
			try
			{
				if(content.getUsers()!=null && content.getUsers().getId()!=null)
				{
					doctor = specialistDAO.getSpecialistByUserID(content.getUsers().getId());
					
					usersBean = new UsersBean();
					usersBean.setId(content.getUsers().getId());
					usersBean.setNameAr(content.getUsers().getNameAr());
					usersBean.setNameEn(content.getUsers().getNameEn());

					
					if(doctor.getImage()!=null)
						usersBean.setLogo(url + DBConstants.USERS_IMAGES_UPLOADS + doctor.getImage()+"");					
					else
						usersBean.setLogo("");					

					bean.setUsers(usersBean);	
					bean.setUnit(null);
					bean.setSponsor_type(3);

					has_logo = true;
				}
			}
			catch(Exception e)
			{
				System.out.println("Exception content unit "+e.getMessage());
			}
			
			if(!has_logo)
			{
				akshffeen = new Akshffeen(); 
				
				akshffeen.setName("Akshffeen");
				akshffeen.setLogo(url + DBConstants.CONTENT_IMAGES_UPLOADS + "logo.png");
				
				bean.setAkshffeen(akshffeen);
				bean.setUnit(null);
				bean.setSponsor_type(1);

			}
			
			bean.setUserFavoritieses(null);
			bean.setCommentses(null);


			contentDetailsService.getContentRating(bean);
			// bean.setId(ent.getId());
			// bean.setRating(ent.getRating());
			catBeanList.add(bean);
		}
		
		return catBeanList;
	}
	


	public List<ContentDetailsEntity> getContentDetails_sponsored_names(int categoryId, String name, boolean sorting, int start, int max_results, List<Integer> ignored_ids, int cat_type)
	{
		List<ContentDetailsEntity> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"SELECT distinct content_details.* \r\n" +
					"FROM content_details content_details \r\n" +
					"inner join content_category con_cat on (con_cat.id=content_details.content_category_id) \r\n "+
//					"WHERE con_cat.content_types_id = :categoryId  \r\n"+
					" WHERE "+(categoryId>0?"con_cat.id = :categoryId":"1=1")+"  \r\n"+

						
					"and content_details.sponsored is not null and content_details.sponsored=1 \r\n" + 
					"and content_details.sponsored_from is not null and content_details.sponsored_to is not null \r\n" + 
					"and content_details.sponsored_from<=CURDATE() and content_details.sponsored_to>=CURDATE() \r\n  " +
					
					"and (" +
					"		(content_details.offer_from is null and content_details.offer_to is null) \r\n" + 
					"			or \r\n"+
					"	 	(content_details.offer_from is not null and content_details.offer_to is not null and content_details.offer_from<=CURDATE() and content_details.offer_to>=CURDATE()) \r\n  " +
					") \r\n"+

					 "and (content_details.title_ar LIKE :search_text or content_details.title_en LIKE :search_text or content_details.content_ar LIKE :search_text or content_details.content_en LIKE :search_text) \r\n" +
					 
					"");

		if(utils.hasValue(ignored_ids))
			sql.append("and content_details.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
		if(cat_type>0)
			sql.append(" and con_cat.content_types_id = :cat_type \r\n");
		
		if (!sorting)
			sql.append( " order by content_rating asc  \r\n ");
		else
			sql.append( " order by content_rating desc  \r\n ");
		
		try
		{
			Query query = hiber.getSession()
					.createSQLQuery(sql.toString())
					.addEntity(ContentDetailsEntity.class)
					.setString("search_text", "%" + name + "%");;
			
			if(categoryId>0)
				query.setInteger("categoryId", categoryId);

			if(cat_type>0)
				query.setInteger("cat_type", cat_type);
			
			query.setFirstResult(start);
			query.setMaxResults(max_results);
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getContentDetails : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
		
	public List<ContentDetailsEntity> getContentDetails_sponsored_keywords(int categoryId, String name, boolean sorting, int start, int max_results, List<Integer> ignored_ids, int cat_type)
	{
		List<ContentDetailsEntity> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"SELECT distinct content_details.* \r\n" +
					"FROM content_details content_details \r\n" +
					"inner join content_category con_cat on (con_cat.id=content_details.content_category_id) \r\n "+
					
					" inner join content_has_keyword chk on chk.content_id = content_details.id \r\n"+
					
					" inner join keyword keyword on keyword.id = chk.keyword_id \r\n"+

//					" WHERE con_cat.content_types_id = :categoryId  \r\n"+
					" WHERE "+(categoryId>0?"con_cat.id = :categoryId":"1=1")+"  \r\n"+


					"and (" +
					"		(content_details.offer_from is null and content_details.offer_to is null) \r\n" + 
					"			or \r\n"+
					"	 	(content_details.offer_from is not null and content_details.offer_to is not null and content_details.offer_from<=CURDATE() and content_details.offer_to>=CURDATE()) \r\n  " +
					") \r\n"+
					
					
					
					" and keyword.name like :search_text \r\n  "+
					" and content_details.sponsored is not null and content_details.sponsored=1 \r\n" + 
					" and content_details.sponsored_from is not null and content_details.sponsored_to is not null \r\n" + 
					" and content_details.sponsored_from<=CURDATE() and content_details.sponsored_to>=CURDATE() \r\n  " );
		
		if(utils.hasValue(ignored_ids))
			sql.append("and content_details.id not in ("+utils.generateInt(ignored_ids)+") \r\n");

		if(cat_type>0)
			sql.append(" and con_cat.content_types_id = :cat_type \r\n");
		
		if (!sorting)
			sql.append( " order by content_rating asc  \r\n ");
		else
			sql.append( " order by content_rating desc  \r\n ");
		
		try
		{
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity(ContentDetailsEntity.class)
							.setString("search_text", "%" + name + "%");

			if(categoryId>0)
				query.setInteger("categoryId", categoryId);
			
			if(cat_type>0)
				query.setInteger("cat_type", cat_type);
			
			query.setFirstResult(start);
			query.setMaxResults(max_results);
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getContentDetails : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
	
	
	public List<ContentDetailsEntity> getContentDetails_all(int categoryId, String name, boolean sorting, int start, int max_results, List<Integer> ignored_ids, int cat_type)
	{
		List<ContentDetailsEntity> entities = null;
		try
		{
			StringBuilder sqlString = new StringBuilder(); 
			
			sqlString.append(
							"SELECT distinct content_details.* \r\n" +
							"FROM content_details content_details \r\n" +
							"inner join content_category con_cat on (con_cat.id=content_details.content_category_id) \r\n "+
							
							" left outer join content_has_keyword chk on chk.content_id = content_details.id \r\n"+
							
							" left outer join keyword keyword on keyword.id = chk.keyword_id \r\n"+
					
//							"WHERE con_cat.content_types_id = :categoryId  \r\n"+
							" WHERE "+(categoryId>0?"con_cat.id = :categoryId":"1=1")+"  \r\n"+

							"and (" +
							"		(content_details.offer_from is null and content_details.offer_to is null) \r\n" + 
							"			or \r\n"+
							"	 	(content_details.offer_from is not null and content_details.offer_to is not null and content_details.offer_from<=CURDATE() and content_details.offer_to>=CURDATE()) \r\n  " +
							") \r\n"+
					
							"");
			
			if (name != null && !name.equals(""))
			{
				sqlString.append( "and (title_ar LIKE '%" + name + "%' or title_en LIKE '%" + name + "%' or content_ar LIKE '%" + name + "%' or content_en LIKE '%" + name + "%' or keyword.name LIKE '%" + name + "%') \r\n");
			}
			
			if(utils.hasValue(ignored_ids))
			{
				sqlString.append(" and content_details.id not in ("+utils.generateInt(ignored_ids)+") \r\n");		
			}
			
			if(cat_type>0)
				sqlString.append(" and con_cat.content_types_id = :cat_type \r\n");
			
			if (!sorting)
				sqlString.append( " order by content_rating asc   LIMIT :start,:limit   \r\n ");
			else
				sqlString.append( " order by content_rating desc    LIMIT :start,:limit   \r\n ");
			
			System.out.println(sqlString);
			
			Query query = hiber.getSession().createSQLQuery(sqlString.toString())
							.addEntity("content_details.", ContentDetailsEntity.class)
							.setInteger("start", start)
							.setInteger("limit", max_results);
			
			if(categoryId>0)
				query.setInteger("categoryId", categoryId);
			
			if(cat_type>0)
				query.setInteger("cat_type", cat_type);
			
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
	

	
	
	
	
	
	
	public List<ContentDetailsEntity> getContentDetails_names(int categoryId, String name, boolean sorting, int start, int max_results, List<Integer> ignored_ids)
	{
		List<ContentDetailsEntity> entities = null;
		try
		{
			StringBuilder sqlString = new StringBuilder(); 
			
			sqlString.append(
							"SELECT content_details.* \r\n" +
							"FROM content_details content_details \r\n" +
							"inner join content_category con_cat on (con_cat.id=content_details.content_category_id) \r\n "+
//							"WHERE con_cat.content_types_id = :categoryId  \r\n"+
							" WHERE con_cat.id = :categoryId  \r\n"+

							"");
			
			if(utils.hasValue(ignored_ids))
				sqlString.append("and content_details.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
			
			if (name != null && !name.equals(""))
			{
				sqlString.append( "and (content_details.title_ar LIKE '%" + name + "%' or content_details.title_en LIKE '%" + name + "%' or content_details.content_ar LIKE '%" + name + "%' or content_details.content_en LIKE '%" + name + "%') \r\n");
			}
			
			if (!sorting)
				sqlString.append( " order by content_rating asc   LIMIT :start,:limit   \r\n ");
			else
				sqlString.append( " order by content_rating desc    LIMIT :start,:limit   \r\n ");
			
			System.out.println(sqlString);
			
			Query query = hiber.getSession().createSQLQuery(sqlString.toString())
							.addEntity("content_details.", ContentDetailsEntity.class)
							.setInteger("categoryId", categoryId)
							.setInteger("start", start)
							.setInteger("limit", max_results);
			
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
	
	public List<ContentDetailsEntity> getContentDetails_keywords(int categoryId, String name, boolean sorting, int start, int max_results, List<Integer> ignored_ids)
	{
		List<ContentDetailsEntity> entities = null;
		try
		{
			StringBuilder sqlString = new StringBuilder(); 
			
			sqlString.append(
							"SELECT content_details.* \r\n" +
							"FROM content_details content_details \r\n" +
							"inner join content_category con_cat on (con_cat.id=content_details.content_category_id) \r\n "+
							
							" inner join content_has_keyword chk on chk.content_id = content_details.id \r\n"+
							
							" inner join keyword keyword on keyword.id = chk.keyword_id \r\n"+
					
//							"WHERE con_cat.content_types_id = :categoryId  \r\n"+
							" WHERE con_cat.id = :categoryId  \r\n"+

							"");
			
			if(utils.hasValue(ignored_ids))
				sqlString.append("and content_details.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
			
			if (name != null && !name.equals(""))
			{
				sqlString.append( "and keyword.name like '%" + name + "%' \r\n");
			}
			
			if (!sorting)
				sqlString.append( " order by content_rating asc   LIMIT :start,:limit   \r\n ");
			else
				sqlString.append( " order by content_rating desc    LIMIT :start,:limit   \r\n ");
			
			System.out.println(sqlString);
			
			Query query = hiber.getSession().createSQLQuery(sqlString.toString())
							.addEntity("content_details.", ContentDetailsEntity.class)
							.setInteger("categoryId", categoryId)
							.setInteger("start", start)
							.setInteger("limit", max_results);
			
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
	
	
	public List<ContentDetailsEntity> getContentDetails_all_original(int categoryId, String name, boolean sorting, int start, int limit)
	{
		List<ContentDetailsEntity> entities = null;
		try
		{
			StringBuilder sqlString = new StringBuilder(); 
			
			sqlString.append(
							"SELECT content_details.* \r\n" +
							"FROM content_details content_details \r\n" +
							"inner join content_category con_cat on (con_cat.id=content_details.content_category_id) \r\n "+
//							"WHERE con_cat.content_types_id = :categoryId  \r\n"+
							" WHERE con_cat.id = :categoryId  \r\n"+

							"");
			
			if (name != null && !name.equals(""))
			{
				sqlString.append( "and (title_ar LIKE '%" + name + "%' or title_en LIKE '%" + name + "%' or content_ar LIKE '%" + name + "%' or content_en LIKE '%" + name + "%') \r\n");
			}
			
			if (!sorting)
				sqlString.append( " order by content_rating asc   LIMIT :start,:limit   \r\n ");
			else
				sqlString.append( " order by content_rating desc    LIMIT :start,:limit   \r\n ");
			
			System.out.println(sqlString);
			
			Query query = hiber.getSession().createSQLQuery(sqlString.toString())
							.addEntity("content_details.", ContentDetailsEntity.class)
							.setInteger("categoryId", categoryId)
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
	

	
	
	public List<Integer> getIds(List<ContentDetailsEntity> results)
	{
		List<Integer> ids = new ArrayList<Integer>();
		
		if (results != null)
		{
			for (ContentDetailsEntity inst : results)
			{				
				ids.add(inst.getId());
			}
		}
		
		return ids;
	}
	
}
