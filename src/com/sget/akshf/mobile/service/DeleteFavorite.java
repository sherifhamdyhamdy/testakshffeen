package com.sget.akshf.mobile.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.service.MobileService;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.SuccessAPI;

/**
 * 
 * @author JDeeb
 * Add Specialist to User Favorite
 */
@Path("/deleteFavorite")
public class DeleteFavorite implements AppConstants{

	private Gson gson = new Gson();
	private MobileService mobileService = new MobileService() ;
	
	/**
	 * 
	 * @param appCat Categories ( Branch , Specialist , Content )
	 * @param id of ( Branch , Specialist , content )
	 * @param userId
	 * @return
	 */
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteFavorite(@FormParam("appCat") int appCat ,@FormParam("id") int id , @FormParam("userId") int userId){
		
		if(appCat > 0){
			if(appCat == APP_BRANCH_ID || appCat == APP_SPECIALIST_ID || appCat == APP_CONTENT_NEWS_ID 
					|| appCat == APP_CONTENT_TIPS_ID || appCat == APP_CONTENT_OFFERS_ID){
				if(id > 0 && userId > 0){
					int result = mobileService.deleteFavorite(appCat , id , userId);
					
					if(result == 1)
						return gson.toJson(new SuccessAPI(ResponseFactory.CODE_SUCCESS,ResponseFactory.MESSAGE_SUCCESS));
					else if(result == 2)
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL , ResponseFactory.MESSAGE_FAIL));
					else if(result == 0)
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX , ResponseFactory.MESSAGE_FAIL_EX));
					else
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
				}else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT , ResponseFactory.MESSAGE_INVALID_INPUT));
			}else
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT , ResponseFactory.MESSAGE_INVALID_INPUT));
		}else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT , ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
