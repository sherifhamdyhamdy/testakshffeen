package old_hibernateeee;

import java.io.Serializable;
import java.sql.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author JDeeb
 */
@Entity(name = "specialist")
public class SpecialistEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private Integer active;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UsersEntity users;
    
//    @JoinColumn(name = "degree_id", referencedColumnName = "id")
//    @ManyToOne(optional = false, fetch = FetchType.EAGER)
//    private DegreeEntity degree;
    
    @Column(name = "logo")
    private String logo;
        
    @Column(name = "languages")
    private String languages;
        
    
    @Column(name = "image")
    private String image;   
    @Column(name = "rating")
    private Integer rating;  
    
    @Column(name = "biography")
    private String biography;   
    
    @Column(name = "biography_en")
    private String biography_en; 
    
    @Column(name = "website")
    private String website; 
    
	
    @Column(name = "user_recommend")
    private Integer user_recommend;
	
	@Column(name = "grad_year")
    private Date gradYear;

		
    @Column(name = "buis_name")
    private String buisnessName;
    
    @Column(name = "buis_name_en")
    private String buisnessName_en;
    
    @Column(name = "job_no_lincs")
    private String job_no_lincs;    
    @Column(name = "gradFac")
    private String gradFac; 
    @Column(name = "union_id")
    private String union_id;     
    @Column(name = "degree_id")
    private Integer degree_id;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY)
    private Set<UserFavoritiesEntity> userFavoritiesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.LAZY)
    private Set<SpecialistHasBranchEntity> specialistHasBranchSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.EAGER)
    private Set<UserRateSpecEntity> userRateSpecSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.EAGER)
    private List<SectionsHasSpecialistEntity> sectionsHasSpecialistSet;
    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY)
    private Set<CommentsEntity> commentsSet;
    
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<Doctor_scheduleEntity> doctor_scheduleList = new ArrayList<Doctor_scheduleEntity>();
    
    
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorHasKeywordEntity> doctorHasKeywordList = new ArrayList<DoctorHasKeywordEntity>();
    
    @Column(name = "sponsored")
    private Integer sponsored;
    
	@Column(name = "sponsored_from")
    private Date sponsored_from;
	
	@Column(name = "sponsored_to")
    private Date sponsored_to;
	
    
    // Setters & Getters
    public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
    public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public SpecialistEntity() {
    }

    public SpecialistEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getActive() {
        return active == null ? 0 : active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public Set<UserFavoritiesEntity> getUserFavoritiesSet() {
        return userFavoritiesSet;
    }

    public void setUserFavoritiesSet(Set<UserFavoritiesEntity> userFavoritiesSet) {
        this.userFavoritiesSet = userFavoritiesSet;
    }

    public Set<SpecialistHasBranchEntity> getSpecialistHasBranchSet() {
        return specialistHasBranchSet;
    }

    public void setSpecialistHasBranchSet(Set<SpecialistHasBranchEntity> specialistHasBranchSet) {
        this.specialistHasBranchSet = specialistHasBranchSet;
    }

    public Set<UserRateSpecEntity> getUserRateSpecSet() {
        return userRateSpecSet;
    }

    public void setUserRateSpecSet(Set<UserRateSpecEntity> userRateSpecSet) {
        this.userRateSpecSet = userRateSpecSet;
    }

    public List<SectionsHasSpecialistEntity> getSectionsHasSpecialistSet() {
        return sectionsHasSpecialistSet;
    }

    public void setSectionsHasSpecialistSet(List<SectionsHasSpecialistEntity> sectionsHasSpecialistSet) {
        this.sectionsHasSpecialistSet = sectionsHasSpecialistSet;
    }

    public Set<CommentsEntity> getCommentsSet() {
        return commentsSet;
    }

    public void setCommentsSet(Set<CommentsEntity> commentsSet) {
        this.commentsSet = commentsSet;
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
        if (!(object instanceof SpecialistEntity)) {
            return false;
        }
        SpecialistEntity other = (SpecialistEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Specialist[ id=" + id + " ]";
    }

	

	



	public String getBuisnessName() {
		return buisnessName;
	}

	public void setBuisnessName(String buisnessName) {
		this.buisnessName = buisnessName;
	}
	
	public String getBuisnessName_en()
	{
		return buisnessName_en;
	}
	public void setBuisnessName_en(String buisnessName_en)
	{
		this.buisnessName_en = buisnessName_en;
	}
	public String getJob_no_lincs() {
		return job_no_lincs;
	}

	public void setJob_no_lincs(String job_no_lincs) {
		this.job_no_lincs = job_no_lincs;
	}

	public String getGradFac() {
		return gradFac;
	}

	public void setGradFac(String gradFac) {
		this.gradFac = gradFac;
	}



	public Date getGradYear() {
		return gradYear;
	}

	public void setGradYear(Date gradYear) {
		this.gradYear = gradYear;
	}
//	public DegreeEntity getDegree() {
//		return degree;
//	}
//	public void setDegree(DegreeEntity degree) {
//		this.degree = degree;
//	}
	public String getUnion_id() {
		return union_id;
	}
	public void setUnion_id(String union_id) {
		this.union_id = union_id;
	}
	public List<Doctor_scheduleEntity> getDoctor_scheduleList()
	{
		return doctor_scheduleList;
	}
	public void setDoctor_scheduleList(List<Doctor_scheduleEntity> doctor_scheduleList)
	{
		this.doctor_scheduleList = doctor_scheduleList;
	}
	
	
	
	
	public String getLanguages()
	{
		return languages;
	}
	public void setLanguages(String languages)
	{
		this.languages = languages;
	}
		
	public String getLogo()
	{
		return logo;
	}
	public void setLogo(String logo)
	{
		this.logo = logo;
	}
	public Integer getDegree_id()
	{
		return degree_id;
	}
	public void setDegree_id(Integer degree_id)
	{
		this.degree_id = degree_id;
	}


    public String getBiography()
	{
		return biography;
	}
	public void setBiography(String biography)
	{
		this.biography = biography;
	}
		
	public String getBiography_en()
	{
		return biography_en;
	}
	public void setBiography_en(String biography_en)
	{
		this.biography_en = biography_en;
	}
	public String getWebsite()
	{
		return website;
	}
	public void setWebsite(String website)
	{
		this.website = website;
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
	
	public List<DoctorHasKeywordEntity> getDoctorHasKeywordList()
	{
		return doctorHasKeywordList;
	}
	public void setDoctorHasKeywordList(List<DoctorHasKeywordEntity> doctorHasKeywordList)
	{
		this.doctorHasKeywordList = doctorHasKeywordList;
	}

	
	
	
	
    
}
