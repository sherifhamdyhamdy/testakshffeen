package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author JDeeb
 */
@Entity(name = "insurance_company")
public class InsuranceCompanyEntity implements Serializable {
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
    @Column(name = "address")
    private String address;
    @Column(name = "active")
    private Integer active;
    
    @JoinColumn(name = "distric_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private DistricEntity distric = new DistricEntity();
	
	@Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
	
	@Column(name = "mobile")
    private String mobile;
	
	@Column(name = "establishdate")
    @Temporal(TemporalType.DATE)
	private Date establishdate;
	
	@Column(name = "region")
    private String region;
    
	@Column(name = "tel")
    private String tel;
	
	@Column(name = "website")
    private String website;
	
	@Column(name = "biography_ar")
    private String biography_ar ;
	
    @Column(name = "biography_en")
    private String biography_en ;
	
	@Column(name = "email")
	private String email;
	
    @Column(name = "address_en")
	private String address_en;

    @Column(name = "facebook")
	private String facebook;

    @Column(name = "twitter")
	private String twitter;

    @Column(name = "linkedIn")
	private String linkedIn;

    @Column(name = "instagram")
	private String instagram;
    
    @Column(name = "business_category")
	private String business_category;

    @Column(name = "num_of_employees")
	private Integer num_of_employees;

    @Column(name = "fax")
	private String fax;

    @Column(name = "contact_person_name")
	private String contact_person_name;

    @Column(name = "contact_person_title")
	private String contact_person_title;
    
    @Column(name = "contact_person_telephone")
	private String contact_person_telephone;

    @Column(name = "crn")
	private String crn;

    @Column(name = "image")
	private String image;
    
    @Column(name = "crn_image")
	private String crn_image;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "insuranceCompany", fetch = FetchType.LAZY)
    private List<UnitHasInsuranceCompanyEntity> unitHasInsuranceCompanySet;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medical_insurance", fetch = FetchType.LAZY)
    private List<CompanyHasMedicalInsurance> companyHasMedicalInsuranceList = new ArrayList<CompanyHasMedicalInsurance>();

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

    
    
    public DistricEntity getDistric()
	{
		return distric;
	}

	public void setDistric(DistricEntity distric)
	{
		this.distric = distric;
	}

	public Double getLat()
	{
		return lat;
	}

	public void setLat(Double lat)
	{
		this.lat = lat;
	}

	public Double getLng()
	{
		return lng;
	}

	public void setLng(Double lng)
	{
		this.lng = lng;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public Date getEstablishdate()
	{
		return establishdate;
	}

	public void setEstablishdate(Date establishdate)
	{
		this.establishdate = establishdate;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(String website)
	{
		this.website = website;
	}

	public String getBiography_ar()
	{
		return biography_ar;
	}

	public void setBiography_ar(String biography_ar)
	{
		this.biography_ar = biography_ar;
	}

	public String getBiography_en()
	{
		return biography_en;
	}

	public void setBiography_en(String biography_en)
	{
		this.biography_en = biography_en;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getAddress_en()
	{
		return address_en;
	}

	public void setAddress_en(String address_en)
	{
		this.address_en = address_en;
	}

	public String getFacebook()
	{
		return facebook;
	}

	public void setFacebook(String facebook)
	{
		this.facebook = facebook;
	}

	public String getTwitter()
	{
		return twitter;
	}

	public void setTwitter(String twitter)
	{
		this.twitter = twitter;
	}

	public String getLinkedIn()
	{
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn)
	{
		this.linkedIn = linkedIn;
	}

	public String getInstagram()
	{
		return instagram;
	}

	public void setInstagram(String instagram)
	{
		this.instagram = instagram;
	}

	public String getBusiness_category()
	{
		return business_category;
	}

	public void setBusiness_category(String business_category)
	{
		this.business_category = business_category;
	}

	public Integer getNum_of_employees()
	{
		return num_of_employees;
	}

	public void setNum_of_employees(Integer num_of_employees)
	{
		this.num_of_employees = num_of_employees;
	}

	public String getFax()
	{
		return fax;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public String getContact_person_name()
	{
		return contact_person_name;
	}

	public void setContact_person_name(String contact_person_name)
	{
		this.contact_person_name = contact_person_name;
	}

	public String getContact_person_title()
	{
		return contact_person_title;
	}

	public void setContact_person_title(String contact_person_title)
	{
		this.contact_person_title = contact_person_title;
	}

	public String getContact_person_telephone()
	{
		return contact_person_telephone;
	}

	public void setContact_person_telephone(String contact_person_telephone)
	{
		this.contact_person_telephone = contact_person_telephone;
	}

	public String getCrn()
	{
		return crn;
	}

	public void setCrn(String crn)
	{
		this.crn = crn;
	}
	
	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public String getCrn_image()
	{
		return crn_image;
	}

	public void setCrn_image(String crn_image)
	{
		this.crn_image = crn_image;
	}

	public List<UnitHasInsuranceCompanyEntity> getUnitHasInsuranceCompanySet() {
        return unitHasInsuranceCompanySet;
    }

    public void setUnitHasInsuranceCompanySet(List<UnitHasInsuranceCompanyEntity> unitHasInsuranceCompanySet) {
        this.unitHasInsuranceCompanySet = unitHasInsuranceCompanySet;
    }
    
    public List<CompanyHasMedicalInsurance> getCompanyHasMedicalInsuranceList()
	{
		return companyHasMedicalInsuranceList;
	}

	public void setCompanyHasMedicalInsuranceList(List<CompanyHasMedicalInsurance> companyHasMedicalInsuranceList)
	{
		this.companyHasMedicalInsuranceList = companyHasMedicalInsuranceList;
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

}
