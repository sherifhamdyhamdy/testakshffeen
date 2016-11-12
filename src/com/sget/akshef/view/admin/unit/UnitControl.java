package com.sget.akshef.view.admin.unit;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.constants.AppUtil;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.CategoryService;
import com.sget.akshef.hibernate.service.UnitService;

/**
 * 
 * @author JDeeb
 *
 * Main Managed bean for Edit Unit Details
 */
@ManagedBean(name="unitCont")
@ViewScoped
public class UnitControl implements Serializable{

	private static final long serialVersionUID = 7065717703873026262L;
	// Variables
	private UnitService unitService = null;
	private CategoryService categoryService = null;
	private UnitBean unitBean = null;
	private ArrayList<SelectItem> menu_items = null;
	
	private String unitLogoImage = null;
	private int unitId = 0 ;
	
	// Functions
	public UnitControl(){
		unitService = new UnitService();
		categoryService = new CategoryService();
		unitBean = new UnitBean();
		
		// Populate Functions
		populateCategories();
		
		unitId = 5;
		if(unitId > 0){
			// get Unit Bean from DB
			unitBean = unitService.getById(unitId);
			unitLogoImage = unitBean.getUnitlogo();
		}else{
			ExternalContext ec = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				ec.redirect(ec.getRequestContextPath());
			} catch (IOException e) {
				Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
						e.getMessage());
			}
		}
	}
	
	// Populate Categories Menu
	private void populateCategories(){
		menu_items = new ArrayList<SelectItem>();
		menu_items.add(new SelectItem("","select"));
		List<CategoryBean> categoryBeans = categoryService.getAll();
		if(categoryBeans != null && categoryBeans.size() > 0){
			for (CategoryBean bean : categoryBeans) {
				menu_items.add(new SelectItem(bean.getId(), bean.getNameAr()));
			}
		}
	}
	
	// For Upload Image
    public void handelUpload(FileUploadEvent event){
        UploadedFile file = event.getFile();
        unitLogoImage = new AppUtil().uploadImage(file,DBConstants.UNIT_IMAGES_UPLOADS);
    }
	
	// Save Unit
	public void saveUnit(ActionEvent action) {
		if( ((unitBean.getNameAr().trim().equalsIgnoreCase("")) && (unitBean.getNameEn().trim().equalsIgnoreCase("")) )){
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 					"Error", "Enter The Data"));
			return;
		}

		// In case of Update or insert new
		if(unitBean.getId() != 0){

			unitBean.setUnitlogo(unitLogoImage);
			boolean up = unitService.update(unitBean);
 			if(up)
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
 					"Updated", "Updated Successfully"));
 			else
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Error in Update"));
		}
//		unitBean = new UnitBean();
//		unitLogoImage = "";
	}
	
	// Setters & Getters
	public UnitService getUnitService() {
		return unitService;
	}
	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public UnitBean getUnitBean() {
		return unitBean;
	}
	public void setUnitBean(UnitBean unitBean) {
		this.unitBean = unitBean;
	}
	public ArrayList<SelectItem> getMenu_items() {
		return menu_items;
	}
	public void setMenu_items(ArrayList<SelectItem> menu_items) {
		this.menu_items = menu_items;
	}
	public String getUnitLogoImage() {
		return unitLogoImage;
	}
	public void setUnitLogoImage(String unitLogoImage) {
		this.unitLogoImage = unitLogoImage;
	}
}
