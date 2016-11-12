package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class ClinicsBean  implements java.io.Serializable {


     private int id;
     private CategoryBean category;

    public ClinicsBean() {
    }

    public ClinicsBean(int id, CategoryBean category) {
       this.id = id;
       this.category = category;
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




}


