package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CertificationEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.DoctorHasKeywordEntity;
//import com.sget.akshef.hibernate.entities.DegreeEntity;
import com.sget.akshef.hibernate.entities.MessagesEntity;
import com.sget.akshef.hibernate.entities.ProfessionalExpEntity;
import com.sget.akshef.hibernate.entities.ScheduleHasDaysEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SectionsHasSpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistHasBranchEntity;
import com.sget.akshef.hibernate.entities.UserRateSpecEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.DoctorAppointmentCriteria;
import com.sget.akshf.searchcriteria.DoctorSectionBranchCriteria;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

/**
 * @author JDeeb Specialist DAO
 */
public class SpecialistDAO
{
	
	// Hibernate Session Class
	HibernateSession hiber;
	
	private Utils utils = Utils.getInstance(); 
	
	public SpecialistDAO()
	{
		hiber = new HibernateSession();
	}
	
	
	
	public void insert(SpecialistEntity entity)
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
	
	public boolean saveDoctor(SpecialistEntity entity, UsersEntity user_recommend)
	{
		try
		{
			if(!utils.hasValue(entity.getUsers().getCompany()) || !utils.hasValue(entity.getUsers().getCompany().getId()))
				entity.getUsers().setCompany(null);
//		
			if(!utils.hasValue(entity.getUsers().getDistric()) || !utils.hasValue(entity.getUsers().getDistric().getId()))
				entity.getUsers().setDistric(null);
			
			entity.getUsers().setActive(0);
			entity.setActive(0);
			
			Serializable ser = null;
			
			//save recommend user
			if(user_recommend!=null)
			{
				ser = hiber.getSession().save(user_recommend);
				entity.setUser_recommend(ser.hashCode());
			}
			
			//save doctor user
			ser = hiber.getSession().save(entity.getUsers());
			entity.setUsers(entity.getUsers());
			
			if(utils.hasValue(entity.getSectionsHasSpecialistSet()) && entity.getSectionsHasSpecialistSet().size()>0)
			{
				for(SectionsHasSpecialistEntity sectionsHasSpecialist : entity.getSectionsHasSpecialistSet())
				{
					SectionsEntity secition = (SectionsEntity) hiber.getSession().get(SectionsEntity.class, sectionsHasSpecialist.getSections().getId()) ;
					
					sectionsHasSpecialist.setSections(secition);
				}
			}
			
			//save doctor
			ser = hiber.getSession().save(entity);
			entity.setId(ser.hashCode());
			
			hiber.getSession().getTransaction().commit();
			
			return true;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			
			return false;
		}
		finally
		{
			hiber.closeSession();
		}
	}
	
	public void insertCertification(CertificationEntity certEnt)
	{
		try
		{
			Serializable ser = hiber.getSession().save(certEnt);
			certEnt.setId(ser.hashCode());
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
	
	
	
	public void insertProfessional(ProfessionalExpEntity certEnt)
	{
		try
		{
			Serializable ser = hiber.getSession().save(certEnt);
			certEnt.setId(ser.hashCode());
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
	
	
	
	public boolean update(SpecialistEntity entity)
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
			System.out.println("Inesrt Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	public boolean delete(SpecialistEntity entity)
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
			System.out.println("Delete Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	public List<SpecialistEntity> getAll()
	{
		List<SpecialistEntity> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT specialist.* FROM specialist specialist ").addEntity("specialist.", SpecialistEntity.class);
			entities = query.list();
			hiber.getSession().getTransaction().commit();
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
	
	
	
//	public List<DegreeEntity> getAllDegree()
//	{
//		List<DegreeEntity> entities = null;
//		try
//		{
//			Query query = hiber.getSession().createSQLQuery("SELECT degree.* FROM degree degree ").addEntity("degree.", DegreeEntity.class);
//			entities = query.list();
//			hiber.getSession().getTransaction().commit();
//		}
//		catch (Exception ex)
//		{
//			System.out.println("Get Ex : " + ex.getMessage());
//		}
//		finally
//		{
//			hiber.closeSession();
//		}
//		return entities;
//	}
	
	
	
	// public List<SpecialistEntity>
	// getAllDetailsByCatId(DoctorSectionBranchCriteria
	// doctorSectionBranchCriteria) {
	// List<SpecialistEntity> entities = null;
	// String
	// sqlString="SELECT distinct specialist.* FROM specialist specialist ";
	// try {
	// if(doctorSectionBranchCriteria.getSection_id()!=0)
	// {
	//
	// sqlString+="inner join sections_has_specialist on  specialist.id=sections_has_specialist.specialistid and "
	// +
	// "sections_has_specialist.sections_id="+doctorSectionBranchCriteria.getSection_id();
	// }
	//
	// if(doctorSectionBranchCriteria.getSection_id()!=0)
	// {
	//
	// sqlString+="inner join sections_has_specialist on  specialist.id=sections_has_specialist.specialistid and "
	// +
	// "sections_has_specialist.sections_id="+doctorSectionBranchCriteria.getSection_id();
	// }
	//
	// Query query =hiber.getSession().createSQLQuery(sqlString)
	// .addEntity("specialist.", SpecialistEntity.class);
	// entities = query.list();
	//
	// } catch (Exception ex) {
	// System.out.println("Get Ex : " + ex.getMessage());
	// } finally{
	// hiber.closeSession();
	// }
	// return entities;
	// }
	
	public SpecialistEntity getById(int id)
	{
		if (id < 1)
			return null;
		
		SpecialistEntity entity = null;
		try
		{
			entity = (SpecialistEntity) hiber.getSession().get(SpecialistEntity.class, id);
			hiber.getSession().getTransaction().commit();
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entity;
	}
	
	
	
	// For Web Service
	/**
	 * Get All Doctors By BranchId , SectionId , Doctor Name and order
	 * 
	 * @param search
	 * @return
	 */
	public List<SpecialistEntity> getSpecialistBySectionAndBranch(DoctorSectionBranchCriteria search)
	{
		List<SpecialistEntity> entities = null;
		try
		{
			
			String sql = "select distinct spec.* from sections_has_specialist section "
					+ "inner join specialist spec  on section.specialist_id = spec.id"
					+ " inner join specialist_has_branch branch on spec.id = branch.specialist_id " + 
//					" inner join users_groups grou on spec.users_id = grou.users_id " + 
					" left outer join user_rate_spec rat on spec.id = rat.specialist_id " + 
					" where branch_id = ? and sections_id = ? ";
//					" where branch_id = ? and sections_id = ? and groups_id = ?";

			if (search.isOrderbyrate())
				sql += " order by rate desc ";
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SpecialistEntity.class);
			
			query.setInteger(0, search.getBranch_id());
			query.setInteger(1, search.getSection_id());
//			query.setInteger(2, DBConstants.GROUP_SPECIALIST);
			
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println("getSpecialistBySectionAndBranch Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	public List<SpecialistEntity> getSpecialistBySectionWeb(DoctorSectionBranchCriteria search)
	{
		List<SpecialistEntity> entities = null;
		try
		{
			
			String sql = "select distinct spec.* from  specialist spec , sections_has_specialist section    "
			
			+ " where  section.specialist_id=spec.id and sections_id = :sectionId ";
			if (search.getDoctorName() != null && !search.getDoctorName().trim().equalsIgnoreCase(""))
				sql += " AND ( name like :doctorName  ) ";
			if (search.isOrderbyrate())
				sql += " order by rate desc  ";
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SpecialistEntity.class);
			
			query.setInteger("sectionId", search.getSection_id());
			if (search.getDoctorName() != null && !search.getDoctorName().trim().equalsIgnoreCase(""))
				query.setInteger("doctorName", search.getSection_id());//
				
				
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println("getSpecialistBySectionAndBranch Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	/**
	 * Get All Doctors By SectionId , Doctor Name and order
	 * 
	 * @param search
	 * @return
	 */
	public SpecialistEntity getSpecialistByUserID(int user_id)
	{
		SpecialistEntity entity = null;
		List<SpecialistEntity> entities = null;
		try
		{
			String sql = "select * from specialist s where s.users_id=:user_id";
			
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SpecialistEntity.class);
			query.setInteger("user_id", user_id);
			entities = query.list();
			if (entities != null && !entities.isEmpty())
				entity = entities.get(0);
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getSpecialistMessages : " + e.getMessage());
		}
		return entity;
		
	}
	
	
	
	public List<SpecialistEntity> getSpecialistBySection(DoctorSectionBranchCriteria search)
	{
		List<SpecialistEntity> entities = null;
		try
		{
			String sql = 
					" SELECT distinct spec.* FROM specialist spec \r\n"
					+ "INNER JOIN sections_has_specialist section  ON section.specialist_id = spec.id \r\n"
					+ "INNER JOIN users users ON users.id = spec.users_id \r\n" + 
//					" INNER JOIN users_groups grou ON spec.users_id = grou.users_id \r\n" + 
					" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n" +

//					" WHERE groups_id = :groupId " +
					 "";
			if (search.getInsuranceCompany() > 0)

				sql+=
					" inner join specialist_has_branch shb ON spec.id = shb.specialist_id \r\n" +
					" inner join branch branch ON branch.id = shb.branch_id \r\n" +
					" inner join unit unit ON unit.id = branch.unit_unit_id \r\n" +
					" inner join unit_has_insurance_company uhic ON unit.id = uhic.unit_id \r\n" +
					" inner join insurance_company insurance_company ON uhic.insurance_company_id = insurance_company.id \r\n" ;
			
			if (search.getSection_id() > 0)
				sql += " where sections_id = :sectionId \r\n";
			if (search.getDoctorName() != null && !search.getDoctorName().trim().equalsIgnoreCase(""))
			{
				if (search.getSection_id() > 0)
					sql+=" and ";
				else
					sql+="where ";
				sql += "  ( users.firstname like '%"+search.getDoctorName()+"%' or users.lastname like '%"+search.getDoctorName()+"%' or spec.name like '%"+search.getDoctorName()+"%' ) \r\n";
			}
			
			if (search.getInsuranceCompany() > 0)
			{			
				if (search.getSection_id() > 0 || (search.getDoctorName() != null && !search.getDoctorName().trim().equalsIgnoreCase("")))
					sql+=" and ";
				else
					sql+=" where ";

				sql += " insurance_company.id = :insurance_companyId \r\n";
			}
				
			if (search.isOrderbyrate())
				sql += " order by rate desc  \r\n";
			
			System.out.println(sql);
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SpecialistEntity.class);
			
//			query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (search.getSection_id() > 0)
				query.setInteger("sectionId", search.getSection_id());
						
			if (search.getInsuranceCompany() > 0)
				query.setInteger("insurance_companyId", search.getInsuranceCompany());
			
//			if (search.getDoctorName() != null && !search.getDoctorName().trim().equalsIgnoreCase(""))
//				query.setString("doctorName", search.getDoctorName());
			
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
	
	
	
	/**
	 * Advanced Search Doctors
	 * 
	 * @param search
	 * @return
	 */
	public List<SpecialistEntity> searchDoctors(SearchDoctorsCriteria search)
	{
		List<SpecialistEntity> entities = null;
		try
		{
			String sql = 
							" SELECT distinct spec.*,users.district_id " +
							" FROM specialist spec " +
							" INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id " + 
							" INNER JOIN users users ON users.id = spec.users_id " + 
//							" INNER JOIN users_groups grou ON spec.users_id = grou.users_id " + 
							" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id " + 
							" INNER JOIN distric dist ON dist.id = users.district_id " + 
							" INNER JOIN city cit ON cit.id = dist.city_id " +
//							" WHERE groups_id = :groupId  " +
							 "";
			
			String condition = "";
			
			if (search.getSectionId() > 0)
				condition += (utils.hasValue(condition)?" and " : " where ") + "  sections_id = :sectionId ";
			if (search.getCountryId() > 0)
				condition += (utils.hasValue(condition)?" and " : " where ") +  "   cit.country_id = :countryId ";
			if (search.getCityId() > 0)
				condition += (utils.hasValue(condition)?" and " : " where ") +  "  cit.id = :cityId ";
			if (search.getDistrictId() > 0)
				condition += (utils.hasValue(condition)?" and " : " where ") +  "   dist.id = :districId ";
			if (search.getDegree() > 0)
				condition += (utils.hasValue(condition)?" and " : " where ") +  "  spec.degree_id = :degreeId ";
			if (search.getGender() == DBConstants.USER_GENDER_FEMALE || search.getGender() == DBConstants.USER_GENDER_MALE)
				condition += (utils.hasValue(condition)?" and " : " where ") +  "  users.gender = :genderId ";
			if (search.getDoctorName() != null && !search.getDoctorName().trim().equalsIgnoreCase(""))
				condition += (utils.hasValue(condition)?" and " : " where ") +  "  ( users.firstname like :doctorName or users.lastname like :doctorName ) ";
			if (search.isOrderbyrate())
				condition += " order by rate desc       ";
			
			sql+=condition;
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SpecialistEntity.class);
			
			// Set Parameters
//			query.setInteger("groupId", DBConstants.GROUP_SPECIALIST);
			
			if (search.getSectionId() > 0)
				query.setInteger("sectionId", search.getSectionId());
			if (search.getCountryId() > 0)
				query.setInteger("countryId", search.getCountryId());
			if (search.getCityId() > 0)
				query.setInteger("cityId", search.getCityId());
			if (search.getDistrictId() > 0)
				query.setInteger("districId", search.getDistrictId());
			if (search.getDegree() > 0)
				query.setInteger("degreeId", search.getDegree());
			if (search.getGender() == DBConstants.USER_GENDER_FEMALE || search.getGender() == DBConstants.USER_GENDER_MALE)
				query.setInteger("genderId", search.getGender());
			if (search.getDoctorName() != null && !search.getDoctorName().trim().equalsIgnoreCase(""))
				query.setString("doctorName", search.getDoctorName());
			
			query.setFirstResult(search.getStart());
			query.setMaxResults(search.getLimit());
			
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println("searchDoctors Get Ex : " + ex.getMessage());
			ex.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	

	
	
	public List<ScheduleHasDaysEntity> getSpecialistSchedule(DoctorAppointmentCriteria search)
	{
		List<ScheduleHasDaysEntity> entities = null;
		try
		{
			
			String sql = "select da.* from " + " ( SELECT sec.id FROM specialist_has_branch sec where sec.specialist_id = ? and branch_id = ? ) as se " + " inner join schedule_has_days da on da.specialist_has_branch_id = se.id";
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(ScheduleHasDaysEntity.class);
			
			query.setInteger(0, search.getDoctor_id());
			query.setInteger(1, search.getBranch_id());
			
			entities = query.list();
			hiber.getSession().getTransaction().commit();
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
	
	
	
	// Get speciliast messages
	
	
	// Get speciliast messages
	public List<MessagesEntity> getSpecialistMessages(int doctorId)
	{
		List<MessagesEntity> entities = null;
		try
		{
			String sql = "select m.* from messages m,specialist s where s.users_id=m.to_user and m.to_user =:doctorId";
			
			
			// String
			// sql="SELECT bran.* from specialist_has_branch spec ,schedule_has_days sced,branch bran "
			// +
			// "where sced.specialist_has_branch_id=spec.id " +
			// "and spec.branch_id=bran.id " +
			// "and spec.specialist_id=:doctorId " +
			// "and sced.days_id=:dayId";
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(MessagesEntity.class);
			query.setInteger("doctorId", doctorId);
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getSpecialistMessages : " + e.getMessage());
		}
		return entities;
	}
	
	
	
	// Get Specialist Places
	public List<BranchEntity> getSpecialistPlaces(int doctorId, int dayId)
	{
		List<BranchEntity> entities = null;
		try
		{
			String sql = 
					"SELECT ba.*"
					+ " FROM specialist_has_branch bran "
//					+ "inner join schedule_has_days da on da.specialist_has_branch_id = bran.id "
					+ " inner join branch ba on ba.id = bran.branch_id " 
					+ " inner join distric distric on distric.id = ba.distric_id " 
					+ "  where bran.specialist_id = :doctorId "
//					+ "and da.days_id = :dayId "
					;
			
			
			// String
			// sql="SELECT bran.* from specialist_has_branch spec ,schedule_has_days sced,branch bran "
			// +
			// "where sced.specialist_has_branch_id=spec.id " +
			// "and spec.branch_id=bran.id " +
			// "and spec.specialist_id=:doctorId " +
			// "and sced.days_id=:dayId";
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
			query.setInteger("doctorId", doctorId);
//			query.setInteger("dayId", dayId);
			entities = query.list();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception e)
		{
			System.out.println("Get Ex in getSpecialistPlaces : " + e.getMessage());
		}
		return entities;
	}
	
	
	
	// Get Specialist Rating
	public List<UserRateSpecEntity> getSpecialistRating(int specId)
	{
		List<UserRateSpecEntity> entities = null;
		if (specId > 0)
		{
			try
			{
				String sql = "SELECT ra.* FROM user_rate_spec ra WHERE specialist_id = :specid";
				Query query = hiber.getSession().createSQLQuery(sql).addEntity(UserRateSpecEntity.class);
				query.setInteger("specid", specId);
				entities = query.list();
				
			}
			catch (Exception e)
			{
				System.out.println("Ex in getSpecialistRating : " + e.getMessage());
			}
			finally
			{
				hiber.closeSession();
			}
		}
		
		return entities;
	}
	
	
	
	// JDeeb get specialist price
	public double getSpecialistPrise(int subCat, int sect, int branch)
	{
		if (subCat <= 0 || sect <= 0 || branch <= 0)
			return 0;
		double resul = 0;
		try
		{
			String sql = " SELECT subSecBranch.price "
					+ "		FROM subcategory_has_sections_has_branch subSecBranch " 
						+ " inner join subcategory_has_sections subSec on subSec.id = subSecBranch.subcategorysection_id"
						+ " where subSec.subcategory_subcategory_id = ? " + " and subSec.sections_section_id = ? and subSecBranch.branch_id = ? ";
			
			Query query = hiber.getSession().createSQLQuery(sql);
			
			query.setInteger(0, subCat);
			query.setInteger(1, sect);
			query.setInteger(2, branch);
			
			resul = (Double) query.uniqueResult();
			hiber.getSession().getTransaction().commit();
		}
		catch (Exception ex)
		{
			System.out.println("Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return resul;
	}
	
	
	
	public List<Object> getAllCommentsByDoctor(int doctorId)
	{
		List<Object> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT comments.*,users.* FROM comments comments,users  users where " + " comments.users_id=users.id" + " and specialist_id=:doctorId ").addEntity("comments.", CommentsEntity.class).addEntity("users.", UsersEntity.class).setInteger("doctorId", doctorId);
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
	
	
	
	public List<Object> getMessagesBranch()
	{
		List<Object> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery(
					
					"select m.* ,b.name_ar from messages m"
//					+ ",users_groups ug"
					+ ",branch b,branch_groups bg  " +
					"where b.id=bg.branch_id  ");
//					"where to_user=ug. users_id and b.id=bg.branch_id and bg.users_groups_id=ug.id and ug.groups_id=5 ");
			
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
	
	
	
	public List<Object> getMessagesSpecialist()
	{
		List<Object> entities = null;
		try
		{
			Query query = hiber.getSession().createSQLQuery("SELECT messages.*, spec.name   FROM messages messages," + " specialist spec WHERE spec. users_id=messages.to_user or  spec. users_id=messages.from_user ");
			
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
	
	
	
	public SpecialistEntity getDoctorById(int user_id)
	{		
		if (user_id < 1)
			return null;
		
		SpecialistEntity entity = null;
		try
		{
			entity = (SpecialistEntity) hiber.getSession()
						.createCriteria(SpecialistEntity.class)
						.add(Restrictions.eq("this.users.id", user_id))
						.uniqueResult();
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entity;
	}
	

	public List<DoctorHasKeywordEntity> loadDoctorHasKeyword(Integer doctor_id)
	{
		List<DoctorHasKeywordEntity> list = null;
		try
		{
			if(doctor_id==null)
				return null;
			
			list = hiber.getSession()
					.createCriteria(DoctorHasKeywordEntity.class)
					.add(Restrictions.eq("this.doctor.id", doctor_id))
					.createAlias("this.keyword", "keyword")
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
}
