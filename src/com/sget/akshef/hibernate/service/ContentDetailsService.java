package com.sget.akshef.hibernate.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.akshffeen.utils.Utils;
import com.sget.akshef.beans.ContentOffersBean;
import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.ContentTypesBean;
import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.dao.ContentDetailsDAO;
import com.sget.akshef.hibernate.dao.ContentForMobileDAO;
import com.sget.akshef.hibernate.dao.SearchDAO;
import com.sget.akshef.hibernate.dao.UserRateBranchDAO;
import com.sget.akshef.hibernate.dao.UserRateContentDAO;
import com.sget.akshef.hibernate.dao.UsersDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.ContentCategoryEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.PriorityEntity;
import com.sget.akshef.hibernate.entities.UserRateBranchEntity;
import com.sget.akshef.hibernate.entities.UserRateContentEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;
import com.sget.akshf.mobile.api.AppConstants;

/**
 * 
 * @author JDeeb ContentDetails Service
 */
public class ContentDetailsService {
	ContentDetailsDAO dao = null;

	Utils utils = Utils.getInstance();
	
	public ContentDetailsService() {
		dao = new ContentDetailsDAO();
	}

	public void insert(ContentDetailsBean bean) {
		if (bean == null)
			return;
		ContentDetailsEntity entity = new ContentDetailsEntity();
		fillEntity(entity, bean);
		dao.insert(entity);
		bean.setId(entity.getId());
	}

	public boolean update(ContentDetailsBean bean) {
		if (bean == null || bean.getId() < 1)
			return false;

		ContentDetailsEntity entity = new ContentDetailsEntity();
		fillEntity(entity, bean);
		return dao.update(entity);

	}

	public boolean delete(ContentDetailsBean bean) {
		if (bean == null || bean.getId() < 1)
			return false;
		ContentDetailsEntity entity = new ContentDetailsEntity();
		fillEntity(entity, bean);
		return dao.delete(entity);

	}

	// Get By ID
	public ContentDetailsBean getById(int id) {
		if (id < 1)
			return null;
		ContentDetailsBean bean = new ContentDetailsBean();
		ContentDetailsEntity entity = dao.getById(id);
		fillBean(bean, entity);
		getContentRating(bean);
		return bean;
	}

	public ContentOffersBean getByIdOffer(int id) {
		if (id < 1)
			return null;
		ContentOffersBean bean = new ContentOffersBean();
		ContentDetailsEntity entity = dao.getById(id);
		fillBeanOffer(bean, entity);
		// getContentRating(bean);

		return bean;
	}
	
	
	public List<ContentDetailsBean> getContentDetails(int categoryId,String name,int start,int limit,boolean rating){
		List<ContentDetailsBean> beans = new ArrayList<ContentDetailsBean>();
		List<ContentDetailsEntity> entities = new ContentForMobileDAO().getAllDetailsByCatId(categoryId,name,rating,start,limit);
		if(entities != null && entities.size() > 0){
			ContentDetailsBean bean ;
			for(ContentDetailsEntity entity : entities){
				bean = new ContentDetailsBean();
			   fillBean(bean, entity);
				beans.add(bean);
			}
		}
		return beans;
	}
	
	public Map<String,List<ContentDetailsBean>> getNewFeeds(){
		
		Map<String,List<ContentDetailsBean>> results=new  HashMap<String, List<ContentDetailsBean>>();
		//news
		List<ContentDetailsBean> beansNews = new ArrayList<ContentDetailsBean>();
		List<ContentDetailsEntity> entitiesNews = new ContentDetailsDAO().getNewestNews();
		if(entitiesNews != null && entitiesNews.size() > 0){
			ContentDetailsBean bean ;
			for(ContentDetailsEntity entity : entitiesNews){
				bean = new ContentDetailsBean();
			   fillBean(bean, entity);
			   beansNews.add(bean);
			}
		}
	
		results.put("NEWS", beansNews);
		//tips
		
		List<ContentDetailsBean> beansTips = new ArrayList<ContentDetailsBean>();
		List<ContentDetailsEntity> entitiesTips = new ContentDetailsDAO().getMostRatedTips();
		if(entitiesTips != null && entitiesTips.size() > 0){
			ContentDetailsBean bean ;
			for(ContentDetailsEntity entity : entitiesTips){
				bean = new ContentDetailsBean();
			   fillBean(bean, entity);
			   beansTips.add(bean);
			}
		}
		results.put("TIPS", beansTips);	
		
		
		//Article
		
		List<ContentDetailsBean> beansArticle = new ArrayList<ContentDetailsBean>();
		List<ContentDetailsEntity> articlesEntities = new ContentDetailsDAO().getMostRatedArticles();
		if(articlesEntities != null && articlesEntities.size() > 0){
			ContentDetailsBean bean ;
			for(ContentDetailsEntity entity : articlesEntities){
				bean = new ContentDetailsBean();
			   fillBean(bean, entity);
			   beansArticle.add(bean);
			}
		}
		results.put("Article", beansArticle);
		
	//Offers
		
		List<ContentDetailsBean> beansOffers = new ArrayList<ContentDetailsBean>();
		List<ContentDetailsEntity> offersEntities = new ContentDetailsDAO().getMostRatedOffers();
		if(offersEntities != null && offersEntities.size() > 0){
			ContentDetailsBean bean ;
			for(ContentDetailsEntity entity : offersEntities){
				bean = new ContentDetailsBean();
			   fillBean(bean, entity);
			   beansOffers.add(bean);
			}
		}
		results.put("Offers", beansOffers);	
		
		return results;
		
	}
	
	

	public void fillBeanOffer(ContentOffersBean bean,
			ContentDetailsEntity entity) {
		if (entity == null)
			return;
		if (bean == null)
			bean = new ContentOffersBean();
		// Basics Data
		bean.setId(entity.getId());
		// bean.setActive(entity.getActive());
		// bean.setContentAr(entity.getContentAr());
		// bean.setContentEn(entity.getContentEn());
		// bean.setEndTime(entity.getEndTime());
		// bean.setStartTime(entity.getStartTime());
		// bean.setLargeImage(DBConstants.CONTENT_IMAGES_UPLOADS +
		// entity.getLargeImage());
		// bean.setSmallImage(DBConstants.CONTENT_IMAGES_UPLOADS +
		// entity.getSmallImage());
		bean.setTitleAr(entity.getTitleAr());
		bean.setTitleEn(entity.getTitleEn());
		bean.setName_ar(entity.getContentAr());
		bean.setName_en(entity.getContentEn());
		bean.setSmall_image(entity.getSmallImage());
		bean.setLarge_image(entity.getLargeImage());
		bean.setTitleAr(entity.getTitleAr());
		bean.setTitleEn(entity.getTitleEn());
		bean.setLarge_image(entity.getLargeImage());
		bean.setId(entity.getId());
		bean.setRating(entity.getRating());
		 if(entity.getContentAr().length()>100)
         {
       	  bean.setSmall_name_ar(entity.getContentAr().substring(0, 100)); 
         }
         else
         {
       	  bean.setSmall_name_ar(entity.getContentAr()); 
         }

		// if(bean.getUsers() == null)
		// bean.setUsers(new UsersBean());
		// bean.getUsers().setId(entity.getUsers().getId());
		// if(bean.getPriority() == null)
		// bean.setPriority(new PriorityBean());
		// bean.getPriority().setId(entity.getPriority().getId());
		// if(bean.getContentCategory() == null)
		// bean.setContentCategory(new ContentCategoryBean());
		// bean.getContentCategory().setId(entity.getContentCategory().getId());
	}

	// Get All
	public List<ContentDetailsBean> getAll() {
		List<ContentDetailsEntity> entities = dao.getAll();
		List<ContentDetailsBean> beans = new ArrayList<ContentDetailsBean>();
		ContentDetailsBean bean;
		if (entities != null && entities.size() > 0) {
			for (ContentDetailsEntity entity : entities) {
				bean = new ContentDetailsBean();
				fillBean(bean, entity);
				getContentRating(bean);
				beans.add(bean);
			}
		}
		return beans;
	}

	// Get Content Rating
	public void getContentRating(ContentDetailsBean bean) {
		if (bean != null && bean.getId() > 0) {
			List<UserRateContentEntity> lists = dao.getContentRating(bean
					.getId());
			if (lists != null && lists.size() > 0) {
				int rating = 0;
				int ratingno = 0;
				for (UserRateContentEntity ent : lists) {
					if (ent != null && ent.getRate() >= 0) {
						rating += ent.getRate();
						ratingno += 1;
					}
				}
				int avarge = (rating * 100 / (ratingno * AppConstants.APP_RATING_MAX));
				bean.setRating(avarge);
				bean.setRatingno(ratingno);
			}
		}
	}

	// JDeeb
	public void updateContentRating(int rating, int contentId) {
		dao.updateContentRating(rating, contentId);
	}

	// Fill Bean From Entity
	public void fillBean(ContentDetailsBean bean, ContentDetailsEntity entity) {
		if (entity == null)
			return;
		if (bean == null)
			bean = new ContentDetailsBean();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		// Basics Data
		bean.setId(entity.getId());
		bean.setActive(entity.getActive());
		bean.setTitleAr(entity.getTitleAr());
		bean.setTitleEn(entity.getTitleEn());
		bean.setContentAr(entity.getContentAr());
		bean.setContentEn(entity.getContentEn());
		bean.setEndTime(entity.getEndTime());
		bean.setStartTime(entity.getStartTime());

		 
		bean.setSponsored(utils.hasValue(entity.getSponsored()) && entity.getSponsored()==1?1:0);
		bean.setSponsored_from(entity.getSponsored_from());
		bean.setSponsored_to(entity.getSponsored_to());
		

		
		
		
		
		
		 if(entity.getContentAr().length()>100)
         {
       	  bean.setSmall_name_ar(entity.getContentAr().substring(0, 100)); 
         }
         else
         {
       	  bean.setSmall_name_ar(entity.getContentAr()); 
         }
		 
			// bean.setLargeImage(DBConstants.CONTENT_IMAGES_UPLOADS +
			// entity.getLargeImage());
			// bean.setSmallImage(DBConstants.CONTENT_IMAGES_UPLOADS +
			// entity.getSmallImage());
		
		bean.setLargeImage(entity.getLargeImage()!=null && !entity.getLargeImage().equals("")?DBConstants.CONTENT_IMAGES_UPLOADS + entity.getLargeImage():"img/content.jpg");
		bean.setSmallImage(entity.getSmallImage()!=null&& !entity.getSmallImage().equals("")?DBConstants.CONTENT_IMAGES_UPLOADS + entity.getSmallImage():"img/content.jpg");
//		bean.setLargeLogo(entity.getLargeLogo()!=null&& !entity.getLargeLogo().equals("") ?DBConstants.CONTENT_IMAGES_UPLOADS + entity.getLargeLogo():"img/content.jpg");
//		bean.setSmallLogo(entity.getSmallLogo()!=null&& !entity.getSmallLogo().equals("")?DBConstants.CONTENT_IMAGES_UPLOADS + entity.getSmallLogo():"img/content.jpg");
		bean.setRating(entity.getRating()!=null ?entity.getRating():0);
		if(entity.getUsers() != null){
			if (bean.getUsers() == null)
				bean.setUsers(new UsersBean());
			bean.getUsers().setId(entity.getUsers().getId());
		}
		if(entity.getPriority() != null){
			if (bean.getPriority() == null)
				bean.setPriority(new PriorityBean());
			bean.getPriority().setId(entity.getPriority().getId());
		}
		if(entity.getContentCategory() != null){
			if (bean.getContentCategory() == null)
				bean.setContentCategory(new ContentCategoryBean());
			bean.getContentCategory().setId(entity.getContentCategory().getId());
			bean.getContentCategory().setNameAr(entity.getContentCategory().getNameAr());
			bean.getContentCategory().setNameEn(entity.getContentCategory().getNameEn());
			if(entity.getContentCategory().getContentTypes() != null){
				if (bean.getContentCategory().getContentTypes() == null)
					bean.getContentCategory().setContentTypes(new ContentTypesBean());
				bean.getContentCategory().getContentTypes().setId(entity.getContentCategory().getContentTypes().getId());
				bean.getContentCategory().getContentTypes().setNameAr(entity.getContentCategory().getContentTypes().getNameAr());
				bean.getContentCategory().getContentTypes().setNameEn(entity.getContentCategory().getContentTypes().getNameEn());
			}
		}
	}

	// Fill Entity From Bean
	public void fillEntity(ContentDetailsEntity entity, ContentDetailsBean bean) {
		if (bean == null)
			return;
		if (entity == null)
			entity = new ContentDetailsEntity();
		// Basics Data
		entity.setId(bean.getId());
		entity.setActive(bean.getActive());
		entity.setTitleAr(bean.getTitleAr());
		entity.setTitleEn(bean.getTitleEn());
		entity.setContentAr(bean.getContentAr());
		entity.setContentEn(bean.getContentEn());
		entity.setEndTime(bean.getEndTime());
		entity.setStartTime(bean.getStartTime());
		
		entity.setLargeImage(bean.getLargeImage());
		entity.setSmallImage(bean.getSmallImage());
//		entity.setLargeLogo(bean.getLargeLogo());
//		entity.setSmallLogo(bean.getSmallLogo());
		
		entity.setSponsored(bean.getSponsored());
		entity.setSponsored_from(bean.getSponsored_from());
		entity.setSponsored_to(bean.getSponsored_to());
		
		if(bean.getUsers() != null){
			if (entity.getUsers() == null)
				entity.setUsers(new UsersEntity());
			entity.getUsers().setId(bean.getUsers().getId());
		}
		if(bean.getPriority() != null){
			if (entity.getPriority() == null)
				entity.setPriority(new PriorityEntity());
			entity.getPriority().setId(bean.getPriority().getId());
		}
		if(bean.getContentCategory() != null){
			if (entity.getContentCategory() == null)
				entity.setContentCategory(new ContentCategoryEntity());
			entity.getContentCategory().setId(bean.getContentCategory().getId());
		}
	}
	
	public List<ContentDetailsBean> getRelatedTopics(String keyword){
		
		List<ContentDetailsBean> beans=new ArrayList<ContentDetailsBean>();
		List<ContentDetailsEntity> lists=new SearchDAO().getRelatedTopics(keyword);
		
		ContentDetailsBean bean;
		for(ContentDetailsEntity entity:lists){
			bean=new ContentDetailsBean();
		     fillBean(bean, entity);
		     beans.add(bean);
		}
		return beans;
	}
	/**
 	 * JDeeb
 	 * get Content Details by Content Category and Branch
 	 */
	public List<ContentDetailsBean> getContentByBranchAndType(int contType, int branchId){
 		List<ContentDetailsBean> beans = null;
    	if(contType > 0 && branchId > 0){
    		beans = new ArrayList<ContentDetailsBean>();
    		List<ContentDetailsEntity> entities = dao.getContentByBranchAndType(contType, branchId);
    		ContentDetailsBean bean ;
        	if(entities != null && entities.size() > 0){
    	    	for(ContentDetailsEntity entity : entities){
    	    		bean = new ContentDetailsBean();
    	    		fillBean(bean, entity);
    	    		getContentRating(bean);
    	    		beans.add(bean);
    	    	}
        	}
    	}
    	
    	return beans;
 	}
	
	
	   
    public int  saveRating(int contentDetId,int userId,int rating)
    {
//   	boolean isRatingSaved=true;
//    	UserRateBranchDAO  ratDao=new UserRateBranchDAO();
//    	int totalRate=0;
//    	String sequence="";
//    	Date date=new Date();
//    	Calendar cal=Calendar.getInstance();
//    	cal.setTime(date);
//    	int month = cal.get(Calendar.MONTH)+1;
//    	int year=cal.get(Calendar.YEAR);
//    	sequence=month+"_"+year+"_"+"1";
//    	ContentDetailsDAO contentDetailsDAO=new ContentDetailsDAO();
//    	UsersDAO userDao=new UsersDAO();
//    	ContentDetailsEntity contentEnt=contentDetailsDAO.getById(contentDetId);
//    	UsersEntity userEntity=userDao.getById(userId);
//    	List<Object> listRat=ratDao.checkIfUserRateContent(contentDetId, userId);
//    	if(listRat==null || listRat.isEmpty())
//    	{
//    	UserRateContentEntity entity=new UserRateContentEntity();
//    	entity.setContent(contentEnt);
//    	entity.setUsers(userEntity);
//    	entity.setRate(rating);
//    	entity.setRating_date(sequence);
//    	ratDao.insertContentRate(entity);
//    	}
//    	else 
//    	{
//    		Object[] arr=(Object[])listRat.get(0);
//    		int id=(Integer) arr[0];
//    		UserRateContentEntity entity=ratDao.getContentById(id);
//    		String ratDate=entity.getRating_date();
//    		String a[]=ratDate.split("_");
//    		if(a!=null && a.length!=0)
//    		{
//    		int monthStr=Integer.parseInt(a[0]);	
//    		int yearStr=Integer.parseInt(a[1]);
//    		int serial=Integer.parseInt(a[2]);	
//    		if(monthStr<month && yearStr<=year)
//    		{
//    		  	sequence=month+"_"+year+"_"+"1";
//    		}
//    		else if(monthStr==month && yearStr<year)
//    		{
//    			sequence=month+"_"+year+"_"+"1";
//    		}
//    		
//    		
//    		
//    		else if(monthStr==month && yearStr==year &&serial==1)
//    		{
//    			sequence=month+"_"+year+"_"+"2";
//    		}
//    		
//    		else if(monthStr==month && yearStr==year &&serial==2)
//    		{
//    			return -1;
//    		}
//    			
//    		}
//    		entity.setRating_date(sequence);
//    		entity.setRate(rating);
//    		ratDao.updatecontent(entity);
//        	
//    		
//    	}
//    	List<Object> list=ratDao.getRatingToBesavedContent(contentDetId);
//    	if(list!=null &&! list.isEmpty())
//    	{
//    	Object[] arr=	(Object[])list.get(0);
//    	Number rate=(Number) arr[0];
//    	Number count=(Number) arr[1];
//    	 totalRate=Math.round(rate.floatValue()/count.floatValue());
//    	 contentEnt.setRating(totalRate);
//    	 contentDetailsDAO.update(contentEnt);
//    		
//    	}
    	
    	
    	boolean isRatingSaved=true;
    	UserRateContentDAO  ratDao=new UserRateContentDAO();
    	int totalRate=0;
    	String sequence="";
    	Date date=new Date();
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	int month = cal.get(Calendar.MONTH)+1;
    	int year=cal.get(Calendar.YEAR);
    	sequence=month+"_"+year+"_"+"1";
    	ContentDetailsDAO contentDao=new ContentDetailsDAO();
    	UsersDAO userDao=new UsersDAO();
    	ContentDetailsEntity contEnt=contentDao.getById(contentDetId);
    	UsersEntity userEntity=userDao.getById(userId);
    	List<UserRateContentEntity> listRat=ratDao.checkIfUserRateContent(contentDetId, userId);
    	if(listRat==null || listRat.isEmpty())
    	{
    		UserRateContentEntity entity=new UserRateContentEntity();
    	entity.setContent(contEnt);
    	entity.setUsers(userEntity);
    	entity.setRate(rating);
    	entity.setRating_date(sequence);
    	ratDao.insert(entity);
    	}
    	else 
    	{
    		UserRateContentEntity entity=(UserRateContentEntity)listRat.get(0);
    	
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
    	List<Object> list=ratDao.getRatingToBesaved(contentDetId);
    	if(list!=null &&! list.isEmpty())
    	{
    	Object[] arr=	(Object[])list.get(0);
    	Number rate=(Number) arr[0];
    	Number count=(Number) arr[1];
    	 totalRate=Math.round(rate.floatValue()/count.floatValue());
    	 contEnt.setRating(totalRate);
    	contentDao.update(contEnt);
    		
    	}
    	return totalRate;

    	
    	
    	
    	
    }
    
    
    
    	public static String convert(String str)
    	{
    		if(str==null || str.equals(""))
    			return str;
    		StringBuffer ostr = new StringBuffer();
    		for(int i=0; i<str.length(); i++) 
    		{
    			char ch = str.charAt(i);
    			if ((ch >= 0x0020) && (ch <= 0x007e))	// Does the char need to be converted to unicode? 
    			{
    				ostr.append(ch);					// No.
    			} else 									// Yes.
    			{
    	        	ostr.append("\\u") ;				// standard unicode format.
    				String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);	// Get hex value of the char. 
    				for(int j=0; j<4-hex.length(); j++)	// Prepend zeros because unicode requires 4 digits
    					ostr.append("0");
    				ostr.append(hex.toLowerCase());		// standard unicode format.
    				//ostr.append(hex.toLowerCase(Locale.ENGLISH));
    			}
    		}
    	return (new String(ostr));
    
    
    	}
    
    
}