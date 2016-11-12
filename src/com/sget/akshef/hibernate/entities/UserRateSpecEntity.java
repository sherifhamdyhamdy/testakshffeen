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
@Entity(name = "user_rate_spec")
public class UserRateSpecEntity implements Serializable {
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
    @JoinColumn(name = "specialist_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private SpecialistEntity specialist;

    public UserRateSpecEntity() {
    }

    public UserRateSpecEntity(Integer id) {
        this.id = id;
    }

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

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public SpecialistEntity getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistEntity specialist) {
        this.specialist = specialist;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRateSpecEntity)) {
            return false;
        }
        UserRateSpecEntity other = (UserRateSpecEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.UserRateSpec[ id=" + id + " ]";
    }

	public String getRating_date() {
		return rating_date;
	}

	public void setRating_date(String rating_date) {
		this.rating_date = rating_date;
	}
    
}
