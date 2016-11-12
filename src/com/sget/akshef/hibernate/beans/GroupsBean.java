package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class GroupsBean  implements java.io.Serializable {


     private int id;
     private String name;
     private int unitId;
     private int branchId;
     private int systemFlag;
     private int active;
     private Set groupsHasRoles = new HashSet(0);
     private Set contentTypeses = new HashSet(0);
     private Set usersGroupses = new HashSet(0);
     private Set approvalMsgs = new HashSet(0);

    public GroupsBean() {
    }

    public GroupsBean(String name, int unitId, int branchId, int systemFlag, int active, Set groupsHasRoles, Set contentTypeses, Set usersGroupses, Set approvalMsgs) {
       this.name = name;
       this.unitId = unitId;
       this.branchId = branchId;
       this.systemFlag = systemFlag;
       this.active = active;
       this.groupsHasRoles = groupsHasRoles;
       this.contentTypeses = contentTypeses;
       this.usersGroupses = usersGroupses;
       this.approvalMsgs = approvalMsgs;
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
    public int getUnitId() {
        return this.unitId;
    }
    
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }
    public int getBranchId() {
        return this.branchId;
    }
    
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    public int getSystemFlag() {
        return this.systemFlag;
    }
    
    public void setSystemFlag(int systemFlag) {
        this.systemFlag = systemFlag;
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
    public Set getContentTypeses() {
        return this.contentTypeses;
    }
    
    public void setContentTypeses(Set contentTypeses) {
        this.contentTypeses = contentTypeses;
    }
    public Set getUsersGroupses() {
        return this.usersGroupses;
    }
    
    public void setUsersGroupses(Set usersGroupses) {
        this.usersGroupses = usersGroupses;
    }
    public Set getApprovalMsgs() {
        return this.approvalMsgs;
    }
    
    public void setApprovalMsgs(Set approvalMsgs) {
        this.approvalMsgs = approvalMsgs;
    }

}
