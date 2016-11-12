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
@Path("/updatePassword_old")
public class UpdatePassword
{
	
	private Gson gson = new Gson();
	
	private UsersService service = new UsersService();
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String ForgotUpdatePassword(@FormParam("username") String username, @FormParam("pass") String pass)
	{
		if (username != null && !username.trim().equalsIgnoreCase("") && pass != null && !pass.trim().equalsIgnoreCase(""))
		{
			try
			{
				boolean isupdated = service.updatePassword(username, pass);
				if (isupdated)
				{
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_PASSWORD_UPDATE, ResponseFactory.MESSAGE_PASSWORD_UPDATE));
				}
				else
				{
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_PASSWORD_NOT, ResponseFactory.MESSAGE_PASSWORD_NOT));
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
