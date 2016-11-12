package com.sget.akshef.hibernate.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.akshffeen.utils.Constants;
import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

/**
 * 
 * @author JDeeb Branch Service
 */
public class BranchServiceNew2
{
	private Utils utils = Utils.getInstance();
	private BranchService branchService = new BranchService();
	HibernateSession hiber;

	public BranchServiceNew2()
	{
		hiber = new HibernateSession();
	}
	
	
	// Get All For Mobile
	public List<BranchBean> getBranchesForMobile(SearchDoctorsCriteria criteria, String url)
	{
//		List<Object[]> results = getBranchesForMobile_all(criteria);
		
		List<Object[]> results = new ArrayList<Object[]>();

		int max_results = Constants.SPONSORED_LIMIT;
		
		if(utils.hasValue(criteria.getDoctorName()))
			results.addAll(getBranchesForMobile_sponsored_names(criteria, max_results, null));

		max_results = Constants.SPONSORED_LIMIT - utils.getIds(results).size();
		if(max_results>0 && utils.hasValue(criteria.getLatitude()) && utils.hasValue(criteria.getLongitude()))
			results.addAll(getBranchesForMobile_sponsored_coordinates(criteria, max_results, utils.getIds(results)));
		
		max_results = Constants.SPONSORED_LIMIT - utils.getIds(results).size();
		if(max_results>0 && utils.hasValue(criteria.getDoctorName()))
			results.addAll(getBranchesForMobile_sponsored_keywords(criteria, max_results, utils.getIds(results)));	
		
		max_results = criteria.getLimit() - utils.getIds(results).size();
		if(max_results>0)
			results.addAll(getBranchesForMobile_all(criteria, max_results, utils.getIds(results)));
		
		max_results = criteria.getLimit() - utils.getIds(results).size();
		if(max_results>0)
			results.addAll(getBranchesForMobile_without_coordinates(criteria, max_results, utils.getIds(results)));
		
		
		
//		max_results = criteria.getLimit() - utils.getIds(results).size();
//		if(max_results>0)
//			results.addAll(getBranchesForMobile_names(criteria, max_results, utils.getIds(results)));
//		
//		max_results = criteria.getLimit() - utils.getIds(results).size();
//		if(max_results>0 && utils.hasValue(criteria.getLatitude()) && utils.hasValue(criteria.getLongitude()))
//			results.addAll(getBranchesForMobile_coordinates(criteria, max_results, utils.getIds(results)));
//		
//		max_results = criteria.getLimit() - utils.getIds(results).size();
//		if(max_results>0 && utils.hasValue(criteria.getDoctorName()))
//			results.addAll(getBranchesForMobile_keywords(criteria, max_results, utils.getIds(results)));	
	
		
		
		
		List<BranchBean> beans = new ArrayList<BranchBean>();
		BranchBean bean;
		DecimalFormat df = new DecimalFormat("#.00");
		if (results != null)
		{
			Double category_id = null;

			for (Object[] inst : results)
			{
				BranchEntity entity = (BranchEntity) inst[0];
				
				bean = new BranchBean();
				branchService.fillBean(bean, entity);
				bean.setImage(bean.getImage() != null && !bean.getImage().equalsIgnoreCase("") ? url + bean.getImage() : "");
				
				category_id = 0d;
				
				if(inst.length>1)
				{
					category_id = (utils.hasValue(inst[1]+"")?Double.parseDouble(inst[1]+""):0);
				}
				
				bean.setCategory_id(category_id.intValue());				
				
				bean.setDistance(0);

				if(inst.length>2)
				{
					if (inst[2] instanceof Double)
						bean.setDistance(Double.valueOf(df.format((Double) inst[2])));
				}
				
				// Add Rating to Bean
				branchService.getBranchRating(bean);
				
				beans.add(bean);
			}
		}
		return beans;
	}

	
	public List<Object[]> getBranchesForMobile_sponsored_names(SearchDoctorsCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"select b.*, " +
					"-1 as category_id,  \r\n" +
					"0.0 as distance  \r\n" +
					
					"from branch b \r\n" +
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					
					" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
					" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
					" INNER JOIN users users ON users.id = spec.users_id \r\n " +

					" where b.sponsored is not null and b.sponsored=1 \r\n" + 
					" and b.sponsored_from is not null and b.sponsored_to is not null \r\n" + 
					" and b.sponsored_from<=CURDATE() and b.sponsored_to>=CURDATE() \r\n  " +
					" and ( users.firstname like :search_text or users.lastname like :search_text or spec.name like :search_text) \r\n" +
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
			
			query.setString("search_text", "%" + criteria.getDoctorName() + "%");

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
	
	public List<Object[]> getBranchesForMobile_sponsored_coordinates(SearchDoctorsCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		// List<BranchEntity> entities = null;
		List<Object[]> results = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"SELECT " +
					"distinct b.* , " +
					"-1 as category_id, \r\n" +

					"4890 * 3 * asin(sqrt(power(sin((:lat - abs(b.lat)) * pi()/180 / 2),2) + cos(:lat * pi()/180 )" + " * cos(abs(b.lat) *pi()/180) * power(sin((:lng - b.lng) *pi()/180 / 2), 2) )) as distance \r\n" +
//					"128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n"+

					"FROM branch b \r\n" +
					
					" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
					" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
					" INNER JOIN users users ON users.id = spec.users_id \r\n " +
					
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
	
	public List<Object[]> getBranchesForMobile_sponsored_keywords(SearchDoctorsCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					"select b.*, " +
					"-1 as category_id,  " +
					"0.0 as distance \r\n" +
					
					"from branch b \r\n" +
					
					" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
					" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
					" INNER JOIN users users ON users.id = spec.users_id \r\n " +
					
					" inner join unit u on u.id = b.unit_unit_id \r\n"+
					
					" inner join doctor_has_keyword dhk on dhk.doctor_id = spec.id \r\n"+
					
					" inner join keyword keyword on keyword.id = dhk.keyword_id \r\n"+

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
							
							.setString("search_text", "%" + criteria.getDoctorName() + "%");;
			
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
	
	
	public List<Object[]> getBranchesForMobile_all(SearchDoctorsCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT distinct b.*, -1 as category_id \r\n ");
		
		if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
		{
			sql.append(",4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(b.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 )\r\n " + " * COS(abs(b.lat) *pi()/180) * POWER(SIN((:lng - b.lng) *pi()/180 / 2), 2) )) as distance ");
//			sql.append(",128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n");
		}
		else
		{
			sql.append(",0.0 as distance ");
//			sql.append(",128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n");
		}
		
		sql.append(
				" FROM branch b \r\n " +
		
				" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
				" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
		
				" left outer join doctor_has_keyword dhk on dhk.doctor_id = spec.id \r\n"+
				
				" left outer join keyword keyword on keyword.id = dhk.keyword_id \r\n"+
						
				" INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id \r\n " + 
				" INNER JOIN users users ON users.id = spec.users_id \r\n " +
				" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n " + 
				" INNER JOIN distric dist ON dist.id = b.distric_id \r\n " + 
				" INNER JOIN city cit ON cit.id = dist.city_id \r\n " +
				
				" join unit u on u.id = b.unit_unit_id \r\n ");
		
		StringBuilder pre_cond = new StringBuilder(" WHERE b.lat<>0 and b.lng<>0 \r\n ");
		
		if (criteria.getSectionId() > 0)
			pre_cond.append("  AND sections_id = :sectionId \r\n ");
		if (criteria.getCountryId() > 0)
			pre_cond.append("  AND cit.country_id = :countryId \r\n ");
		if (criteria.getCityId() > 0)
			pre_cond.append(" AND cit.id = :cityId \r\n ");
		if (criteria.getDistrictId() > 0)
			pre_cond.append("  AND dist.id = :districId \r\n ");
		if (criteria.getDegree() > 0)
			pre_cond.append(" AND spec.degree_id = :degreeId \r\n ");
		if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
			pre_cond.append(" AND users.gender = :genderId \r\n ");
		if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
			pre_cond.append(" AND ( users.firstname like :doctorName or users.lastname like :doctorName  or spec.name like :doctorName or keyword.name like :doctorName ) \r\n ");
		
		if(utils.hasValue(ignored_ids))
		{		
			pre_cond.append(" and b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");		
		}
		
		if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			pre_cond.append(" having distance < 5 \r\n ");
		
		if (criteria.isOrderbyrate())
			pre_cond.append(" order by distance,rate desc       \r\n ");
		else
			pre_cond.append(" order by distance       \r\n ");

		String sql_end = " ";
		

		
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n ";
		String sql_insurane_cond = " and ins.insurance_company_id = :insurancyCompany \r\n ";
		
		try
		{
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany_id() != 0)
			{
				sql.append(sql_insurane);

				pre_cond.append(sql_insurane_cond);
			}
			
			sql.append(pre_cond + sql_end);
			
			SQLQuery sqlQuery = hiber.getSession().
								createSQLQuery(sql.toString()).
								addEntity("b", BranchEntity.class).
								addScalar("category_id");
			
			Query query = null;
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
				query = sqlQuery.addScalar("distance");
			
			query = sqlQuery;
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			{
				query.setDouble("lat", criteria.getLatitude());
				query.setDouble("lng", criteria.getLongitude());
			}
			
			if (criteria.getInsuranceCompany_id() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany_id());
			
			
			// Set Parameters
			// query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (criteria.getSectionId() > 0)
				query.setInteger("sectionId", criteria.getSectionId());
			if (criteria.getCountryId() > 0)
				query.setInteger("countryId", criteria.getCountryId());
			if (criteria.getCityId() > 0)
				query.setInteger("cityId", criteria.getCityId());
			if (criteria.getDistrictId() > 0)
				query.setInteger("districId", criteria.getDistrictId());
			if (criteria.getDegree() > 0)
				query.setInteger("degreeId", criteria.getDegree());
			if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
				query.setInteger("genderId", criteria.getGender());
			if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
				query.setString("doctorName", criteria.getDoctorName());
			
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
	
	public List<Object[]> getBranchesForMobile_without_coordinates(SearchDoctorsCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT distinct b.*, -1 as category_id \r\n ");
		
		if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
		{
			sql.append(",4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(b.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 )\r\n " + " * COS(abs(b.lat) *pi()/180) * POWER(SIN((:lng - b.lng) *pi()/180 / 2), 2) )) as distance ");
//			sql.append(",128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n");
		}
		else
		{
			sql.append(",0.0 as distance ");
//			sql.append(",128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n");
		}
		
		sql.append(
				" FROM branch b \r\n " +
		
				" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
				" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
		
				" left outer join doctor_has_keyword dhk on dhk.doctor_id = spec.id \r\n"+
				
				" left outer join keyword keyword on keyword.id = dhk.keyword_id \r\n"+
						
				" INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id \r\n " + 
				" INNER JOIN users users ON users.id = spec.users_id \r\n " +
				" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n " + 
				" INNER JOIN distric dist ON dist.id = b.distric_id \r\n " + 
				" INNER JOIN city cit ON cit.id = dist.city_id \r\n " +
				
				" join unit u on u.id = b.unit_unit_id \r\n ");
		
		StringBuilder pre_cond = new StringBuilder(" WHERE b.lat<>0 and b.lng<>0 \r\n ");
		
		if (criteria.getSectionId() > 0)
			pre_cond.append("  AND sections_id = :sectionId \r\n ");
		if (criteria.getCountryId() > 0)
			pre_cond.append("  AND cit.country_id = :countryId \r\n ");
		if (criteria.getCityId() > 0)
			pre_cond.append(" AND cit.id = :cityId \r\n ");
		if (criteria.getDistrictId() > 0)
			pre_cond.append("  AND dist.id = :districId \r\n ");
		if (criteria.getDegree() > 0)
			pre_cond.append(" AND spec.degree_id = :degreeId \r\n ");
		if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
			pre_cond.append(" AND users.gender = :genderId \r\n ");
		if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
			pre_cond.append(" AND ( users.firstname like :doctorName or users.lastname like :doctorName  or spec.name like :doctorName or keyword.name like :doctorName ) \r\n ");
		
		if(utils.hasValue(ignored_ids))
		{		
			pre_cond.append(" and b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");		
		}
		
//		if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
//			pre_cond.append(" having distance < 5 \r\n ");
		
		if (criteria.isOrderbyrate())
			pre_cond.append(" order by distance,rate desc       \r\n ");
		else
			pre_cond.append(" order by distance       \r\n ");

		String sql_end = " ";
		

		
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n ";
		String sql_insurane_cond = " and ins.insurance_company_id = :insurancyCompany \r\n ";
		
		try
		{
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany_id() != 0)
			{
				sql.append(sql_insurane);

				pre_cond.append(sql_insurane_cond);
			}
			
			sql.append(pre_cond + sql_end);
			
			SQLQuery sqlQuery = hiber.getSession().
								createSQLQuery(sql.toString()).
								addEntity("b", BranchEntity.class).
								addScalar("category_id");
			
			Query query = null;
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
				query = sqlQuery.addScalar("distance");
			
			query = sqlQuery;
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			{
				query.setDouble("lat", criteria.getLatitude());
				query.setDouble("lng", criteria.getLongitude());
			}
			
			if (criteria.getInsuranceCompany_id() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany_id());
			
			
			// Set Parameters
			// query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (criteria.getSectionId() > 0)
				query.setInteger("sectionId", criteria.getSectionId());
			if (criteria.getCountryId() > 0)
				query.setInteger("countryId", criteria.getCountryId());
			if (criteria.getCityId() > 0)
				query.setInteger("cityId", criteria.getCityId());
			if (criteria.getDistrictId() > 0)
				query.setInteger("districId", criteria.getDistrictId());
			if (criteria.getDegree() > 0)
				query.setInteger("degreeId", criteria.getDegree());
			if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
				query.setInteger("genderId", criteria.getGender());
			if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
				query.setString("doctorName", criteria.getDoctorName());
			
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
	
	
	
	public List<Object[]> getBranchesForMobile_names(SearchDoctorsCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT distinct b.*, -1 as category_id,0.0 as distance \r\n ");
		
		sql.append(
				" FROM branch b \r\n " +
		
				" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
				" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
		
				" INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id \r\n " + 
				" INNER JOIN users users ON users.id = spec.users_id \r\n " +
				" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n " + 
				" INNER JOIN distric dist ON dist.id = b.distric_id \r\n " + 
				" INNER JOIN city cit ON cit.id = dist.city_id \r\n " +
				
				" join unit u on u.id = b.unit_unit_id \r\n ");
		
		StringBuilder pre_cond = new StringBuilder();
		
		if (criteria.getSectionId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" sections_id = :sectionId \r\n ");
		if (criteria.getCountryId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" cit.country_id = :countryId \r\n ");
		if (criteria.getCityId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" cit.id = :cityId \r\n ");
		if (criteria.getDistrictId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" dist.id = :districId \r\n ");
		if (criteria.getDegree() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" spec.degree_id = :degreeId \r\n ");
		if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" users.gender = :genderId \r\n ");
		if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" ( users.firstname like :doctorName or users.lastname like :doctorName  or spec.name like :doctorName ) \r\n ");
		
		if (utils.hasValue(ignored_ids))
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
		if (criteria.isOrderbyrate())
			pre_cond.append(" order by rate desc \r\n ");

		String sql_end = " ";
	
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n ";
		String sql_insurane_cond = " and ins.insurance_company_id = :insurancyCompany \r\n ";
		
		try
		{
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany_id() != 0)
			{
				sql.append(sql_insurane);

				pre_cond.append(sql_insurane_cond);
			}
			
			sql.append(pre_cond + sql_end);
			
			SQLQuery sqlQuery = hiber.getSession().
								createSQLQuery(sql.toString()).
								addEntity("b", BranchEntity.class).
								addScalar("category_id");
			
			Query query = null;
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
				query = sqlQuery.addScalar("distance");
			
			query = sqlQuery;
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getInsuranceCompany_id() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany_id());
			
			
			// Set Parameters
			// query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (criteria.getSectionId() > 0)
				query.setInteger("sectionId", criteria.getSectionId());
			if (criteria.getCountryId() > 0)
				query.setInteger("countryId", criteria.getCountryId());
			if (criteria.getCityId() > 0)
				query.setInteger("cityId", criteria.getCityId());
			if (criteria.getDistrictId() > 0)
				query.setInteger("districId", criteria.getDistrictId());
			if (criteria.getDegree() > 0)
				query.setInteger("degreeId", criteria.getDegree());
			if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
				query.setInteger("genderId", criteria.getGender());
			if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
				query.setString("doctorName", criteria.getDoctorName());
			
			
			
			
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
	
	public List<Object[]> getBranchesForMobile_coordinates(SearchDoctorsCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
				" SELECT distinct b.*, -1 as category_id,"+
				" 128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n"+
				" \r\n ");
		
		sql.append(
				" FROM branch b \r\n " +
		
				" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
				" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
		
				" INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id \r\n " + 
				" INNER JOIN users users ON users.id = spec.users_id \r\n " +
				" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n " + 
				" INNER JOIN distric dist ON dist.id = b.distric_id \r\n " + 
				" INNER JOIN city cit ON cit.id = dist.city_id \r\n " +
				
				" join unit u on u.id = b.unit_unit_id \r\n ");
		
		StringBuilder pre_cond = new StringBuilder(" WHERE b.lat<>0 and b.lng<>0 \r\n ");
		
		if (criteria.getSectionId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" sections_id = :sectionId \r\n ");
		if (criteria.getCountryId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" cit.country_id = :countryId \r\n ");
		if (criteria.getCityId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" cit.id = :cityId \r\n ");
		if (criteria.getDistrictId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" dist.id = :districId \r\n ");
		if (criteria.getDegree() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" spec.degree_id = :degreeId \r\n ");
		if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" users.gender = :genderId \r\n ");
		if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" ( users.firstname like :doctorName or users.lastname like :doctorName  or spec.name like :doctorName ) \r\n ");
		
		if (utils.hasValue(ignored_ids))
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
		if (criteria.isOrderbyrate())
			pre_cond.append(" order by rate desc \r\n ");

		String sql_end = " ";
	
		if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			sql_end = " having distance < 5 \r\n ";
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n ";
		String sql_insurane_cond = " and ins.insurance_company_id = :insurancyCompany \r\n ";
		
		try
		{
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany_id() != 0)
			{
				sql.append(sql_insurane);

				pre_cond.append(sql_insurane_cond);
			}
			
			sql.append(pre_cond + sql_end);
			
			SQLQuery sqlQuery = hiber.getSession().
								createSQLQuery(sql.toString()).
								addEntity("b", BranchEntity.class).
								addScalar("category_id");
			
			Query query = null;
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
				query = sqlQuery.addScalar("distance");
			
			query = sqlQuery;
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			{
				query.setDouble("lat", criteria.getLatitude());
				query.setDouble("lng", criteria.getLongitude());
			}
			
			if (criteria.getInsuranceCompany_id() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany_id());
			
			
			// Set Parameters
			// query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (criteria.getSectionId() > 0)
				query.setInteger("sectionId", criteria.getSectionId());
			if (criteria.getCountryId() > 0)
				query.setInteger("countryId", criteria.getCountryId());
			if (criteria.getCityId() > 0)
				query.setInteger("cityId", criteria.getCityId());
			if (criteria.getDistrictId() > 0)
				query.setInteger("districId", criteria.getDistrictId());
			if (criteria.getDegree() > 0)
				query.setInteger("degreeId", criteria.getDegree());
			if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
				query.setInteger("genderId", criteria.getGender());
			if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
				query.setString("doctorName", criteria.getDoctorName());
			
			
			
			
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
	
	public List<Object[]> getBranchesForMobile_keywords(SearchDoctorsCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT distinct b.*, -1 as category_id,0.0 as distance \r\n ");
		
		sql.append(
				" FROM branch b \r\n " +
		
				" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
				" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
		
				" INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id \r\n " + 
				" INNER JOIN users users ON users.id = spec.users_id \r\n " +
				" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n " + 
				" INNER JOIN distric dist ON dist.id = b.distric_id \r\n " + 
				" INNER JOIN city cit ON cit.id = dist.city_id \r\n " +
				
				" inner join doctor_has_keyword dhk on dhk.doctor_id = spec.id \r\n"+
				
				" inner join keyword keyword on keyword.id = dhk.keyword_id \r\n"+

				
				" join unit u on u.id = b.unit_unit_id \r\n ");
		
		StringBuilder pre_cond = new StringBuilder();
		
		if (criteria.getSectionId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" sections_id = :sectionId \r\n ");
		if (criteria.getCountryId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" cit.country_id = :countryId \r\n ");
		if (criteria.getCityId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" cit.id = :cityId \r\n ");
		if (criteria.getDistrictId() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" dist.id = :districId \r\n ");
		if (criteria.getDegree() > 0)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" spec.degree_id = :degreeId \r\n ");
		if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" users.gender = :genderId \r\n ");
		if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" ( keyword.name like :doctorName ) \r\n ");
		
		if (utils.hasValue(ignored_ids))
			pre_cond.append((pre_cond.length()==0?" where ":" and ")+" b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
		if (criteria.isOrderbyrate())
			pre_cond.append(" order by rate desc \r\n ");

		String sql_end = " ";
	
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n ";
		String sql_insurane_cond = " and ins.insurance_company_id = :insurancyCompany \r\n ";
		
		try
		{
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany_id() != 0)
			{
				sql.append(sql_insurane);

				pre_cond.append(sql_insurane_cond);
			}
			
			sql.append(pre_cond + sql_end);
			
			SQLQuery sqlQuery = hiber.getSession().
								createSQLQuery(sql.toString()).
								addEntity("b", BranchEntity.class).
								addScalar("category_id");
			
			Query query = null;
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
				query = sqlQuery.addScalar("distance");
			
			query = sqlQuery;
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getInsuranceCompany_id() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany_id());
			
			
			// Set Parameters
			// query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (criteria.getSectionId() > 0)
				query.setInteger("sectionId", criteria.getSectionId());
			if (criteria.getCountryId() > 0)
				query.setInteger("countryId", criteria.getCountryId());
			if (criteria.getCityId() > 0)
				query.setInteger("cityId", criteria.getCityId());
			if (criteria.getDistrictId() > 0)
				query.setInteger("districId", criteria.getDistrictId());
			if (criteria.getDegree() > 0)
				query.setInteger("degreeId", criteria.getDegree());
			if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
				query.setInteger("genderId", criteria.getGender());
			if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
				query.setString("doctorName", criteria.getDoctorName());
			
			
			
			
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
	
	
	public List<Object[]> getBranchesForMobile_all_original(SearchDoctorsCriteria criteria)
	{
		List<Object[]> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT distinct b.*, -1 as category_id \r\n ");
		
		if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
		{
//			sql.append(",4890 * 3 * ASIN(SQRT(POWER(SIN((:lat - abs(b.lat)) * pi()/180 / 2),2) + COS(:lat * pi()/180 )\r\n " + " * COS(abs(b.lat) *pi()/180) * POWER(SIN((:lng - b.lng) *pi()/180 / 2), 2) )) as distance ");
			sql.append(",128.1111 * degrees(acos(cos(radians(:lat)) * cos(radians(b.lat)) * cos(radians(:lng - b.lng)) + sin(radians(:lat)) * sin(radians(b.lat)))) as distance \r\n");
		}
		
		sql.append(
				" FROM branch b \r\n " +
		
				" inner join specialist_has_branch spec_branch on (spec_branch.branch_id = b.id) \r\n " +
				" inner join specialist spec on (spec.id = spec_branch.specialist_id) \r\n " +
		
				" INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id \r\n " + 
				" INNER JOIN users users ON users.id = spec.users_id \r\n " +
				" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n " + 
				" INNER JOIN distric dist ON dist.id = b.distric_id \r\n " + 
				" INNER JOIN city cit ON cit.id = dist.city_id \r\n " +
				
				" join unit u on u.id = b.unit_unit_id \r\n ");
		
		StringBuilder pre_cond = new StringBuilder(" WHERE b.lat<>0 and b.lng<>0 \r\n ");
		
		if (criteria.getSectionId() > 0)
			pre_cond.append("  AND sections_id = :sectionId \r\n ");
		if (criteria.getCountryId() > 0)
			pre_cond.append("  AND cit.country_id = :countryId \r\n ");
		if (criteria.getCityId() > 0)
			pre_cond.append(" AND cit.id = :cityId \r\n ");
		if (criteria.getDistrictId() > 0)
			pre_cond.append("  AND dist.id = :districId \r\n ");
		if (criteria.getDegree() > 0)
			pre_cond.append(" AND spec.degree_id = :degreeId \r\n ");
		if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
			pre_cond.append(" AND users.gender = :genderId \r\n ");
		if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
			pre_cond.append(" AND ( users.firstname like :doctorName or users.lastname like :doctorName  or spec.name like :doctorName ) \r\n ");
		if (criteria.isOrderbyrate())
			pre_cond.append(" order by rate desc       \r\n ");
		
		String sql_end = " ";
		
		if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			sql_end = " having distance < 5 \r\n ";
		
		
		String sql_insurane = " inner join unit_has_insurance_company ins on b.unit_unit_id = ins.unit_id \r\n ";
		String sql_insurane_cond = " and ins.insurance_company_id = :insurancyCompany \r\n ";
		
		try
		{
			// New For InsuranceCompany
			if (criteria.getInsuranceCompany_id() != 0)
			{
				sql.append(sql_insurane);

				pre_cond.append(sql_insurane_cond);
			}
			
			sql.append(pre_cond + sql_end);
			
			SQLQuery sqlQuery = hiber.getSession().
								createSQLQuery(sql.toString()).
								addEntity("b", BranchEntity.class).
								addScalar("category_id");
			
			Query query = null;
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
				query = sqlQuery.addScalar("distance");
			
			query = sqlQuery;
			
			System.out.println("SQL : " + sql);
			
			if (criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			{
				query.setDouble("lat", criteria.getLatitude());
				query.setDouble("lng", criteria.getLongitude());
			}
			
			if (criteria.getInsuranceCompany_id() != 0)
				query.setInteger("insurancyCompany", criteria.getInsuranceCompany_id());
			
			
			// Set Parameters
			// query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (criteria.getSectionId() > 0)
				query.setInteger("sectionId", criteria.getSectionId());
			if (criteria.getCountryId() > 0)
				query.setInteger("countryId", criteria.getCountryId());
			if (criteria.getCityId() > 0)
				query.setInteger("cityId", criteria.getCityId());
			if (criteria.getDistrictId() > 0)
				query.setInteger("districId", criteria.getDistrictId());
			if (criteria.getDegree() > 0)
				query.setInteger("degreeId", criteria.getDegree());
			if (criteria.getGender() == DBConstants.USER_GENDER_FEMALE || criteria.getGender() == DBConstants.USER_GENDER_MALE)
				query.setInteger("genderId", criteria.getGender());
			if (criteria.getDoctorName() != null && !criteria.getDoctorName().trim().equalsIgnoreCase(""))
				query.setString("doctorName", criteria.getDoctorName());
			
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
	
	

}
