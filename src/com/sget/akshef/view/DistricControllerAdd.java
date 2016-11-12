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
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.service.CityService;
import com.sget.akshef.hibernate.service.DistricService;

public class DistricControllerAdd implements Serializable {
	
	private static final long serialVersionUID = -3983472905543660780L;
	
	private DistricBean districBean = null;
	private DistricService districService = null;
	private boolean allowUpdate = false;
	private String SelectedId = null;

	private CityBean cityBean = null;

	private List<CityBean> cityBeans = null;
	private CityService cityService = null;
	private ArrayList<SelectItem> menu_items;
	private String saveupdateDistric ="saveDistric";
	
	private boolean saverender  = false;

	public DistricControllerAdd() {
		districService = new DistricService();
		districBean = new DistricBean();

		cityBean = new CityBean();

		cityBeans = new ArrayList<CityBean>();
		cityService = new CityService();
		menu_items = new ArrayList<SelectItem>();

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) session.getAttribute("roleHasPermissionBeans");
if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
// System.out.println("roleHasPermissionBeans.size()        "+roleHasPermissionBeans.size());
for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){

// System.out.println("DistricControllerAdd "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
if(roleHasPermissionBean.getRole().getName().trim().equalsIgnoreCase("District")){

if(roleHasPermissionBean.getPermision().getId() == 2){
saverender = true;
break;
}
}
}
}
		
		
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

				districBean = districService.getById(Integer.parseInt(SelectedId));
				cityBean  =  districBean.getCity();
				saveupdateDistric ="updateDistric";

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		// /addded to get categories
		menu_items.add(new SelectItem("","select"));
		cityBeans = cityService.getAll();
		for (CityBean bean : cityBeans) {
			menu_items.add(new SelectItem(bean.getId(), bean.getNameAr()));

		}
	}
	boolean changeDoneDistrict;
	public boolean isChangeDoneDistrict() {
		return changeDoneDistrict;
	}
	public void setChangeDoneDistrict(boolean changeDoneDistrict) {
		this.changeDoneDistrict = changeDoneDistrict;
	}
	public void changeDone(ActionEvent evt)
	{
		// System.out.println("changeDone");
		String text=((org.primefaces.component.inputtext.InputText)evt.getSource()).getValue().toString();
		if(!text.equals(""))
		{
			changeDoneDistrict=true;
		}
		else
		{
			changeDoneDistrict=false;
		}
		
	}
	public void updateFlag(ActionEvent action) 
	{
		// System.out.println("updateFlag");
	}
	
	
	
	
private String districtMsg="";
	// saveDistric
	public void saveDistric(ActionEvent action) {
		if (districBean.getId() != 0) {
			districBean.setCity(cityBean);
			boolean up =districService.update(districBean);
			if(up)
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Updated", "Updated Successfully"));
			else
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Fail", "Fail to Update"));
		} else {
			if (cityBean != null){
				int exist = districService.checkIfDistrictExist(districBean.getNameAr(), districBean.getNameEn());
				if(exist == 2){
					districBean.setCity(cityBean);
					districService.insert(districBean);
					if(districBean.getId() > 0){
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
		districBean = new DistricBean();
		cityBean   =  new  CityBean();
	}

	public DistricBean getDistricBean() {
		return districBean;
	}

	public void setDistricBean(DistricBean districBean) {
		this.districBean = districBean;
	}

	public DistricService getDistricService() {
		return districService;
	}

	public void setDistricService(DistricService districService) {
		this.districService = districService;
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

	public CityBean getCityBean() {
		return cityBean;
	}

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}

	public List<CityBean> getCityBeans() {
		return cityBeans;
	}

	public void setCityBeans(List<CityBean> cityBeans) {
		this.cityBeans = cityBeans;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public ArrayList<SelectItem> getMenu_items() {
		return menu_items;
	}

	public void setMenu_items(ArrayList<SelectItem> menu_items) {
		this.menu_items = menu_items;
	}

	public String getSaveupdateDistric() {
		return saveupdateDistric;
	}

	public void setSaveupdateDistric(String saveupdateDistric) {
		this.saveupdateDistric = saveupdateDistric;
	}

	public boolean isSaverender() {
		return saverender;
	}

	public void setSaverender(boolean saverender) {
		this.saverender = saverender;
	}

	public String getDistrictMsg() {
		return districtMsg;
	}

	public void setDistrictMsg(String districtMsg) {
		this.districtMsg = districtMsg;
	}

}
