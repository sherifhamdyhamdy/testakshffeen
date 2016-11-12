package com.sget.akshef.hibernate.beans;


/**
 * Author JDeeb
 */
public class DataBaseVersion implements java.io.Serializable {
	
	private static final long serialVersionUID = 4638231265479904613L;
	
	private Integer id;
	private String dataBaseName;
	private int version;
	
	// Setters & Getters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDataBaseName() {
		return dataBaseName;
	}
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
