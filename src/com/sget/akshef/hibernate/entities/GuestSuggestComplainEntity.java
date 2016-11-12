package com.sget.akshef.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author JDeeb
 */
@Entity(name = "guest_sugestcomplain")
public class GuestSuggestComplainEntity implements Serializable {
    private static final long serialVersionUID = 8570354901182543840L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "type")
    private Integer type;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "job")
    private String job;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "title")
    private String title ;
   
    @Column(name = "details")
    private String details ;

    @Column(name = "image")
    private String image ;
    
    // Setters & Getters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
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
	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof GuestSuggestComplainEntity)) {
            return false;
        }
        GuestSuggestComplainEntity other = (GuestSuggestComplainEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "com.egcode.akshef.entities.GuestSuggestComplainEntity[ id=" + id + " ]";
    }
    
}
