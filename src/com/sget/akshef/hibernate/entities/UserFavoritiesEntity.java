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
@Entity(name = "user_favorities")
public class UserFavoritiesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "notes")
    private String notes;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private UsersEntity users;
    @JoinColumn(name = "specialist_id", referencedColumnName = "id")
    @ManyToOne(optional = true,fetch = FetchType.EAGER)
    private SpecialistEntity specialist;
    @JoinColumn(name = "content_details_id", referencedColumnName = "id")
    @ManyToOne(optional = true,fetch = FetchType.EAGER)
    private ContentDetailsEntity contentDetails;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = true,fetch = FetchType.EAGER)
    private BranchEntity branch;   

    public UserFavoritiesEntity() {
    }

    public UserFavoritiesEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public ContentDetailsEntity getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetailsEntity contentDetails) {
        this.contentDetails = contentDetails;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
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
        if (!(object instanceof UserFavoritiesEntity)) {
            return false;
        }
        UserFavoritiesEntity other = (UserFavoritiesEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.UserFavorities[ id=" + id + " ]";
    }
    
}
