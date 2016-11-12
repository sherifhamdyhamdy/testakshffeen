package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class ContentCategoryBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private ContentTypesBean contentTypes;
	private String nameAr;
	private String nameEn;
	private int active;
	
	private String cat_image;
	private String cat_image_sc;

	public String getCat_image() {
		return cat_image;
	}

	public void setCat_image(String cat_image) {
		this.cat_image = cat_image;
	}
	
	

	public String getCat_image_sc()
	{
		return cat_image_sc;
	}

	public void setCat_image_sc(String cat_image_sc)
	{
		this.cat_image_sc = cat_image_sc;
	}

	public ContentCategoryBean() {
	}

	public ContentCategoryBean(ContentTypesBean contentTypes) {
		this.contentTypes = contentTypes;
	}

	public ContentCategoryBean(ContentTypesBean contentTypes, String nameAr,
			String nameEn, int active) {
		this.contentTypes = contentTypes;
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

	public ContentTypesBean getContentTypes() {
		return this.contentTypes;
	}

	public void setContentTypes(ContentTypesBean contentTypes) {
		this.contentTypes = contentTypes;
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

}
