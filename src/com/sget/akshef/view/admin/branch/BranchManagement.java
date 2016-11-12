package com.sget.akshef.view.admin.branch;

import java.io.Serializable;

/**
 * 
 * @author JDeeb
 * Branch Management Bean 
 * Main Controller for Manage Branch Data
 * branchManag	Controller Name
 * 
 */

public class BranchManagement implements Serializable{
	
	private static final long serialVersionUID = 1953402602347157256L;
	
	// Attributes
	private String page ;

	// Functions
	public BranchManagement(){
		page = "page1";		
	}
	
	// Setters and Getters
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
