package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class DrugsCompanyBean  implements java.io.Serializable {


     private int id;
     private String name;
     private Set drugses = new HashSet(0);

    public DrugsCompanyBean() {
    }

    public DrugsCompanyBean(String name, Set drugses) {
       this.name = name;
       this.drugses = drugses;
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
    public Set getDrugses() {
        return this.drugses;
    }
    
    public void setDrugses(Set drugses) {
        this.drugses = drugses;
    }




}


