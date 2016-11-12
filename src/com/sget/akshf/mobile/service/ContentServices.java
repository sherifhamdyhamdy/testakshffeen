package com.sget.akshf.mobile.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;

import com.akshffeen.mapper.Mapping;
import com.akshffeen.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sget.akshef.beans.ContentContainer;
import com.sget.akshef.hibernate.beans.Akshffeen;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.ContentDetailsDAO;
import com.sget.akshef.hibernate.dao.SpecialistDAO;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.utils.Performance;
import com.sget.akshf.mobile.api.ResponseFactory;


@Path("/content")
public class ContentServices
{
	private final ContentDetailsDAO contentDetailsDAO = new ContentDetailsDAO();
	private final Utils utils = Utils.getInstance();
	private final SpecialistDAO specialistDAO = new SpecialistDAO();

	@POST
	@Produces("application/json")		//Encoding json formate
	@Path("/listContents")
	public String listContents(
									@FormParam("pageNum") int pageNum,
									@FormParam("rowCount") int rowCount,
									@FormParam("sponsor_type") int sponsor_type,
									@FormParam("unitId") Integer unitId,
									@FormParam("userId") Integer userId) 
	{	
		int start = ((pageNum-1)*rowCount);

		List<ContentDetailsEntity> contents = new ArrayList<ContentDetailsEntity>();
		if(sponsor_type==1)
		{
			contents = contentDetailsDAO.listAdminContents(start, rowCount);
		}
		else if(sponsor_type==2)
		{
			contents = contentDetailsDAO.listUnitContents(unitId, start, rowCount);
		}
		else if(sponsor_type==3)
		{
			contents = contentDetailsDAO.listUserContents(userId, start, rowCount);
		}	
			
		ContentContainer contentContainer = new ContentContainer();
		
		List<ContentDetailsBean> contentBeanList = new ArrayList<ContentDetailsBean>();
		ContentDetailsBean contentBean = null;
		Akshffeen akshffeen = null;
		UsersBean usersBean = null;
		SpecialistEntity doctor = null;

		String url = utils.getXMLkey("AkshffeenAdmin");

		if(utils.hasValue(contents))
		{
			for(ContentDetailsEntity content : contents)
			{			
				contentBean = Mapping.mapContentDetailsEntity(content);
				
				
				if(sponsor_type==1)
				{
					akshffeen = new Akshffeen(); 
					
					akshffeen.setName("Akshffeen");
					akshffeen.setLogo(url + DBConstants.CONTENT_IMAGES_UPLOADS + "logo.png");
					
					contentBean.setAkshffeen(akshffeen);
					contentBean.setUnit(null);
					contentBean.setUsers(null);
				}
	
				if(sponsor_type==3)
				{
					try
					{
						if(content.getUsers()!=null && content.getUsers().getId()!=null)
						{
							doctor = specialistDAO.getSpecialistByUserID(content.getUsers().getId());
							
							usersBean = new UsersBean();
							usersBean.setId(content.getUsers().getId());
							usersBean.setNameAr(content.getUsers().getNameAr());
							usersBean.setNameEn(content.getUsers().getNameEn());
		
							
							if(doctor.getImage()!=null)
								usersBean.setLogo(url + DBConstants.USERS_IMAGES_UPLOADS + doctor.getImage()+"");					
							else
								usersBean.setLogo("");					
		
							contentBean.setUsers(usersBean);	
							contentBean.setUnit(null);
							contentBean.setAkshffeen(null);
						}
					}
					catch(Exception e)
					{
						System.out.println("Exception content unit "+e.getMessage());
					}
				}
				
				contentBean.setUserFavoritieses(null);
				contentBean.setCommentses(null);
				
				if(utils.hasValue(content.getUnit()))
				{
					contentBean.getUnit().setBranches(null);
					contentBean.getUnit().setUnitGroupses(null);
					contentBean.getUnit().setUnitHasInsuranceCompanies(null);				
				}
				
				contentBeanList.add(contentBean);
			}
			
			contentContainer.setMessage(ResponseFactory.MESSAGE_SUCCESS);
			contentContainer.setCode(ResponseFactory.CODE_SUCCESS);
		}
		else
		{

			contentContainer.setMessage(ResponseFactory.MESSAGE_NO_DATA);
			contentContainer.setCode(ResponseFactory.CODE_NO_DATA);
		}
			
		contentContainer.setData(contentBeanList);
		
		Gson gson = new Gson();
		
		StringBuilder news_gson = new StringBuilder(gson.toJson(contentContainer));
//		news_gson = Utils.convertToEntities(news_gson);
		contentContainer = gson.fromJson(news_gson.toString(), ContentContainer.class);
		
		Performance.releaseMemory();

		return news_gson.toString();
	}
		

}