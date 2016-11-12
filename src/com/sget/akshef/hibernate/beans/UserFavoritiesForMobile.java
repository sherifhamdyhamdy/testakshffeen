package com.sget.akshef.hibernate.beans;

import java.util.List;

/**
 * 
 * @author JDeeb
 * Three-List of User Favorites
 */

public class UserFavoritiesForMobile {
	
	private List<BranchBean> branches ;
	private List<SpecialistBean> specialists ;
	private List<ContentDetailsBean> contents ;
	
	public List<BranchBean> getBranches() {
		return branches;
	}
	public void setBranches(List<BranchBean> branches) {
		this.branches = branches;
	}
	public List<SpecialistBean> getSpecialists() {
		return specialists;
	}
	public void setSpecialists(List<SpecialistBean> specialists) {
		this.specialists = specialists;
	}
	public List<ContentDetailsBean> getContents() {
		return contents;
	}
	public void setContents(List<ContentDetailsBean> contents) {
		this.contents = contents;
	}
}
