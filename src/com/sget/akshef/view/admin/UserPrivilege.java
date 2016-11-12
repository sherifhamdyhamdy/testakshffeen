package com.sget.akshef.view.admin;

import java.io.Serializable;

/**
 * 
 * @author JDeeb
 * User Privilege For Admin Page and User Management (ManagedBean Name : userPriv) 
 */
public class UserPrivilege implements Serializable{

	private static final long serialVersionUID = -5550335819905531488L;
	
	/* 
	 * Admin Page
	 */
	// Category
	private boolean categoryShow = false;
	private boolean categoryAdd = false;
	private boolean categoryUpdate = false;
	private boolean categoryDelete = false;
	
	// SubCategory
	private boolean subCategoryShow = false;
	private boolean subCategoryAdd = false;
	private boolean subCategoryUpdate = false;
	private boolean subCategoryDelete = false;
	
	// Section
	private boolean sectionShow = false;
	private boolean sectionAdd = false;
	private boolean sectionUpdate = false;
	private boolean sectionDelete = false;
	
	// Countries
	private boolean countryShow = false;
	private boolean countryAdd = false;
	private boolean countryUpdate = false;
	private boolean countryDelete = false;

	// City
	private boolean cityShow = false;
	private boolean cityAdd = false;
	private boolean cityUpdate = false;
	private boolean cityDelete = false;
	
	// District
	private boolean districtShow = false;
	private boolean districtAdd = false;
	private boolean districtUpdate = false;
	private boolean districtDelete = false;
	
	// Content Types
	private boolean contentTypeShow = false;
	private boolean contentTypeAdd = false;
	private boolean contentTypeUpdate = false;
	private boolean contentTypeDelete = false;
	
	// Contents
	private boolean contentShow = false;
	private boolean contentAdd = false;
	private boolean contentUpdate = false;
	private boolean contentDelete = false;
	
	// Branch
	private boolean branchShow = false;
	private boolean branchAdd = false;
	private boolean branchUpdate = false;
	private boolean branchDelete = false;
	
	// Unit
	private boolean unitShow = false;
	private boolean unitAdd = false;
	private boolean unitUpdate = false;
	private boolean unitDelete = false;
	
	// Setters & Getters
	public boolean isCategoryShow() {
		return categoryShow;
	}
	public void setCategoryShow(boolean categoryShow) {
		this.categoryShow = categoryShow;
	}
	public boolean isCategoryAdd() {
		return categoryAdd;
	}
	public void setCategoryAdd(boolean categoryAdd) {
		this.categoryAdd = categoryAdd;
	}
	public boolean isCategoryUpdate() {
		return categoryUpdate;
	}
	public void setCategoryUpdate(boolean categoryUpdate) {
		this.categoryUpdate = categoryUpdate;
	}
	public boolean isCategoryDelete() {
		return categoryDelete;
	}
	public void setCategoryDelete(boolean categoryDelete) {
		this.categoryDelete = categoryDelete;
	}
	public boolean isSubCategoryShow() {
		return subCategoryShow;
	}
	public void setSubCategoryShow(boolean subCategoryShow) {
		this.subCategoryShow = subCategoryShow;
	}
	public boolean isSubCategoryAdd() {
		return subCategoryAdd;
	}
	public void setSubCategoryAdd(boolean subCategoryAdd) {
		this.subCategoryAdd = subCategoryAdd;
	}
	public boolean isSubCategoryUpdate() {
		return subCategoryUpdate;
	}
	public void setSubCategoryUpdate(boolean subCategoryUpdate) {
		this.subCategoryUpdate = subCategoryUpdate;
	}
	public boolean isSubCategoryDelete() {
		return subCategoryDelete;
	}
	public void setSubCategoryDelete(boolean subCategoryDelete) {
		this.subCategoryDelete = subCategoryDelete;
	}
	public boolean isSectionShow() {
		return sectionShow;
	}
	public void setSectionShow(boolean sectionShow) {
		this.sectionShow = sectionShow;
	}
	public boolean isSectionAdd() {
		return sectionAdd;
	}
	public void setSectionAdd(boolean sectionAdd) {
		this.sectionAdd = sectionAdd;
	}
	public boolean isSectionUpdate() {
		return sectionUpdate;
	}
	public void setSectionUpdate(boolean sectionUpdate) {
		this.sectionUpdate = sectionUpdate;
	}
	public boolean isSectionDelete() {
		return sectionDelete;
	}
	public void setSectionDelete(boolean sectionDelete) {
		this.sectionDelete = sectionDelete;
	}
	public boolean isCountryShow() {
		return countryShow;
	}
	public void setCountryShow(boolean countryShow) {
		this.countryShow = countryShow;
	}
	public boolean isCountryAdd() {
		return countryAdd;
	}
	public void setCountryAdd(boolean countryAdd) {
		this.countryAdd = countryAdd;
	}
	public boolean isCountryUpdate() {
		return countryUpdate;
	}
	public void setCountryUpdate(boolean countryUpdate) {
		this.countryUpdate = countryUpdate;
	}
	public boolean isCountryDelete() {
		return countryDelete;
	}
	public void setCountryDelete(boolean countryDelete) {
		this.countryDelete = countryDelete;
	}
	public boolean isCityShow() {
		return cityShow;
	}
	public void setCityShow(boolean cityShow) {
		this.cityShow = cityShow;
	}
	public boolean isCityAdd() {
		return cityAdd;
	}
	public void setCityAdd(boolean cityAdd) {
		this.cityAdd = cityAdd;
	}
	public boolean isCityUpdate() {
		return cityUpdate;
	}
	public void setCityUpdate(boolean cityUpdate) {
		this.cityUpdate = cityUpdate;
	}
	public boolean isCityDelete() {
		return cityDelete;
	}
	public void setCityDelete(boolean cityDelete) {
		this.cityDelete = cityDelete;
	}
	public boolean isDistrictShow() {
		return districtShow;
	}
	public void setDistrictShow(boolean districtShow) {
		this.districtShow = districtShow;
	}
	public boolean isDistrictAdd() {
		return districtAdd;
	}
	public void setDistrictAdd(boolean districtAdd) {
		this.districtAdd = districtAdd;
	}
	public boolean isDistrictUpdate() {
		return districtUpdate;
	}
	public void setDistrictUpdate(boolean districtUpdate) {
		this.districtUpdate = districtUpdate;
	}
	public boolean isDistrictDelete() {
		return districtDelete;
	}
	public void setDistrictDelete(boolean districtDelete) {
		this.districtDelete = districtDelete;
	}
	public boolean isContentTypeShow() {
		return contentTypeShow;
	}
	public void setContentTypeShow(boolean contentTypeShow) {
		this.contentTypeShow = contentTypeShow;
	}
	public boolean isContentTypeAdd() {
		return contentTypeAdd;
	}
	public void setContentTypeAdd(boolean contentTypeAdd) {
		this.contentTypeAdd = contentTypeAdd;
	}
	public boolean isContentTypeUpdate() {
		return contentTypeUpdate;
	}
	public void setContentTypeUpdate(boolean contentTypeUpdate) {
		this.contentTypeUpdate = contentTypeUpdate;
	}
	public boolean isContentTypeDelete() {
		return contentTypeDelete;
	}
	public void setContentTypeDelete(boolean contentTypeDelete) {
		this.contentTypeDelete = contentTypeDelete;
	}
	public boolean isContentShow() {
		return contentShow;
	}
	public void setContentShow(boolean contentShow) {
		this.contentShow = contentShow;
	}
	public boolean isContentAdd() {
		return contentAdd;
	}
	public void setContentAdd(boolean contentAdd) {
		this.contentAdd = contentAdd;
	}
	public boolean isContentUpdate() {
		return contentUpdate;
	}
	public void setContentUpdate(boolean contentUpdate) {
		this.contentUpdate = contentUpdate;
	}
	public boolean isContentDelete() {
		return contentDelete;
	}
	public void setContentDelete(boolean contentDelete) {
		this.contentDelete = contentDelete;
	}
	public boolean isBranchShow() {
		return branchShow;
	}
	public void setBranchShow(boolean branchShow) {
		this.branchShow = branchShow;
	}
	public boolean isBranchAdd() {
		return branchAdd;
	}
	public void setBranchAdd(boolean branchAdd) {
		this.branchAdd = branchAdd;
	}
	public boolean isBranchUpdate() {
		return branchUpdate;
	}
	public void setBranchUpdate(boolean branchUpdate) {
		this.branchUpdate = branchUpdate;
	}
	public boolean isBranchDelete() {
		return branchDelete;
	}
	public void setBranchDelete(boolean branchDelete) {
		this.branchDelete = branchDelete;
	}
	public boolean isUnitShow() {
		return unitShow;
	}
	public void setUnitShow(boolean unitShow) {
		this.unitShow = unitShow;
	}
	public boolean isUnitAdd() {
		return unitAdd;
	}
	public void setUnitAdd(boolean unitAdd) {
		this.unitAdd = unitAdd;
	}
	public boolean isUnitUpdate() {
		return unitUpdate;
	}
	public void setUnitUpdate(boolean unitUpdate) {
		this.unitUpdate = unitUpdate;
	}
	public boolean isUnitDelete() {
		return unitDelete;
	}
	public void setUnitDelete(boolean unitDelete) {
		this.unitDelete = unitDelete;
	}
}