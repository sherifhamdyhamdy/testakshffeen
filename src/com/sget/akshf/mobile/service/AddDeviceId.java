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
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb addAndroidDeviceId Web Service
 */

@Path("/addDeviceId")
public class AddDeviceId implements AppConstants
{
	
	private Gson gson = new Gson();
	
	private MobileService mobileService = new MobileService();
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addDeviceId(
								@FormParam("registerationId") String registerationId,
								@FormParam("platform") int platform)
	{
		// Service Add User Comment
		// Mandatory parameters
		if (registerationId != null && !registerationId.equals(""))
		{
			int result = mobileService.addAndroidDeviceID(registerationId, platform);
			if (result == 1)
			{
				ResponseFormatData respon = new ResponseFormatData();
				respon.setCode(ResponseFactory.CODE_SUCCESS);
				respon.setMessage(ResponseFactory.MESSAGE_SUCCESS);
				return gson.toJson(respon);
			}
			else if (result == 2)
			{
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL, ResponseFactory.MESSAGE_FAIL));
			}
			else if (result == 0)
			{
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
			else
				return "";
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
