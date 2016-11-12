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
 * @author JDeeb
 */
@Entity(name = "subcategory_has_sections")
public class SubcategoryHasSectionsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subcategoryHasSections", fetch = FetchType.LAZY)
    private Set<SubcategoryHasSectionsHasBranchEntity> subcategoryHasSectionsHasBranchSet;
    @JoinColumn(name = "subcategory_subcategory_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private SubcategoryEntity subcategory = new SubcategoryEntity();
    @JoinColumn(name = "sections_section_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private SectionsEntity sections = new SectionsEntity();

    public SubcategoryHasSectionsEntity() {
    }

    public SubcategoryHasSectionsEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<SubcategoryHasSectionsHasBranchEntity> getSubcategoryHasSectionsHasBranchSet() {
        return subcategoryHasSectionsHasBranchSet;
    }

    public void setSubcategoryHasSectionsHasBranchSet(Set<SubcategoryHasSectionsHasBranchEntity> subcategoryHasSectionsHasBranchSet) {
        this.subcategoryHasSectionsHasBranchSet = subcategoryHasSectionsHasBranchSet;
    }

    public SubcategoryEntity getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(SubcategoryEntity subcategory) {
        this.subcategory = subcategory;
    }

    public SectionsEntity getSections() {
        return sections;
    }

    public void setSections(SectionsEntity sections) {
        this.sections = sections;
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
        if (!(object instanceof SubcategoryHasSectionsEntity)) {
            return false;
        }
        SubcategoryHasSectionsEntity other = (SubcategoryHasSectionsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.SubcategoryHasSections[ id=" + id + " ]";
    }
    
}
