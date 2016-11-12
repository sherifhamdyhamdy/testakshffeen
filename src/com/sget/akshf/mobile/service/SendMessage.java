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
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.MessagesService;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.MobileMessagesBean;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb Send Messages Web Service
 */

@Path("/sendMessage")
public class SendMessage implements AppConstants
{
	
	private Gson gson = new Gson();
	
	private MessagesService msgService = new MessagesService();
	
	@Context
	private HttpServletRequest httpRequest;
	
	private Utils utils = Utils.getInstance();

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addDoctorMessage(
			@FormParam("appCat") int appCat, 			
			@FormParam("toId") int toId, 
			@FormParam("userId") int userId, 
			@FormParam("text") String text)
	{
		// Service Send Messages
		// Mandatory parameters
		if (appCat == APP_BRANCH_ID || appCat == APP_SPECIALIST_ID)
		{
			String url = utils.getXMLkey("AkshffeenAdmin");
			
			if (appCat == APP_SPECIALIST_ID)		//2
			{
				if (toId != 0 && userId != 0 && text != null && !text.trim().equalsIgnoreCase(""))
				{
					try
					{
						MobileMessagesBean bean = msgService.addDoctorMessage(toId, userId, text, url);
						ResponseFormatData respons = new ResponseFormatData();
						if (bean != null && bean.getId() > 0)
						{
							respons.setData(bean);
							respons.setMessage(ResponseFactory.MESSAGE_SUCCESS);
							respons.setCode(ResponseFactory.CODE_SUCCESS);
							return gson.toJson(respons);
						}
						else if (bean == null)
							return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL, ResponseFactory.MESSAGE_FAIL));
						else
							return "";
					}
					catch (Exception e)
					{
						System.out.println("EX in send Doctor Message : " + e.getMessage());
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
					}
				}
				else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
			}
			else if (appCat == APP_BRANCH_ID)			//1
			{
				if (toId != 0 && userId != 0 && text != null && !text.trim().equalsIgnoreCase(""))
				{
					try
					{
						boolean action = msgService.addUnitMessage(toId, userId, text, url);
						ResponseFormatData respons = new ResponseFormatData();
						if (action)
						{
							respons.setData(null);
							respons.setMessage(ResponseFactory.MESSAGE_SUCCESS);
							respons.setCode(ResponseFactory.CODE_SUCCESS);
							return gson.toJson(respons);
						}
						else if (!action)
							return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL, ResponseFactory.MESSAGE_FAIL));
						else
							return "";
					}
					catch (Exception e)
					{
						System.out.println("EX in send Doctor Message : " + e.getMessage());
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
					}
				}
				else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
			}
			else
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
