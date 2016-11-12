package com.sget.akshf.mobile.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.service.MobileService;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;
import com.sget.akshf.mobile.api.ResponseListWithSize;

/**
 * 
 * @author JDeeb
 * Get Comments
 * 
 */

@Path("/getComments")
public class GetComments implements AppConstants{

	private Gson gson = new Gson();
	private MobileService mobileService = new MobileService();
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String GetCommentsList(@FormParam("start") int start, @FormParam("limit") int limit, @FormParam("appCat") int appCat, @FormParam("id") int id) {
		// Service Add User Comment
		// Mandatory parameters
		if (start >= 0 && limit > 0 && id != 0 && appCat >= 1 && appCat <= 5 ) {
			try {
				ResponseFormatData ret = new ResponseFormatData();
				List<CommentsBean> comments = null ;
				if (appCat == APP_BRANCH_ID) {
					comments = mobileService.getBranchComments(start, limit, id);
				} else if (appCat == APP_SPECIALIST_ID) {
					comments = mobileService.getSpecialistComments(start, limit, id);
				} else if (appCat == APP_CONTENT_NEWS_ID || appCat == APP_CONTENT_TIPS_ID || appCat == APP_CONTENT_OFFERS_ID) {
					comments = mobileService.getContentComments(start, limit, id);
				}else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
				
				ResponseListWithSize<CommentsBean> commen = new ResponseListWithSize<CommentsBean>();
	        	if(comments != null && comments.size() > 0){
	        		commen.setComments(comments);
	        		commen.setNoOfComments(comments.size());
	            	
	        		ret.setData(commen);
	            	ret.setCode(ResponseFactory.CODE_SUCCESS);
	            	ret.setMessage(ResponseFactory.MESSAGE_SUCCESS);
	            	return gson.toJson(ret);
	        	}else
	        		return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA , ResponseFactory.MESSAGE_NO_DATA));

			} catch (Exception ex) {
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX,ResponseFactory.MESSAGE_FAIL_EX));
			}
		} else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
	
}
