package com.sget.akshef.hibernate.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author JDeeb
 */

public class CertificationBean implements Serializable,Cloneable {
    private static final long serialVersionUID = 1L;
   
    private Integer id;
    private String name_en;
    private String name_ar;
    private String date;
    private String details_ar;
    private String details_en;
    
    
    public Object clone()throws CloneNotSupportedException{  
    	return super.clone();  
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
        if (!(object instanceof CertificationBean)) {
            return false;
        }
        CertificationBean other = (CertificationBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Country[ id=" + id + " ]";
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	public String getName_ar() {
		return name_ar;
	}

	public void setName_ar(String name_ar) {
		this.name_ar = name_ar;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDetails_ar()
	{
		return details_ar;
	}

	public void setDetails_ar(String details_ar)
	{
		this.details_ar = details_ar;
	}

	public String getDetails_en()
	{
		return details_en;
	}

	public void setDetails_en(String details_en)
	{
		this.details_en = details_en;
	}

	
    
}
