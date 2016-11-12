package com.sget.akshef.hibernate.beans;

import java.util.List;

/**
 * Author JDeeb
 */
public class SubcategoryBean implements java.io.Serializable {

	private int id;
	private String nameAr;
	private String nameEn;
	private int active;
	private List<CategoryHasSubcategoryBean> categoryHasSubcategories;
	private List<SubcategoryHasSectionsBean> subcategoryHasSectionses;
	private List<SectionsBean> sections ;

	public SubcategoryBean() {
	}

	public SubcategoryBean(String nameAr, String nameEn, int active,
			List<CategoryHasSubcategoryBean> categoryHasSubcategories,
			List<SubcategoryHasSectionsBean> subcategoryHasSectionses) {
		this.nameAr = nameAr;
		this.nameEn = nameEn;
		this.active = active;
		this.categoryHasSubcategories = categoryHasSubcategories;
		this.subcategoryHasSectionses = subcategoryHasSectionses;
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

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public List<CategoryHasSubcategoryBean> getCategoryHasSubcategories() {
		return categoryHasSubcategories;
	}

	public void setCategoryHasSubcategories(
			List<CategoryHasSubcategoryBean> categoryHasSubcategories) {
		this.categoryHasSubcategories = categoryHasSubcategories;
	}

	public List<SubcategoryHasSectionsBean> getSubcategoryHasSectionses() {
		return subcategoryHasSectionses;
	}

	public void setSubcategoryHasSectionses(
			List<SubcategoryHasSectionsBean> subcategoryHasSectionses) {
		this.subcategoryHasSectionses = subcategoryHasSectionses;
	}

	public List<SectionsBean> getSections() {
		return sections;
	}

	public void setSections(List<SectionsBean> sections) {
		this.sections = sections;
	}
	
	

}
