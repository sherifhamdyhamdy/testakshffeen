package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class ContentTypesBean  implements java.io.Serializable {


     private int id;
     private GroupsBean groups;
     private String nameAr;
     private String nameEn;
     private String selected_img;
     private String unselected_img;
     private int active;
     private Set contentCategories = new HashSet(0);

    public ContentTypesBean() {
    }

	
    public ContentTypesBean(GroupsBean groups) {
        this.groups = groups;
    }
    public ContentTypesBean(GroupsBean groups, String nameAr, String nameEn, int active, Set contentCategories) {
       this.groups = groups;
       this.nameAr = nameAr;
       this.nameEn = nameEn;
       this.active = active;
       this.contentCategories = contentCategories;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public GroupsBean getGroups() {
        return this.groups;
    }
    
    public void setGroups(GroupsBean groups) {
        this.groups = groups;
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
    public Set getContentCategories() {
        return this.contentCategories;
    }
    
    public void setContentCategories(Set contentCategories) {
        this.contentCategories = contentCategories;
    }


	public String getUnselected_img() {
		return unselected_img;
	}


	public void setUnselected_img(String unselected_img) {
		this.unselected_img = unselected_img;
	}


	public String getSelected_img() {
		return selected_img;
	}


	public void setSelected_img(String selected_img) {
		this.selected_img = selected_img;
	}




}


