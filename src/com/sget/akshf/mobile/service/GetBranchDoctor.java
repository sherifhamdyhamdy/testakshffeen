package com.sget.akshf.mobile.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.searchcriteria.DoctorSectionBranchCriteria;

@Path("/getBranchDoctor")
public class GetBranchDoctor {

	private Gson gson = new Gson();
	private SpecialistService service = new SpecialistService();
	/**
	 * 
	 * @param branchId 		required
	 * @param section_id 	required
	 * @param orderByRate
	 * @param isOlineNow
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getBranchDoctore(@FormParam("branchId") int branchId, @FormParam("section_id") int section_id,
			@FormParam("orderByRate") int orderByRate, @FormParam("isOlineNow") int isOlineNow) {
		// Mandatory Parameters
		if (branchId > 0 && section_id > 0) {
			try {
				// Search Criteria Object
				DoctorSectionBranchCriteria search = new DoctorSectionBranchCriteria();
				search.setBranch_id(branchId);
				search.setSection_id(section_id);

				// check optional parameters order by rate
				if (orderByRate == 1)
					search.setOrderbyrate(true);
				else
					search.setOrderbyrate(false);
				// Check is Online
				if (isOlineNow == 1)
					search.setOnlinenow(true);
				else
					search.setOnlinenow(false);

				List<SpecialistBean> doctors = service.getSpecialistBySectionAndBranch(search);
				if (doctors != null && doctors.size() > 0) {
					ResponseCollectFormatData<SpecialistBean> respo = new ResponseCollectFormatData<SpecialistBean>();
					respo.setData(doctors);
					respo.setCode(ResponseFactory.CODE_SUCCESS);
					respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					return gson.toJson(respo);
				} else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA,ResponseFactory.MESSAGE_NO_DATA));

			} catch (Exception e) {
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX,ResponseFactory.MESSAGE_FAIL_EX));
			}
		} else 
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT,ResponseFactory.MESSAGE_INVALID_INPUT));
	}
}
