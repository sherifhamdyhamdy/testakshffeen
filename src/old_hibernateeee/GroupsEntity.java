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
import javax.persistence.OneToMany;

/**
 *
 * @author JDeeb
 */
@Entity(name = "groups")
public class GroupsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "unit_id")
    private Integer unitId;
    @Column(name = "branch_id")
    private Integer branchId;
    @Column(name = "system_flag")
    private Integer systemFlag;
    @Column(name = "active")
    private Integer active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<ApprovalMsgEntity> approvalMsgSet;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<ContentTypesEntity> contentTypesSet;*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<GroupsHasRoleEntity> groupsHasRoleSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<UsersGroupsEntity> usersGroupsSet;

    public GroupsEntity() {
    }

    public GroupsEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getSystemFlag() {
        return systemFlag;
    }

    public void setSystemFlag(Integer systemFlag) {
        this.systemFlag = systemFlag;
    }

    public Integer getActive() {
    	return active != null ? active : 0;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<ApprovalMsgEntity> getApprovalMsgSet() {
        return approvalMsgSet;
    }

    public void setApprovalMsgSet(Set<ApprovalMsgEntity> approvalMsgSet) {
        this.approvalMsgSet = approvalMsgSet;
    }

    /*public Set<ContentTypesEntity> getContentTypesSet() {
        return contentTypesSet;
    }

    public void setContentTypesSet(Set<ContentTypesEntity> contentTypesSet) {
        this.contentTypesSet = contentTypesSet;
    }*/

    public Set<GroupsHasRoleEntity> getGroupsHasRoleSet() {
        return groupsHasRoleSet;
    }

    public void setGroupsHasRoleSet(Set<GroupsHasRoleEntity> groupsHasRoleSet) {
        this.groupsHasRoleSet = groupsHasRoleSet;
    }

    public Set<UsersGroupsEntity> getUsersGroupsSet() {
        return usersGroupsSet;
    }

    public void setUsersGroupsSet(Set<UsersGroupsEntity> usersGroupsSet) {
        this.usersGroupsSet = usersGroupsSet;
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
        if (!(object instanceof GroupsEntity)) {
            return false;
        }
        GroupsEntity other = (GroupsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Groups[ id=" + id + " ]";
    }
    
}
