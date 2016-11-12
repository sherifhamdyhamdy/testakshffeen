package com.sget.akshef.view.admin.branch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.ContentForMobileService;
import com.sget.akshef.view.admin.AdminControl;

/**
 * 
 * @author JDeeb
 *
 */

public class OffersContent extends AdminControl implements Serializable{

	private static final long serialVersionUID = 8578344232151953542L;
	// Services and global Variables
	private ContentDetailsService service = null ;
	private ContentForMobileService mobService = null ;
	// Attributes
	private List<ContentDetailsBean> contents = null ;
	private ContentDetailsBean selectedContent = null ;
	private List<ContentCategoryBean> categories = null ;
	private int category = 0 ;
	private List<SelectItem> categoriesSelectItems = null ;
	private boolean isUpdate = false;
	
	// Content Details
	private ContentDetailsBean updateBean ;
	private ContentDetailsBean contentDetailsBean ;
	
	private int branchId = 72 ;
	// Functions
	public OffersContent(){
		service = new ContentDetailsService();
		mobService = new ContentForMobileService();
		categoriesSelectItems = new ArrayList<SelectItem>();
		// For Test
		populateCategories(DBConstants.CONTENT_TYPE_OFFERS);
		
//		if(categories != null && categories.size() > 0)
//			contents = service.getContentByBranchAndType(DBConstants.CONTENT_TYPE_OFFERS, categories.get(0).getId(), branchId);
	}
	/**
	 * Populate ContentDetails dependent on ContentCategory
	 * 
	 */
	public void populateContentDetails(){
		if(category > 0){
//			contents = service.getContentByBranchAndType(DBConstants.CONTENT_TYPE_OFFERS, category, branchId);
			if(contents == null || contents.size() == 0){
				addFacesMessage(FacesMessage.SEVERITY_INFO, "NO DATA !!", "NO DATA !!");
				//FacesContext.getCurrentInstance().addMessage("mainPanel", new FacesMessage(FacesMessage.SEVERITY_ERROR,"asdasd", "asdas"));
			}
		}else{
			contents = null ;
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input !!", "Invalid Input !!");
		}
	}
	
	/**
	 * get Content Categories by Category
	 * @param contType
	 */
	public void populateCategories(int contType){
		if(contType == DBConstants.CONTENT_TYPE_NEWS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_NEWS,"");
		}else if(contType == DBConstants.CONTENT_TYPE_OFFERS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_OFFERS,"");
		}else if(contType == DBConstants.CONTENT_TYPE_TIPS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_TIPS,"");
		}else{
			categories = null ;
		}
		
		if(categories == null || categories.size() == 0){
			addFacesMessage(FacesMessage.SEVERITY_WARN, " No Categories !!", " No Categories !! <br><br>");
		}else{
			// Clear Old Items
			if(categoriesSelectItems != null)
				categoriesSelectItems.clear();
			else
				categoriesSelectItems = new ArrayList<SelectItem>();
			SelectItem item = null ;
			for(ContentCategoryBean bean : categories){
				item = new SelectItem();
				item.setLabel(bean.getNameAr());
				item.setValue(bean.getId());
				
				categoriesSelectItems.add(item);
			}
		}
	}
	
	// For Update Content
	public void prepareContentUpdate(){
		contentDetailsBean = updateBean ;
		isUpdate = true;
	}
	// For Add New Content
	public void prepareContentAdd(){
		contentDetailsBean = new ContentDetailsBean();
		isUpdate = false;
	}
	// Save Or Update
	public void updateOrSaveFun(){
		if(isUpdate){
			boolean status = service.update(contentDetailsBean);
			if(status){
				addFacesMessage(FacesMessage.SEVERITY_INFO, "Updated", "Content Updated Success");
			}else{
				addFacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", "Fail To Update");
			}
		}else{
			service.insert(contentDetailsBean);
			if(contentDetailsBean.getId() > 0){
				addFacesMessage(FacesMessage.SEVERITY_INFO, "Inserted", "Content Inserted Success");
			}else{
				addFacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", "Fail To Insert");
			}
		}
	}
	// Setters and Getters
	public List<ContentDetailsBean> getContents() {
		return contents;
	}
	public void setContents(List<ContentDetailsBean> contents) {
		this.contents = contents;
	}
	public ContentDetailsBean getSelectedContent() {
		return selectedContent;
	}
	public void setSelectedContent(ContentDetailsBean selectedContent) {
		this.selectedContent = selectedContent;
	}
	public List<ContentCategoryBean> getCategories() {
		return categories;
	}
	public void setCategories(List<ContentCategoryBean> categories) {
		this.categories = categories;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public List<SelectItem> getCategoriesSelectItems() {
		return categoriesSelectItems;
	}
	public void setCategoriesSelectItems(List<SelectItem> categoriesSelectItems) {
		this.categoriesSelectItems = categoriesSelectItems;
	}
	public ContentDetailsBean getUpdateBean() {
		return updateBean;
	}
	public void setUpdateBean(ContentDetailsBean updateBean) {
		this.updateBean = updateBean;
	}
	public ContentDetailsBean getContentDetailsBean() {
		if(contentDetailsBean == null)
			contentDetailsBean = new ContentDetailsBean();
		return contentDetailsBean;
	}
	public void setContentDetailsBean(ContentDetailsBean contentDetailsBean) {
		this.contentDetailsBean = contentDetailsBean;
	}
	
}
