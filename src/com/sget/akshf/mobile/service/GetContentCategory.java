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
import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.ContentForMobileService;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;

/**
 * 
 * @author JDeeb
 * 
 */
@Path("/getContentCategory")
public class GetContentCategory implements AppConstants
{
	private ContentForMobileService service = new ContentForMobileService();
	private Utils utils = Utils.getInstance();

	private Gson gson = new Gson();
	
	@Context
	private HttpServletRequest httpRequestt;
		
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getContentCategory(@FormParam("cat_id") int catID)
	{
		if (catID >= 3 && catID <= 5)
		{
			String url = utils.getXMLkey("AkshffeenAdmin") + "/";

			try
			{
				List<ContentCategoryBean> results = null;
				if (catID == APP_CONTENT_NEWS_ID)
				{
					results = service.getContTypeCategories(DBConstants.CONTENT_TYPE_NEWS, url);
				}
				else if (catID == APP_CONTENT_TIPS_ID)
				{
					results = service.getContTypeCategories(DBConstants.CONTENT_TYPE_TIPS, url);
				}
				else if (catID == APP_CONTENT_OFFERS_ID)
				{
					results = service.getContTypeCategories(DBConstants.CONTENT_TYPE_OFFERS, url);
				}
				if (results != null && results.size() > 0)
				{
					ResponseCollectFormatData<ContentCategoryBean> respon = new ResponseCollectFormatData<ContentCategoryBean>();
					respon.setData(results);
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
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
