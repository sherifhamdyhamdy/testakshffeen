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

import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.service.SectionsService;

/**
 * @author Abdelazeem
 * 
 */
public class SectionControllerAll implements Serializable{
	
	private static final long serialVersionUID = -4088250537935741042L;
	
	private SectionsBean sectionBean;
	private SectionsService sectionService;
	private List<SectionsBean> sectionBeans = null;
	private SectionsBean[] selectedSection;
	private int selectedSectionLength;
	private String selected;
	private String message = "";

	// Constructor
	public SectionControllerAll() {
		sectionBean = new SectionsBean();
		sectionService = new SectionsService();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		//////////////////////////////////////////////////////////////////
		getAll();
		int arrayCount = sectionBeans.size();
		selectedSection = new SectionsBean[arrayCount];	
		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");
	}
	// Delete
	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;
		if (selectedSection.length < 1) {
			message = "select at least one";
			return;
		}
		for (int count = 0; count < selectedSection.length; count++) {
			sectionBean = selectedSection[count];
			deleteStatus = sectionService.delete(sectionBean);
			if (deleteStatus)
				deleteCount++;
		}
		if (deleteCount == selectedSection.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();
	}
	
	// update
	public void update() {
		if (selectedSection.length != 1) {
			message = "select one";
			return;
		}
		if (selectedSection != null && selectedSection.length == 1) {
			String page = "/systemadmin/sectionAdd.xhtml?id="
					+ selectedSection[0].getId();
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + page);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	// getAll
	public void getAll() {
		sectionBeans = new ArrayList<SectionsBean>();
		sectionBeans.addAll(sectionService.getAll());
	}
	// Redirect Page
	public void redirecttoPage(String page) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + page);
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,e.getMessage());
		}
	}
	// getter & setter
	public SectionsBean getSectionsBean() {
		return sectionBean;
	}
	public void setSectionsBean(SectionsBean sectionBean) {
		this.sectionBean = sectionBean;
	}
	public SectionsService getSectionService() {
		return sectionService;
	}
	public void setSectionService(SectionsService sectionService) {
		this.sectionService = sectionService;
	}
	public List<SectionsBean> getSectionBeans() {
		return sectionBeans;
	}
	public void setSectionBeans(List<SectionsBean> sectionBeans) {
		this.sectionBeans = sectionBeans;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public SectionsBean[] getSelectedSection() {
		return selectedSection;
	}
	public void setSelectedSection(SectionsBean[] selectedSection) {
		this.selectedSection = selectedSection;
		selectedSectionLength = selectedSection.length;
	}
	public int getSelectedSectionLength() {
		return selectedSectionLength;
	}
	public void setSelectedSectionLength(int selectedSectionLength) {
		this.selectedSectionLength = selectedSectionLength;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
