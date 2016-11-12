package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class SubcategoryHasSectionsBean  implements java.io.Serializable {


     private int id;
     private SectionsBean sections;
     private SubcategoryBean subcategory;
     private Set subcategoryHasSectionsHasBranches = new HashSet(0);

    public SubcategoryHasSectionsBean() {
    }

	
    public SubcategoryHasSectionsBean(SectionsBean sections, SubcategoryBean subcategory) {
        this.sections = sections;
        this.subcategory = subcategory;
    }
    public SubcategoryHasSectionsBean(SectionsBean sections, SubcategoryBean subcategory, Set subcategoryHasSectionsHasBranches) {
       this.sections = sections;
       this.subcategory = subcategory;
       this.subcategoryHasSectionsHasBranches = subcategoryHasSectionsHasBranches;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public SectionsBean getSections() {
        return this.sections;
    }
    
    public void setSections(SectionsBean sections) {
        this.sections = sections;
    }
    public SubcategoryBean getSubcategory() {
        return this.subcategory;
    }
    
    public void setSubcategory(SubcategoryBean subcategory) {
        this.subcategory = subcategory;
    }
    public Set getSubcategoryHasSectionsHasBranches() {
        return this.subcategoryHasSectionsHasBranches;
    }
    
    public void setSubcategoryHasSectionsHasBranches(Set subcategoryHasSectionsHasBranches) {
        this.subcategoryHasSectionsHasBranches = subcategoryHasSectionsHasBranches;
    }




}


