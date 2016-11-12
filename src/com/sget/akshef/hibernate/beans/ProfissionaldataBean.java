package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class ProfissionaldataBean  implements java.io.Serializable {


     private int id;
     private UsersBean users;
     private String degree;

    public ProfissionaldataBean() {
    }

	
    public ProfissionaldataBean(UsersBean users) {
        this.users = users;
    }
    public ProfissionaldataBean(UsersBean users, String degree) {
       this.users = users;
       this.degree = degree;
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
    public String getDegree() {
        return this.degree;
    }
    
    public void setDegree(String degree) {
        this.degree = degree;
    }




}


