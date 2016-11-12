package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class UnitHasInsuranceCompanyBean  implements java.io.Serializable {


     private int id;
     private InsuranceCompanyBean insuranceCompany;
     private UnitBean unit;

    public UnitHasInsuranceCompanyBean() {
    }

    public UnitHasInsuranceCompanyBean(InsuranceCompanyBean insuranceCompany, UnitBean unit) {
       this.insuranceCompany = insuranceCompany;
       this.unit = unit;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public InsuranceCompanyBean getInsuranceCompany() {
        return this.insuranceCompany;
    }
    
    public void setInsuranceCompany(InsuranceCompanyBean insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }
    public UnitBean getUnit() {
        return this.unit;
    }
    
    public void setUnit(UnitBean unit) {
        this.unit = unit;
    }




}


