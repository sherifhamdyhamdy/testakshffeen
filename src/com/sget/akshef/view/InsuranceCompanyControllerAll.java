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

import com.sget.akshef.hibernate.beans.InsuranceCompanyBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.InsuranceCompanyService;

/**
 * @author Abdelazeem
 * 
 */
public class InsuranceCompanyControllerAll {
	private InsuranceCompanyBean insuranceCompanyBean;
	private InsuranceCompanyService insuranceCompanyService;
	private List<InsuranceCompanyBean> insuranceCompanyBeans = null;
	private InsuranceCompanyBean[] selectedInsuranceCompany;
	private int selectedInsuranceCompanyLength;
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

	public InsuranceCompanyControllerAll() {

		insuranceCompanyBean = new InsuranceCompanyBean();
		insuranceCompanyService = new InsuranceCompanyService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// insuranceCompanyBeans = new ArrayList<InsuranceCompanyBean>();
		// if (insuranceCompanyBeans == null)
		// getAll();
		// if (insuranceCompanyBeans != null && insuranceCompanyBeans.size() ==
		// 0)
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
						.equalsIgnoreCase("InsuranceCompany")) {
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

		int arrayCount = insuranceCompanyBeans.size();
		selectedInsuranceCompany = new InsuranceCompanyBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedInsuranceCompany  delete fun ::: "+ selectedInsuranceCompany.length);

		if (selectedInsuranceCompany.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedInsuranceCompany.length; count++) {

			insuranceCompanyBean = selectedInsuranceCompany[count];
			InsuranceCompanyBean beanSelected=checkIfCompExist(insuranceCompanyBean);
			if(beanSelected==null)
			{
				deleteStatus = insuranceCompanyService.delete(insuranceCompanyBean);	
			}
			else
			{
				deleteStatus = insuranceCompanyService.delete(beanSelected);	
			}

			
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedInsuranceCompany.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * insuranceCompanyBean = new InsuranceCompanyBean();
		 * insuranceCompanyBean.setId(Integer.parseInt(selected));
		 * insuranceCompanyService.delete(insuranceCompanyBean);
		 */
	}

	//check If selected exist
	
	private InsuranceCompanyBean checkIfCompExist(InsuranceCompanyBean insComp) 
	{
		for(InsuranceCompanyBean bean:insuranceCompanyBeans)
		{
			if(bean.getNameAr().equals(insComp.getNameAr()))
					{
				return bean;
					}
		}
		
		
		
		return null;
	}
	
	
	
	
	
	
	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  "+ selectedInsuranceCompany.length);

		if (selectedInsuranceCompany.length != 1) {
			message = "select one";
			return;
		}

		if (selectedInsuranceCompany != null
				&& selectedInsuranceCompany.length == 1) {
			String page = "/systemadmin/insuranceCompanyAdd.xhtml?id="
					+ selectedInsuranceCompany[0].getId();
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

		insuranceCompanyBeans = new ArrayList<InsuranceCompanyBean>();
		insuranceCompanyBeans.addAll(insuranceCompanyService.getAll());

		// add to test only
		// System.out.println("insuranceCompanyBeans.size(): "+ insuranceCompanyBeans.size());
		/*for (InsuranceCompanyBean bean : insuranceCompanyBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getNameAr());
			// System.out.println("bean.getNameAr() : " + bean.getNameEn());
			// System.out.println("bean.getNameAr() : " + bean.getAddress());
		}*/

	}

	// /geter setter

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

	public List<InsuranceCompanyBean> getInsuranceCompanyBeans() {
		return insuranceCompanyBeans;
	}

	public void setInsuranceCompanyBeans(
			List<InsuranceCompanyBean> insuranceCompanyBeans) {
		this.insuranceCompanyBeans = insuranceCompanyBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public InsuranceCompanyBean[] getSelectedInsuranceCompany() {
		return selectedInsuranceCompany;
	}

	public void setSelectedInsuranceCompany(
			InsuranceCompanyBean[] selectedInsuranceCompany) {
		this.selectedInsuranceCompany = selectedInsuranceCompany;
		selectedInsuranceCompanyLength = selectedInsuranceCompany.length;
	}

	public int getSelectedInsuranceCompanyLength() {
		return selectedInsuranceCompanyLength;
	}

	public void setSelectedInsuranceCompanyLength(
			int selectedInsuranceCompanyLength) {
		this.selectedInsuranceCompanyLength = selectedInsuranceCompanyLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
