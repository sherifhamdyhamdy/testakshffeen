package com.sget.akshf.mobile.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.service.CategoryService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb 
 *  Web Service For Get Categories
 */
@Path("/getCategories")
public class GetCategories {

	private Gson gson = new Gson();
	
	// POST Method for Service
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories(@FormParam("cat_id") int cat_id,@FormParam("insurance_company") int insuranceCompany) {
		CategoryService catService = new CategoryService();
		// Get One Category by ID & all Sub Category & All Sections
		if (cat_id > 0) {
			try {
				CategoryBean bean = catService.getById(cat_id);
				if (bean != null && bean.getId() != 0) {
					ResponseFormatData data = new ResponseFormatData();
					data.setCode(ResponseFactory.CODE_SUCCESS);
					data.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					data.setData(bean);
					return gson.toJson(data);
				}else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_CATEGORY_NOTFOUND,ResponseFactory.MESSAGE_CATEGORY_NOTFOUND));

			} catch (Exception ex) {
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX,ResponseFactory.MESSAGE_FAIL_EX));
			}
		}else{
			// Get All Categories & all Sub Category & All Sections
			ResponseCollectFormatData<CategoryBean> ret = new ResponseCollectFormatData<CategoryBean>();
        	List<CategoryBean> categories = catService.getAllForMobile(insuranceCompany);
        	if(categories != null && categories.size() > 0){
            	ret.setData(categories);
            	ret.setCode(ResponseFactory.CODE_SUCCESS);
            	ret.setMessage(ResponseFactory.MESSAGE_SUCCESS);
            	return gson.toJson(ret);
        	}else
        		return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA , ResponseFactory.MESSAGE_NO_DATA));
		}
	}
}