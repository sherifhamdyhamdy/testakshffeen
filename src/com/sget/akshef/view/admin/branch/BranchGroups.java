package com.sget.akshef.view.admin.branch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.BranchGroupsBean;
import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.GroupsHasRoleBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.beans.UsersGroupsBean;
import com.sget.akshef.hibernate.service.BranchGroupsService;
import com.sget.akshef.hibernate.service.GroupsHasRoleService;
import com.sget.akshef.hibernate.service.GroupsService;
import com.sget.akshef.hibernate.service.UsersGroupsService;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshef.view.admin.AdminControl;


/**
 * 
 * @author JDeeb Name = contGroup
 */

public class BranchGroups extends AdminControl implements Serializable
{
	
	private static final long serialVersionUID = -3677155311859440932L;
	
	private GroupsService groupService;
	
	private GroupsHasRoleService groRoleService;
	
	private List<GroupsBean> groups;
	
	private GroupsBean selectedGroup;
	
	private List<RoleHasPermissionBean> groupList;
	
	private List<SelectItem> selectOneRoleMenu;
	
	private List<SelectItem> selectMultiPermissions;
	
	private String newGroup;
	
	private int branchId = 0;
	
	private String selectedRole;
	
	private List<String> selectedPermissions;
	
	private List<String> oldSelectedPermission;
	
	private boolean disableSave = false;
	
	private List<GroupsHasRoleBean> groupHasRoles;
	
	private List<UsersBean> groupUsers;
	
	private UsersBean addNewUser;
	
	
	
	// Functions
	public BranchGroups()
	{
		groupService = new GroupsService();
		groRoleService = new GroupsHasRoleService();
		groupList = groRoleService.getBranchGroupPermission();
		
		// For Test
		branchId = 72;
		populateBranchGroups();
		populateRoles();
	}
	
	
	
	public void populateBranchGroups()
	{
		groups = groupService.getBranchGroups(branchId);
	}
	
	
	
	// Prepare Group Permission when choose group from table and before show
	// details Dialog
	public void prepareGroupPermission()
	{
		if (selectedGroup != null && selectedGroup.getId() != 0)
		{
			GroupsHasRoleBean groupHasRole = new GroupsHasRoleBean();
			groupHasRole.setId(selectedGroup.getId());
			groupHasRoles = groRoleService.getByGroup(groupHasRole);
			
			if (oldSelectedPermission == null)
				oldSelectedPermission = new ArrayList<String>();
			else
				oldSelectedPermission.clear();
			
			for (GroupsHasRoleBean bean : groupHasRoles)
			{
				oldSelectedPermission.add(bean.getRoleHasPermission().getId() + "");
			}
			disableSave = false;
		}
		else
		{
			disableSave = true;
			// addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
			// "Choose group First");
		}
		
	}
	
	
	
	// Prepare Group Users
	public void prepareGroupsUsers()
	{
		if (selectedGroup != null && selectedGroup.getId() != 0)
		{
			groupUsers = new ArrayList<UsersBean>();
			
			selectedGroup = groupService.getById(selectedGroup.getId());
			Iterator<UsersGroupsBean> iterator = selectedGroup.getUsersGroupses().iterator();
			while (iterator.hasNext())
			{
				UsersGroupsBean tempBean = iterator.next();
				groupUsers.add(tempBean.getUsers());
			}
			disableSave = false;
		}
		else
		{
			disableSave = true;
			// addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
			// "Choose group First");
		}
		
	}
	
	
	
	public void addNewGroup()
	{
		if (newGroup != null && !newGroup.trim().equals(""))
		{
			GroupsBean gro = new GroupsBean();
			gro.setName(newGroup);
			gro.setActive(1);
			gro.setBranchId(branchId);
			gro.setSystemFlag(0);
			
			groupService.insert(gro);
			if (gro.getId() > 0)
			{
				populateBranchGroups();
				addFacesMessage(FacesMessage.SEVERITY_INFO, "Inserted Successfully", "Inserted Successfully");
			}
			else
			{
				addFacesMessage(FacesMessage.SEVERITY_ERROR, "Fail to Insert", "Fail to Insert");
			}
		}
		else
		{
			addFacesMessage(FacesMessage.SEVERITY_WARN, "You have to insert Group Name", "You have to insert Group Name");
		}
	}
	
	
	
	private void populateRoles()
	{
		selectOneRoleMenu = new ArrayList<SelectItem>();
		if (groupList != null)
		{
			for (RoleHasPermissionBean bean : groupList)
			{
				boolean exist = false;
				for (SelectItem item : selectOneRoleMenu)
				{
					if (item.getValue().equals(bean.getRole().getId()))
						exist = true;
				}
				if (!exist)
				{
					selectOneRoleMenu.add(new SelectItem(bean.getRole().getId(), bean.getRole().getName()));
					exist = false;
				}
			}
		}
	}
	
	
	
	// On change Role Populate Permissions
	public void populateRolesPermissions()
	{
		selectMultiPermissions = new ArrayList<SelectItem>();
		for (RoleHasPermissionBean bean : groupList)
		{
			if (bean.getRole().getId() == Integer.parseInt(selectedRole))
			{
				selectMultiPermissions.add(new SelectItem(bean.getId(), bean.getPermision().getPermission_name()));
			}
		}
		if (selectedPermissions == null)
			selectedPermissions = new ArrayList<String>();
		if (oldSelectedPermission != null && oldSelectedPermission.size() > 0)
			selectedPermissions = oldSelectedPermission;
		
	}
	
	
	
	public void savePermissions()
	{
		if (selectedPermissions != null && selectedPermissions.size() > 0)
		{
			GroupsHasRoleBean bean = null;
			int count = 0;
			try
			{
				List<String> willSaved = getDifferentList(oldSelectedPermission, selectedPermissions);
				for (String sel : willSaved)
				{
					bean = new GroupsHasRoleBean();
					RoleHasPermissionBean tempRolePerm = getRolePermissionById(sel);
					bean.setRoleHasPermission(tempRolePerm);
					bean.setGroups(selectedGroup);
					groRoleService.insert(bean);
					if (bean.getId() > 0)
						count++;
				}
				addFacesMessage(FacesMessage.SEVERITY_INFO, " Success ", count + " added successfully");
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			addFacesMessage(FacesMessage.SEVERITY_WARN, " Choose Permissions", " You haven't Select any Permission !!");
		}
		oldSelectedPermission.clear();
		selectedPermissions.clear();
	}
	
	
	
	private List<String> getDifferentList(List<String> oldList, List<String> newList)
	{
		if (oldList != null && oldList.size() > 0)
		{
			List<String> diff = new ArrayList<String>();
			for (String temp : newList)
			{
				boolean exist = false;
				for (String temp2 : oldList)
				{
					if (temp.equalsIgnoreCase(temp2))
						exist = true;
				}
				if (!exist)
					diff.add(temp);
			}
			return diff;
		}
		else
		{
			return newList;
		}
	}
	
	
	
	// Get Permission
	private RoleHasPermissionBean getRolePermissionById(String id)
	{
		if (id == null || id.equalsIgnoreCase(""))
			return null;
		for (RoleHasPermissionBean bean : groupList)
		{
			if (bean.getId() == Integer.parseInt(id))
				return bean;
		}
		return null;
	}
	
	
	
	// Add New User to Group
	public void addNewUserToGroup()
	{
		if (selectedGroup != null && selectedGroup.getId() > 0)
		{
			if (addNewUser != null)
			{
				if (addNewUser.getUsername() != null && !addNewUser.getUsername().trim().equalsIgnoreCase(""))
				{
					
					// First save As User
					UsersService service = new UsersService();
					boolean x = service.checkUserInRegisterByUsername(addNewUser.getUsername());
					if (x)
					{
						addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error In Add User", "this UserName already exist!!!");
					}
					else
					{
						service.insert(addNewUser);
						if (addNewUser.getId() > 0)
						{
							UsersGroupsBean bean = new UsersGroupsBean();
							
							bean.setGroups(selectedGroup);
							bean.setUsers(addNewUser);
							
							UsersGroupsService serv = new UsersGroupsService();
							serv.insert(bean);
							
							
							if (bean.getId() > 0)
							{
								BranchGroupsBean bea = new BranchGroupsBean();
								BranchBean bb = new BranchBean();
								bb.setId(branchId);
								
								bea.setBranch(bb);
								bea.setUsersGroups(bean);
								
								BranchGroupsService groSer = new BranchGroupsService();
								groSer.insert(bea);
								if (bea.getId() > 0)
								{
									addFacesMessage(FacesMessage.SEVERITY_INFO, "Sucess", "Insert Successfully!!");
								}
								else
								{
									serv.delete(bean);
									service.delete(addNewUser);
									addFacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", "Fail To Insert New User!!");
								}
							}
							else
							{
								service.delete(addNewUser);
								addFacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", "Fail To Insert New User!!");
							}
						}
						else
						{
							addFacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", "Fail To Insert New User!!");
						}
					}
					
				}
				else
				{
					addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error Data", "You have to fill all Data!!");
				}
			}
		}
		else
		{
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Error Criteria", "You have to choose group first!!");
		}
	}
	
	
	
	// Setters & Getters
	public List<GroupsBean> getGroups()
	{
		return groups;
	}
	
	
	
	public void setGroups(List<GroupsBean> groups)
	{
		this.groups = groups;
	}
	
	
	
	public GroupsBean getSelectedGroup()
	{
		return selectedGroup;
	}
	
	
	
	public void setSelectedGroup(GroupsBean selectedGroup)
	{
		this.selectedGroup = selectedGroup;
	}
	
	
	
	public String getNewGroup()
	{
		return newGroup;
	}
	
	
	
	public void setNewGroup(String newGroup)
	{
		this.newGroup = newGroup;
	}
	
	
	
	public List<SelectItem> getSelectOneRoleMenu()
	{
		return selectOneRoleMenu;
	}
	
	
	
	public void setSelectOneRoleMenu(List<SelectItem> selectOneRoleMenu)
	{
		this.selectOneRoleMenu = selectOneRoleMenu;
	}
	
	
	
	public String getSelectedRole()
	{
		return selectedRole;
	}
	
	
	
	public void setSelectedRole(String selectedRole)
	{
		this.selectedRole = selectedRole;
	}
	
	
	
	public List<String> getSelectedPermissions()
	{
		return selectedPermissions;
	}
	
	
	
	public void setSelectedPermissions(List<String> selectedPermissions)
	{
		this.selectedPermissions = selectedPermissions;
	}
	
	
	
	public List<SelectItem> getSelectMultiPermissions()
	{
		return selectMultiPermissions;
	}
	
	
	
	public void setSelectMultiPermissions(List<SelectItem> selectMultiPermissions)
	{
		this.selectMultiPermissions = selectMultiPermissions;
	}
	
	
	
	public boolean isDisableSave()
	{
		return disableSave;
	}
	
	
	
	public void setDisableSave(boolean disableSave)
	{
		this.disableSave = disableSave;
	}
	
	
	
	public List<UsersBean> getGroupUsers()
	{
		return groupUsers;
	}
	
	
	
	public void setGroupUsers(List<UsersBean> groupUsers)
	{
		this.groupUsers = groupUsers;
	}
	
	
	
	public UsersBean getAddNewUser()
	{
		if (addNewUser == null)
			addNewUser = new UsersBean();
		return addNewUser;
	}
	
	
	
	public void setAddNewUser(UsersBean addNewUser)
	{
		this.addNewUser = addNewUser;
	}
}
