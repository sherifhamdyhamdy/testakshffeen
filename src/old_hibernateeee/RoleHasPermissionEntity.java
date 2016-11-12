package old_hibernateeee;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

 
 
/**
 *
 * @author JDeeb
 */
@Entity(name = "role_permission")
public class RoleHasPermissionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    

    //@Column(name = "Pagename")
    //private String pagename;
    
    @JoinColumn(name = "Role_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private RoleEntity role;
    
    @JoinColumn(name = "Permission_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PermissionEntity  permission;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleHasPermission", fetch = FetchType.EAGER)
    private Set<GroupsHasRoleEntity> groupsHasRoles;
    
  
    
  
    public RoleHasPermissionEntity() {
    }

    public RoleHasPermissionEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
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
        if (!(object instanceof RoleHasPermissionEntity)) {
            return false;
        }
        RoleHasPermissionEntity other = (RoleHasPermissionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.GroupsHasRole[ id=" + id + " ]";
    }

	public PermissionEntity getPermission() {
		return permission;
	}

	public void setPermission(PermissionEntity permission) {
		this.permission = permission;
	}

	public Set<GroupsHasRoleEntity> getGroupsHasRoles() {
		return groupsHasRoles;
	}

	public void setGroupsHasRoles(Set<GroupsHasRoleEntity> groupsHasRoles) {
		this.groupsHasRoles = groupsHasRoles;
	}

	/*public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}*/

	 
    
}
