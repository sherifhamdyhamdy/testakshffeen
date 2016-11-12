package com.sget.akshef.hibernate.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Author JDeeb
 */
public class UnitBean  implements java.io.Serializable {

	
	private static final long serialVersionUID = -1527935778314505722L;
	private int id;
	private CategoryBean category;
	private UsersBean user;
	private String nameAr;
	private String nameEn;
	private String biographyAr;
	private String biographyEn;
	private int active;
	private String unitlogo;
	private String website;
	private List<BranchBean> branches = new ArrayList<BranchBean>();
	private List<UnitGroupsBean> unitGroupses = new ArrayList<UnitGroupsBean>();
	private List<UnitHasInsuranceCompanyBean> unitHasInsuranceCompanies = new ArrayList<UnitHasInsuranceCompanyBean>();
	
	private List<CategoryBean> categoryList = new ArrayList<CategoryBean>();

    public UnitBean() {}
    
    public UnitBean(CategoryBean category) {
        this.category = category;
    }
    public UnitBean(CategoryBean category, String nameAr, String nameEn, String biographyAr, String biographyEn, int active) {
       this.category = category;
       this.nameAr = nameAr;
       this.nameEn = nameEn;
       this.biographyAr = biographyAr;
       this.biographyEn = biographyEn;
       this.active = active;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public CategoryBean getCategory() {
    	if(category == null)
    		category = new CategoryBean();
        return this.category;
    }
    public void setCategory(CategoryBean category) {
        this.category = category;
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
    public String getBiographyAr() {
        return this.biographyAr;
    }
    public void setBiographyAr(String biographyAr) {
        this.biographyAr = biographyAr;
    }
    public String getBiographyEn() {
        return this.biographyEn;
    }
    public void setBiographyEn(String biographyEn) {
        this.biographyEn = biographyEn;
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
	public List<UnitGroupsBean> getUnitGroupses() {
		return unitGroupses;
	}
	public void setUnitGroupses(List<UnitGroupsBean> unitGroupses) {
		this.unitGroupses = unitGroupses;
	}
	public List<UnitHasInsuranceCompanyBean> getUnitHasInsuranceCompanies() {
		return unitHasInsuranceCompanies;
	}
	public void setUnitHasInsuranceCompanies(
			List<UnitHasInsuranceCompanyBean> unitHasInsuranceCompanies) {
		this.unitHasInsuranceCompanies = unitHasInsuranceCompanies;
	}
	public UsersBean getUser() {
		return user;
	}
	public void setUser(UsersBean user) {
		this.user = user;
	}
	public String getUnitlogo() {
		return unitlogo;
	}
	public void setUnitlogo(String unitlogo) {
		this.unitlogo = unitlogo;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}

	
	public List<CategoryBean> getCategoryList()
	{
		return categoryList;
	}

	public void setCategoryList(List<CategoryBean> categoryList)
	{
		this.categoryList = categoryList;
	}
}
