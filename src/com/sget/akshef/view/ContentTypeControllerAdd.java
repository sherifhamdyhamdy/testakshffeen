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

import com.sget.akshef.hibernate.beans.ContentTypesBean;
import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.ContentTypesService;
import com.sget.akshef.hibernate.service.GroupsService;

public class ContentTypeControllerAdd {
	private ContentTypesBean contentTypesBean = null;
	private ContentTypesService contentTypesService = null;
	private boolean allowUpdate = false;
	private String SelectedId = null;

	private GroupsBean  groupsBean = null;

	private List<GroupsBean> groupsBeans = null;
	private GroupsService groupsService = null;
	private ArrayList<SelectItem> menu_items;
	
	private boolean saverender  = false;
	
	
private String  saveContentType="saveContentType";

	public ContentTypeControllerAdd() {
		contentTypesService = new ContentTypesService();
		contentTypesBean = new ContentTypesBean();

		groupsBean = new GroupsBean();
		groupsBeans = new ArrayList<GroupsBean>();
		groupsService = new GroupsService();
		menu_items = new ArrayList<SelectItem>();
		// /addded to get categories
		menu_items.add(new SelectItem("","select"));
		groupsBeans = groupsService.getAll();
		for (GroupsBean bean : groupsBeans) {
			menu_items.add(new SelectItem(bean.getId(), bean.getName()));

		}

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
		if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
		// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
			for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){
	
			// System.out.println("ContentTypeControllerAdd "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
				if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("ContentType")){
		
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

				contentTypesBean = contentTypesService.getById(Integer.parseInt(SelectedId));
				groupsBean =contentTypesBean.getGroups();
				saveContentType  ="updateContentType";

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		
	}

	// saveDistric
	public void saveContentTypes(ActionEvent action) {

		// System.out.println("contentTypesBean11    ===   ::save2");
		// System.out.println("contentTypesBean    ===   " + contentTypesBean.getId());

		// System.out.println("contentTypesBean    ===   " + contentTypesBean.getNameAr());
		// System.out.println("contentTypesBean    ===   " + contentTypesBean.getNameEn());


		// // System.out.println("contentTypesBean contentTypesBean.getCity().getId()   ===   "
		// + contentTypesBean.getCity().getId());

		if (contentTypesBean.getId() != 0) {
			contentTypesBean.setGroups(groupsBean);
			boolean up = contentTypesService.update(contentTypesBean);
			if(up)
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
 					"Updated", "Updated Successfully"));
 			else
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Error in Update"));
		} else {

			if (groupsBean == null)
				return;
			contentTypesBean.setGroups(groupsBean);
			contentTypesService.insert(contentTypesBean);
			if(contentTypesBean.getId() > 0)
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
 					"Insert", "Inserted Successfully"));
 			else
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Error in Insert"));
		}
		contentTypesBean = new ContentTypesBean();
		groupsBean  = new GroupsBean();

	}

	public ContentTypesBean getContentTypesBean() {
		return contentTypesBean;
	}

	public void setContentTypesBean(ContentTypesBean contentTypesBean) {
		this.contentTypesBean = contentTypesBean;
	}

	public ContentTypesService getContentTypesService() {
		return contentTypesService;
	}

	public void setContentTypesService(ContentTypesService contentTypesService) {
		this.contentTypesService = contentTypesService;
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

	public GroupsBean getGroupsBean() {
		return groupsBean;
	}

	public void setGroupsBean(GroupsBean groupsBean) {
		this.groupsBean = groupsBean;
	}

	public List<GroupsBean> getGroupsBeans() {
		return groupsBeans;
	}

	public void setGroupsBeans(List<GroupsBean> groupsBeans) {
		this.groupsBeans = groupsBeans;
	}

	 

	public ArrayList<SelectItem> getMenu_items() {
		return menu_items;
	}

	public void setMenu_items(ArrayList<SelectItem> menu_items) {
		this.menu_items = menu_items;
	}

	public String getSaveContentType() {
		return saveContentType;
	}

	public void setSaveContentType(String saveContentType) {
		this.saveContentType = saveContentType;
	}

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

}
