package com.sget.akshef.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.AppUtil;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.ContentForMobileService;
import com.sget.akshef.hibernate.service.PriorityService;

/**
 * 
 * @author JDeeb
 * Content Add Controller
 */
public class ContControllerAdd implements Serializable{
	
	private static final long serialVersionUID = 1714212814266373504L;
	
	// Services and global Variables
	private ContentDetailsService service = null ;
	private ContentForMobileService mobService = null ;
	// Attributes
	private List<SelectItem> categoriesSelectItems = null ;
	private ArrayList<SelectItem> priortitesSelectItems = null ;
	private boolean isUpdate = false;
	// Content Details
	private ContentDetailsBean contentDetailsBean ;
	
	private boolean saverender  = false;
	private String type ;
	private AppUtil appUtil = null ; 
	
	// Functions
	public ContControllerAdd(){
		service = new ContentDetailsService();
		mobService = new ContentForMobileService();
		appUtil = new AppUtil();
		
		// Populate Priorities
//		populatePriorities();
		
		////////////////////////////////////////
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
		if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
			for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){
				if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("ContentDetails")){
					if(roleHasPermissionBean.getPermision().getId() == 2){
						saverender = true;
						break;
					}
				}
			}
		}
		/////////////////////////////////////////
		try {
			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			type = maps.get("type");
			// Page Request
			if(type != null && !type.trim().equalsIgnoreCase("")){
				if(type.trim().equalsIgnoreCase(DBConstants.CONTENT_TYPE_NEWS+"")){
					populateCategories(DBConstants.CONTENT_TYPE_NEWS);
				}else if(type.trim().equalsIgnoreCase(DBConstants.CONTENT_TYPE_TIPS+"")){
					populateCategories(DBConstants.CONTENT_TYPE_TIPS);
				}else if(type.trim().equalsIgnoreCase(DBConstants.CONTENT_TYPE_OFFERS+"")){
					populateCategories(DBConstants.CONTENT_TYPE_OFFERS);
				}else if(type.trim().equalsIgnoreCase(DBConstants.CONTENT_TYPE_ARTICLE+"")){
					populateCategories(DBConstants.CONTENT_TYPE_ARTICLE);
				}else{
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,
							" Leah !!", " HARAAAAAAAAAAAAAAAM"));
				}
			}
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				contentDetailsBean = service.getById(Integer.parseInt(id));
				isUpdate = true ;
 			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * get Content Categories by Type
	 * @param contType
	 */
	private void populateCategories(int contType){
		categoriesSelectItems = new ArrayList<SelectItem>();
		
		List<ContentCategoryBean> categories = null ;
		if(contType == DBConstants.CONTENT_TYPE_NEWS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_NEWS,"");
		}else if(contType == DBConstants.CONTENT_TYPE_OFFERS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_OFFERS,"");
		}else if(contType == DBConstants.CONTENT_TYPE_TIPS){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_TIPS,"");
		}else if(contType == DBConstants.CONTENT_TYPE_ARTICLE){
			categories = mobService.getContTypeCategories(DBConstants.CONTENT_TYPE_ARTICLE,"");
		}else{
			categories = null ;
		}
		
		if(categories == null || categories.size() == 0){
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN,
					" No Categories !!", " No Categories !! <br>"));
		}else{
			// Clear Old Items
			if(categoriesSelectItems != null)
				categoriesSelectItems.clear();
			else
				categoriesSelectItems = new ArrayList<SelectItem>();
			
			categoriesSelectItems.add(new SelectItem("", "Select"));
			SelectItem item = null ;
			for(ContentCategoryBean bean : categories){
				item = new SelectItem();
				item.setLabel(bean.getNameAr());
				item.setValue(bean.getId());
				
				categoriesSelectItems.add(item);
			}
		}
	}
	// Populate Priorities
	/*private void populatePriorities(){
		PriorityService priorityService = new PriorityService();
		priortitesSelectItems = new ArrayList<SelectItem>();
		List<PriorityBean> priorties = priorityService.getAll();
		if(priorties != null && priorties.size() > 0){
			for (PriorityBean  priorityBean : priorties) {			 
				priortitesSelectItems.add(new SelectItem(String.valueOf(priorityBean.getId()),
						priorityBean.getNameAr()));
			}
		}
	}*/
	// Save Or Update
	public void updateOrSaveFun(){
		if(isUpdate){
			boolean status = service.update(contentDetailsBean);
			if(status){
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Updated", "Content Updated Success"));
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fail", "Fail To Update"));
			}
		}else{
			UsersBean user = new UsersBean();
			user.setId(3);
			contentDetailsBean.setUsers(user);
			
			service.insert(contentDetailsBean);
			if(contentDetailsBean.getId() > 0){
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Inserted", "Content Inserted Success"));
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Fail", "Fail To Insert"));
				// Delete All Images
				appUtil.deleteImage(contentDetailsBean.getSmallImage(), DBConstants.CONTENT_IMAGES_UPLOADS);
				appUtil.deleteImage(contentDetailsBean.getLargeImage(), DBConstants.CONTENT_IMAGES_UPLOADS);
//				appUtil.deleteImage(contentDetailsBean.getSmallLogo(), DBConstants.CONTENT_IMAGES_UPLOADS);
//				appUtil.deleteImage(contentDetailsBean.getLargeLogo(), DBConstants.CONTENT_IMAGES_UPLOADS);
			}
		}
		contentDetailsBean = new ContentDetailsBean();
	}
	// For Upload Image
    public void handelUpload(FileUploadEvent event){
        UploadedFile file = event.getFile();
        String componentId = event.getComponent().getId();
        String imageName = appUtil.uploadImage(file,DBConstants.CONTENT_IMAGES_UPLOADS);
        if(imageName != null && !imageName.trim().equalsIgnoreCase("")){
        	// contSmallImage - contLargeImage - contSmallLogo - contLargeLogo
        	if(componentId != null && componentId.trim().equalsIgnoreCase("contSmallImage")){
        		if(contentDetailsBean.getSmallImage() != null && !contentDetailsBean.getSmallImage().trim().equals("")){
        			appUtil.deleteImage(contentDetailsBean.getSmallImage(), DBConstants.CONTENT_IMAGES_UPLOADS);
        		}
        		contentDetailsBean.setSmallImage(imageName);
        	}else if(componentId != null && componentId.trim().equalsIgnoreCase("contLargeImage")){
        		if(contentDetailsBean.getLargeImage() != null && !contentDetailsBean.getLargeImage().trim().equals("")){
        			appUtil.deleteImage(contentDetailsBean.getLargeImage(), DBConstants.CONTENT_IMAGES_UPLOADS);
        		}
        		contentDetailsBean.setLargeImage(imageName);
        	}else if(componentId != null && componentId.trim().equalsIgnoreCase("contSmallLogo")){
//        		if(contentDetailsBean.getSmallLogo() != null && !contentDetailsBean.getSmallLogo().trim().equals("")){
//        			appUtil.deleteImage(contentDetailsBean.getSmallLogo(), DBConstants.CONTENT_IMAGES_UPLOADS);
//        		}
//        		contentDetailsBean.setSmallLogo(imageName);
        	}else if(componentId != null && componentId.trim().equalsIgnoreCase("contLargeLogo")){
//        		if(contentDetailsBean.getLargeLogo() != null && !contentDetailsBean.getLargeLogo().trim().equals("")){
//        			appUtil.deleteImage(contentDetailsBean.getLargeLogo(), DBConstants.CONTENT_IMAGES_UPLOADS);
//        		}
//        		contentDetailsBean.setLargeLogo(imageName);
        	}else{
        		RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
    					"Error !!!", "Error in System"));
        	}
        }else{
        	RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Error !!!", "Error in System"));
        }
    }
	// Setters and Getters
	public List<SelectItem> getCategoriesSelectItems() {
		return categoriesSelectItems;
	}
	public void setCategoriesSelectItems(List<SelectItem> categoriesSelectItems) {
		this.categoriesSelectItems = categoriesSelectItems;
	}
	public ArrayList<SelectItem> getPriortitesSelectItems() {
		return priortitesSelectItems;
	}
	public void setPriortitesSelectItems(ArrayList<SelectItem> priortitesSelectItems) {
		this.priortitesSelectItems = priortitesSelectItems;
	}
	public ContentDetailsBean getContentDetailsBean() {
		if(contentDetailsBean == null)
			contentDetailsBean = new ContentDetailsBean();
		return contentDetailsBean;
	}
	public void setContentDetailsBean(ContentDetailsBean contentDetailsBean) {
		this.contentDetailsBean = contentDetailsBean;
	}
	public boolean isSaverender() {
		return saverender;
	}
	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}