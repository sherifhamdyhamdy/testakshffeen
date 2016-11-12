package com.sget.akshef.buisness;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sget.akshef.beans.AdviceCategoryBean;
import com.sget.akshef.beans.ContentAdviceBean;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.dao.CommentsDAO;
import com.sget.akshef.hibernate.dao.ContentCategoryDAO;
import com.sget.akshef.hibernate.dao.ContentDetailsDAO;
import com.sget.akshef.hibernate.dao.SpecialistDAO;
import com.sget.akshef.hibernate.dao.UserRateBranchDAO;
import com.sget.akshef.hibernate.dao.UsersDAO;
import com.sget.akshef.hibernate.entities.BranchEntity;
import com.sget.akshef.hibernate.entities.CommentsEntity;
import com.sget.akshef.hibernate.entities.ContentCategoryEntity;
import com.sget.akshef.hibernate.entities.ContentDetailsEntity;
import com.sget.akshef.hibernate.entities.SpecialistEntity;
import com.sget.akshef.hibernate.entities.UserRateBranchEntity;
import com.sget.akshef.hibernate.entities.UserRateContentEntity;
import com.sget.akshef.hibernate.entities.UsersEntity;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Vip
 */
public class AdvicesFactory {
     ContentCategoryDAO  catDao;
     ContentDetailsDAO cotentDao;
     CommentsDAO commentsDAO;
    
    public List<AdviceCategoryBean>  getAllAdvicesCategory()
    {
       List<AdviceCategoryBean> catBeanList=new ArrayList<AdviceCategoryBean>();
       AdviceCategoryBean bean;
    if(catDao==null)     
    {
       catDao=new  ContentCategoryDAO();
      List<ContentCategoryEntity> categories= catDao.getAllByContentType("Tips");
      for(ContentCategoryEntity ent:categories)
      {
          bean=new AdviceCategoryBean();
          bean.setName_ar(ent.getNameAr());
          bean.setName_en(ent.getNameEn());
          bean.setId(ent.getId());
          catBeanList.add(bean);
      }
      
    }
   
  
        
     return catBeanList;  
    }
    
  
    public List<ContentAdviceBean>  getContentsAdvicesById(int catAdvID,String name,boolean sorting)
    {
    	//name=convert(name);
       List<ContentAdviceBean> catBeanList=new ArrayList<ContentAdviceBean>();
       ContentAdviceBean bean;
    if(cotentDao==null)     
    {
    	cotentDao=new  ContentDetailsDAO();
      List<ContentDetailsEntity> categories= cotentDao.getAllDetailsByCatId(catAdvID,name,sorting);
  if(categories!=null)
      for(ContentDetailsEntity ent:categories)
      {
          bean=new ContentAdviceBean();
          if(ent.getContentAr().length()>20)
          {
        	  bean.setSmall_name_ar(ent.getContentAr().substring(0, 20)); 
          }
          else
          {
        	  bean.setSmall_name_ar(ent.getContentAr()); 
          }
          
          bean.setName_ar(ent.getContentAr());
          bean.setName_en(ent.getContentEn());
          bean.setSmall_image(ent.getSmallImage());
          bean.setLarge_image(ent.getLargeImage());
          bean.setTitleAr(ent.getTitleAr());
          bean.setTitleEn(ent.getTitleEn());
          bean.setLarge_image(ent.getLargeImage());
          bean.setId(ent.getId());
          bean.setRating(ent.getRating());
          catBeanList.add(bean);
      }
      
    }
   
  
        
     return catBeanList;  
    }
    
    
    
    
////    public List<CommentsBean>  getCommentsByContentId(int contentId)
////    {
//////       List<CommentsBean> commentsBeanList=new ArrayList<CommentsBean>();
//////       CommentsBean bean;
//////   
//////    	commentsDAO=new  CommentsDAO();
//////      List<Object> comments= commentsDAO.getAllCommentsByContent(contentId);
//////     
//////      
//////      for(Object ent:comments)
//////      {
//////    	  Object[] arr=(Object[])ent;
//////    	  
//////    	  
//////    	  
//////          bean=new CommentsBean();
//////          CommentsEntity comment=(CommentsEntity)arr[0];
//////          UsersEntity users=(UsersEntity)arr[1];
//////          bean.setId(comment.getId());
//////          bean.setComment(comment.getComment());
//////          bean.setUsers_id(users.getId());
//////          bean.setUsers_name(users.getNameAr());
//////          
//////         
//////         //  System.out.println("commentsBeanList "+ent.toString());
//////          commentsBeanList.add(bean);
//////     
////      
////      
////    }
//   
//  
//        
//     return commentsBeanList;  
//    }
    
    
    
    public int  saveRating(int contentDetId,int userId,int rating)
    {
   	boolean isRatingSaved=true;
    	UserRateBranchDAO  ratDao=new UserRateBranchDAO();
    	int totalRate=0;
    	String sequence="";
    	Date date=new Date();
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(date);
    	int month = cal.get(Calendar.MONTH)+1;
    	int year=cal.get(Calendar.YEAR);
    	sequence=month+"_"+year+"_"+"1";
    	ContentDetailsDAO contentDetailsDAO=new ContentDetailsDAO();
    	UsersDAO userDao=new UsersDAO();
    	ContentDetailsEntity contentEnt=contentDetailsDAO.getById(contentDetId);
    	UsersEntity userEntity=userDao.getById(userId);
    	List<Object> listRat=ratDao.checkIfUserRateContent(contentDetId, userId);
    	if(listRat==null || listRat.isEmpty())
    	{
    	UserRateContentEntity entity=new UserRateContentEntity();
    	entity.setContent(contentEnt);
    	entity.setUsers(userEntity);
    	entity.setRate(rating);
    	entity.setRating_date(sequence);
    	ratDao.insertContentRate(entity);
    	}
    	else 
    	{
    		Object[] arr=(Object[])listRat.get(0);
    		int id=(Integer) arr[0];
    		UserRateContentEntity entity=ratDao.getContentById(id);
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
    		ratDao.updatecontent(entity);
        	
    		
    	}
    	List<Object> list=ratDao.getRatingToBesavedContent(contentDetId);
    	if(list!=null &&! list.isEmpty())
    	{
    	Object[] arr=	(Object[])list.get(0);
    	Number rate=(Number) arr[0];
    	Number count=(Number) arr[1];
    	 totalRate=Math.round(rate.floatValue()/count.floatValue());
    	 contentEnt.setRating(totalRate);
    	 contentDetailsDAO.update(contentEnt);
    		
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
    
    
    public boolean addCommentsContent(String comment,int userId,int contentDetailsId)
    
    {
    boolean isInserted=false;
    if(commentsDAO==null)
    	commentsDAO=new CommentsDAO();
    CommentsEntity entity=new CommentsEntity();
    UsersEntity user=new UsersDAO().getById(userId);
    ContentDetailsEntity contentDet=new ContentDetailsDAO().getById(contentDetailsId);
    entity.setUsers(user);
    entity.setComment(comment);
    entity.setContentDetails(contentDet);
    commentsDAO.insert(entity);
    
    
    
    return isInserted;
    }
    
public boolean addCommentsDoctor(String comment,int userId,int doctorId)
    
    {
    boolean isInserted=false;
    if(commentsDAO==null)
    	commentsDAO=new CommentsDAO();
    CommentsEntity entity=new CommentsEntity();
    UsersEntity user=new UsersDAO().getById(userId);
    SpecialistEntity specialist=new SpecialistDAO().getById(doctorId);
    entity.setUsers(user);
    entity.setComment(comment);
    entity.setSpecialist(specialist);
    commentsDAO.insert(entity);
    
    
    
    return isInserted;
    }
    
public boolean addCommentsBranch(String comment,int userId,int branchId)

{
boolean isInserted=false;
if(commentsDAO==null)
	commentsDAO=new CommentsDAO();
CommentsEntity entity=new CommentsEntity();
UsersEntity user=new UsersDAO().getById(userId);
BranchEntity branch=new BranchDAO().getById(branchId);
entity.setUsers(user);
entity.setComment(comment);
entity.setBranch(branch);
//entity.setComment_date(Calendar.getInstance().getTimeInMillis());
commentsDAO.insert(entity);



return isInserted;
} 
    
    
    
    
}
