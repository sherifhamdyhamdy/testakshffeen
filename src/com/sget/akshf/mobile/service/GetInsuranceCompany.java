package com.sget.akshf.mobile.service;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.InsuranceCompanyBean;
import com.sget.akshef.hibernate.service.InsuranceCompanyService;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;

/**
 * 
 * @author JDeeb getInsuranceCompany Web Service
 */

@Path("/getInsuranceCompany")
public class GetInsuranceCompany implements AppConstants {

	private Gson gson = new Gson();
	private InsuranceCompanyService service = new InsuranceCompanyService();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getInsuranceCompany() {
		// Service Get All Medical Insurance 
		ResponseCollectFormatData<InsuranceCompanyBean> ret = new ResponseCollectFormatData<InsuranceCompanyBean>();
    	List<InsuranceCompanyBean> insuranceCompany = service.getAll();
    	if(insuranceCompany != null && insuranceCompany.size() > 0){
        	ret.setData(insuranceCompany);
        	ret.setCode(ResponseFactory.CODE_SUCCESS);
        	ret.setMessage(ResponseFactory.MESSAGE_SUCCESS);
        	return gson.toJson(ret);
    	}else
    		return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA , ResponseFactory.MESSAGE_NO_DATA));
	}
}
