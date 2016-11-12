package old_hibernateeee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@Entity(name = "category")
public class CategoryEntity implements Serializable {
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<MedicalTourismEntity> medicalTourismSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<ClinicsEntity> clinicsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<MedicalInstrumentsEntity> medicalInstrumentsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<CategoryHasSubcategoryEntity> categoryHasSubcategorySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<PharmcyEntity> pharmcySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<RadiolgyCenterEntity> radiolgyCenterSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<HospitalDataEntity> hospitalDataSet;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<InsteadRemedyEntity> insteadRemedySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private Set<TempBranchEntity> tempBranches;

 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private List<UnitHasCategory> unitHasCategoryList = new ArrayList<UnitHasCategory>();
    
	public CategoryEntity() {
    }

    public CategoryEntity(Integer id) {
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

    public Set<MedicalTourismEntity> getMedicalTourismSet() {
        return medicalTourismSet;
    }

    public void setMedicalTourismSet(Set<MedicalTourismEntity> medicalTourismSet) {
        this.medicalTourismSet = medicalTourismSet;
    }

    public Set<ClinicsEntity> getClinicsSet() {
        return clinicsSet;
    }

    public void setClinicsSet(Set<ClinicsEntity> clinicsSet) {
        this.clinicsSet = clinicsSet;
    }

    public Set<MedicalInstrumentsEntity> getMedicalInstrumentsSet() {
        return medicalInstrumentsSet;
    }

    public void setMedicalInstrumentsSet(Set<MedicalInstrumentsEntity> medicalInstrumentsSet) {
        this.medicalInstrumentsSet = medicalInstrumentsSet;
    }

    public Set<CategoryHasSubcategoryEntity> getCategoryHasSubcategorySet() {
        return categoryHasSubcategorySet;
    }

    public void setCategoryHasSubcategorySet(Set<CategoryHasSubcategoryEntity> categoryHasSubcategorySet) {
        this.categoryHasSubcategorySet = categoryHasSubcategorySet;
    }

    public Set<PharmcyEntity> getPharmcySet() {
        return pharmcySet;
    }

    public void setPharmcySet(Set<PharmcyEntity> pharmcySet) {
        this.pharmcySet = pharmcySet;
    }

    public Set<RadiolgyCenterEntity> getRadiolgyCenterSet() {
        return radiolgyCenterSet;
    }

    public void setRadiolgyCenterSet(Set<RadiolgyCenterEntity> radiolgyCenterSet) {
        this.radiolgyCenterSet = radiolgyCenterSet;
    }

    public Set<HospitalDataEntity> getHospitalDataSet() {
        return hospitalDataSet;
    }

    public void setHospitalDataSet(Set<HospitalDataEntity> hospitalDataSet) {
        this.hospitalDataSet = hospitalDataSet;
    }

//    public Set<UnitEntity> getUnitSet() {
//        return unitSet;
//    }
//
//    public void setUnitSet(Set<UnitEntity> unitSet) {
//        this.unitSet = unitSet;
//    }

    public Set<InsteadRemedyEntity> getInsteadRemedySet() {
        return insteadRemedySet;
    }

    public void setInsteadRemedySet(Set<InsteadRemedyEntity> insteadRemedySet) {
        this.insteadRemedySet = insteadRemedySet;
    }
    
    public Set<TempBranchEntity> getTempBranches() {
 		return tempBranches;
 	}

 	public void setTempBranches(Set<TempBranchEntity> tempBranches) {
 		this.tempBranches = tempBranches;
 	}
 	

    public List<UnitHasCategory> getUnitHasCategoryList()
	{
		return unitHasCategoryList;
	}

	public void setUnitHasCategoryList(List<UnitHasCategory> unitHasCategoryList)
	{
		this.unitHasCategoryList = unitHasCategoryList;
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
        if (!(object instanceof CategoryEntity)) {
            return false;
        }
        CategoryEntity other = (CategoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Category[ id=" + id + " ]";
    }
    
}
