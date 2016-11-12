package com.sget.akshef.hibernate.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

/**
 * Author JDeeb
 */
public class SpecialistBean implements java.io.Serializable {

	private int id;
	private UsersBean users;
	private String name;
	private int active;
	private int gender ;
	private List<ProfessionalExpBean> profList;
	private List<CertificationBean> certList;
	// For Mobile
	private int rating;
	private int ratingno;
	
    private String biography;   
    private String biography_en;   
    
    private String languages;

    
    private String union_id;     
    private Date gradYear;
    private String buisnessName;
    private String buisnessName_en;
    private String job_no_lincs;    
    private String gradFac; 
    private String degree;
    private Integer degree_id;
    
    private List<CertificationBean> certificationList = new ArrayList<CertificationBean>();
	private List<KeywordBean> keywordList = new ArrayList<KeywordBean>();

//	
//    private Date gradYear;
//    private String buisnessName;
//    private String job_no_lincs;
//    private String union_id;
//    private String gradFac;
    private    List<CommentsBean>     commentsBeans;
   
    public List<CommentsBean> getCommentsBeans() {
		return commentsBeans;
	}

	public void setCommentsBeans(List<CommentsBean> commentsBeans) {
		this.commentsBeans = commentsBeans;
	}

	private List<String> sectionsStringList;
    private List<SectionsBean> sectionBeans;
	public SpecialistBean() {
	}

	public SpecialistBean(UsersBean users) {
		this.users = users;
	}

	public SpecialistBean(UsersBean users, String name, int active) {
		this.users = users;
		this.name = name;
		this.active = active;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UsersBean getUsers() {
		return this.users;
	}

	public void setUsers(UsersBean users) {
		this.users = users;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getRatingno() {
		return ratingno;
	}

	public void setRatingno(int ratingno) {
		this.ratingno = ratingno;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
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

	public Date getGradYear() {
		return gradYear;
	}

	public void setGradYear(Date gradYear) {
		this.gradYear = gradYear;
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



	public List<SectionsBean> getSectionBeans() {
		return sectionBeans;
	}

	public void setSectionBeans(List<SectionsBean> sectionBeans) {
		this.sectionBeans = sectionBeans;
	}

	

	public List<String> getSectionsStringList() {
		return sectionsStringList;
	}

	public void setSectionsStringList(List<String> sectionsStringList) {
		this.sectionsStringList = sectionsStringList;
	}

	public String getUnion_id() {
		return union_id;
	}

	public void setUnion_id(String union_id) {
		this.union_id = union_id;
	}

	public String getDegree()
	{
		return degree;
	}

	public void setDegree(String degree)
	{
		this.degree = degree;
	}

	public Integer getDegree_id()
	{
		return degree_id;
	}

	public void setDegree_id(Integer degree_id)
	{
		this.degree_id = degree_id;
	}

	

	public String getLanguages()
	{
		return languages;
	}

	public void setLanguages(String languages)
	{
		this.languages = languages;
	}

	private int sponsored;

	public int getSponsored()
	{
		return sponsored;
	}

	public void setSponsored(int sponsored)
	{
		this.sponsored = sponsored;
	}

	
	public List<CertificationBean> getCertificationList()
	{
		return certificationList;
	}

	public void setCertificationList(List<CertificationBean> certificationList)
	{
		this.certificationList = certificationList;
	}

	
	public List<KeywordBean> getKeywordList()
	{
		return keywordList;
	}

	public void setKeywordList(List<KeywordBean> keywordList)
	{
		this.keywordList = keywordList;
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

	
	
	
}
