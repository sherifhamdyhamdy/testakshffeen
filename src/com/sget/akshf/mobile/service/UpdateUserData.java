package com.sget.akshf.mobile.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.SpecialistDAO;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.ResponseFormatData;

/**
 * 
 * @author JDeeb
 * Register Web Service
 */
@Path("/updateUser")
public class UpdateUserData {

	private Gson gson = new Gson();
	
	Utils utils = Utils.getInstance();
	
	private UsersService service = new UsersService();
	SpecialistDAO specialistDAO = new SpecialistDAO();
	
	@Context
	private HttpServletRequest httpRequest ;
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String UpdateUserData(
			@FormParam("user_id") int user_id ,
			@FormParam("firstname") String firstname , 
			@FormParam("lastname") String lastname , 
			@FormParam("username") String username , 
			@FormParam("pass") String pass , 
			@FormParam("email") String email,
			@FormParam("gender") int gender ,
			@FormParam("birthdate")long birthdate, 
			@FormParam("nationalId")String nationalId, 
			@FormParam("district_id")int district_id,
			@FormParam("telephone")String telephone, 
			@FormParam("mobile")String mobile, 
			@FormParam("emergency_no")String emergency_no,
			@FormParam("marital_stat")String marital_stat, 
			@FormParam("address")String address
			
			)
	
	{
		System.out.println("UpdateUserData.UpdateUserData()");
		
		StringBuffer baseURL = httpRequest.getRequestURL();
		String path=httpRequest.getServletPath();
	    String 	url=utils.getXMLkey("AkshffeenAdmin");
		if(username != null && !username.trim().equalsIgnoreCase("") && pass != null && 
				!pass.trim().equalsIgnoreCase("") && email != null && !email.trim().equalsIgnoreCase("")&& gender!=0 && user_id!=0){
			try{
				
				boolean mail_exist = service.checkUserInRegisterByEmail(email,user_id);
				boolean name_exist = service.checkUserInRegisterByUsername(username,user_id);
				if(!mail_exist && !name_exist){
					UsersBean user = new UsersBean();
					user.setFirstName(firstname);
					user.setLastName(lastname);
					user.setUsername(username);
					user.setUsername(username);
					user.setPassword(pass);
					user.setEmail(email);
					user.setGender(gender);
					user.setId(user_id);
					user.setBirthdate(new Date(birthdate));

					user.setAddress(address);
					user.setTelephone(telephone);
					user.setMobile(mobile);
					user.setEmergency_contact(emergency_no);
					user.setMarital_stat(marital_stat);
					user.setNationalId(nationalId);
					user.setDistrict_id(district_id);

					boolean isupdated=service.update(user);
					if(isupdated)
					{
						user=service.getById(user.getId());
						user.setProfile_img(url+user.getProfile_img());
						
						user.setUser_type(utils.hasValue(specialistDAO.getDoctorById(user_id))?2:1);			//1 - user ,   2 - doctor
					}
					
					
					
					ResponseFormatData respo = new ResponseFormatData();
					if(user.getId() > 0){
						respo.setData(user);
						respo.setCode(ResponseFactory.CODE_SUCCESS);
						respo.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					}else{
						respo.setData(user);
						respo.setCode(ResponseFactory.CODE_FAIL);
						respo.setMessage(ResponseFactory.MESSAGE_FAIL);
					}
					return gson.toJson(respo);
				}else if(mail_exist && name_exist)
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_USERNAME_EMAIL_EXIST,ResponseFactory.MESSAGE_USERNAME_EMAIL_EXIST));
				else if(mail_exist)
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_EMAIL_EXIST,ResponseFactory.MESSAGE_EMAIL_EXIST));
				else if(name_exist)
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_USERNAME_EXIST,ResponseFactory.MESSAGE_USERNAME_EXIST));
				else
					return "";
			}catch (Exception e) {
				 System.out.println("Ex in Resgister New User : " + e.getMessage());
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX,ResponseFactory.MESSAGE_FAIL_EX));
			}
		}else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT,ResponseFactory.MESSAGE_INVALID_INPUT));
		
	}
		
	
				
				
	
}
