package com.sget.akshef.hibernate.beans;


/**
 * 
 * @author JDeeb
 * Migrate Schedule & day in this 
 */
public class ScheduleDays {
	private String from;
	private String to;
	private int day_id ;
	
	private double price ;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getDay_id() {
		return day_id;
	}
	public void setDay_id(int day_id) {
		this.day_id = day_id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
