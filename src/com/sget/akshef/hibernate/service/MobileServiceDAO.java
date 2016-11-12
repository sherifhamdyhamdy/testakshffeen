package com.sget.akshef.hibernate.service;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.sget.akshef.hibernate.beans.VisitorsInteractiveBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateUtil;
import com.sget.akshf.mobile.api.AppConstants;

/**
 * 
 * @author JDeeb This Class contains SOME functions for Mobile Web Services
 * 
 */
public class MobileServiceDAO
{
	
	// User Add Branch Comment
	public int addBranchComment(int branchid, int userid, String text)
	{
		int result = 0;
		if (branchid > 0 && userid > 0 && text != null && !text.trim().equalsIgnoreCase(""))
		{
			try
			{
				long comment_date = Calendar.getInstance().getTimeInMillis();
				String sql = " INSERT INTO comments (`comment` , `users_id` , `branch_id` , `comment_date`) VALUES (:txt , :userid , :branid , :comment_date); ";
				Query query = getSession().createSQLQuery(sql);
				query.setString("txt", text);
				query.setInteger("userid", userid);
				query.setInteger("branid", branchid);
				query.setLong("comment_date", comment_date);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addBranchComment " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	/**
	 * This Function Add New Complaint For Branch parameters branchid , userid ,
	 * text
	 * 
	 * @return value of (0 exception , 1 success , 2 fail)
	 */
	public int addBranchComplaint(int branchid, int fromUser, String text)
	{
		int result = 0;
		if (branchid > 0 && fromUser > 0 && text != null && !text.trim().equalsIgnoreCase(""))
		{
			try
			{
				int toUser = getUserIdByBranch(branchid);
				
				String sql = " INSERT INTO messages (`title` , `msg_body` , `type_id` , `from_user` , `to_user`) VALUES (:title , :msg , :typeid , :fromuser , :touser); ";
				Query query = getSession().createSQLQuery(sql);
				query.setString("title", "Complaint Message");
				query.setString("msg", text);
				query.setInteger("typeid", DBConstants.MESSAGES_TYPES_COMPLAIN);
				query.setInteger("fromuser", fromUser);
				query.setInteger("touser", toUser);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addBranchComplaint " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	public int addBranchMessage(int branchid, int fromUser, String title, String text)
	{
		int result = 0;
		if (branchid > 0 && fromUser > 0 && text != null && !text.trim().equalsIgnoreCase(""))
		{
			try
			{
				int toUser = getUserIdByBranch(branchid);
				
				String sql = " INSERT INTO messages (`title` , `msg_body` , `from_user` , `to_user` , `msg_date`) VALUES (:title , :msg , :fromuser , :touser ,:msgDate); ";
				Query query = getSession().createSQLQuery(sql);
				
				/*
				 * if(typeId==1) query.setString("title",
				 * "suggesssion Message"); else if(typeId==2)
				 * query.setString("title", "complain Message");
				 */
				
				long msgDate = Calendar.getInstance().getTimeInMillis();
				
				query.setString("title", title);
				query.setString("msg", text);
				query.setInteger("fromuser", fromUser);
				query.setInteger("touser", toUser);
				query.setLong("msgDate", msgDate);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addBranchComplaint " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	public int addDoctorMessage(int doctorId, int fromUser, String title, String text)
	{
		int result = 0;
		if (doctorId > 0 && fromUser > 0 && text != null && !text.trim().equalsIgnoreCase(""))
		{
			try
			{
				int toUser = getUserIdByDoctor(doctorId);
				
				String sql = " INSERT INTO messages (`title` , `msg_body` , `from_user` , `to_user` , `msg_date`) VALUES (:title , :msg , :fromuser , :touser ,:msgDate); ";
				Query query = getSession().createSQLQuery(sql);
				
				/*
				 * if(typeId==1) query.setString("title",
				 * "suggesssion Message"); else if(typeId==2)
				 * query.setString("title", "complain Message");
				 */
				long msgDate = Calendar.getInstance().getTimeInMillis();
				
				query.setString("title", title);
				query.setString("msg", text);
				query.setInteger("fromuser", fromUser);
				query.setInteger("touser", toUser);
				query.setLong("msgDate", msgDate);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addBranchComplaint " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Get User ID ( in group BranchManager ) In Specified Branch
	public int getUserIdByBranch(int branchid)
	{
		int result = 0;
		if (branchid > 0)
		{
			try
			{
				// String sql =
				// "SELECT g.users_id FROM branch_groups bg INNER JOIN users_groups g ON bg.users_groups_id = g.id WHERE branch_id = :branch AND groups_id = :grouid ";
				
				String sql = "SELECT users_id from branch  where id=:branch";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("branch", branchid);
				// query.setInteger("grouid",
				// DBConstants.GROUP_BRANCH_MANAGERS);
				
				List<Integer> li = query.list();
				if (li != null && li.size() > 0)
					result = li.get(0);
				
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addBranchToFav " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Get User ID ( in group BranchManager ) In Specified Branch
	public int getUserIdByDoctor(int doctorId)
	{
		int result = 0;
		if (doctorId > 0)
		{
			try
			{
				// String sql =
				// "SELECT g.users_id FROM branch_groups bg INNER JOIN users_groups g ON bg.users_groups_id = g.id WHERE branch_id = :branch AND groups_id = :grouid ";
				
				String sql = "SELECT users_id from specialist  where id=:doctor";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("doctor", doctorId);
				// query.setInteger("grouid",
				// DBConstants.GROUP_BRANCH_MANAGERS);
				
				List<Integer> li = query.list();
				if (li != null && li.size() > 0)
					result = li.get(0);
				
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addBranchToFav " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Get User ID ( in group System Owner )
//	public int getSystemOwnerId()
//	{
//		int result = 0;
//		try
//		{
//			String sql = "SELECT g.users_id FROM users_groups g WHERE groups_id = :grouid ";
//			Query query = getSession().createSQLQuery(sql);
////			query.setInteger("grouid", DBConstants.GROUP_SYSTEM_OWNERS);
//			
//			List<Integer> li = query.list();
//			if (li != null && li.size() > 0)
//				result = li.get(0);
//			
//			getSession().getTransaction().commit();
//		}
//		catch (Exception e)
//		{
//			System.out.println("Ex in getSystemOnwerID " + e.getMessage());
//			e.printStackTrace();
//		}
//		finally
//		{
//			closeSession();
//		}
//		return result;
//	}
	
	
	
	// User Add Specialist Favorites
	public int addSpecialistToFav(int specId, int userId)
	{
		int result = 0;
		if (specId > 0 && userId > 0)
		{
			try
			{
				String sql = " INSERT INTO user_favorities (`users_id` , `specialist_id`) VALUES (:userid , :specId); ";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("userid", userId);
				query.setInteger("specId", specId);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addSpecialistToFav " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// User Add Branch Favorites
	public int addBranchToFav(int branchid, int userid)
	{
		int result = 0;
		if (branchid > 0 && userid > 0)
		{
			try
			{
				String sql = " INSERT INTO user_favorities (`users_id` , `branch_id`) VALUES (:userid , :branid); ";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("userid", userid);
				query.setInteger("branid", branchid);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addBranchToFav " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// User Add Content Favorites
	public int addContentToFav(int contentid, int userid)
	{
		int result = 0;
		if (contentid > 0 && userid > 0)
		{
			try
			{
				String sql = " INSERT INTO user_favorities (`users_id` , `content_details_id`) VALUES (:userid , :contid); ";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("userid", userid);
				query.setInteger("contid", contentid);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addContentToFav " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Add Branch Rating
	public int addUserBranchRating(int branchId, int userId, int rating)
	{
		int result = 0;
		if (branchId > 0 && userId > 0 && rating >= 0)
		{
			try
			{
				String sql = " INSERT INTO user_rate_branch (`branch_id` , `users_id` , `rate` ) VALUES (:branid , :userid , :rate); ";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("branid", branchId);
				query.setInteger("userid", userId);
				query.setInteger("rate", rating);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addUserBranchRating " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
		
	}
	
	
	
	// Add Specialist Rating
	public int addUserSpecialistRating(int specialistId, int userId, int rating)
	{
		int result = 0;
		if (specialistId > 0 && userId > 0 && rating >= 0)
		{
			try
			{
				String sql = " INSERT INTO user_rate_spec (`specialist_id` , `users_id` , `rate` ) VALUES (:specId , :userid , :rate); ";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("specId", specialistId);
				query.setInteger("userid", userId);
				query.setInteger("rate", rating);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addUserSpecialistRating " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Add Content Rating
	public int addUserContentRating(int contentId, int userId, int rating)
	{
		int result = 0;
		if (contentId > 0 && userId > 0 && rating >= 0)
		{
			try
			{
				String sql = " INSERT INTO user_rate_content (`content_id` , `users_id` , `rate` ) VALUES (:contentId , :userid , :rate); ";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("contentId", contentId);
				query.setInteger("userid", userId);
				query.setInteger("rate", rating);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addUserContentRating " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Check If Branch Already in User Favorites
	public boolean checkIfBranchAlreadyFav(int branchid, int userid)
	{
		boolean exist = false;
		if (branchid > 0 && userid > 0)
		{
			try
			{
				String sql = "SELECT * FROM  user_favorities WHERE users_id = :user AND branch_id = :branch";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("user", userid);
				query.setInteger("branch", branchid);
				List results = query.list();
				if (results != null && results.size() > 0)
					exist = true;
			}
			catch (Exception e)
			{
				System.out.println("EX in checkIfBranchAlreadyFav " + e.getMessage());
			}
			finally
			{
				closeSession();
			}
		}
		return exist;
	}
	
	
	
	// Check If Specialist Already in User Favorites
	public boolean checkIfSpecialistAlreadyFav(int specialist_id, int userid)
	{
		boolean exist = false;
		if (specialist_id > 0 && userid > 0)
		{
			try
			{
				String sql = "SELECT * FROM  user_favorities WHERE users_id = :user AND specialist_id = :spec";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("user", userid);
				query.setInteger("spec", specialist_id);
				List results = query.list();
				if (results != null && results.size() > 0)
					exist = true;
			}
			catch (Exception e)
			{
				System.out.println("EX in checkIfSpecialistAlreadyFav " + e.getMessage());
			}
			finally
			{
				closeSession();
			}
		}
		return exist;
	}
	
	
	
	// Check If Content Already in User Favorites
	public boolean checkIfContentAlreadyFav(int contentId, int userid)
	{
		boolean exist = false;
		if (contentId > 0 && userid > 0)
		{
			try
			{
				String sql = "SELECT * FROM  user_favorities WHERE users_id = :user AND content_details_id = :cont";
				Query query = getSession().createSQLQuery(sql);
				query.setInteger("user", userid);
				query.setInteger("cont", contentId);
				List results = query.list();
				if (results != null && results.size() > 0)
					exist = true;
			}
			catch (Exception e)
			{
				System.out.println("EX in checkIfContentAlreadyFav " + e.getMessage());
			}
			finally
			{
				closeSession();
			}
		}
		return exist;
	}
	
	
	
	// Add Visitor Suggest or Complain
	public int addVisitorSuggetComplain(VisitorsInteractiveBean bean)
	{
		int result = 0;
		if (bean != null)
		{
			try
			{
//				int toUser = getSystemOwnerId();
				int toUser = 0;
				
				String sql = " INSERT INTO visitors_interactive (`name` , `occupation` , `email` , `title` , `desc` , `to_user` , `type_id` , `follow_no`) VALUES " + "(:name , :occupation , :email , :title , :desc , :touser , :typeid , :followno); ";
				Query query = getSession().createSQLQuery(sql);
				query.setString("name", bean.getName());
				query.setString("occupation", bean.getOccupation());
				query.setString("email", bean.getEmail());
				query.setString("title", bean.getTitle());
				query.setString("desc", bean.getDesc());
				query.setInteger("touser", toUser);
				query.setInteger("typeid", bean.getInteractiveType().getId());
				query.setString("followno", bean.getFollow_no());
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addBranchComplaint " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	public int addSpecialistComment(int specid, int userid, String text)
	{
		int result = 0;
		if (specid > 0 && userid > 0 && text != null && !text.trim().equalsIgnoreCase(""))
		{
			try
			{
				long comment_date = Calendar.getInstance().getTimeInMillis();
				
				String sql = " INSERT INTO comments (`comment` , `users_id` , `specialist_id` , `comment_date`) VALUES (:txt , :userid , :specid , :comment_date); ";
				Query query = getSession().createSQLQuery(sql);
				query.setString("txt", text);
				query.setInteger("userid", userid);
				query.setInteger("specid", specid);
				query.setLong("comment_date", comment_date);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addSpecialistComment " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	public int addContentComment(int contid, int userid, String text)
	{
		int result = 0;
		if (contid > 0 && userid > 0 && text != null && !text.trim().equalsIgnoreCase(""))
		{
			try
			{
				long comment_date = Calendar.getInstance().getTimeInMillis();
				
				String sql = " INSERT INTO comments (`comment` , `users_id` , `content_details_id` ,`comment_date`) VALUES (:txt , :userid , :contid , :comment_date); ";
				Query query = getSession().createSQLQuery(sql);
				
				query.setString("txt", text);
				query.setInteger("userid", userid);
				query.setInteger("contid", contid);
				query.setLong("comment_date", comment_date);
				
				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in addContentComment " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Get Branch Comments
	public List<Object[]> getBranchComments(int start, int limit, int branchID)
	{
		List<Object[]> results = null;
		try
		{
			String sqlQuery = "SELECT comm.* , us.* FROM comments comm inner join users us on comm.users_id = us.id where comm.branch_id = :branchID ORDER BY comment_date desc";
			Query query = getSession().createSQLQuery(sqlQuery).addEntity(CommentsEntity.class).addEntity(UsersEntity.class);
			
			query.setInteger("branchID", branchID);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			
			results = query.list();
			
		}
		catch (Exception e)
		{
			System.out.println("Ex in getBranchComments " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		return results;
	}
	
	
	
	// getSpecialistComments
	public List<Object[]> getSpecialistComments(int start, int limit, int specID)
	{
		List<Object[]> results = null;
		try
		{
			String sqlQuery = "SELECT comm.* , us.* FROM comments comm inner join users us on comm.users_id = us.id where comm.specialist_id = :specID ORDER BY comment_date desc";
			Query query = getSession().createSQLQuery(sqlQuery).addEntity(CommentsEntity.class).addEntity(UsersEntity.class);
			
			query.setInteger("specID", specID);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			
			results = query.list();
			
		}
		catch (Exception e)
		{
			System.out.println("Ex in getSpecialistComments " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		return results;
	}
	
	
	
	// get Content Comments
	public List<Object[]> getContentComments(int start, int limit, int contID)
	{
		List<Object[]> results = null;
		try
		{
			String sqlQuery = "SELECT comm.* , us.* FROM comments comm inner join users us on comm.users_id = us.id where comm.content_details_id = :contID ORDER BY comment_date desc";
			Query query = getSession().createSQLQuery(sqlQuery).addEntity(CommentsEntity.class).addEntity(UsersEntity.class);
			
			query.setInteger("contID", contID);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			
			results = query.list();
			
		}
		catch (Exception e)
		{
			System.out.println("Ex in getContentComments " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		return results;
	}
	
	
	
	// JDeeb delete favorite
	public int deleteFavorite(int appCat, int id, int userId)
	{
		int result = 0;
		if (appCat > 0 && appCat <= 5 && id > 0 && userId > 0)
		{
			try
			{
				
				String sql = "DELETE FROM user_favorities WHERE users_id = :userId ";
				String condition = "";
				if (appCat == AppConstants.APP_BRANCH_ID)
					condition = " AND branch_id = :id ";
				else if (appCat == AppConstants.APP_SPECIALIST_ID)
					condition = " AND specialist_id = :id ";
				else if (appCat == AppConstants.APP_CONTENT_NEWS_ID || appCat == AppConstants.APP_CONTENT_OFFERS_ID || appCat == AppConstants.APP_CONTENT_TIPS_ID)
					condition = " AND content_details_id = :id ";
				else
					return 0;
				
				sql += condition;
				System.out.println(sql);
				Query query = getSession().createSQLQuery(sql);
				
				query.setInteger("userId", userId);
				query.setInteger("id", id);
				
				int queryres = query.executeUpdate();
				
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("Ex in delete Favorite " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Push Notifications
	public int addAndroidDeviceID(String registerationId, int platform)
	{
		int result = 0;
		if (registerationId != null && !registerationId.trim().equalsIgnoreCase(""))
		{
			try
			{
				String sqlSelect = "select * from android_devices where registerationId=:registerationId ";
				Query querySelect = getSession().createSQLQuery(sqlSelect).setString("registerationId", registerationId);
				
				List<Object> list = querySelect.list();
				if (list != null && !list.isEmpty())
				{
					return 0;
				}
				
				
				String sql = " INSERT INTO android_devices (`registerationId`,`platform`) VALUES (:registerationId,:platform) ";
				Query query = getSession().createSQLQuery(sql);
				
				query.setString("registerationId", registerationId);
				query.setInteger("platform", platform);

				int queryres = query.executeUpdate();
				if (queryres >= 1)
					result = 1;
				else
					result = 2;
				getSession().getTransaction().commit();
			}
			catch (Exception e)
			{
				System.out.println("MobileServiceDAO Ex in addAndroidDeviceRegisID " + e.getMessage());
				e.printStackTrace();
				result = 0;
			}
			finally
			{
				closeSession();
			}
		}
		return result;
	}
	
	
	
	// Get All Android Devices RegisterationID
	public List<String> getAllAndroidDeviceID()
	{
		List<String> regId = null;
		try
		{
			String sql = " SELECT dev.registerationId FROM android_devices dev ";
			Query query = getSession().createSQLQuery(sql);
			regId = query.list();
		}
		catch (Exception e)
		{
			System.out.println("MobileServiceDAO Ex in getAllAndroidDeviceID " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		return regId;
	}
	
	// Session Control
	private Session session = null;
	
	
	
	public synchronized Session getSession()
	{
		if (session == null || !session.isOpen())
		{
			return makeSession();
		}
		return session;
	}
	
	
	
	public Session makeSession()
	{
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		if (session == null || !session.isOpen())
		{
			session = HibernateUtil.getSessionFactory().openSession();
			System.out.println("Can't get Current Session so Open Session status : " + session.isOpen());
		}
		if (session != null && session.isOpen())
			session.beginTransaction();
		return session;
	}
	
	
	
	public void closeSession()
	{
		if (getSession() != null || getSession().isOpen())
			getSession().close();
	}
	
}
