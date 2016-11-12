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
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

@Path("/searchDoctors")
public class SearchDoctors
{
	
	private Gson gson = new Gson();
	
	private SpecialistService service = new SpecialistService();
	
	@Context
	private HttpServletRequest httpRequest;
	private Utils utils = Utils.getInstance();

	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String searchDoctors(
			@FormParam("doctor_name") String doctorName,
			@FormParam("section_id") int sectionId,
			@FormParam("country_id") int countryId, 
			@FormParam("city_id") int cityId,
			@FormParam("district_id") int districtId, 
			@FormParam("gender") int gender, 
			@FormParam("degree_id") int degree, 
			@FormParam("orderByRate") int orderByRate,
			@FormParam("start") int start, 
			@FormParam("limit") int limit)
	{
		// Mandatory Parameters
		
		StringBuffer baseURL = httpRequest.getRequestURL();
		String path = httpRequest.getServletPath();
		String url = utils.getXMLkey("AkshffeenAdmin");
		if (start >= 0 && limit > 0)
		{
			try
			{
				// Search Criteria Object
				SearchDoctorsCriteria search = new SearchDoctorsCriteria();
				search.setStart(start);
				search.setLimit(limit);
				
				search.setDoctorName(doctorName);
				search.setSectionId(sectionId);
				search.setCountryId(countryId);
				search.setCityId(cityId);
				search.setDistrictId(districtId);
				search.setDegree(degree);
				search.setGender(gender);
				// order by rate
				if (orderByRate == 1)
					search.setOrderbyrate(true);
				else
					search.setOrderbyrate(false);
				
				List<SpecialistBean> doctors = service.searchDoctors(search, url);
				if (doctors != null && doctors.size() > 0)
				{
					ResponseCollectFormatData<SpecialistBean> respo = new ResponseCollectFormatData<SpecialistBean>();
					respo.setData(doctors);
					respo.setCode(ResponseFactory.CODE_SUCCESS);
					respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					return gson.toJson(respo);
				}
				else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA, ResponseFactory.MESSAGE_NO_DATA));
				
			}
			catch (Exception e)
			{
				System.out.println("SearchDoctors Ex : " + e.getMessage());
				e.printStackTrace();
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
