package com.akshffeen.mapper;

import com.akshffeen.utils.Utils;
import com.sget.akshef.beans.SpecialistData;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CategoryEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

public class Mapping
{
	private static Utils utils = Utils.getInstance();

	public static BranchBean mapBranchEntity(BranchEntity branchEntity)
	{
		if(branchEntity==null)
			return null;
		
		BranchBean branchBean = new BranchBean();
				
		branchBean.setId(branchEntity.getId());
		
		if(branchEntity.getUnit()!=null)
			branchBean.setUnit(mapUnitEntity(branchEntity.getUnit()));
		
		if(branchEntity.getDistric()!=null)
			branchBean.setDistric(mapDistricEntity(branchEntity.getDistric()));
		
		branchBean.setNameAr(branchEntity.getNameAr());
		branchBean.setNameEn(branchEntity.getNameEn());
		branchBean.setLat(branchEntity.getLat());
		branchBean.setLng(branchEntity.getLng());
		branchBean.setAddress(branchEntity.getAddress());
		branchBean.setEstablishdate(branchEntity.getEstablishdate());
		branchBean.setTel(branchEntity.getTel());
		branchBean.setWebsite(branchEntity.getWebsite());
		branchBean.setActive(branchEntity.getActive());
		branchBean.setBiography_ar(branchEntity.getBiography_ar());
		branchBean.setBiography_en(branchEntity.getBiography_en());
		branchBean.setImage(branchEntity.getImage());		
		branchBean.setBranch_rating(branchEntity.getBranch_rating()!=null?branchEntity.getBranch_rating():0);

		
		return branchBean;
	}
	
	public static UnitBean mapUnitEntity(UnitEntity unitEntity)
	{
		if(unitEntity==null)
			return null;
		
		String url = utils.getXMLkey("AkshffeenAdmin");

		UnitBean unitBean = new UnitBean();
				
		unitBean.setId(unitEntity.getId());
				
		unitBean.setNameAr(unitEntity.getNameAr());
		unitBean.setNameEn(unitEntity.getNameEn());
			
		if (utils.hasValue(unitEntity.getUnitlogo()))
			unitBean.setUnitlogo(url + DBConstants.UNIT_IMAGES_UPLOADS + unitEntity.getUnitlogo());
		else
			unitBean.setUnitlogo("");
		
		unitBean.setBranches(null);
		unitBean.setUnitGroupses(null);
		unitBean.setUnitHasInsuranceCompanies(null);
				
		return unitBean;
	}

	public static DistricBean mapDistricEntity(DistricEntity districEntity)
	{
		if(districEntity==null)
			return null;
		
		DistricBean districBean = new DistricBean();
				
		districBean.setId(districEntity.getId());
				
		districBean.setNameAr(districEntity.getNameAr());
		districBean.setNameEn(districEntity.getNameEn());
				
		return districBean;
	}
	
	public static ContentDetailsBean mapContentDetailsEntity(ContentDetailsEntity contentDetailsEntity)
	{
		if(contentDetailsEntity==null)
			return null;
		
		ContentDetailsBean contentDetailsBean = new ContentDetailsBean();
				
		contentDetailsBean.setId(contentDetailsEntity.getId());
		
		if(contentDetailsEntity.getUnit()!=null)
		{
			contentDetailsBean.setUnit(mapUnitEntity(contentDetailsEntity.getUnit()));
			
//			contentDetailsBean.setSmallLogo(contentDetailsEntity.getUnit().getUnitlogo());
//			contentDetailsBean.setLargeLogo(contentDetailsEntity.getUnit().getUnitlogo());
		}
				
		contentDetailsBean.setTitleAr(contentDetailsEntity.getTitleAr());
		contentDetailsBean.setTitleEn(contentDetailsEntity.getTitleEn());
		contentDetailsBean.setContentAr(contentDetailsEntity.getContentAr());
		contentDetailsBean.setContentEn(contentDetailsEntity.getContentEn());
		contentDetailsBean.setSmallImage(contentDetailsEntity.getSmallImage());
		contentDetailsBean.setLargeImage(contentDetailsEntity.getLargeImage());

		
		return contentDetailsBean;
	}
	
	public static SpecialistEntity mapDoctor(SpecialistData specialistData)
	{
		SpecialistEntity doctor = new SpecialistEntity();
		UsersEntity user = new UsersEntity();
		doctor.setUsers(user);
		
		doctor.setName(specialistData.getDoctor_name());
		user.setNameAr(specialistData.getDoctor_name());
		user.setNameEn(specialistData.getDoctor_name());
		user.setEmail(specialistData.getUser_email());
		user.setFirstName(specialistData.getDoctor_name());
		user.setLastName(specialistData.getDoctor_name());
		user.setGender(specialistData.getGender());
		user.setAddress(specialistData.getAddress());
		user.setTelephone(specialistData.getUser_tel());
		user.setGender(specialistData.getGender());

		return doctor;
	}
	
	public static CategoryBean mapCategoryEntity(CategoryEntity categoryEntity)
	{
		CategoryBean categoryBean = new CategoryBean();
		
		categoryBean.setId(categoryEntity.getId());
		categoryBean.setNameAr(categoryEntity.getNameAr());
		categoryBean.setNameEn(categoryEntity.getNameEn());

		return categoryBean;	
	}
	
}
