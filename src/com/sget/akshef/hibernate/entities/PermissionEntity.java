package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author JDeeb
 */
//@Entity(name = "permission")
public class PermissionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "Permission_name")
    private String Permission_name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permission", fetch = FetchType.EAGER)
    private Set<RoleHasPermissionEntity> roleHasPermissionEntityEntities;

    public PermissionEntity() {
    }

    public PermissionEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof PermissionEntity)) {
            return false;
        }
        PermissionEntity other = (PermissionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Role[ id=" + id + " ]";
    }

	public Set<RoleHasPermissionEntity> getRoleHasPermissionEntityEntities() {
		return roleHasPermissionEntityEntities;
	}

	public void setRoleHasPermissionEntityEntities(
			Set<RoleHasPermissionEntity> roleHasPermissionEntityEntities) {
		this.roleHasPermissionEntityEntities = roleHasPermissionEntityEntities;
	}

	public String getPermission_name() {
		return Permission_name;
	}

	public void setPermission_name(String permission_name) {
		Permission_name = permission_name;
	}
    
}
