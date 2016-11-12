package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author JDeeb
 */
@Entity(name = "medicalhistory")
public class MedicalhistoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name="date_from")
    Date date_from;
    @Column(name="date_to")
    Date date_to;
    @Column(name="spec_name")
    String spec_name;
    @Column(name="sickness")
    String sickness;
    @Column(name="sick_type")
    String sick_type;
    @Column(name="report_type")
    String report_type;
    @Column(name="report_details")
    String report_details;
    @Column(name="details")
    String details;
    @Column(name="report_attach")
    String report_attach;
    
  
    
    
   
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private UsersEntity users;

    public MedicalhistoryEntity() {
    }

    public MedicalhistoryEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicalhistoryEntity)) {
            return false;
        }
        MedicalhistoryEntity other = (MedicalhistoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Medicalhistory[ id=" + id + " ]";
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
