/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sget.akshef.managedbeans;



/**
 *
 * @author Vip
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RateEvent;

import com.sget.akshef.beans.SpecialistsCategoryBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.CommentsService;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.MobileService;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshf.searchcriteria.DoctorSectionBranchCriteria;
 

public class MedSpecialistBean implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SpecialistBean> specialistsbeans;
	SpecialistBean selectedSepc;
	List<CommentsBean> commentsBeans;
	public List<CommentsBean> getCommentsBeans() {
		return commentsBeans;
	}

	public void setCommentsBeans(List<CommentsBean> commentsBeans) {
		this.commentsBeans = commentsBeans;
	}

	public SpecialistBean getSelectedSepc() {
		return selectedSepc;
	}

	public void setSelectedSepc(SpecialistBean selectedSepc) {
		this.selectedSepc = selectedSepc;
	}


	UsersBean user;
    int advCatBeanId;
    boolean sorting;
    private String name;
    private String commentInserted;
    private String favoriteInserted;
    
    SpecialistService service=new SpecialistService();
    Integer rating;
    int ratingToBeSaved;
    String message="";
    String messageFav="";
    List<SectionsBean> sectionsBeans;
      /** Creates a new instance of MedLoginBean */
    public MedSpecialistBean() {
    	HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
   	  user=	(UsersBean) session.getAttribute("userBean");
   	 
    }
    
    public List<SpecialistBean> getSpecialistsbeans() {
		return specialistsbeans;
	}



	public void setSpecialistsbeans(List<SpecialistBean> specialistsbeans) {
		this.specialistsbeans = specialistsbeans;
	}


 public String pobulateCategory()
 {
	 System.out.println("action is here");

	 sectionsBeans=service.getAllSpecialistsCategory();
    if(sectionsBeans!=null && !sectionsBeans.isEmpty())
    {
    	advCatBeanId=((SectionsBean)sectionsBeans.get(0)).getId();	
    }

   
   return "";
    
    
 }

 
 

 public void loadingContentByCat(ActionEvent evt)
 {
String id=	 ((javax.faces.component.html.HtmlCommandButton)evt.getSource()).getValue().toString();



	if(id!=null && !id.equals(""))
	{
		advCatBeanId=Integer.parseInt(id);
		
		DoctorSectionBranchCriteria search=new DoctorSectionBranchCriteria();
		search.setDoctorName(name);
		search.setSection_id(advCatBeanId);
		search.setOrderbyrate(sorting);
		specialistsbeans = service.getSpecialistBySectionWeb(search,"");
	}
	


   
 }
 
 public String setTheContentId()
 {
	
	 
		HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		message="";
		 RequestContext requestContext = RequestContext.getCurrentInstance();
	
	if(session.getAttribute("userBean")!=null )
	{
		selectedSepc  =(SpecialistBean)session.getAttribute("specialistDetailsBean");
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
	 
		HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	 
				if(session.getAttribute("userBean")!=null )
				{
					selectedSepc  =(SpecialistBean)session.getAttribute("specialistDetailsBean");
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
 

 public String loadingContentDetailsWithComents()
 {

	selectedSepc=((SpecialistBean)((org.primefaces.component.datascroller.DataScroller)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:contentDataTable"))).getRowData());
	HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	List<CommentsBean> commentsBeans=	commentService.getSpecialistComments(selectedSepc.getId());
	selectedSepc.setCommentsBeans(commentsBeans);
	session.setAttribute("specialistDetailsBean", selectedSepc);
	

   user=(UsersBean)session.getAttribute("userBean");

	String page = "/akshf_contentDetails_specialists.xhtml";
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
 
 
 public String submitForm()
{
	 System.out.println("submitForm");
	return "";
}
 private CommentsService commentService = new CommentsService() ; 
 
 public String loadingContentBySorting()
 {

   
	 if(!sorting)
	 sorting=true;
	 else if(sorting)
	sorting=false;
	 if(advCatBeanId!=0)
		{
		 DoctorSectionBranchCriteria search=new DoctorSectionBranchCriteria();
			search.setDoctorName(name);
			search.setSection_id(advCatBeanId);
			search.setOrderbyrate(sorting);
			specialistsbeans = service.getSpecialistBySectionWeb(search,"");
			
		}
	
   return "";
   
 }
 
 
// public String loadingCheckBox()
// {
//	
//	 System.out.println("loadingCheckBox   ");
//	 messageFav="";
//	 
//	 for(SpecialistBean bean  :contentSpecialistBeans)
//	 {
//		System.out.println(); bean.isSelected();
//		if(bean.isSelected())
//			selectedSpecialist=bean;
//	 }
//   
//
//return "";
//   
// }
 
 
 
// public String loadingContentDetailsWithComents()
// {
//	 System.out.println("loadingContentDetailsWithComents");
//	 SpecialistsFactory specialistFact=new SpecialistsFactory();
//	System.out.println("object here :contentForm:catDataTable  "+(((ContentSpecialistsBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:contentDataTable"))).getRowData()).getName_ar()));
//	contentSpecialistsBean=((ContentSpecialistsBean)((DataTable)( FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:contentDataTable"))).getRowData());
//	HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//	session.setAttribute("specialistCategoryBean", contentSpecialistsBean);
//	List<CommentBean> commentsBeans =specialistFact.getCommentsByContentId(contentSpecialistsBean.getId());
//	contentSpecialistsBean.setCommentBeans(commentsBeans);
//   return "akshf_contentDetails.xhtml";
//   
// }
 
 public String addComments()
 {
//	contentSpecialistsBean=selectedSpecialist;
//
//	boolean isinserted=service.addCommentsDoctor(commentInserted, user.getId(), contentSpecialistsBean.getId());
	 
		HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		selectedSepc  =(SpecialistBean)session.getAttribute("specialistDetailsBean");
			 
		CommentsBean CommentsBean=commentService.addSpecialistComment(selectedSepc.getId(), user.getId(), commentInserted);
		selectedSepc  =(SpecialistBean)session.getAttribute("specialistDetailsBean");
		selectedSepc.getCommentsBeans().add(CommentsBean);
		session.setAttribute("specialistDetailsBean", selectedSepc);

		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('commentDialog').hide()");
		
  return "";
   
 }
 
 








public int getAdvCatBeanId() {
	return advCatBeanId;
}



public void setAdvCatBeanId(int advCatBeanId) {
	this.advCatBeanId = advCatBeanId;
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
	int curContentDetId=selectedSepc.getId();
	int rating=ratingToBeSaved;

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
	
	
//	
//	for(SpecialistBean bean:specialistsbeans)
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
//	
	
	selectedSepc.setRating(rate);
	HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	
	session.setAttribute("specialistDetailsBean",selectedSepc);
	
		
	//System.out.println("is saved "+issaved);
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
    System.out.println("((Integer) rateEvent.getRating()).intValue()  "+((Integer) rateEvent.getRating()).intValue());
    ratingToBeSaved=((Integer) rateEvent.getRating()).intValue();
    FacesContext.getCurrentInstance().addMessage(null, message);
    this.rating=0;
}
 
public void oncancel() {
    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
    FacesContext.getCurrentInstance().addMessage(null, message);
    this.rating=0;
    
}


SpecialistBean selectedSpecialist;





	 public String addToFavorites()
	 {
		 
			message="";
			 RequestContext requestContext = RequestContext.getCurrentInstance();
			 HttpSession session=(HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			 
		if( session.getAttribute("userBean")!=null )
		{
			
		 	MobileService mobileService=new MobileService();
		 	selectedSepc  =(SpecialistBean)session.getAttribute("specialistDetailsBean");

		 	boolean exist = mobileService.checkIfSpecialistAlreadyFav(selectedSepc.getId(), user.getId());
		 	if(!exist){
		 		int result = mobileService.addSpecialistToFav(selectedSepc.getId(),  user.getId());
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

public String getMessageFav() {
	return messageFav;
}



public void setMessageFav(String messageFav) {
	this.messageFav = messageFav;
}



public String getFavoriteInserted() {
	return favoriteInserted;
}



public void setFavoriteInserted(String favoriteInserted) {
	this.favoriteInserted = favoriteInserted;
}



public List<SectionsBean> getSectionsBeans() {
	return sectionsBeans;
}



public void setSectionsBeans(List<SectionsBean> sectionsBeans) {
	this.sectionsBeans = sectionsBeans;
}



public SpecialistBean getSelectedSpecialist() {
	return selectedSpecialist;
}



public void setSelectedSpecialist(SpecialistBean selectedSpecialist) {
	this.selectedSpecialist = selectedSpecialist;
}





  
}
