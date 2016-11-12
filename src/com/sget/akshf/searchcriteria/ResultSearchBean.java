package com.sget.akshf.searchcriteria;

import java.io.Serializable;

public class ResultSearchBean implements Serializable {
	
	int branchId;
	String branchNameEn;
	String branchNameAr;
	String branchAdress;
	String branchTel;
	String branchMob;
	String district;
	int branchrating;
	String category;
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchNameEn() {
		return branchNameEn;
	}
	public void setBranchNameEn(String branchNameEn) {
		this.branchNameEn = branchNameEn;
	}
	public String getBranchNameAr() {
		return branchNameAr;
	}
	public void setBranchNameAr(String branchNameAr) {
		this.branchNameAr = branchNameAr;
	}
	public String getBranchAdress() {
		return branchAdress;
	}
	public void setBranchAdress(String branchAdress) {
		this.branchAdress = branchAdress;
	}
	public String getBranchTel() {
		return branchTel;
	}
	public void setBranchTel(String branchTel) {
		this.branchTel = branchTel;
	}
	public String getBranchMob() {
		return branchMob;
	}
	public void setBranchMob(String branchMob) {
		this.branchMob = branchMob;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public int getBranchrating() {
		return branchrating;
	}
	public void setBranchrating(int branchrating) {
		this.branchrating = branchrating;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	//branch.id,branch.name_ar,branch.name_en,branch.address,branch.tel,branch.distric_id,branch.mobile,branch.mobile,branch.rating	
	
	
	
	
	
	
	
	
	

}
