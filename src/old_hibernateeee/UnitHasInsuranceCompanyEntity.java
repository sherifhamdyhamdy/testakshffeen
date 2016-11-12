package old_hibernateeee;

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
@Entity(name = "unit_has_insurance_company")
public class UnitHasInsuranceCompanyEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "unit_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UnitEntity unit;
    @JoinColumn(name = "insurance_company_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private InsuranceCompanyEntity insuranceCompany;

    public UnitHasInsuranceCompanyEntity() {
    }

    public UnitHasInsuranceCompanyEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UnitEntity getUnit() {
        return unit;
    }

    public void setUnit(UnitEntity unit) {
        this.unit = unit;
    }

    public InsuranceCompanyEntity getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(InsuranceCompanyEntity insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
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
        if (!(object instanceof UnitHasInsuranceCompanyEntity)) {
            return false;
        }
        UnitHasInsuranceCompanyEntity other = (UnitHasInsuranceCompanyEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.UnitHasInsuranceCompany[ id=" + id + " ]";
    }
    
}
