package com.sget.akshef.hibernate.beans;


/**
 * Author JDeeb
 */
public class InsuranceCompanyBean implements java.io.Serializable {
	
	private static final long serialVersionUID = -7574977317061426788L;
	
	private int id;
	private String nameAr;
	private String nameEn;
	private String address;
	private int active;

	public InsuranceCompanyBean() {}

	public InsuranceCompanyBean(String nameAr, String nameEn, String address,int active) {
		this.nameAr = nameAr;
		this.nameEn = nameEn;
		this.address = address;
		this.active = active;
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
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getActive() {
		return this.active;
	}
	public void setActive(int active) {
		this.active = active;
	}
}
