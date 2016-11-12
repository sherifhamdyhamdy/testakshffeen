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
@Entity(name = "insurance_company")
public class InsuranceCompanyEntity implements Serializable {
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
    @Column(name = "address")
    private String address;
    @Column(name = "active")
    private Integer active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insuranceCompany", fetch = FetchType.LAZY)
    private Set<UnitHasInsuranceCompanyEntity> unitHasInsuranceCompanySet;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insurance_company", fetch = FetchType.LAZY)
    private Set<CompanyEntity> companySet;
    

    public InsuranceCompanyEntity() {
    }

    public InsuranceCompanyEntity(Integer id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<UnitHasInsuranceCompanyEntity> getUnitHasInsuranceCompanySet() {
        return unitHasInsuranceCompanySet;
    }

    public void setUnitHasInsuranceCompanySet(Set<UnitHasInsuranceCompanyEntity> unitHasInsuranceCompanySet) {
        this.unitHasInsuranceCompanySet = unitHasInsuranceCompanySet;
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
        if (!(object instanceof InsuranceCompanyEntity)) {
            return false;
        }
        InsuranceCompanyEntity other = (InsuranceCompanyEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.InsuranceCompany[ id=" + id + " ]";
    }

	public Set<CompanyEntity> getCompanySet() {
		return companySet;
	}

	public void setCompanySet(Set<CompanyEntity> companySet) {
		this.companySet = companySet;
	}
    
}
