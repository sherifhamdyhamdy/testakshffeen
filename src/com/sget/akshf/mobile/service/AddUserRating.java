package com.sget.akshf.mobile.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.MobileService;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.mobile.api.AppRatingBean;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb rateBranch Web Service
 */

@Path("/addUserRate")
public class AddUserRating implements AppConstants {

	private Gson gson = new Gson();
	private MobileService mobileService = new MobileService();
	private BranchService branchService = new BranchService();
	private SpecialistService specService = new SpecialistService();
	private ContentDetailsService contService = new ContentDetailsService();
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addUserRating(@FormParam("appCat") int appCat, @FormParam("id") int id, @FormParam("userId") int userId, @FormParam("rating") int rating) {
		// Service Add Branch Favorites
		// Mandatory parameters
		if (id != 0 && userId != 0 && appCat >= 1 && appCat <= 5) {

			int result = 0;
			AppRatingBean bean = new AppRatingBean();
			ResponseFormatData respon = new ResponseFormatData();
			if (appCat == APP_BRANCH_ID) {
				result = mobileService.addUserBranchRating(id, userId, rating);
				BranchBean branch = branchService.getById(id);
				bean.setRating(branch.getRating());
				bean.setRatingno(branch.getRatingno());
				branchService.updateBranchRating(branch.getRating(),id);
			} else if (appCat == APP_SPECIALIST_ID) {
				result = mobileService.addUserSpecialistRating(id, userId, rating);
				SpecialistBean spec = specService.getById(id);
				bean.setRating(spec.getRating());
				bean.setRatingno(spec.getRatingno());
				/////////////
			} else if (appCat == APP_CONTENT_NEWS_ID || appCat == APP_CONTENT_TIPS_ID || appCat == APP_CONTENT_OFFERS_ID) {
				result = mobileService.addUserContentRating(id, userId, rating);
				ContentDetailsBean cont = contService.getById(id);
				bean.setRating(cont.getRating());
				bean.setRatingno(cont.getRatingno());
				// update Content Details rating
				contService.updateContentRating(cont.getRating(), id);
			}else
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
			
			if (result == 1){
				respon.setData(bean);
				respon.setCode(ResponseFactory.CODE_SUCCESS);
				respon.setMessage(ResponseFactory.MESSAGE_SUCCESS);
				return gson.toJson(respon);
			}else if (result == 2){
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL, ResponseFactory.MESSAGE_FAIL));
			}else if (result == 0){
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}else
				return "";
		} else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
