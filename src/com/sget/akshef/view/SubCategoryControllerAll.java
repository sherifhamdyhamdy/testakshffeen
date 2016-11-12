package com.sget.akshef.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.service.SubcategoryService;


/**
 * @author Abdelazeem
 * 
 */
public class SubCategoryControllerAll implements Serializable{
	
	private static final long serialVersionUID = -7615348816541709197L;
	
	private SubcategoryBean subcategoryBean;
	private SubcategoryService subCategoryService;
	private List<SubcategoryBean> subCategoryBeans = null;
	private SubcategoryBean[] selectedSubCategory;
	private int selectedSubCategoryLength;
	private String message = "";
	
	// SubCategory Constructor
	public SubCategoryControllerAll() {
		subcategoryBean = new SubcategoryBean();
		subCategoryService = new SubcategoryService();
		
		getAll();
		
		int arrayCount = subCategoryBeans.size();
		selectedSubCategory = new SubcategoryBean[arrayCount];
		
	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		if (selectedSubCategory.length < 1) {
			message = "select at least one";
			return;
		}
		
		for (int count = 0; count < selectedSubCategory.length; count++) {
			subcategoryBean = selectedSubCategory[count];
			if(subcategoryBean != null && subcategoryBean.getId() > 0)
				//deleteStatus = subCategoryService.deleteSubcategory(subcategoryBean.getId());
				deleteStatus = subCategoryService.delete(subcategoryBean);
			if (deleteStatus)
				deleteCount++;
		}
		if (deleteCount == selectedSubCategory.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();
		
	}

	// update
	public void update() {
		if (selectedSubCategory.length != 1) {
			message = "select one";
			return;
		}
		if (selectedSubCategory != null && selectedSubCategory.length == 1) {
			String page = "/systemadmin/subCategoryAdd.xhtml?id="+ selectedSubCategory[0].getId();
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + page);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void redirecttoPage(String page) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + page);
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					e.getMessage());
		}
	}

	// getAll
	public void getAll() {
		subCategoryBeans = new ArrayList<SubcategoryBean>();
		subCategoryBeans.addAll(subCategoryService.getAll());
	}

	// Setters & Getters
	public SubcategoryBean getSubcategoryBean() {
		return subcategoryBean;
	}
	public void setSubcategoryBean(SubcategoryBean subcategoryBean) {
		this.subcategoryBean = subcategoryBean;
	}
	public SubcategoryService getCategoryService() {
		return subCategoryService;
	}
	public void setCategoryService(SubcategoryService subCategoryService) {
		this.subCategoryService = subCategoryService;
	}
	public List<SubcategoryBean> getSubcategoryBeans() {
		return subCategoryBeans;
	}
	public void setSubcategoryBeans(List<SubcategoryBean> subCategoryBeans) {
		this.subCategoryBeans = subCategoryBeans;
	}
	public SubcategoryBean[] getSelectedCategory() {
		return selectedSubCategory;
	}
	public void setSelectedCategory(SubcategoryBean[] selectedSubCategory) {
		this.selectedSubCategory = selectedSubCategory;
		selectedSubCategoryLength = selectedSubCategory.length;
	}
	public int getSelectedCategoryLength() {
		return selectedSubCategoryLength;
	}
	public void setSelectedCategoryLength(int selectedSubCategoryLength) {
		this.selectedSubCategoryLength = selectedSubCategoryLength;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
