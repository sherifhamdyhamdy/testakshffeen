package com.sget.akshef.hibernate.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.akshffeen.utils.Constants;
import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.dao.UnitDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;

/**
 * 
 * @author abdelrhim
 */
public class AdvancedSearchServiceNew
{
	BranchDAO dao = null;
	
	private Utils utils = Utils.getInstance();
	private UnitDAO unitDAO = new UnitDAO();

	HibernateSession hiber;

	private BranchService branchService = new BranchService();

	public AdvancedSearchServiceNew()
	{
		dao = new BranchDAO();
		
		hiber = new HibernateSession();
	}
	
	// Get All For Mobile
	public List<BranchBean> advancedSearchForBranches(AdvancedSearchCriteria criteria, String url)
	{
		List<Object[]> results = new ArrayList<Object[]>();
//		List<Object[]> results = advancedSearchForBranches_all(criteria);

		int max_results = Constants.SPONSORED_LIMIT;
		
		if(utils.hasValue(criteria.getName()))
			results.addAll(advancedSearchForBranches_sponsored_names(criteria, max_results, null));

		max_results = Constants.SPONSORED_LIMIT - getIds(results).size();
		if(max_results>0 && utils.hasValue(criteria.getLatitude()) && utils.hasValue(criteria.getLongitude()))
			results.addAll(advancedSearchForBranches_sponsored_coordinates(criteria, max_results, getIds(results)));
		
		max_results = Constants.SPONSORED_LIMIT - getIds(results).size();
		if(max_results>0 && utils.hasValue(criteria.getName()))
			results.addAll(advancedSearchForBranches_sponsored_keywords(criteria, max_results, getIds(results)));	
				
		max_results = criteria.getLimit() - getIds(results).size();
		if(max_results>0)
			results.addAll(advancedSearchForBranches_all(criteria, max_results, getIds(results)));
		
		max_results = criteria.getLimit() - getIds(results).size();
		if(max_results>0)
			results.addAll(advancedSearchForBranches_without_coordinates(criteria, max_results, getIds(results)));
		


		List<BranchBean> beans = new ArrayList<BranchBean>();
		BranchBean bean;
		DecimalFormat df = new DecimalFormat("#.00");
		if (results != null)
		{
//			Double category_id = null;
			List<Integer> selectedUnits = new ArrayList<Integer>();

			for (Object[] inst : results)
			{
				BranchEntity entity = (BranchEntity) inst[0];
				
				bean = new BranchBean();
				branchService.fillBean(bean, entity);
				bean.setImage(bean.getImage() != null && !bean.getImage().equalsIgnoreCase("") ? url + bean.getImage() : "");
				
//				distance_double = 0d;
//				
//				if(inst.length>2)
//				{
//					distance = 0;
//					distance_double = (utils.hasValue(inst[2]+"")?Double.parseDouble(inst[2]+""):0);
//				}
//				
//				bean.setCategory_id(distance_double.intValue());				
//				
//				if (inst[1] instanceof Double)
//					bean.setDistance(Double.valueOf(df.format((Double) inst[1])));
//				else
//					bean.setDistance(0);
				
				
//				category_id = 0d;
//				
//				if(inst.length>2)
//				{
//					category_id = (utils.hasValue(inst[2]+"")?Double.parseDouble(inst[2]+""):0);
//				}
//				
//				bean.setCategory_id(category_id.intValue());				
				
				bean.setDistance(0);

				if(inst.length>1)
				{
					if (inst[1] instanceof Double)
						bean.setDistance(Double.valueOf(df.format((Double) inst[1])));
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

	
	public List<Object[]> advancedSearchForBranches_sponsored_names(AdvancedSearchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"select b.*, " +
					"0.0 as distance \r\n" +
					
					"from branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					
					" inner join specialist_has_branch on specialist_has_branch.branch_id" + 
					" inner join specialist on specialist_has_branch.specialist_id " + 
							
					"where b.sponsored is not null and b.sponsored=1 \r\n" + 
					"and b.sponsored_from is not null and b.sponsored_to is not null \r\n" + 
					"and b.sponsored_from<=CURDATE() and b.sponsored_to>=CURDATE() \r\n  " +
					"and (specialist.name like :search_text) \r\n" +
					"");

		if(utils.hasValue(ignored_ids))
			sql.append(" and b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
		try
		{
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity("b", BranchEntity.class)
							.addScalar("distance")
							.addScalar("category_id");
			
			query.setString("search_text", "%" + criteria.getName() + "%");

			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in advancedSearchForBranches : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
	
	public List<Object[]> advancedSearchForBranches_sponsored_coordinates(AdvancedSearchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"SELECT " +
					"distinct b.*, " +

	//				"4890 * 3 * asin(sqrt(power(sin((:lat - abs(b.lat)) * pi()/180 / 2),2) + cos(:lat * pi()/180 )" + " * cos(abs(b.lat) *pi()/180) * power(sin((:lng - b.lng) *pi()/180 / 2), 2) )) as distance , \r\n" +
					"128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n"+

					
					"FROM branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					"where b.sponsored is not null and b.sponsored=1 \r\n" + 
					"and b.sponsored_from is not null and b.sponsored_to is not null \r\n" + 
					"and b.sponsored_from<=CURDATE() and b.sponsored_to>=CURDATE() \r\n  " 
					);
		
		if(utils.hasValue(ignored_ids))
			sql.append(" and b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
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
			System.out.println("Get Ex in advancedSearchForBranches : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
	
	public List<Object[]> advancedSearchForBranches_sponsored_keywords(AdvancedSearchCriteria criteria, int max_results, List<Integer> ignored_ids)
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
							
							.setString("search_text", "%" + criteria.getName() + "%");;
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in advancedSearchForBranches : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
	

	
	public List<Object[]> advancedSearchForBranches_all(AdvancedSearchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		if (criteria == null)
			return null;
		List<Object[]> entities = null;
		
		try
		{
			String sql = " SELECT DISTINCT branch.* ";
			String bodySQL = " FROM branch branch \r\n"+
							" inner join unit un on un.id = branch.unit_unit_id \r\n" +
							" left outer join branch_has_keyword bhk on bhk.branch_id = branch.id \r\n"+
							" left outer join keyword keyword on keyword.id = bhk.keyword_id \r\n";
			
			String endSQL = "  WHERE branch.lat<>0 and branch.lng<>0   having distance < 5 ";
			
			String condition = " ";
			
			// category
			if (criteria.getCat_id() > 0 || criteria.getInsuranceCompany_id() > 0)
			{
//				bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
				
				if (criteria.getCat_id() > 0)
				{
					bodySQL += 	" inner join unit_has_category uhc on (uhc.unit_id=un.id and uhc.category_id = " + criteria.getCat_id()+") ";				
				}
				
				if (criteria.getInsuranceCompany_id() > 0)
					bodySQL += " inner join unit_has_insurance_company un_has_insu on (un_has_insu.unit_id = un.id and un_has_insu.insurance_company_id=" + criteria.getInsuranceCompany_id() + ")";
				
			}
			
			//
			if (criteria.getSubcat_id() > 0)
			{
				
				bodySQL += 
							" inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + 
							" inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				
				condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id();
				
			}
			
			bodySQL += 
						" inner join distric on distric.id =branch.distric_id " +
						" inner join city on distric.city_id=city.id " +
						" inner join country on country.id=city.country_id ";
			
			
			if (criteria.getKeyword() != null && !criteria.getKeyword().trim().equals(""))
				condition += " AND (branch.name_en LIKE '%" + criteria.getKeyword() + "%'" + " OR branch.name_ar  LIKE '%" + criteria.getKeyword() + "%' OR keyword.name  LIKE '%" + criteria.getKeyword() + "%')";
			
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
			
			if(utils.hasValue(ignored_ids))
			{
				condition += " and branch.id not in ("+utils.generateInt(ignored_ids)+") \r\n";		
			}
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			{
				sql += " ,4890 * 3 * ASIN(SQRT(POWER(SIN((" + criteria.getLatitude() + " - abs(branch.lat)) * pi()/180 / 2),2) + COS(" + criteria.getLatitude() + " * pi()/180 ) * COS(abs(branch.lat) *pi()/180) " + " * POWER(SIN((" + criteria.getLongitude() + " - branch.lng) *pi()/180 / 2), 2) )) as distance ";				
//				sql += " ,128.1111 * degrees(acos(cos(radians(" + criteria.getLatitude() + ")) * cos(radians(b.lat)) * cos(radians(" + criteria.getLatitude() + " - b.lng)) + sin(radians(" + criteria.getLatitude() + ")) * sin(radians(b.lat)))) as distance \r\n";
			}
			else
				sql += " , 0.0 as distance ";
			
			
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				bodySQL += 
							" inner join specialist_has_branch on specialist_has_branch.branch_id" + 
							" inner join specialist on specialist_has_branch.specialist_id " + 
							" inner join users on specialist.users_id=users.id";
				
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
			
			

			
			sql += bodySQL + condition + ((criteria.getLatitude() > 0 && criteria.getLongitude() > 0) ? endSQL : "");
			
			System.out.println("SQL : " + sql);
			
			sql+=" ORDER BY distance ";
			
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
					
					
					query = sqlQuery.addScalar("specId")
							.addScalar("specName")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
				}
			}
			else
			{
				
				if (criteria.getStart() >= 0 && criteria.getLimit() > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery
							.addScalar("category_id")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
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

	public List<Object[]> advancedSearchForBranches_without_coordinates(AdvancedSearchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		if (criteria == null)
			return null;
		List<Object[]> entities = null;
		
		try
		{
			String sql = " SELECT DISTINCT branch.* ";
			String bodySQL = " FROM branch branch \r\n"+
							" inner join unit un on un.id = branch.unit_unit_id \r\n" +
							" left outer join branch_has_keyword bhk on bhk.branch_id = branch.id \r\n"+
							" left outer join keyword keyword on keyword.id = bhk.keyword_id \r\n";
			
			String endSQL = "  WHERE branch.lat<>0 and branch.lng<>0  ";
			
			String condition = " ";
			
			// category
			if (criteria.getCat_id() > 0 || criteria.getInsuranceCompany_id() > 0)
			{
//				bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
				
				if (criteria.getCat_id() > 0)
				{
					bodySQL += 	" inner join unit_has_category uhc on (uhc.unit_id=un.id and uhc.category_id = " + criteria.getCat_id()+") ";				
				}
					
				if (criteria.getInsuranceCompany_id() > 0)
					bodySQL += " inner join unit_has_insurance_company un_has_insu on (un_has_insu.unit_id = un.id and un_has_insu.insurance_company_id=" + criteria.getInsuranceCompany_id() + ")";
				
			}
			
			//
			if (criteria.getSubcat_id() > 0)
			{
				
				bodySQL += 
							" inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + 
							" inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				
				condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id();
				
			}
			
			bodySQL += 
						" inner join distric on distric.id =branch.distric_id " +
						" inner join city on distric.city_id=city.id " +
						" inner join country on country.id=city.country_id ";
			
			
			if (criteria.getKeyword() != null && !criteria.getKeyword().trim().equals(""))
				condition += " AND (branch.name_en LIKE '%" + criteria.getKeyword() + "%'" + " OR branch.name_ar  LIKE '%" + criteria.getKeyword() + "%' OR keyword.name  LIKE '%" + criteria.getKeyword() + "%')";
			
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
			
			if(utils.hasValue(ignored_ids))
			{
				condition += " and branch.id not in ("+utils.generateInt(ignored_ids)+") \r\n";		
			}
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			{
				sql += " ,4890 * 3 * ASIN(SQRT(POWER(SIN((" + criteria.getLatitude() + " - abs(branch.lat)) * pi()/180 / 2),2) + COS(" + criteria.getLatitude() + " * pi()/180 ) * COS(abs(branch.lat) *pi()/180) " + " * POWER(SIN((" + criteria.getLongitude() + " - branch.lng) *pi()/180 / 2), 2) )) as distance ";				
//				sql += " ,128.1111 * degrees(acos(cos(radians(" + criteria.getLatitude() + ")) * cos(radians(b.lat)) * cos(radians(" + criteria.getLatitude() + " - b.lng)) + sin(radians(" + criteria.getLatitude() + ")) * sin(radians(b.lat)))) as distance \r\n";
			}
			else
				sql += " , 0.0 as distance ";
			
			
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				bodySQL += 
							" inner join specialist_has_branch on specialist_has_branch.branch_id" + 
							" inner join specialist on specialist_has_branch.specialist_id " + 
							" inner join users on specialist.users_id=users.id";
				
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
			
			

			
			sql += bodySQL + condition + ((criteria.getLatitude() > 0 && criteria.getLongitude() > 0) ? endSQL : "");
			
			System.out.println("SQL : " + sql);
			
			sql+=" ORDER BY distance ";
			
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
					
					
					query = sqlQuery.addScalar("specId")
							.addScalar("specName")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
				}
			}
			else
			{
				
				if (criteria.getStart() >= 0 && criteria.getLimit() > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery
							.addScalar("category_id")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<Object[]> advancedSearchForBranches_names(AdvancedSearchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		if (criteria == null)
			return null;
		List<Object[]> entities = null;
		
		try
		{
			String sql = " SELECT DISTINCT branch.* ,un.category_id category_id ";
			String bodySQL = " FROM branch branch \r\n"+
							" inner join unit un on un.id = branch.unit_unit_id \r\n";
			
			String endSQL = "  WHERE (1=1) ";
			
			String condition = " ";
			
			// category
			if (criteria.getCat_id() > 0 || criteria.getInsuranceCompany_id() > 0)
			{
//				bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
				
				if (criteria.getCat_id() > 0)
					condition += " AND un.category_id = " + criteria.getCat_id();
				
				if (criteria.getInsuranceCompany_id() > 0)
					bodySQL += " inner join unit_has_insurance_company un_has_insu on (un_has_insu.unit_id = un.id and un_has_insu.insurance_company_id=" + criteria.getInsuranceCompany_id() + ")";
				
			}
			
			//
			if (criteria.getSubcat_id() > 0)
			{
				
				bodySQL += 
							" inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + 
							" inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				
				condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id();
				
			}
			
			bodySQL += 
						" inner join distric on distric.id =branch.distric_id " +
						" inner join city on distric.city_id=city.id " +
						" inner join country on country.id=city.country_id ";
			
			
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
			
//			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
//			{
////				sql += " ,4890 * 3 * ASIN(SQRT(POWER(SIN((" + criteria.getLatitude() + " - abs(branch.lat)) * pi()/180 / 2),2) + COS(" + criteria.getLatitude() + " * pi()/180 ) * COS(abs(branch.lat) *pi()/180) " + " * POWER(SIN((" + criteria.getLongitude() + " - branch.lng) *pi()/180 / 2), 2) )) as distance ";				
//				sql += " ,128.1111 * degrees(acos(cos(radians(" + criteria.getLatitude() + ")) * cos(radians(b.lat)) * cos(radians(" + criteria.getLatitude() + " - b.lng)) + sin(radians(" + criteria.getLatitude() + ")) * sin(radians(b.lat)))) as distance \r\n";
//
//			}
//			else
				sql += " , '' as distance ";
			
			
			if(utils.hasValue(ignored_ids))
				condition+=" and branch.id not in ("+utils.generateInt(ignored_ids)+") \r\n";
				
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				bodySQL += 
							" inner join specialist_has_branch on specialist_has_branch.branch_id" + 
							" inner join specialist on specialist_has_branch.specialist_id " + 
							" inner join users on specialist.users_id=users.id";
				
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
			
			

			
			sql += bodySQL + condition + ((criteria.getLatitude() > 0 && criteria.getLongitude() > 0) ? endSQL : "");
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getStart() >= 0 && max_results > 0)
			{
				sql += "  LIMIT :start,:limit    ";
			}
			Query query = null;
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				
				if (criteria.getStart() >= 0 && max_results > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery.addScalar("specId")
							.addScalar("specName")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
				}
			}
			else
			{
				
				if (criteria.getStart() >= 0 && max_results > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery
							.addScalar("category_id")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
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

	public List<Object[]> advancedSearchForBranches_coordinates(AdvancedSearchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		if (criteria == null)
			return null;
		List<Object[]> entities = null;
		
		try
		{
			String sql = " SELECT DISTINCT branch.* ,un.category_id category_id ";
			String bodySQL = " FROM branch branch \r\n"+
							" inner join unit un on un.id = branch.unit_unit_id \r\n";
			
			String endSQL = "  WHERE branch.lat<>0 and branch.lng<>0   having distance < 5 ";
			
			String condition = " ";
			
			// category
			if (criteria.getCat_id() > 0 || criteria.getInsuranceCompany_id() > 0)
			{
//				bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
				
				if (criteria.getCat_id() > 0)
					condition += " AND un.category_id = " + criteria.getCat_id();
				
				if (criteria.getInsuranceCompany_id() > 0)
					bodySQL += " inner join unit_has_insurance_company un_has_insu on (un_has_insu.unit_id = un.id and un_has_insu.insurance_company_id=" + criteria.getInsuranceCompany_id() + ")";
				
			}
			
			//
			if (criteria.getSubcat_id() > 0)
			{
				
				bodySQL += 
							" inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + 
							" inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				
				condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id();
				
			}
			
			bodySQL += 
						" inner join distric on distric.id =branch.distric_id " +
						" inner join city on distric.city_id=city.id " +
						" inner join country on country.id=city.country_id ";
			
			
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
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			{
//				sql += " ,4890 * 3 * ASIN(SQRT(POWER(SIN((" + criteria.getLatitude() + " - abs(branch.lat)) * pi()/180 / 2),2) + COS(" + criteria.getLatitude() + " * pi()/180 ) * COS(abs(branch.lat) *pi()/180) " + " * POWER(SIN((" + criteria.getLongitude() + " - branch.lng) *pi()/180 / 2), 2) )) as distance ";				
				sql += " ,128.1111 * degrees(acos(cos(radians(" + criteria.getLatitude() + ")) * cos(radians(branch.lat)) * cos(radians(" + criteria.getLatitude() + " - branch.lng)) + sin(radians(" + criteria.getLatitude() + ")) * sin(radians(branch.lat)))) as distance \r\n";

			}
			else
				sql += " , '' as distance ";
			
			if(utils.hasValue(ignored_ids))
				condition+=" and branch.id not in ("+utils.generateInt(ignored_ids)+") \r\n";
			
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				bodySQL += 
							" inner join specialist_has_branch on specialist_has_branch.branch_id" + 
							" inner join specialist on specialist_has_branch.specialist_id " + 
							" inner join users on specialist.users_id=users.id";
				
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
				
//				if (criteria.getName() != null && !criteria.getName().trim().equals(""))
//					condition += " AND specialist.name LIKE '%" + criteria.getName() + "%'";
				
			}
			
			

			
			sql += bodySQL + condition + ((criteria.getLatitude() > 0 && criteria.getLongitude() > 0) ? endSQL : "");
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getStart() >= 0 && max_results > 0)
			{
				sql += "  LIMIT :start,:limit    ";
			}
			Query query = null;
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				
				if (criteria.getStart() >= 0 && max_results > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery.addScalar("specId")
							.addScalar("specName")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
				}
			}
			else
			{
				
				if (criteria.getStart() >= 0 && max_results > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery
							.addScalar("category_id")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
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

	public List<Object[]> advancedSearchForBranches_keywords(AdvancedSearchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		if (criteria == null)
			return null;
		List<Object[]> entities = null;
		
		try
		{
			String sql = " SELECT DISTINCT branch.* ,un.category_id category_id ";
			String bodySQL = " FROM branch branch \r\n"+
							" inner join unit un on un.id = branch.unit_unit_id \r\n"+
							
							" inner join branch_has_keyword bhk on bhk.branch_id = branch.id \r\n"+
							
							" inner join keyword keyword on keyword.id = bhk.keyword_id \r\n";
			
			String endSQL = "  WHERE (1=1) ";
			
			String condition = " ";
			
			// category
			if (criteria.getCat_id() > 0 || criteria.getInsuranceCompany_id() > 0)
			{
//				bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
				
				if (criteria.getCat_id() > 0)
					condition += " AND un.category_id = " + criteria.getCat_id();
				
				if (criteria.getInsuranceCompany_id() > 0)
					bodySQL += " inner join unit_has_insurance_company un_has_insu on (un_has_insu.unit_id = un.id and un_has_insu.insurance_company_id=" + criteria.getInsuranceCompany_id() + ")";
				
			}
			
			//
			if (criteria.getSubcat_id() > 0)
			{
				
				bodySQL += 
							" inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + 
							" inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				
				condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id();
				
			}
			
			bodySQL += 
						" inner join distric on distric.id =branch.distric_id " +
						" inner join city on distric.city_id=city.id " +
						" inner join country on country.id=city.country_id ";
			
			
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
			
//			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
//			{
////				sql += " ,4890 * 3 * ASIN(SQRT(POWER(SIN((" + criteria.getLatitude() + " - abs(branch.lat)) * pi()/180 / 2),2) + COS(" + criteria.getLatitude() + " * pi()/180 ) * COS(abs(branch.lat) *pi()/180) " + " * POWER(SIN((" + criteria.getLongitude() + " - branch.lng) *pi()/180 / 2), 2) )) as distance ";				
//				sql += " ,128.1111 * degrees(acos(cos(radians(" + criteria.getLatitude() + ")) * cos(radians(b.lat)) * cos(radians(" + criteria.getLatitude() + " - b.lng)) + sin(radians(" + criteria.getLatitude() + ")) * sin(radians(b.lat)))) as distance \r\n";
//
//			}
//			else
				sql += " , '' as distance ";
			
			if(utils.hasValue(ignored_ids))
				condition+=" and branch.id not in ("+utils.generateInt(ignored_ids)+") \r\n";
			
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				bodySQL += 
							" inner join specialist_has_branch on specialist_has_branch.branch_id" + 
							" inner join specialist on specialist_has_branch.specialist_id " + 
							" inner join users on specialist.users_id=users.id";
				
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
				
//				if (criteria.getName() != null && !criteria.getName().trim().equals(""))
//					condition += " AND specialist.name LIKE '%" + criteria.getName() + "%'";
				
				if (criteria.getName() != null && !criteria.getName().trim().equals(""))
					condition += " AND keyword.name LIKE '%" + criteria.getName() + "%'";
			}
			
			

			
			sql += bodySQL + condition + ((criteria.getLatitude() > 0 && criteria.getLongitude() > 0) ? endSQL : "");
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getStart() >= 0 && max_results > 0)
			{
				sql += "  LIMIT :start,:limit    ";
			}
			Query query = null;
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				
				if (criteria.getStart() >= 0 && max_results > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery.addScalar("specId")
							.addScalar("specName")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
				}
			}
			else
			{
				
				if (criteria.getStart() >= 0 && max_results > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery
							.addScalar("category_id")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", max_results);
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


	
	public List<Object[]> advancedSearchForBranches_all_original(AdvancedSearchCriteria criteria)
	{
		if (criteria == null)
			return null;
		List<Object[]> entities = null;
		
		try
		{
			String sql = " SELECT DISTINCT branch.* ,un.category_id category_id ";
			String bodySQL = " FROM branch branch \r\n"+
							" inner join unit un on un.id = branch.unit_unit_id \r\n";
			
			String endSQL = "  WHERE branch.lat<>0 and branch.lng<>0   having distance < 5 ";
			
			String condition = " ";
			
			// category
			if (criteria.getCat_id() > 0 || criteria.getInsuranceCompany_id() > 0)
			{
//				bodySQL += " inner join unit un on un.id = branch.unit_unit_id ";
				
				if (criteria.getCat_id() > 0)
					condition += " AND un.category_id = " + criteria.getCat_id();
				
				if (criteria.getInsuranceCompany_id() > 0)
					bodySQL += " inner join unit_has_insurance_company un_has_insu on (un_has_insu.unit_id = un.id and un_has_insu.insurance_company_id=" + criteria.getInsuranceCompany_id() + ")";
				
			}
			
			//
			if (criteria.getSubcat_id() > 0)
			{
				
				bodySQL += 
							" inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id " + 
							" inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
				
				condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id();
				
			}
			
			bodySQL += 
						" inner join distric on distric.id =branch.distric_id " +
						" inner join city on distric.city_id=city.id " +
						" inner join country on country.id=city.country_id ";
			
			
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
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			{
//				sql += " ,4890 * 3 * ASIN(SQRT(POWER(SIN((" + criteria.getLatitude() + " - abs(branch.lat)) * pi()/180 / 2),2) + COS(" + criteria.getLatitude() + " * pi()/180 ) * COS(abs(branch.lat) *pi()/180) " + " * POWER(SIN((" + criteria.getLongitude() + " - branch.lng) *pi()/180 / 2), 2) )) as distance ";				
				sql += " ,128.1111 * degrees(acos(cos(radians(" + criteria.getLatitude() + ")) * cos(radians(b.lat)) * cos(radians(" + criteria.getLatitude() + " - b.lng)) + sin(radians(" + criteria.getLatitude() + ")) * sin(radians(b.lat)))) as distance \r\n";

			}
			else
				sql += " , '' as distance ";
			
			
			if (criteria.getMode() != null && criteria.getMode().equals("spec"))
			{
				bodySQL += 
							" inner join specialist_has_branch on specialist_has_branch.branch_id" + 
							" inner join specialist on specialist_has_branch.specialist_id " + 
							" inner join users on specialist.users_id=users.id";
				
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
					
					
					query = sqlQuery.addScalar("specId")
							.addScalar("specName")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", criteria.getLimit());
				}
			}
			else
			{
				
				if (criteria.getStart() >= 0 && criteria.getLimit() > 0)
				{
					SQLQuery sqlQuery = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
					
					if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
						sqlQuery = sqlQuery.addScalar("distance");
					
					
					query = sqlQuery
							.addScalar("category_id")
							.setInteger("start", criteria.getStart())
							.setInteger("limit", criteria.getLimit());
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
	
}

