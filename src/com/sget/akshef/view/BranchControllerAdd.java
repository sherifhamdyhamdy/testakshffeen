package com.sget.akshef.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.primefaces.context.RequestContext;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.SubcategoryHasSectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryHasSectionsHasBranchBean;
import com.sget.akshef.hibernate.beans.TempBranchBean;
import com.sget.akshef.hibernate.beans.UnitBean;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.CategoryService;
import com.sget.akshef.hibernate.service.CityService;
import com.sget.akshef.hibernate.service.CountryService;
import com.sget.akshef.hibernate.service.DistricService;
import com.sget.akshef.hibernate.service.SubcategoryHasSectionsHasBranchService;
import com.sget.akshef.hibernate.service.SubcategoryHasSectionsService;
import com.sget.akshef.hibernate.service.UnitService;

/**
 * 
 * @author JDeeb
 * Managed Bean for branchAdd.xhtml
 */

public class BranchControllerAdd implements Serializable{
	
	private static final long serialVersionUID = 2913304640057852352L;
	
	private BranchService branchService = null;
	private UnitService unitService = null ;
	private CityService cityService = null ;
	private DistricService districService = null;
	private SubcategoryHasSectionsService subHasSecService = null ;
	HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	
	private BranchBean branchBean = null;
	
	private ArrayList<SelectItem> menu_itemsUnities;	
	private ArrayList<SelectItem> menu_itemsCategories;
	private ArrayList<SelectItem> menu_itemsCountry;
	private ArrayList<SelectItem> menu_itemsCity;
	private ArrayList<SelectItem> menu_itemsDistricies;
	private ArrayList<SelectItem> menu_itemsSubCategories;
	private ArrayList<SelectItem> menu_itemsSections;
	
	private List<SubcategoryHasSectionsBean> subcatSectList ;
	
	private String saveOrUpdateBranch = "saveBranch";
	private String lat;
	private String lng;

	private int selectedCateg;
	private int selectedUnit;
	private int selectedCountry;
	private int selectedCity;
	private int selectedDistric;
	private int selectedSubCat ;
	private List<String> selectedSections ;
	private List<String> allSelectedSect = new ArrayList<String>();
	private List<String> oldSections ;
	private String SelectedId = null;
	
	public BranchControllerAdd() {
		branchService = new BranchService();
		cityService = new CityService();
		districService  = new  DistricService();
		branchBean = new BranchBean();
		unitService = new UnitService();
		subHasSecService = new SubcategoryHasSectionsService();
		
		selectedCateg = 0;
		selectedCountry = 0;

		// Populate Menus
		populateCountries();
		populateCategories();
		
		//////////////////////////////////////////////////////////
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			Map<String, String> maps = context.getRequestParameterMap();
			String id = maps.get("id");
			//System.out.println("id    ===   " + id);
			if (id != null && !id.trim().equalsIgnoreCase("")) {
				SelectedId = id;			
			}
			if (SelectedId != null && !SelectedId.equalsIgnoreCase("")) {
				branchBean = branchService.getById(Integer.parseInt(SelectedId));
				selectedUnit =  branchBean.getUnit().getId();
				selectedDistric = branchBean.getDistric().getId();
				try{
					// populate Menus
					selectedCateg = branchBean.getUnit().getCategory().getId();
					getUnitsByCategory();
					selectedCountry = branchBean.getDistric().getCity().getCountry().getId();
					selectedCity = branchBean.getDistric().getCity().getId();
					getCitiesByCountry();
					getDistrictByCity();
					// get branch subcatSect from DB
					getBranchSubCategorySections(Integer.parseInt(SelectedId));
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("BranchControleAdd : " + e.getMessage());
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
		 					"Error", "Error in System please contact with site Administrators"));
					//FacesContext.getCurrentInstance().getExternalContext().redirect("/");
				}
 			}
			if (branchBean.getId() != 0) {
				saveOrUpdateBranch="updateBranch";
			} else {
				saveOrUpdateBranch="saveBranch";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BranchControllerAdd : " + e.getMessage());
		}
		
		//to check the temp branch added by liliane in 25/11/2015
		if(session.getAttribute("TEMP_BRANCH")!=null)
		{
		TempBranchBean tempbranch=	(TempBranchBean)session.getAttribute("TEMP_BRANCH");
		branchBean.setLat(tempbranch.getLat());
		branchBean.setLng(tempbranch.getLng());
		selectedCateg=tempbranch.getCatBean().getId();
		getUnitsByCategory();
		branchBean.setCategory_id(tempbranch.getCatBean().getId());
		branchBean.setAddress(tempbranch.getAddress());
		branchBean.setTel(tempbranch.getTel_num());
		branchBean.setNameAr(tempbranch.getName());
		branchBean.setEmail(tempbranch.getEmail());
		session.setAttribute("TEMP_BRANCH",null);
		
		
		}
		
		
		
	}
	
	// get Branch SubCategory Sections in edit mode
	public void getBranchSubCategorySections(int branchId){
		oldSections = new ArrayList<String>();
		SubcategoryHasSectionsHasBranchService serv = new SubcategoryHasSectionsHasBranchService();
		List<SubcategoryHasSectionsHasBranchBean> subcates = serv.getBYBranch(branchId);
		if(subcates != null && subcates.size() > 0){
			for(SubcategoryHasSectionsHasBranchBean bean : subcates){
				if(bean.getSubcategoryHasSections() != null)
					oldSections.add(bean.getSubcategoryHasSections().getId()+"");
			}
			allSelectedSect.addAll(oldSections);
		}
	}
	// Populate All Categories
	public void populateCategories(){
		try{
			menu_itemsCategories = new ArrayList<SelectItem>();		 
			menu_itemsCategories.add(new SelectItem(String.valueOf(""),""));
			CategoryService catService = new CategoryService();
			List<CategoryBean> categories = catService.getAll();
			if(categories != null && categories.size() > 0){
				for (CategoryBean category : categories) {			 
					menu_itemsCategories.add(new SelectItem(String.valueOf(category.getId()),
							category.getNameAr()));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	// Populate All Units
	public void populateUnitsByCategory(ValueChangeEvent evt){
		try{
			if(evt.getNewValue() != null){
				selectedCateg = Integer.parseInt(evt.getNewValue().toString());
				if(selectedCateg > 0)
					getUnitsByCategory();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	private void getUnitsByCategory(){
		try{
			if(menu_itemsUnities != null)
				menu_itemsUnities.clear();
			else
				menu_itemsUnities = new ArrayList<SelectItem>();
			
			menu_itemsUnities.add(new SelectItem(String.valueOf(""),""));
			if(selectedCateg > 0){
				List<UnitBean> beans = unitService.getAllUnitsByCat(selectedCateg);
				if(beans != null && beans.size() > 0){
					for (UnitBean unitBean : beans) {			 
						menu_itemsUnities.add(new SelectItem(String.valueOf(unitBean.getId()),
								unitBean.getNameAr()));
					}
				}
				// Populate Sub Category
				populateSubCategories();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	// Populate All Countries
	public void populateCountries(){
		try{
			menu_itemsCountry = new ArrayList<SelectItem>();		 
			menu_itemsCountry.add(new SelectItem(String.valueOf(""),""));
			CountryService service = new CountryService();
			List<CountryBean> countries = service.getAll();
			if(countries != null && countries.size() > 0){
				for (CountryBean countryBean : countries) {			 
					menu_itemsCountry.add(new SelectItem(String.valueOf(countryBean.getId()),
							countryBean.getNameAr()));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	// Populate All Cities by Country
	public void populateCities(ValueChangeEvent evt){
		try{
			if(evt.getNewValue() != null){
				selectedCountry = Integer.parseInt(evt.getNewValue().toString());
				if(selectedCountry > 0){
					getCitiesByCountry();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	private void getCitiesByCountry(){
		try{
			if(menu_itemsCity != null)
				menu_itemsCity.clear();
			else
				menu_itemsCity = new ArrayList<SelectItem>();		 
			
			menu_itemsCity.add(new SelectItem(String.valueOf(""),""));
			if(selectedCountry > 0){
				List<CityBean> cities = cityService.getAllCitiesByCountry(selectedCountry);
				if(cities != null && cities.size() > 0){
					for (CityBean cityBean : cities) {			 
						menu_itemsCity.add(new SelectItem(String.valueOf(cityBean.getId()),
								cityBean.getNameAr()));
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	// Populate All District by city
	public void populateDistrict(ValueChangeEvent evt){
		try{
			if(evt.getNewValue() != null){
				selectedCity = Integer.parseInt(evt.getNewValue().toString());
				if(selectedCity > 0){
					getDistrictByCity();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	private void getDistrictByCity(){
		try{
			if(menu_itemsDistricies != null)
				menu_itemsDistricies.clear();
			else
				menu_itemsDistricies = new ArrayList<SelectItem>();		 
			
			menu_itemsDistricies.add(new SelectItem(String.valueOf(""),""));
			if(selectedCity > 0){
				List<DistricBean> districtes = districService.getAllDistrictByCity(selectedCity);
				if(districtes != null && districtes.size() > 0){
					for (DistricBean districtBean : districtes) {			 
						menu_itemsDistricies.add(new SelectItem(String.valueOf(districtBean.getId()),
								districtBean.getNameAr()));
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	// Populate All Units
	public void populateSubCategories(){
		try{
			if(selectedCateg > 0){
				subcatSectList = subHasSecService.getAllByCategory(selectedCateg);
				menu_itemsSubCategories = new ArrayList<SelectItem>();
				if(subcatSectList != null && subcatSectList.size() > 0){
					for(SubcategoryHasSectionsBean bean : subcatSectList){
						if(bean.getSubcategory() != null){
							boolean exist = false ;
							for(SelectItem item : menu_itemsSubCategories){
								if(item.getValue().equals(bean.getSubcategory().getId()))
									exist = true ;
							}
							if(!exist)
								menu_itemsSubCategories.add(new SelectItem(bean.getSubcategory().getId(),
										bean.getSubcategory().getNameAr()));
						}
					}
					if(allSelectedSect != null)
						allSelectedSect.clear();
					else
						allSelectedSect = new ArrayList<String>();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	// Populate All Units
	public void populateSections(ValueChangeEvent evt){
		try{
			if(evt.getNewValue() != null){
				menu_itemsSections = new ArrayList<SelectItem>();
				int selectedSubCat = Integer.parseInt(evt.getNewValue().toString());
				if(selectedSubCat > 0){
					if(subcatSectList != null && subcatSectList.size() > 0){
						for(SubcategoryHasSectionsBean bean : subcatSectList){
							if(bean.getSubcategory() != null && bean.getSubcategory().getId() == selectedSubCat){
								menu_itemsSections.add(new SelectItem(bean.getId(),
										bean.getSections().getNameAr()));
							}
						}
						/*selectedSections.clear();
						if(oldSections != null && oldSections.size() > 0)
							selectedSections.addAll(oldSections);
						if(allSelectedSect != null && allSelectedSect.size() > 0)
							selectedSections.addAll(allSelectedSect);*/
						
						if(allSelectedSect != null && allSelectedSect.size() > 0){
							if(selectedSections != null)
								selectedSections.clear();
							else
								selectedSections = new ArrayList<String>();
							for(String old : allSelectedSect){
								for(SelectItem item : menu_itemsSections){
									if(old.equalsIgnoreCase(item.getValue().toString())){
										selectedSections.add(old);
									}
								}
							}
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("BranchControllerAdd : " + ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void populateList(ValueChangeEvent evt){
		
		List<String> oldVal = (List<String>)evt.getOldValue() ;
		List<String> newVal = (List<String>)evt.getNewValue() ;
		
		if(oldVal != null && oldVal.size() > 0){
			for(String item : newVal){
				boolean exist = false;
				for(String old : oldVal){
					if(old.equalsIgnoreCase(item))
						exist = true;
				}
				if(!exist)
					allSelectedSect.add(item);
			}
			for(String old : oldVal){
				boolean exist = false;
				for(String item : newVal){
					if(old.equalsIgnoreCase(item))
						exist = true;
				}
				if(!exist)
					allSelectedSect.remove(old);
			}
		}else{
			allSelectedSect.addAll(newVal);
		}
	}
	
	// saveBranch
	public void saveBranch(ActionEvent action) {
		UnitBean  unitBean  =  new  UnitBean();
		unitBean.setId(selectedUnit);
		DistricBean districBean=new  DistricBean();
		districBean.setId(selectedDistric);
		
		branchBean.setUnit(unitBean);
		branchBean.setDistric(districBean);
		
		if (branchBean.getId() != 0) {
			boolean up = branchService.update(branchBean);
			if(up){
				Map<String,int[]> items = prepareUpdateSubcatSect();
				if(items.get("Deleted") != null && items.get("Deleted").length > 0){
					boolean del = branchService.deleteBranchSubcategorySection(items.get("Deleted"), branchBean.getId());
					if(del){
						if(items.get("Inserted") != null && items.get("Inserted").length > 0){
							boolean ins = branchService.insertBranchSubcategorySection(items.get("Inserted"), branchBean.getId());
							if(ins)
								RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
					 					"Updated", "Updated Success"));
							else	
								RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					 					"Error", "Error in System, call system admin"));
						}else
							RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
				 					"Updated", "Updated Success"));
					}else
						RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
			 					"Error", "Error in System, call system admin"));
				}else if(items.get("Inserted") != null && items.get("Inserted").length > 0){
					boolean ins = branchService.insertBranchSubcategorySection(items.get("Inserted"), branchBean.getId());
					if(ins)
						RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
			 					"Updated", "Updated Success"));
					else	
						RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
			 					"Error", "Error in System, call system admin"));
				}else{
					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
		 					"Updated", "Updated Success"));
				}
 			}else
 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
 	 					"Error", "Error in Update"));
			
		} else {
			int exist = branchService.checkIfBranchExist(branchBean.getNameAr(), branchBean.getNameEn(), 
					branchBean.getLat(), branchBean.getLng());
			if(exist == 2){
				branchService.insert(branchBean);
				if(branchBean.getId() > 0){
					if(allSelectedSect != null && allSelectedSect.size() > 0){
						int[] items = convertListStringToIntArr(allSelectedSect);
						boolean insrt = branchService.insertBranchSubcategorySection(items, branchBean.getId());
						if(insrt)
							RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
				 					"Inserted", "Inserted Successfully"));
					}
				} else
	 				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 	 					"Error", "Error in Insert"));
			}else if(exist == 1){
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 					"Error", "already Exist !!!"));
				if(allSelectedSect != null)
					allSelectedSect.clear();
				selectedSections.clear();
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
	 					"Error", "Error in System !!!"));
			}
		}
		branchBean = new  BranchBean();
		if (branchBean.getId() != 0) {
			saveOrUpdateBranch="updateBranch";
		} else {
			saveOrUpdateBranch="saveBranch";
		}
		//selectedUnit =0;
		selectedDistric=0;
		selectedCity=0;
		selectedCountry=0;
		menu_itemsCity = new ArrayList<SelectItem>();
		menu_itemsDistricies=new ArrayList<SelectItem>();
		if(selectedSections != null)
			selectedSections.clear();
		if(allSelectedSect != null)
			allSelectedSect.clear();
	}
	private int[] convertListStringToIntArr(List<String> items){
		try{
			if(items != null && items.size() > 0){
				int[] intItems = new int[items.size()];
				for(int i=0; i < intItems.length; i++){
					intItems[i] = Integer.parseInt(items.get(i));
				}
				return intItems;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("BranchControllerAdd : " + e.getMessage());
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private Map<String,int[]> prepareUpdateSubcatSect() {
		Map<String,int[]> subCatSecMap = new HashedMap();
		try{
			if(oldSections != null && oldSections.size() > 0 &&
					allSelectedSect != null && allSelectedSect.size() > 0){
				List<String> deletedItems = new ArrayList<String>();
				List<String> insertedItems = new ArrayList<String>();
				for(String item : allSelectedSect){
					boolean exist = false;
					for(String temp : oldSections){
						if(temp.equalsIgnoreCase(item))
							exist = true ;
					}
					if(!exist)
						insertedItems.add(item);
				}
				for(String item : oldSections){
					boolean exist = false;
					for(String temp : allSelectedSect){
						if(temp.equalsIgnoreCase(item))
							exist = true ;
					}
					if(!exist)
						deletedItems.add(item);
				}
				subCatSecMap.put("Inserted", convertListStringToIntArr(insertedItems));
				subCatSecMap.put("Deleted", convertListStringToIntArr(deletedItems));
			}else if(allSelectedSect != null && allSelectedSect.size() > 0){
				subCatSecMap.put("Inserted", convertListStringToIntArr(allSelectedSect));
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("BranchControllerAdd : " + e.getMessage());
		}
		return subCatSecMap;
	}
	
	// Getters & Setters
	public BranchBean getBranchBean() {
		return branchBean;
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
	public String getSaveOrUpdateBranch() {
		return saveOrUpdateBranch;
	}
	public void setSaveOrUpdateBranch(String saveOrUpdateBranch) {
		this.saveOrUpdateBranch = saveOrUpdateBranch;
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
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public ArrayList<SelectItem> getMenu_itemsCountry() {
		return menu_itemsCountry;
	}
	public void setMenu_itemsCountry(ArrayList<SelectItem> menu_itemsCountry) {
		this.menu_itemsCountry = menu_itemsCountry;
	}
	public ArrayList<SelectItem> getMenu_itemsCity() {
		return menu_itemsCity;
	}
	public void setMenu_itemsCity(ArrayList<SelectItem> menu_itemsCity) {
		this.menu_itemsCity = menu_itemsCity;
	}
	public ArrayList<SelectItem> getMenu_itemsCategories() {
		return menu_itemsCategories;
	}
	public void setMenu_itemsCategories(ArrayList<SelectItem> menu_itemsCategories) {
		this.menu_itemsCategories = menu_itemsCategories;
	}
	public ArrayList<SelectItem> getMenu_itemsSubCategories() {
		return menu_itemsSubCategories;
	}
	public void setMenu_itemsSubCategories(
			ArrayList<SelectItem> menu_itemsSubCategories) {
		this.menu_itemsSubCategories = menu_itemsSubCategories;
	}
	public ArrayList<SelectItem> getMenu_itemsSections() {
		return menu_itemsSections;
	}
	public void setMenu_itemsSections(ArrayList<SelectItem> menu_itemsSections) {
		this.menu_itemsSections = menu_itemsSections;
	}
	public int getSelectedCountry() {
		return selectedCountry;
	}
	public void setSelectedCountry(int selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	public int getSelectedCity() {
		return selectedCity;
	}
	public void setSelectedCity(int selectedCity) {
		this.selectedCity = selectedCity;
	}
	public int getSelectedCateg() {
		return selectedCateg;
	}
	public void setSelectedCateg(int selectedCateg) {
		this.selectedCateg = selectedCateg;
	}
	public int getSelectedSubCat() {
		return selectedSubCat;
	}
	public void setSelectedSubCat(int selectedSubCat) {
		this.selectedSubCat = selectedSubCat;
	}
	public List<String> getSelectedSections() {
		return selectedSections;
	}
	public void setSelectedSections(List<String> selectedSections) {
		this.selectedSections = selectedSections;
	}
}