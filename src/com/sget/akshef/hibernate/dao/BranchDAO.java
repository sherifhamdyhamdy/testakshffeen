package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.Branch_scheduleEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.MessagesEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UserRateBranchEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;
import com.sget.akshf.searchcriteria.BranchesCriteria;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

/**
 * @author JDeeb Branch DAO
 */
public class BranchDAO
{
	// Hibernate Session Class
	HibernateSession hiber;
	
	private Utils utils = Utils.getInstance();
	
	
	
	public BranchDAO()
	{
		hiber = new HibernateSession();
	}
	
	
	
	public List<CommentsEntity> getAllCommentsBybranch(int branchId)
	{
		List<CommentsEntity> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT comments.*,users.* FROM comments comments,users  users where " + " comments.users_id=users.id" + " and branch_id=:branchId ").addEntity("comments.", CommentsEntity.class).addEntity("users.", UsersEntity.class).setInteger("branchId", branchId);
			entities = query.list();
			
		}
		catch (Exception ex)
		{
			System.out.println("BranchDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	private List<BranchEntity> getBranchsByLatLngByCategoryById(int categoryId, double lat, double lng, int userid)
	{
		List<BranchEntity> entities = null;
		String sqlString = "SELECT *, 4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(dest.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 ) " + "* COS(abs(dest.lat) *pi()/180) " + " * POWER(SIN((:lng - dest.lng) *pi()/180 / 2), 2) )) " + "as distance FROM branch dest,unit unit,category category,unit_has_insurance_company,company,company_users  where dest.unit_unit_id=unit.id " + " and unit.category_id=category.id   and unit.id=unit_has_insurance_company.unit_id and "
				+ " unit_has_insurance_company.insurance_company_id=company.insurance_company_id and company_users.user_id=:userid and category.id=:id  having distance < 5  ORDER BY distance limit 10";
		
		try
		{
			Query query = hiber.getSession().createSQLQuery(sqlString).addEntity("dest", BranchEntity.class).setInteger("id", categoryId).setDouble("lat", lat).setDouble("lng", lng).setInteger("userid", userid);
			
			entities = query.list();
			// System.out.println("entities gotten  "+entities.get(0).getAddress());
		}
		catch (HibernateException ex)
		{
			System.out.println("BranchDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
		
	}
	
	
	
	public List<BranchEntity> getAllBranchsByFiltersById(Map filter, double lat, double lng, int user_id)
	{
		
		List<BranchEntity> entities = null;
		if (filter.get("category") != null && !filter.get("category").equals("") && (filter.get("section") == null || filter.get("section").equals("")))
		{
			int categoryid = ((Integer) filter.get("category")).intValue();
			entities = getBranchsByLatLngByCategoryById(categoryid, lat, lng, user_id);
		}
		
		else if (filter.get("section") != null && !filter.get("section").equals("") && (filter.get("category") != null && !filter.get("category").equals("")))
		{
			int sectionid = ((Integer) filter.get("section")).intValue();
			int categoryid = ((Integer) filter.get("category")).intValue();
			entities = getBranchsByLatLngBySectionByUserId(sectionid, categoryid, lat, lng, user_id);
		}
		else if (filter.get("category") == null && filter.get("section") == null)
		{
			entities = getAllBranchsByLatLngById(lat, lng, user_id);
		}
		
		return entities;
	}
	
	
	
	private List<BranchEntity> getAllBranchsByLatLngById(double lat, double lng, int userid)
	{
		
		List<BranchEntity> entities = null;
		String sqlString = "SELECT dest.*, 4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(dest.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 ) * COS(abs(dest.lat) *pi()/180) " + " * POWER(SIN((:lng - dest.lng) *pi()/180 / 2), 2) )) as distance " + " FROM branch dest,unit unit,category category,unit_has_insurance_company,company,company_users " + " where dest.unit_unit_id=unit.id and unit.category_id=category.id and unit.id=unit_has_insurance_company.unit_id and "
				+ " unit_has_insurance_company.insurance_company_id=company.insurance_company_id and company_users.user_id=:id" + " and category.id=1 having distance < 5  ORDER BY distance limit 10";
		
		try
		{
			Query query = hiber.getSession().createSQLQuery(sqlString).addEntity("dest", BranchEntity.class).setDouble("lat", lat).setDouble("lng", lng).setInteger("id", userid);
			// .addEntity("unit", UnitEntity.class).addEntity("category",
			// CategoryEntity.class).setParameter("id", 1);
			entities = query.list();
			// System.out.println("entities gotten  "+entities.get(0).getAddress());
		}
		catch (HibernateException ex)
		{
			System.out.println("BranchDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
		
	}
	
	
	
	private List<BranchEntity> getBranchsByLatLngBySectionByUserId(int sectionId, int categoryId, double lat, double lng, int userId)
	{
		
		System.out.println("sectionId  " + sectionId);
		System.out.println("categoryId  " + sectionId);
		List<BranchEntity> entities = null;
		//
		//
		
		String sqlString = "SELECT *, 4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(dest.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 ) " + "* COS(abs(dest.lat) *pi()/180) * POWER(SIN((:lng - dest.lng) *pi()/180 / 2), 2) )) " + "as distance FROM branch dest,unit unit,category category ,subcategory subcategory," + " subcategory_has_sections_has_branch subcategory_has_sections_has_branch,subcategory_has_sections subcategory_has_sections,"
				+ " sections sections,unit_has_insurance_company,company,company_users  " + " where dest.unit_unit_id=unit.id " + " and unit.category_id=category.id and subcategory_has_sections_has_branch.branch_id =dest.id " + " and subcategory_has_sections_has_branch.subcategorysection_id=subcategory_has_sections.id" + " and subcategory_has_sections.subcategory_subcategory_id=subcategory.id"
				+ " and subcategory_has_sections.sections_section_id=sections.id and unit.id=unit_has_insurance_company.unit_id and " + "  unit_has_insurance_company.insurance_company_id=company.insurance_company_id and company_users.user_id=:userid" + " and sections.id=:sectionid and category.id=:categoryId     having distance < 5  ORDER BY distance limit 10";
		
		try
		{
			Query query = hiber.getSession().createSQLQuery(sqlString).addEntity("dest", BranchEntity.class).setDouble("lat", lat).setInteger("userid", userId).setDouble("lng", lng).setInteger("sectionid", sectionId).setInteger("categoryId", categoryId);
			// .addEntity("unit", UnitEntity.class).addEntity("category",
			// CategoryEntity.class).setParameter("id", 1);
			entities = query.list();
			//
		}
		catch (HibernateException ex)
		{
			System.out.println("BranchDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		// System.out.println("entities : " + entities);
		return entities;
	}
	
	
	
	public void insert(BranchEntity entity)
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
	
	
	
	public boolean update(BranchEntity entity)
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
			System.out.println("BranchDAO : Inesrt Ex : " + ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	public boolean delete(BranchEntity entity)
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
			System.out.println("BranchDAO : Delete Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	public List<BranchEntity> getAll()
	{
		List<BranchEntity> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT branch.* FROM branch branch ").addEntity("branch.", BranchEntity.class);
			entities = query.list();
			/*
			 * for (BranchEntity entity : entities) {
			 * System.out.println("BranchDAO : Get Ex   entity.getUnit().getId(): "
			 * + entity.getUnit().getId()); }
			 */
			
		}
		catch (Exception ex)
		{
			System.out.println("BranchDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	public List<BranchEntity> getAllByUnit(int id)
	{
		List<BranchEntity> entities = null;
		try
		{
			
			String hql = "Select DISTINCT c  from branch c where c.unit = :name1 ";
			Query query = hiber.getSession().createQuery(hql);
			query.setInteger("name1", id);
			System.out.println("query.toString() ::  " + query.toString());
			
			entities = query.list();
			
			/*
			 * for (BranchEntity entity : entities) {
			 * System.out.println("Get Ex   entity.getUnit().getId(): " +
			 * entity.getUnit().getId()); }
			 */
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("BranchDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	public BranchEntity getById(int id)
	{
		if (id < 1)
			return null;
		
		BranchEntity entity = null;
		try
		{
			entity = (BranchEntity) hiber.getSession().get(BranchEntity.class, id);
			// entity = (BranchEntity)
			// hiber.getSession().createSQLQuery("SELECT * FROM branch WHERE id = ?")
			// .addEntity(BranchEntity.class).setInteger(0, id).uniqueResult();
		}
		catch (HibernateException ex)
		{
			System.out.println("BranchDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entity;
	}
	
	
	
	public List<BranchEntity> getAllBranchsByFilters(Map filter, double lat, double lng)
	{
		
		List<BranchEntity> entities = null;
		if (filter.get("category") != null && !filter.get("category").equals("") && (filter.get("section") == null || filter.get("section").equals("")))
		{
			int categoryid = ((Integer) filter.get("category")).intValue();
			entities = getBranchsByLatLngByCategory(categoryid, lat, lng);
		}
		
		else if (filter.get("section") != null && !filter.get("section").equals("") && (filter.get("category") != null && !filter.get("category").equals("")))
		{
			int sectionid = ((Integer) filter.get("section")).intValue();
			int categoryid = ((Integer) filter.get("category")).intValue();
			entities = getBranchsByLatLngBySection(sectionid, categoryid, lat, lng);
		}
		else if (filter.get("category") == null && filter.get("section") == null)
		{
			entities = getAllBranchsByLatLng(lat, lng);
		}
		
		return entities;
	}
	
	
	
	private List<BranchEntity> getAllBranchsByLatLng(double lat, double lng)
	{
		
		List<BranchEntity> entities = null;
		String sqlString = " SELECT *, 4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(dest.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 ) * COS(abs(dest.lat) *pi()/180) " + " * POWER(SIN((:lng - dest.lng) *pi()/180 / 2), 2) )) as distance FROM branch dest having distance < 5 ORDER BY distance limit 10";
		
		try
		{
			Query query = hiber.getSession().createSQLQuery(sqlString).addEntity("dest", BranchEntity.class).setDouble("lat", lat).setDouble("lng", lng);
			// .addEntity("unit", UnitEntity.class).addEntity("category",
			// CategoryEntity.class).setParameter("id", 1);
			entities = query.list();
			// System.out.println("entities gotten  "+entities.get(0).getAddress());
		}
		catch (HibernateException ex)
		{
			System.out.println("BranchDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
		
	}
	
	
	
	private List<BranchEntity> getBranchsByLatLngBySection(int sectionId, int categoryId, double lat, double lng)
	{
		
		System.out.println("sectionId  " + sectionId);
		System.out.println("categoryId  " + sectionId);
		List<BranchEntity> entities = null;
		//
		//
		
		String sqlString = "SELECT *, 4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(dest.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 ) " + "* COS(abs(dest.lat) *pi()/180) * POWER(SIN((:lng - dest.lng) *pi()/180 / 2), 2) )) " + "as distance FROM branch dest,unit unit,category category ,subcategory subcategory," + " subcategory_has_sections_has_branch subcategory_has_sections_has_branch,subcategory_has_sections subcategory_has_sections," + " sections sections "
				+ " where dest.unit_unit_id=unit.id " + " and unit.category_id=category.id and subcategory_has_sections_has_branch.branch_id =dest.id " + " and subcategory_has_sections_has_branch.subcategorysection_id=subcategory_has_sections.id" + " and subcategory_has_sections.subcategory_subcategory_id=subcategory.id" + " and subcategory_has_sections.sections_section_id=sections.id" + " and sections.id=:sectionid and category.id=:categoryId having distance < 5  ORDER BY distance limit 10";
		
		try
		{
			Query query = hiber.getSession().createSQLQuery(sqlString).addEntity("dest", BranchEntity.class).setDouble("lat", lat).setDouble("lng", lng).setInteger("sectionid", sectionId).setInteger("categoryId", categoryId);
			// .addEntity("unit", UnitEntity.class).addEntity("category",
			// CategoryEntity.class).setParameter("id", 1);
			entities = query.list();
			//
		}
		catch (HibernateException ex)
		{
			System.out.println("BranchDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		// System.out.println("entities : " + entities);
		return entities;
	}
	
	
	
	private List<BranchEntity> getBranchsByLatLngByCategory(int categoryId, double lat, double lng)
	{
		List<BranchEntity> entities = null;
		String sqlString = "SELECT *, 4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(dest.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 ) " + "* COS(abs(dest.lat) *pi()/180) " + " * POWER(SIN((:lng - dest.lng) *pi()/180 / 2), 2) )) " + "as distance FROM branch dest,unit unit,category category where dest.unit_unit_id=unit.id " + "and unit.category_id=category.id and category.id=:id having distance < 5  ORDER BY distance limit 10";
		
		try
		{
			Query query = hiber.getSession().createSQLQuery(sqlString).addEntity("dest", BranchEntity.class).setInteger("id", categoryId).setDouble("lat", lat).setDouble("lng", lng);
			
			entities = query.list();
			// System.out.println("entities gotten  "+entities.get(0).getAddress());
		}
		catch (HibernateException ex)
		{
			System.out.println("BranchDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
		
	}
	
	
	

	
	
	public List<Object[]> getBranchesForMobile(SearchDoctorsCriteria searchDoctorsCriteria)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		String sql = " SELECT distinct b.*, u.category_id as category_id \r\n ";
		
		if (searchDoctorsCriteria.getLatitude() > 0 && searchDoctorsCriteria.getLongitude() > 0)
			sql += ",4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(b.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 )\r\n " + " * COS(abs(b.lat) *pi()/180) * POWER(SIN((:lng - b.lng) *pi()/180 / 2), 2) )) as distance ";
		
		sql += " FROM branch b \r\n " +
		
		" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " + " inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
		
		" INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id \r\n " + " INNER JOIN users users ON users.id = spec.users_id \r\n " +
		// " INNER JOIN users_groups grou ON spec.users_id = grou.users_id \r\n "+
				" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n " + " INNER JOIN distric dist ON dist.id = b.distric_id \r\n " + " INNER JOIN city cit ON cit.id = dist.city_id \r\n " +
				
				
				" join unit u on u.id = b.unit_unit_id \r\n ";
		
		// String pre_cond =
		// " WHERE groups_id = :groupId and b.lat<>0 and b.lng<>0 \r\n ";
		String pre_cond = " WHERE b.lat<>0 and b.lng<>0 \r\n ";
		
		
		if (searchDoctorsCriteria.getSectionId() > 0)
			pre_cond += "  AND sections_id = :sectionId \r\n ";
		if (searchDoctorsCriteria.getCountryId() > 0)
			pre_cond += "  AND cit.country_id = :countryId \r\n ";
		if (searchDoctorsCriteria.getCityId() > 0)
			pre_cond += " AND cit.id = :cityId \r\n ";
		if (searchDoctorsCriteria.getDistrictId() > 0)
			pre_cond += "  AND dist.id = :districId \r\n ";
		if (searchDoctorsCriteria.getDegree() > 0)
			pre_cond += " AND spec.degree_id = :degreeId \r\n ";
		if (searchDoctorsCriteria.getGender() == DBConstants.USER_GENDER_FEMALE || searchDoctorsCriteria.getGender() == DBConstants.USER_GENDER_MALE)
			pre_cond += " AND users.gender = :genderId \r\n ";
		if (searchDoctorsCriteria.getDoctorName() != null && !searchDoctorsCriteria.getDoctorName().trim().equalsIgnoreCase(""))
			pre_cond += " AND ( users.firstname like :doctorName or users.lastname like :doctorName ) \r\n ";
		if (searchDoctorsCriteria.isOrderbyrate())
			pre_cond += " order by rate desc       \r\n ";
		
		String sql_end = " ";
		
		if (searchDoctorsCriteria.getLatitude() > 0 && searchDoctorsCriteria.getLongitude() > 0)
			sql_end = " having distance < 5 \r\n ";
		
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n ";
		String sql_insurane_cond = " ins.insurance_company_id = :insurancyCompany \r\n ";
		
		try
		{
			// New For InsuranceCompany
			if (searchDoctorsCriteria.getInsuranceCompany_id() != 0)
			{
				sql += sql_insurane;
				if (pre_cond != null && pre_cond.trim().equalsIgnoreCase(""))
					pre_cond += " and ";
				else
					pre_cond += " AND ";
				pre_cond += sql_insurane_cond;
			}
			
			sql += pre_cond + sql_end;
			
			SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity("b", BranchEntity.class).addScalar("category_id");
			
			Query query = null;
			if (searchDoctorsCriteria.getLatitude() > 0 && searchDoctorsCriteria.getLongitude() > 0)
				query = sqlQuery.addScalar("distance");
			
			query = sqlQuery;
			
			System.out.println("SQL : " + sql);
			
			if (searchDoctorsCriteria.getLatitude() > 0 && searchDoctorsCriteria.getLongitude() > 0)
			{
				query.setDouble("lat", searchDoctorsCriteria.getLatitude());
				query.setDouble("lng", searchDoctorsCriteria.getLongitude());
			}
			
			if (searchDoctorsCriteria.getInsuranceCompany_id() != 0)
				query.setInteger("insurancyCompany", searchDoctorsCriteria.getInsuranceCompany_id());
			
			
			// Set Parameters
			// query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (searchDoctorsCriteria.getSectionId() > 0)
				query.setInteger("sectionId", searchDoctorsCriteria.getSectionId());
			if (searchDoctorsCriteria.getCountryId() > 0)
				query.setInteger("countryId", searchDoctorsCriteria.getCountryId());
			if (searchDoctorsCriteria.getCityId() > 0)
				query.setInteger("cityId", searchDoctorsCriteria.getCityId());
			if (searchDoctorsCriteria.getDistrictId() > 0)
				query.setInteger("districId", searchDoctorsCriteria.getDistrictId());
			if (searchDoctorsCriteria.getDegree() > 0)
				query.setInteger("degreeId", searchDoctorsCriteria.getDegree());
			if (searchDoctorsCriteria.getGender() == DBConstants.USER_GENDER_FEMALE || searchDoctorsCriteria.getGender() == DBConstants.USER_GENDER_MALE)
				query.setInteger("genderId", searchDoctorsCriteria.getGender());
			if (searchDoctorsCriteria.getDoctorName() != null && !searchDoctorsCriteria.getDoctorName().trim().equalsIgnoreCase(""))
				query.setString("doctorName", searchDoctorsCriteria.getDoctorName());
			
			query.setFirstResult(searchDoctorsCriteria.getStart());
			query.setMaxResults(searchDoctorsCriteria.getLimit());
			
			results = query.list();
			// entities = query.list();
			hiber.getSession().getTransaction().commit();
			// System.out.println("  " + entities.size());
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getBranchesForMobile : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
	
	
	
	public List<Object[]> getBranchesForWeb(BranchesCriteria criteria)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		String sql = "SELECT distinct b.*, u.category_id as category_id ,4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(b.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 )" + " * COS(abs(b.lat) *pi()/180) * POWER(SIN((:lng - b.lng) *pi()/180 / 2), 2) )) as distance FROM branch b join unit u on u.id = b.unit_unit_id";
		// String sql_end =
		// " having distance < 5 ORDER BY distance limit :start , :limit " ;
		String sql_end = " having distance < 5 ";
		String sql_section = " inner join subcategory_has_sections_has_branch s on b.id = s.branch_id inner join subcategory_has_sections sub on sub.id = s.subcategorysection_id ";
		String sql_section_cond = " sub.sections_section_id = :sect_id ";
		// String sql_category = " join unit u on u.id = b.unit_unit_id ";
		String sql_category_cond = " u.category_id = :cat_id ";
		String pre_cond = "";
		String orderbyDist = " ORDER BY distance ";
		String orderbyRate = " ORDER BY branch_rating desc ";
		String rateJoinQuery = " LEFT OUTER JOIN user_rate_branch br on b.id = br.branch_id ";
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id ";
		String sql_insurane_cond = " ins.insurance_company_id = :insurancyCompany";
		
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				pre_cond += " WHERE " + sql_category_cond;
			}
			if (criteria.getSection_id() != 0)
			{
				sql += sql_section;
				if (pre_cond != null && pre_cond.trim().equalsIgnoreCase(""))
					pre_cond += " WHERE ";
				else
					pre_cond += " AND ";
				pre_cond += sql_section_cond;
			}
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
			{
				if (pre_cond != null && pre_cond.trim().equalsIgnoreCase(""))
					pre_cond += " WHERE ";
				else
					pre_cond += " AND ";
				pre_cond += " ( b.name_ar like :keyword or  b.name_en like :keyword ) ";
			}
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany() != 0)
			{
				sql += sql_insurane;
				if (pre_cond != null && pre_cond.trim().equalsIgnoreCase(""))
					pre_cond += " WHERE ";
				else
					pre_cond += " AND ";
				pre_cond += sql_insurane_cond;
			}
			// Rating
			if (criteria.isRating())
			{
				// sql += rateJoinQuery;
				sql_end += orderbyRate;
			}
			else
				sql_end += orderbyDist;
			
			sql += pre_cond + sql_end;
			Query query = hiber.getSession().createSQLQuery(sql).addEntity("b", BranchEntity.class).addScalar("distance").addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			query.setDouble("lat", criteria.getLatitude());
			query.setDouble("lng", criteria.getLongitude());
			// query.setInteger("start", criteria.getStart());
			// query.setInteger("limit", criteria.getLimit());
			
			if (criteria.getCat_id() != 0)
				query.setInteger("cat_id", criteria.getCat_id());
			if (criteria.getSection_id() != 0)
				query.setInteger("sect_id", criteria.getSection_id());
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
				query.setString("keyword", "%" + criteria.getSearch_text() + "%");
			// New For Insurance Company
			if (criteria.getInsuranceCompany() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(criteria.getLimit());
			
			results = query.list();
			// entities = query.list();
			hiber.getSession().getTransaction().commit();
			// System.out.println("  " + entities.size());
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getBranchesForMobile : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
	
	
	
	// get Rating for Branch
	public List<UserRateBranchEntity> getBranchRating(int branchId)
	{
		List<UserRateBranchEntity> entities = null;
		if (branchId > 0)
		{
			try
			{
				String sql = "SELECT ra.* FROM user_rate_branch ra WHERE branch_id = :branid";
				Query query = hiber.getSession().createSQLQuery(sql).addEntity(UserRateBranchEntity.class);
				query.setInteger("branid", branchId);
				entities = query.list();
				
			}
			catch (Exception e)
			{
				System.out.println("BranchDAO : Ex in getBranchRating : " + e.getMessage());
			}
			finally
			{
				hiber.closeSession();
			}
		}
		
		return entities;
	}
	
	public List<Branch_scheduleEntity> getBranchSchedules(int branchId)
	{
		List<Branch_scheduleEntity> list = null;
		try
		{
			list = hiber.getSession()
						.createCriteria(Branch_scheduleEntity.class)
						.add(Restrictions.eq("this.branch.id", branchId))
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
	
	public List<SubcategoryEntity> getSubcategoriesByBranch(int branchId)
	{
		List<SubcategoryEntity> list = null;
		try
		{
			System.out.println("BranchDAO.getSubcategoriesByBranch()");
			
			list = hiber.getSession()
						.createCriteria(SubcategoryEntity.class)
						.createCriteria("this.subcategoryHasSectionsSet", "subcategoryHasSectionsSet")
						.createCriteria("subcategoryHasSectionsSet.subcategoryHasSectionsHasBranchSet", "subcategoryHasSectionsHasBranchSet")
						.createCriteria("subcategoryHasSectionsHasBranchSet.branch", "branch")
						.add(Restrictions.eq("branch.id", branchId))
						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.list();
			
			System.out.println("BranchDAO.getSubcategoriesByBranch()");
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
	
	
	// Get speciliast messages
	public List<MessagesEntity> getBranchMessages(int branchMangId)
	{
		List<MessagesEntity> entities = null;
		try
		{
			String sql = " select m.* from messages m,branch b where b.users_id=m.to_user and m.to_user =:branchMangId";
			
			// String
			// sql="SELECT bran.* from specialist_has_branch spec ,schedule_has_days sced,branch bran "
			// +
			// "where sced.specialist_has_branch_id=spec.id " +
			// "and spec.branch_id=bran.id " +
			// "and spec.specialist_id=:doctorId " +
			// "and sced.days_id=:dayId";
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(MessagesEntity.class);
			query.setInteger("branchMangId", branchMangId);
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getBranchMessages : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	// JDeeb
	/**
	 * Search for branches by advanced criteria
	 * 
	 * @param AdvancedSearchCriteria
	 * @return List of Branches
	 */
	
	public List<Object[]> advancedSearchForBranchesWithoutDimensions(AdvancedSearchCriteria criteria)
	{
		
		if (criteria == null)
			return null;
		List<Object[]> entities = null;
		try
		{
			String sql = " SELECT DISTINCT branch.*,branch.distric_id  distric_id ";
			String bodySQL = " FROM branch branch ";
			// +
			// "	inner join subcategory_has_sections_has_branch subsect on branch.id = subsect.branch_id "
			// +
			// " inner join subcategory_has_sections sect on subsect.subcategorysection_id = sect.id inner join unit un on un.id = branch.unit_unit_id "
			// +
			// " inner join distric dist on dist.id = branch.distric_id inner join city on city.id = dist.city_id "
			// +
			// " inner join specialist_has_branch specbran on specbran.branch_id = branch.id inner join specialist spe on spe.id = specbran.specialist_id "
			// +
			// " inner join schedule_has_days days on days.specialist_has_branch_id = specbran.id inner join schedule sch on sch.id = days.schedule_id WHERE branch.active = 1 "
			// ;
			
			
			String condition = " AND country.id=1 ";
			bodySQL += " inner join distric on distric_id =branch.distric_id  inner join  city on distric.city_id=city.id inner join  country on country.id=city.country_id";
			
			// category
			if (criteria.getCat_id() > 0)
			{
				bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
				condition += " AND un.category_id = " + criteria.getCat_id();
			}
			
			//
			if (criteria.getSubcat_id() > 0)
			{
				
				bodySQL += "  inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + " inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				
				condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id();
				
			}
			
			if (criteria.getSection_id() > 0)
			{
				
				if (criteria.getSubcat_id() == 0)
				{
					
					bodySQL += "  inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + " inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				}
				
				condition += " AND subcategory_has_sections.sections_section_id = " + criteria.getSection_id();
				
				
			}
			
			
			if (criteria.getKeyword() != null && !criteria.getKeyword().trim().equals(""))
				condition += " AND (branch.name_en LIKE '%" + criteria.getKeyword() + "%'" + " OR branch.name_ar  LIKE '%" + criteria.getKeyword() + "%')";
			
			if (criteria.getDistrict_id() != 0 || criteria.getCity_id() != 0 || criteria.getCountry_id() != 0)
			{
				
				sql += ", distric.name_ar distric_name ";
			}
			
			
			if (criteria.getDistrict_id() > 0)
			{
				condition += " AND distric.id = " + criteria.getDistrict_id();
				
			}
			
			if (criteria.getCity_id() > 0)
			{
				
				condition += " AND city.id = " + criteria.getCity_id();
			}
			
			// if(criteria.getCountry_id() > 0)
			// {
			//
			// condition += " AND country.id = " + criteria.getCountry_id() ;
			// }
			
			
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				bodySQL += "  inner join specialist_has_branch on specialist_has_branch.branch_id" + " inner join specialist on specialist_has_branch.specialist_id " + " inner join users on specialist.users_id=users.id";
				sql += ",specialist.id specId ,specialist.name specName ";
				
				if (criteria.getGender() > 0)
				{
					condition += " AND users.gender = " + criteria.getGender();
					
				}
				
				if (criteria.getDegree_id() > 0)
				{
					bodySQL += " inner join degree on specialist.degree_id=degree.id";
					condition += " AND users.gender = " + criteria.getDegree_id();
					
				}
				
				if (criteria.getName() != null && !criteria.getName().trim().equals(""))
					condition += " AND specialist.name LIKE '%" + criteria.getName() + "%'";
				
			}
			
			
			sql += bodySQL + condition;
			System.out.println("SQL : " + sql);
			Query query;
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				if (criteria.getDistrict_id() != 0 || criteria.getCity_id() != 0 || criteria.getCountry_id() != 0)
				{
					query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class).addScalar("distric_id").addScalar("specId").addScalar("specName").addScalar("distric_name");
				}
				else
				{
					query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class).addScalar("distric_id").addScalar("specId").addScalar("specName");
				}
				
			}
			else
			{
				
				
				if (criteria.getDistrict_id() != 0 || criteria.getCity_id() != 0 || criteria.getCountry_id() != 0)
				{
					
					query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class).addScalar("distric_id").addScalar("distric_name");
				}
				else
				{
					query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class).addScalar("distric_id");
				}
				
				
			}
			
			if (criteria.getStart() >= 0 && criteria.getLimit() > 0)
			{
				query.setFirstResult(criteria.getStart());
				query.setMaxResults(criteria.getLimit());
			}
			entities = query.list();
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("EX in advancedSearchForBranches : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		
		return entities;
		
		
	}
	
	
	
	public List<Object[]> advancedSearchForBranches(AdvancedSearchCriteria criteria)
	{
		if (criteria == null)
			return null;
		List<Object[]> entities = null;
		try
		{
			String sql = " SELECT DISTINCT branch.* ,branch.id branchId";
			String bodySQL = " FROM branch branch ";
			// +
			// "	inner join subcategory_has_sections_has_branch subsect on branch.id = subsect.branch_id "
			// +
			// " inner join subcategory_has_sections sect on subsect.subcategorysection_id = sect.id inner join unit un on un.id = branch.unit_unit_id "
			// +
			// " inner join distric dist on dist.id = branch.distric_id inner join city on city.id = dist.city_id "
			// +
			// " inner join specialist_has_branch specbran on specbran.branch_id = branch.id inner join specialist spe on spe.id = specbran.specialist_id "
			// +
			// " inner join schedule_has_days days on days.specialist_has_branch_id = specbran.id inner join schedule sch on sch.id = days.schedule_id"
			// + " WHERE branch.active = 1 " ;
			
			String endSQL = "  WHERE branch.lat<>0 and branch.lng<>0   having distance < 5 ";
			
			String condition = " ";
			// if(criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			// sql +=
			// " ,4890 * 3 * ASIN(SQRT(POWER(SIN(("+criteria.getLatitude()+" - abs(branch.lat)) * pi()/180 / 2),2) + COS("+criteria.getLatitude()+" * pi()/180 ) * COS(abs(branch.lat) *pi()/180) "
			// + " * POWER(SIN((" +criteria.getLongitude()+
			// " - branch.lng) *pi()/180 / 2), 2) )) as distance " ;
			// else
			// sql += " , '' as distance " ;
			
			// category
			if (criteria.getCat_id() > 0 || criteria.getInsuranceCompany_id() > 0)
			{
				bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
				
				if (criteria.getCat_id() > 0)
					condition += " AND un.category_id = " + criteria.getCat_id();
				
				if (criteria.getInsuranceCompany_id() > 0)
					bodySQL += " inner join unit_has_insurance_company un_has_insu on (un_has_insu.unit_id = un.id and un_has_insu.insurance_company_id=" + criteria.getInsuranceCompany_id() + ")";
				
			}
			
			// if (criteria.getCat_id() > 0)
			// {
			// bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
			// condition += " AND un.category_id = " + criteria.getCat_id();
			// }
			
			
			//
			if (criteria.getSubcat_id() > 0)
			{
				
				bodySQL += "  inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + " inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				
				condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id();
				
			}
			
			// if (criteria.getSection_id() > 0)
			// {
			//
			// if (criteria.getSubcat_id() == 0)
			// {
			//
			// bodySQL +=
			// "  inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id "
			// +
			// " inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
			// }
			//
			// condition +=
			// " AND subcategory_has_sections.sections_section_id = " +
			// criteria.getSection_id();
			//
			//
			// }
			
			bodySQL += " inner join distric on distric.id =branch.distric_id " + " inner join city on distric.city_id=city.id " + " inner join country on country.id=city.country_id ";
			
			
			if (criteria.getKeyword() != null && !criteria.getKeyword().trim().equals(""))
				condition += " AND (branch.name_en LIKE '%" + criteria.getKeyword() + "%'" + " OR branch.name_ar  LIKE '%" + criteria.getKeyword() + "%')";
			
			if (criteria.getDistrict_id() > 0)
			{
				condition += " AND distric.id = " + criteria.getDistrict_id();
			}
			
			if (criteria.getCity_id() > 0)
			{
				condition += " AND city.id = " + criteria.getCity_id();
			}
			
			if (criteria.getCountry_id() > 0)
			{
				condition += " AND country.id = " + criteria.getCountry_id();
			}
			
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				bodySQL += "  inner join specialist_has_branch on specialist_has_branch.branch_id" + " inner join specialist on specialist_has_branch.specialist_id " + " inner join users on specialist.users_id=users.id";
				sql += ",specialist.id specId ,specialist.name specName ";
				
				if (criteria.getGender() > 0)
				{
					condition += " AND users.gender = " + criteria.getGender();
					
				}
				
				if (criteria.getDegree_id() > 0)
				{
					bodySQL += " inner join degree on specialist.degree_id=degree.id";
					condition += " AND users.gender = " + criteria.getDegree_id();
					
				}
				
				if (criteria.getName() != null && !criteria.getName().trim().equals(""))
					condition += " AND specialist.name LIKE '%" + criteria.getName() + "%'";
				
			}
			
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
				sql += " ,4890 * 3 * ASIN(SQRT(POWER(SIN((" + criteria.getLatitude() + " - abs(branch.lat)) * pi()/180 / 2),2) + COS(" + criteria.getLatitude() + " * pi()/180 ) * COS(abs(branch.lat) *pi()/180) " + " * POWER(SIN((" + criteria.getLongitude() + " - branch.lng) *pi()/180 / 2), 2) )) as distance ";
			else
				sql += " , '' as distance ";
			
			sql += bodySQL + condition + ((criteria.getLatitude() > 0 && criteria.getLongitude() > 0) ? endSQL : "");
			System.out.println("SQL : " + sql);
			
			if (criteria.getStart() >= 0 && criteria.getLimit() > 0)
			{
				sql += "  LIMIT :start,:limit    ";
			}
			Query query = null;
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				
				if (criteria.getStart() >= 0 && criteria.getLimit() > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery.addScalar("specId").addScalar("specName").setInteger("start", criteria.getStart()).setInteger("limit", criteria.getLimit());
				}
			}
			else
			{
				
				if (criteria.getStart() >= 0 && criteria.getLimit() > 0)
				{
					
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery.addScalar("branchId").setInteger("start", criteria.getStart()).setInteger("limit", criteria.getLimit());
				}
			}
			
			
			if (query != null)
			{
				entities = query.list();
				System.out.println("Branches Old Services : " + entities.size() + " items");
			}
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("BranchDAO : EX in advancedSearchForBranches : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		
		return entities;
	}
	
	
	
	// JDeeb
	public void updateBranchRating(int rating, int branchId)
	{
		try
		{
			String sqlQuery = " UPDATE branch SET branch_rating = " + rating + " WHERE id = " + branchId;
			Query query = hiber.getSession().createSQLQuery(sqlQuery);
			
			int result = query.executeUpdate();
			if (result < 1)
				System.out.println("BranchDAO : Error update Branch Rating");
		}
		catch (Exception e)
		{
			System.out.println("BranchDAO : Ex in update Branch Rating : " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
	}
	
	
	
	// JDeeb get Hours from DB
	private String getHoursFromDBByID(int id)
	{
		String result = "";
		try
		{
			String sqlQuery = "SELECT h.hour FROM hours h WHERE H.id = ? ";
			Query query = hiber.getSession().createSQLQuery(sqlQuery);
			query.setInteger(0, id);
			
			result = (String) query.uniqueResult();
			
			if (result.contains("AM"))
			{
				result = result.replace("AM", "");
			}
			else if (result.contains("PM"))
			{
				result = result.replace("PM", "");
				String[] tempd = result.trim().split(":");
				if (tempd != null && tempd.length == 2)
				{
					int temp = Integer.parseInt(tempd[0]) + 12;
					result = temp + ":" + tempd[1];
				}
			}
			
		}
		catch (Exception e)
		{
			System.out.println("BranchDAO : Ex in getHoursFromDBByID : " + e.getMessage());
			e.printStackTrace();
			result = null;
		}
		finally
		{
			hiber.closeSession();
		}
		return result != null ? result.trim() : null;
	}
	
	
	
	public BranchEntity getBranchById(int id)
	{
		if (id < 1)
			return null;
		
		BranchEntity entity = null;
		try
		{
			String sql = " SELECT * FROM branch WHERE id = :ID ";
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
			query.setInteger("ID", id);
			entity = (BranchEntity) query.uniqueResult();
		}
		catch (HibernateException ex)
		{
			System.out.println("BranchDAO : Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entity;
	}
	
	
	
	// Insert Multi SubcategorySection to Branch
	public boolean insertBranchSubcategorySection(int[] subSect, int branchId)
	{
		if (subSect != null && subSect.length > 0)
		{
			try
			{
				String sql = " INSERT INTO subcategory_has_sections_has_branch (subcategorysection_id, branch_id) VALUES ";
				
				for (int i = 0; i < subSect.length; i++)
				{
					if (i == subSect.length - 1)
						sql += " (" + subSect[i] + " , " + branchId + ") ";
					else
						sql += " (" + subSect[i] + " , " + branchId + "), ";
				}
				Query query = hiber.getSession().createSQLQuery(sql);
				int result = query.executeUpdate();
				if (result > 0)
					return true;
			}
			catch (HibernateException ex)
			{
				System.out.println("BranchDAO : Get Row : " + ex.getMessage());
				ex.printStackTrace();
				return false;
			}
			finally
			{
				hiber.getSession().getTransaction().commit();
				hiber.closeSession();
			}
		}
		return false;
	}
	
	
	
	// Delete multi SubcategorySection to Branch
	public boolean deleteBranchSubcategorySection(int[] subSect, int branchId)
	{
		if (subSect != null && subSect.length > 0)
		{
			try
			{
				String sql = " DELETE FROM subcategory_has_sections_has_branch WHERE branch_id = :branchID and subcategorysection_id = :subSectId ";
				Query query = null;
				for (int item : subSect)
				{
					try
					{
						query = hiber.getSession().createSQLQuery(sql);
						query.setInteger("branchID", branchId);
						query.setInteger("subSectId", item);
						
						query.executeUpdate();
					}
					catch (Exception ex)
					{
						System.out.println("BranchDAO : " + ex.getMessage());
						ex.printStackTrace();
					}
				}
				return true;
			}
			catch (HibernateException ex)
			{
				System.out.println("BranchDAO : Get Row : " + ex.getMessage());
				ex.printStackTrace();
				return false;
			}
			finally
			{
				hiber.getSession().getTransaction().commit();
				hiber.closeSession();
			}
		}
		return false;
	}
	
	
	
	// check if unit exist or not
	public int checkIfBranchExist(String nameAr, String nameEn, double lat, double lng)
	{
		List<BranchEntity> entities = null;
		try
		{
			String sql = " SELECT * FROM branch WHERE ( name_ar = :nameAr OR name_en = :nameEn ) AND lat = :lat AND lng = :lng ";
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
			query.setString("nameAr", nameAr);
			query.setString("nameEn", nameEn);
			query.setDouble("lat", lat);
			query.setDouble("lng", lng);
			
			entities = query.list();
			
			if (entities != null && entities.size() > 0)
				return 1;
		}
		catch (Exception ex)
		{
			System.out.println("BranchDAO : Get Ex : " + ex.getMessage());
			return 0;
		}
		finally
		{
			hiber.closeSession();
		}
		return 2;
	}
	
	
	
	public List listUnitBranches(int unitId, int start, int rowCount)
	{
		List<BranchEntity> list = null;
		try
		{
			list = hiber.getSession().createCriteria(BranchEntity.class).createCriteria("this.unit", "unit").add(Restrictions.eq("unit.id", unitId)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).setFirstResult(start).setMaxResults(rowCount).list();
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
	
	
	
//	public List<DegreeEntity> findAll()
//	{
//		List<DegreeEntity> list = null;
//		try
//		{
//			list = hiber.getSession().createCriteria(DegreeEntity.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
//		}
//		catch (HibernateException ex)
//		{
//			System.out.println("Get Row : " + ex.getMessage());
//		}
//		finally
//		{
//			hiber.closeSession();
//		}
//		return list;
//	}
}
