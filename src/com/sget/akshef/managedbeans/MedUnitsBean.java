/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sget.akshef.managedbeans;



import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpSession;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RateEvent;

import com.sget.akshef.beans.AdviceCategoryBean;
import com.sget.akshef.beans.ContentAdviceBean;
import com.sget.akshef.beans.ContentArticlesBean;
import com.sget.akshef.beans.ContentNewsBean;
import com.sget.akshef.buisness.AdvicesFactory;
import com.sget.akshef.buisness.UnitsFactory;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.CategoryService;
import com.sget.akshef.hibernate.service.CommentsService;
import com.sget.akshef.hibernate.service.CompanyService;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.MobileService;
import com.sget.akshef.utils.RatingComparator;
import com.sget.akshf.searchcriteria.BranchesCriteria;
import org.primefaces.component.datascroller.DataScroller;
 

public class MedUnitsBean implements Serializable{
    
    
    private boolean sectionTableAppear;
    private boolean userCompany;
    List<CategoryBean> catBean;
    List<BranchBean> branchBean;
    List<SubcategoryBean> subCategoryBean;
    CategoryService catService=new CategoryService();
    BranchService branchService=new BranchService();
    UsersBean user;
    int categoryId;
    int sectionId;
    int subCategory;
    Integer rating;
    int ratingToBeSaved;
    int branchId;
    String message="";
    String profileSetting;
    boolean sorting;
    private String searchKeyword;
    String lat="0.0";
    String lng="0.0";
    BranchesCriteria branchCriteria = new BranchesCriteria();

    public List<CategoryBean> getCatBean() {
        return catBean;
    }

    public void setCatBean(List<CategoryBean> catBean) {
        this.catBean = catBean;
    }
    
      /** Creates a new instance of MedLoginBean */
   
   
	    
	   public String loadingAddressText()
	   {
		   HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			
			
			String address;
			
			if(session.getAttribute("address")!=null && !session.getAttribute("address").equals(""))
			{
				address= session.getAttribute("address").toString();
			}
			else
			{
				address="";
			}
			  ((org.primefaces.component.inputtext.InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent(":addressForm:addressText")).setValue(address);
				  
			   return"";
	   }
	    
	    
	    
    
    public MedUnitsBean() {
    	
    	String address;
    	if(session.getAttribute("address")!=null && !session.getAttribute("address").toString().equals(""))
    	{
    		 address= session.getAttribute("address").toString();
    	}
    	else
    	{
    		address="";
    	}
    	//set the value of the text address
 	    ((org.primefaces.component.inputtext.InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent(":addressForm:addressText")).setValue(address);
 	   
    	
    	user=	(UsersBean) session.getAttribute("userBean");
    	
    	pobulateCategoryAndDefaultBranches();
    }
    
    HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    UnitsFactory unitfact=new UnitsFactory();
    CompanyService service=new CompanyService();
    CategoryBean categoryBean=new CategoryBean();
 public String pobulateCategoryAndDefaultBranches()
 {
	 //get the categories
    if(user!=null &&user.isUserCompany())
    {
    	
    	catBean=catService.getAllByUserId(user.getId());
    	
    }
    else
    {
    	catBean=catService.getAll();
    }
    categoryBean=catBean!=null && !catBean.isEmpty()?catBean.get(0):null;
    //get all the branches
    
    lat=session.getAttribute("lat")!=null&&!session.getAttribute("lat").equals("")?session.getAttribute("lat").toString():"0.0";
	 lng=session.getAttribute("lng")!=null&&!session.getAttribute("lng").equals("")?session.getAttribute("lng").toString():"0.0";
	 if(lat ==null || lng==null || lat.equals("0.0")|| lng.equals("0.0"))
	 {
		  return "";
	 }
   //get the default branches
   BranchesCriteria branchCriteria = new BranchesCriteria();
	// Mandatory Parameters
	branchCriteria.setLatitude(Double.parseDouble(lat));
	branchCriteria.setLongitude(Double.parseDouble(lng));
	branchCriteria.setStart(0);
	branchCriteria.setLimit(500);
	// Optional Parameters

	

	//New For InsuranceCompany

	
   
   if(user!=null &&user.isUserCompany())
   {
  // branchBean=branchService.getBranchsByLatLngById(lat!=null&&!lat.equals("")?Double.parseDouble(lat):0.0,lng!=null&&!lng.equals("")?Double.parseDouble(lng):0.0, filters, user.getId());
   }else{

 branchBean = branchService.getBranchesForWeb(branchCriteria);
   }  
   subCategoryBean=categoryBean.getSubCategories();
  
   
   
   return "";
    
    
 }
 List<BranchBean> branchBeanOld;
 List<BranchBean> branchBeanOldSearching;
 private String commentInserted;
 
 CommentsService commentServ=new CommentsService();
 
 public String addComments()
 {
	
	CommentsBean isinserted=commentServ.addBranchComment(branchId, user.getId(), commentInserted);
	//System.out.println("isinserted    --- "+isinserted.getComment());
	commentInserted="";
   return "";
   
 }
 
 public String loadingBranchsBySorting()
 {
	 
	
	 if(!sorting)
	 {
	 sorting=true;
	 branchBeanOld=new ArrayList<BranchBean>();
	 branchBeanOld.addAll(branchBean);
	 
	 Collections.sort(branchBean, new RatingComparator());
	 }
	 else if(sorting)
	 {
		 sorting=false;
		 branchBean=new ArrayList<BranchBean>();
		 branchBean.addAll(branchBeanOld);
		 
		 
	 }
	
	
	 
	 
	 
	
   

   return "";
   
 }
 
 
 public String searchByNameInBranch()
 {
	 
	 String name;
	 boolean isFound=false;
	 List<BranchBean> branchsFound=new ArrayList<BranchBean>();
	 if(searchKeyword==null || searchKeyword.equals(""))
	 {
		if(branchBeanOldSearching!=null && !branchBeanOldSearching.isEmpty()) 
		{
			branchBean=new ArrayList<BranchBean>();
			branchBean.addAll(branchBeanOldSearching);
			
		}
		 return "";
	 }
		
	for(BranchBean bean:branchBean)
	{
		 name=bean.getNameAr();
		 if(name.contains(searchKeyword))
		 {
			 isFound=true;
			 branchsFound.add(bean);
		 }
	}
	 
	 if(isFound)
	 {
		 branchBeanOldSearching=new  ArrayList<BranchBean>();		 
		 branchBeanOldSearching.addAll(branchBean); 
		 
		 branchBean=new ArrayList<BranchBean>();
		 branchBean.addAll(branchsFound); 
				 
	 }
	 else
	 {
		 branchBean=new ArrayList<BranchBean>();
	 }
	
	
	
   

   return "";
   
 }
 
 private String address="";
 
 
 public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public String loadingBranchByCat()
 {
	
	 lat=session.getAttribute("lat")!=null&&!session.getAttribute("lat").equals("")?session.getAttribute("lat").toString():"0.0";
	 lng=session.getAttribute("lng")!=null&&!session.getAttribute("lng").equals("")?session.getAttribute("lng").toString():"0.0";
	 if(lat ==null || lng==null || lat.equals("0.0")|| lng.equals("0.0"))
	 {
		  return "";
	 }
   //get the default branches
    branchCriteria = new BranchesCriteria();
	// Mandatory Parameters
	branchCriteria.setLatitude(Double.parseDouble(lat));
	branchCriteria.setLongitude(Double.parseDouble(lng));
	branchCriteria.setStart(0);
	branchCriteria.setLimit(10);
	// Optional Parameters
	 categoryBean=((CategoryBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":rightForm:catDataTable"))).getRowData());
	 branchCriteria.setCat_id(categoryBean.getId());
	
	
	
   
 
   if(user!=null &&user.isUserCompany())
   {
  // branchBean=branchService.getBranchsByLatLngById(lat!=null&&!lat.equals("")?Double.parseDouble(lat):0.0,lng!=null&&!lng.equals("")?Double.parseDouble(lng):0.0, filters, user.getId());
   }else{

	branchBean=branchService.getBranchesForWeb(branchCriteria);
   }
//    subCategoryBean=unitFact.getAllSubCategories(categoryId);
//    for(SubcategoryBean bean:subCategoryBean)
//    {
//    	bean.setSections(unitFact.getAllSectionsBySubCategory(categoryId,bean.getId()));	
//    }
    subCategoryBean=categoryBean.getSubCategories();
   
   return "";
   
 }
 public String loadingUnitsBySection()
 {
	
	  sectionId=((SectionsBean)(((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":mainForm:subcategoryTable:sectionDatatable"))).getRowData())).getId();
   
	  lat=session.getAttribute("lat")!=null&&!session.getAttribute("lat").equals("")?session.getAttribute("lat").toString():"0.0";
		 lng=session.getAttribute("lng")!=null&&!session.getAttribute("lng").equals("")?session.getAttribute("lng").toString():"0.0";
		 if(lat ==null || lng==null || lat.equals("0.0")|| lng.equals("0.0"))
		 {
			  return "";
		 }
	   //get the default branches
	    branchCriteria = new BranchesCriteria();
		// Mandatory Parameters
		branchCriteria.setLatitude(Double.parseDouble(lat));
		branchCriteria.setLongitude(Double.parseDouble(lng));
		branchCriteria.setStart(0);
		branchCriteria.setLimit(10);
		// Optional Parameters
		 //categoryBean=((CategoryBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":rightForm:catDataTable"))).getRowData());
		if(categoryBean.getId()!=0)
		branchCriteria.setCat_id(categoryBean.getId());
		if(sectionId!=0)
		branchCriteria.setSection_id(sectionId);		
		 
   if( user!=null &&user.isUserCompany())
   {
	   //branchBean=branchService.getBranchsByLatLng(lat!=null&&!lat.equals("")?Double.parseDouble(lat):0.0,lng!=null&&!lng.equals("")?Double.parseDouble(lng):0.0, filters);
   }  else{

	branchBean=branchService.getBranchesForWeb(branchCriteria);
   }
   
   return "";
   
 }
 
 
 
 public String loadingsectionBySubCat()
 {
	 
	  subCategory=((CategoryBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":mainForm:subcategoryTable"))).getRowData()).getId();
	  
	//();
	 
    sectionTableAppear=true;
   
   
   return "";
   
 }

public UsersBean getUser() {
	return user;
}

public void setUser(UsersBean user) {
	this.user = user;
}

public List<BranchBean> getBranchBean() {
	return branchBean;
}

public void setBranchBean(List<BranchBean> branchBean) {
	this.branchBean = branchBean;
}

public List<SubcategoryBean> getSubCategoryBean() {
	return subCategoryBean;
}

public void setSubCategoryBean(List<SubcategoryBean> subCategoryBean) {
	this.subCategoryBean = subCategoryBean;
}

public boolean isSectionTableAppear() {
	return sectionTableAppear;
}

public void setSectionTableAppear(boolean sectionTableAppear) {
	this.sectionTableAppear = sectionTableAppear;
}

public int getCategoryId() {
	return categoryId;
}

public void setCategoryId(int categoryId) {
	this.categoryId = categoryId;
}

public int getSubCategory() {
	return subCategory;
}

public void setSubCategory(int subCategory) {
	this.subCategory = subCategory;
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

public String setTheBranchId()
{
	
	
	 message="";
	 RequestContext requestContext = RequestContext.getCurrentInstance();

if(user!=null && session.getAttribute("userBean")!=null )
{
	branchId=((BranchBean)((DataScroller)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":mainForm:branchDatatable"))).getRowData()).getId();
	
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
}

String messageFav;
public String   addToFavorites()
{
	 RequestContext requestContext = RequestContext.getCurrentInstance();
		
		if(user!=null && session.getAttribute("userBean")!=null )
		{
			
		 	MobileService mobileService=new MobileService();
		 	branchId=((BranchBean)((DataScroller)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":mainForm:branchDatatable"))).getRowData()).getId();
		 	boolean exist = mobileService.checkIfBranchAlreadyFav(branchId, user.getId());
		 	if(!exist){
		 		int result = mobileService.addBranchToFav(branchId,  user.getId());
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

//commentDialog




public String setTheBranchIdToComment()
{
	
	
	 message="";
	 RequestContext requestContext = RequestContext.getCurrentInstance();

if(user!=null && session.getAttribute("userBean")!=null )
{
	branchId=((BranchBean)((DataScroller)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":mainForm:branchDatatable"))).getRowData()).getId();
	
	requestContext.execute("PF('commentDialog').show()");
	requestContext.execute("PF('messageDialog').hide()");
	message="";
	return"";
}
else
{
	requestContext.execute("PF('commentDialog').hide()");
	requestContext.execute("PF('messageDialog').show()");
	message="يجب أن تسجل دخول أولا";
	return "";
	
}
}


CommentsService commentService=new CommentsService();

public String loadingBranchWithComents()
{
	 System.out.println("loadingBranchWithComents");
	 BranchBean branchBean=((BranchBean)((DataScroller)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":mainForm:branchDatatable"))).getRowData());
	HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	session.setAttribute("branchDetailsBean", branchBean);
	List<CommentsBean> commentsList=commentService.getAllBranchComments(branchBean.getId());
	session.setAttribute("commentsList", commentsList);
	
	String page = "/akshf_Details_Branch.xhtml";
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


public String  saveRating()
{
	int userId=user.getId();
	int curBranchId=getBranchId();
	int rating=ratingToBeSaved;
	int rate=branchService.saveRating(curBranchId, userId, rating);
	
	if(rate==-1)
	{
		message="لا يمكن الاضافة الان ,يمكنك الانتظار للشهر القادم";
	}
	else 
	{
		message="تم الاضافة";
	}
	
	
	
	for(BranchBean bean:branchBean)
	{
		if(bean.getId()==curBranchId)
		{
			if( rate!=-1)
			{
				bean.setRating(rate);
			}
			break;
		}
	}
	
	
	
	this.rating=0;
	
((org.primefaces.component.rating.Rating)( FacesContext.getCurrentInstance().getViewRoot().findComponent("mainForm:branchDatatable:ratingVal"))).setValue(rating);
	
	
	return"";
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

public int getBranchId() {
	return branchId;
}

public void setBranchId(int branchId) {
	this.branchId = branchId;
}

public int getSectionId() {
	return sectionId;
}

public void setSectionId(int sectionId) {
	this.sectionId = sectionId;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public boolean isSorting() {
	return sorting;
}

public void setSorting(boolean sorting) {
	this.sorting = sorting;
}


public List<BranchBean> getBranchBeanOld() {
	
	return branchBeanOld;
}

public void setBranchBeanOld(List<BranchBean> branchBeanOld) {
	this.branchBeanOld = branchBeanOld;
}

public String getSearchKeyword() {
	return searchKeyword;
}

public void setSearchKeyword(String searchKeyword) {
	this.searchKeyword = searchKeyword;
}

public List<BranchBean> getBranchBeanOldSearching() {
	return branchBeanOldSearching;
}

public void setBranchBeanOldSearching(List<BranchBean> branchBeanOldSearching) {
	this.branchBeanOldSearching = branchBeanOldSearching;
}

public String getProfileSetting() {
	return profileSetting;
}

public void setProfileSetting(String profileSetting) {
	this.profileSetting = profileSetting;
}

public String getLat() {
	return lat;
}

public void setLat(String lat) {
	this.lat = lat;
}

public String getLng() {
	return lng;
}

public void setLng(String lng) {
	this.lng = lng;
}

public boolean isUserCompany() {
	return userCompany;
}

public void setUserCompany(boolean userCompany) {
	this.userCompany = userCompany;
}

public String getCommentInserted() {
	return commentInserted;
}

public void setCommentInserted(String commentInserted) {
	this.commentInserted = commentInserted;
}

public String getMessageFav() {
	return messageFav;
}

public void setMessageFav(String messageFav) {
	this.messageFav = messageFav;
}

    
}
