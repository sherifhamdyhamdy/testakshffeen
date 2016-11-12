package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.MessagesEntity;
import com.sget.akshef.hibernate.entities.UserRateContentEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * ContentDetails DAO
 */
public class ContentDetailsDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public ContentDetailsDAO(){
		hiber = new HibernateSession();
	}
	
	private Utils utils = Utils.getInstance();
	
	public void insert(ContentDetailsEntity entity)
	{
		try
		{				
			Serializable ser = hiber.getSession().save(entity);
			entity.setId(ser.hashCode());
			hiber.getSession().getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			hiber.closeSession();
		}
	}

	public boolean update(ContentDetailsEntity entity) {
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

    public boolean delete(ContentDetailsEntity entity) {
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
    
    public List<ContentDetailsEntity> getAll() {
        List<ContentDetailsEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT content_details.* FROM content_details content_details ")
                    .addEntity("content_details.", ContentDetailsEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    public List<ContentDetailsEntity>  getNewestNews()
    {
    	   List<ContentDetailsEntity> entities = null;
           try {
               Query query = hiber.getSession().createSQLQuery("SELECT content_details.* FROM content_details content_details ,  content_category content_category "
               		+ "  where   content_details.content_category_id=content_category.id and   content_category.content_types_id=:content_types    order by creat_date desc").
            		   addEntity("content_details.", ContentDetailsEntity.class)
            		   .setInteger("content_types", DBConstants.CONTENT_TYPE_NEWS);
               query.setFirstResult(0);
   			query.setMaxResults(7);
                      
               entities = query.list();
               
               
           } catch (Exception ex) {
                System.out.println("Get Ex : " + ex.getMessage());
           } finally{
               hiber.closeSession();
           }
           return entities;
    }
    
    public List<ContentDetailsEntity>  getMostRatedTips()
    {
    	   List<ContentDetailsEntity> entities = null;
           try {
               Query query = hiber.getSession().createSQLQuery("SELECT content_details.* FROM content_details content_details ,  content_category content_category "
                  		+ "  where   content_details.content_category_id=content_category.id and   content_category.content_types_id=:content_types    order by creat_date desc").addEntity("content_details.", ContentDetailsEntity.class)
            		   .setInteger("content_types", DBConstants.CONTENT_TYPE_TIPS);
               query.setFirstResult(0);
   			query.setMaxResults(4);
                      
               entities = query.list();
               
               
           } catch (Exception ex) {
                System.out.println("Get Ex : " + ex.getMessage());
           } finally{
               hiber.closeSession();
           }
           return entities;
    }
    
    public List<ContentDetailsEntity>  getMostRatedOffers()
    {
    	   List<ContentDetailsEntity> entities = null;
           try {
               Query query = hiber.getSession().createSQLQuery("SELECT content_details.* FROM content_details content_details ,  content_category content_category "
                  		+ "  where   content_details.content_category_id=content_category.id and   content_category.content_types_id=:content_types    order by creat_date desc").addEntity("content_details.", ContentDetailsEntity.class)
            		   .setInteger("content_types", DBConstants.CONTENT_TYPE_OFFERS);
               query.setFirstResult(0);
   			query.setMaxResults(4);
                      
               entities = query.list();
               
               
           } catch (Exception ex) {
                System.out.println("Get Ex : " + ex.getMessage());
           } finally{
               hiber.closeSession();
           }
           return entities;
    }
    public List<ContentDetailsEntity>  getMostRatedArticles()
    {
    	   List<ContentDetailsEntity> entities = null;
           try {
               Query query = hiber.getSession().createSQLQuery("SELECT content_details.* FROM content_details content_details ,  content_category content_category "
               		+ "  where   content_details.content_category_id=content_category.id and   content_category.content_types_id= :content_types   order by creat_date desc").addEntity("content_details.", ContentDetailsEntity.class)
            		   .setInteger("content_types", DBConstants.CONTENT_TYPE_ARTICLE);
               query.setFirstResult(0);
   			query.setMaxResults(4);
                      
               entities = query.list();
               
               
           } catch (Exception ex) {
                System.out.println("Get Ex : " + ex.getMessage());
           } finally{
               hiber.closeSession();
           }
           return entities;
    }
    
    public ContentDetailsEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	ContentDetailsEntity entity = null;
        try {
            entity = (ContentDetailsEntity) hiber.getSession().createSQLQuery("SELECT * FROM content_details WHERE id = ?")
        			.addEntity(ContentDetailsEntity.class).setInteger(0, id).uniqueResult();
        } catch (HibernateException ex) {
             System.out.println("ContentDetailsDAO : Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    public List<ContentDetailsEntity> getAllDetailsByCatId(int catId, String name,boolean sorting) {
        List<ContentDetailsEntity> entities = null;
        try {
        	String sqlString="SELECT content_details.* FROM content_details content_details " +
            		"where content_category_id=:catId ";
        	
        	if(name!=null &&! name.equals(""))
        	{
        		sqlString+=" and (title_ar LIKE '%"+name+"%'"+" or content_ar LIKE '%"+name+"%')";
        	}
        	
        	if(!sorting)
        		sqlString+=" order by content_rating ";
        	else
        		sqlString+=" order by content_rating desc";
            Query query = hiber.getSession().createSQLQuery(sqlString)
                    .addEntity("content_details.", ContentDetailsEntity.class).setInteger("catId", catId);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
   
    public List<UserRateContentEntity> getContentRating(int contId){
    	List<UserRateContentEntity> entities = null;
    	if(contId > 0){
    		try{
    			String sql = "SELECT ra.* FROM user_rate_content ra WHERE content_id = :contId";
    			Query query = hiber.getSession().createSQLQuery(sql).addEntity(UserRateContentEntity.class);
    			query.setInteger("contId", contId);
    			entities = query.list();
    			
    		}catch (Exception e) {
    			 System.out.println("Ex in getContentRating : " + e.getMessage());
    		}finally{
    			 hiber.closeSession();
    		}
    	}
    	
    	return entities;
    }
    // JDeeb
 	public void updateContentRating(int rating,int contentId){
 		try{
 			ContentDetailsEntity entity = getById(contentId);
 			entity.setRating(rating);
 			update(entity);
 		}catch(Exception e){
 			 System.out.println("Ex in update Branch Rating : " + e.getMessage());
 			e.printStackTrace();
 		}
 	}
 	
 	/**
 	 * JDeeb
 	 * get Content Details by Content Category and Branch
 	 */
 	@SuppressWarnings("unchecked")
	public List<ContentDetailsEntity> getContentByBranchAndType(int contType ,int branchId){
 		List<ContentDetailsEntity> entities = null;
    	if(contType > 0 && branchId > 0){
    		try{
    			String sql = " SELECT DISTINCT det.* FROM content_details det "
    					+ " INNER JOIN content_category cat ON det.content_category_id = cat.id "
    	 				+ " INNER JOIN content_types typ ON typ.id = cat.content_types_id "
    	 				+ " INNER JOIN branch_contenttype brc ON brc.content_type = typ.id "
    	 				+ " WHERE typ.id = :contType AND brc.branch_id = :branchId ";
    			Query query = hiber.getSession().createSQLQuery(sql).addEntity(ContentDetailsEntity.class);
    			query.setInteger("contType", contType);
    			query.setInteger("branchId", branchId);
    			
    			entities = query.list();
    			
    		}catch (Exception e) {
    			 System.out.println("Ex in getContentByBranchAndType : " + e.getMessage());
    		}finally{
    			 hiber.closeSession();
    		}
    	}
    	
    	return entities;
 	}
 
 	
 	

	public List listUserContents(int userId, int start, int rowCount)
	{
		List<BranchEntity> list = null;
		try
		{
			System.out.println("ContentDetailsDAO.listUserContents()");
			
			list = hiber.getSession()
					.createCriteria(ContentDetailsEntity.class)
					
					.createCriteria("this.users", "user")
					.add(Restrictions.eq("user.id", userId))
					
					.add(Restrictions.isNull("this.offer_from"))
					.add(Restrictions.isNull("this.offer_to"))
					
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.setFirstResult(start)
					.setMaxResults(rowCount)
					.list();
			
			list.addAll(
					hiber.getSession()
					.createCriteria(ContentDetailsEntity.class)
					
					.createCriteria("this.users", "user")
					.add(Restrictions.eq("user.id", userId))
					
					.add(Restrictions.isNotNull("this.offer_from"))
					.add(Restrictions.isNotNull("this.offer_to"))
					
					.add(Restrictions.le("this.offer_from", new Date()))
					.add(Restrictions.ge("this.offer_to", new Date()))

					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.setFirstResult(start)
					.setMaxResults(rowCount)
					.list()
				);
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
	
	public List listUnitContents(int unitId, int start, int rowCount)
	{
		List<BranchEntity> list = null;
		try
		{
			System.out.println("ContentDetailsDAO.listUnitContents()");
			
			list = hiber.getSession()
					.createCriteria(ContentDetailsEntity.class)
					
					.createCriteria("this.unit", "unit")
					.add(Restrictions.eq("unit.id", unitId))
					
					.add(Restrictions.isNull("this.offer_from"))
					.add(Restrictions.isNull("this.offer_to"))
					
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.setFirstResult(start)
					.setMaxResults(rowCount)
					.list();
			
			list.addAll(
					hiber.getSession()
					.createCriteria(ContentDetailsEntity.class)
					
					.createCriteria("this.unit", "unit")
					.add(Restrictions.eq("unit.id", unitId))
					
					.add(Restrictions.isNotNull("this.offer_from"))
					.add(Restrictions.isNotNull("this.offer_to"))
					
					.add(Restrictions.le("this.offer_from", new Date()))
					.add(Restrictions.ge("this.offer_to", new Date()))

					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.setFirstResult(start)
					.setMaxResults(rowCount)
					.list()
				);
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
	
	public List listAdminContents(int start, int rowCount)
	{
		List<BranchEntity> list = null;
		try
		{
			System.out.println("ContentDetailsDAO.listAdminContents()");
			
			list = hiber.getSession()
					.createCriteria(ContentDetailsEntity.class)
					
					.add(Restrictions.isNull("this.users"))
					.add(Restrictions.isNull("this.unit"))
					
					.add(Restrictions.isNull("this.offer_from"))
					.add(Restrictions.isNull("this.offer_to"))
					
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.setFirstResult(start)
					.setMaxResults(rowCount)
					.list();
			
			list.addAll(
						hiber.getSession()
						.createCriteria(ContentDetailsEntity.class)
						
						.add(Restrictions.isNull("this.users"))
						.add(Restrictions.isNull("this.unit"))
						
						.add(Restrictions.isNotNull("this.offer_from"))
						.add(Restrictions.isNotNull("this.offer_to"))
						
						.add(Restrictions.le("this.offer_from", new Date()))
						.add(Restrictions.ge("this.offer_to", new Date()))

						.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
						.setFirstResult(start)
						.setMaxResults(rowCount)
						.list()
					);
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
