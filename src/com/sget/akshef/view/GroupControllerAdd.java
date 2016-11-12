package com.sget.akshef.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.ContentCategoryService;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.GroupsService;
import com.sget.akshef.hibernate.service.PriorityService;
import com.sget.akshef.hibernate.service.UnitService;
import com.sget.akshef.hibernate.service.UsersService;

//@javax.faces.bean.ManagedBean(name="branchControllerAdd")
//@ViewScoped
public class GroupControllerAdd {
	private GroupsBean groupBean = null;

	private int selectedUnit;
	private ArrayList<SelectItem> menu_itemsUnits;

	private int selectedBranch;
	private ArrayList<SelectItem> menu_itemsBranchs;

	private GroupsService groupsService = null;
	private UnitService unitService = null;
	private BranchService branchService = null;

	private String SelectedId = null;
	private String saveOrUpdateGroups = "saveGroups";
	private boolean saverender = false;

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	public GroupControllerAdd() {
		groupsService = new GroupsService();
		unitService = new UnitService();
		branchService = new BranchService();
		groupBean = new GroupsBean();

		menu_itemsUnits = new ArrayList<SelectItem>();
		menu_itemsUnits.add(new SelectItem(String.valueOf(""), ""));
		for (UnitBean unitBean : unitService.getAll()) {
			menu_itemsUnits.add(new SelectItem(
					String.valueOf(unitBean.getId()), unitBean.getNameAr()));
		}

		menu_itemsBranchs = new ArrayList<SelectItem>();
		menu_itemsBranchs.add(new SelectItem(String.valueOf(""), ""));
		for (BranchBean branchBean : branchService.getAll()) {
			menu_itemsBranchs.add(new SelectItem(String.valueOf(branchBean
					.getId()), branchBean.getNameAr()));
		}

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		// ////////////////////////////////////////////////////////////////////////forAdd
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans = (List<RoleHasPermissionBean>) session
				.getAttribute("roleHasPermissionBeans");
		if (roleHasPermissionBeans != null && roleHasPermissionBeans.size() > 0) {
			// System.out.println("roleHasPermissionBeans.size() "+ roleHasPermissionBeans.size());
			for (RoleHasPermissionBean roleHasPermissionBean : roleHasPermissionBeans) {

				if (roleHasPermissionBean.getRole().getName().trim()
						.equalsIgnoreCase("Group")) {

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
				groupBean = groupsService.getById(Integer.parseInt(SelectedId));
				selectedUnit = groupBean.getUnitId();
				selectedBranch = groupBean.getBranchId();
			}
			if (groupBean.getId() != 0) {
				saveOrUpdateGroups = "updateGroups";
			} else {
				saveOrUpdateGroups = "saveGroups";
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// saveContentDetails
	public void saveGroups(ActionEvent action) {

		// System.out.println("groupssBean11    ===   ::save2");
		// System.out.println("groupssBean    ===   " + groupBean.getId());

		// System.out.println("selectedBranch    ===   " + selectedBranch);
		// System.out.println("selectedBranch    ===   " + selectedBranch);

		groupBean.setBranchId(selectedBranch);
		groupBean.setUnitId(selectedUnit);

		if (groupBean.getId() != 0) {
			groupsService.update(groupBean);
		} else {
			groupsService.insert(groupBean);
		}
		groupBean = new GroupsBean();
		if (groupBean.getId() != 0) {
			saveOrUpdateGroups = "updateGroups";
		} else {
			saveOrUpdateGroups = "saveGroups";
		}
		selectedBranch = 0;
		selectedUnit = 0;
	}

	public GroupsBean getGroupBean() {
		return groupBean;
	}

	public void setGroupBean(GroupsBean groupBean) {
		this.groupBean = groupBean;
	}

	public int getSelectedUnit() {
		return selectedUnit;
	}

	public void setSelectedUnit(int selectedUnit) {
		this.selectedUnit = selectedUnit;
	}

	public ArrayList<SelectItem> getMenu_itemsUnits() {
		return menu_itemsUnits;
	}

	public void setMenu_itemsUnits(ArrayList<SelectItem> menu_itemsUnits) {
		this.menu_itemsUnits = menu_itemsUnits;
	}

	public int getSelectedBranch() {
		return selectedBranch;
	}

	public void setSelectedBranch(int selectedBranch) {
		this.selectedBranch = selectedBranch;
	}

	public ArrayList<SelectItem> getMenu_itemsBranchs() {
		return menu_itemsBranchs;
	}

	public void setMenu_itemsBranchs(ArrayList<SelectItem> menu_itemsBranchs) {
		this.menu_itemsBranchs = menu_itemsBranchs;
	}

	public String getSaveOrUpdateGroups() {
		return saveOrUpdateGroups;
	}

	public void setSaveOrUpdateGroups(String saveOrUpdateGroups) {
		this.saveOrUpdateGroups = saveOrUpdateGroups;
	}

}
