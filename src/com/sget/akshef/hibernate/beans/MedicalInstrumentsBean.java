package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class MedicalInstrumentsBean  implements java.io.Serializable {


     private int id;
     private CategoryBean category;

    public MedicalInstrumentsBean() {
    }

    public MedicalInstrumentsBean(CategoryBean category) {
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


