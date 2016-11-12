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
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;

/**
 * 
 * @author JDeeb getDoctorPlaces Web Service
 * 
 */
@Path("/getDoctorPlaces")
public class GetDoctorPlaces
{
	private Utils utils = Utils.getInstance();

	private Gson gson = new Gson();
	
	private SpecialistService service = new SpecialistService();
	
	@Context
	private HttpServletRequest httpRequest;
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getDoctorPlaces(@FormParam("doctorId") int doctorId, @FormParam("dayId") int dayId)
	{
		// Mandatory Parameters
		if (doctorId != 0)
		{
			StringBuffer baseURL = httpRequest.getRequestURL();
			String path = httpRequest.getServletPath();
			String url = utils.getXMLkey("AkshffeenAdmin");
			
			List<BranchBean> beans = service.getSpecialistPlaces(doctorId, dayId, url);
			ResponseCollectFormatData<BranchBean> respo = new ResponseCollectFormatData<BranchBean>();
			if (beans != null && beans.size() > 0)
			{
				respo.setData(beans);
				respo.setCode(ResponseFactory.CODE_SUCCESS);
				respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
			}
			else
			{
				respo.setData(new ArrayList<BranchBean>());
				respo.setCode(ResponseFactory.CODE_NO_DATA);
				respo.setMessage(ResponseFactory.MESSAGE_NO_DATA);
			}
			return gson.toJson(respo);
			
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
