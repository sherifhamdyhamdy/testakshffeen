package com.sget.akshef.view.admin.branch;

import java.io.Serializable;
import java.util.List;

import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.service.CommentsService;
import com.sget.akshef.view.admin.AdminControl;

/**
 * 
 * @author JDeeb
 *
 */
public class BranchComments extends AdminControl implements Serializable{

	private static final long serialVersionUID = -9104231749061535190L;
	private CommentsService service = null ;
	private List<CommentsBean> comments = null;
	private int branchId = 72 ;
	
	public BranchComments(){
		service = new CommentsService();;
		getBranchComments();
	}
	
	public void getBranchComments(){
		comments = service.getAllBranchComments(branchId);
	}

	
	// Setters & Getters
	public List<CommentsBean> getComments() {
		return comments;
	}
	public void setComments(List<CommentsBean> comments) {
		this.comments = comments;
	}
}
