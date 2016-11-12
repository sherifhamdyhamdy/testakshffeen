package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class ScheduleHasDaysBean  implements java.io.Serializable {


     private int id;
     private SpecialistHasBranchBean specialistHasBranch;
     private ScheduleBean schedule;
     private DaysBean days;

    public ScheduleHasDaysBean() {
    }

    public ScheduleHasDaysBean(SpecialistHasBranchBean specialistHasBranch, ScheduleBean schedule, DaysBean days) {
       this.specialistHasBranch = specialistHasBranch;
       this.schedule = schedule;
       this.days = days;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public SpecialistHasBranchBean getSpecialistHasBranch() {
        return this.specialistHasBranch;
    }
    
    public void setSpecialistHasBranch(SpecialistHasBranchBean specialistHasBranch) {
        this.specialistHasBranch = specialistHasBranch;
    }
    public ScheduleBean getSchedule() {
        return this.schedule;
    }
    
    public void setSchedule(ScheduleBean schedule) {
        this.schedule = schedule;
    }
    public DaysBean getDays() {
        return this.days;
    }
    
    public void setDays(DaysBean days) {
        this.days = days;
    }




}


