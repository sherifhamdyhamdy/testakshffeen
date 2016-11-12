package com.sget.akshf.mobile.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;

import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sget.akshef.beans.SchedulesBean;
import com.sget.akshef.hibernate.beans.UserLoginBean;
import com.sget.akshef.hibernate.dao.Doctor_scheduleDAO;
import com.sget.akshef.hibernate.dao.SpecialistDAO;
import com.sget.akshef.hibernate.dao.SubcategoryHasSectionsHasBranchDAO;
import com.sget.akshef.hibernate.entities.Doctor_scheduleEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SectionsHasSpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsHasBranchEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.utils.Performance;
import com.sget.akshf.mobile.api.ResponseFactory;


@Path("/doctor")
public class DoctorServices
{
	private final Doctor_scheduleDAO doctor_scheduleDAO = new Doctor_scheduleDAO();
	private final SpecialistDAO doctorDAO = new SpecialistDAO();
	private final Utils utils = Utils.getInstance();
	private final SubcategoryHasSectionsHasBranchDAO subcategoryHasSectionsHasBranchDAO = new SubcategoryHasSectionsHasBranchDAO();

	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/getDoctorBranchSchedules")
	public String getDoctorBranchSchedules(@FormParam("doctorId") Integer doctorId,@FormParam("branchId") int branchId) 
	{		
		List<Doctor_scheduleEntity> doctor_scheduleList = doctor_scheduleDAO.getDoctorBranchSchedules(doctorId, branchId);
		
		Map<String, TreeSet<Integer>> schedules_map = new HashMap<String, TreeSet<Integer>>();
		
		String schedule = "";
		for(Doctor_scheduleEntity doctor_schedule : doctor_scheduleList)
		{
			schedule = doctor_schedule.getFrom_hour() + "-" + doctor_schedule.getTo_hour();
			
			if(schedules_map.containsKey(schedule))
				schedules_map.get(schedule).add(doctor_schedule.getDay_id());
			else
			{
				TreeSet<Integer> days = new TreeSet<Integer>();
				days.add(doctor_schedule.getDay_id());
				schedules_map.put(schedule, days);
			}
		}
			
		SchedulesBean schedulesBean = utils.getSchedules(schedules_map);
		
		Gson gson = new Gson();
		
		StringBuilder news_gson = new StringBuilder(gson.toJson(schedulesBean));
		schedulesBean = gson.fromJson(news_gson.toString(), SchedulesBean.class);
		
		Performance.releaseMemory();

		return news_gson.toString();
	}
		

	

	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/getDoctor/{doctorId}")
	public String getDoctor(@FormParam("doctorId") Integer doctorId) 
	{		
		SpecialistEntity doctor = doctor_scheduleDAO.getDoctor(doctorId);
		
		if(utils.hasValue(doctor))
		{
			doctor.getUsers().setSpecialistSet(null);
			doctor.getUsers().setMessagesSet(null);
			doctor.getUsers().setMessagesSet1(null);
			doctor.getUsers().setApprovalMsgSet(null);
			doctor.getUsers().setMedicalhistorySet(null);
			doctor.getUsers().setContentDetailsSet(null);
//			doctor.getUsers().setProfissionaldataSet(null);
			doctor.getUsers().setUserFavoritiesSet(null);
			doctor.getUsers().setUserRateSpecSet(null);
			doctor.getUsers().setUserRateBranchSet(null);
			doctor.getUsers().setUsersGroupsSet(null);
			doctor.getUsers().setBranchSet(null);
			doctor.getUsers().setUnitSet(null);
			doctor.getUsers().setCertificationSet(null);
			doctor.getUsers().setProfessionalSet(null);
			doctor.getUsers().setDistric(null);
			doctor.getUsers().setCommentsSet(null);
			
			
			doctor.setUserFavoritiesSet(null);
			doctor.setSpecialistHasBranchSet(null);
			doctor.setUserRateSpecSet(null);
			doctor.setSectionsHasSpecialistSet(null);
			doctor.setCommentsSet(null);
			doctor.setDoctor_scheduleList(null);
		}
			
		Gson gson = new Gson();
		
		StringBuilder news_gson = new StringBuilder(gson.toJson(doctor));
//		news_gson = Utils.convertToEntities(news_gson);
		doctor = gson.fromJson(news_gson.toString(), SpecialistEntity.class);
//		doctor = gson.fromJson(news_gson.toString(), new TypeToken<List<?>>(){}.getType());
		
		Performance.releaseMemory();

		return news_gson.toString();
	}

	
	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/addTempDoctor")
	public String addTempDoctor(
			@FormParam("user_type") int user_type,				// 1 guest   , 2 doctor
			
			@FormParam("user_id") int user_id,
			@FormParam("guest_name") String guest_name,
			@FormParam("user_tel") String user_tel,			
			@FormParam("user_email") String user_email,

			@FormParam("doctor_name") String doctor_name,
			@FormParam("gender") int gender,
			@FormParam("address") String address,
			@FormParam("doctor_tel") String doctor_tel,
			@FormParam("speciality_ids") String speciality_ids
			) 
	{
		SpecialistEntity doctor = new SpecialistEntity();
		UsersEntity user = new UsersEntity();
		doctor.setUsers(user);
		
		doctor.setName(doctor_name);
		user.setNameAr(doctor_name);
		user.setNameEn(doctor_name);
		user.setFirstName(doctor_name);
		user.setLastName(doctor_name);
		user.setGender(gender);
		user.setAddress(address);
		user.setTelephone(doctor_tel);
		
		List<Integer> sections = utils.generateListInteger(speciality_ids);
		
		List<SectionsHasSpecialistEntity> sectionsHasSpecialistList = new ArrayList<SectionsHasSpecialistEntity>();
		for(Integer section : sections)
		{
			SectionsHasSpecialistEntity sectionsHasSpecialistEntity = new SectionsHasSpecialistEntity();
			sectionsHasSpecialistEntity.setSpecialist(doctor);
			sectionsHasSpecialistEntity.setSections(new SectionsEntity(section));
			
			sectionsHasSpecialistList.add(sectionsHasSpecialistEntity);
		}
		
		doctor.setSectionsHasSpecialistSet(sectionsHasSpecialistList);
		
		
		UsersEntity user_recommend = null;
		if(user_type==1)
		{
			if(user_id==0)
			{
				user_recommend = new UsersEntity();
				user_recommend.setNameAr(guest_name);
				user_recommend.setNameEn(guest_name);
				user_recommend.setEmail(user_email);
				user_recommend.setTelephone(user_tel);
				user_recommend.setActive(1);
			}
			else
			{
				doctor.setUser_recommend(user_id);
			}
		}
					
		boolean action = doctorDAO.saveDoctor(doctor, user_recommend);

		UserLoginBean userLoginBean = new UserLoginBean();

		if(action)
		{
			userLoginBean.setCode(ResponseFactory.CODE_SUCCESS);
			userLoginBean.setMessage(ResponseFactory.MESSAGE_SUCCESS);
		}
		else
		{
			userLoginBean.setCode(ResponseFactory.CODE_FAIL);
			userLoginBean.setMessage(ResponseFactory.MESSAGE_FAIL);
		}
		
		Performance.releaseMemory();
		
		Gson gson = new Gson();
		
		String output = gson.toJson(userLoginBean);
		
		Performance.releaseMemory();

		return output;			
	}


	
	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/getDoctorsPrices")
	public String getDoctorsPrices(@FormParam("branchId") Integer branchId,@FormParam("doctorId") int doctorId) 
	{
		List<SubcategoryHasSectionsHasBranchEntity> sections = subcategoryHasSectionsHasBranchDAO.getDoctorsPrices(branchId, doctorId);

		if(utils.hasValue(sections))
		{
			for(SubcategoryHasSectionsHasBranchEntity section : sections)
			{
				section.setSection_ar(section.getSubcategoryHasSections().getSections().getNameAr());
				section.setSection_en(section.getSubcategoryHasSections().getSections().getNameEn());
				section.setSection_id(section.getSubcategoryHasSections().getSections().getId());
				section.setSubcategoryHasSections(null);		
				section.setBranch(null);		
				section.setId(null);		
			}
		}
			
		Gson gson = new Gson();
		
		StringBuilder news_gson = new StringBuilder(gson.toJson(sections));
//		news_gson = Utils.convertToEntities(news_gson);
		sections = gson.fromJson(news_gson.toString(), new TypeToken<List<?>>(){}.getType());
		
		Performance.releaseMemory();

		return news_gson.toString();
	}

}