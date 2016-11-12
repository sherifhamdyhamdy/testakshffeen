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
//@Entity(name = "groups_has_role")
public class GroupsHasRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private RoleHasPermissionEntity roleHasPermission;
    
    @JoinColumn(name = "groups_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private GroupsEntity groups;

    public GroupsHasRoleEntity() {
    }

    public GroupsHasRoleEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public GroupsEntity getGroups() {
        return groups;
    }

    public void setGroups(GroupsEntity groups) {
        this.groups = groups;
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
        if (!(object instanceof GroupsHasRoleEntity)) {
            return false;
        }
        GroupsHasRoleEntity other = (GroupsHasRoleEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.GroupsHasRole[ id=" + id + " ]";
    }

	public RoleHasPermissionEntity getRoleHasPermission() {
		return roleHasPermission;
	}

	public void setRoleHasPermission(RoleHasPermissionEntity roleHasPermission) {
		this.roleHasPermission = roleHasPermission;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
	
}
