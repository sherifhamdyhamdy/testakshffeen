package com.sget.akshef.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

@ManagedBean(name="unitControllerAdd")
@ViewScoped
public class UnitControllerAdd implements  Serializable{

	private static final long serialVersionUID = 3874471573910958703L;
	
	private UnitService unitService = null;
	private CategoryService categoryService = null;
	private UnitBean unitBean = null;
	private CategoryBean categoryBean = null;
	private ArrayList<SelectItem> menu_items = null;
	
	private String SelectedId = null;
	private String  saveorupdateUnit ="saveUnit";
	private String  msgsunit ="";
	private String unitLogoImage = null ;
	
	
	public UnitControllerAdd() {
		unitService = new UnitService();
		unitBean = new UnitBean();
		categoryBean = new CategoryBean();
		categoryService = new CategoryService();
		
		// Populate Functions
		populateCategories();
		
		// Get Security
		ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
		try {
			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");

			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
			}
  			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {
				unitBean = unitService.getById(Integer.parseInt(SelectedId));
				if(unitBean != null &&  unitBean.getId() != 0){
					categoryBean=unitBean.getCategory();
					saveorupdateUnit ="updateUnit";
					unitLogoImage = unitBean.getUnitlogo();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	
	// saveUnit
	public void saveUnit(ActionEvent action) {
		//unitBean.setUser(userUnit);
		if(categoryBean.getId() == 0){
			msgsunit ="select category ";			
			return;
		}
		if( ((unitBean.getNameAr().trim().equalsIgnoreCase("")) && (unitBean.getNameEn().trim().equalsIgnoreCase("")) )){
			msgsunit ="enter  the  data ";
			return;
		}
		msgsunit ="";
		// In case of Update or insert new
		if(unitBean.getId() != 0){
			if (categoryBean == null)
				return;
			unitBean.setCategory(categoryBean);
			unitBean.setUnitlogo(unitLogoImage);
			boolean up = unitService.update(unitBean);
 			if(up)
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
 					"Updated", "Updated Successfully"));
 			else
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Error in Update"));
		}else{
			if(categoryBean == null)
				return;
			int exist = unitService.checkIfUnitExist(unitBean.getNameAr(), unitBean.getNameEn());
			if(exist == 2){
				unitBean.setCategory(categoryBean);
				unitBean.setUnitlogo(unitLogoImage);
				unitService.insert(unitBean);
				if(unitBean.getId() > 0)
	 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
	 					"Inserted", "Inserted Successfully"));
				else{
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		 					"Error", "Error in Inserted"));
					new AppUtil().deleteImage(unitBean.getUnitlogo(), DBConstants.UNIT_IMAGES_UPLOADS);
				}
			}else if(exist == 1){
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 					"Error", "already Exist !!!"));
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 					"Error", "Error in System !!!"));
			}			
		}
		
		saveorupdateUnit=  "saveUnit";
		unitBean = new UnitBean();
		categoryBean = new CategoryBean();
		unitLogoImage = "";
	}
	
	// For Upload Image
    public void handelUpload(FileUploadEvent event){
        UploadedFile file = event.getFile();
        unitLogoImage = new AppUtil().uploadImage(file,DBConstants.UNIT_IMAGES_UPLOADS);
    }
    
	// Setters && Getters	
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
	public String getSelectedId() {
		return SelectedId;
	}
	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}
	public CategoryBean getCategoryBean() {
		return categoryBean;
	}
	public void setCategoryBean(CategoryBean categoryBean) {
		this.categoryBean = categoryBean;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public ArrayList<SelectItem> getMenu_items() {
		return menu_items;
	}
	public void setMenu_items(ArrayList<SelectItem> menu_items) {
		this.menu_items = menu_items;
	}
	public String getMsgsunit() {
		return msgsunit;
	}
	public void setMsgsunit(String msgsunit) {
		this.msgsunit = msgsunit;
	}
	public String getUnitLogoImage() {
		return unitLogoImage;
	}
	public void setUnitLogoImage(String unitLogoImage) {
		this.unitLogoImage = unitLogoImage;
	}
	public String getSaveorupdateUnit() {
		return saveorupdateUnit;
	}
	public void setSaveorupdateUnit(String saveorupdateUnit) {
		this.saveorupdateUnit = saveorupdateUnit;
	}
}
