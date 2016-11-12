package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class BranchGroupsBean  implements java.io.Serializable {


     private int id;
     private UsersGroupsBean usersGroups;
     private BranchBean branch;

    public BranchGroupsBean() {
    }

    public BranchGroupsBean(UsersGroupsBean usersGroups, BranchBean branch) {
       this.usersGroups = usersGroups;
       this.branch = branch;
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
    public BranchBean getBranch() {
        return this.branch;
    }
    
    public void setBranch(BranchBean branch) {
        this.branch = branch;
    }




}


