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

/**
 *
 * @author JDeeb
 */
@Entity(name = "specialist")
public class SpecialistEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private Integer active;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private UsersEntity users = new UsersEntity();
    
//    @JoinColumn(name = "degree_id", referencedColumnName = "id")
//    @ManyToOne(optional = true, fetch = FetchType.EAGER)
//    private DegreeEntity degree = new DegreeEntity();


    @Column(name = "content_numbers")
	private Integer content_numbers;
    
    @Column(name = "offers_numbers")
	private Integer offers_numbers;

    @Column(name = "doctor_keywords_numbers")
	private Integer doctor_keywords_numbers;

    @Column(name = "content_keywords_numbers")
	private Integer content_keywords_numbers;
    
    @Column(name = "offers_keywords_numbers")
	private Integer offers_keywords_numbers;
   
    
    @Column(name = "image")
    private String image;   
    @Column(name = "rating")
    private Integer rating;  
    
    @Column(name = "biography")
    private String biography;   
    
    @Column(name = "biography_en")
    private String biography_en; 
    
    @Column(name = "sponsored")
    private Integer sponsored;
    
	@Column(name = "sponsored_from")
    private Date sponsored_from;
	
	@Column(name = "sponsored_to")
    private Date sponsored_to;
	
    @Column(name = "screen_name_en")
    private String screen_name_en;   
    
    @Column(name = "sos_number")
    private Integer sos_number;
    
    @Column(name = "languages")
    private String languages;
    
    @Column(name = "contentCategory_management")
    private Boolean contentCategory_management;
    
    @Column(name = "content_management")
    private Boolean content_management;
    
    @Column(name = "offer_management")
    private Boolean offer_management;
    
    @Column(name = "user_recommend")
    private Integer user_recommend;
    
    

	
        
        
    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY)
    private Set<UserFavoritiesEntity> userFavoritiesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.LAZY)
    private List<SpecialistHasBranchEntity> specialistHasBranchSet = new ArrayList<SpecialistHasBranchEntity>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.EAGER)
    private Set<UserRateSpecEntity> userRateSpecSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.EAGER)
    private List<SectionsHasSpecialistEntity> sectionsHasSpecialistSet = new ArrayList<SectionsHasSpecialistEntity>();
    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY)
    private Set<CommentsEntity> commentsSet;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.LAZY)
//    private Set<CertificationEntity> certificationSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialist", fetch = FetchType.LAZY)
    private Set<ProfessionalExpEntity> professionalSet;
    
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<Doctor_scheduleEntity> doctor_scheduleList = new ArrayList<Doctor_scheduleEntity>();
   
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<DoctorHasKeywordEntity> doctorHasKeywordList = new ArrayList<DoctorHasKeywordEntity>();
    
    
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

    public List<SpecialistHasBranchEntity> getSpecialistHasBranchSet() {
        return specialistHasBranchSet;
    }

    public void setSpecialistHasBranchSet(List<SpecialistHasBranchEntity> specialistHasBranchSet) {
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

	

	




	public Set<ProfessionalExpEntity> getProfessionalSet() {
		return professionalSet;
	}

	public void setProfessionalSet(Set<ProfessionalExpEntity> professionalSet) {
		this.professionalSet = professionalSet;
	}

	
	public List<Doctor_scheduleEntity> getDoctor_scheduleList()
	{
		return doctor_scheduleList;
	}
	public void setDoctor_scheduleList(List<Doctor_scheduleEntity> doctor_scheduleList)
	{
		this.doctor_scheduleList = doctor_scheduleList;
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
	public List<DoctorHasKeywordEntity> getDoctorHasKeywordList()
	{
		return doctorHasKeywordList;
	}
	public void setDoctorHasKeywordList(List<DoctorHasKeywordEntity> doctorHasKeywordList)
	{
		this.doctorHasKeywordList = doctorHasKeywordList;
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
	
	
	public String getScreen_name_en()
	{
		return screen_name_en;
	}
	public void setScreen_name_en(String screen_name_en)
	{
		this.screen_name_en = screen_name_en;
	}
	public Integer getSos_number()
	{
		return sos_number;
	}
	public void setSos_number(Integer sos_number)
	{
		this.sos_number = sos_number;
	}
	
	public String getLanguages()
	{
		return languages;
	}
	public void setLanguages(String languages)
	{
		this.languages = languages;
	}
	
	
	public Boolean getContentCategory_management()
	{
		return contentCategory_management;
	}
	public void setContentCategory_management(Boolean contentCategory_management)
	{
		this.contentCategory_management = contentCategory_management;
	}
	public Boolean getContent_management()
	{
		return content_management;
	}
	public void setContent_management(Boolean content_management)
	{
		this.content_management = content_management;
	}
	public Boolean getOffer_management()
	{
		return offer_management;
	}
	public void setOffer_management(Boolean offer_management)
	{
		this.offer_management = offer_management;
	}
	
	
	
	public Integer getUser_recommend()
	{
		return user_recommend;
	}
	public void setUser_recommend(Integer user_recommend)
	{
		this.user_recommend = user_recommend;
	}
	public Integer getContent_numbers()
	{
		return content_numbers;
	}
	public void setContent_numbers(Integer content_numbers)
	{
		this.content_numbers = content_numbers;
	}
	public Integer getOffers_numbers()
	{
		return offers_numbers;
	}
	public void setOffers_numbers(Integer offers_numbers)
	{
		this.offers_numbers = offers_numbers;
	}
	public Integer getDoctor_keywords_numbers()
	{
		return doctor_keywords_numbers;
	}
	public void setDoctor_keywords_numbers(Integer doctor_keywords_numbers)
	{
		this.doctor_keywords_numbers = doctor_keywords_numbers;
	}
	public Integer getContent_keywords_numbers()
	{
		return content_keywords_numbers;
	}
	public void setContent_keywords_numbers(Integer content_keywords_numbers)
	{
		this.content_keywords_numbers = content_keywords_numbers;
	}
	public Integer getOffers_keywords_numbers()
	{
		return offers_keywords_numbers;
	}
	public void setOffers_keywords_numbers(Integer offers_keywords_numbers)
	{
		this.offers_keywords_numbers = offers_keywords_numbers;
	}

	
	
    
}
