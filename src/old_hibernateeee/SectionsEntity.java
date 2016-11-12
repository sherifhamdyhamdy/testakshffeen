package old_hibernateeee;

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
@Entity(name = "sections")
public class SectionsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name_ar")
    private String nameAr;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "active")
    private Integer active;
    @Column(name = "image")
    private String image;
   
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sections", fetch = FetchType.EAGER)
    private Set<SubcategoryHasSectionsEntity> subcategoryHasSectionsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sections", fetch = FetchType.LAZY)
    private Set<SectionsHasSpecialistEntity> sectionsHasSpecialistSet;

    public SectionsEntity() {
    }

    public SectionsEntity(Integer id) {
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
    
    public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public Set<SubcategoryHasSectionsEntity> getSubcategoryHasSectionsSet() {
        return subcategoryHasSectionsSet;
    }

    public void setSubcategoryHasSectionsSet(Set<SubcategoryHasSectionsEntity> subcategoryHasSectionsSet) {
        this.subcategoryHasSectionsSet = subcategoryHasSectionsSet;
    }

    public Set<SectionsHasSpecialistEntity> getSectionsHasSpecialistSet() {
        return sectionsHasSpecialistSet;
    }

    public void setSectionsHasSpecialistSet(Set<SectionsHasSpecialistEntity> sectionsHasSpecialistSet) {
        this.sectionsHasSpecialistSet = sectionsHasSpecialistSet;
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
        if (!(object instanceof SectionsEntity)) {
            return false;
        }
        SectionsEntity other = (SectionsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Sections[ id=" + id + " ]";
    }
    
}
