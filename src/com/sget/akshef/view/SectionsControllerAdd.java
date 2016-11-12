package com.sget.akshef.view;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.service.SectionsService;

/**
 * 
 * @author JDeeb
 *
 */

public class SectionsControllerAdd implements Serializable{

	private static final long serialVersionUID = -8665935095214939325L;
	
	private SectionsBean sectionsBean = null;
	private SectionsService sectionsService = null;
	
	// Functions
	public SectionsControllerAdd() {
		sectionsBean = new SectionsBean();
		sectionsService = new SectionsService();
		
		///////////////
		
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			if (id != null && !id.equalsIgnoreCase("")) {
				sectionsBean=sectionsService.getById(Integer.parseInt(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void saveSection(ActionEvent action) {
		if(sectionsBean == null){
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 					"Error", "Error in System"));
			return;
		}
		if(sectionsBean.getId() != 0){
			boolean up = sectionsService.update(sectionsBean);
			if(up){
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Updated", "Updated Successfully"));
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Fail to Update"));
			}
		}else{
			int exist = sectionsService.checkSectionExist(sectionsBean.getNameAr(), sectionsBean.getNameEn());
			if(exist == 2){
				sectionsService.insert(sectionsBean);
				if(sectionsBean.getId() > 0){
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Inserted", "Inserted Successfully"));
				}else
	 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 	 					"Error", "Error in Insert"));
			}else if(exist == 1){
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 					"Error", "already Exist !!!"));
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 					"Error", "Error in System !!!"));
			}
		}
		sectionsBean = new SectionsBean();
	}

	// Setters & Getters
	public SectionsBean getSectionsBean() {
		return sectionsBean;
	}
	public void setSectionsBean(SectionsBean sectionsBean) {
		this.sectionsBean = sectionsBean;
	}
}
