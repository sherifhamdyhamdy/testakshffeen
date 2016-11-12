package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
        
    @Column(name = "name_ar")
    private String name_ar;
        
    @Column(name = "type")
    private String type;				//1 --> Admin    &    2,3 --> Unit & Branch    &    2 --> Unit    &    3 --> Branch
    
    @Column(name = "permissions_ids")
    private String permissions_ids;

    @Column(name = "unit_id")
    private Integer unit_id;
        
    @Column(name = "medical_insurance_id")
    private Integer medical_insurance_id;
        
    @Column(name = "active")
    private Integer active;
    
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groups", fetch = FetchType.LAZY)
//    private List<ApprovalMsgEntity> approvalMsgList;
    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "groups", fetch = FetchType.LAZY)
    private List<ContentTypesEntity> contentTypesList;*/
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.LAZY)
//    private List<GroupsHasPermissionEntity> groupsHasPermissionList = new ArrayList<GroupsHasPermissionEntity>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.EAGER)
    private List<UsersGroupsEntity> usersGroupsList = new ArrayList<UsersGroupsEntity>();

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
   
    
    public String getName_ar()
	{
		return name_ar;
	}

	public void setName_ar(String name_ar)
	{
		this.name_ar = name_ar;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

		
	public String getPermissions_ids()
	{
		return permissions_ids;
	}

	public void setPermissions_ids(String permissions_ids)
	{
		this.permissions_ids = permissions_ids;
	}

		
	public Integer getUnit_id()
	{
		return unit_id;
	}

	public void setUnit_id(Integer unit_id)
	{
		this.unit_id = unit_id;
	}
	
	public Integer getMedical_insurance_id()
	{
		return medical_insurance_id;
	}

	public void setMedical_insurance_id(Integer medical_insurance_id)
	{
		this.medical_insurance_id = medical_insurance_id;
	}

	public Integer getActive() {
    	return active != null ? active : 0;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

//    public List<ApprovalMsgEntity> getApprovalMsgList() {
//        return approvalMsgList;
//    }
//
//    public void setApprovalMsgList(List<ApprovalMsgEntity> approvalMsgList) {
//        this.approvalMsgList = approvalMsgList;
//    }

    /*public List<ContentTypesEntity> getContentTypesList() {
        return contentTypesList;
    }

    public void setContentTypesList(List<ContentTypesEntity> contentTypesList) {
        this.contentTypesList = contentTypesList;
    }*/

//    public List<GroupsHasPermissionEntity> getGroupsHasPermissionList() {
//        return groupsHasPermissionList;
//    }
//
//    public void setGroupsHasPermissionList(List<GroupsHasPermissionEntity> groupsHasPermissionList) {
//        this.groupsHasPermissionList = groupsHasPermissionList;
//    }

    public List<UsersGroupsEntity> getUsersGroupsList() {
        return usersGroupsList;
    }

    public void setUsersGroupsList(List<UsersGroupsEntity> usersGroupsList) {
        this.usersGroupsList = usersGroupsList;
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
