package com.sget.akshef.hibernate.beans;
/**
 * Author JDeeb
 */
public class ApprovalMsgBean  implements java.io.Serializable {


     private int id;
     private UsersBean users;
     private ActionsBean actions;
     private GroupsBean groups;
     private String title;
     private String messgae;
     private String notes;

    public ApprovalMsgBean() {
    }

	
    public ApprovalMsgBean(UsersBean users, ActionsBean actions, GroupsBean groups) {
        this.users = users;
        this.actions = actions;
        this.groups = groups;
    }
    public ApprovalMsgBean(UsersBean users, ActionsBean actions, GroupsBean groups, String title, String messgae, String notes) {
       this.users = users;
       this.actions = actions;
       this.groups = groups;
       this.title = title;
       this.messgae = messgae;
       this.notes = notes;
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
    public ActionsBean getActions() {
        return this.actions;
    }
    
    public void setActions(ActionsBean actions) {
        this.actions = actions;
    }
    public GroupsBean getGroups() {
        return this.groups;
    }
    
    public void setGroups(GroupsBean groups) {
        this.groups = groups;
    }
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessgae() {
        return this.messgae;
    }
    
    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }




}


