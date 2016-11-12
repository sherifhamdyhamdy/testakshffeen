package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.akshffeen.utils.Utils;
import com.mysql.jdbc.Util;
import com.sget.akshef.hibernate.entities.Admin;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CategoryHasSubcategoryEntity;
import com.sget.akshef.hibernate.entities.CertificationEntity;
import com.sget.akshef.hibernate.entities.ProfessionalExpEntity;
import com.sget.akshef.hibernate.entities.RoleHasPermissionEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;
import com.sget.akshef.view.admin.AdminControl;
import com.sget.akshf.mobile.api.AppConstants;

/**
 * @author JDeeb Users DAO
 */
public class UsersDAO
{
	// Hibernate Session Class
	HibernateSession hiber;
	
	private Utils utils = Utils.getInstance();
	
	
	
	public UsersDAO()
	{
		hiber = new HibernateSession();
	}
	
	
	
	public void insert(UsersEntity entity)
	{
		try
		{
			System.out.println("UsersDAO.insert() 11111111");
			
			if(!utils.hasValue(entity.getCompany()) || !utils.hasValue(entity.getCompany().getId()))
				entity.setCompany(null);
			
			if(!utils.hasValue(entity.getDistric()) || !utils.hasValue(entity.getDistric().getId()))
				entity.setDistric(null);

//			if(!utils.hasValue(entity.getMedicalhistorySet()) || !entity.getMedicalhistorySet().isEmpty())
//				entity.setDistric(null);
			
			Serializable ser = hiber.getSession().save(entity);
			entity.setId(ser.hashCode());
			hiber.getSession().getTransaction().commit();
			
			System.out.println("UsersDAO.insert() 222222");
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
	
	
	
	public int insertById(UsersEntity entity)
	{
		int id = 0;
		try
		{
			Serializable ser = hiber.getSession().save(entity);
			entity.setId(ser.hashCode());
			id = ser.hashCode();
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
		
		return id;
	}
	
	
	
	public boolean update(UsersEntity entity)
	{
		if (entity == null)
			return false;
		try
		{
//			if(!utils.hasValue(entity.getCompany()) || !utils.hasValue(entity.getCompany().getId()))
//				entity.setCompany(null);
//			
//			if(!utils.hasValue(entity.getDistric()) || !utils.hasValue(entity.getDistric().getId()))
//				entity.setDistric(null);
			
			UsersEntity element = getById(entity.getId());
			if(element!=null)
			{
				entity.setCompany(element.getCompany());
				entity.setDistric(element.getDistric());
			}
			
			hiber.getSession().update(entity);
			hiber.getSession().getTransaction().commit();
			return true;
		}
		catch (Exception ex)
		{
			System.out.println("UsersDAO : Inesrt Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return false;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<RoleHasPermissionEntity> getAllRolesPermission(UsersEntity entity)
	{
		List<RoleHasPermissionEntity> roleHasPermissionEntities = null;
		try
		{
			// System.out.println("   entity .getId()  ::  " + entity .getId());
			
			String jpql = "Select DISTINCT r FROM role_permission r , IN ( r.groupsHasRoles )  gr, IN ( gr.groups ) g  , IN ( g.usersGroupsSet ) ug WHERE ug.users like '" + entity.getId() + "'  ";
			
			Query query = hiber.getSession().createQuery(jpql);
			// System.out.println("query.toString() ::  " + query.toString());
			roleHasPermissionEntities = query.list();
			
			if (roleHasPermissionEntities.size() > 0)
			{
				return roleHasPermissionEntities;
			}
			
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("UsersDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return roleHasPermissionEntities;
	}
	
	
	/**
	 * check if the user is Branch or Specialist
	 */
	public int checkUserBranchOrSpecialist(int userID)
	{
		int status = 0;
		if (userID > 0)
		{
			try
			{
				String sql = " SELECT * FROM branch WHERE users_id = :userID ";
				
				Query query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("userID", userID);
				
				List results = query.list();
				
				if (results != null && results.size() > 0)
					return AppConstants.APP_BRANCH_ID;
				
				sql = " SELECT * FROM specialist WHERE users_id = :userID";
				query = hiber.getSession().createSQLQuery(sql);
				query.setInteger("userID", userID);
				
				results = query.list();
				if (results != null && results.size() > 0)
					return AppConstants.APP_SPECIALIST_ID;
				status = AppConstants.APP_END_USER;
			}
			catch (Exception e)
			{
				System.out.println("UsersDAO : Ex in checkUserBranchOrSpecialist : " + e.getMessage());
				status = 0;
			}
			finally
			{
				hiber.closeSession();
			}
		}
		return status;
	}
	
	
	
	// JDeeb Get Users whom i send MSGs to them or recieve
	public List<UsersEntity> getCommunications(int userId)
	{
		List<UsersEntity> entities = null;
		if (userId > 0)
		{
			try
			{
				
				String sql = " SELECT us.* FROM ( SELECT DISTINCT ms.to_user AS userId FROM messages ms WHERE ms.from_user = :userId AND ms.to_user != :userId " + " UNION SELECT DISTINCT ms.from_user AS userId FROM messages ms WHERE ms.to_user = :userId AND ms.from_user != :userId ) al " + " INNER JOIN users us ON al.userId = us.id ";
				Query query = hiber.getSession().createSQLQuery(sql).addEntity(UsersEntity.class);
				
				query.setInteger("userId", userId);
				
				entities = query.list();
				
			}
			catch (Exception e)
			{
				System.out.println("UsersDAO : Ex in addSpecialistComment " + e.getMessage());
				e.printStackTrace();
			}
			finally
			{
				hiber.closeSession();
			}
		}
		return entities;
	}
	
	
	
	// Old Project Functions missed
	public List<UsersEntity> getByMatchNationalId(String NationalId)
	{
		
		List<UsersEntity> entities = null;
		try
		{
			
			// System.out.println("query dd :: NationalId   NationalId  "+NationalId);
			
			Criteria criteria = hiber.getSession().createCriteria(UsersEntity.class).add(Restrictions.like("nationalId", NationalId + "%"));
			entities = criteria.list();
			// System.out.println("Rows affected: query.toString()   ::  "
			// +criteria.toString());
			
			
			return entities;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}
	
	
	
	/**
	 * JDeeb get all users in specified group
	 * 
	 * @return
	 */
	public List<UsersEntity> getAllByGroupId(int groupId)
	{
		List<UsersEntity> entities = null;
		try
		{
			String sql = " SELECT users.* FROM users users INNER JOIN users_groups usg ON usg.users_id = users.id WHERE groups_id = :groupId ";
			Query query = hiber.getSession().createSQLQuery(sql).addEntity("users.", UsersEntity.class);
			query.setInteger("groupId", groupId);
			entities = query.list();
		}
		catch (Exception ex)
		{
			System.out.println("UsersDAO : Get Ex : " + ex.getMessage());
		}
		finally
		{
			hiber.closeSession();
		}
		return entities;
	}

	
	
	
	 public void insert2(UsersEntity entity) {
		    try {
		      Serializable ser = this.hiber.getSession().save(entity);
		      entity.setId(Integer.valueOf(ser.hashCode()));
		      this.hiber.getSession().getTransaction().commit();
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      this.hiber.closeSession();
		    }
		  }

		  public int insertById2(UsersEntity entity) { int id = 0;
		    try {
		      Serializable ser = this.hiber.getSession().save(entity);
		      entity.setId(Integer.valueOf(ser.hashCode()));
		      id = ser.hashCode();
		      this.hiber.getSession().getTransaction().commit();
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      this.hiber.closeSession();
		    }

		    return id; }

		  public boolean update2(UsersEntity entity)
		  {
		    if (entity == null)
		      return false;
		    try {
		      this.hiber.getSession().update(entity);
		      this.hiber.getSession().getTransaction().commit();
		      return true;
		    } catch (Exception ex) {
		      System.out.println("UsersDAO : Inesrt Ex : " + ex.getMessage());
		    } finally {
		      this.hiber.closeSession();
		    }
		    return false;
		  }

		  public List<RoleHasPermissionEntity> getAllRolesPermission2(UsersEntity entity)
		  {
		    List roleHasPermissionEntities = null;
		    try
		    {
		      String jpql = "Select DISTINCT r FROM role_permission r , IN ( r.groupsHasRoles )  gr, IN ( gr.groups ) g  , IN ( g.usersGroupsSet ) ug WHERE ug.users like '" + 
		        entity.getId() + "'  ";

		      Query query = this.hiber.getSession().createQuery(jpql);

		      roleHasPermissionEntities = query.list();

		      if (roleHasPermissionEntities.size() > 0) {
		        return roleHasPermissionEntities;
		      }
		    }
		    catch (Exception ex)
		    {
		      ex.printStackTrace();
		      System.out.println("UsersDAO : Get Ex : " + ex.getMessage());
		    } finally {
		      this.hiber.closeSession(); } this.hiber.closeSession();

		    return roleHasPermissionEntities;
		  }

		  public boolean delete(UsersEntity entity)
		  {
		    if ((entity == null) || (entity.getId().intValue() < 1))
		      return false;
		    try {
		      this.hiber.getSession().delete(entity);
		      this.hiber.getSession().getTransaction().commit();
		      return true;
		    } catch (Exception ex) {
		      System.out.println("UsersDAO : Delete Ex : " + ex.getMessage());
		    } finally {
		      this.hiber.closeSession();
		    }
		    return false;
		  }

		  public UsersEntity checkUser(UsersEntity entity)
		  {
		    if (entity == null)
		      return null;
		    try {
		      String hql = "select u from users u where u.username = :name1 AND  u.password = :name2  ";
		      Query query = this.hiber.getSession().createQuery(hql);
		      query.setString("name1", entity.getUsername().trim());
		      query.setString("name2", entity.getPassword().trim());
		      System.out.println("Rows affected: query.toString()   ::  " + query.toString());
		      entity = (UsersEntity)query.uniqueResult();
		      return entity;
		    } catch (Exception ex) {
		      ex.printStackTrace();
		    } finally {
		      this.hiber.closeSession();
		    }
		    return entity;
		  }

		  public List<UsersEntity> getAll() {
		    List entities = null;
		    try {
		      Query query = this.hiber.getSession().createSQLQuery("SELECT users.* FROM users users ")
		        .addEntity("users.", UsersEntity.class);
		      entities = query.list();
		    }
		    catch (Exception ex) {
		      System.out.println("UsersDAO : Get Ex : " + ex.getMessage());
		    } finally {
		      this.hiber.closeSession();
		    }
		    return entities;
		  }

		  public UsersEntity getById(int id) {
		    if (id < 1) {
		      return null;
		    }
		    UsersEntity entity = null;
		    try {
		      entity = (UsersEntity)this.hiber.getSession().get(UsersEntity.class, Integer.valueOf(id));
		    } catch (HibernateException ex) {
		      System.out.println("UsersDAO : Get Row : " + ex.getMessage());
		    } finally {
		      this.hiber.closeSession();
		    }
		    return entity;
		  }

	
	
	public UsersEntity loginUser(String username, String pass, String token, int platform)
	{
		if ((username != null) && (!username.trim().equalsIgnoreCase("")) && (pass != null) && (!pass.trim().equalsIgnoreCase("")))
		{
			UsersEntity entity = null;
			try
			{
				String sql = "SELECT * FROM users WHERE ( username = :username or email = :username or mobile = :username ) AND password = :pass ";
				Query query = this.hiber.getSession().createSQLQuery(sql).addEntity("users.", UsersEntity.class);
				query.setString("username", username);
				query.setString("pass", utils.encryptText(pass));
				
				List result = query.list();
				if ((result != null) && (result.size() > 0))
				{
					entity = (UsersEntity) result.get(0);
					
					SpecialistEntity doctor = (SpecialistEntity)this.hiber.getSession().createCriteria(SpecialistEntity.class)
									            .add(Restrictions.eq("this.users.id", entity.getId()))
									            .uniqueResult();
			          
					if(doctor!=null)
						entity.setUser_type(2);
					else
						entity.setUser_type(1);
					
					if(token!=null && platform>0)
					{
						entity.setToken(token);
						entity.setPlatform(platform);
						hiber.getSession().merge(entity);
					}
				}
				this.hiber.getSession().getTransaction().commit();
			}
			catch (HibernateException ex)
			{
				System.out.println("UsersDAO : Get Row : " + ex.getMessage());
			}
			finally
			{
				this.hiber.closeSession();
			}
			return entity;
		}
		return null;
	}

		  public boolean checkUserInRegisterByUsername(String username) {
		    boolean exist = false;
		    if ((username != null) && (!username.trim().equalsIgnoreCase(""))) {
		      try {
		        String sql = " SELECT * FROM users WHERE username = :name ";
		        Query query = this.hiber.getSession().createSQLQuery(sql);
		        query.setString("name", username);
		        List results = query.list();

		        if ((results != null) && (results.size() > 0))
		          exist = true;
		      }
		      catch (Exception e) {
		        System.out.println("UsersDAO : Ex in checkUserInRegisterByUsername : " + e.getMessage());
		        exist = false;
		      } finally {
		        this.hiber.closeSession();
		      }
		    }
		    return exist;
		  }

		  public boolean checkUserInRegisterByUsername(String username, int user_id)
		  {
		    boolean exist = false;
		    if ((username != null) && (!username.trim().equalsIgnoreCase(""))) {
		      try {
		        String sql = " SELECT * FROM users WHERE username = :name and  username not in (select username from users where id=:user_id)";
		        Query query = this.hiber.getSession().createSQLQuery(sql);
		        query.setString("name", username).setInteger("user_id", user_id);
		        List results = query.list();

		        if ((results != null) && (results.size() > 0))
		          exist = true;
		      }
		      catch (Exception e) {
		        System.out.println("UsersDAO : Ex in checkUserInRegisterByUsername : " + e.getMessage());
		        exist = false;
		      } finally {
		        this.hiber.closeSession();
		      }
		    }
		    return exist;
		  }

		  public void insertCertification(CertificationEntity certEnt)
		  {
		    try
		    {
		      Serializable ser = this.hiber.getSession().save(certEnt);
		      certEnt.setId(Integer.valueOf(ser.hashCode()));
		      this.hiber.getSession().getTransaction().commit();
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      this.hiber.closeSession();
		    }
		  }

		  public void insertProfessional(ProfessionalExpEntity certEnt) {
		    try {
		      Serializable ser = this.hiber.getSession().save(certEnt);
		      certEnt.setId(Integer.valueOf(ser.hashCode()));
		      this.hiber.getSession().getTransaction().commit();
		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      this.hiber.closeSession();
		    }
		  }

		  public boolean updatePassword(String username, String password)
		  {
		    boolean updated = false;
		    if ((username != null) && (!username.trim().equalsIgnoreCase("")))
		    {
		      try
		      {
		        String sql = "SELECT * FROM users WHERE  username = :username ";
		        Query query = this.hiber.getSession().createSQLQuery(sql).addEntity("users.", UsersEntity.class);
		        query.setString("username", username);

		        List result = query.list();
		        if ((result != null) && (result.size() > 0))
		        {
		          password = this.utils.encryptText(password);

		          UsersEntity userEnity = (UsersEntity)result.get(0);
		          userEnity.setPassword(password);
		          userEnity.setPass_creationDate(null);
		          userEnity.setPass_emailCount(Integer.valueOf(0));
		          this.hiber.getSession().update(userEnity);

		          Admin admin = (Admin)this.hiber.getSession().createCriteria(Admin.class)
		            .add(Restrictions.eq("this.username", username))
		            .uniqueResult();

		          if (admin != null)
		          {
		            admin.setPassword(password);

		            this.hiber.getSession().update(admin);
		          }

		          this.hiber.getSession().getTransaction().commit();
		          updated = true;
		        }
		      }
		      catch (Exception e)
		      {
		        System.out.println("UsersDAO : Ex in checkUserInRegisterByUsername : " + e.getMessage());
		        updated = false;
		      }
		      finally
		      {
		        this.hiber.closeSession();
		      }
		    }
		    return updated;
		  }

		  public int checkNationalId(String nationalId)
		  {
		    boolean exist = false;

		    int id = 0;
		    if ((nationalId != null) && (!nationalId.trim().equalsIgnoreCase(""))) {
		      try {
		        String sql = " SELECT * FROM users WHERE national_id = :nationalId ";
		        Query query = this.hiber.getSession().createSQLQuery(sql).addEntity("users", UsersEntity.class);
		        query.setString("nationalId", nationalId);
		        List results = query.list();

		        if ((results != null) && (results.size() > 0))
		        {
		          UsersEntity userEntity = (UsersEntity)results.get(0);
		          id = userEntity.getId().intValue();
		        }
		        exist = true;
		      }
		      catch (Exception e) {
		        System.out.println("UsersDAO : Ex in nationalId : " + e.getMessage());
		        exist = false;
		      } finally {
		        this.hiber.closeSession();
		      }
		    }
		    return id;
		  }

	
	
	public boolean checkUserInRegisterByEmail(String email)
	{
		boolean exist = false;
		if ((email != null) && (!email.trim().equalsIgnoreCase("")))
		{
			try
			{
				String sql = " SELECT * FROM users WHERE email = :email  ";
				Query query = this.hiber.getSession().createSQLQuery(sql);
				query.setString("email", email);
				List results = query.list();
				
				if ((results != null) && (results.size() > 0))
					exist = true;
			}
			catch (Exception e)
			{
				System.out.println("UsersDAO : Ex in checkUserInRegisterByEmail : " + e.getMessage());
				exist = false;
			}
			finally
			{
				this.hiber.closeSession();
			}
		}
		return exist;
	}
		
	public boolean checkUserInRegisterByMobile(String mobile)
	{
		boolean exist = false;
		if ((mobile != null) && (!mobile.trim().equalsIgnoreCase("")))
		{
			try
			{
				String sql = " SELECT * FROM users WHERE mobile = :mobile  ";
				Query query = this.hiber.getSession().createSQLQuery(sql);
				query.setString("mobile", mobile);
				List results = query.list();
				
				if ((results != null) && (results.size() > 0))
					exist = true;
			}
			catch (Exception e)
			{
				System.out.println("UsersDAO : Ex in checkUserInRegisterByMobile : " + e.getMessage());
				exist = false;
			}
			finally
			{
				this.hiber.closeSession();
			}
		}
		return exist;
	}



		  public boolean checkUserInRegisterByEmail(String email, int user_id)
		  {
		    boolean exist = false;
		    if ((email != null) && (!email.trim().equalsIgnoreCase(""))) {
		      try {
		        String sql = " SELECT * FROM users WHERE email = :email  and  email not in (select email from users where id=:user_id)";
		        Query query = this.hiber.getSession().createSQLQuery(sql);
		        query.setString("email", email).setInteger("user_id", user_id);
		        List results = query.list();

		        if ((results != null) && (results.size() > 0))
		          exist = true;
		      }
		      catch (Exception e) {
		        System.out.println("UsersDAO : Ex in checkUserInRegisterByEmail : " + e.getMessage());
		        exist = false;
		      } finally {
		        this.hiber.closeSession();
		      }
		    }
		    return exist;
		  }

		  public UsersEntity validateEmail(String email)
		  {
		    boolean exist = false;
		    UsersEntity entity = null;
		    if ((email != null) && (!email.trim().equalsIgnoreCase(""))) {
		      try {
		        String sql = " SELECT * FROM users users WHERE email = :email  ";
		        Query query = this.hiber.getSession().createSQLQuery(sql).addEntity("users", UsersEntity.class);
		        query.setString("email", email);
		        List results = query.list();

		        if ((results != null) && (results.size() > 0))
		          entity = (UsersEntity)results.get(0);
		      }
		      catch (Exception e) {
		        System.out.println("UsersDAO : Ex in checkUserInRegisterByEmail : " + e.getMessage());
		        exist = false;
		      } finally {
		        this.hiber.closeSession();
		      }

		    }

		    return entity;
		  }

		  public Integer isUserExist(String userName, String password)
		  {
		    Integer userId = Integer.valueOf(0);

		    UsersEntity u = (UsersEntity)this.hiber.getSession().createCriteria(UsersEntity.class)
		      .add(Restrictions.ilike("username", userName))
		      .add(Restrictions.ilike("password", password)).uniqueResult();
		    if (u != null)
		    {
		      userId = u.getId();
		    }

		    return userId;
		  }

		  public int checkUserBranchOrSpecialist2(int userID)
		  {
		    int status = 0;
		    if (userID > 0) {
		      try {
		        String sql = " SELECT * FROM branch WHERE users_id = :userID ";

		        Query query = this.hiber.getSession().createSQLQuery(sql);
		        query.setInteger("userID", userID);

		        List results = query.list();

		        if ((results != null) && (results.size() > 0)) {
		          return 1;
		        }
		        sql = " SELECT * FROM specialist WHERE users_id = :userID";
		        query = this.hiber.getSession().createSQLQuery(sql);
		        query.setInteger("userID", userID);

		        results = query.list();
		        if ((results != null) && (results.size() > 0))
		          return 2;
		        status = 6;
		      } catch (Exception e) {
		        System.out.println("UsersDAO : Ex in checkUserBranchOrSpecialist : " + e.getMessage());
		        status = 0;
		      } finally {
		        this.hiber.closeSession();
		      }
		    }
		    return status;
		  }

		  public List<UsersEntity> getCommunications2(int userId)
		  {
		    List entities = null;
		    if (userId > 0) {
		      try
		      {
		        String sql = " SELECT us.* FROM ( SELECT DISTINCT ms.to_user AS userId FROM messages ms WHERE ms.from_user = :userId AND ms.to_user != :userId  UNION SELECT DISTINCT ms.from_user AS userId FROM messages ms WHERE ms.to_user = :userId AND ms.from_user != :userId ) al  INNER JOIN users us ON al.userId = us.id ";

		        Query query = this.hiber.getSession().createSQLQuery(sql).addEntity(UsersEntity.class);

		        query.setInteger("userId", userId);

		        entities = query.list();
		      }
		      catch (Exception e) {
		        System.out.println("UsersDAO : Ex in addSpecialistComment " + e.getMessage());
		        e.printStackTrace();
		      } finally {
		        this.hiber.closeSession();
		      }
		    }
		    return entities;
		  }

		  public List<UsersEntity> getByMatchNationalId2(String NationalId)
		  {
		    List entities = null;
		    try
		    {
		      Criteria criteria = this.hiber.getSession().createCriteria(UsersEntity.class)
		        .add(Restrictions.like("nationalId", NationalId + "%"));
		      entities = criteria.list();

		      return entities;
		    } catch (Exception ex) {
		      ex.printStackTrace();
		    } finally {
		      this.hiber.closeSession();
		    }
		    return entities;
		  }

		  public List<UsersEntity> getAllByGroupId2(int groupId)
		  {
		    List entities = null;
		    try {
		      String sql = " SELECT users.* FROM users users INNER JOIN users_groups usg ON usg.users_id = users.id WHERE groups_id = :groupId ";
		      Query query = this.hiber.getSession().createSQLQuery(sql)
		        .addEntity("users.", UsersEntity.class);
		      query.setInteger("groupId", groupId);
		      entities = query.list();
		    } catch (Exception ex) {
		      System.out.println("UsersDAO : Get Ex : " + ex.getMessage());
		    } finally {
		      this.hiber.closeSession();
		    }
		    return entities;
		  }
	
	
	
	
	
	
	
	public UsersEntity forgetPassword(String email, String forget_password_code)
	{
		UsersEntity user = null;
		try
		{
			user = (UsersEntity) hiber.getSession().createCriteria(UsersEntity.class)
					.add(Restrictions.eq("this.email", email))
					.uniqueResult();
			
			if(utils.hasValue(user))
			{
				user.setForget_password_code(forget_password_code);
				
				hiber.getSession().update(user);
				hiber.getSession().getTransaction().commit();
			}
			
			return user;
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
			
			return null;
		}
		finally
		{
			hiber.closeSession();
		}
	}
	
	public UsersEntity resetPassword(String email, String code, String password)
	{
		UsersEntity user = null;
		try
		{
			user = (UsersEntity) hiber.getSession().createCriteria(UsersEntity.class)
					.add(Restrictions.eq("this.email", email))
					.add(Restrictions.eq("this.forget_password_code", code))
					.uniqueResult();
			
			if(utils.hasValue(user))
			{
				user.setPassword(utils.encryptText(password));
				
				hiber.getSession().update(user);
				hiber.getSession().getTransaction().commit();
			}
			
			return user;
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
			
			return null;
		}
		finally
		{
			hiber.closeSession();
		}
	}
	
	public UsersEntity updatePassword(Integer user_id, String password, String password_new)
	{
		UsersEntity user = null;
		try
		{
			user = (UsersEntity) hiber.getSession().createCriteria(UsersEntity.class)
					.add(Restrictions.eq("this.id", user_id))
					.add(Restrictions.eq("this.password", utils.encryptText(password)))
					.uniqueResult();
			
			if(utils.hasValue(user))
			{
				user.setPassword(utils.encryptText(password_new));
				
				hiber.getSession().update(user);
				hiber.getSession().getTransaction().commit();
			}
			
			return user;
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
			
			return null;
		}
		finally
		{
			hiber.closeSession();
		}
	}
	
	public UsersEntity logout(Integer user_id)
	{
		UsersEntity user = null;
		try
		{
			user = (UsersEntity) hiber.getSession().createCriteria(UsersEntity.class)
					.add(Restrictions.eq("this.id", user_id))
					.uniqueResult();
			
			if(utils.hasValue(user))
			{
				user.setLogin(0);
				
				hiber.getSession().update(user);
				hiber.getSession().getTransaction().commit();
			}
			
			return user;
		}
		catch (HibernateException ex)
		{
			System.out.println("Get Row : " + ex.getMessage());
			
			return null;
		}
		finally
		{
			hiber.closeSession();
		}
	}
	



}
