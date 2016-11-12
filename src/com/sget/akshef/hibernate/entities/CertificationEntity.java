package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
import java.util.Date;

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
@Entity(name = "certification")
public class CertificationEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "NAME_EN")
    private String name_en;
    @Column(name = "NAME_AR")
    private String name_ar;
    @Column(name = "DATE")
    private Date date;
    
    @Column(name = "details_ar")
    private String details_ar;

    @Column(name = "details_en")
    private String details_en;
    
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UsersEntity users = new UsersEntity();
 
    @JoinColumn(name = "degree_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private DegreeEntity degree = new DegreeEntity();
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificationEntity)) {
            return false;
        }
        CertificationEntity other = (CertificationEntity) object;
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


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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

	public UsersEntity getUsers() {
		return users;
	}

	public void setUsers(UsersEntity users) {
		this.users = users;
	}

	public DegreeEntity getDegree()
	{
		return degree;
	}

	public void setDegree(DegreeEntity degree)
	{
		this.degree = degree;
	}

	

	
    
}
