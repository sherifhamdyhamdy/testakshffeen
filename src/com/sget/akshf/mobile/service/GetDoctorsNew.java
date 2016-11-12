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
import com.sget.akshef.hibernate.dao.SpecialistDAO;
import com.sget.akshef.hibernate.service.DoctorServiceNew;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.searchcriteria.DoctorSectionBranchCriteria;

@Path("/getDoctors")
public class GetDoctorsNew
{
	private Gson gson = new Gson();
	
	private SpecialistDAO dao = new SpecialistDAO();
	private DoctorServiceNew doctorServiceNew = new DoctorServiceNew();

	/**
	 * 
	 * @param section_id
	 *            required
	 * @param orderByRate
	 * @param isOlineNow
	 * @return
	 */
	@Context
	private HttpServletRequest httpRequest;
	
	private Utils utils = Utils.getInstance();
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getDoctors(
					@FormParam("doctor_name") String doctorName, 
					@FormParam("section_id") int section_id, 
					@FormParam("orderByRate") int orderByRate, 
					@FormParam("start") int start, 
					@FormParam("limit") int limit,
					@FormParam("insurance_company") int insuranceCompany)

	{
		String url = utils.getXMLkey("AkshffeenAdmin");
		// Mandatory Parameters
		if (start >= 0 && limit > 0)
		{
			try
			{
				// Search Criteria Object
				DoctorSectionBranchCriteria search = new DoctorSectionBranchCriteria();
				search.setSection_id(section_id);
				search.setStart(start);
				search.setLimit(limit);
				
				// check optional parameters
				// Doctor Name
				if (doctorName != null && !doctorName.trim().equalsIgnoreCase(""))
					search.setDoctorName(doctorName);
				// order by rate
				if (orderByRate == 1)
					search.setOrderbyrate(true);
				else
					search.setOrderbyrate(false);
								
				search.setInsuranceCompany(insuranceCompany);
	
				List<SpecialistBean> doctors = doctorServiceNew.getSpecialistBySection(search, url);
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
				System.out.println("GetDoctors Ex : " + e.getMessage());
				e.printStackTrace();
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}

}
