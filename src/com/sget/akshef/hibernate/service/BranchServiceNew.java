package com.sget.akshef.hibernate.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.hibernate.Query;

import com.akshffeen.utils.Constants;
import com.akshffeen.utils.Utils;
import com.sget.akshef.beans.SchedulesBean;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.dao.UnitDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.Branch_scheduleEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.BranchesCriteria;

/**
 * 
 * @author abdelrhim
 */
public class BranchServiceNew
{
	BranchDAO dao = null;
	
	private Utils utils = Utils.getInstance();
	HibernateSession hiber;

	private BranchService branchService = new BranchService();
	private UnitDAO unitDAO = new UnitDAO();

	public BranchServiceNew()
	{
		dao = new BranchDAO();
		
		hiber = new HibernateSession();
	}
	
	
	// Get All For Mobile
	public List<BranchBean> getBranchesForMobile(BranchesCriteria criteria, String url)
	{
		List<Object[]> results = new ArrayList<Object[]>();
//		List<Object[]> results = getBranchesForMobile_all(criteria);

		int max_results = Constants.SPONSORED_LIMIT;
		
		if(utils.hasValue(criteria.getSearch_text()))
			results.addAll(getBranchesForMobile_sponsored_names(criteria, max_results, null));

		max_results = Constants.SPONSORED_LIMIT - getIds(results).size();
		if(max_results>0 && utils.hasValue(criteria.getLatitude()) && utils.hasValue(criteria.getLongitude()))
			results.addAll(getBranchesForMobile_sponsored_coordinates(criteria, max_results, getIds(results)));
		
		max_results = Constants.SPONSORED_LIMIT - getIds(results).size();
		if(max_results>0 && utils.hasValue(criteria.getSearch_text()))
			results.addAll(getBranchesForMobile_sponsored_keywords(criteria, max_results, getIds(results)));	
		
		max_results = criteria.getLimit() - getIds(results).size();
		if(max_results>0)
			results.addAll(getBranchesForMobile_all(criteria, max_results, getIds(results)));
		
		max_results = criteria.getLimit() - getIds(results).size();
		if(max_results>0)
			results.addAll(getBranchesForMobile_without_coordinates(criteria, max_results, getIds(results)));
		
		
		



		List<BranchBean> beans = new ArrayList<BranchBean>();
		BranchBean bean;
		DecimalFormat df = new DecimalFormat("#.00");
		if (results != null)
		{
			List<Integer> selectedUnits = new ArrayList<Integer>();
			
			for (Object[] inst : results)
			{				
				BranchEntity entity = (BranchEntity) inst[0];
				
				bean = new BranchBean();
				branchService.fillBean(bean, entity);
				bean.setImage(bean.getImage() != null && !bean.getImage().equalsIgnoreCase("") ? url + bean.getImage() : "");
				
				try
				{
					bean.setDistance(Double.valueOf(df.format((Double) inst[1])));
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
					// TODO Auto-generated catch block
					bean.setDistance(0);
				}
								
//				bean.setSubcategory(inst[3]+"");
				// Add Rating to Bean
				branchService.getBranchRating(bean);
				
				branchService.getBranchSchedules(bean);
				branchService.getSubcategoriesByBranch(bean);
				
				if(utils.hasValue(bean.getDistric()))
					bean.getDistric().setBranches(null);
				if(utils.hasValue(bean.getUnit()))
				{
					bean.getUnit().setBranches(null);
					bean.getUnit().setUnitGroupses(null);
					bean.getUnit().setUnitHasInsuranceCompanies(null);
				}
				
				selectedUnits.add(bean.getUnit().getId());

				beans.add(bean);
			}
			
			if(utils.hasValue(selectedUnits))
			{
				Map<Integer, List<CategoryBean>> categories = unitDAO.getUnitCategories(selectedUnits);
				
				for(BranchBean element : beans)
				{
					if(categories.containsKey(element.getUnit().getId()))
						element.getUnit().setCategoryList(categories.get(element.getUnit().getId()));
				}
			}
		}
		return beans;
	}

	
	public List<Object[]> getBranchesForMobile_sponsored_names(BranchesCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"select b.*, " +
					"0.0 as distance \r\n" +
					
					"from branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					
					"where b.sponsored is not null and b.sponsored=1 \r\n" + 
					"and b.sponsored_from is not null and b.sponsored_to is not null \r\n" + 
					"and b.sponsored_from<=CURDATE() and b.sponsored_to>=CURDATE() \r\n  " +
					"and (b.name_ar like :search_text or b.name_en like :search_text) \r\n" +
					"");

		if(utils.hasValue(ignored_ids))
			sql.append("and b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
		try
		{
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			query.setString("search_text", "%" + criteria.getSearch_text() + "%");

			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
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
	
	public List<Object[]> getBranchesForMobile_sponsored_coordinates(BranchesCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"SELECT " +
					"distinct b.* , " +
					
//					"4890 * 3 * asin(sqrt(power(sin((:lat - abs(b.lat)) * pi()/180 / 2),2) + cos(:lat * pi()/180 )" + " * cos(abs(b.lat) *pi()/180) * power(sin((:lng - b.lng) *pi()/180 / 2), 2) )) as distance , \r\n" +
					"128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n"+
							
					"FROM branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					"where b.sponsored is not null and b.sponsored=1 \r\n" + 
					"and b.sponsored_from is not null and b.sponsored_to is not null \r\n" + 
					"and b.sponsored_from<=CURDATE() and b.sponsored_to>=CURDATE() \r\n  " 
					);
		
		if(utils.hasValue(ignored_ids))
			sql.append("and b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
		sql.append(" having distance < 5 \r\n");


		try
		{			
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			query.setDouble("lat", criteria.getLatitude());
			query.setDouble("lng", criteria.getLongitude());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
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
	
	public List<Object[]> getBranchesForMobile_sponsored_keywords(BranchesCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"select b.*, " +
					"0.0 as distance \r\n" +
					
					"from branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					
					" inner join branch_has_keyword bhk on bhk.branch_id = b.id \r\n"+
					
					" inner join keyword keyword on keyword.id = bhk.keyword_id \r\n"+

					" where keyword.name like :search_text \r\n  "+
					" and b.sponsored is not null and b.sponsored=1 \r\n" + 
					" and b.sponsored_from is not null and b.sponsored_to is not null \r\n" + 
					" and b.sponsored_from<=CURDATE() and b.sponsored_to>=CURDATE() \r\n  " );
		
		if(utils.hasValue(ignored_ids))
			sql.append("and b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");

		try
		{
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id")
							
							.setString("search_text", "%" + criteria.getSearch_text() + "%");;
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
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
	

	public List<Object[]> getBranchesForMobile_all(BranchesCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		String sql = 
					"SELECT " +
					"distinct b.* , " +
	
					"4890 * 3 * asin(sqrt(power(sin((:lat - abs(b.lat)) * pi()/180 / 2),2) + cos(:lat * pi()/180 )" + " * cos(abs(b.lat) *pi()/180) * power(sin((:lng - b.lng) *pi()/180 / 2), 2) )) as distance \r\n" +
//					"128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance, \r\n"+
										
					"FROM branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					
 					" left outer join branch_has_keyword bhk on bhk.branch_id = b.id \r\n"+
 					" left outer join keyword keyword on keyword.id = bhk.keyword_id \r\n"+
 
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
//		String sql_category_cond = " u.category_id = :cat_id ";
		String pre_cond = "";
		String orderbyDist = " ORDER BY distance \r\n";
		String orderbyRate = " ORDER BY branch_rating desc \r\n";
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n";
		String sql_insurane_cond = " ins.insurance_company_id = :insurancyCompany \r\n";
		
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				sql += 	" inner join unit_has_category uhc on (uhc.unit_id=u.id and uhc.category_id = :cat_id ) ";				
			}
			
			if (criteria.getSection_id() != 0)
			{
//				sql += sql_section;
				if (!utils.hasValue(pre_cond))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				pre_cond += sql_section_cond;
			}
			
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
			{
				if (!utils.hasValue(pre_cond))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				pre_cond += " ( b.name_ar like :search_text or  b.name_en like :search_text or keyword.name like :search_text ) ";
			}
			
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany() != 0)
			{
				sql += sql_insurane;
				if (!utils.hasValue(pre_cond))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				
				pre_cond += sql_insurane_cond;
			}
			
			if(utils.hasValue(ignored_ids))
			{
				if (!utils.hasValue(pre_cond))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				
				pre_cond += " b.id not in ("+utils.generateInt(ignored_ids)+") \r\n";		
			}
			
			
			// Rating
			if (criteria.isRating())
			{
				sql_end += orderbyRate;
			}
			else
				sql_end += orderbyDist;
			
			sql += pre_cond + sql_end;
			
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			
			query.setDouble("lat", criteria.getLatitude());
			query.setDouble("lng", criteria.getLongitude());
			
			if (criteria.getCat_id() != 0)
				query.setInteger("cat_id", criteria.getCat_id());
			if (criteria.getSection_id() != 0)
				query.setInteger("sect_id", criteria.getSection_id());
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
				query.setString("search_text", "%" + criteria.getSearch_text() + "%");
			// New For Insurance Company
			if (criteria.getInsuranceCompany() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
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
	
	public List<Object[]> getBranchesForMobile_without_coordinates(BranchesCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		String sql = 
					"SELECT " +
					"distinct b.* , " +
	
					"4890 * 3 * asin(sqrt(power(sin((:lat - abs(b.lat)) * pi()/180 / 2),2) + cos(:lat * pi()/180 )" + " * cos(abs(b.lat) *pi()/180) * power(sin((:lng - b.lng) *pi()/180 / 2), 2) )) as distance \r\n" +
//					"128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance, \r\n"+
										
					"FROM branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					
 					" left outer join branch_has_keyword bhk on bhk.branch_id = b.id \r\n"+
 					" left outer join keyword keyword on keyword.id = bhk.keyword_id \r\n"+
 
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
//		String sql_category_cond = " u.category_id = :cat_id ";
		String pre_cond = "";
		String orderbyDist = " ORDER BY distance \r\n";
		String orderbyRate = " ORDER BY distance,branch_rating desc \r\n";
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n";
		String sql_insurane_cond = " ins.insurance_company_id = :insurancyCompany \r\n";
		
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				sql += 	" inner join unit_has_category uhc on (uhc.unit_id=u.id and uhc.category_id = :cat_id ) ";				
			}
			
			if (criteria.getSection_id() != 0)
			{
//				sql += sql_section;
				if (!utils.hasValue(pre_cond))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				pre_cond += sql_section_cond;
			}
			
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
			{
				if (!utils.hasValue(pre_cond))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				pre_cond += " ( b.name_ar like :search_text or  b.name_en like :search_text or keyword.name like :search_text ) ";
			}
			
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany() != 0)
			{
				sql += sql_insurane;
				if (!utils.hasValue(pre_cond))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				
				pre_cond += sql_insurane_cond;
			}
			
			if(utils.hasValue(ignored_ids))
			{
				if (!utils.hasValue(pre_cond))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				
				pre_cond += " b.id not in ("+utils.generateInt(ignored_ids)+") \r\n";		
			}
			
			
			// Rating
			if (criteria.isRating())
			{
				sql_end += orderbyRate;
			}
			else
				sql_end += orderbyDist;
			
			sql += pre_cond + sql_end;
			
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			
			query.setDouble("lat", criteria.getLatitude());
			query.setDouble("lng", criteria.getLongitude());
			
			if (criteria.getCat_id() != 0)
				query.setInteger("cat_id", criteria.getCat_id());
			if (criteria.getSection_id() != 0)
				query.setInteger("sect_id", criteria.getSection_id());
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
				query.setString("search_text", "%" + criteria.getSearch_text() + "%");
			// New For Insurance Company
			if (criteria.getInsuranceCompany() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
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
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Object[]> getBranchesForMobile_names(BranchesCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT " +
					"distinct b.* , " +
					"0.0 as distance , \r\n" +
					"u.category_id as category_id  " +
					
					"FROM branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
				 "") ;
		
		if (criteria.getSection_id() != 0)
		{
			sql.append(
					" left outer join unit_has_category uhs on uhs.unit_id = u.id \r\n"+
					" left outer join category category on category.id = uhs.category_id \r\n"+
					" left outer join category_has_subcategory chs on chs.category_id = category.id \r\n"+
					" left outer join subcategory_has_sections shs on shs.subcategory_subcategory_id = chs.subcategory_id \r\n"+
					" \r\n"
					);
		}
		
		if (criteria.getInsuranceCompany() != 0)
			sql.append(" inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n");
			
		StringBuilder sql_end = new StringBuilder();
		
		StringBuilder conditions = new StringBuilder();
		String orderbyRate = " ORDER BY branch_rating desc \r\n";
				
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				conditions.append( " where  u.category_id = :cat_id ");
			}
			if (criteria.getSection_id() != 0)
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append(  "shs.sections_section_id = :sect_id \r\n");
			}
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
					
				conditions.append( " ( b.name_ar like :search_text or  b.name_en like :search_text ) ");
			}
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany() != 0)
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append( " ins.insurance_company_id = :insurancyCompany \r\n");
			}
			
			if(utils.hasValue(ignored_ids))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append(" b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
			}
			
			// Rating
			if (criteria.isRating())
			{
				// sql += rateJoinQuery;
				sql_end.append(orderbyRate);
			}
			
			//Add conditions
			sql.append(conditions);
			
			
			//Add order
			sql.append(sql_end);
			
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getCat_id() != 0)
				query.setInteger("cat_id", criteria.getCat_id());
			if (criteria.getSection_id() != 0)
				query.setInteger("sect_id", criteria.getSection_id());
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
				query.setString("search_text", "%" + criteria.getSearch_text() + "%");
			// New For Insurance Company
			if (criteria.getInsuranceCompany() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
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

	public List<Object[]> getBranchesForMobile_coordinates(BranchesCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		StringBuilder sql = new StringBuilder();

		sql.append(
					"SELECT " +
					"distinct b.* , " +
					
	//				"4890 * 3 * asin(sqrt(power(sin((:lat - abs(b.lat)) * pi()/180 / 2),2) + cos(:lat * pi()/180 )" + " * cos(abs(b.lat) *pi()/180) * power(sin((:lng - b.lng) *pi()/180 / 2), 2) )) as distance , \r\n" +
					"128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance, \r\n"+
		
					"u.category_id as category_id  " +
					
					"FROM branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					"");
		
		if (criteria.getSection_id() != 0)
		{
			sql.append(
					" left outer join unit_has_category uhs on uhs.unit_id = u.id \r\n"+
					" left outer join category category on category.id = uhs.category_id \r\n"+
					" left outer join category_has_subcategory chs on chs.category_id = category.id \r\n"+
					" left outer join subcategory_has_sections shs on shs.subcategory_subcategory_id = chs.subcategory_id \r\n"+
					" \r\n"
					);
		}
		
		if (criteria.getInsuranceCompany() != 0)
			sql.append(" inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n");
			
		StringBuilder sql_end = new StringBuilder(" having distance < 5 \r\n");
		
		StringBuilder conditions = new StringBuilder();
		String orderbyRate = " ORDER BY branch_rating desc \r\n";
				
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				conditions.append( " where  u.category_id = :cat_id ");
			}
			if (criteria.getSection_id() != 0)
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append(  "shs.sections_section_id = :sect_id \r\n");
			}
			
//			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
//			{
//				if (conditions.length()==0)
//					conditions.append( " where ");
//				else
//					conditions.append( " and ");
//					
//				conditions.append( " ( b.name_ar like :search_text or  b.name_en like :search_text ) ");
//			}
			
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany() != 0)
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append( " ins.insurance_company_id = :insurancyCompany \r\n");
			}
			
			if(utils.hasValue(ignored_ids))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append(" b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
			}
			
			
			// Rating
			if (criteria.isRating())
			{
				// sql += rateJoinQuery;
				sql_end.append(orderbyRate);
			}
			
			//Add conditions
			sql.append(conditions);
			
			
			//Add order
			sql.append(sql_end);
			
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getCat_id() != 0)
				query.setInteger("cat_id", criteria.getCat_id());
			if (criteria.getSection_id() != 0)
				query.setInteger("sect_id", criteria.getSection_id());
			
//			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
//				query.setString("search_text", "%" + criteria.getSearch_text() + "%");
			
			// New For Insurance Company
			if (criteria.getInsuranceCompany() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany());
			
			query.setDouble("lat", criteria.getLatitude());
			query.setDouble("lng", criteria.getLongitude());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
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

	public List<Object[]> getBranchesForMobile_keywords(BranchesCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		StringBuilder sql = new StringBuilder();

		sql.append(
				"SELECT " +
				"distinct b.* , " +
				"0.0 as distance , \r\n" +
				"u.category_id as category_id  " +
				
				"FROM branch b \r\n" +
				" inner join unit u on u.id = b.unit_unit_id \r\n"+
				
				" inner join branch_has_keyword bhk on bhk.branch_id = b.id \r\n"+
					
				" inner join keyword keyword on keyword.id = bhk.keyword_id \r\n"+

				"") ;
		
		if (criteria.getSection_id() != 0)
		{
			sql.append(
					" left outer join unit_has_category uhs on uhs.unit_id = u.id \r\n"+
					" left outer join category category on category.id = uhs.category_id \r\n"+
					" left outer join category_has_subcategory chs on chs.category_id = category.id \r\n"+
					" left outer join subcategory_has_sections shs on shs.subcategory_subcategory_id = chs.subcategory_id \r\n"+
					" \r\n"
					);
		}
		
		if (criteria.getInsuranceCompany() != 0)
			sql.append(" inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n");
			
		StringBuilder sql_end = new StringBuilder("");
		
		StringBuilder conditions = new StringBuilder();
		String orderbyRate = " ORDER BY branch_rating desc \r\n";
				
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				conditions.append( " where  u.category_id = :cat_id ");
			}
			if (criteria.getSection_id() != 0)
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append(  " shs.sections_section_id = :sect_id \r\n");
			}

			
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
					
				conditions.append( " keyword.name like :search_text ");
			}
			
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany() != 0)
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append( " ins.insurance_company_id = :insurancyCompany \r\n");
			}
			
			if(utils.hasValue(ignored_ids))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append(" b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
			}
			
			
			// Rating
			if (criteria.isRating())
			{
				// sql += rateJoinQuery;
				sql_end.append(orderbyRate);
			}
			
			//Add conditions
			sql.append(conditions);
			
			
			//Add order
			sql.append(sql_end);
			
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getCat_id() != 0)
				query.setInteger("cat_id", criteria.getCat_id());
			if (criteria.getSection_id() != 0)
				query.setInteger("sect_id", criteria.getSection_id());
			
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
				query.setString("search_text", "%" + criteria.getSearch_text() + "%");
			
			// New For Insurance Company
			if (criteria.getInsuranceCompany() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
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



	public List<Object[]> getBranchesForMobile_all_original(BranchesCriteria criteria)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		String sql = 
					"SELECT " +
					"distinct b.* , " +
	
//					"4890 * 3 * asin(sqrt(power(sin((:lat - abs(b.lat)) * pi()/180 / 2),2) + cos(:lat * pi()/180 )" + " * cos(abs(b.lat) *pi()/180) * power(sin((:lng - b.lng) *pi()/180 / 2), 2) )) as distance , \r\n" +
					"128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance, \r\n"+
					
					"u.category_id as category_id  " +
					
					"FROM branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
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
		String sql_category_cond = " u.category_id = :cat_id ";
		String pre_cond = "";
		String orderbyDist = " ORDER BY distance \r\n";
		String orderbyRate = " ORDER BY branch_rating desc \r\n";
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n";
		String sql_insurane_cond = " ins.insurance_company_id = :insurancyCompany \r\n";
		
		try
		{
			// Optional Parameters
			if (criteria.getCat_id() != 0)
			{
				pre_cond += " where " + sql_category_cond;
			}
			
			if (criteria.getSection_id() != 0)
			{
//				sql += sql_section;
				if (pre_cond != null && pre_cond.trim().equalsIgnoreCase(""))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				pre_cond += sql_section_cond;
			}
			
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
			{
				if (pre_cond != null && pre_cond.trim().equalsIgnoreCase(""))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				pre_cond += " ( b.name_ar like :search_text or  b.name_en like :search_text ) ";
			}
			
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany() != 0)
			{
				sql += sql_insurane;
				if (pre_cond != null && pre_cond.trim().equalsIgnoreCase(""))
					pre_cond += " where ";
				else
					pre_cond += " and ";
				
				pre_cond += sql_insurane_cond;
			}
			
			// Rating
			if (criteria.isRating())
			{
				sql_end += orderbyRate;
			}
			else
				sql_end += orderbyDist;
			
			sql += pre_cond + sql_end;
			
			Query query = hiber.getSession()
							.createSQLQuery(sql)
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			System.out.println("SQL : " + sql);
			
			query.setDouble("lat", criteria.getLatitude());
			query.setDouble("lng", criteria.getLongitude());
			
			if (criteria.getCat_id() != 0)
				query.setInteger("cat_id", criteria.getCat_id());
			if (criteria.getSection_id() != 0)
				query.setInteger("sect_id", criteria.getSection_id());
			if (criteria.getSearch_text() != null && !criteria.getSearch_text().trim().equalsIgnoreCase(""))
				query.setString("search_text", "%" + criteria.getSearch_text() + "%");
			// New For Insurance Company
			if (criteria.getInsuranceCompany() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany());
			
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
	
	
	public List<Integer> getIds(List<Object[]> results)
	{
		List<Integer> ids = new ArrayList<Integer>();
		
		if (results != null)
		{
			for (Object[] inst : results)
			{
				BranchEntity entity = (BranchEntity) inst[0];
				
				ids.add(entity.getId());
			}
		}
		
		return ids;
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

