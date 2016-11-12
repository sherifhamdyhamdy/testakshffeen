package com.sget.akshf.mobile.service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.TempBranchBean;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshef.hibernate.service.CategoryService;
import com.sget.akshef.hibernate.service.TempBranchService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.SuccessAPI;

@Path("/addTempBranch")
public class AddTempBranch {

	private Gson gson = new Gson();
	private TempBranchService tempBranchService=new TempBranchService();
	private CategoryService service = new CategoryService();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addTempBranch(
			@FormParam("name") String name,
			@FormParam("address") String address,
			@FormParam("tel_num") String tel_num, 
			@FormParam("latitude") double lat, 
			@FormParam("longitude") double lng , 
			@FormParam("cat_id") int cat_id,
			@FormParam("email") String email,
			
			@FormParam("user_type") int user_type,				// 1 guest   , 2 unit owner
			
			@FormParam("user_id") int user_id,
			@FormParam("guest_name") String guest_name,
			@FormParam("user_tel") String user_tel		
			
			
			)
	{
		System.out.println("AddTempBranch.addTempBranch()");
		
		if (
			name != null && !name.trim().equalsIgnoreCase("") && address != null && 
			!address.trim().equalsIgnoreCase("") && tel_num != null && 
			!tel_num.trim().equalsIgnoreCase("") && lat != 0 && lng != 0 && 
			cat_id > 0 && user_type != 0)
		{
			
			CategoryBean catBean = service.getById(cat_id);
			if (catBean == null || catBean.getId() > 0)
			{
				TempBranchBean tempBranch = new TempBranchBean();
				tempBranch.setAddress(address);
				tempBranch.setName(name);
				tempBranch.setLat(lat);
				tempBranch.setLng(lng);
				tempBranch.setTel_num(tel_num);
				tempBranch.setCatBean(catBean);
				tempBranch.setEmail(email);
				tempBranch.setUser_type(user_type);
				tempBranch.setUser_tel(user_tel);
				
				UsersEntity user_recommend = null;
				if(user_type==1)
				{
					if(user_id==0)
					{
						user_recommend = new UsersEntity();
						user_recommend.setNameAr(guest_name);
						user_recommend.setNameEn(guest_name);
						user_recommend.setEmail(email);
						user_recommend.setTelephone(user_tel);
						user_recommend.setActive(1);
					}
					else
					{
						tempBranch.setUser_recommend(user_id);
					}
				}
				
				
				boolean result = tempBranchService.insert(tempBranch, user_recommend);
				if (result)
					return gson.toJson(new SuccessAPI(ResponseFactory.CODE_SUCCESS, ResponseFactory.MESSAGE_SUCCESS));
				else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL, ResponseFactory.MESSAGE_FAIL));
			}
			else
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_CATEGORY_NOTFOUND, ResponseFactory.MESSAGE_CATEGORY_NOTFOUND));
			
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
		
	}

}
