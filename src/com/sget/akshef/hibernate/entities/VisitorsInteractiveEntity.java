package com.sget.akshef.hibernate.entities;

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
@Entity(name = "visitors_interactive")
public class VisitorsInteractiveEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "occupation")
    private String occupation;
    @Column(name = "email")
    private String email;
    @Column(name = "title")
    private String title;
    @Column(name = "desc")
    private String desc;
    @Column(name = "follow_no")
    private String follow_no;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private InteractiveTypesEntity interactiveType;
    @JoinColumn(name = "to_user", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UsersEntity toUser;
    private String status ;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public UsersEntity getToUser() {
		return toUser;
	}
	public void setToUser(UsersEntity toUser) {
		this.toUser = toUser;
	}
	public InteractiveTypesEntity getInteractiveType() {
		return interactiveType;
	}
	public void setInteractiveType(InteractiveTypesEntity interactiveType) {
		this.interactiveType = interactiveType;
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
