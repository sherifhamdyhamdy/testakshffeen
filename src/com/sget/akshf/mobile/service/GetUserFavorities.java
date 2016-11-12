package com.sget.akshf.mobile.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.UserFavoritiesForMobile;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.UserFavoritiesService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb
 * getDoctorPlaces Web Service
 * 
 */
@Path("/getUserFavorities")
public class GetUserFavorities {
	private Utils utils = Utils.getInstance();

	private Gson gson = new Gson();
	private UserFavoritiesService service = new UserFavoritiesService();
	@Context
	private HttpServletRequest httpRequest ;
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserFavorities(@FormParam("userId") int userId ){
		// Mandatory Parameters
		if(userId > 0){
			StringBuffer baseURL = httpRequest.getRequestURL();
			String path=httpRequest.getServletPath();
		    String 	url=utils.getXMLkey("AkshffeenAdmin");
		    
			UserFavoritiesForMobile bean = service.getAllForMobile(userId,url);
			ResponseFormatData respo = new ResponseFormatData();
			if(bean != null && ( ( bean.getBranches() != null && bean.getBranches().size() > 0 ) || 
					( bean.getSpecialists() != null && bean.getSpecialists().size() > 0 ) || 
					( bean.getContents() != null && bean.getContents().size() > 0 )) ) {
				respo.setData(bean);
				respo.setCode(ResponseFactory.CODE_SUCCESS);
				respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
			}else{
				respo.setData(new ArrayList<BranchBean>());
				respo.setCode(ResponseFactory.CODE_NO_DATA);
				respo.setMessage(ResponseFactory.MESSAGE_NO_DATA);
			}
			return gson.toJson(respo);
		}else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT,ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
