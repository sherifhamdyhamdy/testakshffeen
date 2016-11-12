package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class UserRateBranchBean implements java.io.Serializable {

	private int id;
	private BranchBean branch;
	private UsersBean users;
	private int rate;
	private String rating_Date;

	public UserRateBranchBean() {}

	public UserRateBranchBean(BranchBean branch, UsersBean users) {
		this.branch = branch;
		this.users = users;
	}

	public UserRateBranchBean(BranchBean branch, UsersBean users, int rate , String rating_Date) {
		this.branch = branch;
		this.users = users;
		this.rate = rate;
		this.rating_Date = rating_Date;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BranchBean getBranch() {
		return this.branch;
	}

	public void setBranch(BranchBean branch) {
		this.branch = branch;
	}

	public UsersBean getUsers() {
		return this.users;
	}

	public void setUsers(UsersBean users) {
		this.users = users;
	}

	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getRating_Date() {
		return rating_Date;
	}

	public void setRating_Date(String rating_Date) {
		this.rating_Date = rating_Date;
	}

}
