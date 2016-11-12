package old_hibernateeee;

import java.io.Serializable;
import java.util.Date;
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
    @Basic(optional = false)
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
   
    @JoinColumn(name = "distric_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DistricEntity distric;
    
    
    @JoinColumn(name = "insurance_company_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private InsuranceCompanyEntity insurance_company;
    

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

	public InsuranceCompanyEntity getInsurance_company() {
		return insurance_company;
	}

	public void setInsurance_company(InsuranceCompanyEntity insurance_company) {
		this.insurance_company = insurance_company;
	}



    
}
