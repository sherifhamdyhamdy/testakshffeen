package com.sget.akshef.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.CityService;
import com.sget.akshef.hibernate.service.CountryService;

public class CityControllerAdd implements Serializable{
	
	private static final long serialVersionUID = 5183582917907321517L;
	
	private CityBean cityBean = null;
	private CityService cityService = null;
	private boolean allowUpdate = false;
	private String SelectedId = null;

	private CountryBean countryBean = null;
private  String saveOrUpdateCity ="saveCity";

	private List<CountryBean> countryBeans = null;
	private CountryService countryService = null;
	private ArrayList<SelectItem> menu_items;

/////// variable save //////////////////// make set and get ///////////
	private boolean saverender  = false;
//////////////////////////////////////////

	public CityControllerAdd() {
		cityService = new CityService();
		cityBean = new CityBean();

		countryBean = new CountryBean();

		countryBeans = new ArrayList<CountryBean>();
		countryService = new CountryService();
		menu_items = new ArrayList<SelectItem>();

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
//////////////////////////////////////////////////////////////////////////forAdd
HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

// System.out.println("CategoryControllerAdd "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("City")){

if(roleHasPermissionBean.getPermision().getId() == 2){
saverender = true;
break;
}
}
}
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		try {

			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			// System.out.println("id    ===   " + id);
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
				allowUpdate = true;
			}
			// System.out.println("allowUpdate    ===   " + allowUpdate);
			// System.out.println("SelectedId    ===   " + SelectedId);
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {

				cityBean = cityService.getById(Integer.parseInt(SelectedId));
				if(cityBean.getCountry()!=null )
				countryBean   = cityBean.getCountry();
				// System.out.println("country bean "+countryBean.getId());
				saveOrUpdateCity ="UpdateCity";

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		// /addded to get categories
		menu_items.add(new SelectItem("","select"));
		countryBeans = countryService.getAll();
		for (CountryBean bean : countryBeans) {
			menu_items.add(new SelectItem(bean.getId(), bean.getNameAr()));

		}
	}

	// saveCity
	public void saveCity(ActionEvent action) {

		if (cityBean.getId() != 0) {
			if(cityBean.getCountry()==null )
			{
				cityBean.setCountry(countryBean);
			}
			
			boolean up = cityService.update(cityBean);
			if(up)
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Updated", "Updated Successfully"));
			else
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fail", "Fail to Update"));
		} else {
			if (countryBean != null){
				int exist = cityService.checkIfCityExist(cityBean.getNameAr(), cityBean.getNameEn());
				if(exist == 2){
					cityBean.setCountry(countryBean);
					
					cityService.insert(cityBean);
					if(cityBean.getId() > 0){
						RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Inserted", "Inserted Successfully"));
					} else
		 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		 	 					"Error", "Error in Insert"));
				}else if(exist == 1){
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		 					"Error", "already Exist !!!"));
				}else{
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		 					"Error", "Error in System !!!"));
				}
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 					"Error", "Choose Category First"));
				return;
			}
		}
		cityBean = new CityBean();
		countryBean  =  new  CountryBean();
	}

	public CityBean getCityBean() {
		return cityBean;
	}

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public boolean isAllowUpdate() {
		return allowUpdate;
	}

	public void setAllowUpdate(boolean allowUpdate) {
		this.allowUpdate = allowUpdate;
	}

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	public CountryBean getCountryBean() {
		return countryBean;
	}

	public void setCountryBean(CountryBean countryBean) {
		this.countryBean = countryBean;
	}

	public List<CountryBean> getCountryBeans() {
		return countryBeans;
	}

	public void setCountryBeans(List<CountryBean> countryBeans) {
		this.countryBeans = countryBeans;
	}

	public CountryService getCountryService() {
		return countryService;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public ArrayList<SelectItem> getMenu_items() {
		return menu_items;
	}

	public void setMenu_items(ArrayList<SelectItem> menu_items) {
		this.menu_items = menu_items;
	}

	public String getSaveOrUpdateCity() {
		return saveOrUpdateCity;
	}

	public void setSaveOrUpdateCity(String saveOrUpdateCity) {
		this.saveOrUpdateCity = saveOrUpdateCity;
	}

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

}
