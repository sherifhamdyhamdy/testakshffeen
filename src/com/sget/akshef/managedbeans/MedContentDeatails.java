/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sget.akshef.managedbeans;



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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RateEvent;

import com.akshffeen.utils.Utils;
import com.sget.akshef.beans.AdviceCategoryBean;
import com.sget.akshef.beans.ContentAdviceBean;
import com.sget.akshef.buisness.AdvicesFactory;
import com.sget.akshef.buisness.BranchFactory;
import com.sget.akshef.buisness.UnitsFactory;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sun.jersey.api.core.HttpRequestContext;
 

public class MedContentDeatails implements Serializable{
	private Utils utils = Utils.getInstance();

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List <AdviceCategoryBean> advicesBeans;
	private List<ContentAdviceBean> contentAdviceBeans;
	private List<ContentAdviceBean> contentAdviceBeanstest;
    UsersBean user;
    int advCatBeanId;
    boolean sorting;
    private String name;
    private String commentInserted;
    ContentAdviceBean adviceCategoryBean;
    Integer rating;
    int ratingToBeSaved;
    String message="";
      /** Creates a new instance of MedLoginBean */
    public MedContentDeatails() {
    	HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    	user=	(UsersBean) session.getAttribute("userBean");
    }
    


 public String pobulateCategory()
 {
     AdvicesFactory unitfact=new AdvicesFactory();
     advicesBeans=unitfact.getAllAdvicesCategory();
  

   
   return "";
    
    
 }

 public String loadingContentByCat()
 {
	 AdvicesFactory adviceFact=new AdvicesFactory();
	advCatBeanId=((AdviceCategoryBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":mainForm:catDataTable"))).getRowData()).getId();
	contentAdviceBeans=  adviceFact.getContentsAdvicesById(advCatBeanId,name,sorting);
	
   
	
   return "";
   
 }
 
 public String loadingContentBySorting()
 {
	 if(!sorting)
	 sorting=true;
	 else if(sorting)
	sorting=false;
	 AdvicesFactory adviceFact=new AdvicesFactory();
	advCatBeanId=((AdviceCategoryBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":mainForm:catDataTable"))).getRowData()).getId();
	contentAdviceBeans=  adviceFact.getContentsAdvicesById(advCatBeanId,name,sorting);
	
   

   return "";
   
 }
 
 
 
 
 public String loadingContentDetailsWithComents()
 {
	 AdvicesFactory adviceFact=new AdvicesFactory();
	adviceCategoryBean=((ContentAdviceBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:contentDataTable"))).getRowData());
//	HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//	session.setAttribute("adviceCategoryBean", adviceCategoryBean);
//	List<CommentsBean> commentsBeans =adviceFact.getCommentsByContentId(adviceCategoryBean.getId());
//	adviceCategoryBean.setCommentsBeans(commentsBeans);
	
	String page = "/akshf_contentDetails.xhtml?id="
			+ adviceCategoryBean.getId();
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
 
 
 public String loadingContentDetailsWithComentsWhenStart()
 {
	 ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
	 Map<String, String> maps = context.getRequestParameterMap();
		String id = maps.get("id");
		if (id != null && !id.trim().equalsIgnoreCase("")) {
		}
		
		String baseURL =  ((HttpServletRequest)context.getRequest()).getRequestURI();
		String path=((HttpServletRequest)context.getRequest()).getServletPath();
		String ids=((HttpServletRequest)context.getRequest()).getParameter("id");
	    String 	url=utils.getXMLkey("AkshffeenAdmin");
	 
	 
	 return "";
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 public String addComments()
 {
	 AdvicesFactory adviceFact=new AdvicesFactory();
	adviceCategoryBean=((ContentAdviceBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:contentDataTable"))).getRowData());
	boolean isinserted=adviceFact.addCommentsContent(commentInserted, user.getId(), adviceCategoryBean.getId());
   return "";
   
 }
 
 public String setTheContentId()
 {
	 adviceCategoryBean=((ContentAdviceBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:contentDataTable"))).getRowData());
 	int rating=(((ContentAdviceBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:contentDataTable"))).getRowData())).getRating();   
 	
 	return "";
 }
 
 
public List<AdviceCategoryBean> getAdvicesBeans() {
	return advicesBeans;
}



public void setAdvicesBeans(List<AdviceCategoryBean> advicesBeans) {
	this.advicesBeans = advicesBeans;
}



public UsersBean getUser() {
	return user;
}



public void setUser(UsersBean user) {
	this.user = user;
}



public int getAdvCatBeanId() {
	return advCatBeanId;
}



public void setAdvCatBeanId(int advCatBeanId) {
	this.advCatBeanId = advCatBeanId;
}



public List<ContentAdviceBean> getContentAdviceBeans() {
	return contentAdviceBeans;
}



public void setContentAdviceBeans(List<ContentAdviceBean> contentAdviceBeans) {
	this.contentAdviceBeans = contentAdviceBeans;
}



public ContentAdviceBean getAdviceCategoryBean() {
	return adviceCategoryBean;
}



public void setAdviceCategoryBean(ContentAdviceBean adviceCategoryBean) {
	this.adviceCategoryBean = adviceCategoryBean;
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
	int curContentDetId=adviceCategoryBean.getId();
	int rating=ratingToBeSaved;
	AdvicesFactory branchFact=new AdvicesFactory();
	int rate=branchFact.saveRating(curContentDetId, userId, rating);
	
//	if(rate==-1)
//	{
//		   message="لا يمكن الاضافة";
//	}
//	else 
//	{
//		message="تم الاضافة";
//	}
//	
//	
//	
//	for(BranchBean bean:branchBean)
//	{
//		if(bean.getBranchId()==curBranchId)
//		{
//			if( rate!=-1)
//			{
//				bean.setRating(rate);
//			}
//			break;
//		}
//	}
	
	
	
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



public List<ContentAdviceBean> getContentAdviceBeanstest() {
	if(contentAdviceBeanstest==null)
		contentAdviceBeanstest=new ArrayList<ContentAdviceBean>();
	ContentAdviceBean ct=new ContentAdviceBean();
	ct.setName_ar("ddd");
	ct.setName_en("111");
	ContentAdviceBean ct1=new ContentAdviceBean();
	ct1.setName_ar("ddd1");
	ct1.setName_en("1113");
	contentAdviceBeanstest.add(ct);
	contentAdviceBeanstest.add(ct1);
	
	return contentAdviceBeanstest;
}



public void setContentAdviceBeanstest(
		List<ContentAdviceBean> contentAdviceBeanstest) {
	this.contentAdviceBeanstest = contentAdviceBeanstest;
}
    
}
