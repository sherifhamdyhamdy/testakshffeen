package old_hibernateeee;

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
@Entity(name = "branch")
public class BranchEntity implements Serializable {
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
    
    @Column(name = "category_id")
    private Integer category_id;
    
    @Column(name = "biography_ar")
    private String biography_ar ;
    @Column(name = "biography_en")
    private String biography_en ;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
    
    @Column(name = "address")
    private String address;
    @Column(name = "establishdate")
    @Temporal(TemporalType.DATE)
    private Date establishdate;
    
    @Column(name = "tel")
    private String tel;
    @Column(name = "postlcode")
    private String postlcode;
    @Column(name = "fax")
    private String fax ;
    @Column(name = "hotline")
    private String hotline;
    
    @Column(name = "website")
    private String website;
    @Column(name = "email")
    private String email;
    
    @Column(name = "branch_rating")
    private Integer branch_rating;
    @Column(name = "active")
    private Integer active;
    
    //added by liliane to show the image
    @Column(name = "image")
    private String image;
    
    @Column(name = "user_recommend")
    private Integer user_recommend;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch", fetch = FetchType.LAZY)
    private Set<SubcategoryHasSectionsHasBranchEntity> subcategoryHasSectionsHasBranchSet;
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private Set<UserFavoritiesEntity> userFavoritiesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch", fetch = FetchType.LAZY)
    private Set<BranchGroupsEntity> branchGroupsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch", fetch = FetchType.LAZY)
    private Set<SpecialistHasBranchEntity> specialistHasBranchSet;
    @JoinColumn(name = "unit_unit_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UnitEntity unit;
    @JoinColumn(name = "distric_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DistricEntity distric;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch", fetch = FetchType.LAZY)
    private Set<UserRateBranchEntity> userRateBranchSet;
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private Set<CommentsEntity> commentsSet;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UsersEntity users;

    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Doctor_scheduleEntity> doctor_scheduleList = new ArrayList<Doctor_scheduleEntity>();

    
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Branch_scheduleEntity> branch_scheduleList = new ArrayList<Branch_scheduleEntity>();
    
    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch", fetch = FetchType.LAZY)
    private List<BranchHasKeywordEntity> branchHasKeywordList = new ArrayList<BranchHasKeywordEntity>();
    
    
    @Column(name = "sponsored")
    private Integer sponsored;
    
	@Column(name = "sponsored_from")
    private Date sponsored_from;
	
	@Column(name = "sponsored_to")
    private Date sponsored_to;
	
    @Column(name = "has_delivery")
    private Integer has_delivery;
    
    
    public BranchEntity() {
    }
    public BranchEntity(Integer id) {
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
      
    
    public Integer getCategory_id()
	{
		return category_id;
	}
	public void setCategory_id(Integer category_id)
	{
		this.category_id = category_id;
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
    public Integer getRating() {
        return branch_rating;
    }
    public void setRating(Integer branch_rating) {
        this.branch_rating = branch_rating;
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
    	return active != null ? active : 0;
    }
    public void setActive(Integer active) {
        this.active = active;
    }
    public Set<SubcategoryHasSectionsHasBranchEntity> getSubcategoryHasSectionsHasBranchSet() {
        return subcategoryHasSectionsHasBranchSet;
    }
    public void setSubcategoryHasSectionsHasBranchSet(Set<SubcategoryHasSectionsHasBranchEntity> subcategoryHasSectionsHasBranchSet) {
        this.subcategoryHasSectionsHasBranchSet = subcategoryHasSectionsHasBranchSet;
    }
    public Set<UserFavoritiesEntity> getUserFavoritiesSet() {
        return userFavoritiesSet;
    }
    public void setUserFavoritiesSet(Set<UserFavoritiesEntity> userFavoritiesSet) {
        this.userFavoritiesSet = userFavoritiesSet;
    }
    public Set<BranchGroupsEntity> getBranchGroupsSet() {
        return branchGroupsSet;
    }
    public void setBranchGroupsSet(Set<BranchGroupsEntity> branchGroupsSet) {
        this.branchGroupsSet = branchGroupsSet;
    }
    public Set<SpecialistHasBranchEntity> getSpecialistHasBranchSet() {
        return specialistHasBranchSet;
    }
    public void setSpecialistHasBranchSet(Set<SpecialistHasBranchEntity> specialistHasBranchSet) {
        this.specialistHasBranchSet = specialistHasBranchSet;
    }
    public UnitEntity getUnit() {
        return unit;
    }
    public void setUnit(UnitEntity unit) {
        this.unit = unit;
    }
    public DistricEntity getDistric() {
        return distric;
    }
    public void setDistric(DistricEntity distric) {
        this.distric = distric;
    }
    public Set<UserRateBranchEntity> getUserRateBranchSet() {
        return userRateBranchSet;
    }
    public void setUserRateBranchSet(Set<UserRateBranchEntity> userRateBranchSet) {
        this.userRateBranchSet = userRateBranchSet;
    }
    public Set<CommentsEntity> getCommentsSet() {
        return commentsSet;
    }
    public void setCommentsSet(Set<CommentsEntity> commentsSet) {
        this.commentsSet = commentsSet;
    }
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getBranch_rating() {
		return branch_rating;
	}
	public void setBranch_rating(Integer branch_rating) {
		this.branch_rating = branch_rating;
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
	public UsersEntity getUsers() {
		return users;
	}
	public void setUsers(UsersEntity users) {
		this.users = users;
	}
	public String getPostlcode() {
		return postlcode;
	}
	public void setPostlcode(String postlcode) {
		this.postlcode = postlcode;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getHotline() {
		return hotline;
	}
	public void setHotline(String hotline) {
		this.hotline = hotline;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	
	
	public List<Doctor_scheduleEntity> getDoctor_scheduleList()
	{
		return doctor_scheduleList;
	}
	public void setDoctor_scheduleList(List<Doctor_scheduleEntity> doctor_scheduleList)
	{
		this.doctor_scheduleList = doctor_scheduleList;
	}
	
	
	public List<Branch_scheduleEntity> getBranch_scheduleList()
	{
		return branch_scheduleList;
	}
	public void setBranch_scheduleList(List<Branch_scheduleEntity> branch_scheduleList)
	{
		this.branch_scheduleList = branch_scheduleList;
	}
	
	
	
	
	
	public List<BranchHasKeywordEntity> getBranchHasKeywordList()
	{
		return branchHasKeywordList;
	}
	public void setBranchHasKeywordList(List<BranchHasKeywordEntity> branchHasKeywordList)
	{
		this.branchHasKeywordList = branchHasKeywordList;
	}
	
	
	public Integer getUser_recommend()
	{
		return user_recommend;
	}
	public void setUser_recommend(Integer user_recommend)
	{
		this.user_recommend = user_recommend;
	}
	
	
	
	public Integer getSponsored()
	{
		return sponsored;
	}
	public void setSponsored(Integer sponsored)
	{
		this.sponsored = sponsored;
	}
	
	public Date getSponsored_from()
	{
		return sponsored_from;
	}
	public void setSponsored_from(Date sponsored_from)
	{
		this.sponsored_from = sponsored_from;
	}
	public Date getSponsored_to()
	{
		return sponsored_to;
	}
	public void setSponsored_to(Date sponsored_to)
	{
		this.sponsored_to = sponsored_to;
	}
	public Integer getHas_delivery()
	{
		return has_delivery;
	}
	public void setHas_delivery(Integer has_delivery)
	{
		this.has_delivery = has_delivery;
	}
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BranchEntity)) {
            return false;
        }
        BranchEntity other = (BranchEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Branch[ id=" + id + " ]";
    }
}
