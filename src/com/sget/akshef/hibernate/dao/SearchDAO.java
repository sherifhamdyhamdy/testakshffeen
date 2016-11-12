package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CityEntity;
import com.sget.akshef.hibernate.entities.ClinicsEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;

/**
 * @author JDeeb
 * Clinics DAO
 */
public class SearchDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public SearchDAO(){
		hiber = new HibernateSession();
	}
	
public List<BranchEntity> searchBranch(Map searchCrietria)
{
	List<BranchEntity> entities = null;
	try{
	String sqlString="SELECT branch.* FROM branch branch ";
	Query query = null ;
	if(searchCrietria!=null && !searchCrietria.isEmpty())
	{
		
		if(searchCrietria.get("district")!=null &&!searchCrietria.get("district").equals("")) 
		{
			int distric_id=Integer.parseInt(searchCrietria.get("district").toString());
			if((searchCrietria.get("category")==null ||searchCrietria.get("category").equals(""))
					&& (searchCrietria.get("sub")==null ||searchCrietria.get("sub").equals(""))	
					&& (searchCrietria.get("section")==null ||searchCrietria.get("section").equals(""))		
						
				  )
			{
				sqlString="SELECT branch.* FROM branch branch where distric_id=:1 ";
				 query = hiber.getSession().createSQLQuery(sqlString)
		         .addEntity("branch.", BranchEntity.class).setInteger("1",distric_id);
			}
			
			
		
			else	if(searchCrietria.get("category")!=null &&!searchCrietria.get("category").equals("")
				&& (searchCrietria.get("sub")==null ||searchCrietria.get("sub").equals(""))	
				&& (searchCrietria.get("section")==null ||searchCrietria.get("section").equals(""))		
					
			  )
			{
				sqlString="SELECT branch.* FROM branch branch ,unit unit   where  branch.unit_unit_id= unit.id and distric_id=:1" +
						" and  unit.category_id=:2";
				int  category_id=Integer.parseInt(searchCrietria.get("category").toString());
				 query = hiber.getSession().createSQLQuery(sqlString)
				         .addEntity("branch.", BranchEntity.class).setInteger("1",distric_id).setInteger("2", category_id);
			}
			
			
		
		
		
		
		}
		
		
	}
	
	  entities = query.list();
      
} catch (Exception ex) {
	ex.printStackTrace();
     System.out.println("Get Ex : " + ex.getMessage());
} finally{
    hiber.closeSession();
}
	return entities;
}
	
	
public List<Object[]> advancedSearchForBranches(AdvancedSearchCriteria criteria){
	if(criteria == null)
		return null ;
	List<Object[]> entities = null;
	try{
		String sql = " SELECT DISTINCT branch.* , un.category_id as category_id " ;
		String bodySQL = " FROM branch branch "
					+ "	inner join subcategory_has_sections_has_branch subsect on branch.id = subsect.branch_id "
					+ " inner join subcategory_has_sections sect on subsect.subcategorysection_id = sect.id inner join unit un on un.id = branch.unit_unit_id "
					+ " inner join distric dist on dist.id = branch.distric_id inner join city on city.id = dist.city_id "
					+ " inner join specialist_has_branch specbran on specbran.branch_id = branch.id inner join specialist spe on spe.id = specbran.specialist_id "
					+ " inner join schedule_has_days days on days.specialist_has_branch_id = specbran.id inner join schedule sch on sch.id = days.schedule_id WHERE branch.active = 1 " ;
		
		// where sect.subcategory_subcategory_id = 20 and sect.sections_section_id = 45 and un.category_id = 40 and city.country_id = 1 and city.id = 2
		// and dist.id = 1 and spe.name like '%MAGDY%' and days.days_id between 1 and 2 and sch.from >= '2015-06-01' and sch.to <= '2015-06-08'

		String endSQL = " having distance < 5 " ;
		
		String condition = " ";
		if(criteria.getLatitude() > 0 && criteria.getLongitude() > 0)
			sql += " ,4890 * 3 * ASIN(SQRT(POWER(SIN(("+criteria.getLatitude()+" - abs(branch.lat)) * pi()/180 / 2),2) + COS("+criteria.getLatitude()+" * pi()/180 ) * COS(abs(branch.lat) *pi()/180) "
					+ " * POWER(SIN((" +criteria.getLongitude()+ " - branch.System.out.printlng) *pi()/180 / 2), 2) )) as distance " ;
		else
			sql += " , '' as distance " ;
		if(criteria.getCat_id() > 0)
			condition += " AND un.category_id = " + criteria.getCat_id() ;
		if(criteria.getSubcat_id() > 0)
			condition += " AND sect.subcategory_subcategory_id = " + criteria.getSubcat_id() ;
		if(criteria.getSection_id() > 0)
			condition += " AND sect.sections_section_id = " + criteria.getSection_id() ;
		if(criteria.getCountry_id() > 0)
			condition += " AND city.country_id = " + criteria.getCountry_id() ;
		if(criteria.getCity_id() > 0)
			condition += " AND city.id = " + criteria.getCity_id() ;
		if(criteria.getDistrict_id() > 0)
			condition += " AND dist.id = " + criteria.getDistrict_id() ;
		if(criteria.getName() != null && !criteria.getName().trim().equals(""))
			condition += " AND spe.name LIKE '%" + criteria.getName() +"%'" ;
		if(criteria.getFromday_id() > 0 && criteria.getToday_id() > 0)
			condition += " AND days.days_id between " + criteria.getFromday_id() + " AND " + criteria.getToday_id() ;
		
		
		sql += bodySQL + condition + ( ( criteria.getLatitude() > 0 && criteria.getLongitude() > 0 ) ? endSQL : "" ) ;
		 System.out.println("SQL : " + sql);
		Query query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class).addScalar("distance").addScalar("category_id");
		if(criteria.getStart() >= 0 && criteria.getLimit() > 0){
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(criteria.getLimit());
		}
		entities = query.list();
		
	}catch(Exception ex){
		ex.printStackTrace();
		 System.out.println("EX in advancedSearchForBranches : " + ex.getMessage());
	}finally{
		hiber.closeSession();
	}
	
	return entities ;
}



public List<BranchEntity> advancedSearchForBranchesWithoutDimensions(AdvancedSearchCriteria criteria){

	if(criteria == null)
		return null ;
	List<BranchEntity> entities = null;
	try{
		String sql = " SELECT DISTINCT branch.*  " ;
		String bodySQL = " FROM branch branch ";
//					+ "	inner join subcategory_has_sections_has_branch subsect on branch.id = subsect.branch_id "
//					+ " inner join subcategory_has_sections sect on subsect.subcategorysection_id = sect.id inner join unit un on un.id = branch.unit_unit_id "
//					+ " inner join distric dist on dist.id = branch.distric_id inner join city on city.id = dist.city_id "
//					+ " inner join specialist_has_branch specbran on specbran.branch_id = branch.id inner join specialist spe on spe.id = specbran.specialist_id "
//					+ " inner join schedule_has_days days on days.specialist_has_branch_id = specbran.id inner join schedule sch on sch.id = days.schedule_id WHERE branch.active = 1 " ;

	
		
		String condition = " ";
		
		//category
		if(criteria.getCat_id() > 0)
		{
			bodySQL+=" inner join unit un on un.id = branch.unit_unit_id ";
			condition += " AND un.category_id = " + criteria.getCat_id();
		}
		 
		//
		if(criteria.getSubcat_id() > 0)
		{
		
				bodySQL+="  inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id "
						+ " inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
			
			condition += " AND subcategory_has_sections.subcategory_subcategory_id = " + criteria.getSubcat_id() ;
		
		}
		
		if(criteria.getSection_id() > 0)
		{
			
			if(criteria.getSubcat_id()==0)
			{
			
			bodySQL+="  inner join subcategory_has_sections_has_branch on subcategory_has_sections_has_branch.branch_id=branch.id "
					+ " inner join subcategory_has_sections on subcategory_has_sections_has_branch.subcategorysection_id";
			}
		
		condition += " AND subcategory_has_sections.sections_section_id = " + criteria.getSection_id() ;
	
			
		}
		
		
		 if(criteria.getKeyword()!=null && !criteria.getKeyword().trim().equals(""))
		condition += " AND (branch.name_en LIKE '%"+criteria.getKeyword()+"%'"+" OR branch.name_ar  LIKE '%"+criteria.getKeyword()+"%')";
		
		if(criteria.getDistrict_id() > 0)
		{
			bodySQL+=" inner join distric on distric_id =branch.distric_id";
			condition += " AND distric.id = " + criteria.getDistrict_id() ;
		}
		
		if(criteria.getCity_id() > 0)
		{
			bodySQL+=" inner join  city on distric.city_id=city.id";
			condition += " AND city.id = " + criteria.getCity_id() ;
		}
		
		if(criteria.getCountry_id() > 0)
		{
			bodySQL+=" inner join  country on country.id=city.country_id";
			condition += " AND country.id = " + criteria.getCountry_id() ;
		}
		
		
		
		
		sql += bodySQL + condition ;
		 System.out.println("SQL : " + sql);
		Query query = hiber.getSession().createSQLQuery(sql).addEntity(BranchEntity.class);
		if(criteria.getStart() >= 0 && criteria.getLimit() > 0){
			query.setFirstResult(criteria.getStart());
			query.setMaxResults(criteria.getLimit());
		}
		entities = query.list();
		
	}catch(Exception ex){
		ex.printStackTrace();
		 System.out.println("EX in advancedSearchForBranches : " + ex.getMessage());
	}finally{
		hiber.closeSession();
	}
	
	return entities ;



}


public List<ContentDetailsEntity> getRelatedTopics(String keyword)
{
	
	String sqlStr="select * from content_details where content_details.content_ar like '%"+keyword+"%' or content_details.title_ar like'%"+keyword+"%'";
	
	
		List<ContentDetailsEntity> entities = null;
		try {
			Query query = hiber.getSession().createSQLQuery( sqlStr).addEntity("content_details", ContentDetailsEntity.class);
					
			entities = query.list();

		} catch (Exception ex) {
			 System.out.println("Get Ex : " + ex.getMessage());
		} finally {
			hiber.closeSession();
		}
		return entities;
	
	

}




private String getHoursFromDBByID(int id){
	String result = "";
	try{
		String sqlQuery = "SELECT h.hour FROM hours h WHERE H.id = ? " ;
		Query query = hiber.getSession().createSQLQuery(sqlQuery);
		query.setInteger(0, id);
		
		result = (String) query.uniqueResult();
		
		if(result.contains("AM")){
			result = result.replace("AM", "");
		}else if(result.contains("PM")){
			result = result.replace("PM", "");
			String[] tempd = result.trim().split(":") ;
			if(tempd != null && tempd.length == 2){
				int temp = Integer.parseInt(tempd[0]) + 12 ;
				result = temp + ":" + tempd[1]; 
			}
		} 
		
	}catch(Exception e){
		 System.out.println("Ex in getHoursFromDBByID : " + e.getMessage());
		e.printStackTrace();
		result = null ;
	}finally{
		hiber.closeSession();
	}
	return result != null ? result.trim() : null ;
}




}






