package com.sget.akshef.hibernate.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author JDeeb
 */
public class DistricBean implements java.io.Serializable {

	private int id;
	private CityBean city;
	private String nameAr;
	private String nameEn;
	private int active;
	private List<BranchBean> branches = new ArrayList<BranchBean>();

	public DistricBean() {
	}

	public DistricBean(CityBean city) {
		this.city = city;
	}

	public DistricBean(CityBean city, String nameAr, String nameEn, int active) {
		this.city = city;
		this.nameAr = nameAr;
		this.nameEn = nameEn;
		this.active = active;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CityBean getCity() {
		return this.city;
	}

	public void setCity(CityBean city) {
		this.city = city;
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

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<BranchBean> getBranches() {
		return branches;
	}

	public void setBranches(List<BranchBean> branches) {
		this.branches = branches;
	}

}
