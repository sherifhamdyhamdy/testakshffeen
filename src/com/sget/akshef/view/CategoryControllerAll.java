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

import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.service.CategoryService;

/**
 * @author Abdelazeem
 * 
 */
public class CategoryControllerAll implements Serializable{
	
	private static final long serialVersionUID = 5896356054597496885L;
	
	private CategoryBean categoryBean;
	private CategoryService categoryService;
	private List<CategoryBean> categoryBeans = null;
	private CategoryBean[] selectedCategory;
	private int selectedCategoryLength;
	private String selected;
	private String message = "messages";

	public CategoryControllerAll() {

		categoryBean = new CategoryBean();
		categoryService = new CategoryService();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		getAll();

		int arrayCount = categoryBeans.size();
		selectedCategory = new CategoryBean[arrayCount];

		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");
		
	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedCategory  delete fun ::: "+ selectedCategory.length);

		if (selectedCategory.length < 1) {
			message = "select at least one";
			return;
		}

		for (int count = 0; count < selectedCategory.length; count++) {

			categoryBean = selectedCategory[count];

			deleteStatus = categoryService.delete(categoryBean);
			if (deleteStatus)
				deleteCount++;

		}
		if (deleteCount == selectedCategory.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();

		/*
		 * categoryBean = new CategoryBean();
		 * categoryBean.setId(Integer.parseInt(selected));
		 * categoryService.delete(categoryBean);
		 */
	}

	public void updateeeeee() {
		// System.out.println("selectedCategory  update fun ::: "+ selectedCategory.length);
		if (selectedCategory.length != 1)
			return;
		redirecttoPage("/systemadmin/addCategories.xhtml?selected="
				+ selectedCategory[0].getId());

	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedCategory.length);

		if (selectedCategory.length != 1) {
			message = "select one and only one";
			return;
		}

		if (selectedCategory != null && selectedCategory.length == 1) {
			String page = "/systemadmin/categoryAdd.xhtml?id="
					+ selectedCategory[0].getId();
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

	// getAll
	public void getAll() {
		categoryBeans = new ArrayList<CategoryBean>();
		categoryBeans.addAll(categoryService.getAll());
	}

	// getter & setter
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
	public List<CategoryBean> getCategoryBeans() {
		return categoryBeans;
	}
	public void setCategoryBeans(List<CategoryBean> categoryBeans) {
		this.categoryBeans = categoryBeans;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public CategoryBean[] getSelectedCategory() {
		return selectedCategory;
	}
	public void setSelectedCategory(CategoryBean[] selectedCategory) {
		this.selectedCategory = selectedCategory;
		selectedCategoryLength = selectedCategory.length;
	}
	public int getSelectedCategoryLength() {
		return selectedCategoryLength;
	}
	public void setSelectedCategoryLength(int selectedCategoryLength) {
		this.selectedCategoryLength = selectedCategoryLength;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
