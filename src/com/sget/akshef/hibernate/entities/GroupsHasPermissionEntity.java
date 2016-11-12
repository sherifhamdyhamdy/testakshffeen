//package com.sget.akshef.hibernate.entities;
//
//import java.io.Serializable;
//
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
///**
// *
// * @author JDeeb
// */
//@Entity(name = "groups_has_permission")
//public class GroupsHasPermissionEntity implements Serializable {
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = true)
//    @Column(name = "id")
//    private Integer id;
//    
//    @JoinColumn(name = "permission_id", referencedColumnName = "id")
//    @ManyToOne(optional = true, fetch = FetchType.LAZY)
//    private PermissionEntity permission;
//    
//    @JoinColumn(name = "groups_id", referencedColumnName = "id")
//    @ManyToOne(optional = true, fetch = FetchType.LAZY)
//    private GroupsEntity group;
//
//    public GroupsHasPermissionEntity() {
//    }
//
//    public GroupsHasPermissionEntity(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    
//
//    public GroupsEntity getGroup() {
//        return group;
//    }
//
//    public void setGroup(GroupsEntity group) {
//        this.group = group;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof GroupsHasPermissionEntity)) {
//            return false;
//        }
//        GroupsHasPermissionEntity other = (GroupsHasPermissionEntity) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.egcode.akshef.entities.GroupsHasPermission[ id=" + id + " ]";
//    }
//    
//    
//    public PermissionEntity getPermission()
//	{
//		return permission;
//	}
//
//	public void setPermission(PermissionEntity permission)
//	{
//		this.permission = permission;
//	}
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
// 
//	
//}
