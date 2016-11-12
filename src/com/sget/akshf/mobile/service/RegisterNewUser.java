package com.sget.akshf.mobile.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.akshffeen.utils.Constants;
import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb Register Web Service
 */
@Path("/register")
public class RegisterNewUser
{
	
	private Gson gson = new Gson();
	
	private UsersService service = new UsersService();
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String registerUser(
			@FormParam("firstname") String firstname, 
			@FormParam("lastname") String lastname, 
			@FormParam("username") String username, 
			@FormParam("pass") String pass, 
			@FormParam("email") String email, 
			@FormParam("mobile") String mobile, 
			@FormParam("gender") int gender, 
			@FormParam("birthdate") long birthdate,
			@FormParam("token") String token,
			@FormParam("platform") int platform)
	{
		
		if (username != null && !username.trim().equalsIgnoreCase("") && pass != null && !pass.trim().equalsIgnoreCase("") && email != null && !email.trim().equalsIgnoreCase(""))
		{
			try
			{
				
				boolean mail_exist = service.checkUserInRegisterByEmail(email);
				boolean username_exist = service.checkUserInRegisterByUsername(username);
				boolean mobile_exist = service.checkUserInRegisterByMobile(mobile);
				
				if (!mail_exist && !username_exist && !mobile_exist)
				{					
					UsersBean user = new UsersBean();
					
					user.setFirstName(firstname);
					user.setLastName(lastname);

					user.setUsername(username);
					user.setPassword(pass);
					user.setEmail(email);
					user.setGender(gender);
					user.setMobile(mobile);
					
					user.setBirthdate(new Date(birthdate));
					user.setBirthdateStr(formatter.format(new Date(birthdate)));
					
					user.setActive(1);
					user.setUser_type(1);			//1 - user ,   2 - doctor
					
					user.setToken(token);
					user.setPlatform(platform);
					user.setLogin(1);
					
					service.insert(user);
					
					user.setPassword(null);

					ResponseFormatData respo = new ResponseFormatData();
					if (user.getId() > 0)
					{
						respo.setData(user);
						respo.setCode(ResponseFactory.CODE_SUCCESS);
						respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					}
					else
					{
						respo.setData(user);
						respo.setCode(ResponseFactory.CODE_FAIL);
						respo.setMessage(ResponseFactory.MESSAGE_FAIL);
					}
					return gson.toJson(respo);
				}
				else if (mail_exist && username_exist && mobile_exist)
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_USERNAME_EMAIL_MOBILE_EXIST, ResponseFactory.MESSAGE_USERNAME_EMAIL_MOBILE_EXIST));
				else if (mail_exist)
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_EMAIL_EXIST, ResponseFactory.MESSAGE_EMAIL_EXIST));
				else if (username_exist)
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_USERNAME_EXIST, ResponseFactory.MESSAGE_USERNAME_EXIST));
				else if (mobile_exist)
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_MOBILE_EXIST, ResponseFactory.MESSAGE_MOBILE_EXIST));
				else
					return "";
			}
			catch (Exception e)
			{
				System.out.println("Ex in Resgister New User : " + e.getMessage());
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
		
	}
}
