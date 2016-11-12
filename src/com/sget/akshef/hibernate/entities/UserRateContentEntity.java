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
 * Created on 2015-03-13
 * ( user_rate_content ) Table
 * 
 */
@Entity(name = "user_rate_content")
public class UserRateContentEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "rate")
    private Integer rate;
    @Column(name = "rating_date")
    private String rating_date;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private UsersEntity users;
    @JoinColumn(name = "content_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private ContentDetailsEntity content;
	
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public String getRating_date() {
		return rating_date;
	}
	public void setRating_date(String rating_date) {
		this.rating_date = rating_date;
	}
	public UsersEntity getUsers() {
		return users;
	}
	public void setUsers(UsersEntity users) {
		this.users = users;
	}
	public ContentDetailsEntity getContent() {
		return content;
	}
	public void setContent(ContentDetailsEntity content) {
		this.content = content;
	}
	
}
