package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class PriorityBean  implements java.io.Serializable {


     private int id;
     private String nameAr;
     private String nameEn;
     private int active;
     private String code;
     private Set contentDetailses = new HashSet(0);

    public PriorityBean() {
    }

    public PriorityBean(String nameAr, String nameEn, int active, String code, Set contentDetailses) {
       this.nameAr = nameAr;
       this.nameEn = nameEn;
       this.active = active;
       this.code = code;
       this.contentDetailses = contentDetailses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNameAr() {
        return this.nameAr;
    }
    
    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
    public String getNameEn() {
        return this.nameEn;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public int getActive() {
        return this.active;
    }
    
    public void setActive(int active) {
        this.active = active;
    }
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public Set getContentDetailses() {
        return this.contentDetailses;
    }
    
    public void setContentDetailses(Set contentDetailses) {
        this.contentDetailses = contentDetailses;
    }




}


