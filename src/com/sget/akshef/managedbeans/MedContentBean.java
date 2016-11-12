/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sget.akshef.managedbeans;



import org.primefaces.component.dialog.*;
import org.primefaces.context.RequestContext;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import org.primefaces.event.RateEvent;

import com.sget.akshef.beans.ContentOffersBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.service.CommentsService;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.ContentForMobileService;
import com.sget.akshef.hibernate.service.MobileService;
import com.sun.jersey.api.core.HttpRequestContext;
 

public class MedContentBean implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<ContentCategoryBean> categories ;
	List<ContentDetailsBean> contentDetails ;
	ContentDetailsBean selectedContent;
    UsersBean user;
    boolean sorting;
    int categorySelected;
    private String name;
    private String commentInserted;
    Integer rating;
    int ratingToBeSaved;
    HttpSession session;
    int categoryType;
    HttpServletRequest   request=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	private ContentDetailsService service = new ContentDetailsService() ;
	private ContentForMobileService mobService = new ContentForMobileService() ;
	private CommentsService commentService = new CommentsService() ;
	private ContentDetailsBean contentDetailsBean ;
	private ContentDetailsBean relatedContentDetailsBean ;
	

    public ContentDetailsBean getRelatedContentDetailsBean() {
		return relatedContentDetailsBean;
	}


	public void setRelatedContentDetailsBean(ContentDetailsBean relatedContentDetailsBean) {
		this.relatedContentDetailsBean = relatedContentDetailsBean;
	}
	String message="";
      /** Creates a new instance of MedLoginBean */
    public MedContentBean() {
    	 session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	  user=	(UsersBean) session.getAttribute("userBean");
    	  ExternalContext context = FacesContext.getCurrentInstance()
    			  .getExternalContext();
    			  Map<String, String> params = context.getRequestParameterMap();
    			  String parameterOne = params.get("contentID");
    			  String id = params.get("ID");
    			  if(id!=null &&! id.equals("0")  &&! id.equals(""))
    			  {
    				  loadingAllData(Integer.parseInt(id));  
    			  }



    			  System.out.println("parameterOne   -----     "+id);
    }
    

 public String submitForm()
{
	 System.out.println("submitForm");
	return "";
}
 public String pobulateCategory()
 {
	 
	    HttpServletRequest   request=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String path=request.getServletPath();
		
		if(path.equals("/akshf_tips.xhtml"))
		{
			categoryType=DBConstants.CONTENT_TYPE_TIPS;
		
		}
		else if(path.equals("/akshf_offres.xhtml"))
		{
			categoryType=DBConstants.CONTENT_TYPE_OFFERS;
		}
		else if(path.equals("/akshf_news.xhtml"))
			
		{
			categoryType=DBConstants.CONTENT_TYPE_NEWS;
		}
		else if(path.equals("/akshf_articles.xhtml"))
		{
			categoryType=DBConstants.CONTENT_TYPE_ARTICLE;
		}
	 
	 populateCategories(categoryType);
  

   
   return "";
    
    
 }
	private List<SelectItem> categoriesSelectItems = null ;
 
 private void populateCategories(int contType){
		categoriesSelectItems = new ArrayList<SelectItem>();
		
		
		if(contType == DBConstants.CONTENT_TYPE_NEWS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_NEWS,"");
		}else if(contType == DBConstants.CONTENT_TYPE_OFFERS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_OFFERS,"");
		}else if(contType == DBConstants.CONTENT_TYPE_TIPS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_TIPS,"");
		}else if(contType == DBConstants.CONTENT_TYPE_ARTICLE){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_ARTICLE,"");
		}else{
			categories = null ;
		}
		
		if(categories == null || categories.size() == 0){
		//	RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,
				//	" No Categories !!", " No Categories !! <br>"));
		}else{
			// Clear Old Items
			if(categoriesSelectItems != null)
				categoriesSelectItems.clear();
			else
				categoriesSelectItems = new ArrayList<SelectItem>();
			
			categoriesSelectItems.add(new SelectItem("", "Select"));
			SelectItem item = null ;
			for(ContentCategoryBean bean : categories){
				item = new SelectItem();
				item.setLabel(bean.getNameAr());
				item.setValue(bean.getId());
				
				
				categoriesSelectItems.add(item);
						}
			
			categorySelected=categories.get(0).getId();
			
			contentDetails=  service.getContentDetails(categorySelected, name, 0, 1000, sorting);
		}
	}
 
 public String addToFavorites()
 {
	 
		message="";
		 RequestContext requestContext = RequestContext.getCurrentInstance();
	
	if(user!=null && session.getAttribute("userBean")!=null )
	{
		
	 	MobileService mobileService=new MobileService();
	 	selectedContent  =(ContentDetailsBean)session.getAttribute("contentDetailsBean");
	 	
	 	boolean exist = mobileService.checkIfContentAlreadyFav(contentDetailsBean.getId(), user.getId());
	 	if(!exist){
	 		int result = mobileService.addContentToFav(contentDetailsBean.getId(),  user.getId());
	 		if(result>0)
	 		{
	 			requestContext.execute("PF('messageDialog').show()");
	 			message="تم الاضافه بنجاح";
	 		}
	 }
	 	else
	 	{
	 		requestContext.execute("PF('messageDialog').show()");
	 		message="هذه موجودة من قبل";
	 	}
		return "";
		
	}
	else
	{
		message="يجب أن تسجل دخول أولا";
		requestContext.execute("PF('messageDialog').show()");
		
		return "";
	}
	 
 }
 
 String messageFav;
 

 public String loadingContentByCat(ActionEvent evt)
 {
String id=	 ((org.primefaces.component.commandbutton.CommandButton)evt.getSource()).getValue().toString();



	if(id!=null && !id.equals(""))
	{
		categorySelected=Integer.parseInt(id);	contentDetails=  service.getContentDetails(categorySelected, name, 0, 1000, sorting);
	}
	

   return "";
   
 }
 
 public void loadDataFromBtn(ActionEvent evt)
 {
	String value=((org.primefaces.component.commandbutton.CommandButton)evt.getSource()).getValue().toString();
	System.out.println("loadDataFromBtn  "+value);
 }
 
 
 
 public String loadingContentBySorting(ActionEvent evt)
 {
	 if(!sorting)
	 sorting=true;
	 else if(sorting)
	sorting=false;
	 if(categorySelected!=0)
		{
			contentDetails=  service.getContentDetails(categorySelected, name, 0, 1000, sorting);
		}
	
   

   return "";
   
 }
 
 
 public String loadingContentDetailsWithComentsByRelated()
 {
	
	 contentDetailsBean=((ContentDetailsBean)((org.primefaces.component.datagrid.DataGrid)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":dataForm:relatedTopicBeansDataTable"))).getRowData());
	HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	session.setAttribute("contentDetailsBean", contentDetailsBean);

	
	String page = "/akshf_contentDetails.xhtml?ID="+contentDetailsBean.getId();
	ExternalContext context = FacesContext.getCurrentInstance()
			.getExternalContext();
	
	List<CommentsBean> commentsBeans =commentService.getAllContentComments(contentDetailsBean.getId());
	 List<ContentDetailsBean> relatedTopicBeans=new ContentDetailsService().getRelatedTopics(contentDetailsBean.getTitleAr());
	 contentDetailsBean.setRelatedTopics(relatedTopicBeans);
	contentDetailsBean  =(ContentDetailsBean)session.getAttribute("contentDetailsBean");
	
	
	contentDetailsBean.setCommentsBeans(commentsBeans);
	
	
	try {
		context.redirect(context.getRequestContextPath() + page);
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
   return "";
   
 }
 
 
 
 
 public String loadingContentDetailsWithComents()
 {
	
	contentDetailsBean=((ContentDetailsBean)((org.primefaces.component.datagrid.DataGrid)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:contentDataTable"))).getRowData());
	

	
	String page = "/akshf_contentDetails.xhtml?ID="+contentDetailsBean.getId();
	ExternalContext context = FacesContext.getCurrentInstance()
			.getExternalContext();

	
	
	try {
		context.redirect(context.getRequestContextPath() + page);
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
   return "";
   
 }
 
public void loadingAllData(int id)
{
	
	HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	contentDetailsBean=service.getById(id);
	session.setAttribute("contentDetailsBean", contentDetailsBean);
	
	
	List<CommentsBean> commentsBeans =commentService.getAllContentComments(contentDetailsBean.getId());
	 List<ContentDetailsBean> relatedTopicBeans=new ContentDetailsService().getRelatedTopics(contentDetailsBean.getTitleAr());
	 contentDetailsBean.setRelatedTopics(relatedTopicBeans);
	contentDetailsBean  =(ContentDetailsBean)session.getAttribute("contentDetailsBean");

	contentDetailsBean.setCommentsBeans(commentsBeans);
	
	
	
}
 
 
 
 
 
 public String loadingContentDetailsWithComentsWhenStart()
 {
	 ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	 HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		contentDetailsBean  =(ContentDetailsBean)session.getAttribute("contentDetailsBean");
	 return "";
 }

 
 
 
 public String addComments()
 {
	CommentsBean CommentsBean=commentService.addContentComment(contentDetailsBean.getId(), user.getId(), commentInserted);
	contentDetailsBean  =(ContentDetailsBean)session.getAttribute("contentDetailsBean");
	contentDetailsBean.getCommentsBeans().add(CommentsBean);
     session.setAttribute("contentDetailsBean",contentDetailsBean);
	RequestContext requestContext = RequestContext.getCurrentInstance();
	requestContext.execute("PF('commentDialog').hide()");
	
   return "";
   
 }
 
 public String setTheContentId()
 {
	
	 
	
		message="";
		 RequestContext requestContext = RequestContext.getCurrentInstance();
	
	if(user!=null && session.getAttribute("userBean")!=null )
	{
	 	contentDetailsBean  =(ContentDetailsBean)session.getAttribute("contentDetailsBean");
		requestContext.execute("PF('ratingDialog').show()");
		requestContext.execute("PF('messageDialog').hide()");
		message="";
		return"";
	}
	else
	{
		requestContext.execute("PF('ratingDialog').hide()");
		requestContext.execute("PF('messageDialog').show()");
		message="يجب أن تسجل دخول أولا";
		return "";
		
	}
//	
	 
 	
 
 }
 
 
 public String setSelectedContentAction()
 {
	 message="";

	 RequestContext requestContext = RequestContext.getCurrentInstance();
	 
				if(user!=null && session.getAttribute("userBean")!=null )
				{
					contentDetailsBean  =(ContentDetailsBean)session.getAttribute("contentDetailsBean");
					requestContext.execute("PF('commentDialog').show()");
					requestContext.execute("PF('messageDialog').hide()");
					message="";
					return"";
				}
				else
				{
					requestContext.execute("PF('ratingDialog').hide()");
					requestContext.execute("PF('messageDialog').show()");
					message="يجب أن تسجل دخول أولا";
					return"";
					
				}
	 
	 
 
 	
 }
 
 
 
 



public UsersBean getUser() {
	return user;
}



public void setUser(UsersBean user) {
	this.user = user;
}







public String getName() {
	return name;
}



public void setName(String name) {
	this.name = name;
}



public boolean isSorting() {
	return sorting;
}



public void setSorting(boolean sorting) {
	this.sorting = sorting;
}



public String getCommentInserted() {
	return commentInserted;
}



public void setCommentInserted(String commentInserted) {
	this.commentInserted = commentInserted;
}



public Integer getRating() {
	return rating;
}



public void setRating(Integer rating) {
	this.rating = rating;
}



public int getRatingToBeSaved() {
	return ratingToBeSaved;
}



public void setRatingToBeSaved(int ratingToBeSaved) {
	this.ratingToBeSaved = ratingToBeSaved;
}


public String  saveRating()
{
	int userId=user.getId();
	int curContentDetId=contentDetailsBean.getId();
	int rating=ratingToBeSaved;
	 System.out.println("user id  "+userId +"  curBranchId "+curContentDetId+" rating  "+rating);
	int rate=service.saveRating(curContentDetId, userId, rating);
	
	if(rate==-1)
	{
		   message="لا يمكن الاضافة";
		   return "";
	}
	else 
	{
		message="تم الاضافة";
	}
	
	
	
//	for(ContentDetailsBean bean:contentDetails)
//	{
//		if(bean.getId()==curContentDetId)
//		{
//			if( rate!=-1)
//			{
//				bean.setRating(rate);
//			}
//			break;
//		}
//	}
	
	contentDetailsBean.setRating(rate);
	session.setAttribute("contentDetailsBean",contentDetailsBean);
	
	
	// System.out.println("is saved "+issaved);
	this.rating=0;
	

	
	
	// System.out.println("is saved "+issaved);
	this.rating=0;
	
	

	
	
	return"";
}






public static long getSerialversionuid() {
	return serialVersionUID;
}



public String getMessage() {
	return message;
}



public void setMessage(String message) {
	this.message = message;
}
 

public void onrate(RateEvent rateEvent) {
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + ((Integer) rateEvent.getRating()).intValue());
    ratingToBeSaved=((Integer) rateEvent.getRating()).intValue();
    FacesContext.getCurrentInstance().addMessage(null, message);
    this.rating=0;
}
 
public void oncancel() {
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
    FacesContext.getCurrentInstance().addMessage(null, message);
    this.rating=0;
    
}



//public List<ContentAdviceBean> getContentAdviceBeanstest() {
////	if(contentAdviceBeanstest==null)
////		contentAdviceBeanstest=new ArrayList<ContentAdviceBean>();
////	ContentAdviceBean ct=new ContentAdviceBean();
////	ct.setName_ar("ddd");
////	ct.setName_en("111");
////	ContentAdviceBean ct1=new ContentAdviceBean();
////	ct1.setName_ar("ddd1");
////	ct1.setName_en("1113");
////	contentAdviceBeanstest.add(ct);
////	contentAdviceBeanstest.add(ct1);
////	
//	//return contentAdviceBeanstest;
//}







public String getMessageFav() {
	return messageFav;
}



public void setMessageFav(String messageFav) {
	this.messageFav = messageFav;
}



public int getCategorySelected() {
	return categorySelected;
}



public void setCategorySelected(int categorySelected) {
	this.categorySelected = categorySelected;
}


public ContentDetailsBean getContentDetailsBean() {
	return contentDetailsBean;
}


public void setContentDetailsBean(ContentDetailsBean contentDetailsBean) {
	this.contentDetailsBean = contentDetailsBean;
}


public HttpServletRequest getRequest() {
	return request;
}


public void setRequest(HttpServletRequest request) {
	this.request = request;
}




public List<ContentCategoryBean> getCategories() {
	return categories;
}


public void setCategories(List<ContentCategoryBean> categories) {
	this.categories = categories;
}


public List<ContentDetailsBean> getContentDetails() {
	return contentDetails;
}


public void setContentDetails(List<ContentDetailsBean> contentDetails) {
	this.contentDetails = contentDetails;
}


public ContentDetailsBean getSelectedContent() {
	return selectedContent;
}


public void setSelectedContent(ContentDetailsBean selectedContent) {
	this.selectedContent = selectedContent;
}








    
}
