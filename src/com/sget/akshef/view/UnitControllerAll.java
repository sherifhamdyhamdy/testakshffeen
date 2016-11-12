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

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.service.UnitService;

/**
 * @author Abdelazeem
 * 
 */

public class UnitControllerAll implements Serializable{
	
	private static final long serialVersionUID = 742341190791257905L;
	
	private UnitBean unitBean;
	private UnitService unitService;
	private List<UnitBean> unitBeans = null;
	private UnitBean[] selectedUnit;
	private int selectedUnitLength;
	private String selected;
	private String message = "";

	public UnitControllerAll() {

		unitBean = new UnitBean();
		unitService = new UnitService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		
		getAll();
		
		int arrayCount = unitBeans.size();
		selectedUnit = new UnitBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;
		if (selectedUnit.length < 1) {
			message = "select at least one";
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, 
 					"Error", "Select at Least One !!!"));
			return;
		}
		for (int count = 0; count < selectedUnit.length; count++) {
			unitBean = selectedUnit[count];
			deleteStatus = unitService.delete(unitBean);
			if (deleteStatus)
				deleteCount++;
		}
		if (deleteCount == selectedUnit.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();
	}

	// update
	public void update() {
		if (selectedUnit.length != 1) {
			message = "select one";
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, 
 					"Error", "Select One Unit !!!"));
			return;
		}
		if (selectedUnit != null && selectedUnit.length == 1) {
			String page = "/systemadmin/unitAdd.xhtml?id="
					+ selectedUnit[0].getId();
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + page);
			} catch (IOException e) {
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

	// get All Units
	public void getAll() {
		unitBeans = new ArrayList<UnitBean>();
		unitBeans.addAll(unitService.getAll());
	}

	// getter setter
	public UnitBean getUnitBean() {
		return unitBean;
	}
	public void setUnitBean(UnitBean unitBean) {
		this.unitBean = unitBean;
	}
	public UnitService getUnitService() {
		return unitService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public List<UnitBean> getUnitBeans() {
		return unitBeans;
	}
	public void setUnitBeans(List<UnitBean> unitBeans) {
		this.unitBeans = unitBeans;
	}
	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	public UnitBean[] getSelectedUnit() {
		return selectedUnit;
	}
	public void setSelectedUnit(UnitBean[] selectedUnit) {
		this.selectedUnit = selectedUnit;
		selectedUnitLength = selectedUnit.length;
	}
	public int getSelectedUnitLength() {
		return selectedUnitLength;
	}
	public void setSelectedUnitLength(int selectedUnitLength) {
		this.selectedUnitLength = selectedUnitLength;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
