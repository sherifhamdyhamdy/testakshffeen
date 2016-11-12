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

import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.ActionsBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.ActionsService;

/**
 * @author Abdelazeem
 * 
 */
public class ActionControllerAll implements Serializable {
	private ActionsBean actionBean;
	private ActionsService actionService;
	private List<ActionsBean> actionBeans = null;
	private ActionsBean[] selectedAction;
	private int selectedActionLength;
	private String selected;
	private String message = "";
	/////////////////////////////////////  make variable  for  update  and  delete //////  and  make set and get for its //////////////
	private boolean updaterender  = false;
	private boolean deleterender  = false;
/////////////////////////////////////////////////////////////////////////////////////////////////////

	public ActionControllerAll() {

		actionBean = new ActionsBean();
		actionService = new ActionsService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		
//////////////////////////////////////////////////////////////////////////for ALL

HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

// System.out.println("CategoryControllerAll "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("Action")){
if(roleHasPermissionBean.getPermision().getId() == 4){
deleterender = true;
}
if(roleHasPermissionBean.getPermision().getId() == 3){
updaterender = true;
}
}
}
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// actionBeans = new ArrayList<ActionsBean>();
		// if (actionBeans == null)
		// getAll();
		// if (actionBeans != null && actionBeans.size() == 0)
		// getAll();

		getAll();

		int arrayCount = actionBeans.size();
		selectedAction = new ActionsBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedAction  delete fun ::: "+ selectedAction.length);

		if (selectedAction.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedAction.length; count++) {

			actionBean = selectedAction[count];

			deleteStatus = actionService.delete(actionBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedAction.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * actionBean = new ActionsBean();
		 * actionBean.setId(Integer.parseInt(selected));
		 * actionService.delete(actionBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedAction.length);

		if (selectedAction.length != 1) {
			message = "select one";
			return;
		}

		if (selectedAction != null && selectedAction.length == 1) {
			String page = "/systemadmin/actionAdd.xhtml?id="
					+ selectedAction[0].getId();
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

		actionBeans = new ArrayList<ActionsBean>();
		actionBeans.addAll(actionService.getAll());

		// add to test only
		// System.out.println("actionBeans.size(): " + actionBeans.size());
		/*for (ActionsBean bean : actionBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getName());
			// System.out.println("bean.getNameAr() : " + bean.getDescription());
		}*/

	}

	// /geter setter

	public ActionsBean getActionsBean() {
		return actionBean;
	}

	public void setActionsBean(ActionsBean actionBean) {
		this.actionBean = actionBean;
	}

	public ActionsService getActionService() {
		return actionService;
	}

	public void setActionService(ActionsService actionService) {
		this.actionService = actionService;
	}

	public List<ActionsBean> getActionBeans() {
		return actionBeans;
	}

	public void setActionBeans(List<ActionsBean> actionBeans) {
		this.actionBeans = actionBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public ActionsBean[] getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(ActionsBean[] selectedAction) {
		this.selectedAction = selectedAction;
		selectedActionLength = selectedAction.length;
	}

	public int getSelectedActionLength() {
		return selectedActionLength;
	}

	public void setSelectedActionLength(int selectedActionLength) {
		this.selectedActionLength = selectedActionLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isDeleterender() {
		return deleterender;
	}

	public void setDeleterender(boolean deleterender) {
		this.deleterender = deleterender;
	}

	public ActionsBean getActionBean() {
		return actionBean;
	}

	public void setActionBean(ActionsBean actionBean) {
		this.actionBean = actionBean;
	}

	public boolean isUpdaterender() {
		return updaterender;
	}

	public void setUpdaterender(boolean updaterender) {
		this.updaterender = updaterender;
	}

}
