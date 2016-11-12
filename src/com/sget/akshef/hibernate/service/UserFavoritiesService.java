package com.sget.akshef.hibernate.service;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CertificationBean;
import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.DegreeBean;
import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.beans.ProfessionalExpBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.UserFavoritiesBean;
import com.sget.akshef.hibernate.beans.UserFavoritiesForMobile;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.UserFavoritiesDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CertificationEntity;
import com.sget.akshef.hibernate.entities.ContentCategoryEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.PriorityEntity;
import com.sget.akshef.hibernate.entities.ProfessionalExpEntity;
import com.sget.akshef.hibernate.entities.SectionsHasSpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.UserFavoritiesEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/**
 * 
 * @author JDeeb
 * User Favorites Service
 */
public class UserFavoritiesService {
	UserFavoritiesDAO dao = null ;
	
	public UserFavoritiesService(){
		dao = new UserFavoritiesDAO();
	}
	
	public void insert(UserFavoritiesBean bean){
		if(bean == null)
			return;
		UserFavoritiesEntity entity = new UserFavoritiesEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(UserFavoritiesBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        UserFavoritiesEntity entity = new UserFavoritiesEntity();
		fillEntity(entity, bean);
		return dao.update(entity);
		
    }

    public boolean delete(UserFavoritiesBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        UserFavoritiesEntity entity = new UserFavoritiesEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public UserFavoritiesBean getById(int id) {
    	if(id < 1)
    		return null ;
    	UserFavoritiesBean bean = new UserFavoritiesBean();
    	UserFavoritiesEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	return bean ;
    }
    // Get All
    public List<UserFavoritiesBean> getAll() {
    	List<UserFavoritiesEntity> entities = dao.getAll();
    	List<UserFavoritiesBean> beans = new ArrayList<UserFavoritiesBean>();
    	UserFavoritiesBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(UserFavoritiesEntity entity : entities){
	    		bean = new UserFavoritiesBean();
	    		fillBean(bean, entity);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    // Get All For Mobile
    public UserFavoritiesForMobile getAllForMobile(int userId , String url) {
    	List<UserFavoritiesEntity> entities = dao.getAllByUser(userId);
    	UserFavoritiesForMobile beans = new UserFavoritiesForMobile();
    	
    	if(beans.getBranches() == null)
    		beans.setBranches(new ArrayList<BranchBean>());
    	if(beans.getContents() == null)
    		beans.setContents(new ArrayList<ContentDetailsBean>());
    	if(beans.getSpecialists() == null)
    		beans.setSpecialists(new ArrayList<SpecialistBean>());
    	BranchService branService = new BranchService();
    	SpecialistService spservice = new SpecialistService();
    	ContentDetailsService contservice = new ContentDetailsService();
    	if(entities != null && entities.size() > 0){
	    	for(UserFavoritiesEntity entity : entities){
	    		if(entity.getBranch() != null){
	    			BranchBean branchbean = new BranchBean();
	    			fillBranchBean(branchbean, entity.getBranch());
	    			branchbean.setImage(url + branchbean.getImage());
	    	    	// Add Rating to Bean
	    			branService.getBranchRating(branchbean);
	    			beans.getBranches().add(branchbean);
	    		}else if(entity.getContentDetails() != null){
	    			ContentDetailsBean contentBean = new ContentDetailsBean();
	    			fillContentBean(contentBean, entity.getContentDetails());
	    			// Add Rating to Bean
	    			contservice.getContentRating(contentBean);
	    			beans.getContents().add(contentBean);
	    		}else if(entity.getSpecialist() != null){
	    			SpecialistBean specBean = new SpecialistBean();
	    			fillSpecialistBean(specBean, entity.getSpecialist());
	    			// Add Rating
	    			spservice.getSpecialistRating(specBean);
	    			beans.getSpecialists().add(specBean);
	    		}
	    	}
    	}
    	return beans;
    }
	// Fill Bean From Entity
	public void fillBean(UserFavoritiesBean bean , UserFavoritiesEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new UserFavoritiesBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setNotes(entity.getNotes());
		if(bean.getUsers() == null)
			bean.setUsers(new UsersBean());
		bean.getUsers().setId(entity.getUsers().getId());
		if(bean.getContentDetails() == null)
			bean.setContentDetails(new ContentDetailsBean());
		bean.getContentDetails().setId(entity.getContentDetails().getId());
		if(bean.getSpecialist() == null)
			bean.setSpecialist(new SpecialistBean());
		bean.getSpecialist().setId(entity.getSpecialist().getId());
		if(bean.getBranch() == null)
			bean.setBranch(new BranchBean());
		bean.getBranch().setId(entity.getBranch().getId());
	}
	// Fill Entity From Bean
	public void fillEntity(UserFavoritiesEntity entity,UserFavoritiesBean bean){
		if(bean == null)
			return;
		if(entity == null)
			entity = new UserFavoritiesEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setNotes(bean.getNotes());
		if(entity.getUsers() == null)
			entity.setUsers(new UsersEntity());
		entity.getUsers().setId(bean.getUsers().getId());
		
		
		if(bean.getContentDetails()!=null)
		{
		if(entity.getContentDetails() == null)
			entity.setContentDetails(new ContentDetailsEntity());
		entity.getContentDetails().setId(bean.getContentDetails().getId());
		}
		
		if(bean.getSpecialist()!=null)
		{
		if(entity.getSpecialist() == null)
			entity.setSpecialist(new SpecialistEntity());
		entity.getSpecialist().setId(bean.getSpecialist().getId());
		}
		
		
		if(bean.getBranch()!=null)
		{
		if(entity.getBranch() == null)
			entity.setBranch(new BranchEntity());
		entity.getBranch().setId(bean.getBranch().getId());
		}
	}
	// Fill BranchBean
	public void fillBranchBean(BranchBean bean , BranchEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new BranchBean();
		// Basics Data
		bean.setActive(entity.getActive());
		bean.setId(entity.getId());
		bean.setNameEn(entity.getNameEn());
		bean.setNameAr(entity.getNameAr());
		bean.setAddress(entity.getAddress());
		bean.setEstablishdate(entity.getEstablishdate());
		bean.setLat(entity.getLat());
		bean.setLng(entity.getLng());
//		bean.setMobile(entity.getMobile());
//		bean.setRegion(entity.getRegion());
		bean.setTel(entity.getTel());
		bean.setWebsite(entity.getWebsite());
		bean.setBiography_ar(entity.getBiography_ar());
		bean.setBiography_en(entity.getBiography_en());
		bean.setImage(DBConstants.BRANCH_IMAGES_UPLOADS + entity.getImage());
		
		// New 
		bean.setEmail(entity.getEmail());
		bean.setFax(entity.getFax());
		bean.setHotline(entity.getHotline());
		bean.setPostlcode(entity.getPostlcode());

	}
	// Fill SpecialistBean
	public void fillSpecialistBean(SpecialistBean bean , SpecialistEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new SpecialistBean();
		// Basics Data
		
		/*rating image grad_year buis_name job_no_lincs gradfac degree_id*/
		List<SectionsBean> sections=new ArrayList<SectionsBean>();
		List<CertificationBean> certificationBeans=new ArrayList<CertificationBean>();
		List<ProfessionalExpBean> profBeans= new ArrayList<ProfessionalExpBean>();
		UsersBean userbean=new UsersBean();
		SectionsBean sectionBean;
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());

		bean.setRating(entity.getRating()!=null?entity.getRating():0);
		//bean.setGradYear(entity.getGradYear());

//		if(entity.getDegree()!=null)
//		{
//			DegreeBean degreeBean=new DegreeBean();
//			degreeBean.setId(entity.getId());
//			degreeBean.setDegree(entity.getDegree().getDegree());
//			bean.setDegree(degreeBean);
//			
//		}
//		else
//		{
//			bean.setDegree(new DegreeBean());	
//		}
		
		if(entity.getUsers() != null){
			userbean.setId(entity.getUsers().getId());
			userbean.setNameEn(entity.getUsers().getNameEn());
			userbean.setNameAr(entity.getUsers().getNameAr());
			userbean.setUsername(entity.getUsers().getUsername());
//			userbean.setPassword(entity.getUsers().getPassword());
			userbean.setNationalId(entity.getUsers().getNationalId());
			userbean.setEmail(entity.getUsers().getEmail());
			userbean.setGender(entity.getUsers().getGender());
			userbean.setFirstName(entity.getUsers().getFirstName());
			userbean.setLastName(entity.getUsers().getLastName());
			userbean.setAddress(entity.getUsers().getAddress());
			userbean.setDistrict_id(entity.getUsers().getDistric()!=null?entity.getUsers().getDistric().getId():0);

			userbean.setCityST(entity.getUsers().getDistric()!=null?entity.getUsers().getDistric().getCity().getNameAr():"");
			userbean.setCountryST(entity.getUsers().getDistric()!=null?entity.getUsers().getDistric().getCity().getCountry().getNameAr():"");
			userbean.setDistrictST(entity.getUsers().getDistric()!=null?entity.getUsers().getDistric().getNameAr():"");
			userbean.setCityST(entity.getUsers().getDistric()!=null?entity.getUsers().getDistric().getCity().getNameAr():"");
			userbean.setCountryST(entity.getUsers().getDistric()!=null?entity.getUsers().getDistric().getCity().getCountry().getNameAr():"");			
			
			
			
			if(entity.getUsers().getBirthdate()!=null && entity.getUsers().getBirthdate().getTime()!=0)
				userbean.setBirthdate(new java.sql.Date(entity.getUsers().getBirthdate().getTime()));
			userbean.setTelephone(entity.getUsers().getTelephone());
			userbean.setMobile(entity.getUsers().getMobile());
			userbean.setMarital_stat(entity.getUsers().getMarital_stat());
			userbean.setEmergency_contact(entity.getUsers().getEmergency_contact());
			userbean.setGenderSt(entity.getUsers().getGender()==1?"ذكر":"أنثى");
			userbean.setProfile_img(entity.getUsers().getProfile_img()!=null &&!entity.getUsers().getProfile_img().equals("")  ? entity.getUsers().getProfile_img():"");
			if (entity.getActive() != null)
				userbean.setActive(entity.getUsers().getActive());
			bean.setUsers(userbean);
			
		}
		
		
		if(entity.getSectionsHasSpecialistSet() != null){
			for(SectionsHasSpecialistEntity secentity:entity.getSectionsHasSpecialistSet())
			{
				sectionBean=new SectionsBean();
				sectionBean.setId(secentity.getSections().getId());
				sectionBean.setNameAr(secentity.getSections().getNameAr());
				sections.add(sectionBean);
			}
			
			bean.setSectionBeans(sections);
		}
		
		CertificationBean cetbean;
		if(entity.getUsers().getCertificationSet() != null){
			for(CertificationEntity cetEntity:entity.getUsers().getCertificationSet())
			{
				cetbean=new CertificationBean();
				cetbean.setId(cetEntity.getId());
				cetbean.setName_ar(cetEntity.getName_ar());
				cetbean.setDate(cetEntity.getDate()+"");

				cetbean.setDetails_ar(cetEntity.getDetails_ar());
				cetbean.setDetails_en(cetEntity.getDetails_en());
				
				certificationBeans.add(cetbean);
			}
			
			bean.setCertList(certificationBeans);
			
		}
		
		ProfessionalExpBean profBean;
		if(entity.getUsers().getProfessionalSet() != null){
			for(ProfessionalExpEntity professionalExpEntity:entity.getUsers().getProfessionalSet())
			{
				profBean=new ProfessionalExpBean();
				profBean.setId(professionalExpEntity.getId());
				profBean.setPlace(professionalExpEntity.getPlace());
				profBean.setStartDate(professionalExpEntity.getStartDate());
				profBean.setEndDate(professionalExpEntity.getEndDate());
				profBean.setPosition(professionalExpEntity.getPosition());
				profBeans.add(profBean);
			}
			
			bean.setProfList(profBeans);
			
		}
		
	}
	// Fill ContentBean
	public void fillContentBean(ContentDetailsBean bean , ContentDetailsEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ContentDetailsBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setContentAr(entity.getContentAr());
		bean.setContentEn(entity.getContentEn());
		bean.setEndTime(entity.getEndTime());
		bean.setStartTime(entity.getStartTime());
		bean.setLargeImage(DBConstants.CONTENT_IMAGES_UPLOADS + entity.getLargeImage());
		bean.setSmallImage(DBConstants.CONTENT_IMAGES_UPLOADS + entity.getSmallImage());
		bean.setTitleAr(entity.getTitleAr());
		bean.setTitleEn(entity.getTitleEn());
		
		/*if(bean.getUsers() == null)
			bean.setUsers(new UsersBean());
		
		fillUserBean(bean.getUsers(), entity.getUsers());
		if(bean.getPriority() == null)
			bean.setPriority(new PriorityBean());
		fillPriorityBean(bean.getPriority(), entity.getPriority());
		if(bean.getContentCategory() == null)
			bean.setContentCategory(new ContentCategoryBean());
		fillContentCategoryBean(bean.getContentCategory(), entity.getContentCategory());*/
	}
	// Fill UsersBean
	public void fillUserBean(UsersBean bean , UsersEntity entity){
		if(entity == null)
			return;
		if(bean == null)
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
	// Fill PriorityBean
	public void fillPriorityBean(PriorityBean bean , PriorityEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new PriorityBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setCode(entity.getCode());
		bean.setNameAr(entity.getNameAr());
		bean.setNameEn(entity.getNameEn());
	}
	// Fill Bean From Entity
	public void fillContentCategoryBean(ContentCategoryBean bean , ContentCategoryEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ContentCategoryBean();
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setNameAr(entity.getNameAr());
		bean.setNameEn(entity.getNameEn());
	}
}