package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.SchedulerQuartz;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistHasBranchEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

/**
 * @author JDeeb
 * Specialist Has Branch DAO
 */
public class SpecialistHasBranchDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	private Utils utils = Utils.getInstance(); 
	
	public SpecialistHasBranchDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(SpecialistHasBranchEntity entity){
		try{
		Serializable ser = hiber.getSession().save(entity);
		entity.setId(ser.hashCode());
		hiber.getSession().getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			hiber.closeSession();
		}
	}
	public boolean update(SpecialistHasBranchEntity entity) {
        if(entity == null)
        	return false;
		try {
            hiber.getSession().update(entity);
            hiber.getSession().getTransaction().commit();
            return true;
        } catch (Exception ex) {
             System.out.println("Inesrt Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return false;
    }

    public boolean delete(SpecialistHasBranchEntity entity) {
        if(entity == null || entity.getId() < 1)
        	return false;
    	try {
            hiber.getSession().delete(entity);
            hiber.getSession().getTransaction().commit();
            return true ;
        } catch (Exception ex) {
             System.out.println("Delete Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return false;
    }
    
    public List<SpecialistHasBranchEntity> getAll() {
        List<SpecialistHasBranchEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT specialist_has_branch.* FROM specialist_has_branch specialist_has_branch ")
                    .addEntity("specialist_has_branch.", SpecialistHasBranchEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public SpecialistHasBranchEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	SpecialistHasBranchEntity entity = null;
        try {
        	entity = (SpecialistHasBranchEntity) hiber.getSession().get(SpecialistHasBranchEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    
	public List<SpecialistHasBranchEntity> searchDoctorsClinics(SearchDoctorsCriteria search)
	{		
		List<SpecialistHasBranchEntity> entities = null;
		try
		{
			String sql = 
						" SELECT distinct shb.* \r\n" + 
						"from specialist_has_branch shb \r\n" +
						"inner join specialist spec on(shb.specialist_id=spec.id) \r\n" + 
						"INNER JOIN sections_has_specialist section ON section.specialist_id = spec.id \r\n" + 
						" INNER JOIN users users ON users.id = spec.users_id \r\n" + 
//						" INNER JOIN users_groups grou ON spec.users_id = grou.users_id \r\n" + 
						" LEFT OUTER JOIN user_rate_spec rat ON spec.id = rat.specialist_id \r\n" + 
						" INNER JOIN distric dist ON dist.id = users.district_id \r\n" + 
						" INNER JOIN city cit ON cit.id = dist.city_id \r\n" +
//						" WHERE groups_id = :groupId  " +
						"";
			
			String condition = "";
			
			if (search.getSectionId() > 0)
				condition += (utils.hasValue(condition)?" and " : " where ") +  "   sections_id = :sectionId ";
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
				condition += (utils.hasValue(condition)?" and " : " where ") +  " order by rate desc       ";
			
			sql+=condition;
			
			Query query = hiber.getSession().createSQLQuery(sql).addEntity(SpecialistHasBranchEntity.class);
			
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


}
