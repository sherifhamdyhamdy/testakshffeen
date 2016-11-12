package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class GroupsHasRoleBean  implements java.io.Serializable {


     private int id;
     private RoleHasPermissionBean roleHasPermission;
     private GroupsBean groups;

    public GroupsHasRoleBean() {
    }

    public GroupsHasRoleBean(RoleHasPermissionBean roleHasPermission, GroupsBean groups) {
       this.setRoleHasPermission(roleHasPermission);
       this.groups = groups;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
     
    public GroupsBean getGroups() {
        return this.groups;
    }
    
    public void setGroups(GroupsBean groups) {
        this.groups = groups;
    }

	public RoleHasPermissionBean getRoleHasPermission() {
		return roleHasPermission;
	}

	public void setRoleHasPermission(RoleHasPermissionBean roleHasPermission) {
		this.roleHasPermission = roleHasPermission;
	}




}


