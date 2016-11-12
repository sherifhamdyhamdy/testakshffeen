package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class RoleBean  implements java.io.Serializable {


     private int id;
     private String name;
     private int active;
     private Set groupsHasRoles = new HashSet(0);
     private Set<PermisionBean>  RolesHasPermissions = new HashSet(0);
 
    public RoleBean() {
    }

    public RoleBean(String name, int active, Set groupsHasRoles) {
       this.name = name;
       this.active = active;
       this.groupsHasRoles = groupsHasRoles;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public int getActive() {
        return this.active;
    }
    
    public void setActive(int active) {
        this.active = active;
    }
    public Set getGroupsHasRoles() {
        return this.groupsHasRoles;
    }
    
    public void setGroupsHasRoles(Set groupsHasRoles) {
        this.groupsHasRoles = groupsHasRoles;
    }

	public Set<PermisionBean> getRolesHasPermissions() {
		return RolesHasPermissions;
	}

	public void setRolesHasPermissions(Set<PermisionBean> rolesHasPermissions) {
		RolesHasPermissions = rolesHasPermissions;
	}




}


