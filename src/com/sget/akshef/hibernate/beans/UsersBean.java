package com.sget.akshef.hibernate.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

/**
 * Author JDeeb
 */
public class UsersBean  implements java.io.Serializable {

	private static final long serialVersionUID = 3555340771992870237L;
	private int id;
	private String nameAr;
	private String nameEn;
	private String nationalId;
	private String username;
	private String password;
//	private String repassword;
	private String email;
	private int gender;
	private String profile_img;
	private String firstName;
	private String lastName;
	private String address;
	private String telephone;
	private String mobile;
	private String marital_stat;
	private String emergency_contact;
	private int district_id;
	private Date  birthdate;
	private String birthdateStr;
	
	private boolean userCompany;
	private String genderSt;
	private String maritalSt;
	private String districtST;
	private String cityST;
	private String countryST;
	private boolean admin;
	private Date creationDate;
	private Date  pass_creationDate;
	private int pass_emailCount;
	private int emailCount;
    private int active_email;
    private String blood_type;
    private List<MedicalhistoryBean> medicalHist;
     private String progress;
     private List<ProfessionalExpBean> profList;
 	private List<CertificationBean> certList;
	private int user_type;							//1 - user ,   2 - doctor

	private int active;
	
    private String token;
    
    private Integer platform;
        
    private Integer login;
	
    private String logo;
    
     /*private Set commentses = new HashSet(0);
     private Set userFavoritieses = new HashSet(0);
     private Set userRateSpecs = new HashSet(0);
     private Set messagesesForFromUser = new HashSet(0);
     private Set usersGroupses = new HashSet(0);
     private Set userRateBranches = new HashSet(0);
     private Set messagesesForToUser = new HashSet(0);
     private Set contentDetailses = new HashSet(0);
     private Set approvalMsgs = new HashSet(0);
     private Set specialists = new HashSet(0);
     private Set medicalhistories = new HashSet(0);
     private Set profissionaldatas = new HashSet(0);*/

     // For Mobile
     private int userType ;
     
     
    public UsersBean() {
    }

    public UsersBean(String nameAr, String nameEn, String nationalId, String username, String password, String email, int active
    		/*,Set commentses, Set userFavoritieses, Set userRateSpecs, Set messagesesForFromUser, Set usersGroupses, Set userRateBranches, Set messagesesForToUser,
    		 *  Set contentDetailses, Set approvalMsgs, Set specialists, Set medicalhistories, Set profissionaldatas*/) {
       this.nameAr = nameAr;
       this.nameEn = nameEn;
       this.nationalId = nationalId;
       this.username = username;
//       this.password = password;
       this.email = email;
       this.active = active;
       /*this.commentses = commentses;
       this.userFavoritieses = userFavoritieses;
       this.userRateSpecs = userRateSpecs;
       this.messagesesForFromUser = messagesesForFromUser;
       this.usersGroupses = usersGroupses;
       this.userRateBranches = userRateBranches;
       this.messagesesForToUser = messagesesForToUser;
       this.contentDetailses = contentDetailses;
       this.approvalMsgs = approvalMsgs;
       this.specialists = specialists;
       this.medicalhistories = medicalhistories;
       this.profissionaldatas = profissionaldatas;*/
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNameAr() {
        return this.nameAr;
    }
    
    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
    public String getNameEn() {
        return this.nameEn;
    }
    public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public String getNationalId() {
        return this.nationalId;
    }
    
    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public int getActive() {
        return this.active;
    }
    
    public void setActive(int active) {
        this.active = active;
    }
    /*public Set getCommentses() {
        return this.commentses;
    }
    
    public void setCommentses(Set commentses) {
        this.commentses = commentses;
    }
    public Set getUserFavoritieses() {
        return this.userFavoritieses;
    }
    
    public void setUserFavoritieses(Set userFavoritieses) {
        this.userFavoritieses = userFavoritieses;
    }
    public Set getUserRateSpecs() {
        return this.userRateSpecs;
    }
    
    public void setUserRateSpecs(Set userRateSpecs) {
        this.userRateSpecs = userRateSpecs;
    }
    public Set getMessagesesForFromUser() {
        return this.messagesesForFromUser;
    }
    
    public void setMessagesesForFromUser(Set messagesesForFromUser) {
        this.messagesesForFromUser = messagesesForFromUser;
    }
    public Set getUsersGroupses() {
        return this.usersGroupses;
    }
    
    public void setUsersGroupses(Set usersGroupses) {
        this.usersGroupses = usersGroupses;
    }
    public Set getUserRateBranches() {
        return this.userRateBranches;
    }
    
    public void setUserRateBranches(Set userRateBranches) {
        this.userRateBranches = userRateBranches;
    }
    public Set getMessagesesForToUser() {
        return this.messagesesForToUser;
    }
    
    public void setMessagesesForToUser(Set messagesesForToUser) {
        this.messagesesForToUser = messagesesForToUser;
    }
    public Set getContentDetailses() {
        return this.contentDetailses;
    }
    
    public void setContentDetailses(Set contentDetailses) {
        this.contentDetailses = contentDetailses;
    }
    public Set getApprovalMsgs() {
        return this.approvalMsgs;
    }
    
    public void setApprovalMsgs(Set approvalMsgs) {
        this.approvalMsgs = approvalMsgs;
    }
    public Set getSpecialists() {
        return this.specialists;
    }
    
    public void setSpecialists(Set specialists) {
        this.specialists = specialists;
    }
    public Set getMedicalhistories() {
        return this.medicalhistories;
    }
    
    public void setMedicalhistories(Set medicalhistories) {
        this.medicalhistories = medicalhistories;
    }
    public Set getProfissionaldatas() {
        return this.profissionaldatas;
    }
    
    public void setProfissionaldatas(Set profissionaldatas) {
        this.profissionaldatas = profissionaldatas;
    }*/

	public String getProfile_img() {
		return profile_img;
	}

	public void setProfile_img(String profile_img) {
		this.profile_img = profile_img;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
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

	public int getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	
	public String getBirthdateStr()
	{
		return birthdateStr;
	}

	public void setBirthdateStr(String birthdateStr)
	{
		this.birthdateStr = birthdateStr;
	}

	@Override
    public boolean equals(Object object) {
        if (!(object instanceof UsersBean)) {
            return false;
        }
        UsersBean other = (UsersBean) object;
        if (this.id == other.id) {
            return true;
        }
        return false;
    }


	public boolean isUserCompany() {
		return userCompany;
	}

	public void setUserCompany(boolean userCompany) {
		this.userCompany = userCompany;
	}

	public String getGenderSt() {
		return genderSt;
	}

	public void setGenderSt(String genderSt) {
		this.genderSt = genderSt;
	}

	public String getMaritalSt() {
		return maritalSt;
	}

	public void setMaritalSt(String maritalSt) {
		this.maritalSt = maritalSt;
	}

	public String getDistrictST() {
		return districtST;
	}

	public void setDistrictST(String districtST) {
		this.districtST = districtST;
	}

//	public String getRepassword() {
//		return repassword;
//	}
//
//	public void setRepassword(String repassword) {
//		this.repassword = repassword;
//	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getEmailCount() {
		return emailCount;
	}

	public void setEmailCount(int emailCount) {
		this.emailCount = emailCount;
	}

	public int getActive_email() {
		return active_email;
	}

	public void setActive_email(int active_email) {
		this.active_email = active_email;
	}

	public String getBlood_type() {
		return blood_type;
	}

	public void setBlood_type(String blood_type) {
		this.blood_type = blood_type;
	}

	public List<MedicalhistoryBean> getMedicalHist() {
		return medicalHist;
	}

	public void setMedicalHist(List<MedicalhistoryBean> medicalHist) {
		this.medicalHist = medicalHist;
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

	public int getPass_emailCount() {
		return pass_emailCount;
	}

	public void setPass_emailCount(int pass_emailCount) {
		this.pass_emailCount = pass_emailCount;
	}

	public List<ProfessionalExpBean> getProfList() {
		return profList;
	}

	public void setProfList(List<ProfessionalExpBean> profList) {
		this.profList = profList;
	}

	public List<CertificationBean> getCertList() {
		return certList;
	}

	public void setCertList(List<CertificationBean> certList) {
		this.certList = certList;
	}

	public String getCityST() {
		return cityST;
	}

	public void setCityST(String cityST) {
		this.cityST = cityST;
	}

	public String getCountryST() {
		return countryST;
	}

	public void setCountryST(String countryST) {
		this.countryST = countryST;
	}

	public int getUser_type()
	{
		return user_type;
	}

	public void setUser_type(int user_type)
	{
		this.user_type = user_type;
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

	public String getLogo()
	{
		return logo;
	}

	public void setLogo(String logo)
	{
		this.logo = logo;
	}

	
	
	
	
}