package com.sget.akshef.hibernate.entities;

import java.io.Serializable;

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
@Entity(name = "sections_has_specialist")
public class SectionsHasSpecialistEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "specialist_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private SpecialistEntity specialist;
    @JoinColumn(name = "sections_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private SectionsEntity sections;

    public SectionsHasSpecialistEntity() {
    }

    public SectionsHasSpecialistEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SpecialistEntity getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistEntity specialist) {
        this.specialist = specialist;
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
        if (!(object instanceof SectionsHasSpecialistEntity)) {
            return false;
        }
        SectionsHasSpecialistEntity other = (SectionsHasSpecialistEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.SectionsHasSpecialist[ id=" + id + " ]";
    }
    
}
