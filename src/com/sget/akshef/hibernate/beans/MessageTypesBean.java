package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class MessageTypesBean  implements java.io.Serializable {


     private int id;
     private String name;
     private Set messageses = new HashSet(0);

    public MessageTypesBean() {
    }

    public MessageTypesBean(String name, Set messageses) {
       this.name = name;
       this.messageses = messageses;
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
    public Set getMessageses() {
        return this.messageses;
    }
    
    public void setMessageses(Set messageses) {
        this.messageses = messageses;
    }




}


