package com.sget.akshef.hibernate.beans;

import java.util.List;

import com.sget.akshef.hibernate.entities.CategoryHasSubcategoryEntity;
import com.sget.akshef.hibernate.entities.ClinicsEntity;
import com.sget.akshef.hibernate.entities.HospitalDataEntity;
import com.sget.akshef.hibernate.entities.InsteadRemedyEntity;
import com.sget.akshef.hibernate.entities.MedicalInstrumentsEntity;
import com.sget.akshef.hibernate.entities.MedicalTourismEntity;
import com.sget.akshef.hibernate.entities.PharmcyEntity;
import com.sget.akshef.hibernate.entities.RadiolgyCenterEntity;
import com.sget.akshef.hibernate.entities.UnitEntity;

/**
 * Author JDeeb
 */
public class CategoryBean implements java.io.Serializable {

	private int id;
	private String nameAr;
	private String nameEn;
	private int active;

	private List<MedicalTourismEntity> medicalTourisms;
	private List<ClinicsEntity> clinicses;
	private List<MedicalInstrumentsEntity> medicalInstrumentses;
	private List<CategoryHasSubcategoryEntity> categoryHasSubcategories;
	private List<PharmcyEntity> pharmcies;
	private List<RadiolgyCenterEntity> radiolgyCenters;
	private List<HospitalDataEntity> hospitalDatas;
	private List<UnitEntity> units;
	private List<InsteadRemedyEntity> insteadRemedies;
	private List<SubcategoryBean> subCategories ;
	
	public CategoryBean() {
	}

	public CategoryBean(String nameAr, String nameEn, int active,
			List<HospitalDataEntity> hospitalDatas,
			List<MedicalInstrumentsEntity> medicalInstrumentses,
			List<UnitEntity> units, List<InsteadRemedyEntity> insteadRemedies,
			List<PharmcyEntity> pharmcies,
			List<RadiolgyCenterEntity> radiolgyCenters,
			List<ClinicsEntity> clinicses,
			List<CategoryHasSubcategoryEntity> categoryHasSubcategories,
			List<MedicalTourismEntity> medicalTourisms) {
		this.nameAr = nameAr;
		this.nameEn = nameEn;
		this.active = active;
		this.hospitalDatas = hospitalDatas;
		this.medicalInstrumentses = medicalInstrumentses;
		this.units = units;
		this.insteadRemedies = insteadRemedies;
		this.pharmcies = pharmcies;
		this.radiolgyCenters = radiolgyCenters;
		this.clinicses = clinicses;
		this.categoryHasSubcategories = categoryHasSubcategories;
		this.medicalTourisms = medicalTourisms;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameAr() {
		return this.nameAr == null ? "" : this.nameAr ;
	}

	public void setNameAr(String nameAr) {
		this.nameAr = nameAr;
	}

	public String getNameEn() {
		return this.nameEn == null ? "" : this.nameEn;
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

	public List<MedicalTourismEntity> getMedicalTourisms() {
		return medicalTourisms;
	}

	public void setMedicalTourisms(List<MedicalTourismEntity> medicalTourisms) {
		this.medicalTourisms = medicalTourisms;
	}

	public List<ClinicsEntity> getClinicses() {
		return clinicses;
	}

	public void setClinicses(List<ClinicsEntity> clinicses) {
		this.clinicses = clinicses;
	}

	public List<MedicalInstrumentsEntity> getMedicalInstrumentses() {
		return medicalInstrumentses;
	}

	public void setMedicalInstrumentses(
			List<MedicalInstrumentsEntity> medicalInstrumentses) {
		this.medicalInstrumentses = medicalInstrumentses;
	}

	public List<CategoryHasSubcategoryEntity> getCategoryHasSubcategories() {
		return categoryHasSubcategories;
	}

	public void setCategoryHasSubcategories(
			List<CategoryHasSubcategoryEntity> categoryHasSubcategories) {
		this.categoryHasSubcategories = categoryHasSubcategories;
	}

	public List<PharmcyEntity> getPharmcies() {
		return pharmcies;
	}

	public void setPharmcies(List<PharmcyEntity> pharmcies) {
		this.pharmcies = pharmcies;
	}

	public List<RadiolgyCenterEntity> getRadiolgyCenters() {
		return radiolgyCenters;
	}

	public void setRadiolgyCenters(List<RadiolgyCenterEntity> radiolgyCenters) {
		this.radiolgyCenters = radiolgyCenters;
	}

	public List<HospitalDataEntity> getHospitalDatas() {
		return hospitalDatas;
	}

	public void setHospitalDatas(List<HospitalDataEntity> hospitalDatas) {
		this.hospitalDatas = hospitalDatas;
	}

	public List<UnitEntity> getUnits() {
		return units;
	}

	public void setUnits(List<UnitEntity> units) {
		this.units = units;
	}

	public List<InsteadRemedyEntity> getInsteadRemedies() {
		return insteadRemedies;
	}

	public void setInsteadRemedies(List<InsteadRemedyEntity> insteadRemedies) {
		this.insteadRemedies = insteadRemedies;
	}

	public List<SubcategoryBean> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<SubcategoryBean> subCategories) {
		this.subCategories = subCategories;
	}
}
