package com.sget.akshef.view;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import  com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.CountryService;

public class CountryControllerAdd {
	private CountryBean countryBean = null;
	private CountryService countryService = null;
 	private String SelectedId = null;
	private String saveOrUpdateCountry = "saveCountry";
	private String countryAddMsg="";
	
	private boolean saverender  = false;
    private boolean changeDoneCountry=false;
	
	public boolean isChangeDoneCountry() {
		return changeDoneCountry;
	}



	public void setChangeDoneCountry(boolean changeDoneCountry) {
		this.changeDoneCountry = changeDoneCountry;
	}



	public void changeDone(ActionEvent evt)
	{
		// System.out.println("changeDone");
		String text=((org.primefaces.component.inputtext.InputText)evt.getSource()).getValue().toString();
		if(!text.equals(""))
		{
			changeDoneCountry=true;
		}
		else
		{
			changeDoneCountry=false;
		}
		
	}
	
	
	
	public CountryControllerAdd() {
		countryService = new CountryService();
		countryBean = new CountryBean();
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {

			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			// System.out.println("id    ===   " + id);
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
 				saveOrUpdateCountry="updateCountry";
			}
 			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {

				countryBean = countryService.getById(Integer
						.parseInt(SelectedId));

			
				
			}

			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
			if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
			// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
			for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

			// System.out.println("CountryControllerAdd "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
			if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("Country")){

			if(roleHasPermissionBean.getPermision().getId() == 2){
			saverender = true;
			break;
			}
			}
			}
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// saveCountry
	public void saveCountry(ActionEvent action) {
		
		if (countryBean.getId() != 0) {
			boolean up = countryService.update(countryBean);
			if(up)
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Updated", "Updated Successfully"));
			else
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fail", "Fail to Update"));
		} else {
			List<CountryBean> list= countryService.getCountryName(countryBean.getNameEn(),countryBean.getNameAr());
			if(changeDoneCountry&&list.size()>0){
				countryAddMsg=countryBean.getNameEn()+" is already exist";
				return;
			}else{
				countryAddMsg="";
			}
			countryService.insert(countryBean);
			if(countryBean.getId() > 0)
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Inserted", "Inserted Successfully"));
			else
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fail", "Fail to Insert"));
		}
		countryBean = new  CountryBean();

	}

	public CountryBean getCountryBean() {
		return countryBean;
	}

	public void setCountryBean(CountryBean countryBean) {
		this.countryBean = countryBean;
	}

	public CountryService getCountryService() {
		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	 

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	public String getSaveOrUpdateCountry() {
		return saveOrUpdateCountry;
	}

	public void setSaveOrUpdateCountry(String saveOrUpdateCountry) {
		this.saveOrUpdateCountry = saveOrUpdateCountry;
	}

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	public String getCountryAddMsg() {
		return countryAddMsg;
	}

	public void setCountryAddMsg(String countryAddMsg) {
		this.countryAddMsg = countryAddMsg;
	}

}
