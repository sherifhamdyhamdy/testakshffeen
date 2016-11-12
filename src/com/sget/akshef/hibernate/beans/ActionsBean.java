package com.sget.akshef.hibernate.beans;


import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class ActionsBean  implements java.io.Serializable {


     private int id;
     private String name;
     private String description;
     private Set approvalMsgs = new HashSet(0);

    public ActionsBean() {
    }

    public ActionsBean(String name, String description, Set approvalMsgs) {
       this.name = name;
       this.description = description;
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
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public Set getApprovalMsgs() {
        return this.approvalMsgs;
    }
    
    public void setApprovalMsgs(Set approvalMsgs) {
        this.approvalMsgs = approvalMsgs;
    }




}


