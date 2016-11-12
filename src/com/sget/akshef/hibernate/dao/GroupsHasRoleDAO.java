package com.sget.akshef.hibernate.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.sget.akshef.hibernate.beans.GroupsHasRoleBean;
import com.sget.akshef.hibernate.entities.CategoryHasSubcategoryEntity;
import com.sget.akshef.hibernate.entities.GroupsEntity;
import com.sget.akshef.hibernate.entities.GroupsHasRoleEntity;
import com.sget.akshef.hibernate.entities.RoleHasPermissionEntity;
import com.sget.akshef.hibernate.entities.SubcategoryHasSectionsHasBranchEntity;
import com.sget.akshef.hibernate.utils.HibernateSession;

/**
 * @author JDeeb
 * Groups Has Role DAO
 */
public class GroupsHasRoleDAO {
	// Hibernate Session Class 
	HibernateSession hiber ;
	
	public GroupsHasRoleDAO(){
		hiber = new HibernateSession();
	}
	
	public void insert(GroupsHasRoleEntity entity){
		try{
		Serializable ser = hiber.getSession().save(entity);
		entity.setId(ser.hashCode());
		hiber.getSession().getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			hiber.closeSession();
		}
	}
	public boolean update(GroupsHasRoleEntity entity) {
        if(entity == null)
        	return false;
		try {
            hiber.getSession().update(entity);
            hiber.getSession().getTransaction().commit();
            return true;
        } catch (Exception ex) {
             System.out.println("Inesrt Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return false;
    }

    public boolean delete(GroupsHasRoleEntity entity) {
        if(entity == null || entity.getId() < 1)
        	return false;
    	try {
            hiber.getSession().delete(entity);
            hiber.getSession().getTransaction().commit();
            return true ;
        } catch (Exception ex) {
             System.out.println("Delete Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return false;
    }
   
    
    public List<GroupsHasRoleEntity> getAll() {
        List<GroupsHasRoleEntity> entities = null;
        try {
            Query query = hiber.getSession().createSQLQuery("SELECT groups_has_role.* FROM groups_has_role groups_has_role ")
                    .addEntity("groups_has_role.", GroupsHasRoleEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public List<GroupsHasRoleEntity> getByGroup(GroupsEntity group) {
        List<GroupsHasRoleEntity> entities = null;
        try {
			Criteria itemCriteria = hiber.getSession().createCriteria(
					GroupsHasRoleEntity.class);
			 
			itemCriteria.add(Restrictions.like("groups", group));
			entities = itemCriteria.list();
			
			 
			if ( entities != null && entities.size() > 0) {
	             System.out.println("+ entities.size() nn  : " + entities.size());
				return entities;
			}else{
			entities  = new  ArrayList<GroupsHasRoleEntity>();	
			}
			
    } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }
    
    public boolean deleteByGroup(GroupsEntity group) {
  	  
        if(group == null )
        	return false;
    	try {  
    		String hql = "delete from groups_has_role c where c.groups = :name1 ";
    		Query query = hiber.getSession().createQuery(hql);
    		query.setInteger("name1",group.getId());
     		int rowCount = query.executeUpdate();
 		hiber.getSession().getTransaction().commit();
            return true ;
        } catch (Exception ex) {
			ex.printStackTrace();        
			} finally{
            hiber.closeSession();
        }
        return false;
    }
    
    public GroupsHasRoleEntity getById(int id) {
        if(id < 1)
        	return null ;
        
    	GroupsHasRoleEntity entity = null;
        try {
        	entity = (GroupsHasRoleEntity) hiber.getSession().get(GroupsHasRoleEntity.class, id);
        } catch (HibernateException ex) {
             System.out.println("Get Row : " + ex.getMessage());
        } finally {
            hiber.closeSession();
        }
        return entity;
    }
    
    // JDeeb Get Permissions for branch group
    public List<RoleHasPermissionEntity> getBranchGroupPermission(){
    	List<RoleHasPermissionEntity> entities = null;
        try {
        	String sql = " SELECT rolper.* FROM role_permission rolper INNER JOIN role rol ON rolper.role_id = rol.id"
        					+" WHERE rol.name LIKE 'branch_%' ;" ;
            Query query = hiber.getSession().createSQLQuery(sql).addEntity(RoleHasPermissionEntity.class);
            entities = query.list();
            
        } catch (Exception ex) {
             System.out.println("Get Ex : " + ex.getMessage());
        } finally{
            hiber.closeSession();
        }
        return entities;
    }

}
