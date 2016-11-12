package com.sget.akshef.hibernate.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author JDeeb
 */
public class BranchTempBean implements java.io.Serializable {

	private int id;
	private DistricBean distric;
	private UnitBean unit;
	private String nameAr;
	private String nameEn;
	private Double lat;
	private Double lng;
	private String address;
	private Date establishdate;
	private String mobile;
	private String region;
	private String tel;
	private String website;
	private int active;
	private String biography_ar ;
	private String biography_en ;
	private int branch_rating ;
	private String image;
	// For Mobile
	private double distance ;
	private int category_id;
	private int rating;
	private int ratingno ;
    private List<SubcategoryBean> subcategories ;
    private  boolean  isChanaged;
    private Set sections;
	private UsersBean userbean;

	
	
	public BranchTempBean() {
	}

	public BranchTempBean(DistricBean distric, UnitBean unit) {
		this.distric = distric;
		this.unit = unit;
	}

	public BranchTempBean(DistricBean distric, UnitBean unit, String nameAr,
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
		this.mobile = mobile;
		this.rating = rating;
		this.region = region;
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

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
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
}
