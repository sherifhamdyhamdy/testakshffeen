package com.sget.akshef.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 * Author JDeeb
 */
public class CityBean  implements java.io.Serializable {


     private int id;
     private CountryBean country;
     private String nameAr;
     private String nameEn;
     private int active;
     private Set districs = new HashSet(0);

    public CityBean() {
    }

	
    public CityBean(CountryBean country) {
        this.country = country;
    }
    public CityBean(CountryBean country, String nameAr, String nameEn, int active, Set districs) {
       this.country = country;
       this.nameAr = nameAr;
       this.nameEn = nameEn;
       this.active = active;
       this.districs = districs;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public CountryBean getCountry() {
        return this.country;
    }
    
    public void setCountry(CountryBean country) {
        this.country = country;
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
    public Set getDistrics() {
        return this.districs;
    }
    
    public void setDistrics(Set districs) {
        this.districs = districs;
    }




}


