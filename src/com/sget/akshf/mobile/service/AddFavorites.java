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
 * addBranchToFav Web Service
 */

@Path("/addFavorties")
public class AddFavorites implements AppConstants{
	
	private Gson gson = new Gson();
	private MobileService mobileService = new MobileService() ;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addBranchToFav(@FormParam("appCat") int appCat , @FormParam("id") int id , @FormParam("userId") int userId){
		// Service Add Favorites
		// Mandatory parameters
		System.out.println("AddFavorites.addBranchToFav()");
		
		if(appCat != 0 && id != 0 && userId != 0 ){
			if(appCat == APP_BRANCH_ID){
				boolean exist = mobileService.checkIfBranchAlreadyFav(id, userId);
				if(!exist){
					int result = mobileService.addBranchToFav(id, userId);
					if(result == 1)
						return gson.toJson(new SuccessAPI(ResponseFactory.CODE_SUCCESS,ResponseFactory.MESSAGE_SUCCESS));
					else if(result == 2)
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL , ResponseFactory.MESSAGE_FAIL));
					else if(result == 0)
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX , ResponseFactory.MESSAGE_FAIL_EX));
					else
						return "";
				}else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_ALREADY_EXIST , ResponseFactory.MESSAGE_ALREADY_EXIST));
			}else if(appCat == APP_SPECIALIST_ID){
				boolean exist = mobileService.checkIfSpecialistAlreadyFav(id, userId);
				if(!exist){
					int result = mobileService.addSpecialistToFav(id, userId);
					if(result == 1)
						return gson.toJson(new SuccessAPI(ResponseFactory.CODE_SUCCESS,ResponseFactory.MESSAGE_SUCCESS));
					else if(result == 2)
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL , ResponseFactory.MESSAGE_FAIL));
					else if(result == 0)
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX , ResponseFactory.MESSAGE_FAIL_EX));
					else
						return "";
				}else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_ALREADY_EXIST , ResponseFactory.MESSAGE_ALREADY_EXIST));
			}else if(appCat == APP_CONTENT_NEWS_ID || appCat == APP_CONTENT_TIPS_ID || appCat == APP_CONTENT_OFFERS_ID){
				boolean exist = mobileService.checkIfContentAlreadyFav(id, userId);
				if(!exist){
					int result = mobileService.addContentToFav(id, userId);
					if(result == 1)
						return gson.toJson(new SuccessAPI(ResponseFactory.CODE_SUCCESS,ResponseFactory.MESSAGE_SUCCESS));
					else if(result == 2)
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL , ResponseFactory.MESSAGE_FAIL));
					else if(result == 0)
						return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX , ResponseFactory.MESSAGE_FAIL_EX));
					else
						return "";
				}else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_ALREADY_EXIST , ResponseFactory.MESSAGE_ALREADY_EXIST));
			}else
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT , ResponseFactory.MESSAGE_INVALID_INPUT));
		}else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT , ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
