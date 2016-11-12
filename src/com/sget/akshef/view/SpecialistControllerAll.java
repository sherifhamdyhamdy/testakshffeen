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

import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.service.SpecialistService;

/**
 * @author Abdelazeem
 * 
 */
public class SpecialistControllerAll {
	private SpecialistBean specialistBean;
	private SpecialistService specialistService;
	private List<SpecialistBean> specialistBeans = null;
	private SpecialistBean[] selectedSpecialist;
	private int selectedSpecialistLength;
	private String selected;
	private String message = "";
	private boolean updaterender = false;
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

	private boolean deleterender = false;

	public SpecialistControllerAll() {

		specialistBean = new SpecialistBean();
		specialistService = new SpecialistService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// specialistBeans = new ArrayList<SpecialistBean>();
		// if (specialistBeans == null)
		// getAll();
		// if (specialistBeans != null && specialistBeans.size() == 0)
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
						.equalsIgnoreCase("Specialist")) {
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

		int arrayCount = specialistBeans.size();
		selectedSpecialist = new SpecialistBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		if (selectedSpecialist.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedSpecialist.length; count++) {

			specialistBean = selectedSpecialist[count];

			deleteStatus = specialistService.delete(specialistBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedSpecialist.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * specialistBean = new SpecialistBean();
		 * specialistBean.setId(Integer.parseInt(selected));
		 * specialistService.delete(specialistBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedSpecialist.length);

		if (selectedSpecialist.length != 1) {
			message = "select one";
			return;
		}

		if (selectedSpecialist != null && selectedSpecialist.length == 1) {
			String page = "/systemadmin/specialistAdd.xhtml?id="
					+ selectedSpecialist[0].getId();
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

		specialistBeans = new ArrayList<SpecialistBean>();
		specialistBeans.addAll(specialistService.getAll());

		// add to test only
		// System.out.println("specialistBeans.size(): " + specialistBeans.size());
		/*for (SpecialistBean bean : specialistBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getName());

		}*/

	}

	// /geter setter

	public SpecialistBean getSpecialistBean() {
		return specialistBean;
	}

	public void setSpecialistBean(SpecialistBean specialistBean) {
		this.specialistBean = specialistBean;
	}

	public SpecialistService getSpecialistService() {
		return specialistService;
	}

	public void setSpecialistService(SpecialistService specialistService) {
		this.specialistService = specialistService;
	}

	public List<SpecialistBean> getSpecialistBeans() {
		return specialistBeans;
	}

	public void setSpecialistBeans(List<SpecialistBean> specialistBeans) {
		this.specialistBeans = specialistBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public SpecialistBean[] getSelectedSpecialist() {
		return selectedSpecialist;
	}

	public void setSelectedSpecialist(SpecialistBean[] selectedSpecialist) {
		this.selectedSpecialist = selectedSpecialist;
		selectedSpecialistLength = selectedSpecialist.length;
	}

	public int getSelectedSpecialistLength() {
		return selectedSpecialistLength;
	}

	public void setSelectedSpecialistLength(int selectedSpecialistLength) {
		this.selectedSpecialistLength = selectedSpecialistLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
