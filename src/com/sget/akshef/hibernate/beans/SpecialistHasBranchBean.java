package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class SpecialistHasBranchBean  implements java.io.Serializable {


     private int id;
     private SpecialistBean specialist;
     private BranchBean branch;
     private Set scheduleHasDayses = new HashSet(0);

    public SpecialistHasBranchBean() {
    }

	
    public SpecialistHasBranchBean(SpecialistBean specialist, BranchBean branch) {
        this.specialist = specialist;
        this.branch = branch;
    }
    public SpecialistHasBranchBean(SpecialistBean specialist, BranchBean branch, Set scheduleHasDayses) {
       this.specialist = specialist;
       this.branch = branch;
       this.scheduleHasDayses = scheduleHasDayses;
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
    public BranchBean getBranch() {
        return this.branch;
    }
    
    public void setBranch(BranchBean branch) {
        this.branch = branch;
    }
    public Set getScheduleHasDayses() {
        return this.scheduleHasDayses;
    }
    
    public void setScheduleHasDayses(Set scheduleHasDayses) {
        this.scheduleHasDayses = scheduleHasDayses;
    }




}


