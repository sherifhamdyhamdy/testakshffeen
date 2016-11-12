package com.sget.akshef.hibernate.beans;

import java.util.Date;

/**
 * Author JDeeb
 */
public class CommentsBean  implements java.io.Serializable {


     private int id;
     private SpecialistBean specialist;
     private BranchBean branch;
     private UsersBean users;
     private ContentDetailsBean contentDetails;
     private String comment;
     private long comment_date ;
     private Date commentDate;
     private String users_name;

    public String getUsers_name() {
		return users_name;
	}


	public void setUsers_name(String users_name) {
		this.users_name = users_name;
	}


	public Date getCommentDate() {
		return commentDate;
	}


	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}


	public CommentsBean() {
    }

	
    public CommentsBean(UsersBean users) {
        this.users = users;
    }
    public CommentsBean(SpecialistBean specialist, BranchBean branch, UsersBean users, ContentDetailsBean contentDetails, String comment) {
       this.specialist = specialist;
       this.branch = branch;
       this.users = users;
       this.contentDetails = contentDetails;
       this.comment = comment;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public SpecialistBean getSpecialist() {
        return this.specialist;
    }
    
    public void setSpecialist(SpecialistBean specialist) {
        this.specialist = specialist;
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
    public ContentDetailsBean getContentDetails() {
        return this.contentDetails;
    }
    
    public void setContentDetails(ContentDetailsBean contentDetails) {
        this.contentDetails = contentDetails;
    }
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

	public long getComment_date() {
		return comment_date;
	}

	public void setComment_date(long comment_date) {
		this.comment_date = comment_date;
	}

}
