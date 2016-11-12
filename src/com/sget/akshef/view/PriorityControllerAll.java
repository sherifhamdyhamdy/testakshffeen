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

import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.PriorityService;

/**
 * @author Abdelazeem
 * 
 */
public class PriorityControllerAll {
	private PriorityBean priorityBean;
	private PriorityService priorityService;
	private List<PriorityBean> priorityBeans = null;
	private PriorityBean[] selectedPriority;
	private int selectedPriorityLength;
	private String selected;
	private String message = "";
	private boolean updaterender  = false;
	private boolean deleterender  = false;

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

	public PriorityControllerAll() {

		priorityBean = new PriorityBean();
		priorityService = new PriorityService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// priorityBeans = new ArrayList<PriorityBean>();
		// if (priorityBeans == null)
		// getAll();
		// if (priorityBeans != null && priorityBeans.size() == 0)
		// getAll();
		// ////////////////////////////////////////////////////////////////////////for
		// ALL

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans = (List<RoleHasPermissionBean>) session
				.getAttribute("roleHasPermissionBeans");
		if (roleHasPermissionBeans != null && roleHasPermissionBeans.size() > 0) {
			for (RoleHasPermissionBean roleHasPermissionBean : roleHasPermissionBeans) {

				if (roleHasPermissionBean.getRole().getName().trim()
						.equalsIgnoreCase("Priority")) {
					if (roleHasPermissionBean.getPermision().getId() == 4) {
						deleterender = true;
					}
					if (roleHasPermissionBean.getPermision().getId() == 3) {
						updaterender = true;
					}
				}
			}
		}
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		getAll();

		int arrayCount = priorityBeans.size();
		selectedPriority = new PriorityBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		if (selectedPriority.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedPriority.length; count++) {

			priorityBean = selectedPriority[count];

			deleteStatus = priorityService.delete(priorityBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedPriority.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * priorityBean = new PriorityBean();
		 * priorityBean.setId(Integer.parseInt(selected));
		 * priorityService.delete(priorityBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedPriority.length);

		if (selectedPriority.length != 1) {
			message = "select one";
			return;
		}

		if (selectedPriority != null && selectedPriority.length == 1) {
			String page = "/systemadmin/priorityAdd.xhtml?id="
					+ selectedPriority[0].getId();
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

		priorityBeans = new ArrayList<PriorityBean>();
		priorityBeans.addAll(priorityService.getAll());

		// add to test only
		// System.out.println("priorityBeans.size(): " + priorityBeans.size());
		/*for (PriorityBean bean : priorityBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getNameAr());
			// System.out.println("bean.getNameAr() : " + bean.getNameEn());
		}*/

	}

	// /geter setter

	public PriorityBean getPriorityBean() {
		return priorityBean;
	}

	public void setPriorityBean(PriorityBean priorityBean) {
		this.priorityBean = priorityBean;
	}

	public PriorityService getPriorityService() {
		return priorityService;
	}

	public void setPriorityService(PriorityService priorityService) {
		this.priorityService = priorityService;
	}

	public List<PriorityBean> getPriorityBeans() {
		return priorityBeans;
	}

	public void setPriorityBeans(List<PriorityBean> priorityBeans) {
		this.priorityBeans = priorityBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public PriorityBean[] getSelectedPriority() {
		return selectedPriority;
	}

	public void setSelectedPriority(PriorityBean[] selectedPriority) {
		this.selectedPriority = selectedPriority;
		selectedPriorityLength = selectedPriority.length;
	}

	public int getSelectedPriorityLength() {
		return selectedPriorityLength;
	}

	public void setSelectedPriorityLength(int selectedPriorityLength) {
		this.selectedPriorityLength = selectedPriorityLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
