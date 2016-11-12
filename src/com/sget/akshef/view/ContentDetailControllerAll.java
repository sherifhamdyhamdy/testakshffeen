/**
 * 
 */
package com.sget.akshef.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.ContentDetailsService;

/**
 * @author Abdelazeem
 * 
 */
public class ContentDetailControllerAll implements Serializable{
	
	private static final long serialVersionUID = -3038685635962682687L;
	
	private ContentDetailsBean contentDetailBean;
	private ContentDetailsService contentDetailService;
	private List<ContentDetailsBean> contentDetailBeans = null;
	private ContentDetailsBean[] selectedContentDetail;
	private int selectedContentDetailLength;
	private String selected;
	private String message = "";

	private boolean updaterender  = false;
	private boolean deleterender  = false;
	
	public ContentDetailControllerAll() {

		contentDetailBean = new ContentDetailsBean();
		contentDetailService = new ContentDetailsService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		
		

HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

// System.out.println("ContentDetailControllerAll "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("ContentDetails")){
if(roleHasPermissionBean.getPermision().getId() == 4){
deleterender = true;
}
if(roleHasPermissionBean.getPermision().getId() == 3){
updaterender = true;
}
}
}
}
		
		
		
		// contentDetailBeans = new ArrayList<ContentDetailBean>();
		// if (contentDetailBeans == null)
		// getAll();
		// if (contentDetailBeans != null && contentDetailBeans.size() == 0)
		// getAll();

		getAll();

		int arrayCount = contentDetailBeans.size();
		selectedContentDetail = new ContentDetailsBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		if (selectedContentDetail.length < 1) {
			message = "select at least one";
			return;
		}
		for (int count = 0; count < selectedContentDetail.length; count++) {
			contentDetailBean = selectedContentDetail[count];

			deleteStatus = contentDetailService.delete(contentDetailBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedContentDetail.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

	}

	// update
	public void update() {

		if (selectedContentDetail.length != 1) {
			message = "select one";
			return;
		}

		if (selectedContentDetail != null && selectedContentDetail.length == 1) {
			String page = "/systemadmin/contAdd.xhtml";
			
			if(selectedContentDetail[0].getContentCategory().getId() > 0){
				if(selectedContentDetail[0].getContentCategory().getId() == DBConstants.CONTENT_TYPE_TIPS){
					page += "?type="+DBConstants.CONTENT_TYPE_TIPS+"&id=" + selectedContentDetail[0].getId();
				}else if(selectedContentDetail[0].getContentCategory().getId() == DBConstants.CONTENT_TYPE_OFFERS){
					page += "?type="+DBConstants.CONTENT_TYPE_OFFERS+"&id=" + selectedContentDetail[0].getId();
				}else if(selectedContentDetail[0].getContentCategory().getId() == DBConstants.CONTENT_TYPE_NEWS){
					page += "?type="+DBConstants.CONTENT_TYPE_NEWS+"&id=" + selectedContentDetail[0].getId();
				}else if(selectedContentDetail[0].getContentCategory().getId() == DBConstants.CONTENT_TYPE_ARTICLE){
					page += "?type="+DBConstants.CONTENT_TYPE_ARTICLE+"&id=" + selectedContentDetail[0].getId();
				}
			}else{
				page += "?id="+ selectedContentDetail[0].getId();
			}
			
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + page);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void redirecttoPage(String page) {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + page);

		} catch (IOException e) {

			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					e.getMessage());
		}
	}

	// getAll
	public void getAll() {

		contentDetailBeans = new ArrayList<ContentDetailsBean>();
		contentDetailBeans.addAll(contentDetailService.getAll());

		// add to test only
		// System.out.println("contentDetailBeans.size(): "+ contentDetailBeans.size());
		/*for (ContentDetailsBean bean : contentDetailBeans) {
			// System.out.println("bean.getNameAr() : " + bean.getSmallImage());
			// System.out.println("bean.getNameAr() : " + bean.getLargeImage());
		}*/

	}

	// /geter setter

	public ContentDetailsBean getContentDetailBean() {
		return contentDetailBean;
	}

	public void setContentDetailBean(ContentDetailsBean contentDetailBean) {
		this.contentDetailBean = contentDetailBean;
	}

	public ContentDetailsService getContentDetailService() {
		return contentDetailService;
	}

	public void setContentDetailService(
			ContentDetailsService contentDetailService) {
		this.contentDetailService = contentDetailService;
	}

	public List<ContentDetailsBean> getContentDetailBeans() {
		return contentDetailBeans;
	}

	public void setContentDetailBeans(
			List<ContentDetailsBean> contentDetailBeans) {
		this.contentDetailBeans = contentDetailBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public ContentDetailsBean[] getSelectedContentDetail() {
		return selectedContentDetail;
	}

	public void setSelectedContentDetail(
			ContentDetailsBean[] selectedContentDetail) {
		this.selectedContentDetail = selectedContentDetail;
		selectedContentDetailLength = selectedContentDetail.length;
	}

	public int getSelectedContentDetailLength() {
		return selectedContentDetailLength;
	}

	public void setSelectedContentDetailLength(int selectedContentDetailLength) {
		this.selectedContentDetailLength = selectedContentDetailLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isUpdaterender() {
		return updaterender;
	}

	public void setUpdaterender(boolean updaterender) {
		this.updaterender = updaterender;
	}

	public boolean isDeleterender() {
		return deleterender;
	}

	public void setDeleterender(boolean deleterender) {
		this.deleterender = deleterender;
	}

}
