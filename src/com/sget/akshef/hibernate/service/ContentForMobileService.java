package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.akshffeen.mapper.Mapping;
import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.ContentForMobileDAO;
import com.sget.akshef.hibernate.entities.ContentCategoryEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.PriorityEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * 
 */

public class ContentForMobileService
{
	private ContentForMobileDAO dao = new ContentForMobileDAO();
	
	private ContentDetailsService contService = new ContentDetailsService();
	
	private Utils utils = Utils.getInstance();
	
	
	/*
	 * // get Content News Categories public List<ContentCategoryBean>
	 * getNewsCategory(){ List<ContentCategoryBean> beans = new
	 * ArrayList<ContentCategoryBean>(); List<ContentCategoryEntity> entities =
	 * dao.getNewsCategory(); if(entities != null && entities.size() > 0){
	 * ContentCategoryBean bean ; for(ContentCategoryEntity entity : entities){
	 * bean = new ContentCategoryBean(); fillContentCategoryBean(bean, entity);
	 * beans.add(bean); } } return beans; } // get Content Tips Categories
	 * public List<ContentCategoryBean> getTipsCategory(){
	 * List<ContentCategoryBean> beans = new ArrayList<ContentCategoryBean>();
	 * List<ContentCategoryEntity> entities = dao.getTipsCategory(); if(entities
	 * != null && entities.size() > 0){ ContentCategoryBean bean ;
	 * for(ContentCategoryEntity entity : entities){ bean = new
	 * ContentCategoryBean(); fillContentCategoryBean(bean, entity);
	 * beans.add(bean); } } return beans; } // get Content Offers Categories
	 * public List<ContentCategoryBean> getOffersCategory(){
	 * List<ContentCategoryBean> beans = new ArrayList<ContentCategoryBean>();
	 * List<ContentCategoryEntity> entities = dao.getOffersCategory();
	 * if(entities != null && entities.size() > 0){ ContentCategoryBean bean ;
	 * for(ContentCategoryEntity entity : entities){ bean = new
	 * ContentCategoryBean(); fillContentCategoryBean(bean, entity);
	 * beans.add(bean); } } return beans; }
	 */
	// get Content Offers Categories
	public List<ContentCategoryBean> getContTypeCategories(int contType, String url)
	{
		List<ContentCategoryBean> beans = new ArrayList<ContentCategoryBean>();
		List<ContentCategoryEntity> entities = dao.getContTypeCategories(contType);
		if (entities != null && entities.size() > 0)
		{
			ContentCategoryBean bean;
			for (ContentCategoryEntity entity : entities)
			{
				bean = new ContentCategoryBean();
				fillContentCategoryBean(bean, entity, url);
				beans.add(bean);
			}
		}
		return beans;
	}
	
	

	
	

	
	private void fillContentCategoryBean(ContentCategoryBean bean, ContentCategoryEntity entity, String url)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new ContentCategoryBean();
		
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		
		if (entity.getCat_image() != null && !entity.getCat_image().equals(""))
			bean.setCat_image(url + "images/content/" + entity.getCat_image());
		else
			bean.setCat_image("");
		
		
		if (entity.getCat_image_sc() != null && !entity.getCat_image_sc().equals(""))
			bean.setCat_image_sc(url + "images/content/" + entity.getCat_image_sc());
		else
			bean.setCat_image_sc("");
	}
	
	
	
	public void fillContentDetails(ContentDetailsBean bean, ContentDetailsEntity entity, String url)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new ContentDetailsBean();
		
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setContentAr(entity.getContentAr());
		bean.setContentEn(entity.getContentEn());
		bean.setEndTime(entity.getEndTime());
		bean.setLargeImage(url + DBConstants.CONTENT_IMAGES_UPLOADS + entity.getLargeImage());
		bean.setSmallImage(url + DBConstants.CONTENT_IMAGES_UPLOADS + entity.getSmallImage());
		bean.setStartTime(entity.getStartTime());
		bean.setTitleAr(entity.getTitleAr());
		bean.setTitleEn(entity.getTitleEn());
		
		bean.setUserFavoritieses(null);
		bean.setCommentses(null);
		
//		bean.setSmallLogo(null);
//		bean.setLargeLogo(null);

		
//		if (utils.hasValue(entity.getUnit()) && utils.hasValue(entity.getUnit().getUnitlogo()))
//			bean.setSmallLogo(url + DBConstants.CONTENT_IMAGES_UPLOADS + entity.getUnit().getUnitlogo());
//		else if (!utils.hasValue(entity.getUnit()))
//			bean.setSmallLogo(url + DBConstants.DEFAULT_IMAGE);
//		else
//			bean.setSmallLogo("");
//		
//		
//		
//		if (utils.hasValue(entity.getUnit()) && utils.hasValue(entity.getUnit().getUnitlogo()))
//			bean.setLargeLogo(url + DBConstants.CONTENT_IMAGES_UPLOADS + entity.getLargeLogo());
//		else if (!utils.hasValue(entity.getUnit()))
//			bean.setLargeLogo(url + DBConstants.DEFAULT_IMAGE);
//		else
//			bean.setLargeLogo("");

		
	
		if (utils.hasValue(entity.getSmallImage()))
			bean.setSmallImage(url + DBConstants.CONTENT_IMAGES_UPLOADS + entity.getSmallImage());
		else
			bean.setSmallImage("");
		
		if (utils.hasValue(entity.getLargeImage()))
			bean.setLargeImage(url + DBConstants.CONTENT_IMAGES_UPLOADS + entity.getLargeImage());
		else
			bean.setLargeImage("");
		

		
		bean.setUnit(Mapping.mapUnitEntity(entity.getUnit()));
		
	}
	
	
	
	public void fillUsersBean(UsersBean bean, UsersEntity entity)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new UsersBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setUsername(entity.getUsername());
//		bean.setPassword(entity.getPassword());
		bean.setNationalId(entity.getNationalId());
		bean.setEmail(entity.getEmail());
	}
	
	
	
	// Fill Bean From Entity
	public void fillPriorityBean(PriorityBean bean, PriorityEntity entity)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new PriorityBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setCode(entity.getCode());
		bean.setNameAr(entity.getNameAr());
		bean.setNameEn(entity.getNameEn());
	}
}
