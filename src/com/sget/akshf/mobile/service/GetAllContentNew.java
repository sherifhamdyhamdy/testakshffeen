package com.sget.akshf.mobile.service;

import java.util.ArrayList;
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
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.ContentForMobileServiceNew;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;

@Path("/getAllContent")
public class GetAllContentNew
{
	
	private ContentForMobileServiceNew contentForMobileServiceNew = new ContentForMobileServiceNew();
	private Utils utils = Utils.getInstance();

	private Gson gson = new Gson();
	
	@Context
	private HttpServletRequest httpRequestt;
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllContent(
								@FormParam("cat_id") int cat_id, 
								@FormParam("cat_type") int cat_type, 
								@FormParam("searchName") String searchName, 
								@FormParam("rating") int rating, 
								@FormParam("start") int start, 
								@FormParam("limit") int limit)
	{
//		if (cat_id > 0)
		{
			String url = utils.getXMLkey("AkshffeenAdmin");
			
			List<ContentDetailsBean> contents = new ArrayList<ContentDetailsBean>();
			try
			{
				contents = contentForMobileServiceNew.getContentDetails(cat_id, searchName, rating > 0 ? true : false, url, start, limit, cat_type);
				
				if (contents != null && contents.size() > 0)
				{
					ResponseCollectFormatData<ContentDetailsBean> respon = new ResponseCollectFormatData<ContentDetailsBean>();
					respon.setData(contents);
					respon.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					respon.setCode(ResponseFactory.CODE_SUCCESS);
					
					return gson.toJson(respon);
				}
				else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA, ResponseFactory.MESSAGE_NO_DATA));
			}
			catch (Exception e)
			{
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		}
//		else
//			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
