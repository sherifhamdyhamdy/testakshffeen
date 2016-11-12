package com.sget.akshef.hibernate.beans;


/**
 * Author JDeeb
 */
public class PermisionBean  implements java.io.Serializable {


     private int id;
     private String Permission_name;
 
    public PermisionBean() {
    }

     
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }



	public String getPermission_name() {
		return Permission_name;
	}



	public void setPermission_name(String permission_name) {
		Permission_name = permission_name;
	}
   
    




}


