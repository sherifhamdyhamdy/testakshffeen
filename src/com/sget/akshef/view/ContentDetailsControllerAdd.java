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

import com.sget.akshef.hibernate.beans.ContentCategoryBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.PriorityBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.ContentCategoryService;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.PriorityService;
import com.sget.akshef.hibernate.service.UsersService;


//@javax.faces.bean.ManagedBean(name="branchControllerAdd")
//@ViewScoped
public class ContentDetailsControllerAdd {
	private ContentDetailsBean  contentDetailsBean = null;
 	private int selectedPriority;
	private ArrayList<SelectItem> menu_itemsPriorities;
	
	private int selectedUsers;
	private ArrayList<SelectItem> menu_itemsUsers;
	
	private int selectedContentCategory;
	private ArrayList<SelectItem> menu_itemsContentCategory;
	 

	private ContentDetailsService contentDetailsService = null;
	private PriorityService priorityService = null;
	private UsersService usersService = null;
	private ContentCategoryService contentCategoryService = null;
	

 	private String SelectedId = null;
	private String saveOrUpdateContentDetails= "saveContentDetails";
	
	
	private boolean saverender  = false;

	public ContentDetailsControllerAdd() {
		contentDetailsService = new ContentDetailsService();
		priorityService  = new  PriorityService();
		usersService  = new  UsersService();
		contentCategoryService  = new  ContentCategoryService();
		contentDetailsBean = new ContentDetailsBean();
		
		menu_itemsContentCategory = new ArrayList<SelectItem>();		 
		menu_itemsContentCategory.add(new SelectItem(String.valueOf("" ),
				""));		
		for (ContentCategoryBean   contentCategoryBean : contentCategoryService.getAll()) {			 
			menu_itemsContentCategory.add(new SelectItem(String.valueOf(contentCategoryBean.getId()),
					contentCategoryBean.getNameAr()));
		}
		
		menu_itemsPriorities = new ArrayList<SelectItem>();		 
		menu_itemsPriorities.add(new SelectItem(String.valueOf("" ),
				""));		
		for (PriorityBean  priorityBean : priorityService.getAll()) {			 
			menu_itemsPriorities.add(new SelectItem(String.valueOf(priorityBean.getId()),
					priorityBean.getNameAr()));
		}
		
		menu_itemsUsers = new ArrayList<SelectItem>();		 
		menu_itemsUsers.add(new SelectItem(String.valueOf("" ),
				""));		
		for (UsersBean usersBean : usersService.getAll()) {			 
			menu_itemsUsers.add(new SelectItem(String.valueOf(usersBean.getId()),
					usersBean.getNameAr()));
		}
		
		
		
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
		if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
		// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
		for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

		// System.out.println("ContentDetailsControllerAdd "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
		if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("ContentDetails")){

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
			}
 			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {
				contentDetailsBean = contentDetailsService.getById(Integer
						.parseInt(SelectedId));
				selectedPriority =  contentDetailsBean.getPriority().getId();
				selectedUsers = contentDetailsBean.getUsers().getId();
				selectedContentCategory = contentDetailsBean.getContentCategory().getId();
 			}
			if (contentDetailsBean.getId() != 0) {
				saveOrUpdateContentDetails="updateContentDetails";
			} else {
				saveOrUpdateContentDetails="saveContentDetails";
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// saveContentDetails
	public void saveContentDetails(ActionEvent action) {

		// System.out.println("contentDetailsBean11    ===   ::save2");
		// System.out.println("contentDetailsBean    ===   " + contentDetailsBean.getId());

		PriorityBean  priorityBean  =  new  PriorityBean();
		priorityBean.setId(selectedPriority);
		
		UsersBean usersBean=new  UsersBean();
		usersBean.setId(selectedUsers);
		
		ContentCategoryBean contentCategoryBean=new  ContentCategoryBean();
		contentCategoryBean.setId(selectedContentCategory);
		
		contentDetailsBean.setPriority(priorityBean);
		contentDetailsBean.setUsers(usersBean);
		contentDetailsBean.setContentCategory(contentCategoryBean);

		if (contentDetailsBean.getId() != 0) {
			boolean up = contentDetailsService.update(contentDetailsBean);
			if(up)
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
 					"Updated", "Updated Successfully"));
 			else
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Error in Update"));
		} else {
			contentDetailsService.insert(contentDetailsBean);
			if(contentDetailsBean.getId() > 0)
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
 					"Inserted", "Inserted Successfully"));
 			else
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Error in Insert"));
		}
		contentDetailsBean = new  ContentDetailsBean();
		if (contentDetailsBean.getId() != 0) {
			saveOrUpdateContentDetails="updateContentDetails";
		} else {
			saveOrUpdateContentDetails="saveContentDetails";
		}
		selectedPriority =0;
		selectedUsers=0;
		selectedContentCategory =0;
		

	}
	
	 
	public ContentDetailsBean getContentDetailsBean() {
		return contentDetailsBean;
	}

	public void setContentDetailsBean(ContentDetailsBean contentDetailsBean) {
		this.contentDetailsBean = contentDetailsBean;
	}

	 
	 

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	public String getSaveOrUpdateContentDetails() {
		return saveOrUpdateContentDetails;
	}

	public void setSaveOrUpdateContentDetails(String saveOrUpdateContentDetails) {
		this.saveOrUpdateContentDetails = saveOrUpdateContentDetails;
	}

	public int getSelectedPriority() {
		return selectedPriority;
	}

	public void setSelectedPriority(int selectedPriority) {
		this.selectedPriority = selectedPriority;
	}

	public ArrayList<SelectItem> getMenu_itemsPriorities() {
		return menu_itemsPriorities;
	}

	public void setMenu_itemsPriorities(ArrayList<SelectItem> menu_itemsPriorities) {
		this.menu_itemsPriorities = menu_itemsPriorities;
	}

	public int getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(int selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	public ArrayList<SelectItem> getMenu_itemsUsers() {
		return menu_itemsUsers;
	}

	public void setMenu_itemsUsers(ArrayList<SelectItem> menu_itemsUsers) {
		this.menu_itemsUsers = menu_itemsUsers;
	}

	public int getSelectedContentCategory() {
		return selectedContentCategory;
	}

	public void setSelectedContentCategory(int selectedContentCategory) {
		this.selectedContentCategory = selectedContentCategory;
	}

	public ArrayList<SelectItem> getMenu_itemsContentCategory() {
		return menu_itemsContentCategory;
	}

	public void setMenu_itemsContentCategory(
			ArrayList<SelectItem> menu_itemsContentCategory) {
		this.menu_itemsContentCategory = menu_itemsContentCategory;
	}

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	 

	 
}
