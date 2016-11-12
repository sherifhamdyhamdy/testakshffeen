package com.sget.akshf.mobile.service;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.akshffeen.utils.Constants;
import com.akshffeen.utils.Image;
import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.UserLoginBean;
import com.sget.akshef.hibernate.dao.UsersDAO;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.utils.Performance;
import com.sget.akshf.mobile.api.ResponseFactory;


@Path("/user")
public class UserServices
{
	private UsersDAO userDAO = new UsersDAO();
	private Utils utils = Utils.getInstance();
	
	
	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/forgetPassword")
	public String forgetPassword(@FormParam("email") String email) 
	{
		Gson gson = new Gson();
		
		UserLoginBean userLoginBean = new UserLoginBean();
		
		String forget_password_code = utils.generateRandomString();
		UsersEntity user = userDAO.forgetPassword(email, forget_password_code);
		
		if(utils.hasValue(user))
		{
			try
			{
				String content = "Kindly use this code '"+forget_password_code+"' in forget password ";
			
				utils.sendMail(email, "Forget Password", "Mr/Ms "+user.getNameAr()+"", content);
				
				userLoginBean.setCode(ResponseFactory.CODE_SUCCESS);
				userLoginBean.setMessage(ResponseFactory.MESSAGE_SUCCESS);
			}
			catch(Exception e)
			{
				userLoginBean.setCode(ResponseFactory.CODE_FAIL);
				userLoginBean.setMessage(ResponseFactory.MESSAGE_FAIL);
			}
		}
		else
		{
			userLoginBean.setCode(ResponseFactory.CODE_EMAIL_NOT_EXIST);
			userLoginBean.setMessage(ResponseFactory.MESSAGE_EMAIL_NOT_EXIST);
		}
		
		String output = gson.toJson(userLoginBean);
		
		Performance.releaseMemory();

		return output;
	}
	
	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/resetPassword")
	public String resetPassword(
								@FormParam("email") String email,
								@FormParam("code") String code,
								@FormParam("password") String password) 
	{
		Gson gson = new Gson();
		
		UserLoginBean userLoginBean = new UserLoginBean();
		
		if(!utils.hasValue(code))
		{
			userLoginBean.setCode(ResponseFactory.CODE_FAIL);
			userLoginBean.setMessage(ResponseFactory.MESSAGE_FAIL);
		}
		else
		{
			UsersEntity user = userDAO.resetPassword(email, code, password);
			
			if(utils.hasValue(user))
			{
				try
				{
					userLoginBean.setCode(ResponseFactory.CODE_SUCCESS);
					userLoginBean.setMessage(ResponseFactory.MESSAGE_SUCCESS);
				}
				catch(Exception e)
				{
					userLoginBean.setCode(ResponseFactory.CODE_FAIL);
					userLoginBean.setMessage(ResponseFactory.MESSAGE_FAIL);
				}
			}
			else
			{
				userLoginBean.setCode(ResponseFactory.CODE_NOT_EXIST);
				userLoginBean.setMessage(ResponseFactory.MESSAGE_CODE_NOT_EXIST);
			}
		}
		
		String output = gson.toJson(userLoginBean);
		
		Performance.releaseMemory();

		return output;
	}
	
	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/updatePassword")
	public String updatePassword(
								@FormParam("userId") Integer user_id,
								@FormParam("password") String password,
								@FormParam("password_new") String password_new) 
	{
		Gson gson = new Gson();
		
		UserLoginBean userLoginBean = new UserLoginBean();
		
		UsersEntity user = userDAO.updatePassword(user_id, password, password_new);
		
		if(utils.hasValue(user))
		{
			try
			{
				userLoginBean.setCode(ResponseFactory.CODE_SUCCESS);
				userLoginBean.setMessage(ResponseFactory.MESSAGE_SUCCESS);
			}
			catch(Exception e)
			{
				userLoginBean.setCode(ResponseFactory.CODE_FAIL);
				userLoginBean.setMessage(ResponseFactory.MESSAGE_FAIL);
			}
		}
		else
		{
			userLoginBean.setCode(ResponseFactory.CODE_INVALID_PASSWORD);
			userLoginBean.setMessage(ResponseFactory.MESSAGE_CODE_INVALID_PASSWORD);
		}
		
		
		String output = gson.toJson(userLoginBean);
		
		Performance.releaseMemory();

		return output;
	}
	
	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/logout")
	public String logout(@FormParam("user_id") Integer user_id) 
	{
		Gson gson = new Gson();
		
		UserLoginBean userLoginBean = new UserLoginBean();
		
		UsersEntity user = userDAO.logout(user_id);
		
		if(utils.hasValue(user))
		{
			try
			{
				userLoginBean.setCode(ResponseFactory.CODE_SUCCESS);
				userLoginBean.setMessage(ResponseFactory.MESSAGE_SUCCESS);
			}
			catch(Exception e)
			{
				userLoginBean.setCode(ResponseFactory.CODE_FAIL);
				userLoginBean.setMessage(ResponseFactory.MESSAGE_FAIL);
			}
		}
		else
		{
			userLoginBean.setCode(ResponseFactory.CODE_USERID_NOT_EXIST);
			userLoginBean.setMessage(ResponseFactory.MESSAGE_USERID_NOT_EXIST);
		}
		
		
		String output = gson.toJson(userLoginBean);
		
		Performance.releaseMemory();

		return output;
	}
	

	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/updateImage")
	public String updateImage(
								@FormParam("user_id") int user_id,
								@FormParam("image") String image, 
								@FormParam("extension") String extension) 
	{
		Gson gson = new Gson();
		
		UserLoginBean userLoginBean = new UserLoginBean();
		
		try
		{		
			System.out.println("UserServices.updateImage()");
			
			if(!utils.fixRequest(image).equals(""))
			{		
				String query = "";
				String charset = Constants.charset;

				try
				{			
					query = String.format("user_id=%s&image=%s&extension=%s&method=%s&folder=%s", 
					     	URLEncoder.encode(user_id+"", charset),
					     	URLEncoder.encode(image, charset),
					     	URLEncoder.encode(extension, charset),
					     	URLEncoder.encode("upateUserImage", charset),
							URLEncoder.encode("users", charset));

				}
				catch (Exception e) {
					// TODO: handle exception
				}
								
//				String action = Constants.success;
				String action = utils.sendImage(query);
				
				System.out.println("action : "+action);
//				image = new Image().uploadImage(image, extension, user.getProfile_img(),"images/users");
				
				if(action.equals(Constants.success))
				{					
					userLoginBean.setCode(ResponseFactory.CODE_SUCCESS);
					userLoginBean.setMessage(ResponseFactory.MESSAGE_SUCCESS);
				}
				else
				{
					userLoginBean.setCode(ResponseFactory.CODE_INVALID_UPLOAD_IMAGE);
					userLoginBean.setMessage(ResponseFactory.MESSAGE_INVALID_UPLOAD_IMAGE);
				}
			}
		}
		catch(Exception e)
		{
			userLoginBean.setCode(ResponseFactory.CODE_FAIL);
			userLoginBean.setMessage(ResponseFactory.MESSAGE_FAIL);
		}
		
		String output = gson.toJson(userLoginBean);
		
		Performance.releaseMemory();

		return output;
	}

	

}