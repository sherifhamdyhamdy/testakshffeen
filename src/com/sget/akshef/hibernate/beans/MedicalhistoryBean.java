package com.sget.akshef.hibernate.beans;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

/**
 * Author JDeeb
 */
public class MedicalhistoryBean implements Serializable,Cloneable {


     private int id;
     private UsersBean users;
     Date date_from;
     Date date_to;
     String spec_name;
     String sickness;
     String sick_type;
     String report_type;
     String report_details;
     String details;
     String report_attach;
     public Object clone()throws CloneNotSupportedException{  
     	return super.clone();  
     	} 
       

    public MedicalhistoryBean() {
    }

	
    public MedicalhistoryBean(UsersBean users) {
        this.users = users;
    }
    public MedicalhistoryBean(UsersBean users, int age) {
       this.users = users;
      
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public UsersBean getUsers() {
        return this.users;
    }
    
    public void setUsers(UsersBean users) {
        this.users = users;
    }


	public Date getDate_from() {
		return date_from;
	}


	public void setDate_from(Date date_from) {
		this.date_from = date_from;
	}


	public Date getDate_to() {
		return date_to;
	}


	public void setDate_to(Date date_to) {
		this.date_to = date_to;
	}


	public String getSpec_name() {
		return spec_name;
	}


	public void setSpec_name(String spec_name) {
		this.spec_name = spec_name;
	}


	public String getSickness() {
		return sickness;
	}


	public void setSickness(String sickness) {
		this.sickness = sickness;
	}


	public String getSick_type() {
		return sick_type;
	}


	public void setSick_type(String sick_type) {
		this.sick_type = sick_type;
	}


	public String getReport_type() {
		return report_type;
	}


	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}


	public String getReport_details() {
		return report_details;
	}


	public void setReport_details(String report_details) {
		this.report_details = report_details;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public String getReport_attach() {
		return report_attach;
	}


	public void setReport_attach(String report_attach) {
		this.report_attach = report_attach;
	}
  




}


