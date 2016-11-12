package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class RoleHasPermissionBean  implements java.io.Serializable {


     private int id;
     private RoleBean role;
     private PermisionBean permision;
     
     //String pageName ="";

    public RoleHasPermissionBean() {
    }

    public RoleHasPermissionBean(RoleBean role, PermisionBean permision) {
       this.role = role;
       this.permision = permision;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public RoleBean getRole() {
        return this.role;
    }
    
    public void setRole(RoleBean role) {
        this.role = role;
    }
    public PermisionBean getPermision() {
        return this.permision;
    }
    
    /*public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}*/

	public void setPermision(PermisionBean permision) {
        this.permision = permision;
    }




}


