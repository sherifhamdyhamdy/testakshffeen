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
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.BranchServiceNew2;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

@Path("/searchDoctorsNew")
public class SearchDoctorsNew2
{
	
	private Gson gson = new Gson();
	
	private BranchServiceNew2 branchServiceNew2 = new BranchServiceNew2();
	
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
			@FormParam("insuranceCompany_id") int insuranceCompany_id,
			@FormParam("latitude") double latitude,
			@FormParam("longitude") double longitude,
			@FormParam("start") int start, 
			@FormParam("limit") int limit)
	{
		
		if (start >= 0 && limit > 0)
		{
			
			try
			{
				StringBuffer baseURL = httpRequest.getRequestURL();
				String path = httpRequest.getServletPath();
				String url = utils.getXMLkey("AkshffeenAdmin");
				
				// Search Criteria Object
				SearchDoctorsCriteria searchDoctorsCriteria = new SearchDoctorsCriteria();
				searchDoctorsCriteria.setStart(start);
				searchDoctorsCriteria.setLimit(limit);
				
				searchDoctorsCriteria.setDoctorName(doctorName);
				searchDoctorsCriteria.setSectionId(sectionId);
				searchDoctorsCriteria.setCountryId(countryId);
				searchDoctorsCriteria.setCityId(cityId);
				searchDoctorsCriteria.setDistrictId(districtId);
				searchDoctorsCriteria.setDegree(degree);
				searchDoctorsCriteria.setGender(gender);
				searchDoctorsCriteria.setInsuranceCompany_id(insuranceCompany_id);
				
				searchDoctorsCriteria.setLatitude(latitude);
				searchDoctorsCriteria.setLongitude(longitude);
				
				// order by rate
				if (orderByRate == 1)
					searchDoctorsCriteria.setOrderbyrate(true);
				else
					searchDoctorsCriteria.setOrderbyrate(false);
				
				
				List<BranchBean> branches = branchServiceNew2.getBranchesForMobile(searchDoctorsCriteria, url);
				if (branches != null && branches.size() > 0)
				{
					ResponseCollectFormatData<BranchBean> respo = new ResponseCollectFormatData<BranchBean>();
					respo.setData(branches);
					respo.setCode(ResponseFactory.CODE_SUCCESS);
					respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					return gson.toJson(respo);
				}
				else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA, ResponseFactory.MESSAGE_NO_DATA));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
