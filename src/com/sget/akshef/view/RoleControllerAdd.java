package com.sget.akshef.view;

import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.RoleBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.RoleService;

public class RoleControllerAdd {
	private RoleBean roleBean = null;
	private RoleService roleService = null;
	private boolean allowUpdate = false;
	private String SelectedId = null;
	private String saveupdateRole = "saveRole";
	private boolean saverender = false;

	public RoleControllerAdd() {
		roleService = new RoleService();
		roleBean = new RoleBean();
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		// ////////////////////////////////////////////////////////////////////////forAdd
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans = (List<RoleHasPermissionBean>) session
				.getAttribute("roleHasPermissionBeans");
		if (roleHasPermissionBeans != null && roleHasPermissionBeans.size() > 0) {

			for (RoleHasPermissionBean roleHasPermissionBean : roleHasPermissionBeans) {
				
				if (roleHasPermissionBean.getRole().getName().trim()
						.equalsIgnoreCase("Role")) {

					if (roleHasPermissionBean.getPermision().getId() == 2) {
						saverender = true;
						break;
					}
				}
			}
		}
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		try {

			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			// System.out.println("id    ===   " + id);
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
				allowUpdate = true;
			}
			// System.out.println("allowUpdate    ===   " + allowUpdate);
			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {

				roleBean = roleService.getById(Integer.parseInt(SelectedId));
				saveupdateRole = "updateRole";

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	// saveRole
	public void saveRole(ActionEvent action) {

		// System.out.println("roleBean11    ===   ::save2");
		// System.out.println("roleBean    ===   " + roleBean.getId());

		// System.out.println("roleBean    ===   " + roleBean.getName());
		// // System.out.println("roleBean    ===   " + roleBean.getNameEn());

		if (roleBean.getId() != 0) {
			roleService.update(roleBean);
		} else {
			roleService.insert(roleBean);
		}
		roleBean = new RoleBean();

	}

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

	public boolean isAllowUpdate() {
		return allowUpdate;
	}

	public void setAllowUpdate(boolean allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	public String getSaveupdateRole() {
		return saveupdateRole;
	}

	public void setSaveupdateRole(String saveupdateRole) {
		this.saveupdateRole = saveupdateRole;
	}

}
