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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.CityService;

/**
 * @author Abdelazeem
 * 
 */
public class CityControllerAll implements Serializable {
	private static final long serialVersionUID = 2557344324591978690L;
	
	private CityBean cityBean;
	private CityService cityService;
	private List<CityBean> cityBeans = null;
	private CityBean[] selectedCity;
	private int selectedCityLength;
	private String selected;
	private String message = "";
	/////////////////////////////////////  make variable  for  update  and  delete //////  and  make set and get for its //////////////
	private boolean updaterender  = false;
	private boolean deleterender  = false;
/////////////////////////////////////////////////////////////////////////////////////////////////////

	public CityControllerAll() {

		cityBean = new CityBean();
		cityService = new CityService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		// cityBeans = new ArrayList<CityBean>();
		// if (cityBeans == null)
		// getAll();
		// if (cityBeans != null && cityBeans.size() == 0)
		// getAll();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
		if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
		// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
		for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

		// System.out.println("CategoryControllerAll "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
		if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("City")){
		if(roleHasPermissionBean.getPermision().getId() == 4){
		deleterender = true;
		}
		if(roleHasPermissionBean.getPermision().getId() == 3){
		updaterender = true;
		}
		}
		}
		}

		getAll();

		int arrayCount = cityBeans.size();
		selectedCity = new CityBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedCity  delete fun ::: "+ selectedCity.length);

		if (selectedCity.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedCity.length; count++) {

			cityBean = selectedCity[count];

			deleteStatus = cityService.delete(cityBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedCity.length)
			message = "deleted";
		else
			message = "Cannot  delete this city as it depend on other district";
		getAll();

		/*
		 * cityBean = new CityBean();
		 * cityBean.setId(Integer.parseInt(selected));
		 * cityService.delete(cityBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedCity.length);

		if (selectedCity.length != 1) {
			message = "select one";
			return;
		}

		if (selectedCity != null && selectedCity.length == 1) {
			String page = "/systemadmin/cityAdd.xhtml?id="
					+ selectedCity[0].getId();
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

		cityBeans = new ArrayList<CityBean>();
		cityBeans.addAll(cityService.getAll());

		// add to test only
		// System.out.println("cityBeans.size(): " + cityBeans.size());
		/*for (CityBean bean : cityBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getNameAr());
			// System.out.println("bean.getNameAr() : " + bean.getNameEn());
		}*/

	}

	// /geter setter

	public CityBean getCityBean() {
		return cityBean;
	}

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public List<CityBean> getCityBeans() {
		return cityBeans;
	}

	public void setCityBeans(List<CityBean> cityBeans) {
		this.cityBeans = cityBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public CityBean[] getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(CityBean[] selectedCity) {
		this.selectedCity = selectedCity;
		selectedCityLength = selectedCity.length;
	}

	public int getSelectedCityLength() {
		return selectedCityLength;
	}

	public void setSelectedCityLength(int selectedCityLength) {
		this.selectedCityLength = selectedCityLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

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

}
