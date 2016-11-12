package com.sget.akshf.searchcriteria;

/**
 * 
 * @author JDeeb
 *
 * Search On DB For Doctors On Selected Section and Branch
 * Result Return List of Doctors
 */

public class DoctorSectionBranchCriteria {
	// Mandatory
	private int section_id ;
	private int branch_id ;
	private String doctorName;
	// Optional
	private boolean orderbyrate ;
	private boolean onlinenow ;
	
	private int insuranceCompany;

	private int start ;
	private int limit ;
	
	public int getSection_id() {
		return section_id;
	}
	public void setSection_id(int section_id) {
		this.section_id = section_id;
	}
	public int getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}
	public boolean isOrderbyrate() {
		return orderbyrate;
	}
	public void setOrderbyrate(boolean orderbyrate) {
		this.orderbyrate = orderbyrate;
	}
	public boolean isOnlinenow() {
		return onlinenow;
	}
	public void setOnlinenow(boolean onlinenow) {
		this.onlinenow = onlinenow;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
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
	public int getInsuranceCompany()
	{
		return insuranceCompany;
	}
	public void setInsuranceCompany(int insuranceCompany)
	{
		this.insuranceCompany = insuranceCompany;
	}
	
	
}
