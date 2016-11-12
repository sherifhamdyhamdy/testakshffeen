package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class UnitGroupsBean  implements java.io.Serializable {


     private int id;
     private UsersGroupsBean usersGroups;
     private UnitBean unit;

    public UnitGroupsBean() {
    }

    public UnitGroupsBean(UsersGroupsBean usersGroups, UnitBean unit) {
       this.usersGroups = usersGroups;
       this.unit = unit;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public UsersGroupsBean getUsersGroups() {
        return this.usersGroups;
    }
    
    public void setUsersGroups(UsersGroupsBean usersGroups) {
        this.usersGroups = usersGroups;
    }
    public UnitBean getUnit() {
        return this.unit;
    }
    
    public void setUnit(UnitBean unit) {
        this.unit = unit;
    }




}


