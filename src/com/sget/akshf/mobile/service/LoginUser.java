package com.sget.akshf.mobile.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb Login Web Service
 */
@Path("/login")
public class LoginUser
{
	
	private Gson gson = new Gson();
	
	private UsersService service = new UsersService();
	
	@Context
	private HttpServletRequest httpRequestt;
	
	private Utils utils = Utils.getInstance();

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String loginUser(
							@FormParam("username") String username, 
							@FormParam("pass") String pass,
							@FormParam("token") String token,
							@FormParam("platform") int platform)
	{
		
		StringBuffer baseURL = httpRequestt.getRequestURL();
		String path = httpRequestt.getServletPath();
		String url = utils.getXMLkey("AkshffeenAdmin");
		if (username != null && !username.trim().equalsIgnoreCase("") && pass != null && !pass.trim().equalsIgnoreCase(""))
		{
			try
			{
				UsersBean user = service.loginUser(username, pass, url, token, platform);
				ResponseFormatData respo = new ResponseFormatData();
				
				if (user != null && user.getId() > 0)
				{					
					respo.setData(user);
					respo.setCode(ResponseFactory.CODE_SUCCESS);
					respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
				}
				else
				{
					respo.setData(user);
					respo.setCode(ResponseFactory.CODE_INVALID_LOGIN);
					respo.setMessage(ResponseFactory.MESSAGE_INVALID_LOGIN);
				}
				return gson.toJson(respo);
			}
			catch (Exception e)
			{
				System.out.println("Ex in Login : " + e.getMessage());
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
		
	}
}
