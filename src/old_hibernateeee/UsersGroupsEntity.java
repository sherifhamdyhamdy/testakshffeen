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
@Entity(name = "users_groups")
public class UsersGroupsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usersGroups", fetch = FetchType.LAZY)
    private Set<UnitGroupsEntity> unitGroupsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usersGroups", fetch = FetchType.LAZY)
    private Set<BranchGroupsEntity> branchGroupsSet;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UsersEntity users;
    @JoinColumn(name = "groups_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private GroupsEntity groups;

    public UsersGroupsEntity() {
    }

    public UsersGroupsEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<UnitGroupsEntity> getUnitGroupsSet() {
        return unitGroupsSet;
    }

    public void setUnitGroupsSet(Set<UnitGroupsEntity> unitGroupsSet) {
        this.unitGroupsSet = unitGroupsSet;
    }

    public Set<BranchGroupsEntity> getBranchGroupsSet() {
        return branchGroupsSet;
    }

    public void setBranchGroupsSet(Set<BranchGroupsEntity> branchGroupsSet) {
        this.branchGroupsSet = branchGroupsSet;
    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
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
        if (!(object instanceof UsersGroupsEntity)) {
            return false;
        }
        UsersGroupsEntity other = (UsersGroupsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.UsersGroups[ id=" + id + " ]";
    }
    
}
