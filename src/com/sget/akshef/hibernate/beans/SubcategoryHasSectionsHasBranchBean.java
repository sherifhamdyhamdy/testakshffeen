package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class SubcategoryHasSectionsHasBranchBean  implements java.io.Serializable {


     private int id;
     private BranchBean branch;
     private SubcategoryHasSectionsBean subcategoryHasSections;
     private Double price;

    public SubcategoryHasSectionsHasBranchBean() {
    }

	
    public SubcategoryHasSectionsHasBranchBean(BranchBean branch, SubcategoryHasSectionsBean subcategoryHasSections) {
        this.branch = branch;
        this.subcategoryHasSections = subcategoryHasSections;
    }
    public SubcategoryHasSectionsHasBranchBean(BranchBean branch, SubcategoryHasSectionsBean subcategoryHasSections, Double price) {
       this.branch = branch;
       this.subcategoryHasSections = subcategoryHasSections;
       this.price = price;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public BranchBean getBranch() {
        return this.branch;
    }
    
    public void setBranch(BranchBean branch) {
        this.branch = branch;
    }
    public SubcategoryHasSectionsBean getSubcategoryHasSections() {
        return this.subcategoryHasSections;
    }
    
    public void setSubcategoryHasSections(SubcategoryHasSectionsBean subcategoryHasSections) {
        this.subcategoryHasSections = subcategoryHasSections;
    }
    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }




}


