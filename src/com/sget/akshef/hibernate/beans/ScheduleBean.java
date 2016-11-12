package com.sget.akshef.hibernate.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class ScheduleBean  implements java.io.Serializable {


     private int id;
     private Date from_date;
     private Date to_date;
     private int active;
     private Set scheduleHasDayses = new HashSet(0);

    public ScheduleBean() {
    }

    public ScheduleBean(Date from, Date to, int active, Set scheduleHasDayses) {
       this.from_date = from;
       this.to_date = to;
       this.active = active;
       this.scheduleHasDayses = scheduleHasDayses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Date getFrom_date() {
        return this.from_date;
    }
    
    public void setFrom_date(Date from) {
        this.from_date = from;
    }
    public Date getTo_date() {
        return this.to_date;
    }
    
    public void setTo_date(Date to) {
        this.to_date = to;
    }
    public int getActive() {
        return this.active;
    }
    
    public void setActive(int active) {
        this.active = active;
    }
    public Set getScheduleHasDayses() {
        return this.scheduleHasDayses;
    }
    
    public void setScheduleHasDayses(Set scheduleHasDayses) {
        this.scheduleHasDayses = scheduleHasDayses;
    }

}
