package com.sget.akshf.searchcriteria;

/**
 * 
 * @author JDeeb
 * Get Doctor Appointment
 */
public class DoctorAppointmentCriteria {

	// Mandatory
	private int branch_id ;
	private int doctor_id ;
	private int day_id ;
	
	public int getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public int getDay_id() {
		return day_id;
	}
	public void setDay_id(int day_id) {
		this.day_id = day_id;
	}
}
