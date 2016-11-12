package com.sget.akshf.mobile.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.service.CommentsService;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb addUserComment Web Service
 */

@Path("/addUserComment")
public class AddUserComment implements AppConstants {

	private Gson gson = new Gson();
	private CommentsService commService = new CommentsService();
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addUserComment(@FormParam("appCat") int appCat, @FormParam("id") int id, @FormParam("userId") int userId, @FormParam("comment") String comment) {
		// Service Add User Comment
		// Mandatory parameters
		if (id != 0 && userId != 0 && appCat >= 1 && appCat <= 5 && comment != null && !comment.equals("")) {

			try{
				CommentsBean bean = null ;
				if (appCat == APP_BRANCH_ID) {
					bean = commService.addBranchComment(id, userId, comment);
				} else if (appCat == APP_SPECIALIST_ID) {
					bean = commService.addSpecialistComment(id, userId, comment);
				} else if (appCat == APP_CONTENT_NEWS_ID || appCat == APP_CONTENT_TIPS_ID || appCat == APP_CONTENT_OFFERS_ID) {
					bean = commService.addContentComment(id, userId, comment);
				}else{
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
				}
				ResponseFormatData respons = new ResponseFormatData();
				if (bean != null && bean.getId() > 0) {
					respons.setData(bean);
					respons.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					respons.setCode(ResponseFactory.CODE_SUCCESS);
					return gson.toJson(respons);
				}else if(bean == null)
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL, ResponseFactory.MESSAGE_FAIL));
				else
					return "";
			} catch (Exception e) {
				 System.out.println("EX in send Doctor Message : " + e.getMessage());
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		} else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
