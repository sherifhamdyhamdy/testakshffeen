package com.sget.akshef.hibernate.beans;


/**
 * Author JDeeb
 */
public class DegreeBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String degree;
	
	//private Set scheduleHasDayses = new HashSet(0);
	
	public DegreeBean() {
	}

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	
}
