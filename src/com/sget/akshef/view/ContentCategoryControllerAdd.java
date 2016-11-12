package com.sget.akshef.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentTypesBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.constants.AppUtil;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.ContentCategoryService;
import com.sget.akshef.hibernate.service.ContentTypesService;

public class ContentCategoryControllerAdd {
	private ContentCategoryBean contentCategoryBean = null;
	private ContentCategoryService contentCategoryService = null;
	private boolean allowUpdate = false;
	private String SelectedId = null;

	private ContentTypesBean contentTypeBean = null;

	private List<ContentTypesBean> contentTypeBeans = null;
	private ContentTypesService contentTypeService = null;
	private ArrayList<SelectItem> menu_items;
	 String categoyLogoImage = "" ;

	
	private boolean saverender  = false;
	
    private String  saveorUpdateContentCategory ="saveContentCategory";
	public ContentCategoryControllerAdd() {
		contentCategoryService = new ContentCategoryService();
		contentCategoryBean = new ContentCategoryBean();

		contentTypeBean = new ContentTypesBean();

		contentTypeBeans = new ArrayList<ContentTypesBean>();
		contentTypeService = new ContentTypesService();
		menu_items = new ArrayList<SelectItem>();
		
		  menu_items.add(new SelectItem(null,"select"));
				contentTypeBeans = contentTypeService.getAll();
				for (ContentTypesBean bean : contentTypeBeans) {
					menu_items.add(new SelectItem(bean.getId(), bean.getNameAr()));

				}

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
		
		
		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
		if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
		// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
		for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

		// System.out.println("CategoryControllerAdd "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
		if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("ContentCategory")){

		if(roleHasPermissionBean.getPermision().getId() == 2){
		saverender = true;
		break;
		}
		}
		}
		}
		
		
		
		
		
		
		try {

			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			// System.out.println("id    ===   " + id);
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
				allowUpdate = true;
			}
			// System.out.println("allowUpdate    ===   " + allowUpdate);
			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {

				contentCategoryBean = contentCategoryService.getById(Integer
						.parseInt(SelectedId));
				contentTypeBean=	contentCategoryBean.getContentTypes();
				categoyLogoImage = contentCategoryBean.getCat_image();

				setSaveorUpdateContentCategory("UpdateContentCategory");
				

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		
	}

	// saveContentCategory
	public void saveContentCategory(ActionEvent action) {

		// // System.out.println("contentCategoryBean contentCategoryBean.getCategory().getId()   ===   "
		// + contentCategoryBean.getCategory().getId());

		if (contentCategoryBean.getId() != 0) {
			contentCategoryBean.setContentTypes(contentTypeBean);
			contentCategoryBean.setCat_image(categoyLogoImage);

			boolean up = contentCategoryService.update(contentCategoryBean);
			if(up)
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
 					"Updated", "Updated Successfully"));
 			else
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Error in Update"));
		} else {
			if (contentTypeBean == null)
				return;
			 contentCategoryBean.setContentTypes(contentTypeBean);
			 contentCategoryBean.setCat_image(categoyLogoImage);
			 contentCategoryService.insert(contentCategoryBean);
			 if(contentCategoryBean.getId() > 0)
	 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
	 					"Inserted", "Inserted Successfully"));
	 			else
	 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 	 					"Error", "Error in Insert"));
		}
		contentCategoryBean = new ContentCategoryBean();
		contentTypeBean =  new ContentTypesBean();
		categoyLogoImage = "";


	}

	public ContentCategoryBean getContentCategoryBean() {
		return contentCategoryBean;
	}

	public void setContentCategoryBean(ContentCategoryBean contentCategoryBean) {
		this.contentCategoryBean = contentCategoryBean;
	}

	public ContentCategoryService getContentCategoryService() {
		return contentCategoryService;
	}

	public void setContentCategoryService(
			ContentCategoryService contentCategoryService) {
		this.contentCategoryService = contentCategoryService;
	}

	public boolean isAllowUpdate() {
		return allowUpdate;
	}

	public void setAllowUpdate(boolean allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	 

	public List<ContentTypesBean> getContentTypeBeans() {
		return contentTypeBeans;
	}

	public void setContentTypeBeans(List<ContentTypesBean> contentTypeBeans) {
		this.contentTypeBeans = contentTypeBeans;
	}

	public ContentTypesService getContentTypeService() {
		return contentTypeService;
	}

	public void setContentTypeService(ContentTypesService contentTypeService) {
		this.contentTypeService = contentTypeService;
	}

	public ArrayList<SelectItem> getMenu_items() {
		return menu_items;
	}

	public void setMenu_items(ArrayList<SelectItem> menu_items) {
		this.menu_items = menu_items;
	}

	public ContentTypesBean getContentTypeBean() {
		return contentTypeBean;
	}

	public void setContentTypeBean(ContentTypesBean contentTypeBean) {
		this.contentTypeBean = contentTypeBean;
	}

	public String getSaveorUpdateContentCategory() {
		return saveorUpdateContentCategory;
	}

	public void setSaveorUpdateContentCategory(
			String saveorUpdateContentCategory) {
		this.saveorUpdateContentCategory = saveorUpdateContentCategory;
	}

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	
	
	
	


	// For Upload Image
    public void handelUpload(FileUploadEvent event){
        UploadedFile file = event.getFile();
        categoyLogoImage = new AppUtil().uploadImage(file,DBConstants.CONTENT_IMAGES_UPLOADS);
        System.out.println("categoyLogoImage : "+categoyLogoImage);
    }

	public String getCategoyLogoImage() {
		return categoyLogoImage;
	}

	public void setCategoyLogoImage(String categoyLogoImage) {
		this.categoyLogoImage = categoyLogoImage;
	}
	
	
	
}
