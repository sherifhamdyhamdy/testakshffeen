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
@Entity(name = "subcategory")
public class SubcategoryEntity implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcategory", fetch = FetchType.EAGER)
    private Set<CategoryHasSubcategoryEntity> categoryHasSubcategorySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcategory", fetch = FetchType.EAGER)
    private Set<SubcategoryHasSectionsEntity> subcategoryHasSectionsSet;

    public SubcategoryEntity() {
    }

    public SubcategoryEntity(Integer id) {
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

    public Set<CategoryHasSubcategoryEntity> getCategoryHasSubcategorySet() {
        return categoryHasSubcategorySet;
    }

    public void setCategoryHasSubcategorySet(Set<CategoryHasSubcategoryEntity> categoryHasSubcategorySet) {
        this.categoryHasSubcategorySet = categoryHasSubcategorySet;
    }

    public Set<SubcategoryHasSectionsEntity> getSubcategoryHasSectionsSet() {
        return subcategoryHasSectionsSet;
    }

    public void setSubcategoryHasSectionsSet(Set<SubcategoryHasSectionsEntity> subcategoryHasSectionsSet) {
        this.subcategoryHasSectionsSet = subcategoryHasSectionsSet;
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
        if (!(object instanceof SubcategoryEntity)) {
            return false;
        }
        SubcategoryEntity other = (SubcategoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Subcategory[ id=" + id + " ]";
    }
    
}
