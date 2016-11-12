package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class UserRateSpecBean  implements java.io.Serializable {


     private int id;
     private SpecialistBean specialist;
     private UsersBean users;
     private int rate;

    public UserRateSpecBean() {
    }

	
    public UserRateSpecBean(SpecialistBean specialist, UsersBean users) {
        this.specialist = specialist;
        this.users = users;
    }
    public UserRateSpecBean(SpecialistBean specialist, UsersBean users, int rate) {
       this.specialist = specialist;
       this.users = users;
       this.rate = rate;
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
    public UsersBean getUsers() {
        return this.users;
    }
    
    public void setUsers(UsersBean users) {
        this.users = users;
    }
    public int getRate() {
        return this.rate;
    }
    
    public void setRate(int rate) {
        this.rate = rate;
    }




}


