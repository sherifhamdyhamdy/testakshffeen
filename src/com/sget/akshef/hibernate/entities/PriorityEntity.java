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
import javax.persistence.OneToMany;

/**
 *
 * @author JDeeb
 */
@Entity(name = "priority")
public class PriorityEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name_ar")
    private String nameAr;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "active")
    private Integer active;
    @Column(name = "code")
    private String code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "priority", fetch = FetchType.EAGER)
    private Set<ContentDetailsEntity> contentDetailsSet;

    public PriorityEntity() {
    }

    public PriorityEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getActive() {
    	return active != null ? active : 0;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ContentDetailsEntity> getContentDetailsSet() {
        return contentDetailsSet;
    }

    public void setContentDetailsSet(Set<ContentDetailsEntity> contentDetailsSet) {
        this.contentDetailsSet = contentDetailsSet;
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
        if (!(object instanceof PriorityEntity)) {
            return false;
        }
        PriorityEntity other = (PriorityEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Priority[ id=" + id + " ]";
    }
    
}
