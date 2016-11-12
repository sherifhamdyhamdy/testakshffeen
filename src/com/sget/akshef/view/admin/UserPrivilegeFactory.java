package com.sget.akshef.view.admin;

import java.io.Serializable;
import java.util.List;

import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;

public class UserPrivilegeFactory implements Serializable{

	private static final long serialVersionUID = -254028688420006369L;
	
	
	private final int PERMISSION_VIEW = 1;
	private final int PERMISSION_ADD = 2 ;
	private final int PERMISSION_UPDATE = 3 ;
	private final int PERMISSION_DELETE = 4 ;
	
	/**
	 * Prepare User Privileges For BackEnd System 
	 * @param roleHasPermissionBeans
	 * @param userPrivileges
	 */
	public UserPrivilege prepareUserPrivilege(List<RoleHasPermissionBean> roleHasPermissionBeans,UserPrivilege userPrivileges){
		if(roleHasPermissionBeans == null || roleHasPermissionBeans.size() == 0)
			return null;
		if(userPrivileges == null)
			userPrivileges = new UserPrivilege();
		for(RoleHasPermissionBean bean : roleHasPermissionBeans){
			if(bean.getRole().getName().trim().equalsIgnoreCase("Category")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setCategoryShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setCategoryAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setCategoryUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setCategoryDelete(true);
					continue;
				}
			}else if(bean.getRole().getName().trim().equalsIgnoreCase("SubCategory")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setSubCategoryShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setSubCategoryAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setSubCategoryUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setSubCategoryDelete(true);
					continue;
				}
			}else if(bean.getRole().getName().trim().equalsIgnoreCase("Section")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setSectionShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setSectionAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setSectionUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setSectionDelete(true);
					continue;
				}
			}else if(bean.getRole().getName().trim().equalsIgnoreCase("Country")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setCountryShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setCountryAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setCountryUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setCountryDelete(true);
					continue;
				}
			}else if(bean.getRole().getName().trim().equalsIgnoreCase("City")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setCityShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setCityAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setCityUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setCityDelete(true);
					continue;
				}
			}else if(bean.getRole().getName().trim().equalsIgnoreCase("District")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setDistrictShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setDistrictAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setDistrictUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setDistrictDelete(true);
					continue;
				}
			}else if(bean.getRole().getName().trim().equalsIgnoreCase("ContentType")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setContentTypeShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setContentTypeAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setContentTypeUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setContentTypeDelete(true);
					continue;
				}
			}else if(bean.getRole().getName().trim().equalsIgnoreCase("Branch")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setBranchShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setBranchAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setBranchUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setBranchDelete(true);
					continue;
				}
			}else if(bean.getRole().getName().trim().equalsIgnoreCase("Unit")){
				if(bean.getPermision().getId() == PERMISSION_VIEW){
					userPrivileges.setUnitShow(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_ADD){
					userPrivileges.setUnitAdd(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_UPDATE){
					userPrivileges.setUnitUpdate(true);
					continue;
				}else if(bean.getPermision().getId() == PERMISSION_DELETE){
					userPrivileges.setUnitDelete(true);
					continue;
				}
			}
		}
		return userPrivileges;
	}
	
}