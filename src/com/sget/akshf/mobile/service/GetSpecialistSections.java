package com.sget.akshf.mobile.service;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.service.SectionsService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;

/**
 * 
 * @author JDeeb
 * 
 */
@Path("/getSpecialistSections")
public class GetSpecialistSections
{
	private Gson gson = new Gson();
	
	private SectionsService service = new SectionsService();
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getSpecialistSections()
	{
		try
		{
			List<SectionsBean> sections = service.getSpecialistSections();
			if (sections != null && sections.size() > 0)
			{
				ResponseCollectFormatData<SectionsBean> respo = new ResponseCollectFormatData<SectionsBean>();
				respo.setData(sections);
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
