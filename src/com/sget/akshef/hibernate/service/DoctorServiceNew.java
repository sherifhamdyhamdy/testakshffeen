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
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.Branch_scheduleEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.DoctorSectionBranchCriteria;
import com.sget.akshf.searchcriteria.DoctorSectionBranchCriteria;

/**
 * 
 * @author abdelrhim
 */
public class DoctorServiceNew
{
	BranchDAO dao = null;
	
	private Utils utils = Utils.getInstance();
	HibernateSession hiber;

	private SpecialistService specialistService = new SpecialistService();

	public DoctorServiceNew()
	{
		dao = new BranchDAO();
		
		hiber = new HibernateSession();
	}
	
	public List<SpecialistBean> getSpecialistBySection(DoctorSectionBranchCriteria search, String url)
	{
//		List<SpecialistEntity> entities = getSpecialistBySectionQuery_all(search);
		
		List<SpecialistEntity> entities = new ArrayList<SpecialistEntity>();

		int max_results = Constants.SPONSORED_LIMIT;
		
		if(utils.hasValue(search.getDoctorName()))
			entities.addAll(getSpecialistBySection_sponsored_names(search, max_results, null));
		
		max_results = Constants.SPONSORED_LIMIT - getIds(entities).size();
		if(max_results>0 && utils.hasValue(search.getDoctorName()))
			entities.addAll(getSpecialistBySection_sponsored_keywords(search, max_results, getIds(entities)));	
		
		max_results = search.getLimit() - getIds(entities).size();
		if(max_results>0)
			entities.addAll(getSpecialistBySectionQuery_all(search, max_results, getIds(entities)));
		
		
//		max_results = search.getLimit() - getIds(entities).size();
//		if(max_results>0)
//			entities.addAll(getSpecialistBySection_names(search, max_results, getIds(entities)));
//		
//		max_results = search.getLimit() - getIds(entities).size();
//		if(max_results>0 && utils.hasValue(search.getDoctorName()))
//			entities.addAll(getSpecialistBySection_keywords(search, max_results, getIds(entities)));	
		
		
		List<SpecialistBean> beans = new ArrayList<SpecialistBean>();
		SpecialistBean bean = null;
		if (entities != null && entities.size() > 0)
		{
			for (SpecialistEntity entity : entities)
			{
				bean = new SpecialistBean();
				specialistService.fillBean(bean, entity);
				
				specialistService.getSpecialistRating(bean);
				
				specialistService.fillCertifications(entity, bean);
				
				specialistService.fillKeywords(entity, bean);
				
				bean.getUsers().setProfile_img(url + bean.getUsers().getProfile_img());
				beans.add(bean);
			}
		}
		return beans;
	}

	
	public List<SpecialistEntity> getSpecialistBySection_sponsored_names(DoctorSectionBranchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<SpecialistEntity> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					" SELECT distinct spec.* FROM specialist spec \r\n" +
					"INNER JOIN sections_has_specialist section  ON section.specialist_id = spec.id \r\n" +
					"INNER JOIN users users ON users.id = spec.users_id \r\n" + 
						
					"where spec.sponsored is not null and spec.sponsored=1 \r\n" + 
					"and spec.sponsored_from is not null and spec.sponsored_to is not null \r\n" + 
					"and spec.sponsored_from<=CURDATE() and spec.sponsored_to>=CURDATE() \r\n  " +
					
					" and ( users.firstname like :search_text or users.lastname like :search_text or spec.name like :search_text ) \r\n" +

					"");

		if(utils.hasValue(ignored_ids))
			sql.append("and spec.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
		
		sql.append(" order by users.firstname ");
		
		try
		{
			Query query = hiber.getSession()
					.createSQLQuery(sql.toString())
					.addEntity(SpecialistEntity.class)
					.setString("search_text", "%" + criteria.getDoctorName() + "%");
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getSpecialistBySection : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
		
	public List<SpecialistEntity> getSpecialistBySection_sponsored_keywords(DoctorSectionBranchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<SpecialistEntity> results = null;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(
					" SELECT distinct spec.* FROM specialist spec \r\n" +
					"INNER JOIN sections_has_specialist section  ON section.specialist_id = spec.id \r\n" +
					"INNER JOIN users users ON users.id = spec.users_id \r\n" + 
					
					" inner join doctor_has_keyword dhk on dhk.doctor_id = spec.id \r\n"+
					
					" inner join keyword keyword on keyword.id = dhk.keyword_id \r\n"+

					" where keyword.name like :search_text \r\n  "+
					" and spec.sponsored is not null and spec.sponsored=1 \r\n" + 
					" and spec.sponsored_from is not null and spec.sponsored_to is not null \r\n" + 
					" and spec.sponsored_from<=CURDATE() and spec.sponsored_to>=CURDATE() \r\n  " );
		
		if(utils.hasValue(ignored_ids))
			sql.append("and spec.id not in ("+utils.generateInt(ignored_ids)+") \r\n");

		sql.append(" order by users.firstname ");

		try
		{
			Query query = hiber.getSession()
							.createSQLQuery(sql.toString())
							.addEntity(SpecialistEntity.class)
							.setString("search_text", "%" + criteria.getDoctorName() + "%");
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			
			results = query.list();

			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getSpecialistBySection : " + e.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return results;
	}
	

	public List<SpecialistEntity> getSpecialistBySectionQuery_all(DoctorSectionBranchCriteria search, int max_results, List<Integer> ignored_ids)
	{
		List<SpecialistEntity> entities = null;
		try
		{
			StringBuilder sql =  new StringBuilder();
			StringBuilder conditions = new StringBuilder();

			sql.append(
						" SELECT distinct spec.* FROM specialist spec \r\n" +
						"INNER JOIN sections_has_specialist section  ON section.specialist_id = spec.id \r\n" +
						"INNER JOIN users users ON users.id = spec.users_id \r\n" + 
						" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n" +
						
						" left outer join doctor_has_keyword dhk on dhk.doctor_id = spec.id \r\n"+
						
						" left outer join keyword keyword on keyword.id = dhk.keyword_id \r\n"+
						
						"");
			
			if (search.getInsuranceCompany() > 0)

				sql.append(
							" inner join specialist_has_branch shb ON spec.id = shb.specialist_id \r\n" +
							" inner join branch branch ON branch.id = shb.branch_id \r\n" +
							" inner join unit unit ON unit.id = branch.unit_unit_id \r\n" +
							" inner join unit_has_insurance_company uhic ON unit.id = uhic.unit_id \r\n" +
							" inner join insurance_company insurance_company ON uhic.insurance_company_id = insurance_company.id \r\n") ;
			
			
			if (search.getSection_id() > 0)
				conditions.append(" where sections_id = :sectionId \r\n");
			if (utils.hasValue(search.getDoctorName()))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append("  ( users.firstname like '%"+search.getDoctorName()+"%' or users.lastname like '%"+search.getDoctorName()+"%' or spec.name like '%"+search.getDoctorName()+"%' or keyword.name like '%"+search.getDoctorName()+"%') \r\n");
			}
			
			if(utils.hasValue(ignored_ids))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append( " b.id not in ("+utils.generateInt(ignored_ids)+") \r\n");		
			}
			
			if (search.getInsuranceCompany() > 0)
			{			
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");

				conditions.append(" insurance_company.id = :insurance_companyId \r\n");
			}
				
			if (search.isOrderbyrate())
				conditions.append(" order by rate desc  \r\n");
			else
				conditions.append(" order by users.firstname \r\n");

			
			sql.append(conditions);

			System.out.println(sql);
			
			Query query = hiber.getSession().createSQLQuery(sql.toString()).addEntity(SpecialistEntity.class);
						
			if (search.getSection_id() > 0)
				query.setInteger("sectionId", search.getSection_id());
						
			if (search.getInsuranceCompany() > 0)
				query.setInteger("insurance_companyId", search.getInsuranceCompany());
			
			query.setFirstResult(search.getStart());
			query.setMaxResults(max_results);
			//
			System.out.println(query);
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println("getSpecialistBySection Get Ex : " + ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	
	
	
	
	public List<SpecialistEntity> getSpecialistBySection_names(DoctorSectionBranchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<SpecialistEntity> entities = null;
		try
		{
			StringBuilder sql =  new StringBuilder();
			StringBuilder conditions = new StringBuilder();

			sql.append(
						" SELECT distinct spec.* FROM specialist spec \r\n" +
						"INNER JOIN sections_has_specialist section  ON section.specialist_id = spec.id \r\n" +
						"INNER JOIN users users ON users.id = spec.users_id \r\n" + 
						" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n" +
						"");
			
			if (criteria.getInsuranceCompany() > 0)

				sql.append(
							" inner join specialist_has_branch shb ON spec.id = shb.specialist_id \r\n" +
							" inner join branch branch ON branch.id = shb.branch_id \r\n" +
							" inner join unit unit ON unit.id = branch.unit_unit_id \r\n" +
							" inner join unit_has_insurance_company uhic ON unit.id = uhic.unit_id \r\n" +
							" inner join insurance_company insurance_company ON uhic.insurance_company_id = insurance_company.id \r\n") ;
			
			
			if (criteria.getSection_id() > 0)
				conditions.append(" where sections_id = :sectionId \r\n");
			if (utils.hasValue(criteria.getDoctorName()))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				conditions.append("  ( users.firstname like '%"+criteria.getDoctorName()+"%' or users.lastname like '%"+criteria.getDoctorName()+"%' or spec.name like '%"+criteria.getDoctorName()+"%' ) \r\n");
			}
			
			if (criteria.getInsuranceCompany() > 0)
			{			
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");

				conditions.append(" insurance_company.id = :insurance_companyId \r\n");
			}
			
			if(utils.hasValue(ignored_ids))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append(" spec.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
			}
			
			if (criteria.isOrderbyrate())
				conditions.append(" order by users.firstname,rate desc  \r\n");
			else
				conditions.append(" order by users.firstname ");
			
			sql.append(conditions);
			
			System.out.println(sql);

			Query query = hiber.getSession().createSQLQuery(sql.toString()).addEntity(SpecialistEntity.class);
						
			if (criteria.getSection_id() > 0)
				query.setInteger("sectionId", criteria.getSection_id());
						
			if (criteria.getInsuranceCompany() > 0)
				query.setInteger("insurance_companyId", criteria.getInsuranceCompany());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			//
			System.out.println(query);
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println("getSpecialistBySection Get Ex : " + ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	public List<SpecialistEntity> getSpecialistBySection_keywords(DoctorSectionBranchCriteria criteria, int max_results, List<Integer> ignored_ids)
	{
		List<SpecialistEntity> entities = null;
		try
		{
			StringBuilder sql =  new StringBuilder();
			StringBuilder conditions = new StringBuilder();

			sql.append(
						" SELECT distinct spec.* FROM specialist spec \r\n" +
						"INNER JOIN sections_has_specialist section  ON section.specialist_id = spec.id \r\n" +
						"INNER JOIN users users ON users.id = spec.users_id \r\n" + 
						" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n" +
						
						" inner join doctor_has_keyword dhk on dhk.doctor_id = spec.id \r\n"+
						
						" inner join keyword keyword on keyword.id = dhk.keyword_id \r\n"+
						
						"");
			
			if (criteria.getInsuranceCompany() > 0)

				sql.append(
							" inner join specialist_has_branch shb ON spec.id = shb.specialist_id \r\n" +
							" inner join branch branch ON branch.id = shb.branch_id \r\n" +
							" inner join unit unit ON unit.id = branch.unit_unit_id \r\n" +
							" inner join unit_has_insurance_company uhic ON unit.id = uhic.unit_id \r\n" +
							" inner join insurance_company insurance_company ON uhic.insurance_company_id = insurance_company.id \r\n") ;
			
			
			if (criteria.getSection_id() > 0)
				conditions.append(" where sections_id = :sectionId \r\n");
		
//			if (utils.hasValue(criteria.getDoctorName()))
//			{
//				if (conditions.length()==0)
//					conditions.append( " where ");
//				else
//					conditions.append( " and ");
//				conditions.append("  ( users.firstname like '%"+criteria.getDoctorName()+"%' or users.lastname like '%"+criteria.getDoctorName()+"%' or spec.name like '%"+criteria.getDoctorName()+"%' ) \r\n");
//			}
			
			if (utils.hasValue(criteria.getDoctorName()))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				conditions.append("  keyword.name like '%"+criteria.getDoctorName()+"%' \r\n");
			}
			
			if (criteria.getInsuranceCompany() > 0)
			{			
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");

				conditions.append(" insurance_company.id = :insurance_companyId \r\n");
			}
			
			if(utils.hasValue(ignored_ids))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append(" spec.id not in ("+utils.generateInt(ignored_ids)+") \r\n");
			}
			
			if (criteria.isOrderbyrate())
				conditions.append(" order by users.firstname,rate desc  \r\n");
			else
				conditions.append(" order by users.firstname ");
			
			sql.append(conditions);
			
			System.out.println(sql);

			Query query = hiber.getSession().createSQLQuery(sql.toString()).addEntity(SpecialistEntity.class);
						
			if (criteria.getSection_id() > 0)
				query.setInteger("sectionId", criteria.getSection_id());
						
			if (criteria.getInsuranceCompany() > 0)
				query.setInteger("insurance_companyId", criteria.getInsuranceCompany());
			
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(max_results);
			//
			System.out.println(query);
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println("getSpecialistBySection Get Ex : " + ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	

	public List<SpecialistEntity> getSpecialistBySectionQuery_all_original(DoctorSectionBranchCriteria search)
	{
		List<SpecialistEntity> entities = null;
		try
		{
			StringBuilder sql =  new StringBuilder();
			StringBuilder conditions = new StringBuilder();

			sql.append(
						" SELECT distinct spec.* FROM specialist spec \r\n" +
						"INNER JOIN sections_has_specialist section  ON section.specialist_id = spec.id \r\n" +
						"INNER JOIN users users ON users.id = spec.users_id \r\n" + 
						" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n" +
						"");
			
			if (search.getInsuranceCompany() > 0)

				sql.append(
							" inner join specialist_has_branch shb ON spec.id = shb.specialist_id \r\n" +
							" inner join branch branch ON branch.id = shb.branch_id \r\n" +
							" inner join unit unit ON unit.id = branch.unit_unit_id \r\n" +
							" inner join unit_has_insurance_company uhic ON unit.id = uhic.unit_id \r\n" +
							" inner join insurance_company insurance_company ON uhic.insurance_company_id = insurance_company.id \r\n") ;
			
			
			if (search.getSection_id() > 0)
				conditions.append(" where sections_id = :sectionId \r\n");
			if (utils.hasValue(search.getDoctorName()))
			{
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");
				
				conditions.append("  ( users.firstname like '%"+search.getDoctorName()+"%' or users.lastname like '%"+search.getDoctorName()+"%' or spec.name like '%"+search.getDoctorName()+"%' ) \r\n");
			}
			
			if (search.getInsuranceCompany() > 0)
			{			
				if (conditions.length()==0)
					conditions.append( " where ");
				else
					conditions.append( " and ");

				conditions.append(" insurance_company.id = :insurance_companyId \r\n");
			}
				
			if (search.isOrderbyrate())
				conditions.append(" order by rate desc  \r\n");
			
			sql.append(conditions);

			System.out.println(sql);
			
			Query query = hiber.getSession().createSQLQuery(sql.toString()).addEntity(SpecialistEntity.class);
						
			if (search.getSection_id() > 0)
				query.setInteger("sectionId", search.getSection_id());
						
			if (search.getInsuranceCompany() > 0)
				query.setInteger("insurance_companyId", search.getInsuranceCompany());
			
			query.setFirstResult(search.getStart());
			query.setMaxResults(search.getLimit());
			//
			System.out.println(query);
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println("getSpecialistBySection Get Ex : " + ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	public List<Integer> getIds(List<SpecialistEntity> results)
	{
		List<Integer> ids = new ArrayList<Integer>();
		
		if (results != null)
		{
			for (SpecialistEntity inst : results)
			{				
				ids.add(inst.getId());
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

