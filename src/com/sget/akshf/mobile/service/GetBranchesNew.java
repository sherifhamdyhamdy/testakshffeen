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
import com.sget.akshef.hibernate.service.BranchServiceNew;
import com.sget.akshef.hibernate.service.Services;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.searchcriteria.BranchesCriteria;

/**
 * 
 * @author abdelrhim
 * 
 */
@Path("/getBranches")
public class GetBranchesNew
{
	private Gson gson = new Gson();
	
	private BranchServiceNew branchServiceNew = new BranchServiceNew();
	
	@Context
	private HttpServletRequest httpRequest;
	
	private Utils utils = Utils.getInstance();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getBranches(
			@FormParam("longitude") double longitude,
			@FormParam("latitude") double latitude, 
			@FormParam("start") int start, 
			@FormParam("limit") int limit, 
			@FormParam("search_text") String search_text,
			@FormParam("section_id") int section_id, 
			@FormParam("cat_id") int cat_id, 
			@FormParam("rating") int rating, 
			@FormParam("insurance_company") int insuranceCompany)
	{
		
		if (longitude != 0.0 && latitude != 0.0 && start >= 0 && limit > 0)
		{
			try
			{
				String url = utils.getXMLkey("AkshffeenAdmin");

				BranchesCriteria branchCriteria = new BranchesCriteria();
				
				// Mandatory Parameters
				branchCriteria.setLatitude(latitude);
				branchCriteria.setLongitude(longitude);
				branchCriteria.setStart(start);
				branchCriteria.setLimit(limit);
				
				// Optional Parameters
				if (cat_id != 0)
					branchCriteria.setCat_id(cat_id);
				if (section_id != 0)
					branchCriteria.setSection_id(section_id);
				if (search_text != null && !search_text.trim().equalsIgnoreCase(""))
					branchCriteria.setSearch_text(search_text);
				
				// New For InsuranceCompany
				if (insuranceCompany != 0)
					branchCriteria.setInsuranceCompany(insuranceCompany);
				if (rating != 0)
					branchCriteria.setRating(true);
				else
					branchCriteria.setRating(false);
				
				
				List<BranchBean> branches = branchServiceNew.getBranchesForMobile(branchCriteria, url);
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
