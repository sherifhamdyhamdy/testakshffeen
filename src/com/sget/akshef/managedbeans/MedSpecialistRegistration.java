package com.sget.akshef.managedbeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import com.akshffeen.utils.Utils;
import com.sget.akshef.buisness.UserFactory;
import com.sget.akshef.hibernate.beans.CertificationBean;
import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.DegreeBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.MedicalhistoryBean;
import com.sget.akshef.hibernate.beans.ProfessionalExpBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.SpecialistHasBranchBean;
import com.sget.akshef.hibernate.beans.SubcategoryBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.CityService;
import com.sget.akshef.hibernate.service.CountryService;
import com.sget.akshef.hibernate.service.DistricService;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshef.utils.EmailValidator;
import com.sget.akshef.utils.Emailer;

//@ManagedBean(name="specialistControllerAdd")
//@ViewScoped
public class MedSpecialistRegistration {
	private Utils utils = Utils.getInstance();

	private SpecialistBean specialistBean = null;
	private UsersBean usersBean = null;
	private SpecialistHasBranchBean branchBean;
	HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	  CountryBean countryBean;
	   CityBean cityBean;
	   DistricBean districtBean;
	   DualListModel<SectionsBean> modelSection;
	   List<SectionsBean> specBeans=new ArrayList<SectionsBean>();
	   List<SectionsBean> target=new ArrayList<SectionsBean>();
	   CertificationBean certBean=new CertificationBean();
	   List<CertificationBean> certBeans=new ArrayList<CertificationBean>();
	   
	   ProfessionalExpBean profBean=new ProfessionalExpBean();
	   List<ProfessionalExpBean> profBeans=new ArrayList<ProfessionalExpBean>();
	  Emailer emailer= new Emailer();
	private SpecialistService specialistService = new SpecialistService();
	private boolean firstStep;
	private boolean secondStep;
	private boolean doctorStep;

	   UsersService userServ=new UsersService();
	
	
	public String registerUser()
	{
//		   if(usersBean.getPassword()==null||usersBean.getPassword().equals(""))
//		   {
//			   messageOutput="من فضللك أدخل كلمه المرور"; 
//			   return ""; 
//		   }
		   
		   if(usersBean.getEmail()==null||usersBean.getEmail().equals(""))
		   {
			   messageOutput="من فضللك أدخل البريد الأكترونى"; 
			   return ""; 
		   }
		   
		   if(userServ.checkUserInRegisterByUsername(usersBean.getUsername()))
		   {
			   messageOutput="هذا المستخدم موجود من قبل من فضلك اختر اسم مستخدم اخر"; 
			   return ""; 
		   }
		   if(userServ.checkUserInRegisterByEmail(usersBean.getEmail()))
		   {
			   messageOutput="هذا البريد  الالكترونى  موجود من قبل من فضلك اختر اخر"; 
			   return "";   
		   }
		   boolean valid = new EmailValidator().validate(usersBean.getEmail());
		   if(!valid)
		   {
			   messageOutput="من فضللك أدخل البريد الأكترونى صحيح"; 
			   return ""; 
		   }
		   usersBean.setActive(0);
		   usersBean.setUsername(usersBean.getEmail());
		   usersBean.setCreationDate(new java.util.Date());
		   userServ.insert(usersBean);
		   usersBean.setUserType(6);
			session.setAttribute("userBean",usersBean);
			secondStep=true;
			firstStep=false;
			doctorStep=false;
			messageOutput="";
			return "";
	}
	
	public String validateData(){
		
		
		
		
		 if(usersBean.getFirstName()==null||usersBean.getFirstName().equals(""))
		   {
			   messageOutput="من فضللك أدخل الاسم الاول"; 
			   return "";
		   }
		
		if(usersBean.getLastName()==null||usersBean.getLastName().equals(""))
		   {
			   messageOutput="من فضللك أدخل اسم العائلة"; 
			   return "";
		   }
		
		 
		 if(usersBean.getGender()==0)
			   {
				   messageOutput="من فضللك أدخل النوع"; 
				   return ""; 
			   }
		 
//		 if(usersBean.getBirthdate()==null ||usersBean.getBirthdate().getTime()==0.0)
//		   {
//			   messageOutput="من فضللك أدخل تاريخ الميلاد"; 
//			   return ""; 
//		   }
		
		
		
		
		if(usersBean.getMobile()==null||usersBean.getMobile().equals(""))
		   {
			   messageOutput="من فضللك أدخل  رقم الهاتف"; 
			   return "";
		   }
		if(usersBean.getMarital_stat()==null||usersBean.getMarital_stat().equals(""))
		   {
			   messageOutput="من فضللك أدخل الحالة الاجتماعية  "; 
			   return "";
		   }
		
		
		
		
		   
		   messageOutput="";
		   
		   
		   return"update";
	}
	
	public String goBackward()
	{
	    firstStep=true;
	    secondStep=false;
	    doctorStep=false;
	    return "";
	}
	
	
	
	public String goBackwardFromSpec()
	{
	    firstStep=false;
	    secondStep=true;
	    doctorStep=false;
	    return "";
	}
	
	
	
	public  String updateUser()
	{
		
		
		String status=validateData();
		if(status==null || status.equals(""))
		return "";
		usersBean.setActive(1);
		boolean isupdated=userServ.update(usersBean);
		if(isupdated)
		{
			
			usersBean=userServ.getById(usersBean.getId());
			String msg=usersBean.getUsername();
			emailer.sendMail(usersBean.getEmail(),  msg);
			StringBuffer baseURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL();
			String path=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletPath();
		     String	url=utils.getXMLkey("AkshffeenAdmin");
			emailer.sendMailForVerification(usersBean.getEmail(), msg,usersBean.getId(),usersBean.getEmailCount(),url);
		    firstStep=false;
		    secondStep=false;
		    doctorStep=false;
		   try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("akshf_login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		}
		
		return "";
	}
	
	public String completeAsSpecialist()
	{
		   firstStep=false;
		    secondStep=false;
		    doctorStep=true;
		
		return "";
	}
	
	
	
	
	
	
	  public String insertUser()
	   {
//		   if(!usersBean.getPassword().trim().equals(usersBean.getRepassword()))
//		   {
//			   messageOutput="كلمه المرور غير متاطبقه"; 
//			   return "";
//		   }
//		   else
//		   {
//			   messageOutput="";
//		   }
		   
//		   if(usersBean.getUsername()==null||usersBean.getUsername().equals(""))
//		   {
//			   messageOutput="من فضللك أدخل أسم المستخدم"; 
//			   return "";
//		   }
//		   if(usersBean.getPassword()==null||usersBean.getPassword().equals(""))
//		   {
//			   messageOutput="من فضللك أدخل كلمه المرور"; 
//			   return ""; 
//		   }
//		   if(usersBean.getRepassword()==null||usersBean.getRepassword().equals(""))
//		   {
//			   messageOutput="من فضللك أعد كتابه كلمه المرور"; 
//			   return ""; 
//		   }
		   
		   if(usersBean.getEmail()==null||usersBean.getEmail().equals(""))
		   {
			   messageOutput="من فضللك أدخل البريد الأكترونى"; 
			   return ""; 
		   }
//		   if(usersBean.getBirthdate()==null ||usersBean.getBirthdate().getTime()==0.0)
//		   {
//			   messageOutput="من فضللك أدخل تاريخ الميلاد"; 
//			   return ""; 
//		   }
//		   if(usersBean.getGender()==0)
//		   {
//			   messageOutput="من فضللك أدخل النوع"; 
//			   return ""; 
//		   }
		   
		   UsersService serv=new UsersService();
		   if(serv.checkUserInRegisterByUsername(usersBean.getUsername()))
		   {
			   messageOutput="هذا المستخدم موجود من قبل من فضلك اختر اسم مستخدم اخر"; 
			   return ""; 
		   }
		   if(serv.checkUserInRegisterByEmail(usersBean.getEmail()))
		   {
			   messageOutput="هذا البريد  الالكترونى  موجود من قبل من فضلك اختر اخر"; 
			   return "";   
		   }
		   boolean valid = new EmailValidator().validate(usersBean.getEmail());
		   if(!valid)
		   {
			   messageOutput="من فضللك أدخل البريد الأكترونى صحيح"; 
			   return ""; 
		   }
		   usersBean.setActive(0);
		   usersBean.setActive_email(0);
//		   usersBean.setPassword(usersBean.getEmail());
		   usersBean.setCreationDate(new java.util.Date());
		   usersBean.setEmailCount(1);
		   serv.insert(usersBean);
		   String msg=usersBean.getUsername();
		   usersBean.setUserType(6);
			session.setAttribute("userBean",usersBean);
			// System.out.println("usersBean.getId()    ===   " + usersBean.getId());
			messageOutput=" مرحبا"+msg+"  لقد تم تسجيلك  بنجاح من فضلك أذهب للحساب الخاص بك";
			emailer.sendMail(usersBean.getEmail(),  msg);
			StringBuffer baseURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL();
			String path=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletPath();
		     String	url=utils.getXMLkey("AkshffeenAdmin");
			emailer.sendMailForVerification(usersBean.getEmail(), msg,usersBean.getId(),usersBean.getEmailCount(),url);
		   
		  // usersBean=new UsersBean();
		   buttonAppeared=true;
		   try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("akshf_login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   return "";
	   }

//	public String pobulateDegree()
//	 {
//		List<DegreeBean> degreeBeans=specialistService.getAllDegree();
//	      degreeListItems=new ArrayList<SelectItem>();
//	      degreeListItems.add(new SelectItem(" "," ")); 
//	      for(DegreeBean bean:degreeBeans)
//	      {
//	    	  degreeListItems.add(new SelectItem(String.valueOf(bean.getId()),
//	    	  bean.getDegree()));
//	      }
//	       
//	   return "";
//	    
//	    
//	 }
	
	
	public String pobulateSections()
	 {
		
		 specBeans=specialistService.getAllSpecialistsCategory();
	      sectionsListItems=new ArrayList<SelectItem>();
	      sectionsListItems.add(new SelectItem(" "," ")); 
	      for(SectionsBean bean:specBeans)
	      {
	    	  sectionsListItems.add(new SelectItem(String.valueOf(bean.getId()),
	    	  bean.getNameAr()));
	      }
	       
	      
	      

	   
	   return "";
	    
	    
	 }
	
	

	public MedSpecialistRegistration() {
	
		 
		 
		
	}
	
	
	public String loadInitialData()
	{
		specialistBean = new SpecialistBean();
		 usersBean = new UsersBean();
		 branchBean=new SpecialistHasBranchBean();
		 countryBean=new CountryBean();
		 cityBean=new CityBean();
		 districtBean=new DistricBean();
		 buttonAppeared=false;
		 firstStep=true;
		 secondStep=false;
		 doctorStep=false;
//		 pobulateDegree();
		 pobulateSections();
		 return "";
	}

	// specialistControllerAddService
	String messageOutput="";
	public String saveSpecialist() {
		   usersBean.setActive(1);
		   usersBean.setActive_email(0);
		   usersBean.setCreationDate(new java.util.Date());
		   usersBean.setEmailCount(1);
		   userServ.update(usersBean);
		   String msg=usersBean.getUsername();
		   
		specialistBean.setName(usersBean.getFirstName()+usersBean.getLastName());
		specialistBean.setUsers(usersBean);
		specialistBean.setCertList(certBeans);
		specialistBean.setProfList(profBeans);
		specialistBean.setSectionBeans(target);
  
		
		specialistService.regitserSpecialist(specialistBean);
		usersBean.setUserType(2);
		session.setAttribute("userBean",usersBean);
		
		// System.out.println("usersBean.getId()    ===   " + usersBean.getId());
		messageOutput=" مرحبا"+msg+"  لقد تم تسجيلك  بنجاح من فضلك أذهب للحساب الخاص بك";
	   emailer.sendMail(usersBean.getEmail(),  msg);
	   StringBuffer baseURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL();
		String path=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletPath();
	     String	url=utils.getXMLkey("AkshffeenAdmin");
	   emailer.sendMailForVerification(usersBean.getEmail(), msg,usersBean.getId(),usersBean.getEmailCount(),url);
	  // usersBean=new UsersBean();
	   try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("akshf_login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public String clearAllData()
	{
		specialistBean = new SpecialistBean();
		return "";
	}
	public String redirectToProfile()
	{
		try {
		


			
			FacesContext.getCurrentInstance().getExternalContext().redirect("akshf_login.xhtml");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	
	public void addCert(ActionEvent evt)
	{
		
		Object btnName=((org.primefaces.component.commandbutton.CommandButton)evt.getSource()).getValue();
		
		
		if(btnName!=null &&btnName.equals("addCertBtn"))
		{
			CertificationBean _certBean=new CertificationBean();
			try {
				_certBean = (CertificationBean) certBean.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			certBeans.add(_certBean);
			
		}
		else if(btnName!=null && btnName.equals("addProfBtn"))
		{
			ProfessionalExpBean _profBean=new ProfessionalExpBean();
			try {
				_profBean = (ProfessionalExpBean) profBean.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			profBeans.add(_profBean);
		}
		
		
	}
	
	
	public void addProfessionalData(ActionEvent evt)
	{
		ProfessionalExpBean _profBean=new ProfessionalExpBean();
		try {
			_profBean = (ProfessionalExpBean) profBean.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		profBeans.add(_profBean);
	}
	
    ArrayList<CountryBean> countryList;
    ArrayList<DistricBean> districtList;
    ArrayList<CityBean> cityList;
    private ArrayList<SelectItem> countryListItems;	
    private ArrayList<SelectItem> cityListItems;	
    private ArrayList<SelectItem> districtListItems;
    private ArrayList<SelectItem> sectionsListItems;
    private ArrayList<SelectItem> degreeListItems;
	
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
	    
	    

	public SpecialistBean getSpecialistBean() {
		return specialistBean;
	}

	public void setSpecialistBean(SpecialistBean specialistBean) {
		this.specialistBean = specialistBean;
	}

	public UsersBean getUsersBean() {
		return usersBean;
	}

	public void setUsersBean(UsersBean usersBean) {
		this.usersBean = usersBean;
	}

	public SpecialistHasBranchBean getBranchBean() {
		return branchBean;
	}

	public void setBranchBean(SpecialistHasBranchBean branchBean) {
		this.branchBean = branchBean;
	}

	public SpecialistService getSpecialistService() {
		return specialistService;
	}

	public void setSpecialistService(SpecialistService specialistService) {
		this.specialistService = specialistService;
	}

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

	
	
	

	public List<SectionsBean> getTarget() {
		return target;
	}

	public void setTarget(List<SectionsBean> target) {
		this.target = target;
	}

	

	public void setModelSection(DualListModel<SectionsBean> modelSection) {
		this.modelSection = modelSection;
	}

	public List<SectionsBean> getSpecBeans() {
		return specBeans;
	}

	public void setSpecBeans(List<SectionsBean> specBeans) {
		this.specBeans = specBeans;
	}

	public DualListModel<SectionsBean> getModelSection() {
		return modelSection;
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


	public CertificationBean getCertBean() {
		return certBean;
	}


	public void setCertBean(CertificationBean certBean) {
		this.certBean = certBean;
	}


	public List<CertificationBean> getCertBeans() {
		return certBeans;
	}


	public void setCertBeans(List<CertificationBean> certBeans) {
		this.certBeans = certBeans;
	}


	public ProfessionalExpBean getProfBean() {
		return profBean;
	}


	public void setProfBean(ProfessionalExpBean profBean) {
		this.profBean = profBean;
	}


	public List<ProfessionalExpBean> getProfBeans() {
		return profBeans;
	}


	public void setProfBeans(List<ProfessionalExpBean> profBeans) {
		this.profBeans = profBeans;
	}

	public String getMessageOutput() {
		return messageOutput;
	}

	public void setMessageOutput(String messageOutput) {
		this.messageOutput = messageOutput;
	}

	public boolean buttonAppeared;
	public boolean isButtonAppeared() {
		return buttonAppeared;
	}

	public void setButtonAppeared(boolean buttonAppeared) {
		this.buttonAppeared = buttonAppeared;
	}




	public boolean isFirstStep() {
		return firstStep;
	}




	public void setFirstStep(boolean firstStep) {
		this.firstStep = firstStep;
	}




	public boolean isSecondStep() {
		return secondStep;
	}




	public void setSecondStep(boolean secondStep) {
		this.secondStep = secondStep;
	}




	public boolean isDoctorStep() {
		return doctorStep;
	}




	public void setDoctorStep(boolean doctorStep) {
		this.doctorStep = doctorStep;
	}





}
