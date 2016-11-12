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
@Entity(name = "company_has_medical_insurance")
public class CompanyHasMedicalInsurance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
   
    private Integer id;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private CompanyEntity company = new CompanyEntity();
    
    @JoinColumn(name = "medical_insurance_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private InsuranceCompanyEntity medical_insurance;

    public CompanyHasMedicalInsurance() {
    }

    public CompanyHasMedicalInsurance(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    
    public InsuranceCompanyEntity getMedical_insurance()
	{
		return medical_insurance;
	}

	public void setMedical_insurance(InsuranceCompanyEntity medical_insurance)
	{
		this.medical_insurance = medical_insurance;
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
        if (!(object instanceof CompanyHasMedicalInsurance)) {
            return false;
        }
        CompanyHasMedicalInsurance other = (CompanyHasMedicalInsurance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.CompanyHasInsuranceCompany[ id=" + id + " ]";
    }
    
}
