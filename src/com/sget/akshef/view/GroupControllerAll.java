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

import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.GroupsService;

/**
 * @author Abdelazeem
 * 
 */
public class GroupControllerAll {
	private GroupsBean groupBean;
	private GroupsService groupService;
	private List<GroupsBean> groupBeans = null;
	private GroupsBean[] selectedGroup;
	private int selectedGroupLength;
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

	public GroupControllerAll() {

		groupBean = new GroupsBean();
		groupService = new GroupsService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// groupBeans = new ArrayList<GroupBean>();
		// if (groupBeans == null)
		// getAll();
		// if (groupBeans != null && groupBeans.size() == 0)
		// getAll();

		// ////////////////////////////////////////////////////////////////////////for
		// ALL

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans = (List<RoleHasPermissionBean>) session
				.getAttribute("roleHasPermissionBeans");
		if (roleHasPermissionBeans != null && roleHasPermissionBeans.size() > 0) {
			// System.out.println("roleHasPermissionBeans.size() "+ roleHasPermissionBeans.size());
			for (RoleHasPermissionBean roleHasPermissionBean : roleHasPermissionBeans) {

				if (roleHasPermissionBean.getRole().getName().trim()
						.equalsIgnoreCase("Group")) {
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

		int arrayCount = groupBeans.size();
		selectedGroup = new GroupsBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedGroup  delete fun ::: "+ selectedGroup.length);

		if (selectedGroup.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedGroup.length; count++) {

			groupBean = selectedGroup[count];

			deleteStatus = groupService.delete(groupBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedGroup.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * groupBean = new GroupBean();
		 * groupBean.setId(Integer.parseInt(selected));
		 * groupService.delete(groupBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedGroup.length);

		if (selectedGroup.length != 1) {
			message = "select one";
			return;
		}

		if (selectedGroup != null && selectedGroup.length == 1) {
			String page = "/systemadmin/groupAdd.xhtml?id="
					+ selectedGroup[0].getId();
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

		groupBeans = new ArrayList<GroupsBean>();
		groupBeans.addAll(groupService.getAll());

		// add to test only
		// System.out.println("groupBeans.size(): " + groupBeans.size());
		/*for (GroupsBean bean : groupBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getName());
			// System.out.println("bean.getNameAr() : " + bean.getSystemFlag());
		}*/

	}

	// /geter setter

	public GroupsBean getGroupBean() {
		return groupBean;
	}

	public void setGroupBean(GroupsBean groupBean) {
		this.groupBean = groupBean;
	}

	public GroupsService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupsService groupService) {
		this.groupService = groupService;
	}

	public List<GroupsBean> getGroupBeans() {
		return groupBeans;
	}

	public void setGroupBeans(List<GroupsBean> groupBeans) {
		this.groupBeans = groupBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public GroupsBean[] getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(GroupsBean[] selectedGroup) {
		this.selectedGroup = selectedGroup;
		selectedGroupLength = selectedGroup.length;
	}

	public int getSelectedGroupLength() {
		return selectedGroupLength;
	}

	public void setSelectedGroupLength(int selectedGroupLength) {
		this.selectedGroupLength = selectedGroupLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
