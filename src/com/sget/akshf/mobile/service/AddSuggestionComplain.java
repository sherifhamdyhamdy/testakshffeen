package com.sget.akshf.mobile.service;

import java.net.URLEncoder;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.akshffeen.utils.Constants;
import com.akshffeen.utils.Image;
import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.sget.akshef.hibernate.beans.GuestSuggestComplainBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.GuestSuggestComplainService;
import com.sget.akshf.mobile.api.ErrorAPI;
import com.sget.akshf.mobile.api.ResponseFactory;
import com.sget.akshf.mobile.api.SuccessAPI;

@Path("/addSuggestionComplaint")
public class AddSuggestionComplain
{
	private Utils utils = Utils.getInstance();
	private Gson gson = new Gson();
	
	private GuestSuggestComplainService service = new GuestSuggestComplainService();
	
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addSuggestionComplain(
			@FormParam("type") int type, 
			@FormParam("name") String name, 
			@FormParam("job") String job, 
			@FormParam("email") String email, 
			@FormParam("title") String title, 
			@FormParam("details") String details,
			@FormParam("image") String image,
			@FormParam("extension") String extension)
	{
		System.out.println("AddSuggestionComplain.addSuggestionComplain()");
		
		if ((type == DBConstants.MESSAGES_TYPES_SUGGEST || type == DBConstants.MESSAGES_TYPES_COMPLAIN) && name != null && !name.trim().equalsIgnoreCase(""))
		{
			GuestSuggestComplainBean bean = new GuestSuggestComplainBean();
			bean.setType(type);
			bean.setName(name);
			bean.setJob(job);
			bean.setEmail(email);
			bean.setTitle(title);
			bean.setDetails(details);
			try
			{
				System.out.println("AddSuggestionComplain.addSuggestionComplain()");
				
				int suggestionId = service.insert(bean);

				if(utils.hasValue(image))
				{
//					image = new Image().uploadImage(image, extension, null,"images/suggestion_complaint");
//					
//					if(utils.hasValue(image))
//						bean.setImage(image);
										
					String query = "";
					String charset = Constants.charset;

					try
					{			
						query = String.format("suggestionId=%s&image=%s&extension=%s&method=%s&folder=%s", 
						     	URLEncoder.encode(suggestionId+"", charset),
						     	URLEncoder.encode(image, charset),
						     	URLEncoder.encode(extension, charset),
						     	URLEncoder.encode("upateSuggestionImage", charset),
				     			URLEncoder.encode("suggestion_complaint", charset));

					}
					catch (Exception e) {
						// TODO: handle exception
					}
									
//					String action = Constants.success;
					String action = utils.sendImage(query);
				}
								
				if (bean.getId() > 0)
					return gson.toJson(new SuccessAPI(ResponseFactory.CODE_SUCCESS, ResponseFactory.MESSAGE_SUCCESS));
				else
					return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL, ResponseFactory.MESSAGE_FAIL));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				return gson.toJson(new ErrorAPI(ResponseFactory.CODE_FAIL_EX, ResponseFactory.MESSAGE_FAIL_EX));
			}
		}
		else
			return gson.toJson(new ErrorAPI(ResponseFactory.CODE_INVALID_INPUT, ResponseFactory.MESSAGE_INVALID_INPUT));
	}
	
}
