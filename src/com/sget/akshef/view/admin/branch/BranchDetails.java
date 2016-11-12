package com.sget.akshef.view.admin.branch;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.DistricService;
import com.sget.akshef.hibernate.service.UnitService;
import com.sget.akshef.view.admin.AdminControl;

/**
 * 
 * @author JDeeb
 *
 */
public class BranchDetails extends AdminControl implements Serializable {
	
	private static final long serialVersionUID = -8145903723998726220L;
	
	private BranchBean branchBean = null;
	private ArrayList<SelectItem> menu_itemsUnities;
	private int selectedDistric;
	private int selectedUnit;
	private ArrayList<SelectItem> menu_itemsDistricies;
	private BranchService branchService = null;
	private DistricService districService = null;
	private UnitService unitService = null;

	private String SelectedId = null;

	public BranchDetails() {
		branchService = new BranchService();
		unitService = new UnitService();
		districService = new DistricService();
		branchBean = new BranchBean();

		menu_itemsUnities = new ArrayList<SelectItem>();
		menu_itemsUnities.add(new SelectItem(String.valueOf(""), ""));
		for (UnitBean unitBean : unitService.getAll()) {
			menu_itemsUnities.add(new SelectItem(String.valueOf(unitBean.getId()), unitBean.getNameAr()));
		}

		menu_itemsDistricies = new ArrayList<SelectItem>();
		menu_itemsDistricies.add(new SelectItem(String.valueOf(""), ""));
		for (DistricBean districBean : districService.getAll()) {
			menu_itemsDistricies.add(new SelectItem(String.valueOf(districBean.getId()), districBean.getNameAr()));
		}

		try {
			String id = "72";
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;
			}

			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {
				branchBean = branchService.getById(Integer.parseInt(SelectedId));

				selectedUnit = branchBean.getUnit().getId();
				selectedDistric = branchBean.getDistric().getId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// saveBranch
	public void saveBranch(ActionEvent action) {

		try{
			UnitBean unitBean = new UnitBean();
			unitBean.setId(selectedUnit);
			DistricBean districBean = new DistricBean();
			districBean.setId(selectedDistric);
	
			branchBean.setUnit(unitBean);
			branchBean.setDistric(districBean);
	
			if (branchBean.getId() != 0) {
				branchService.update(branchBean);
			}
			addFacesMessage(FacesMessage.SEVERITY_INFO, "updated Sucessfually", "Update Branch done !! ");
		}catch(Exception ex){
			ex.printStackTrace();
			addFacesMessage(FacesMessage.SEVERITY_ERROR, "Fail update", "Error in update Branch !!");
		}
	}

	
	// Setters & Getters
	public BranchBean getBranchBean() {
		return branchBean;
	}

	public void setBranchBean(BranchBean branchBean) {
		this.branchBean = branchBean;
	}

	public BranchService getBranchService() {
		return branchService;
	}

	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}

	public String getSelectedId() {
		return SelectedId;
	}

	public void setSelectedId(String selectedId) {
		SelectedId = selectedId;
	}

	public int getSelectedUnit() {
		return selectedUnit;
	}

	public void setSelectedUnit(int selectedUnit) {
		this.selectedUnit = selectedUnit;
	}

	public ArrayList<SelectItem> getMenu_itemsUnities() {
		return menu_itemsUnities;
	}

	public void setMenu_itemsUnities(ArrayList<SelectItem> menu_itemsUnities) {
		this.menu_itemsUnities = menu_itemsUnities;
	}

	public int getSelectedDistric() {
		return selectedDistric;
	}

	public void setSelectedDistric(int selectedDistric) {
		this.selectedDistric = selectedDistric;
	}

	public ArrayList<SelectItem> getMenu_itemsDistricies() {
		return menu_itemsDistricies;
	}

	public void setMenu_itemsDistricies(ArrayList<SelectItem> menu_itemsDistricies) {
		this.menu_itemsDistricies = menu_itemsDistricies;
	}

	public DistricService getDistricService() {
		return districService;
	}

	public void setDistricService(DistricService districService) {
		this.districService = districService;
	}

	public UnitService getUnitService() {
		return unitService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

}
