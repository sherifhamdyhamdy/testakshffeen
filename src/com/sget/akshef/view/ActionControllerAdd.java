package com.sget.akshef.view;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.ActionsBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.ActionsService;

public class ActionControllerAdd implements Serializable{
	private ActionsBean actionsBean = null;
	private ActionsService actionsService = null;
 	private String SelectedId = null;
	private String saveOrUpdateAction = "saveAction";
	
	
/////// variable save //////////////////// make set and get ///////////
	private boolean saverender  = false;
//////////////////////////////////////////

	public ActionControllerAdd() {
		actionsService = new ActionsService();
		actionsBean = new ActionsBean();
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
		if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
		// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
		for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

		// System.out.println("CategoryControllerAdd "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
		if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("Action")){

		if(roleHasPermissionBean.getPermision().getId() == 2){
		saverender = true;
		break;
		}
		}
		}
		}
		try {

			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			// System.out.println("id    ===   " + id);
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
 				saveOrUpdateAction="updateAction";
			}
 			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {

				actionsBean = actionsService.getById(Integer
						.parseInt(SelectedId));
				// System.out.println("actionsBean    ===   " + actionsBean.getId());
				// System.out.println("actionsBean    ===   " + actionsBean.getName());
				// System.out.println("actionsBean    ===   " + actionsBean.getDescription());


			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// saveAction
	public void saveAction(ActionEvent action) {

		// System.out.println("actionsBean11    ===   ::save2");
		// System.out.println("actionsBean    ===   " + actionsBean.getId());
		// System.out.println("actionsBean    ===   " + actionsBean.getName());
		// System.out.println("actionsBean    ===   " + actionsBean.getDescription());

		if (actionsBean.getId() != 0) {
			actionsService.update(actionsBean);
		} else {
			actionsService.insert(actionsBean);
		}
		actionsBean = new  ActionsBean();

	}

	public ActionsBean getActionsBean() {
		return actionsBean;
	}

	public void setActionsBean(ActionsBean actionsBean) {
		this.actionsBean = actionsBean;
	}

	public ActionsService getActionsService() {
		return actionsService;
	}

	public void setActionsService(ActionsService actionsService) {
		this.actionsService = actionsService;
	}

	 

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	public String getSaveOrUpdateAction() {
		return saveOrUpdateAction;
	}

	public void setSaveOrUpdateAction(String saveOrUpdateAction) {
		this.saveOrUpdateAction = saveOrUpdateAction;
	}

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

}
