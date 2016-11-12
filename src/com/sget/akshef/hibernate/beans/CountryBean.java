package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class CountryBean  implements java.io.Serializable {

	private static final long serialVersionUID = -1041474793237584863L;
	
	private int id;
	private String nameAr;
	private String nameEn;
	private String code;
	private int active;
	private Set cities = new HashSet(0);

    public CountryBean() {
    }

    public CountryBean(String nameAr, String nameEn, String code, int active, Set cities) {
       this.nameAr = nameAr;
       this.nameEn = nameEn;
       this.code = code;
       this.active = active;
       this.cities = cities;
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
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public int getActive() {
        return this.active;
    }
    
    public void setActive(int active) {
        this.active = active;
    }
    public Set getCities() {
        return this.cities;
    }
    
    public void setCities(Set cities) {
        this.cities = cities;
    }




}


