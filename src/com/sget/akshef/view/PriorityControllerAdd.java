package com.sget.akshef.view;

import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.PriorityService;

public class PriorityControllerAdd {
	private PriorityBean priorityBean = null;
	private PriorityService priorityService = null;
	private boolean allowUpdate = false;
	private String SelectedId = null;
	private String saveupdatePriority = "savePriority";
	private boolean saverender = false;

	public PriorityControllerAdd() {
		priorityService = new PriorityService();
		priorityBean = new PriorityBean();
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		// ////////////////////////////////////////////////////////////////////////forAdd
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
						.getExternalContext().getSession(true);
				List<RoleHasPermissionBean> roleHasPermissionBeans = (List<RoleHasPermissionBean>) session
						.getAttribute("roleHasPermissionBeans");
				if (roleHasPermissionBeans != null && roleHasPermissionBeans.size() > 0) {
					// System.out.println("roleHasPermissionBeans.size()"+ roleHasPermissionBeans.size());
					for (RoleHasPermissionBean roleHasPermissionBean : roleHasPermissionBeans) {

						if (roleHasPermissionBean.getRole().getName().trim()
								.equalsIgnoreCase("Priority")) {

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

				priorityBean = priorityService.getById(Integer
						.parseInt(SelectedId));
				saveupdatePriority = "updatePriority";

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

	// savePriority
	public void savePriority(ActionEvent action) {

		// System.out.println("priorityBean11    ===   ::save2");
		// System.out.println("priorityBean    ===   " + priorityBean.getId());

		// System.out.println("priorityBean    ===   " + priorityBean.getNameAr());
		// System.out.println("priorityBean    ===   " + priorityBean.getNameEn());

		if (priorityBean.getId() != 0) {
			priorityService.update(priorityBean);
		} else {
			priorityService.insert(priorityBean);
		}
		priorityBean = new PriorityBean();

	}

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

	public String getSaveupdatePriority() {
		return saveupdatePriority;
	}

	public void setSaveupdatePriority(String saveupdatePriority) {
		this.saveupdatePriority = saveupdatePriority;
	}

}
