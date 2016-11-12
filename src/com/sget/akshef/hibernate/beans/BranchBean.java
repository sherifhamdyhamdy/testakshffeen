package com.sget.akshef.hibernate.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;

import com.sget.akshef.beans.SchedulesBean;
import com.sget.akshef.hibernate.entities.Branch_scheduleEntity;
import com.sget.akshef.hibernate.entities.SubcategoryEntity;

/**
 * Author JDeeb
 */
public class BranchBean implements java.io.Serializable {

	private static final long serialVersionUID = -6312094910317657477L;
	
	private int id;
	private DistricBean distric;
	private UnitBean unit;
	private String nameAr;
	private String nameEn;
	private Double lat;
	private Double lng;
	private String address;
	private Date establishdate;
	private String tel;
	private String website;
	private int active;
	private String biography_ar ;
	private String biography_en ;
	private int branch_rating ;
	private String image;
	private String districtName;
	private int sponsored;
	private int has_delivery;
	
	public String getDistrictName() {
		return districtName;
	}
    
    @Transient
    private SchedulesBean branch_schedules = new SchedulesBean();
    
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	private String specId;
	private String specName;
	//New
	private String email ;
	private String fax ;
	private String hotline ;
	private String postlcode ;
	
	// For Mobile
	private double distance ;
	private int category_id;
	private int rating;
	private int ratingno ;
    private List<SubcategoryBean> subcategories ;
    private  boolean  isChanaged;
    private Set sections;
	private UsersBean userbean;
	private String dateFormatEstDate;
	private List<SubcategoryEntity> subCategoryList = new ArrayList<SubcategoryEntity>();
	
	
//	private String category_nameAr;
//	private String category_nameEn;

//    private List<String> subcategories = new ArrayList<String>();

	
	public BranchBean() {
	}

	public BranchBean(DistricBean distric, UnitBean unit) {
		this.distric = distric;
		this.unit = unit;
	}

	public BranchBean(DistricBean distric, UnitBean unit, String nameAr,
			String nameEn, Double lat, Double lng, String address,
			Date establishdate, String mobile, int rating, String region,
			String tel, String website, int active) {
		this.distric = distric;
		this.unit = unit;
		this.nameAr = nameAr;
		this.nameEn = nameEn;
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.establishdate = establishdate;
		this.rating = rating;
		this.tel = tel;
		this.website = website;
		this.active = active;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DistricBean getDistric() {
		return this.distric;
	}

	public void setDistric(DistricBean distric) {
		this.distric = distric;
	}

	public UnitBean getUnit() {
		return this.unit;
	}

	public void setUnit(UnitBean unit) {
		this.unit = unit;
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

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Double getLat() {
		return this.lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return this.lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getEstablishdate() {
		return this.establishdate;
	}

	public void setEstablishdate(Date establishdate) {
		this.establishdate = establishdate;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
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

	public int getRatingno() {
		return ratingno;
	}

	public void setRatingno(int ratingno) {
		this.ratingno = ratingno;
	}

	public int getBranch_rating() {
		return branch_rating;
	}

	public void setBranch_rating(int branch_rating) {
		this.branch_rating = branch_rating;
	}

	public List<SubcategoryBean> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<SubcategoryBean> subcategories) {
		this.subcategories = subcategories;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isChanaged() {
		return isChanaged;
	}

	public void setChanaged(boolean isChanaged) {
		this.isChanaged = isChanaged;
	}

	public Set getSections() {
		return sections;
	}

	public void setSections(Set sections) {
		this.sections = sections;
	}

	public UsersBean getUserbean() {
		return userbean;
	}

	public void setUserbean(UsersBean userbean) {
		this.userbean = userbean;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPostlcode() {
		return postlcode;
	}

	public void setPostlcode(String postlcode) {
		this.postlcode = postlcode;
	}

	public String getDateFormatEstDate() {
		return dateFormatEstDate;
	}

	public void setDateFormatEstDate(String dateFormatEstDate) {
		this.dateFormatEstDate = dateFormatEstDate;
	}

//	public String getSubcategory()
//	{
//		return subcategory;
//	}
//
//	public void setSubcategory(String subcategory)
//	{
//		this.subcategory = subcategory;
//	}


	public List<SubcategoryEntity> getSubCategoryList()
	{
		return subCategoryList;
	}

	public void setSubCategoryList(List<SubcategoryEntity> subCategoryList)
	{
		this.subCategoryList = subCategoryList;
	}

	public int getSponsored()
	{
		return sponsored;
	}

	public void setSponsored(int sponsored)
	{
		this.sponsored = sponsored;
	}

	public int getHas_delivery()
	{
		return has_delivery;
	}

	public void setHas_delivery(int has_delivery)
	{
		this.has_delivery = has_delivery;
	}

	public SchedulesBean getBranch_schedules()
	{
		return branch_schedules;
	}

	public void setBranch_schedules(SchedulesBean branch_schedules)
	{
		this.branch_schedules = branch_schedules;
	}



	
	
//	public String getCategory_nameAr()
//	{
//		return category_nameAr;
//	}
//
//	public void setCategory_nameAr(String category_nameAr)
//	{
//		this.category_nameAr = category_nameAr;
//	}
//
//	public String getCategory_nameEn()
//	{
//		return category_nameEn;
//	}
//
//	public void setCategory_nameEn(String category_nameEn)
//	{
//		this.category_nameEn = category_nameEn;
//	}

	
}
