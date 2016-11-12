package old_hibernateeee;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
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
@Entity(name = "users")
public class UsersEntity implements Serializable {
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
    @Column(name = "national_id")
    private String nationalId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    
    @Column(name = "firstname")
    private String firstName;
    
    @Column(name = "lastname")
    private String lastName;
    
    @Column(name = "profile_img")
    private String profile_img;
    
    @Column(name = "gender")
    private Integer gender;
    
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "telephone")
    private String telephone;
    
    @Column(name = "mobile")
    private String mobile;
    
    @Column(name = "marital_stat")
    private String marital_stat;
    
    @Column(name = "emergency_contact")
    private String emergency_contact;
    
    @Column(name = "birthdate")
    private Date birthdate;

    
    @Column(name = "creationDate")
    private Date creationDate;
    
    @Column(name = "emailCount")
	private Integer emailCount;
	
    @Column(name = "active_email")
    private Integer active_email;
    
    
    @Column(name = "blood_type")
    private String blood_type;
    
    
    @Column(name = "progress")
    private String progress;
    
    
    @Column(name = "pass_creationDate")
    private Date pass_creationDate;
    
    @Column(name = "pass_emailCount")
    private Integer pass_emailCount;
    
    @Column(name = "forget_password_code")
    private String forget_password_code;
    
    @Column(name = "token")
    private String token;
    
    @Column(name = "platform")
    private Integer platform;
        
    @Column(name = "login")
    private Integer login;
    	
    	
    	
	@Column(name = "active")
    private Integer active;
	
	@Transient
	private int user_type;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<SpecialistEntity> specialistSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toUser", fetch = FetchType.LAZY)
    private Set<MessagesEntity> messagesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fromUser", fetch = FetchType.LAZY)
    private Set<MessagesEntity> messagesSet1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<ApprovalMsgEntity> approvalMsgSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.EAGER)
    private Set<MedicalhistoryEntity> medicalhistorySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<ContentDetailsEntity> contentDetailsSet;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
//    private Set<ProfissionaldataEntity> profissionaldataSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<UserFavoritiesEntity> userFavoritiesSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<UserRateSpecEntity> userRateSpecSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<UserRateBranchEntity> userRateBranchSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<CommentsEntity> commentsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<UsersGroupsEntity> usersGroupsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<BranchEntity> branchSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private Set<UnitEntity> unitSet;
    @JoinColumn(name = "district_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private DistricEntity distric;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.EAGER)
    private Set<CertificationEntity> certificationSet = new HashSet<CertificationEntity>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.EAGER)
    private Set<ProfessionalExpEntity> professionalSet;

    public UsersEntity() {
    }

    public UsersEntity(Integer id) {
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

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }


    public Integer getGender() {
		return gender == null ? 0 : gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
    
    
    public Set<SpecialistEntity> getSpecialistSet() {
        return specialistSet;
    }

    public void setSpecialistSet(Set<SpecialistEntity> specialistSet) {
        this.specialistSet = specialistSet;
    }

    public Set<MessagesEntity> getMessagesSet() {
        return messagesSet;
    }

    public void setMessagesSet(Set<MessagesEntity> messagesSet) {
        this.messagesSet = messagesSet;
    }

    public Set<MessagesEntity> getMessagesSet1() {
        return messagesSet1;
    }

    public void setMessagesSet1(Set<MessagesEntity> messagesSet1) {
        this.messagesSet1 = messagesSet1;
    }

    public Set<ApprovalMsgEntity> getApprovalMsgSet() {
        return approvalMsgSet;
    }

    public void setApprovalMsgSet(Set<ApprovalMsgEntity> approvalMsgSet) {
        this.approvalMsgSet = approvalMsgSet;
    }

    public Set<MedicalhistoryEntity> getMedicalhistorySet() {
        return medicalhistorySet;
    }

    public void setMedicalhistorySet(Set<MedicalhistoryEntity> medicalhistorySet) {
        this.medicalhistorySet = medicalhistorySet;
    }

    public Set<ContentDetailsEntity> getContentDetailsSet() {
        return contentDetailsSet;
    }

    public void setContentDetailsSet(Set<ContentDetailsEntity> contentDetailsSet) {
        this.contentDetailsSet = contentDetailsSet;
    }

//    public Set<ProfissionaldataEntity> getProfissionaldataSet() {
//        return profissionaldataSet;
//    }
//
//    public void setProfissionaldataSet(Set<ProfissionaldataEntity> profissionaldataSet) {
//        this.profissionaldataSet = profissionaldataSet;
//    }

    public Set<UserFavoritiesEntity> getUserFavoritiesSet() {
        return userFavoritiesSet;
    }

    public void setUserFavoritiesSet(Set<UserFavoritiesEntity> userFavoritiesSet) {
        this.userFavoritiesSet = userFavoritiesSet;
    }

    public Set<UserRateSpecEntity> getUserRateSpecSet() {
        return userRateSpecSet;
    }

    public void setUserRateSpecSet(Set<UserRateSpecEntity> userRateSpecSet) {
        this.userRateSpecSet = userRateSpecSet;
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

    public Set<UsersGroupsEntity> getUsersGroupsSet() {
        return usersGroupsSet;
    }

    public void setUsersGroupsSet(Set<UsersGroupsEntity> usersGroupsSet) {
        this.usersGroupsSet = usersGroupsSet;
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
        if (!(object instanceof UsersEntity)) {
            return false;
        }
        UsersEntity other = (UsersEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Users[ id=" + id + " ]";
    }

	public String getProfile_img() {
		return profile_img;
	}

	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}

	public Set<BranchEntity> getBranchSet() {
		return branchSet;
	}

	public void setBranchSet(Set<BranchEntity> branchSet) {
		this.branchSet = branchSet;
	}

	public Set<UnitEntity> getUnitSet() {
		return unitSet;
	}

	public void setUnitSet(Set<UnitEntity> unitSet) {
		this.unitSet = unitSet;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMarital_stat() {
		return marital_stat;
	}

	public void setMarital_stat(String marital_stat) {
		this.marital_stat = marital_stat;
	}

	public String getEmergency_contact() {
		return emergency_contact;
	}

	public void setEmergency_contact(String emergency_contact) {
		this.emergency_contact = emergency_contact;
	}


	public DistricEntity getDistric() {
		return distric;
	}

	public void setDistric(DistricEntity distric) {
		this.distric = distric;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	



	public String getBlood_type() {
		return blood_type;
	}

	public void setBlood_type(String blood_type) {
		this.blood_type = blood_type;
	}

	public Integer getEmailCount() {
		return emailCount;
	}

	public void setEmailCount(Integer emailCount) {
		this.emailCount = emailCount;
	}

	public Integer getActive_email() {
		return active_email;
	}

	public void setActive_email(Integer active_email) {
		this.active_email = active_email;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}





	public Date getPass_creationDate() {
		return pass_creationDate;
	}

	public void setPass_creationDate(Date pass_creationDate) {
		this.pass_creationDate = pass_creationDate;
	}

	public Integer getPass_emailCount() {
		return pass_emailCount;
	}

	public void setPass_emailCount(Integer pass_emailCount) {
		this.pass_emailCount = pass_emailCount;
	}

	public Set<CertificationEntity> getCertificationSet() {
		return certificationSet;
	}

	public void setCertificationSet(Set<CertificationEntity> certificationSet) {
		this.certificationSet = certificationSet;
	}

	public Set<ProfessionalExpEntity> getProfessionalSet() {
		return professionalSet;
	}

	public void setProfessionalSet(Set<ProfessionalExpEntity> professionalSet) {
		this.professionalSet = professionalSet;
	}

	public String getForget_password_code()
	{
		return forget_password_code;
	}

	public void setForget_password_code(String forget_password_code)
	{
		this.forget_password_code = forget_password_code;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}

	public Integer getPlatform()
	{
		return platform;
	}

	public void setPlatform(Integer platform)
	{
		this.platform = platform;
	}

		


	public Integer getLogin()
	{
		return login;
	}

	public void setLogin(Integer login)
	{
		this.login = login;
	}

	public int getUser_type()
	{
		return user_type;
	}

	public void setUser_type(int user_type)
	{
		this.user_type = user_type;
	}

	
	
	
	
	
    
}
