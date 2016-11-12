/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sget.akshef.managedbeans;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RateEvent;

import com.sget.akshef.beans.AdviceCategoryBean;
import com.sget.akshef.beans.ContentAdviceBean;
import com.sget.akshef.buisness.AdvicesFactory;
import com.sget.akshef.buisness.BranchFactory;
import com.sget.akshef.buisness.UnitsFactory;
import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.CategoryBean;
import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.DaysBean;
import com.sget.akshef.hibernate.beans.DegreeBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.HourBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.dao.BranchDAO;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.CityService;
import com.sget.akshef.hibernate.service.CountryService;
import com.sget.akshef.hibernate.service.DaysService;
import com.sget.akshef.hibernate.service.DistricService;
import com.sget.akshef.hibernate.service.SearchService;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshef.hibernate.service.SubcategoryService;
import com.sget.akshf.searchcriteria.AdvancedSearchCriteria;
import com.sget.akshf.searchcriteria.ResultSearchBean;
 

public class MedAdvancedSearchBean implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    UsersBean user;
    CategoryBean selectedCatBean;
    SubcategoryBean subCategoryBean;
    CountryBean countryBean;
    CityBean cityBean;
    SectionsBean sectionBean;
    DistricBean districtBean;
    DaysBean dayFrom;
    DaysBean dayTo;
    ArrayList<SubcategoryBean> subCategoryList;
    ArrayList<SectionsBean> sectionList;
    ArrayList<CategoryBean> categoryList;
    ArrayList<CountryBean> countryList;
    ArrayList<DistricBean> districtList;
    ArrayList<DaysBean> daysList;
    ArrayList<HourBean> hourList;
    private String keyWordName;
    private boolean specialistFlag;
    private String gender;
    private DegreeBean degree;
    
    public DegreeBean getDegree() {
		return degree;
	}



	public void setDegree(DegreeBean degree) {
		this.degree = degree;
	}



	public String getKeyWordName() {
		return keyWordName;
	}



	public void setKeyWordName(String keyWordName) {
		this.keyWordName = keyWordName;
	}



	ArrayList<CityBean> cityList;
    private ArrayList<SelectItem> categoryListItems;	
    private ArrayList<SelectItem> subCategoryListItems;	
    private ArrayList<SelectItem> sectionsListItems;	
    private ArrayList<SelectItem> countryListItems;	
    private ArrayList<SelectItem> cityListItems;	
    private ArrayList<SelectItem> districtListItems;
    private ArrayList<SelectItem> daysfromListItems;
    private ArrayList<SelectItem> daysToListItems;
    private ArrayList<SelectItem> hoursfromListItems;
    private ArrayList<SelectItem> hoursToListItems;
    private ArrayList<SelectItem> degreeListItems;	
      public ArrayList<SelectItem> getDegreeListItems() {
		return degreeListItems;
	}



	public void setDegreeListItems(ArrayList<SelectItem> degreeListItems) {
		this.degreeListItems = degreeListItems;
	}



	/** Creates a new instance of MedLoginBean */
    public MedAdvancedSearchBean() {
    	selectedCatBean=new CategoryBean();
    	subCategoryBean=new SubcategoryBean();
    	countryBean=new CountryBean();
    	cityBean=new CityBean();
    	 sectionBean=new SectionsBean();
         districtBean=new DistricBean();
    }
    


 public String pobulateCategory()
 {
	 UnitsFactory unitFact=new UnitsFactory();
	 categoryList =(ArrayList<CategoryBean>) unitFact.getAllCategories();
      categoryListItems=new ArrayList<SelectItem>();
      categoryListItems.add(new SelectItem(" "," ")); 
      for(CategoryBean bean:categoryList)
      {
    	  categoryListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	  bean.getNameAr())); 
      }
      
      sectionsListItems=new ArrayList<SelectItem>();
      sectionsListItems.add(new SelectItem(" "," ")); 
      

   
   return "";
    
    
   
   
 }
 
 
 public String pobulateSubCategory()
 {
	 SubcategoryService service=new SubcategoryService();
	 
	 subCategoryList =(ArrayList<SubcategoryBean>) service.getAllSubCategories(selectedCatBean.getId());
      subCategoryListItems=new ArrayList<SelectItem>();
      subCategoryListItems.add(new SelectItem(" "," ")); 
      
      for(SubcategoryBean bean:subCategoryList)
      {
    	  subCategoryListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	  bean.getNameAr())); 
      }
      
      

   
   return "";
    
    
 }

 public String pobulateSections()
 {
	 UnitsFactory unitFact=new UnitsFactory();
	 
	 sectionList =(ArrayList<SectionsBean>) unitFact.getAllSectionsBySubCategory(selectedCatBean.getId(),subCategoryBean.getId());
      sectionsListItems=new ArrayList<SelectItem>();
      sectionsListItems.add(new SelectItem(" "," ")); 
      for(SectionsBean bean:sectionList)
      {
    	  sectionsListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	  bean.getNameAr())); 
      }
      
      
      

   
   return "";
    
    
 }
 
 SpecialistService specService=new SpecialistService();
 
// public String pobulateDegree()
// {
//	
//	List<DegreeBean> degreeBeans=specService.getAllDegree();
//       System.out.println("degreeBeans  "+degreeBeans);
//      degreeListItems=new ArrayList<SelectItem>();
//      degreeListItems.add(new SelectItem(" "," ")); 
//      for(DegreeBean bean:degreeBeans)
//      {
//    	  degreeListItems.add(new SelectItem(String.valueOf(bean.getId()),
//    	  bean.getDegree()));
//      }
//       
//   return "";
//    
//    
// }
 
 
 
 public String pobulateCountry()
 {
	 CountryService service=new CountryService();
	 countryList =(ArrayList<CountryBean>) service.getAll();
      countryListItems=new ArrayList<SelectItem>();
      countryListItems.add(new SelectItem(" "," ")); 
      for(CountryBean bean:countryList)
      {
    	  countryListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	  bean.getNameAr()));
      }
       
      districtListItems=new ArrayList<SelectItem>();
      districtListItems.add(new SelectItem(" "," ")); 
      

   
   return "";
    
    
 }
 
 public String pobulateCity()
 {
	 CityService service=new CityService();
	 cityList =(ArrayList<CityBean>) service.getAllCitiesByCountry(countryBean.getId());
      cityListItems=new ArrayList<SelectItem>();
      cityListItems.add(new SelectItem(" "," ")); 
      
      for(CityBean bean:cityList)
      {
    	  cityListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	  bean.getNameAr()));
      }
//      if(cityListItems.size()>0)
//      {
//      String valueCity=((SelectItem) cityListItems.get(0)).getValue().toString();
//	  cityBean.setId(Integer.parseInt(valueCity));
//      }
//      else
//      {
//    	  cityBean.setId(0);
//      }
      

   
   return "";
    
    
 }
 
 
 public String pobulateDistrict()
 {
	 DistricService service=new DistricService();
//	 if(cityBean.getId()==0)
//	 {
//		if(cityListItems.size()!=0) 
//		{
//		 String value=((SelectItem) cityListItems.get(0)).getValue().toString();
//		 cityBean.setId(Integer.parseInt(value));
//		}
//	 }
//	 
	 districtList =(ArrayList<DistricBean>) service.getAllDistrictByCity(cityBean.getId());
      districtListItems=new ArrayList<SelectItem>();
      districtListItems.add(new SelectItem(" "," ")); 
      
      for(DistricBean bean:districtList)
      {
    	  districtListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	  bean.getNameAr()));
      }
       
      
      

   
   return "";
    
    
 }
 
 
 public String pobulateDays()
 {
	 DaysService daysServ=new DaysService();
	 daysList =(ArrayList<DaysBean>) daysServ.getAll();
      daysfromListItems=new ArrayList<SelectItem>();
      daysToListItems=new ArrayList<SelectItem>();
      daysfromListItems.add(new SelectItem(" "," ")); 
      daysToListItems.add(new SelectItem(" "," "));
      for(DaysBean bean:daysList)
      {
    	  daysfromListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	  bean.getName_ar())); 
    	  daysToListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	    	  bean.getName_ar())); 
      }
      
    
      

   
   return "";
    
    
 }
  
 
 
 public String pobulateHours()
 {
	 DaysService daysServ=new DaysService();
	 hourList =(ArrayList<HourBean>) daysServ.getAllHours();
    
      hoursfromListItems=new ArrayList<SelectItem>();
      hoursToListItems=new ArrayList<SelectItem>();
      hoursfromListItems.add(new SelectItem(" "," ")); 
      hoursToListItems.add(new SelectItem(" "," "));
      for(HourBean bean:hourList)
      {
    	  hoursfromListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	  bean.getHour())); 
    	  hoursToListItems.add(new SelectItem(String.valueOf(bean.getId()),
    	    	  bean.getHour())); 
      }
      
    
      

   
   return "";
    
    
 }
 
 

public UsersBean getUser() {
	return user;
}



public void setUser(UsersBean user) {
	this.user = user;
}



public CategoryBean getSelectedCatBean() {
	return selectedCatBean;
}



public void setSelectedCatBean(CategoryBean selectedCatBean) {
	this.selectedCatBean = selectedCatBean;
}



public ArrayList<CategoryBean> getCategoryList() {
	if(categoryList==null)
		categoryList=new ArrayList<CategoryBean>();
	return categoryList;
}



public void setCategoryList(ArrayList<CategoryBean> categoryList) {
	this.categoryList = categoryList;
}



public ArrayList<SelectItem> getCategoryListItems() {
	return categoryListItems;
}



public void setCategoryListItems(ArrayList<SelectItem> categoryListItems) {
	this.categoryListItems = categoryListItems;
}



public ArrayList<SubcategoryBean> getSubCategoryList() {
	return subCategoryList;
}



public void setSubCategoryList(ArrayList<SubcategoryBean> subCategoryList) {
	this.subCategoryList = subCategoryList;
}



public ArrayList<SelectItem> getSubCategoryListItems() {
	return subCategoryListItems;
}



public void setSubCategoryListItems(ArrayList<SelectItem> subCategoryListItems) {
	this.subCategoryListItems = subCategoryListItems;
}



public SubcategoryBean getSubCategoryBean() {
	return subCategoryBean;
}



public void setSubCategoryBean(SubcategoryBean subCategoryBean) {
	this.subCategoryBean = subCategoryBean;
}



public ArrayList<SectionsBean> getSectionList() {
	return sectionList;
}



public void setSectionList(ArrayList<SectionsBean> sectionList) {
	this.sectionList = sectionList;
}



public ArrayList<SelectItem> getSectionsListItems() {
	return sectionsListItems;
}



public void setSectionsListItems(ArrayList<SelectItem> sectionsListItems) {
	this.sectionsListItems = sectionsListItems;
}



public ArrayList<SelectItem> getCountryListItems() {
	return countryListItems;
}



public void setCountryListItems(ArrayList<SelectItem> countryListItems) {
	this.countryListItems = countryListItems;
}



public ArrayList<CountryBean> getCountryList() {
	return countryList;
}



public void setCountryList(ArrayList<CountryBean> countryList) {
	this.countryList = countryList;
}







public CountryBean getCountryBean() {
	return countryBean;
}



public void setCountryBean(CountryBean countryBean) {
	this.countryBean = countryBean;
}



public ArrayList<CityBean> getCityList() {
	return cityList;
}



public void setCityList(ArrayList<CityBean> cityList) {
	this.cityList = cityList;
}



public ArrayList<SelectItem> getCityListItems() {
	return cityListItems;
}



public void setCityListItems(ArrayList<SelectItem> cityListItems) {
	this.cityListItems = cityListItems;
}



public CityBean getCityBean() {
	return cityBean;
}



public void setCityBean(CityBean cityBean) {
	this.cityBean = cityBean;
}



public ArrayList<DistricBean> getDistrictList() {
	return districtList;
}



public void setDistrictList(ArrayList<DistricBean> districtList) {
	this.districtList = districtList;
}



public ArrayList<SelectItem> getDistrictListItems() {
	return districtListItems;
}



public void setDistrictListItems(ArrayList<SelectItem> districtListItems) {
	this.districtListItems = districtListItems;
}

public void searchBranch(ActionEvent evt)
{
	  int cat=selectedCatBean.getId();
	  int sub= subCategoryBean.getId();
	  int  country= countryBean.getId();
	  int city= cityBean.getId();
	  int district= districtBean.getId();
	  int section= sectionBean.getId();
	  
	
	  
	  BranchService service=new BranchService();
	
	  AdvancedSearchCriteria criertia =new AdvancedSearchCriteria();
	  criertia.setCat_id(cat);
	  criertia.setSubcat_id(sub);
	  criertia.setSection_id(section);
	  criertia.setCountry_id(country);
	  criertia.setCity_id(city);
	  criertia.setDistrict_id(district);
	

	  if(specialistFlag )
	  {
		  criertia.setName(keyWordName);
		  criertia.setKeyword(null);
		  criertia.setMode("spec");
		  
		  if(gender!=null&&!gender.trim().equals(""))
		  {
			  criertia.setGender(Integer.parseInt(gender)) ;
		  }
		  else
		  {
			  criertia.setGender(0) ;
			  
		  }
		  
		String value=((HtmlSelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent(":contentForm:degreeMenu")).getValue().toString();
		  
		
		if(value!=null && !value.equals(""))
		{
			degree=new DegreeBean();
			degree.setId(Integer.parseInt(value));
			  criertia.setDegree_id(degree.getId()) ; 
		}
		
		  
		
		  
	  }
	
	  else 
	  {
		  criertia.setName(null);
		  criertia.setKeyword(keyWordName); 
		  criertia.setMode(null);
		
	  }
	  //criertia.setToday_id(dayTo.getId());
	  list=  (ArrayList<BranchBean>) service.advancedSearchForBranchesWithoutDimensions(criertia);
  

   
   
}

public String searchBySpec( )
{
	
	
	if(specialistFlag)
	{
		genderAppear=true;	
	}
	else
	{
		genderAppear=false;
	}
	
	
	return "";
}



ArrayList<BranchBean> list;
boolean genderAppear;


public SectionsBean getSectionBean() {
	return sectionBean;
}



public void setSectionBean(SectionsBean sectionBean) {
	this.sectionBean = sectionBean;
}



public DistricBean getDistrictBean() {
	return districtBean;
}



public void setDistrictBean(DistricBean districtBean) {
	this.districtBean = districtBean;
}



public ArrayList<BranchBean> getList() {
	return list;
}



public void setList(ArrayList<BranchBean> list) {
	this.list = list;
}



public ArrayList<DaysBean> getDaysList() {
	return daysList;
}



public void setDaysList(ArrayList<DaysBean> daysList) {
	this.daysList = daysList;
}



public ArrayList<SelectItem> getDaysListItems() {
	return daysfromListItems;
}



public void setDaysListItems(ArrayList<SelectItem> daysListItems) {
	this.daysfromListItems = daysListItems;
}



public DaysBean getDayFrom() {
	return dayFrom;
}



public void setDayFrom(DaysBean dayFrom) {
	this.dayFrom = dayFrom;
}



public DaysBean getDayTo() {
	return dayTo;
}



public void setDayTo(DaysBean dayTo) {
	this.dayTo = dayTo;
}



public ArrayList<SelectItem> getDaysToListItems() {
	return daysToListItems;
}



public void setDaysToListItems(ArrayList<SelectItem> daysToListItems) {
	this.daysToListItems = daysToListItems;
}



public ArrayList<SelectItem> getDaysfromListItems() {
	return daysfromListItems;
}



public void setDaysfromListItems(ArrayList<SelectItem> daysfromListItems) {
	this.daysfromListItems = daysfromListItems;
}



public ArrayList<SelectItem> getHoursfromListItems() {
	return hoursfromListItems;
}



public void setHoursfromListItems(ArrayList<SelectItem> hoursfromListItems) {
	this.hoursfromListItems = hoursfromListItems;
}



public ArrayList<SelectItem> getHoursToListItems() {
	return hoursToListItems;
}



public void setHoursToListItems(ArrayList<SelectItem> hoursToListItems) {
	this.hoursToListItems = hoursToListItems;
}



public ArrayList<HourBean> getHourList() {
	return hourList;
}



public void setHourList(ArrayList<HourBean> hourList) {
	this.hourList = hourList;
}



public boolean isSpecialistFlag() {
	return specialistFlag;
}



public void setSpecialistFlag(boolean specialistFlag) {
	this.specialistFlag = specialistFlag;
}



public String getGender() {
	return gender;
}



public void setGender(String gender) {
	this.gender = gender;
}



public boolean isGenderAppear() {
	return genderAppear;
}



public void setGenderAppear(boolean genderAppear) {
	this.genderAppear = genderAppear;
}
    
}
