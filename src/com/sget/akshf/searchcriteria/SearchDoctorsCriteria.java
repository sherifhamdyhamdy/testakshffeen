package com.sget.akshf.searchcriteria;

import java.io.Serializable;

/**
 * 
 * @author JDeeb
 *
 * Advanced Search For Doctors 
 * 
 */

public class SearchDoctorsCriteria implements Serializable {
	
	private String doctorName;
	private int sectionId ;
	private int degree ;
	private int countryId ;
	private int cityId ;
	private int districtId ;
	private int gender ;
	private boolean orderbyrate ;
	private int start ;
	private int limit ;
	private int insuranceCompany_id;
	
	private double longitude ;
	private double latitude ;
	
	// Setters & Getters
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public int getSectionId() {
		return sectionId;
	}
	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public boolean isOrderbyrate() {
		return orderbyrate;
	}
	public void setOrderbyrate(boolean orderbyrate) {
		this.orderbyrate = orderbyrate;
	}
	public int getDistrictId() {
		return districtId;
	}
	public void setDistrictId(int districtId) {
		this.districtId = districtId;
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
	public int getInsuranceCompany_id()
	{
		return insuranceCompany_id;
	}
	public void setInsuranceCompany_id(int insuranceCompany_id)
	{
		this.insuranceCompany_id = insuranceCompany_id;
	}
	public double getLongitude()
	{
		return longitude;
	}
	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}
	public double getLatitude()
	{
		return latitude;
	}
	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}
	
}
