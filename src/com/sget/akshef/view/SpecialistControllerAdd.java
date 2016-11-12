package com.sget.akshef.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshef.hibernate.service.UsersService;

//@ManagedBean(name="specialistControllerAdd")
//@ViewScoped
public class SpecialistControllerAdd {

	private SpecialistBean specialistBean = null;
	private UsersBean usersBean = null;

	private UsersService usersService = null;
	private SpecialistService specialistService = null;

	private String saveOrUpdateSpecialist = "saveSpecialist";
	private int selectedUsers;
	private ArrayList<SelectItem> menu_items;

	private String SelectedId = null;
	private boolean saverender = false;

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	public SpecialistControllerAdd() {

		specialistBean = new SpecialistBean();
		usersBean = new UsersBean();

		usersService = new UsersService();
		specialistService = new SpecialistService();

		menu_items = new ArrayList<SelectItem>();

		menu_items.add(new SelectItem("", ""));

		for (UsersBean usersBean1 : usersService.getAll()) {
			menu_items.add(new SelectItem(String.valueOf(usersBean1.getId()),
					usersBean1.getNameAr()));
		}

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
						.equalsIgnoreCase("Specialist")) {

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
			}
			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {
				specialistBean = specialistService.getById(Integer
						.parseInt(SelectedId));
				selectedUsers = specialistBean.getUsers().getId();
			}
			if (specialistBean.getId() != 0) {
				saveOrUpdateSpecialist = "updatespecialist";
			} else {
				saveOrUpdateSpecialist = "saveSpecialist";
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// specialistControllerAddService
	public void saveSpecialist(ActionEvent action) {
		
		// System.out.println("selectedCategory    ===   " + selectedUsers);

		usersBean.setId(selectedUsers);
		specialistBean.setUsers(usersBean);

		if (specialistBean.getId() != 0) {
			specialistService.update(specialistBean);
		} else {
			specialistService.insert(specialistBean);
		}
		specialistService.insert(specialistBean);
		// System.out.println("usersBean.getId()    ===   " + usersBean.getId());

		specialistBean = new SpecialistBean();
		selectedUsers = 0;

	}

	public ArrayList<SelectItem> getMenu_items() {
		return menu_items;
	}

	public void setMenu_items(ArrayList<SelectItem> menu_items) {
		this.menu_items = menu_items;
	}

	public SpecialistBean getSpecialistBean() {
		return specialistBean;
	}

	public void setSpecialistBean(SpecialistBean specialistBean) {
		this.specialistBean = specialistBean;
	}

	public UsersBean getUsersBean() {
		return usersBean;
	}

	public void setUsersBean(UsersBean usersBean) {
		this.usersBean = usersBean;
	}

	public String getSaveOrUpdateSpecialist() {
		return saveOrUpdateSpecialist;
	}

	public void setSaveOrUpdateSpecialist(String saveOrUpdateSpecialist) {
		this.saveOrUpdateSpecialist = saveOrUpdateSpecialist;
	}

	public int getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(int selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

}
