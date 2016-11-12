package com.sget.akshef.hibernate.beans;

import java.io.Serializable;

/**
 *
 * @author JDeeb
 */
public class GuestSuggestComplainBean implements Serializable {
	
	private static final long serialVersionUID = -3729083458038539917L;
	
	private int id;
    private int type;
    private String name;
    private String job;
    private String email;
    private String title ;
    private String details ;
    private String image ;
    
    // Setters & Getters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage()
	{
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
	
	
}
