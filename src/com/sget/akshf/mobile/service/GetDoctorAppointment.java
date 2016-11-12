package com.sget.akshf.mobile.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.ScheduleDays;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.searchcriteria.DoctorAppointmentCriteria;

/**
 * 
 * @author JDeeb Web Service For Doctor Appointment
 */
@Path("/getDoctorAppointment")
public class GetDoctorAppointment {

	private Gson gson = new Gson();
	private SpecialistService service = new SpecialistService();
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getDoctorAppointment(@FormParam("branchId") int branchId,
			@FormParam("doctorId") int doctorId, @FormParam("section_id") int section_id, @FormParam("subcat_id") int subcat_id ) {
		// Mandatory Parameters
		if (branchId > 0 && doctorId > 0) {
			try {
				// Search Criteria Object
				DoctorAppointmentCriteria search = new DoctorAppointmentCriteria();
				search.setBranch_id(branchId);
				search.setDoctor_id(doctorId);
				
				List<ScheduleDays> scheduleDays = service.getSpecialistSchedule(search,subcat_id,section_id);
				// get Doctor price by SubCategory and section
				if (scheduleDays != null && scheduleDays.size() > 0) {
					ResponseCollectFormatData<ScheduleDays> respo = new ResponseCollectFormatData<ScheduleDays>();
					respo.setData(scheduleDays);
					respo.setCode(ResponseFactory.CODE_SUCCESS);
					respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					return gson.toJson(respo);
				} else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_APPOINTMENT,ResponseFactory.MESSAGE_NO_APPOINTMENT));

			} catch (Exception e) {
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX,ResponseFactory.MESSAGE_FAIL_EX));
			}
		}else 
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT,ResponseFactory.MESSAGE_INVALID_INPUT));
		
	}
}
