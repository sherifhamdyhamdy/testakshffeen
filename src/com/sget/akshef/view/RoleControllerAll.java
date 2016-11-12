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

import com.sget.akshef.hibernate.beans.RoleBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.RoleService;

/**
 * @author Abdelazeem
 * 
 */
public class RoleControllerAll {
	private RoleBean roleBean;
	private RoleService roleService;
	private List<RoleBean> roleBeans = null;
	private RoleBean[] selectedRole;
	private int selectedRoleLength;
	private String selected;
	private String message = "";
	private boolean updaterender = false;
	private boolean deleterender = false;

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

	public RoleControllerAll() {

		roleBean = new RoleBean();
		roleService = new RoleService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// roleBeans = new ArrayList<RoleBean>();
		// if (roleBeans == null)
		// getAll();
		// if (roleBeans != null && roleBeans.size() == 0)
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
						.equalsIgnoreCase("Role")) {
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

		int arrayCount = roleBeans.size();
		selectedRole = new RoleBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedRole  delete fun ::: "+ selectedRole.length);

		if (selectedRole.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedRole.length; count++) {

			roleBean = selectedRole[count];

			deleteStatus = roleService.delete(roleBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedRole.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * roleBean = new RoleBean();
		 * roleBean.setId(Integer.parseInt(selected));
		 * roleService.delete(roleBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedRole.length);

		if (selectedRole.length != 1) {
			message = "select one";
			return;
		}

		if (selectedRole != null && selectedRole.length == 1) {
			String page = "/systemadmin/roleAdd.xhtml?id="
					+ selectedRole[0].getId();
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

		roleBeans = new ArrayList<RoleBean>();
		roleBeans.addAll(roleService.getAll());

		// add to test only
		// System.out.println("roleBeans.size(): " + roleBeans.size());
		/*for (RoleBean bean : roleBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getName());

		}*/

	}

	// /geter setter

	public RoleBean getRoleBean() {
		return roleBean;
	}

	public void setRoleBean(RoleBean roleBean) {
		this.roleBean = roleBean;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<RoleBean> getRoleBeans() {
		return roleBeans;
	}

	public void setRoleBeans(List<RoleBean> roleBeans) {
		this.roleBeans = roleBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public RoleBean[] getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(RoleBean[] selectedRole) {
		this.selectedRole = selectedRole;
		selectedRoleLength = selectedRole.length;
	}

	public int getSelectedRoleLength() {
		return selectedRoleLength;
	}

	public void setSelectedRoleLength(int selectedRoleLength) {
		this.selectedRoleLength = selectedRoleLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
