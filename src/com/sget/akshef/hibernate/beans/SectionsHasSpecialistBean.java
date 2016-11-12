package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class SectionsHasSpecialistBean  implements java.io.Serializable {


     private int id;
     private SpecialistBean specialist;
     private SectionsBean sections;

    public SectionsHasSpecialistBean() {
    }

    public SectionsHasSpecialistBean(SpecialistBean specialist, SectionsBean sections) {
       this.specialist = specialist;
       this.sections = sections;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public SpecialistBean getSpecialist() {
        return this.specialist;
    }
    
    public void setSpecialist(SpecialistBean specialist) {
        this.specialist = specialist;
    }
    public SectionsBean getSections() {
        return this.sections;
    }
    
    public void setSections(SectionsBean sections) {
        this.sections = sections;
    }




}


