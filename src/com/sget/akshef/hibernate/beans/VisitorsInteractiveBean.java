package com.sget.akshef.hibernate.beans;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author JDeeb
 */

public class VisitorsInteractiveBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name ;
    private String occupation;
    private String email;
    private String title;
    private String desc;
    private String follow_no;
    private InteractiveTypesBean interactiveType;
    private UsersBean toUser;
	private String status ;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFollow_no() {
		return follow_no;
	}
	public void setFollow_no(String follow_no) {
		this.follow_no = follow_no;
	}
	public InteractiveTypesBean getInteractiveType() {
		return interactiveType;
	}
	public void setInteractiveType(InteractiveTypesBean interactiveType) {
		this.interactiveType = interactiveType;
	}
	public UsersBean getToUser() {
		return toUser;
	}
	public void setToUser(UsersBean toUser) {
		this.toUser = toUser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
