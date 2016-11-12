package com.sget.akshf.searchcriteria;

/**
 * 
 * @author JDeeb
 * Branches Search Criteria
 */

public class BranchesCriteria {
	// Mandatory
	private double longitude ;
	private double latitude ;
	private int start ;
	private int limit ;
	// Optional
	private String search_text ;
	private int section_id ;
	private int cat_id ;
	private boolean rating ;
	//New 
	private int insuranceCompany ;
	
	// Setters & Getters
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
	public String getSearch_text() {
		return search_text;
	}
	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}
	public int getSection_id() {
		return section_id;
	}
	public void setSection_id(int section_id) {
		this.section_id = section_id;
	}
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
	public boolean isRating() {
		return rating;
	}
	public void setRating(boolean rating) {
		this.rating = rating;
	}
	public int getInsuranceCompany() {
		return insuranceCompany;
	}
	public void setInsuranceCompany(int insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}
}
