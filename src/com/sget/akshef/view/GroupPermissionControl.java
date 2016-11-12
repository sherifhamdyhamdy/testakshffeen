package com.sget.akshef.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.sget.akshef.hibernate.beans.GroupsBean;
import com.sget.akshef.hibernate.beans.GroupsHasRoleBean;
import com.sget.akshef.hibernate.beans.PermisionBean;
import com.sget.akshef.hibernate.beans.RoleBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.GroupsHasRoleService;
import com.sget.akshef.hibernate.service.GroupsService;
import com.sget.akshef.hibernate.service.RoleService;

/**
 * This class is like RolePermissionsControllerAdd class
 * and the XHTML Page groupPermission is like Role_Permission page
 */
public class GroupPermissionControl implements Serializable{
	
	private static final long serialVersionUID = -5348481332995529373L;
	
	private GroupsBean groupsBean = null;
	private RoleBean roleBean = null;
	private GroupsHasRoleBean groupsHasRoleBean = null;
	private RoleHasPermissionBean roleHasPermissionBean  = new RoleHasPermissionBean();
	
	private GroupsService groupsService = null;
	private RoleService roleService = null;
	private GroupsHasRoleService  groupsHasRoleService=  null;
	
	private ArrayList<SelectItem> menu_items  =  new  ArrayList<SelectItem>();
 	private List<SelectItem> menu_itemsDistricies =null;
    private List<SelectItem> sectionsBeansItems =  null;
    
 	private List<GroupsBean> groupsBeans = null;
 	private List<RoleBean> roleBeans = null;
	private List<String> sectionsBeansString   = new  ArrayList<String>(); // +getter +setter
    private List<GroupsHasRoleBean> groupsHasRoleBeans = null;
	
	Map<Integer, RoleBean>  sourceSubcategoryBeans= null;
 	Map<Integer, List<String>>  RolesPermissions= null;
 	
 	private int targetString =0;
 	private String message = "";
 	
 	
	public GroupPermissionControl() {
		groupsBean = new GroupsBean();
		groupsBeans = new ArrayList<GroupsBean>();
		roleBean = new RoleBean();
		roleBeans = new ArrayList<RoleBean>();
		roleService = new RoleService();
		groupsService = new GroupsService();
		
		menu_items = new ArrayList<SelectItem>();
		menu_items.add(new SelectItem("","select"));
		groupsBeans = groupsService.getAll();
		for (GroupsBean groupsBean : groupsBeans) {
			menu_items.add(new SelectItem(groupsBean.getId(), groupsBean.getName()));
		}
		
		menu_itemsDistricies = new ArrayList<SelectItem>();
		roleBeans = roleService.getAll();
		
		filSourceSubcategoryBeans();
		fillAllSubcategoryBeans();
		
        sectionsBeansItems =  new  ArrayList<SelectItem>();
		groupsHasRoleService  =  new GroupsHasRoleService();
		groupsHasRoleBean  = new GroupsHasRoleBean();
		groupsHasRoleBeans  =   new   ArrayList<GroupsHasRoleBean>();		
		RolesPermissions  = new HashMap<Integer, List<String>>();
	}
	
	private void filSourceSubcategoryBeans() {
		sourceSubcategoryBeans= new  HashMap<Integer, RoleBean>();		
 		for(RoleBean  roleBean:  roleBeans){
 			sourceSubcategoryBeans.put(roleBean.getId(), roleBean);		 
		} 		
	}

	private void fillAllSubcategoryBeans() {
		menu_itemsDistricies.clear();
  		for(RoleBean  roleBean:  roleBeans){		
			SelectItem item = new SelectItem();
			item.setValue(roleBean.getId());
			item.setLabel(roleBean.getName());
			menu_itemsDistricies.add(item);
		}
 		
	}
	
	private void fillRoles(){
		RolesPermissions.clear();
		 if(groupsHasRoleBeans !=  null){
	        for(GroupsHasRoleBean  hasRoleBean :groupsHasRoleBeans){
			  	if( !RolesPermissions.containsKey(hasRoleBean.getRoleHasPermission().getRole().getId()))	{
			  		List<String>  list= new ArrayList<String>();
			  		list.add(String.valueOf(hasRoleBean.getRoleHasPermission().getId()));
			  		RolesPermissions.put(hasRoleBean.getRoleHasPermission().getRole().getId(), list);	  		
			  	}else{
			  		List<String>  list= RolesPermissions.get(hasRoleBean.getRoleHasPermission().getRole().getId());
			  		list.add(String.valueOf(hasRoleBean.getRoleHasPermission().getId()));
			  		RolesPermissions.put(hasRoleBean.getRoleHasPermission().getRole().getId(), list);
			  	}
	    	}
	        
	     }
	}
	
	public void onSelectGroup(ActionEvent actionEvent) {
		
		groupsHasRoleBeans.clear();
		RolesPermissions.clear();
		sectionsBeansItems.clear();

		groupsHasRoleBean .setId(groupsBean.getId());
        groupsHasRoleBeans=  groupsHasRoleService.getByGroup(groupsHasRoleBean);
        
        fillRoles();    
	}
	
	private boolean checksExist(int yu){ 
		List<String>  strings  =  RolesPermissions.get(roleBean.getId());
		if (strings  != null &&  strings.size() >0) {
			for(String  integer:strings){
				if(integer.trim().equalsIgnoreCase(String.valueOf(yu).trim())){
					return  true;
				}
			}
			
		}
      return false;
	}
	 
	public void selectSections(ActionEvent actionEvent) {
		roleBean =  sourceSubcategoryBeans.get(targetString);       
        sectionsBeansItems =  new  ArrayList<SelectItem>();
  		SelectItem item =  new SelectItem();  		  		
     	Set<PermisionBean>  permisionBeans =	roleBean.getRolesHasPermissions();
     	Iterator<PermisionBean>  iteratoriter =	permisionBeans.iterator();

     	if(permisionBeans  !=  null){
	 		while(iteratoriter.hasNext()){
	 			PermisionBean permisionBean = iteratoriter.next();
	 			item  = new SelectItem();
		 		item.setValue(permisionBean.getId()+"");
		 		item.setLabel(permisionBean.getPermission_name());

		 		if(checksExist(permisionBean.getId()  )){
		 			sectionsBeansString.add(String.valueOf(permisionBean.getId()  ));
		 		}
		 		sectionsBeansItems.add(item);
	 		}
     	}
	}
    public void changeSections(ActionEvent actionEvent) {
    	if(sectionsBeansString  != null){
    		List<String>  list = new ArrayList<String>();
		  	list.addAll(sectionsBeansString);
		  	RolesPermissions.put(roleBean.getId(), list);
    	}
    }
    
    public void save(ActionEvent action) {
		if(sectionsBeansString  != null){   		
	  	List<String>  list= new ArrayList<String>();
	  	list.addAll(sectionsBeansString);
		  RolesPermissions.put(roleBean.getId(), list);	  						
    	}
		if(groupsBean == null || groupsBean.getId() == 0){
			return;
		}
		groupsHasRoleService.deleteByGroup(groupsBean);
		
        java.util.Set<Integer> set = RolesPermissions.keySet();
		Iterator<Integer> iterator= set.iterator();
		while(iterator.hasNext()){
			int rd =    iterator.next();
			List<String>  strings  =  RolesPermissions.get(rd);
			if (strings  != null &&  strings.size() >0) {
				for(String  integer:strings){
					groupsHasRoleBean = new  GroupsHasRoleBean();
					groupsHasRoleBean.setGroups(groupsBean);
					roleHasPermissionBean=  new  RoleHasPermissionBean();
					roleHasPermissionBean.setId(Integer.parseInt(integer));
					groupsHasRoleBean.setRoleHasPermission(roleHasPermissionBean);
					groupsHasRoleService.insert(groupsHasRoleBean);
				}				
			}
		}
		clear(null);
	}

	public void clear(ActionEvent action) {
		groupsBean = new GroupsBean();
		groupsBean.setId(0);
		roleBean= new RoleBean();
		groupsHasRoleBeans.clear();
		RolesPermissions.clear();
		sectionsBeansItems.clear();
	}
	
	// Setters & Getters
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<GroupsBean> getGroupsBeans() {
		return groupsBeans;
	}
	public void setGroupsBeans(List<GroupsBean> groupsBeans) {
		this.groupsBeans = groupsBeans;
	}
	public List<RoleBean> getRoleBeans() {
		return roleBeans;
	}
	public void setRoleBeans(List<RoleBean> roleBeans) {
		this.roleBeans = roleBeans;
	}
	public GroupsBean getGroupsBean() {
		return groupsBean;
	}
	public void setGroupsBean(GroupsBean groupsBean) {
		this.groupsBean = groupsBean;
	}
	public ArrayList<SelectItem> getMenu_items() {
		return menu_items;
	}
	public void setMenu_items(ArrayList<SelectItem> menu_items) {
		this.menu_items = menu_items;
	}
	public RoleBean getRoleBean() {
		return roleBean;
	}
	public void setRoleBean(RoleBean roleBean) {
		this.roleBean = roleBean;
	}
	public int getTargetString() {
		return targetString;
	}
	public void setTargetString(int targetString) {
		this.targetString = targetString;
	}
	public List<SelectItem> getMenu_itemsDistricies() {
		return menu_itemsDistricies;
	}
	public void setMenu_itemsDistricies(List<SelectItem> menu_itemsDistricies) {
		this.menu_itemsDistricies = menu_itemsDistricies;
	}
	public List<SelectItem> getSectionsBeansItems() {
		return sectionsBeansItems;
	}
	public void setSectionsBeansItems(List<SelectItem> sectionsBeansItems) {
		this.sectionsBeansItems = sectionsBeansItems;
	}
	public List<String> getSectionsBeansString() {
		return sectionsBeansString;
	}
	public void setSectionsBeansString(List<String> sectionsBeansString) {
		this.sectionsBeansString = sectionsBeansString;
	}	
}
