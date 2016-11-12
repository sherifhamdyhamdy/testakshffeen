package com.sget.akshf.mobile.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
@Path("/ForgotPasswordCheckEmail")
public class ForgotPasswordCheckEmail
{
	
	private Gson gson = new Gson();
	
	private UsersService service = new UsersService();
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String ForgotPasswordCheckEmail(@FormParam("email") String email)
	{
		
		if (email != null && !email.trim().equalsIgnoreCase(""))
		{
			try
			{
				boolean mail_exist = service.checkUserInRegisterByEmail(email);
				if (mail_exist)
				{
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_SUCCESS, ResponseFactory.MESSAGE_SUCCESS));
				}
				else
				{
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_EMAIL_NOT_EXIST, ResponseFactory.MESSAGE_EMAIL_NOT_EXIST));
				}
				
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
