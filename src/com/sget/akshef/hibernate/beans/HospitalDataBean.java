package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class HospitalDataBean  implements java.io.Serializable {


     private int id;
     private CategoryBean category;
     private String name;

    public HospitalDataBean() {
    }

	
    public HospitalDataBean(CategoryBean category) {
        this.category = category;
    }
    public HospitalDataBean(CategoryBean category, String name) {
       this.category = category;
       this.name = name;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public CategoryBean getCategory() {
        return this.category;
    }
    
    public void setCategory(CategoryBean category) {
        this.category = category;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }




}


