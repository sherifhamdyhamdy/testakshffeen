package com.sget.akshef.hibernate.beans;


/**
 * Author JDeeb
 */
public class DaysBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name_ar;
	private String name_en;
	//private Set scheduleHasDayses = new HashSet(0);
	
	public DaysBean() {
	}

	public DaysBean(String name_ar , String name_en) {
		this.name_ar = name_ar;
		this.name_en = name_en;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	
}
