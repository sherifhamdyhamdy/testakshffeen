package com.sget.akshef.hibernate.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.hibernate.Query;

import com.akshffeen.utils.Utils;
import com.sget.akshef.beans.SchedulesBean;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.dao.SectionsDAO;
import com.sget.akshef.hibernate.dao.UserRateBranchDAO;
import com.sget.akshef.hibernate.dao.UsersDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.Branch_scheduleEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;
import com.sget.akshef.hibernate.entities.Doctor_scheduleEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UserRateBranchEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;
import com.sget.akshf.searchcriteria.BranchesCriteria;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

/**
 * 
 * @author JDeeb Branch Service
 */
public class Services
{
	BranchDAO dao = null;
	
	private Utils utils = Utils.getInstance();
	HibernateSession hiber;

	private BranchService branchService = new BranchService();

	public Services()
	{
		dao = new BranchDAO();
		
		hiber = new HibernateSession();
	}
	
	
	

	public List<Object[]> getBranchesForMobile_sponserd(BranchesCriteria criteria)
	{
		List<Object[]> results = null;
		
		String sql = 
					"select b.* " +
					"-1 as distance , \r\n" +
					"u.category_id as category_id  " +
					
					"from branch b \r\n" +
					" join unit u on u.id = b.unit_unit_id \r\n"+
					
					"where b.sponsored is not null and b.sponsored=1 " + 
					"and b.sponsored_from is not null and b.sponsored_to is not null \r\n" + 
					"and b.sponsored_from<=CURDATE() and b.sponsored_to>=CURDATE() \r\n  ";

		try
		{
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(2);
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
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
	
	public List<Object[]> getBranchesForMobile_names(BranchesCriteria criteria)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		String sql = 
				"SELECT " +
				"distinct b.* , " +
				"-1 as distance , \r\n" +
				"u.category_id as category_id  " +
				
				"FROM branch b \r\n" +
				" join unit u on u.id = b.unit_unit_id \r\n"+
				 "" ;
		if (criteria.getSection_id() != 0)
		{
			sql+=
					" left outer join unit_has_category uhs on uhs.unit_id = u.id \r\n"+
					" left outer join category category on category.id = uhs.category_id \r\n"+
					" left outer join category_has_subcategory chs on chs.category_id = category.id \r\n"+
					" left outer join subcategory_has_sections shs on shs.subcategory_subcategory_id = chs.subcategory_id \r\n"+
					" \r\n";
		}
		
		String sql_end = "";
		
		String sql_section_cond = " shs.sections_section_id = :sect_id \r\n";
		// String sql_category = " join unit u on u.id = b.unit_unit_id ";
		String sql_category_cond = " u.category_id = :cat_id ";
		String pre_cond = "";
		String orderbyRate = " ORDER BY branch_rating desc \r\n";
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n";
		String sql_insurane_cond = " ins.insurance_company_id = :insurancyCompany \r\n";
		
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				pre_cond += " WHERE " + sql_category_cond;
			}
			if (criteria.getSection_id() != 0)
			{
//				sql += sql_section;
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
			
			sql += pre_cond + sql_end;
			
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			
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

	public List<Object[]> getBranchesForMobile_coordinates(BranchesCriteria criteria)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		String sql = 
				"SELECT " +
				"distinct b.* , " +
				"4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(b.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 )" + " * COS(abs(b.lat) *pi()/180) * POWER(SIN((:lng - b.lng) *pi()/180 / 2), 2) )) as distance , \r\n" +
				"u.category_id as category_id  " +
				
				"FROM branch b \r\n" +
				" join unit u on u.id = b.unit_unit_id \r\n"+
				 "" ;
		String sql_end = " having distance < 5 \r\n";
		
		try
		{
			sql += sql_end;
			
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			query.setDouble("lat", criteria.getLatitude());
			query.setDouble("lng", criteria.getLongitude());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(criteria.getLimit());
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
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
	
	public List<Object[]> getBranchesForMobile_keywords(BranchesCriteria criteria)
	{
		List<Object[]> results = null;
		
		String sql = 
					"select b.* " +
					"-1 as distance , \r\n" +
					"u.category_id as category_id  " +
					
					"from branch b \r\n" +
					" join unit u on u.id = b.unit_unit_id \r\n"+
					
					" join branchHasKeywordList bhk on bhk.branch_id = b.id \r\n"+
					
					" join keyword keyword on keyword.id = bhk.keyword_id \r\n"+

					"where b.name like :keyword \r\n  ";


		try
		{
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id")
							
							.setString("keyword", "%" + criteria.getSearch_text() + "%");;
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(2);
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
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
	

	
	
	

	public List<Object[]> getBranchesForMobile_all(BranchesCriteria criteria)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		String sql = 
				"SELECT " +
				"distinct b.* , " +

//				"4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(b.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 )" + " * COS(abs(b.lat) *pi()/180) * POWER(SIN((:lng - b.lng) *pi()/180 / 2), 2) )) as distance , \r\n" +
				"128.1111 * DEGREES(ACOS(COS(RADIANS(:lat)) * COS(RADIANS(b.lat)) * COS(RADIANS(:lng - b.lng)) + SIN(RADIANS(:lat)) * SIN(RADIANS(b.lat)))) AS distance, \r\n"+
				
				"u.category_id as category_id  " +
				
				"FROM branch b \r\n" +
				" join unit u on u.id = b.unit_unit_id \r\n"+
				 "" ;
		
		if (criteria.getSection_id() != 0)
		{
			sql+=
					" left outer join unit_has_category uhs on uhs.unit_id = u.id \r\n"+
					" left outer join category category on category.id = uhs.category_id \r\n"+
					" left outer join category_has_subcategory chs on chs.category_id = category.id \r\n"+
					" left outer join subcategory_has_sections shs on shs.subcategory_subcategory_id = chs.subcategory_id \r\n"+
					" \r\n";
		}
		
		String sql_end = " having distance < 5 \r\n";
		
		String sql_section_cond = " shs.sections_section_id = :sect_id \r\n";
		// String sql_category = " join unit u on u.id = b.unit_unit_id ";
		String sql_category_cond = " u.category_id = :cat_id ";
		String pre_cond = "";
		String orderbyDist = " ORDER BY distance \r\n";
		String orderbyRate = " ORDER BY branch_rating desc \r\n";
		String rateJoinQuery = " LEFT OUTER JOIN user_rate_branch br on b.id = br.branch_id \r\n";
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n";
		String sql_insurane_cond = " ins.insurance_company_id = :insurancyCompany \r\n";
		
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				pre_cond += " WHERE " + sql_category_cond;
			}
			if (criteria.getSection_id() != 0)
			{
//				sql += sql_section;
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
			
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id")
//							.addScalar("sub_category")
							;
			
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
	
	
	
	
	
	
	
	
	
	
	
	
	// get Schedules for Branch
	public void getBranchSchedules(BranchBean bean)
	{
		if (bean != null && bean.getId() > 0)
		{
			List<Branch_scheduleEntity> branch_scheduleList = dao.getBranchSchedules(bean.getId());
			
			Map<String, TreeSet<Integer>> schedules_map = new HashMap<String, TreeSet<Integer>>();
			
			String schedule = "";
			for(Branch_scheduleEntity branch_schedule : branch_scheduleList)
			{
				schedule = branch_schedule.getFrom_hour() + "-" + branch_schedule.getTo_hour();
				
				if(schedules_map.containsKey(schedule))
					schedules_map.get(schedule).add(branch_schedule.getDay_id());
				else
				{
					TreeSet<Integer> days = new TreeSet<Integer>();
					days.add(branch_schedule.getDay_id());
					schedules_map.put(schedule, days);
				}
			}
				
			SchedulesBean schedulesBean = utils.getSchedules(schedules_map);
			
			bean.setBranch_schedules(schedulesBean);
		}
	}

}

