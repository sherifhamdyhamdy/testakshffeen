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
@Entity(name = "company")
public class CompanyEntity implements Serializable {
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
    @Column(name = "address")
    private String address;
    @Column(name = "establishdate")
    @Temporal(TemporalType.DATE)
	private Date establishdate;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "region")
    private String region;
    @Column(name = "tel")
    private String tel;
    @Column(name = "website")
    private String website;
    @Column(name = "active")
    private Integer active;
    @Column(name = "biography_ar")
    private String biography_ar ;
    @Column(name = "biography_en")
    private String biography_en ;
    @Column(name = "image")
	private String image;
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

    @Column(name = "crn_image")
	private String crn_image;



    
    
    
    
    
    @JoinColumn(name = "distric_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private DistricEntity distric = new DistricEntity();
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.LAZY)			//CAUSE ERROR
    private List<UsersEntity> users = new ArrayList<UsersEntity>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.EAGER)
    private List<CompanyHasMedicalInsurance> companyHasMedicalInsurance;
    
    public CompanyEntity() {
    }

    public CompanyEntity(Integer id) {
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getEstablishdate() {
        return establishdate;
    }

    public void setEstablishdate(Date establishdate) {
        this.establishdate = establishdate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

   
 

    public DistricEntity getDistric() {
        return distric;
    }

    public void setDistric(DistricEntity distric) {
        this.distric = distric;
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
        if (!(object instanceof CompanyEntity)) {
            return false;
        }
        CompanyEntity other = (CompanyEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Branch[ id=" + id + " ]";
    }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
	public String getEmail()
	{
		return email;
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

	public String getCrn_image()
	{
		return crn_image;
	}

	public void setCrn_image(String crn_image)
	{
		this.crn_image = crn_image;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	
	
	
	
	public String getBiography_ar() {
		return biography_ar;
	}

	public void setBiography_ar(String biography_ar) {
		this.biography_ar = biography_ar;
	}

	public String getBiography_en() {
		return biography_en;
	}

	public void setBiography_en(String biography_en) {
		this.biography_en = biography_en;
	}

	public List<UsersEntity> getUsers()
	{
		return users;
	}

	public void setUsers(List<UsersEntity> users)
	{
		this.users = users;
	}

	public List<CompanyHasMedicalInsurance> getCompanyHasMedicalInsurance()
	{
		return companyHasMedicalInsurance;
	}

	public void setCompanyHasMedicalInsurance(List<CompanyHasMedicalInsurance> companyHasMedicalInsurance)
	{
		this.companyHasMedicalInsurance = companyHasMedicalInsurance;
	}

	
	

    
}
