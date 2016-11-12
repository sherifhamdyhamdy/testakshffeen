package com.sget.akshef.hibernate.beans;

import java.util.List;

import com.sget.akshef.hibernate.entities.SectionsHasSpecialistEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsEntity;

/**
 * Author JDeeb
 */
public class SectionsBean implements java.io.Serializable {

	private int id;
	private String nameAr;
	private String nameEn;
	private int active;
	private String sectionImage;
	private String image;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private List<SectionsHasSpecialistEntity> sectionsHasSpecialists;
	private List<SubcategoryHasSectionsEntity> subcategoryHasSectionses;

	public SectionsBean() {
	}

	public SectionsBean(String nameAr, String nameEn, int active,
			List<SectionsHasSpecialistEntity> sectionsHasSpecialists,
			List<SubcategoryHasSectionsEntity> subcategoryHasSectionses) {
		this.nameAr = nameAr;
		this.nameEn = nameEn;
		this.active = active;
		this.sectionsHasSpecialists = sectionsHasSpecialists;
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

	
	
	
	public String getSectionImage()
	{
		return sectionImage;
	}

	public void setSectionImage(String sectionImage)
	{
		this.sectionImage = sectionImage;
	}

	public List<SectionsHasSpecialistEntity> getSectionsHasSpecialists() {
		return sectionsHasSpecialists;
	}

	public void setSectionsHasSpecialists(
			List<SectionsHasSpecialistEntity> sectionsHasSpecialists) {
		this.sectionsHasSpecialists = sectionsHasSpecialists;
	}

	public List<SubcategoryHasSectionsEntity> getSubcategoryHasSectionses() {
		return subcategoryHasSectionses;
	}

	public void setSubcategoryHasSectionses(
			List<SubcategoryHasSectionsEntity> subcategoryHasSectionses) {
		this.subcategoryHasSectionses = subcategoryHasSectionses;
	}
}
