package com.sget.akshf.mobile.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseCollectFormatData;
import com.sget.akshf.mobile.api.ResponseFactory;

/**
 * 
 * @author JDeeb
 * Login Web Service
 */
@Path("/getCommunications")
public class GetCommunications {

	private Gson gson = new Gson();
	private UsersService service = new UsersService();

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String loginUser(@FormParam("userId") int userId ){
		
		if( userId > 0 ){
			try{
				List<UsersBean> users = service.getCommunications(userId);
				ResponseCollectFormatData<UsersBean> respon = new ResponseCollectFormatData<UsersBean>();
				
				if(users != null && users.size() > 0){
					respon.setData(users);
					respon.setCode(ResponseFactory.CODE_SUCCESS);
					respon.setMessage(ResponseFactory.MESSAGE_SUCCESS);
					return gson.toJson(respon);
				}else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_NO_DATA,ResponseFactory.MESSAGE_NO_DATA));
			}catch (Exception e) {
				 System.out.println("Ex in Login : " + e.getMessage());
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX,ResponseFactory.MESSAGE_FAIL_EX));
			}
		}else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT,ResponseFactory.MESSAGE_INVALID_INPUT));
		
	}
}
