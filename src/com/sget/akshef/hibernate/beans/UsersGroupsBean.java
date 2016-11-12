package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class UsersGroupsBean  implements java.io.Serializable {


     private int id;
     private UsersBean users;
     private GroupsBean groups;
     private Set unitGroupses = new HashSet(0);
     private Set branchGroupses = new HashSet(0);

    public UsersGroupsBean() {
    }

	
    public UsersGroupsBean(UsersBean users, GroupsBean groups) {
        this.users = users;
        this.groups = groups;
    }
    public UsersGroupsBean(UsersBean users, GroupsBean groups, Set unitGroupses, Set branchGroupses) {
       this.users = users;
       this.groups = groups;
       this.unitGroupses = unitGroupses;
       this.branchGroupses = branchGroupses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public UsersBean getUsers() {
        return this.users;
    }
    
    public void setUsers(UsersBean users) {
        this.users = users;
    }
    public GroupsBean getGroups() {
        return this.groups;
    }
    
    public void setGroups(GroupsBean groups) {
        this.groups = groups;
    }
    public Set getUnitGroupses() {
        return this.unitGroupses;
    }
    
    public void setUnitGroupses(Set unitGroupses) {
        this.unitGroupses = unitGroupses;
    }
    public Set getBranchGroupses() {
        return this.branchGroupses;
    }
    
    public void setBranchGroupses(Set branchGroupses) {
        this.branchGroupses = branchGroupses;
    }




}


