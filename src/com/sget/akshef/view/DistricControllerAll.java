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
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.DistricService;

/**
 * @author Abdelazeem
 * 
 */
public class DistricControllerAll implements Serializable{
	
	private static final long serialVersionUID = 8463513551529586490L;
	
	private DistricBean districBean;
	private DistricService districService;
	private List<DistricBean> districBeans = null;
	private DistricBean[] selectedDistric;
	private int selectedDistricLength;
	private String selected;
	private String message = "";
	
	private boolean updaterender  = false;
	private boolean deleterender  = false;

	public DistricControllerAll() {

		districBean = new DistricBean();
		districService = new DistricService();
		 

HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

// System.out.println("DistricControllerAll "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("District")){
if(roleHasPermissionBean.getPermision().getId() == 4){
deleterender = true;
}
if(roleHasPermissionBean.getPermision().getId() == 3){
updaterender = true;
}
}
}
}
		
		
		// districBeans = new ArrayList<DistricBean>();
		// if (districBeans == null)
		// getAll();
		// if (districBeans != null && districBeans.size() == 0)
		// getAll();

		getAll();

		int arrayCount = districBeans.size();
		selectedDistric = new DistricBean[arrayCount];

		 

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedDistric  delete fun ::: "+ selectedDistric.length);

		if (selectedDistric.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedDistric.length; count++) {

			districBean = selectedDistric[count];

			deleteStatus = districService.delete(districBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedDistric.length)
			message = "deleted";
		else
			message = "City can not be deleted,district was  linked to other branches";
		getAll();

		/*
		 * districBean = new DistricBean();
		 * districBean.setId(Integer.parseInt(selected));
		 * districService.delete(districBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedDistric.length);

		if (selectedDistric.length != 1) {
			message = "select one";
			return;
		}

		if (selectedDistric != null && selectedDistric.length == 1) {
			String page = "/systemadmin/districAdd.xhtml?id="
					+ selectedDistric[0].getId();
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

		districBeans = new ArrayList<DistricBean>();
		districBeans.addAll(districService.getAll());

		// add to test only
		// System.out.println("districBeans.size(): " + districBeans.size());
		/*for (DistricBean bean : districBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getNameAr());
			// System.out.println("bean.getNameAr() : " + bean.getNameEn());
		}*/

	}

	// /geter setter

	public DistricBean getDistricBean() {
		return districBean;
	}

	public void setDistricBean(DistricBean districBean) {
		this.districBean = districBean;
	}

	public DistricService getDistricService() {
		return districService;
	}

	public void setDistricService(DistricService districService) {
		this.districService = districService;
	}

	public List<DistricBean> getDistricBeans() {
		return districBeans;
	}

	public void setDistricBeans(List<DistricBean> districBeans) {
		this.districBeans = districBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public DistricBean[] getSelectedDistric() {
		return selectedDistric;
	}

	public void setSelectedDistric(DistricBean[] selectedDistric) {
		this.selectedDistric = selectedDistric;
		selectedDistricLength = selectedDistric.length;
	}

	public int getSelectedDistricLength() {
		return selectedDistricLength;
	}

	public void setSelectedDistricLength(int selectedDistricLength) {
		this.selectedDistricLength = selectedDistricLength;
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
