package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author JDeeb
 */
@Entity(name = "degree")
public class DegreeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "degree")
    private String degree;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "degree", fetch = FetchType.LAZY)
    private List<CertificationEntity> certificationList;
    
    public DegreeEntity() {
    }

    public DegreeEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    public List<CertificationEntity> getCertificationList()
	{
		return certificationList;
	}

	public void setCertificationList(List<CertificationEntity> certificationList)
	{
		this.certificationList = certificationList;
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
        if (!(object instanceof DegreeEntity)) {
            return false;
        }
        DegreeEntity other = (DegreeEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.DegreeEntity[ id=" + id + " ]";
    }
    
}
