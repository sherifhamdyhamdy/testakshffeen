package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class UserFavoritiesBean  implements java.io.Serializable {


     private int id;
     private SpecialistBean specialist;
     private BranchBean branch;
     private UsersBean users;
     private ContentDetailsBean contentDetails;
     private String notes;

    public UserFavoritiesBean() {
    }

	
    public UserFavoritiesBean(UsersBean users) {
        this.users = users;
    }
    public UserFavoritiesBean(SpecialistBean specialist, BranchBean branch, UsersBean users, ContentDetailsBean contentDetails, String notes) {
       this.specialist = specialist;
       this.branch = branch;
       this.users = users;
       this.contentDetails = contentDetails;
       this.notes = notes;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public SpecialistBean getSpecialist() {
        return this.specialist;
    }
    
    public void setSpecialist(SpecialistBean specialist) {
        this.specialist = specialist;
    }
    public BranchBean getBranch() {
        return this.branch;
    }
    
    public void setBranch(BranchBean branch) {
        this.branch = branch;
    }
    public UsersBean getUsers() {
        return this.users;
    }
    
    public void setUsers(UsersBean users) {
        this.users = users;
    }
    public ContentDetailsBean getContentDetails() {
        return this.contentDetails;
    }
    
    public void setContentDetails(ContentDetailsBean contentDetails) {
        this.contentDetails = contentDetails;
    }
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }




}


