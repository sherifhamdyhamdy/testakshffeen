package com.sget.akshef.hibernate.beans;


/**
 * Author JDeeb
 */
public class HourBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String hour;

	//private Set scheduleHasDayses = new HashSet(0);
	
	public HourBean() {
	}

	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getHour() {
		return hour;
	}



	public void setHour(String hour) {
		this.hour = hour;
	}


	
}
