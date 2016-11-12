package com.sget.akshef.view;

import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.InsuranceCompanyBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.InsuranceCompanyService;

public class InsuranceCompanyControllerAdd {
	private InsuranceCompanyBean insuranceCompanyBean = null;
	private InsuranceCompanyService insuranceCompanyService = null;
	private String SelectedId = null;
	private String saveOrUpdateInsuranceCompany = "saveInsuranceCompany";
	private boolean saverender = false;

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	public InsuranceCompanyControllerAdd() {
		insuranceCompanyService = new InsuranceCompanyService();
		insuranceCompanyBean = new InsuranceCompanyBean();
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
						.equalsIgnoreCase("InsuranceCompany")) {

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
				saveOrUpdateInsuranceCompany = "updateInsuranceCompany";
			}
			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {

				insuranceCompanyBean = insuranceCompanyService.getById(Integer
						.parseInt(SelectedId));

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// saveInsuranceCompany
	public void saveInsuranceCompany(ActionEvent action) {

		// System.out.println("insuranceCompanyBean11    ===   ::save2");

		if (insuranceCompanyBean.getId() != 0) {
			insuranceCompanyService.update(insuranceCompanyBean);
		} else {
			insuranceCompanyService.insert(insuranceCompanyBean);
		}
		insuranceCompanyBean = new InsuranceCompanyBean();

	}

	public InsuranceCompanyBean getInsuranceCompanyBean() {
		return insuranceCompanyBean;
	}

	public void setInsuranceCompanyBean(
			InsuranceCompanyBean insuranceCompanyBean) {
		this.insuranceCompanyBean = insuranceCompanyBean;
	}

	public InsuranceCompanyService getInsuranceCompanyService() {
		return insuranceCompanyService;
	}

	public void setInsuranceCompanyService(
			InsuranceCompanyService insuranceCompanyService) {
		this.insuranceCompanyService = insuranceCompanyService;
	}

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	public String getSaveOrUpdateInsuranceCompany() {
		return saveOrUpdateInsuranceCompany;
	}

	public void setSaveOrUpdateInsuranceCompany(
			String saveOrUpdateInsuranceCompany) {
		this.saveOrUpdateInsuranceCompany = saveOrUpdateInsuranceCompany;
	}

}
