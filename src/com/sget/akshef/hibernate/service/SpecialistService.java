package com.sget.akshef.hibernate.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.akshffeen.utils.Constants;
import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CertificationBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.ContentTypesBean;
import com.sget.akshef.hibernate.beans.DegreeBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.KeywordBean;
import com.sget.akshef.hibernate.beans.MedicalhistoryBean;
import com.sget.akshef.hibernate.beans.MessagesBean;
import com.sget.akshef.hibernate.beans.ProfessionalExpBean;
import com.sget.akshef.hibernate.beans.ScheduleDays;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.SpecialistHasBranchBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.CommentsDAO;
import com.sget.akshef.hibernate.dao.DistricDAO;
import com.sget.akshef.hibernate.dao.SectionsDAO;
import com.sget.akshef.hibernate.dao.SectionsHasSpecialistDAO;
import com.sget.akshef.hibernate.dao.SpecialistDAO;
import com.sget.akshef.hibernate.dao.UserRateSpecDAO;
import com.sget.akshef.hibernate.dao.UsersDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CertificationEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.ContentTypesEntity;
//import com.sget.akshef.hibernate.entities.DegreeEntity;
import com.sget.akshef.hibernate.entities.DistricEntity;
import com.sget.akshef.hibernate.entities.DoctorHasKeywordEntity;
import com.sget.akshef.hibernate.entities.KeywordEntity;
import com.sget.akshef.hibernate.entities.MedicalhistoryEntity;
import com.sget.akshef.hibernate.entities.MessagesEntity;
import com.sget.akshef.hibernate.entities.ProfessionalExpEntity;
import com.sget.akshef.hibernate.entities.ScheduleHasDaysEntity;
import com.sget.akshef.hibernate.entities.SectionsEntity;
import com.sget.akshef.hibernate.entities.SectionsHasSpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.SpecialistHasBranchEntity;
import com.sget.akshef.hibernate.entities.UserRateSpecEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshf.mobile.api.AppConstants;
import com.sget.akshf.searchcriteria.DoctorAppointmentCriteria;
import com.sget.akshf.searchcriteria.DoctorSectionBranchCriteria;
import com.sget.akshf.searchcriteria.SearchDoctorsCriteria;

/**
 * 
 * @author JDeeb
 * Specialist Service
 */
public class SpecialistService {
	SpecialistDAO dao = null ;
	Utils utils = Utils.getInstance();
	private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	public SpecialistService(){
		dao = new SpecialistDAO();
	}
	
	public void insert(SpecialistBean bean){
		if(bean == null)
			return;
		SpecialistEntity entity = new SpecialistEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}
	public boolean update(SpecialistBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
		
        SpecialistEntity entity = new SpecialistEntity();
		fillEntity(entity, bean);
		calculateProgressSpecialist(bean.getId());
		return dao.update(entity);
		
    }

    public boolean delete(SpecialistBean bean) {
        if(bean == null || bean.getId() < 1)
        	return false;
        SpecialistEntity entity = new SpecialistEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);
		
    }
    // Get By ID
    public SpecialistBean getById(int id) {
    	if(id < 1)
    		return null ;
    	SpecialistBean bean = new SpecialistBean();
    	SpecialistEntity entity = dao.getById(id);
    	fillBean(bean, entity);
    	getSpecialistRating(bean);
    	return bean ;
    }
    // Get All
    public List<SpecialistBean> getAll() {
    	List<SpecialistEntity> entities = dao.getAll();
    	List<SpecialistBean> beans = new ArrayList<SpecialistBean>();
    	SpecialistBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(SpecialistEntity entity : entities){
	    		bean = new SpecialistBean();
	    		fillBean(bean, entity);
	    		getSpecialistRating(bean);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    // getSpecialistBySectionAndBranch
    public List<SpecialistBean> getSpecialistBySectionAndBranch(DoctorSectionBranchCriteria search){
    	List<SpecialistEntity> entities = dao.getSpecialistBySectionAndBranch(search);
    	List<SpecialistBean> beans = new ArrayList<SpecialistBean>();
    	SpecialistBean bean ;
    	if(entities != null && entities.size() > 0){
	    	for(SpecialistEntity entity : entities){
	    		bean = new SpecialistBean();
	    		fillBean(bean, entity);
	    		//getSpecialistRating(bean);
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    
    public SpecialistBean getSpecialistByUserID(int user_id){
    	SpecialistEntity entity = dao.getSpecialistByUserID(user_id);
    	SpecialistBean bean=new SpecialistBean() ;
	    fillBean(bean, entity);
	    getSpecialistRating(bean);
	    
    	return bean;
    }
    
    public int  saveRating(int specialistId,int userId,int rating)
    {
   	boolean isRatingSaved=true;
    	UserRateSpecDAO ratDao=new UserRateSpecDAO();
    	int totalRate=0;
    	String sequence="";
    	Date date=new Date();
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	int month = cal.get(Calendar.MONTH)+1;
    	int year=cal.get(Calendar.YEAR);
    	sequence=month+"_"+year+"_"+"1";
    	SpecialistDAO specialistDAO=new SpecialistDAO();
    	UsersDAO userDao=new UsersDAO();
    	SpecialistEntity specEnt=specialistDAO.getById(specialistId);
    	UsersEntity userEntity=userDao.getById(userId);
    	List<Object> listRat=ratDao.checkIfUserRateSpec(specialistId, userId);
    	if(listRat==null || listRat.isEmpty())
    	{
    	UserRateSpecEntity entity=new UserRateSpecEntity();
    	entity.setSpecialist(specEnt);
    	entity.setUsers(userEntity);
    	entity.setRate(rating);
    	entity.setRating_date(sequence);
    	ratDao.insert(entity);
    	}
    	else 
    	{
    		Object[] arr=(Object[])listRat.get(0);
    		int id=(Integer) arr[0];
    		UserRateSpecEntity entity=ratDao.getById(specialistId);
    		String ratDate=entity.getRating_date();
    		String a[]=ratDate.split("_");
    		if(a!=null && a.length!=0)
    		{
    		int monthStr=Integer.parseInt(a[0]);	
    		int yearStr=Integer.parseInt(a[1]);
    		int serial=Integer.parseInt(a[2]);	
    		if(monthStr<month && yearStr<=year)
    		{
    		  	sequence=month+"_"+year+"_"+"1";
    		}
    		else if(monthStr==month && yearStr<year)
    		{
    			sequence=month+"_"+year+"_"+"1";
    		}
    		
    		
    		
    		else if(monthStr==month && yearStr==year &&serial==1)
    		{
    			sequence=month+"_"+year+"_"+"2";
    		}
    		
    		else if(monthStr==month && yearStr==year &&serial==2)
    		{
    			return -1;
    		}
    			
    		}
    		entity.setRating_date(sequence);
    		entity.setRate(rating);
    		ratDao.update(entity);
        	
    		
    	}
    	List<Object> list=ratDao.getRatingToBesavedSpec(specialistId);
    	if(list!=null &&! list.isEmpty())
    	{
    	Object[] arr=	(Object[])list.get(0);
    	Number rate=(Number) arr[0];
    	Number count=(Number) arr[1];
    	 totalRate=Math.round(rate.floatValue()/count.floatValue());
    	 specEnt.setRating(totalRate);
    	 specialistDAO.update(specEnt);
    		
    	}
    	return totalRate;
    }
    

    
	public void fillCertifications(SpecialistEntity entity, SpecialistBean bean)
	{
		List<CertificationBean> certificationList = new ArrayList<CertificationBean>();

		try
		{
			List<CertificationEntity> certificationSet = entity.getUsers().getCertificationSet();
			
			Iterator<CertificationEntity> iterator = certificationSet.iterator();
			CertificationEntity certification = null;
			CertificationBean certificationBean = null;
			while(iterator.hasNext())
			{
				certification = iterator.next();
				
				certificationBean = new CertificationBean();
				certificationBean.setId(certification.getId());
				certificationBean.setName_ar(certification.getName_ar());
				certificationBean.setName_en(certification.getName_en());

				certificationBean.setDetails_ar(certification.getDetails_ar());
				certificationBean.setDetails_en(certification.getDetails_en());
				
				if(certification.getDate()!=null)
					certificationBean.setDate(formatter.format(certification.getDate()));
				
				certificationList.add(certificationBean);
			}
			
		}
		catch(Exception e)
		{
			
		}
		
		bean.setCertificationList(certificationList);
	}
	
	  
	public void fillKeywords(SpecialistEntity entity, SpecialistBean bean)
	{
		List<KeywordBean> keywordList = new ArrayList<KeywordBean>();

		try
		{
			List<DoctorHasKeywordEntity> keywordSet = specialistDAO.loadDoctorHasKeyword(entity.getId());
			
			Iterator<DoctorHasKeywordEntity> iterator = keywordSet.iterator();
			DoctorHasKeywordEntity keyword = null;
			KeywordBean keywordBean = null;
			while(iterator.hasNext())
			{
				keyword = iterator.next();
				
				keywordBean = new KeywordBean();
				keywordBean.setId(keyword.getId());
				keywordBean.setName(keyword.getKeyword().getName());
				
				keywordList.add(keywordBean);
			}
			
		}
		catch(Exception e)
		{
			
		}
		
		bean.setKeywordList(keywordList);
	}
		

	
	
    public List<SpecialistBean> getSpecialistBySectionWeb(DoctorSectionBranchCriteria search,String url){
    	List<SpecialistEntity> entities = dao.getSpecialistBySectionWeb(search);
    	List<SpecialistBean> beans = new ArrayList<SpecialistBean>();
    	SpecialistBean bean = null ;
    	if(entities != null && entities.size() > 0){
	    	for(SpecialistEntity entity : entities){
	    		bean = new SpecialistBean();
	    		fillBean(bean, entity);
	    		getSpecialistRating(bean);
	    		bean.getUsers().setProfile_img(url+bean.getUsers().getProfile_img());
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    } 
    
    /**
     * Advanced Search Doctor
     * @param search
     * @return
     */
    
//    public List<DegreeBean>  getAllDegree()
//    {
//    List<DegreeEntity> degreeBeansEntities=	dao.getAllDegree();
//    List<DegreeBean> degreeBeans=new ArrayList<DegreeBean>();
//    DegreeBean bean;
//    
//    	for(DegreeEntity ent:degreeBeansEntities)
//    	{
//    		bean=new DegreeBean();
//    		bean.setId(ent.getId());
//    		bean.setDegree(ent.getDegree());
//    		degreeBeans.add(bean);
//    		
//    	}
//    	
//    	return degreeBeans;
//    }
    
    public List<CommentsBean>  getCommentsByContentId(int contentId)
    {
       List<CommentsBean> commentsBeanList=new ArrayList<CommentsBean>();
       CommentsBean bean;
 
       CommentsDAO	commentsDAO=new  CommentsDAO();
      List<CommentsEntity> comments= commentsDAO.getAllCommentsByContent(contentId);
     
      
      for(CommentsEntity entity:comments)
      {
          bean=new CommentsBean();
         
       // Basics Data
  		bean.setId(entity.getId());
  		bean.setComment(entity.getComment());
  		bean.setComment_date(entity.getComment_date());
  	
  	
  		if(entity.getSpecialist() != null){
  			if(bean.getSpecialist() == null)
  				bean.setSpecialist(new SpecialistBean());
  			bean.getSpecialist().setId(entity.getSpecialist().getId());
  		}
  		if(bean.getUsers() == null)
  			bean.setUsers(new UsersBean());
  		bean.getUsers().setId(entity.getUsers().getId());
          
         
         //  System.out.println("commentsBeanList "+ent.toString());
          commentsBeanList.add(bean);
     
      
      
    }
   
  
        
     return commentsBeanList;  
    }
    
    
    
    public boolean addCommentsDoctor(String comment,int userId,int doctorId)
    
    {
	
	if(userId==0)
		userId=3;
    boolean isInserted=false;
   
    	CommentsDAO commentsDAO=new CommentsDAO();
    CommentsEntity entity=new CommentsEntity();
    UsersEntity user=new UsersDAO().getById(userId);
    SpecialistEntity specialist=new SpecialistDAO().getById(doctorId);
    entity.setUsers(user);
    entity.setComment(comment);
    entity.setSpecialist(specialist);
    commentsDAO.insert(entity);
    
    
    
    return isInserted;
    }
    
    
    
    
   
    public List<SpecialistBean> searchDoctors(SearchDoctorsCriteria search,String url){
    	List<SpecialistEntity> entities = dao.searchDoctors(search);
    	List<SpecialistBean> beans = new ArrayList<SpecialistBean>();
    	SpecialistBean bean = null ;
    	if(entities != null && entities.size() > 0){
	    	for(SpecialistEntity entity : entities){
	    		bean = new SpecialistBean();
	    		fillBean(bean, entity);
	    		getSpecialistRating(bean);
	    		bean.getUsers().setProfile_img(url+bean.getUsers().getProfile_img());
	    		beans.add(bean);
	    	}
    	}
    	return beans;
    }
    

    
    public List<ScheduleDays> getSpecialistSchedule(DoctorAppointmentCriteria search,int subCat , int sect){
    	List<ScheduleHasDaysEntity> entities = dao.getSpecialistSchedule(search);
    	double price = getSpecialistPrise(subCat , sect , search.getBranch_id());
    	List<ScheduleDays> beans = new ArrayList<ScheduleDays>();
    	ScheduleDays bean ;
    	for(ScheduleHasDaysEntity entity : entities){
    		bean = new ScheduleDays();
    		fillSceduleDay(bean, entity);
    		bean.setPrice(price);
    		beans.add(bean);
    	}
    	return beans;
    }
    
    public List<BranchBean> getSpecialistPlaces(int doctorId , int dayId , String url){
    	List<BranchEntity> entities = dao.getSpecialistPlaces(doctorId, dayId);
    	List<BranchBean> beans = new ArrayList<BranchBean>();
    	BranchBean bean ;
    	BranchService branService = new BranchService();
    	if(entities != null && entities.size() > 0){
    		for(BranchEntity entity : entities){
        		bean = new BranchBean();
        		fillBranchBean(bean, entity);
        		bean.setImage(url + bean.getImage());
        		branService.getBranchRating(bean);
        		beans.add(bean);
        	}
    	}
    	return beans;
    }
    
    // JDeeb get Specialist price
    public double getSpecialistPrise(int subCat , int sect , int branch){
    	if(subCat <= 0 ||  sect <= 0 || branch <= 0)
    		return 0 ;
    	return dao.getSpecialistPrise(subCat , sect , branch);
    }
 //getSpecialistMessages  
    
    public List<MessagesBean> getSpecialistMessages(int doctorId ){
    	List<MessagesEntity> entities = dao.getSpecialistMessages(doctorId);
    	List<MessagesBean> beans = new ArrayList<MessagesBean>();
    	MessagesBean bean ;
    
    	if(entities != null && entities.size() > 0){
    		for(MessagesEntity entity : entities){
        		bean = new MessagesBean();
        		fillMessagesBean(bean, entity);
        		beans.add(bean);
        	}
    	}
    	return beans;
    }
    
    // Get Specialist Rating
    public void getSpecialistRating(SpecialistBean bean){
    	if(bean != null && bean.getId() > 0){
    		List<UserRateSpecEntity> lists = dao.getSpecialistRating(bean.getId());
    		if(lists != null && lists.size() > 0){
    			int rating = 0 ;
    			int ratingno = 0 ;
    			for(UserRateSpecEntity ent : lists){
    				if(ent != null && ent.getRate() >= 0){
    					rating += ent.getRate() ;
    					ratingno += 1 ;
    				}
    			}
    			int avarge = ( rating * 100  / (ratingno * AppConstants.APP_RATING_MAX) ) ;
    			bean.setRating(avarge);
    			bean.setRatingno(ratingno);
    		}
    	}	
    		
    }


    
    private void fillCertification(SpecialistBean bean, SpecialistEntity entity)
    {
//		bean.setGradYear(entity.getGradYear());
//		bean.setBuisnessName(utils.getValue(entity.getBuisnessName()));
//		bean.setBuisnessName_en(utils.getValue(entity.getBuisnessName_en()));
//		bean.setJob_no_lincs(utils.getValue(entity.getJob_no_lincs()));
//		bean.setGradFac(utils.getValue(entity.getGradFac()));
//		bean.setUnion_id(utils.getValue(entity.getUnion_id()));
////		bean.setDegree(getDegree(entity.getDegree_id()));
//		bean.setDegree_id(utils.hasValue(entity.getDegree_id())?entity.getDegree_id():1);
    }

    
	// Fill Bean From Entity
	public void fillBean(SpecialistBean bean, SpecialistEntity entity)
	{
		if (entity == null)
			return;
		if (bean == null)
			bean = new SpecialistBean();
		// Basics Data
		
		/* rating image grad_year buis_name job_no_lincs gradfac degree_id */
		List<SectionsBean> sections = new ArrayList<SectionsBean>();
		List<CertificationBean> certificationBeans = new ArrayList<CertificationBean>();
		List<ProfessionalExpBean> profBeans = new ArrayList<ProfessionalExpBean>();
		UsersBean userbean = new UsersBean();
		SectionsBean sectionBean;
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());

		bean.setBiography(utils.fixRequest(entity.getBiography()));
		bean.setBiography_en(utils.fixRequest(entity.getBiography_en()));
		
		bean.setSponsored(entity.getSponsored()==null?0:entity.getSponsored());
		
		bean.setLanguages(entity.getLanguages());

		fillCertification(bean, entity);
		

		bean.setRating(entity.getRating() != null ? entity.getRating() : 0);
		// bean.setGradYear(entity.getGradYear());
		// if(entity.getDegree()!=null)
		// {
		// DegreeBean degreeBean=new DegreeBean();
		// degreeBean.setId(entity.getId());
		// degreeBean.setDegree(entity.getDegree().getDegree());
		// bean.setDegree(degreeBean);
		//
		// }
		// else
		// {
		// bean.setDegree(new DegreeBean());
		// }
		
		if (entity.getUsers() != null)
		{
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
			userbean.setDistrict_id(entity.getUsers().getDistric() != null ? entity.getUsers().getDistric().getId() : 0);
			userbean.setDistrictST(entity.getUsers().getDistric() != null ? entity.getUsers().getDistric().getNameAr() : "");
			userbean.setCityST(entity.getUsers().getDistric() != null ? entity.getUsers().getDistric().getCity().getNameAr() : "");
			userbean.setCountryST(entity.getUsers().getDistric() != null ? entity.getUsers().getDistric().getCity().getCountry().getNameAr() : "");
						
			if (entity.getUsers().getBirthdate() != null && entity.getUsers().getBirthdate().getTime() != 0)
				userbean.setBirthdate(entity.getUsers().getBirthdate());
			
			userbean.setTelephone(entity.getUsers().getTelephone());
			userbean.setMobile(entity.getUsers().getMobile());
			userbean.setMarital_stat(entity.getUsers().getMarital_stat());
			userbean.setEmergency_contact(entity.getUsers().getEmergency_contact());
			userbean.setGenderSt(entity.getUsers().getGender() == 1 ? "ذكر" : "أنثى");
			userbean.setProfile_img(entity.getUsers().getProfile_img() != null && !entity.getUsers().getProfile_img().equals("") ? entity.getUsers().getProfile_img() : "img/profile.jpg");
			if (entity.getActive() != null)
				userbean.setActive(entity.getUsers().getActive());
			userbean.setActive_email(entity.getUsers().getActive_email() != null ? entity.getUsers().getActive_email() : 0);
			userbean.setEmailCount(entity.getUsers().getEmailCount() != null ? entity.getUsers().getEmailCount() : 0);
			userbean.setCreationDate(entity.getUsers().getCreationDate());
			userbean.setPass_creationDate(entity.getUsers().getPass_creationDate());
			userbean.setPass_emailCount(entity.getUsers().getPass_emailCount() != null ? entity.getUsers().getPass_emailCount() : 0);
			userbean.setBlood_type(entity.getUsers().getBlood_type());
			userbean.setProgress(entity.getUsers().getProgress());
			
			// fill medical hist
			List<MedicalhistoryBean> medHistBean = new ArrayList<MedicalhistoryBean>();
			MedicalhistoryBean medBean;
			if (entity.getUsers().getMedicalhistorySet() != null)
				for (MedicalhistoryEntity entityMed : entity.getUsers().getMedicalhistorySet())
				{
					medBean = new MedicalhistoryBean();
					medBean.setId(entity.getId());
					medBean.setDate_from(entityMed.getDate_from() != null ? new java.util.Date(entityMed.getDate_from().getTime()) : new java.util.Date());
					medBean.setDate_to(entityMed.getDate_to() != null ? new java.util.Date(entityMed.getDate_to().getTime()) : new java.util.Date());
					medBean.setSpec_name(entityMed.getSpec_name());
					medBean.setSickness(entityMed.getSickness());
					medBean.setSick_type(entityMed.getSick_type());
					medBean.setReport_type(entityMed.getReport_type());
					medBean.setReport_details(entityMed.getReport_details());
					medBean.setDetails(entityMed.getDetails());
					medBean.setReport_attach(entityMed.getReport_attach());
					medHistBean.add(medBean);
				}
			
			userbean.setCertList(null);

			userbean.setMedicalHist(medHistBean);
			bean.setUsers(userbean);
			
		}
		
		
		if (entity.getSectionsHasSpecialistSet() != null)
		{
			for (SectionsHasSpecialistEntity secentity : entity.getSectionsHasSpecialistSet())
			{
				sectionBean = new SectionsBean();
				sectionBean.setId(secentity.getSections().getId());
				sectionBean.setNameAr(secentity.getSections().getNameAr());
				sectionBean.setNameEn(secentity.getSections().getNameEn());
				sections.add(sectionBean);
			}
			
			bean.setSectionBeans(sections);
		}
		
		CertificationBean cetbean;
//		if (entity.getUsers().getCertificationSet() != null)
//		{
//			for (CertificationEntity cetEntity : entity.getUsers().getCertificationSet())
//			{
//				cetbean = new CertificationBean();
//				cetbean.setId(cetEntity.getId());
//				cetbean.setName_ar(cetEntity.getName_ar());
//				cetbean.setDate(cetEntity.getDate()+"");
//				
//				cetbean.setDetails_ar(cetEntity.getDetails_ar());
//				cetbean.setDetails_en(cetEntity.getDetails_en());
//				
//				certificationBeans.add(cetbean);
//			}
//			
//			bean.setCertList(certificationBeans);
//			
//		}
		
		ProfessionalExpBean profBean;
		if (entity.getUsers().getProfessionalSet() != null)
		{
			for (ProfessionalExpEntity professionalExpEntity : entity.getUsers().getProfessionalSet())
			{
				profBean = new ProfessionalExpBean();
				profBean.setId(professionalExpEntity.getId());
				profBean.setPlace(professionalExpEntity.getPlace());
				profBean.setStartDate(professionalExpEntity.getStartDate());
				profBean.setEndDate(professionalExpEntity.getEndDate());
				profBean.setPosition(professionalExpEntity.getPosition());
				profBean.setDetails(professionalExpEntity.getDetails());
				profBeans.add(profBean);
			}
			
			bean.setProfList(profBeans);
			
		}
		
		
	}



	public List<SectionsBean>  getAllSpecialistsCategory()
    {
       List<SectionsBean> catBeanList=new ArrayList<SectionsBean>();
       SectionsBean bean;
       SectionsHasSpecialistDAO  catDao=new  SectionsHasSpecialistDAO();
      List<SectionsEntity> categories= catDao.getSpecialistCategory();
      for(SectionsEntity ent:categories)
      {
          bean=new SectionsBean();
          bean.setNameEn(ent.getNameEn());
          bean.setNameAr(ent.getNameEn());
          bean.setId(ent.getId());
  		  bean.setImage(ent.getImage()!=null &&! ent.getImage().equals("")?"images/sections/" + ent.getImage():"img/content.jpg");
          catBeanList.add(bean);
      }
        
     return catBeanList;  
    }
	
	
	
	
    
    
//    public List<SpecialistBean>  getContentsSpecialistById(int catSectionId,String name,boolean sorting,int district_id,int gender,int degree)
//    {
// 
//      List<SpecialistEntity> categories= dao.getSpecialistBySection();
//      for(SpecialistEntity ent:categories)
//      {
//          bean=new ContentSpecialistsBean();
//          bean.setName_ar(ent.getName());
//          bean.setImage(ent.getImage());
//          bean.setId(ent.getId());
//          bean.setRating(ent.getRating());
//          catBeanList.add(bean);
//      
//      
//    }
//   
//  
//        
//     return catBeanList;  
//    }
	
	//Register Specialist
	public void regitserSpecialist(SpecialistBean specBean)
	{
		SpecialistEntity  entity=new SpecialistEntity();
		specialistDAO=new SpecialistDAO();
		//fill degree 
//		DegreeBean degreeBean=  specBean.getDegree();
//		DegreeEntity degreeEntity=new DegreeEntity();
//		degreeEntity.setId(degreeBean.getId());
//		degreeEntity.setDegree(degreeBean.getDegree());
//		entity.setDegree(degreeEntity);
		
		fillEntity(entity, specBean);
		UsersService userService=new UsersService();
		UsersEntity userEnt = new UsersEntity();
	 	userService.fillEntity(userEnt,specBean.getUsers());
		entity.setUsers(userEnt);
		specialistDAO.insert(entity);
		SectionsDAO secDao=new SectionsDAO();
		SectionsHasSpecialistDAO specService=new SectionsHasSpecialistDAO();
		SectionsHasSpecialistEntity secSpecEnt=new SectionsHasSpecialistEntity();
		CertificationEntity certEnt=new CertificationEntity();
		ProfessionalExpEntity profEnt=new ProfessionalExpEntity();
		for(Object secBean:specBean.getSectionBeans())
		{
			secSpecEnt=new SectionsHasSpecialistEntity();
			System.out.println("classs   "+secBean.getClass());
			secSpecEnt.setSections(secDao.getById(Integer.parseInt(secBean.toString())));
			secSpecEnt.setSpecialist(entity);
			specService.insert(secSpecEnt);
		}
		
		for(CertificationBean certBean:specBean.getCertList())
		{
			certEnt=new CertificationEntity();
//			if(certBean.getDate()!=null)
//			{
//				certEnt.setDate(certBean.getDate());
//			}
			certEnt.setName_ar(certBean.getName_ar());
			
			certEnt.setDetails_ar(certBean.getDetails_ar());
			certEnt.setDetails_en(certBean.getDetails_en());
			
			certEnt.setUsers(userEnt);
			specialistDAO.insertCertification(certEnt);
			
			
		}
		
		
		for(ProfessionalExpBean profBean:specBean.getProfList())
		{
			profEnt=new ProfessionalExpEntity();
			if(profBean.getStartDate()!=null && profBean.getStartDate().getTime()!=0)
			{
				profEnt.setStartDate(new java.sql.Date(profBean.getStartDate().getTime()));
			}
			if(profBean.getEndDate()!=null && profBean.getEndDate().getTime()!=0)
			{
				profEnt.setEndDate(new java.sql.Date(profBean.getEndDate().getTime()));
			}
			
			profEnt.setDetails(profBean.getDetails());
			profEnt.setPlace(profBean.getPlace());
			profEnt.setPosition(profBean.getPosition());
			profEnt.setUsers(userEnt);
			specialistDAO.insertProfessional(profEnt);
		}
		
		
	}
	
	
	
	public void updateSpecialist(SpecialistBean specBean)
	{
		SpecialistEntity  entity=new SpecialistEntity();
		specialistDAO=new SpecialistDAO();
		fillEntity(entity, specBean);
		UsersService userService=new UsersService();
		UsersDAO usersDao=new UsersDAO();
		DistricDAO districtser=new DistricDAO();
		userService.update(specBean.getUsers());
		UsersBean UsersBeanspec=userService.getById(specBean.getUsers().getId());
		specBean.getUsers().setProgress(UsersBeanspec.getProgress());
		UsersEntity entityuser=  userService.fillEntity(specBean.getUsers());
		entity.setUsers(entityuser);
		DistricEntity dictrict=  districtser.getById(specBean.getUsers().getDistrict_id());
		entityuser.setDistric(dictrict);
		entity.setUsers(entityuser);
		usersDao.update(entityuser);
		specialistDAO.update(entity);
		SectionsDAO secDao=new SectionsDAO();
		SectionsHasSpecialistDAO specService=new SectionsHasSpecialistDAO();
		SectionsHasSpecialistEntity secSpecEnt=new SectionsHasSpecialistEntity();
		CertificationEntity certEnt=new CertificationEntity();
		ProfessionalExpEntity profEnt=new ProfessionalExpEntity();
		for(Object secBean:specBean.getSectionBeans())
		{
			secSpecEnt=new SectionsHasSpecialistEntity();
			System.out.println("classs   "+secBean.getClass());
			secSpecEnt.setSections(secDao.getById(Integer.parseInt(secBean.toString())));
			secSpecEnt.setSpecialist(entity);
			specService.insert(secSpecEnt);
		}
		
		for(CertificationBean certBean:specBean.getCertList())
		{
			certEnt=new CertificationEntity();
//			if(certBean.getDate()!=null && certBean.getDate().getTime()!=0)
//			{
//				certEnt.setDate(new java.sql.Date(certBean.getDate().getTime()));
//			}
			certEnt.setName_ar(certBean.getName_ar());

			certEnt.setDetails_ar(certBean.getDetails_ar());
			certEnt.setDetails_en(certBean.getDetails_en());
			
			certEnt.setUsers(entityuser);
			specialistDAO.insertCertification(certEnt);
			
			
		}
		
		
		for(ProfessionalExpBean profBean:specBean.getProfList())
		{
			profEnt=new ProfessionalExpEntity();
			if(profBean.getStartDate()!=null && profBean.getStartDate().getTime()!=0)
			{
				profEnt.setStartDate(new java.sql.Date(profBean.getStartDate().getTime()));
			}
			if(profBean.getEndDate()!=null && profBean.getEndDate().getTime()!=0)
			{
				profEnt.setEndDate(new java.sql.Date(profBean.getEndDate().getTime()));
			}
			
			profEnt.setDetails(profBean.getDetails());
			profEnt.setPlace(profBean.getPlace());
			profEnt.setPosition(profBean.getPosition());
			profEnt.setUsers(entityuser);
			specialistDAO.insertProfessional(profEnt);
				
		}
		
		calculateProgressSpecialist(specBean.getId());
		
		
	}
	
	public void calculateProgressSpecialist(int id)
	{
		SpecialistEntity specEnt=	dao.getById(id);
		//checkUserBranchOrSpecialist(userEnt.getId()));
		int countProgress=0;
		 

//		  if(specEnt.getDegree()!=null && specEnt.getDegree().getId()!=0)//5
//			  countProgress++;
		  if(specEnt.getUsers().getCertificationSet()!=null &&! specEnt.getUsers().getCertificationSet().isEmpty())//6
			  countProgress++;
		  if(specEnt.getSectionsHasSpecialistSet()!=null &&! specEnt.getSectionsHasSpecialistSet().isEmpty())//7
			  countProgress++;
		  if(specEnt.getUsers().getProfessionalSet()!=null &&! specEnt.getUsers().getProfessionalSet().isEmpty())//8
			  countProgress++;
		  
		  if(specEnt.getUsers().getNationalId()!=null && !specEnt.getUsers().getNationalId().equals(""))//9
			  countProgress++;
		  if(specEnt.getUsers().getUsername()!=null && !specEnt.getUsers().getUsername().equals(""))//10
			  countProgress++;
		  if(specEnt.getUsers().getPassword()!=null && !specEnt.getUsers().getPassword().equals(""))//11
			  countProgress++;
		  if(specEnt.getUsers().getEmail()!=null && !specEnt.getUsers().getEmail().equals(""))//12
			  countProgress++;
		  if(specEnt.getUsers().getGender()!=null && !specEnt.getUsers().getGender().equals("")&& !specEnt.getUsers().getGender().equals("0"))//13
			  countProgress++;
		  if(specEnt.getUsers().getProfile_img()!=null && !specEnt.getUsers().getProfile_img().equals(""))//14
			  countProgress++;
		  if(specEnt.getUsers().getFirstName()!=null && !specEnt.getUsers().getFirstName().equals(""))//15
			  countProgress++;
		  if(specEnt.getUsers().getLastName()!=null && !specEnt.getUsers().getLastName().equals(""))//16
			  countProgress++;
		  if(specEnt.getUsers().getAddress()!=null && !specEnt.getUsers().getAddress().equals(""))//17
			  countProgress++;
		  if(specEnt.getUsers().getTelephone()!=null && !specEnt.getUsers().getTelephone().equals("")&& !specEnt.getUsers().getTelephone().equals("0"))//18
			  countProgress++;
		  if(specEnt.getUsers().getMobile()!=null && !specEnt.getUsers().getMobile().equals("")&& !specEnt.getUsers().getMobile().equals("0"))//19
			  countProgress++;
		  if(specEnt.getUsers().getMarital_stat()!=null && !specEnt.getUsers().getMarital_stat().equals(""))//20
			  countProgress++;
		  if(specEnt.getUsers().getEmergency_contact()!=null && !specEnt.getUsers().getEmergency_contact().equals(""))//21
			  countProgress++;
		  if(specEnt.getUsers().getDistric()!=null && !specEnt.getUsers().getDistric().getId().equals("") && !specEnt.getUsers().getDistric().getId().equals("0"))//22
			  countProgress++;
		  if(specEnt.getUsers().getBirthdate()!=null && specEnt.getUsers().getBirthdate().getTime()!=0)//23
			  countProgress++;
		  if(specEnt.getUsers().getBlood_type()!=null &&! specEnt.getUsers().getBlood_type().equals(""))//24
			  countProgress++;
		  if(specEnt.getUsers().getMedicalhistorySet()!=null &&! specEnt.getUsers().getMedicalhistorySet().isEmpty())//25
			  countProgress++;

		  
		  int progress=countProgress*100/26;
		  specEnt.getUsers().setProgress(progress+"");
		  new UsersDAO().update(specEnt.getUsers());
		  
		  
		  
		
	}


	
	
	// Fill Entity From Bean
	public void fillEntity(SpecialistEntity entity,SpecialistBean bean){
		if(bean == null)
			return;

		if(entity == null)
			entity = new SpecialistEntity();
		// Basics Data
		entity.setActive(bean.getActive());
		entity.setId(bean.getId());
		entity.setName(bean.getName());
		
	}
	// Return schedule from schedule and days
	public void fillSceduleDay(ScheduleDays bean , ScheduleHasDaysEntity entity ){
		if(entity == null)
			return;
		if(bean == null)
			bean = new ScheduleDays();
		bean.setDay_id(entity.getDays().getId());
		bean.setFrom(formateDate(entity.getSchedule().getFrom_date()));
		bean.setTo(formateDate(entity.getSchedule().getTo_date()));
	}
	// Fill BranchBean From BranchEntity
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
		
		
		DistricBean districBean = new DistricService().fillBean(entity.getDistric()); ;
		bean.setDistric(districBean);
		bean.setImage(DBConstants.BRANCH_IMAGES_UPLOADS + entity.getImage());
	}
	
	// Fill message bean From Message entity
	public void fillMessagesBean(MessagesBean bean , MessagesEntity entity){
		if(entity == null)
			return;
		if(bean == null)
			bean = new MessagesBean();
		// Basics Data
		bean.setMsgBody(entity.getMsgBody());
		bean.setNotes(entity.getNotes());
		bean.setFrom_username(entity.getFromUser().getNameEn());
		bean.setTo_username(entity.getToUser().getNameEn());
	}
	
	// Convert From Date To String To delete Seconds
	public String formateDate(Date date){
		try{
			DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
			return df.format(date);
		}catch(Exception ex){
			ex.printStackTrace();
			return "";
		}
	}
	SpecialistDAO specialistDAO=new SpecialistDAO();
	 public List<CommentsBean>  getCommentsByDoctorId(int doctorId)
	    {
	    	System.out.println("doctorId  --- "+doctorId);
	       List<CommentsBean> commentsBeanList=new ArrayList<CommentsBean>();
	       CommentsBean bean;
	    if(specialistDAO==null)     
	    {
	     List<Object> comments= specialistDAO.getAllCommentsByDoctor(doctorId);
	     
	      
	      for(Object ent:comments)
	      {
	    	  Object[] arr=(Object[])ent;
	    	  
	    	  
	    	  System.out.println("ent   is --- "+arr[0]);
	    	  
	          bean=new CommentsBean();
	          CommentsEntity comment=(CommentsEntity)arr[0];
	          UsersEntity users=(UsersEntity)arr[1];
	          bean.setId(comment.getId());
	          bean.setComment(comment.getComment());
	          bean.getUsers().setId(users.getId());
	          
	         
	         // System.out.println("commentsBeanList "+ent.toString());
	          commentsBeanList.add(bean);
	      }
	      	specialistDAO=new  SpecialistDAO();
	     
	      
	    }
	   
	  
	        
	     return commentsBeanList;  
	    }
	 
	 
	 public List<MessagesBean> getMessagesBranch () 
	 {
		 List<MessagesBean> returned=new ArrayList<MessagesBean>(); 
		   List<Object> objects= specialistDAO.getMessagesBranch();
		   MessagesBean messagebean;
		   ContentTypesBean contBean;
		   ContentTypesService serviceContent=new ContentTypesService();
		   UsersService userService=new UsersService();
		   UsersBean fromuser;
		   UsersBean touser;  
		      
		      for(Object ent:objects)
		      {
		    	  Object[] arr=(Object[])ent;
		    	  messagebean=new MessagesBean();
		    	  contBean=new ContentTypesBean();
		    	  messagebean.setId(arr[0]!=null ?(Integer)arr[0]:0);
		    	  messagebean.setTitle(arr[1]!=null?(String)arr[1]:"");
		    	  messagebean.setMsgBody(arr[2]!=null?(String)arr[2]:"");
		    	  messagebean.setNotes(arr[3]!=null?(String)arr[3]:"");
		    	  if(arr[4]!=null)
		    	  {
		    		  contBean=  serviceContent.getById((Integer)arr[4]);
		    		  messagebean.setMsg_type(contBean.getNameAr());
		    	  }
		    	  
		    	  if(arr[5]!=null)
		    	  {
		    		  fromuser=  userService.getById((Integer)arr[5]);
		    		  messagebean.setFromUser(fromuser);
		    		  messagebean.setFrom_username(fromuser.getUsername());
		    	  }
		    	  if(arr[6]!=null)
		    	  {
		    		  touser=  userService.getById((Integer)arr[6]);
		    		  messagebean.setToUser(touser);
		    		  messagebean.setTo_username(touser.getUsername());
		    	  }
		    	  if(arr[8]!=null)
		    	  {
		    		   messagebean.setBranchName(arr[8].toString());
		    		 
		    	  }
		    	 
		    	  returned.add(messagebean);
		    	  
		    	  
		    	  System.out.println("ent   is --- "+arr[0]);
		    	  
		         
	 }
		      return returned;
	 
	 }
	 
	 
	 public List<MessagesBean> getMessagesSpecialist () 
	 {
		   List<Object> objects= specialistDAO.getMessagesSpecialist();
		   MessagesBean messagebean;
		   ContentTypesBean contBean;
		   ContentTypesService serviceContent=new ContentTypesService();
		   UsersService userService=new UsersService();
		   UsersBean fromuser;
		   UsersBean touser; 
		   List<MessagesBean> returned=new ArrayList<MessagesBean>(); 
		   
		      
		      for(Object ent:objects)
		      {
		    	  Object[] arr=(Object[])ent;
		    	  messagebean=new MessagesBean();
		    	  contBean=new ContentTypesBean();
		    	  messagebean.setId(arr[0]!=null ?(Integer)arr[0]:0);
		    	  messagebean.setTitle(arr[1]!=null?(String)arr[1]:"");
		    	  messagebean.setMsgBody(arr[2]!=null?(String)arr[2]:"");
		    	  messagebean.setNotes(arr[3]!=null?(String)arr[3]:"");
		    	  if(arr[4]!=null)
		    	  {
		    		  contBean=  serviceContent.getById((Integer)arr[4]);
		    		  messagebean.setMsg_type(contBean.getNameAr());
		    	  }
		    	  
		    	  if(arr[5]!=null)
		    	  {
		    		  fromuser=  userService.getById((Integer)arr[5]);
		    		  messagebean.setFromUser(fromuser);
		    		  messagebean.setFrom_username(fromuser.getUsername());
		    	  }
		    	  if(arr[6]!=null)
		    	  {
		    		  touser=  userService.getById((Integer)arr[6]);
		    		  messagebean.setToUser(touser);
		    		  messagebean.setTo_username(touser.getUsername());
		    	  }
		    	  
		    	  if(arr[8]!=null)
		    	  {
		    		   messagebean.setSpecialistName(arr[8].toString());
		    		 
		    	  }
		    	  
		    	  returned.add(messagebean);
		    	  
		    	  
		    	  System.out.println("ent   is --- "+arr[0]);
		    	  
		         
	 }
		      return returned;
	 
	 }
	 
	 
	 
	 

	 
	 
	 public String getLanguage(int language_int)
	 {
		 String language = ";";
		 
		 switch (language_int)
		{
			case 1 : language= "Akan;Akan"; break;
			case 2 : language= "Amharic;Amharic"; break;
			case 3 : language= "Arabic;عربى"; break;
			case 4 : language= "Assamese;Assamese"; break;
			case 5 : language= "Awadhi;Awadhi"; break;
			case 6 : language= "Azerbaijani;Azerbaijani"; break;
			case 7 : language= "Balochi;Balochi"; break;
			case 8 : language= "Belarusian;Belarusian"; break;
			case 9 : language= "Bengali;Bengali"; break;
			case 10 : language= "Bhojpuri;Bhojpuri"; break;
			case 11 : language= "Burmese;Burmese"; break;
			case 12 : language= "Cebuano (Visayan);Cebuano (Visayan)"; break;
			case 13 : language= "Chewa;Chewa"; break;
			case 14 : language= "Chhattisgarhi;Chhattisgarhi"; break;
			case 15 : language= "Chittagonian;Chittagonian"; break;
			case 16 : language= "Czech;Czech"; break;
			case 17 : language= "Deccan;Deccan"; break;
			case 18 : language= "Dhundhari;Dhundhari"; break;
			case 19 : language= "Dutch;Dutch"; break;
			case 20 : language= "Eastern Min;Eastern Min"; break;
			case 21 : language= "English;انجليزى"; break;
			case 22 : language= "French;فرنساوى"; break;
			case 23 : language= "Fula;Fula"; break;
			case 24 : language= "Gan Chinese;Gan Chinese"; break;
			case 25 : language= "German;German"; break;
			case 26 : language= "Greek;Greek"; break;
			case 27 : language= "Gujarati;Gujarati"; break;
			case 28 : language= "Haitian Creole;Haitian Creole"; break;
			case 29 : language= "Hakka;Hakka"; break;
			case 30 : language= "Haryanvi;Haryanvi"; break;
			case 31 : language= "Hausa;Hausa"; break;
			case 32 : language= "Hiligaynon;Hiligaynon"; break;
			case 33 : language= "Hindi;Hindi"; break;
			case 34 : language= "Hmong;Hmong"; break;
			case 35 : language= "Hungarian;Hungarian"; break;
			case 36 : language= "Igbo;Igbo"; break;
			case 37 : language= "Ilocano;Ilocano"; break;
			case 38 : language= "Italian;Italian"; break;
			case 39 : language= "Japanese;Japanese"; break;
			case 40 : language= "Javanese;Javanese"; break;
			case 41 : language= "Jin;Jin"; break;
			case 42 : language= "Kannada;Kannada"; break;
			case 43 : language= "Kazakh;Kazakh"; break;
			case 44 : language= "Khmer;Khmer"; break;
			case 45 : language= "Kinyarwanda;Kinyarwanda"; break;
			case 46 : language= "Kirundi;Kirundi"; break;
			case 47 : language= "Konkani;Konkani"; break;
			case 48 : language= "Korean;Korean"; break;
			case 49 : language= "Kurdish;Kurdish"; break;
			case 50 : language= "Madurese;Madurese"; break;
			case 51 : language= "Magahi;Magahi"; break;
			case 52 : language= "Maithili;Maithili"; break;
			case 53 : language= "Malagasy;Malagasy"; break;
			case 54 : language= "Malay/Indonesian;Malay/Indonesian"; break;
			case 55 : language= "Malayalam;Malayalam"; break;
			case 56 : language= "Mandarin;Mandarin"; break;
			case 57 : language= "Marathi;Marathi"; break;
			case 58 : language= "Marwari;Marwari"; break;
			case 59 : language= "Mossi;Mossi"; break;
			case 60 : language= "Nepali;Nepali"; break;
			case 61 : language= "Northern Min;Northern Min"; break;
			case 62 : language= "Odia (Oriya);Odia (Oriya)"; break;
			case 63 : language= "Oromo;Oromo"; break;
			case 64 : language= "Pashto;Pashto"; break;
			case 65 : language= "Persian;Persian"; break;
			case 66 : language= "Polish;Polish"; break;
			case 67 : language= "Portuguese;Portuguese"; break;
			case 68 : language= "Punjabi;Punjabi"; break;
			case 69 : language= "Quechua;Quechua"; break;
			case 70 : language= "Romanian;Romanian"; break;
			case 71 : language= "Russian;Russian"; break;
			case 72 : language= "Saraiki;Saraiki"; break;
			case 73 : language= "Serbo-Croatian;Serbo-Croatian"; break;
			case 74 : language= "Shona;Shona"; break;
			case 75 : language= "Sindhi;Sindhi"; break;
			case 76 : language= "Sinhalese;Sinhalese"; break;
			case 77 : language= "Somali;Somali"; break;
			case 78 : language= "Southern Min;Southern Min"; break;
			case 79 : language= "Spanish;Spanish"; break;
			case 80 : language= "Sundanese;Sundanese"; break;
			case 81 : language= "Swedish;Swedish"; break;
			case 82 : language= "Sylheti;Sylheti"; break;
			case 83 : language= "Tagalog;Tagalog"; break;
			case 84 : language= "Tamil;Tamil"; break;
			case 85 : language= "Telugu;Telugu"; break;
			case 86 : language= "Thai;Thai"; break;
			case 87 : language= "Turkish;Turkish"; break;
			case 88 : language= "Turkmen;Turkmen"; break;
			case 89 : language= "Ukrainian;Ukrainian"; break;
			case 90 : language= "Urdu;Urdu"; break;
			case 91 : language= "Uyghur;Uyghur"; break;
			case 92 : language= "Uzbek;Uzbek"; break;
			case 93 : language= "Vietnamese;Vietnamese"; break;
			case 94 : language= "Wu (Shanghainese);Wu (Shanghainese)"; break;
			case 95 : language= "Xhosa;Xhosa"; break;
			case 96 : language= "Xiang (Hunnanese);Xiang (Hunnanese)"; break;
			case 97 : language= "Yoruba;Yoruba"; break;
			case 98 : language= "Yue (Cantonese);Yue (Cantonese)"; break;
			case 99 : language= "Zhuang;Zhuang"; break;
			case 100 : language= "Zulu;Zulu"; break;

			default:
				break;
		}
		 
		 return language;
	 }
	 
}
