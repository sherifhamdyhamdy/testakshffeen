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
import com.sget.akshef.hibernate.service.AdvancedSearchServiceNew;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;

/**
 * 
 * @author JDeeb no Mandatory fields
 */

@Path("/search")
public class AdvancedSearchNew
{
	private Gson gson = new Gson();
	private Utils utils = Utils.getInstance();

	private AdvancedSearchServiceNew advancedSearchServiceNew = new AdvancedSearchServiceNew();
	
	@Context
	private HttpServletRequest httpRequest;
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getAdvancedSearch(
									@FormParam("longitude") double longitude, 
									@FormParam("latitude") double latitude, 
									@FormParam("cat_id") int cat_id, 
									@FormParam("subcat_id") int subcat_id, 
									@FormParam("section_id") int section_id, 
									@FormParam("country_id") int country_id, 
									@FormParam("city_id") int city_id, 
									@FormParam("district_id") int district_id, 
									@FormParam("fromday_id") int fromday_id, 
									@FormParam("today_id") int today_id, 
									@FormParam("fromhour_id") int fromhour_id,
									@FormParam("tohour_id") int tohour_id, 
									@FormParam("name") String name,
									@FormParam("degree_id") int degree_id, 
									@FormParam("gender") int gender,
									@FormParam("insuranceCompany_id") int insuranceCompany_id,
									@FormParam("start") int start,
									@FormParam("limit") int limit)
	{
		
		try
		{
			StringBuffer baseURL = httpRequest.getRequestURL();
			String path = httpRequest.getServletPath();
			String url = utils.getXMLkey("AkshffeenAdmin");
			
			// Set Search Criteria in Bean
			AdvancedSearchCriteria criteria = new AdvancedSearchCriteria();
			
			criteria.setCat_id(cat_id);
			criteria.setSubcat_id(subcat_id);
			criteria.setSection_id(section_id);
			
			criteria.setCountry_id(country_id);
			criteria.setCity_id(city_id);
			criteria.setDistrict_id(district_id);
			
			criteria.setLongitude(longitude);
			criteria.setLatitude(latitude);
			
			criteria.setFromday_id(fromday_id);
			criteria.setToday_id(today_id);
			criteria.setFromhour_id(fromhour_id);
			criteria.setTohour_id(tohour_id);
			
			criteria.setName(name);
			criteria.setDegree_id(degree_id);
			criteria.setGender(gender);
			
			criteria.setInsuranceCompany_id(insuranceCompany_id);
			
			criteria.setStart(start);
			criteria.setLimit(limit);
			
			
			List<BranchBean> branches = advancedSearchServiceNew.advancedSearchForBranches(criteria, url);
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
}
