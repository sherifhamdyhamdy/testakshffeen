package com.sget.akshf.mobile.service;

import java.util.List;

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
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.MobileMessagesBean;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;

/**
 * 
 * @author JDeeb
 * Login Web Service
 */
@Path("/getMessages")
public class GetMessages {
	private Utils utils = Utils.getInstance();

	private Gson gson = new Gson();
	private MessagesService service = new MessagesService();

	@Context
	private HttpServletRequest httpRequest ;
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String loginUser(@FormParam("userId") int userId ,@FormParam("oppositeID") int oppositeID ,@FormParam("start") int start ,@FormParam("limit") int limit){
		
		if( userId > 0 && oppositeID > 0 &&  start >= 0 && limit >= 0){
			StringBuffer baseURL = httpRequest.getRequestURL();
			String path=httpRequest.getServletPath();
		    String 	url=utils.getXMLkey("AkshffeenAdmin");
		    
			try{
				List<MobileMessagesBean> messages = service.getAllMessagesBetweenUsers(userId, oppositeID, start, limit,url);
				ResponseCollectFormatData<MobileMessagesBean> respon = new ResponseCollectFormatData<MobileMessagesBean>();
				
				if(messages != null && messages.size() > 0){
					respon.setData(messages);
					respon.setCode(ResponseFactory.CODE_SUCCESS);
					respon.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					return gson.toJson(respon);
				}else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA,ResponseFactory.MESSAGE_NO_DATA));
			}catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX,ResponseFactory.MESSAGE_FAIL_EX));
			}
		}else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT,ResponseFactory.MESSAGE_INVALID_INPUT));
		
	}
}
