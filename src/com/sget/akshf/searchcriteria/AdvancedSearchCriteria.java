package com.sget.akshf.searchcriteria;


/**
 * 
 * @author JDeeb
 * Advanced search bean used in Mobile Service Search
 * 
 */
public class AdvancedSearchCriteria {

	private double longitude ;
	private double latitude ;
	private int cat_id ;
	private int subcat_id ;
	private int section_id ;
	private int country_id ;
	private int city_id ;
	private int district_id ;
	private int fromday_id ;
	private int today_id ;
	private int fromhour_id ;
	private int tohour_id ;
	private String name ;
	private int degree_id ;
	private int gender ;
	private int start ;
	private int limit ;
	private String keyword;
	private String mode;
	private int insuranceCompany_id;
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
	public int getSubcat_id() {
		return subcat_id;
	}
	public void setSubcat_id(int subcat_id) {
		this.subcat_id = subcat_id;
	}
	public int getSection_id() {
		return section_id;
	}
	public void setSection_id(int section_id) {
		this.section_id = section_id;
	}
	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}
	public int getCity_id() {
		return city_id;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public int getDistrict_id() {
		return district_id;
	}
	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}
	public int getFromday_id() {
		return fromday_id;
	}
	public void setFromday_id(int fromday_id) {
		this.fromday_id = fromday_id;
	}
	public int getToday_id() {
		return today_id;
	}
	public void setToday_id(int today_id) {
		this.today_id = today_id;
	}
	public int getFromhour_id() {
		return fromhour_id;
	}
	public void setFromhour_id(int fromhour_id) {
		this.fromhour_id = fromhour_id;
	}
	public int getTohour_id() {
		return tohour_id;
	}
	public void setTohour_id(int tohour_id) {
		this.tohour_id = tohour_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDegree_id() {
		return degree_id;
	}
	public void setDegree_id(int degree_id) {
		this.degree_id = degree_id;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getInsuranceCompany_id()
	{
		return insuranceCompany_id;
	}
	public void setInsuranceCompany_id(int insuranceCompany_id)
	{
		this.insuranceCompany_id = insuranceCompany_id;
	}
	
}
