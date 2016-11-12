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

import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.service.CountryService;

/**
 * @author Abdelazeem
 * 
 */
public class CountryControllerAll implements Serializable{
	
	private static final long serialVersionUID = 8062372842471240408L;
	
	private CountryBean countryBean;
	private CountryService countryService;
	private List<CountryBean> countryBeans = null;
	private CountryBean[] selectedCountry;
	private int selectedCountryLength;
	private String selected;
	private String message = "";
	
	public CountryControllerAll() {

		countryBean = new CountryBean();
		countryService = new CountryService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		
		getAll();

		int arrayCount = countryBeans.size();
		selectedCountry = new CountryBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedCountry  delete fun ::: "+ selectedCountry.length);

		if (selectedCountry.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedCountry.length; count++) {

			countryBean = selectedCountry[count];

			deleteStatus = countryService.delete(countryBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedCountry.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * countryBean = new CountryBean();
		 * countryBean.setId(Integer.parseInt(selected));
		 * countryService.delete(countryBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedCountry.length);

		if (selectedCountry.length != 1) {
			message = "select one";
			return;
		}

		if (selectedCountry != null && selectedCountry.length == 1) {
			String page = "/systemadmin/countryAdd.xhtml?id="
					+ selectedCountry[0].getId();
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

		countryBeans = new ArrayList<CountryBean>();
		countryBeans.addAll(countryService.getAll());

		// add to test only
		// System.out.println("countryBeans.size(): " + countryBeans.size());
		/*for (CountryBean bean : countryBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
			// System.out.println("bean.getNameAr() : " + bean.getNameAr());
			// System.out.println("bean.getNameAr() : " + bean.getNameEn());
		}*/

	}

	// /geter setter

	public CountryBean getCountryBean() {
		return countryBean;
	}

	public void setCountryBean(CountryBean countryBean) {
		this.countryBean = countryBean;
	}

	public CountryService getCountryService() {
		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public List<CountryBean> getCountryBeans() {
		return countryBeans;
	}

	public void setCountryBeans(List<CountryBean> countryBeans) {
		this.countryBeans = countryBeans;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public CountryBean[] getSelectedCountry() {
		return selectedCountry;
	}

	public void setSelectedCountry(CountryBean[] selectedCountry) {
		this.selectedCountry = selectedCountry;
		selectedCountryLength = selectedCountry.length;
	}

	public int getSelectedCountryLength() {
		return selectedCountryLength;
	}

	public void setSelectedCountryLength(int selectedCountryLength) {
		this.selectedCountryLength = selectedCountryLength;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
