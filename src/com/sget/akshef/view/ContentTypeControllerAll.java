/**
 * 
 */
package com.sget.akshef.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.ContentTypesBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.ContentTypesService;

/**
 * @author Abdelazeem
 * 
 */
public class ContentTypeControllerAll {
	private ContentTypesBean contentTypeBean;
	private ContentTypesService contentTypeService;
	private List<ContentTypesBean> contentTypeBeans = null;
	private ContentTypesBean[] selectedContentType;
	private int selectedContentTypeLength;
	private String selected;
	private String message = "";

	private boolean updaterender  = false;
	private boolean deleterender  = false;
	
	public ContentTypeControllerAll() {

		contentTypeBean = new ContentTypesBean();
		contentTypeService = new ContentTypesService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		
		


HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
//System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

//System.out.println(" ContentTypeControllerAll "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("ContentType")){
if(roleHasPermissionBean.getPermision().getId() == 4){
deleterender = true;
}
if(roleHasPermissionBean.getPermision().getId() == 3){
updaterender = true;
}
}
}
}

		// contentTypeBeans = new ArrayList<ContentTypesBean>();
		// if (contentTypeBeans == null)
		// getAll();
		// if (contentTypeBeans != null && contentTypeBeans.size() == 0)
		// getAll();

		getAll();

		int arrayCount = contentTypeBeans.size();
		selectedContentType = new ContentTypesBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		//System.out.println("selectedContentType  delete fun ::: "+ selectedContentType.length);

		if (selectedContentType.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedContentType.length; count++) {

			contentTypeBean = selectedContentType[count];

			deleteStatus = contentTypeService.delete(contentTypeBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedContentType.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * contentTypeBean = new ContentTypesBean();
		 * contentTypeBean.setId(Integer.parseInt(selected));
		 * contentTypeService.delete(contentTypeBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		//System.out.println("herer update fu :::  ");

		System.out
				.println("herer update fu :::  " + selectedContentType.length);

		if (selectedContentType.length != 1) {
			message = "select one";
			return;
		}

		if (selectedContentType != null && selectedContentType.length == 1) {
			String page = "/systemadmin/contentTypeAdd.xhtml?id="
					+ selectedContentType[0].getId();
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

		contentTypeBeans = new ArrayList<ContentTypesBean>();
		contentTypeBeans.addAll(contentTypeService.getAll());

		// add to test only
		//System.out.println("contentTypeBeans.size(): "+ contentTypeBeans.size());
		/*for (ContentTypesBean bean : contentTypeBeans) {
			//System.out.println("bean.getId() : " + bean.getId());
			//System.out.println("bean.getNameAr() : " + bean.getNameAr());
			//System.out.println("bean.getNameAr() : " + bean.getNameEn());
		}*/

	}

	// /geter setter

	public ContentTypesBean getContentTypeBean() {
		return contentTypeBean;
	}

	public void setContentTypeBean(ContentTypesBean contentTypeBean) {
		this.contentTypeBean = contentTypeBean;
	}

	public ContentTypesService getContentTypesService() {
		return contentTypeService;
	}

	public void setContentTypesService(ContentTypesService contentTypeService) {
		this.contentTypeService = contentTypeService;
	}

	public List<ContentTypesBean> getContentTypeBeans() {
		return contentTypeBeans;
	}

	public void setContentTypeBeans(List<ContentTypesBean> contentTypeBeans) {
		this.contentTypeBeans = contentTypeBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public ContentTypesBean[] getSelectedContentType() {
		return selectedContentType;
	}

	public void setSelectedContentType(ContentTypesBean[] selectedContentType) {
		this.selectedContentType = selectedContentType;
		selectedContentTypeLength = selectedContentType.length;
	}

	public int getSelectedContentTypeLength() {
		return selectedContentTypeLength;
	}

	public void setSelectedContentTypeLength(int selectedContentTypeLength) {
		this.selectedContentTypeLength = selectedContentTypeLength;
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
