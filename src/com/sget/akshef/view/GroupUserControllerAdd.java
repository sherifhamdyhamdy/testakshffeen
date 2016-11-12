package com.sget.akshef.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.GroupsService;
import com.sget.akshef.hibernate.service.UsersGroupsService;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshef.view.admin.AdminControl;

@ManagedBean(name="groupUserControllerAdd")
@ViewScoped
public class GroupUserControllerAdd extends AdminControl implements Serializable{

	private static final long serialVersionUID = 1192053216054301345L;
	
	private UsersService userService ;
	private UsersGroupsService userGroupService ;
	private List<SelectItem> groupItems;
	private List<UsersBean> groupUsers ;
	
	private int selectedGroup ;
	private UsersBean[] selectedUsers ;
	private UsersBean[] usersToAdd ;
	private List<UsersBean> allUsers ; 
	private List<UsersBean> usersItems ;
	
	// Functions
	public GroupUserControllerAdd(){
		userService = new UsersService();
		userGroupService = new UsersGroupsService();
		
		// get all groups
		fillGroups();
		// get All users in group
		reGetUsersByGroup();
		// get All Users
		getAllUsers();
	}
	/**
	 * Fill All Groups
	 */
	private void fillGroups(){
		try{
			GroupsService groServ = new GroupsService();
			List<GroupsBean> groups = groServ.getAll();
			groupItems = new ArrayList<SelectItem>();
			if(groups != null && groups.size() > 0){
				for(GroupsBean bean : groups){
					if(bean != null && bean.getId() > 0 && bean.getName() != null 
							&& !bean.getName().trim().equalsIgnoreCase(""))
						groupItems.add(new SelectItem(bean.getId()+"", bean.getName()));
				}
				selectedGroup = groups.get(0).getId();
			}else{
				addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", "Error in System !!!!");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", "Error in System !!!!");
		}
	}
	
	/**
	 * ReGenerate Table of users by Group Id 
	 */
	public void reGetUsersByGroup(){
		if(selectedGroup > 0){
			groupUsers = userService.getAllByGroupId(selectedGroup);
			if(groupUsers == null || groupUsers.size() == 0){
				if(groupUsers == null)
					groupUsers = new ArrayList<UsersBean>();
				addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", "No Data !!!!");
			}
			// get All Users
			getAllUsers();
		}else{
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", " Choose Group First  !!!!");
		}
		
	}
	public void getAllUsers(){
		allUsers = userService.getAll();
		if(allUsers != null && allUsers.size() > 0){
			if(usersItems == null)
				usersItems = new ArrayList<UsersBean>();
			else
				usersItems.clear();
			
			for(UsersBean bean : allUsers){
				if(!groupUsers.contains(bean))
					usersItems.add(bean);
			}
		}
	}
	/**
	 * Remove Users from Groups
	 */
	public void removeUsersFromGroups(){
		if(selectedUsers.length > 0){
			int count = 0 ;
			for(UsersBean bean : selectedUsers){
				if(bean.getId() > 0){
					boolean status = userGroupService.deleteUsersFromGroup(bean.getId(), selectedGroup);
					if(status)
						count++;
				}
			}
			if(count > 0)
				addFacesMessage(FacesMessage.SEVERITY_INFO, "Sucess ", count + " Users Removed from Group Succesfully  !!!!");
			else
				addFacesMessage(FacesMessage.SEVERITY_ERROR, " Error ", " cann't delete Users");
			getAllUsers();
			// for refresh only
			selectedGroup= groupUsers.get(0).getId();
			
		}else{
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", " Choose at least one user  !!!!");
		}
	}
	/**
	 * 
	 */
	public void addUsersToGroup(){
		if(usersToAdd.length > 0){
			int count = 0 ;
			for(UsersBean bean : usersToAdd){
				if(bean.getId() > 0){
					boolean status = userGroupService.insertUserToGroup(bean.getId(), selectedGroup);
					if(status)
						count++;
				}
			}
			if(count > 0)
				addFacesMessage(FacesMessage.SEVERITY_INFO, " Success ", count + " Users added to Group Succesfully  !!!!");
			else
				addFacesMessage(FacesMessage.SEVERITY_ERROR, " Error ", " cann't insert Users");
			
			reGetUsersByGroup();
			getAllUsers();
		}else{
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error ", " Choose at least one user  !!!!");
		}
	}
	
	//Setters & Getters
	public List<UsersBean> getGroupUsers() {
		return groupUsers;
	}
	public void setGroupUsers(List<UsersBean> groupUsers) {
		this.groupUsers = groupUsers;
	}
	public List<SelectItem> getGroupItems() {
		return groupItems;
	}
	public void setGroupItems(List<SelectItem> groupItems) {
		this.groupItems = groupItems;
	}
	public int getSelectedGroup() {
		return selectedGroup;
	}
	public void setSelectedGroup(int selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	public UsersBean[] getSelectedUsers() {
		return selectedUsers;
	}
	public void setSelectedUsers(UsersBean[] selectedUsers) {
		this.selectedUsers = selectedUsers;
	}
	public List<UsersBean> getUsersItems() {
		return usersItems;
	}
	public void setUsersItems(List<UsersBean> usersItems) {
		this.usersItems = usersItems;
	}
	public UsersBean[] getUsersToAdd() {
		return usersToAdd;
	}
	public void setUsersToAdd(UsersBean[] usersToAdd) {
		this.usersToAdd = usersToAdd;
	}
}