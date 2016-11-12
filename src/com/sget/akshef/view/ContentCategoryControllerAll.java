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

import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.ContentCategoryService;

/**
 * @author Abdelazeem
 * 
 */
public class ContentCategoryControllerAll implements Serializable{
	
	private static final long serialVersionUID = 5773280868724986570L;
	
	private ContentCategoryBean contentCategoryBean;
	private ContentCategoryService contentCategoryService;
	private List<ContentCategoryBean> contentCategoryBeans = null;
	private ContentCategoryBean[] selectedContentCategory;
	private int selectedContentCategoryLength;
	private String selected;
	private String message = "";

	private boolean updaterender  = false;
	private boolean deleterender  = false;
	
	
	public ContentCategoryControllerAll() {

		contentCategoryBean = new ContentCategoryBean();
		contentCategoryService = new ContentCategoryService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		
		
		

HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

// System.out.println("CategoryControllerAll "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("ContentCategory")){
if(roleHasPermissionBean.getPermision().getId() == 4){
deleterender = true;
}
if(roleHasPermissionBean.getPermision().getId() == 3){
updaterender = true;
}
}
}
}
		
		
		
		// contentCategoryBeans = new ArrayList<ContentCategoryBean>();
		// if (contentCategoryBeans == null)
		// getAll();
		// if (contentCategoryBeans != null && contentCategoryBeans.size() == 0)
		// getAll();

		getAll();

		int arrayCount = contentCategoryBeans.size();
		selectedContentCategory = new ContentCategoryBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		if (selectedContentCategory.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedContentCategory.length; count++) {

			contentCategoryBean = selectedContentCategory[count];

			deleteStatus = contentCategoryService.delete(contentCategoryBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedContentCategory.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * contentCategoryBean = new ContentCategoryBean();
		 * contentCategoryBean.setId(Integer.parseInt(selected));
		 * contentCategoryService.delete(contentCategoryBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		if (selectedContentCategory.length != 1) {
			message = "select one";
			return;
		}

		if (selectedContentCategory != null
				&& selectedContentCategory.length == 1) {
			String page = "/systemadmin/contentCategoryAdd.xhtml?id="
					+ selectedContentCategory[0].getId();
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

		contentCategoryBeans = new ArrayList<ContentCategoryBean>();
		contentCategoryBeans.addAll(contentCategoryService.getAll());

		// add to test only
		// System.out.println("contentCategoryBeans.size(): "+ contentCategoryBeans.size());
		/*for (ContentCategoryBean bean : contentCategoryBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getNameAr());
			// System.out.println("bean.getNameAr() : " + bean.getNameEn());
		}*/

	}

	// /geter setter

	public ContentCategoryBean getContentCategoryBean() {
		return contentCategoryBean;
	}

	public void setContentCategoryBean(ContentCategoryBean contentCategoryBean) {
		this.contentCategoryBean = contentCategoryBean;
	}

	public ContentCategoryService getContentCategoryService() {
		return contentCategoryService;
	}

	public void setContentCategoryService(
			ContentCategoryService contentCategoryService) {
		this.contentCategoryService = contentCategoryService;
	}

	public List<ContentCategoryBean> getContentCategoryBeans() {
		return contentCategoryBeans;
	}

	public void setContentCategoryBeans(
			List<ContentCategoryBean> contentCategoryBeans) {
		this.contentCategoryBeans = contentCategoryBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public ContentCategoryBean[] getSelectedContentCategory() {
		return selectedContentCategory;
	}

	public void setSelectedContentCategory(
			ContentCategoryBean[] selectedContentCategory) {
		this.selectedContentCategory = selectedContentCategory;
		selectedContentCategoryLength = selectedContentCategory.length;
	}

	public int getSelectedContentCategoryLength() {
		return selectedContentCategoryLength;
	}

	public void setSelectedContentCategoryLength(
			int selectedContentCategoryLength) {
		this.selectedContentCategoryLength = selectedContentCategoryLength;
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
