package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class CategoryHasSubcategoryBean  implements java.io.Serializable {


     private int id;
     private CategoryBean category;
     private SubcategoryBean subcategory;

    public CategoryHasSubcategoryBean() {
    }

    public CategoryHasSubcategoryBean(CategoryBean category, SubcategoryBean subcategory) {
       this.category = category;
       this.subcategory = subcategory;
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
    public SubcategoryBean getSubcategory() {
        return this.subcategory;
    }
    
    public void setSubcategory(SubcategoryBean subcategory) {
        this.subcategory = subcategory;
    }




}


