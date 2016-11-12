package com.sget.akshef.managedbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.model.DualListModel;

import com.sget.akshef.hibernate.beans.CertificationBean;
import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CompanyBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.DegreeBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.InsuranceCompanyBean;
import com.sget.akshef.hibernate.beans.MedicalInstrumentsBean;
import com.sget.akshef.hibernate.beans.ProfessionalExpBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.SpecialistHasBranchBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.CityService;
import com.sget.akshef.hibernate.service.CompanyService;
import com.sget.akshef.hibernate.service.CountryService;
import com.sget.akshef.hibernate.service.DistricService;
import com.sget.akshef.hibernate.service.InsuranceCompanyService;
import com.sget.akshef.hibernate.service.MedicalInstrumentsService;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshef.hibernate.service.UsersService;

//@ManagedBean(name="specialistControllerAdd")
//@ViewScoped
public class MedCompanyRegistration {
	
	private CompanyBean companyBean;
	
	

	  CountryBean countryBean;
	   CityBean cityBean;
	   DistricBean districtBean;
	   
	 
	   
	private CompanyService compService = new CompanyService();



	public MedCompanyRegistration() {
		
		companyBean=new CompanyBean();
		inCompBean=new InsuranceCompanyBean();
		 countryBean=new CountryBean();
		 cityBean=new CityBean();
		 districtBean=new DistricBean();
		pobulateCountry();
		pobulateInsuranceCompany();
		 
		
	}

	// specialistControllerAddService
	public void saveCompany(ActionEvent action) {
		companyBean.setInBean(inCompBean);
		companyBean.setDistric(districtBean);
		compService.insert(companyBean);

	}
	
	
    ArrayList<CountryBean> countryList;
    ArrayList<DistricBean> districtList;
    ArrayList<CityBean> cityList;
    private ArrayList<SelectItem> countryListItems;	
    private ArrayList<SelectItem> cityListItems;	
    private ArrayList<SelectItem> districtListItems;
    private ArrayList<SelectItem> sectionsListItems;
    private ArrayList<SelectItem> degreeListItems;
    
    private ArrayList<SelectItem> medInsuranceListItems;
    private List<InsuranceCompanyBean> medicalInsuranceList;
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
//	      if(cityListItems.size()>0)
//	      {
//	      String valueCity=((SelectItem) cityListItems.get(0)).getValue().toString();
//		  cityBean.setId(Integer.parseInt(valueCity));
//	      }
//	      else
//	      {
//	    	  cityBean.setId(0);
//	      }
	      

	   
	   return "";
	    
	    
	 }
	 
	 
	 public String pobulateDistrict()
	 {
		 DistricService service=new DistricService();		 
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
	    
	 public String pobulateInsuranceCompany()
	 {
		 InsuranceCompanyService service=new InsuranceCompanyService();
		 medicalInsuranceList =(ArrayList<InsuranceCompanyBean>) service.getAll();
	      medInsuranceListItems=new ArrayList<SelectItem>();
	      medInsuranceListItems.add(new SelectItem(" "," ")); 
	      for(InsuranceCompanyBean bean:medicalInsuranceList)
	      {
	    	  medInsuranceListItems.add(new SelectItem(String.valueOf(bean.getId()),
	    	  bean.getNameAr()));
	      }
	      

	   
	   return "";
	    
	    
	 }
	 
	 InsuranceCompanyBean inCompBean; 


	public ArrayList<CountryBean> getCountryList() {
		return countryList;
	}

	public void setCountryList(ArrayList<CountryBean> countryList) {
		this.countryList = countryList;
	}

	public ArrayList<DistricBean> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(ArrayList<DistricBean> districtList) {
		this.districtList = districtList;
	}

	public ArrayList<SelectItem> getCountryListItems() {
		return countryListItems;
	}

	public void setCountryListItems(ArrayList<SelectItem> countryListItems) {
		this.countryListItems = countryListItems;
	}

	public ArrayList<SelectItem> getCityListItems() {
		return cityListItems;
	}

	public void setCityListItems(ArrayList<SelectItem> cityListItems) {
		this.cityListItems = cityListItems;
	}

	public ArrayList<SelectItem> getDistrictListItems() {
		return districtListItems;
	}

	public void setDistrictListItems(ArrayList<SelectItem> districtListItems) {
		this.districtListItems = districtListItems;
	}

	public CountryBean getCountryBean() {
		return countryBean;
	}

	public void setCountryBean(CountryBean countryBean) {
		this.countryBean = countryBean;
	}

	public CityBean getCityBean() {
		return cityBean;
	}

	public void setCityBean(CityBean cityBean) {
		this.cityBean = cityBean;
	}

	public ArrayList<CityBean> getCityList() {
		return cityList;
	}

	public void setCityList(ArrayList<CityBean> cityList) {
		this.cityList = cityList;
	}

	public DistricBean getDistrictBean() {
		return districtBean;
	}

	public void setDistrictBean(DistricBean districtBean) {
		this.districtBean = districtBean;
	}

	


	public ArrayList<SelectItem> getSectionsListItems() {
		return sectionsListItems;
	}


	public void setSectionsListItems(ArrayList<SelectItem> sectionsListItems) {
		this.sectionsListItems = sectionsListItems;
	}


	public ArrayList<SelectItem> getDegreeListItems() {
		return degreeListItems;
	}


	public void setDegreeListItems(ArrayList<SelectItem> degreeListItems) {
		this.degreeListItems = degreeListItems;
	}




	public CompanyBean getCompanyBean() {
		return companyBean;
	}


	public void setCompanyBean(CompanyBean companyBean) {
		this.companyBean = companyBean;
	}

	public ArrayList<SelectItem> getMedInsuranceListItems() {
		return medInsuranceListItems;
	}

	public void setMedInsuranceListItems(ArrayList<SelectItem> medInsuranceListItems) {
		this.medInsuranceListItems = medInsuranceListItems;
	}

	public List<InsuranceCompanyBean> getMedicalInsuranceList() {
		return medicalInsuranceList;
	}

	public void setMedicalInsuranceList(List<InsuranceCompanyBean> medicalInsuranceList) {
		this.medicalInsuranceList = medicalInsuranceList;
	}

	public InsuranceCompanyBean getInCompBean() {
		return inCompBean;
	}

	public void setInCompBean(InsuranceCompanyBean inCompBean) {
		this.inCompBean = inCompBean;
	}





}
