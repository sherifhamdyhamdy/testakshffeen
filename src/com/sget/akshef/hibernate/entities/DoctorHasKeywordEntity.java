package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
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
 * @author abdelrhim
 */
@Entity(name = "doctor_has_keyword")
public class DoctorHasKeywordEntity implements Serializable 
{
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SpecialistEntity doctor;
    
    @JoinColumn(name = "keyword_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private KeywordEntity keyword;

    public DoctorHasKeywordEntity() {
    }

    public DoctorHasKeywordEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SpecialistEntity getDoctor()
	{
		return doctor;
	}

	public void setDoctor(SpecialistEntity doctor)
	{
		this.doctor = doctor;
	}

	public KeywordEntity getKeyword() {
        return keyword;
    }

    public void setKeyword(KeywordEntity keyword) {
        this.keyword = keyword;
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
        if (!(object instanceof DoctorHasKeywordEntity)) {
            return false;
        }
        DoctorHasKeywordEntity other = (DoctorHasKeywordEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.KeywordHasKeywordEntity[ id=" + id + " ]";
    }
    
}
