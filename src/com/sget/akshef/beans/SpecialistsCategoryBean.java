package com.sget.akshef.beans;

import java.io.Serializable;

public class SpecialistsCategoryBean implements Serializable{
	
	
	 private String name_ar;
	  private String name_en;
	  private int id;
	   
	   
	    

	    public String getName_ar() {
	        return name_ar;
	    }

	    public void setName_ar(String name_ar) {
	        this.name_ar = name_ar;
	    }

	    public String getName_en() {
	        return name_en;
	    }

	    public void setName_en(String name_en) {
	        this.name_en = name_en;
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	

}
