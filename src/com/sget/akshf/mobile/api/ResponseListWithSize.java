package com.sget.akshf.mobile.api;

import java.util.List;

/**
 * 
 * @author JDeeb
 *
 */
public class ResponseListWithSize<T>{

	private int noOfComments ;
	private List<T> comments ;
	public int getNoOfComments() {
		return noOfComments;
	}
	public void setNoOfComments(int noOfComments) {
		this.noOfComments = noOfComments;
	}
	public List<T> getComments() {
		return comments;
	}
	public void setComments(List<T> comments) {
		this.comments = comments;
	}
}
