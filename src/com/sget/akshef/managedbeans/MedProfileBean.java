package com.sget.akshef.managedbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.CertificationBean;
import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.DegreeBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.MedicalhistoryBean;
import com.sget.akshef.hibernate.beans.MessagesBean;
import com.sget.akshef.hibernate.beans.ProfessionalExpBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.UserFavoritiesForMobile;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.AppUtil;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.CityService;
import com.sget.akshef.hibernate.service.CountryService;
import com.sget.akshef.hibernate.service.DistricService;
import com.sget.akshef.hibernate.service.MessagesService;
import com.sget.akshef.hibernate.service.SpecialistService;
import com.sget.akshef.hibernate.service.UserFavoritiesService;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshef.utils.EmailValidator;
import com.sget.akshef.utils.EncryptUtils;

public class MedProfileBean implements Serializable {
	private Utils utils = Utils.getInstance();

	UsersBean user;
	boolean endUserViewMode;
	boolean endUserUpdateMode;
	boolean specialistViewMode;
	boolean specialistUpdateMode;
	String userLogoImage;
	String userLogoImageView;
	String specialistLogoImage;
	String specialistLogoImageView;
	
	public String getUserLogoImageView() {
		return userLogoImageView;
	}


	public void setUserLogoImageView(String userLogoImageView) {
		this.userLogoImageView = userLogoImageView;
	}


	public String getUserLogoImage() {
		return userLogoImage;
	}


	public void setUserLogoImage(String userLogoImage) {
		this.userLogoImage = userLogoImage;
	}

	boolean messageAppeared;
	boolean messagebranchAppear;
	boolean messageSpecilistAppear;
	 CertificationBean certBean=new CertificationBean();
	 CertificationBean certBeanUser=new CertificationBean();
	 
	 
	 MedicalhistoryBean medhistBean=new MedicalhistoryBean();
	 
	  CountryBean countryBean=new CountryBean();
	   CityBean cityBean=new CityBean();
	   DistricBean districtBean=new DistricBean();
	   UsersService userService=new UsersService();
	   SpecialistService specService=new SpecialistService();
	   SpecialistBean specBean=new SpecialistBean();
	   List<CertificationBean> certBeans=new ArrayList<CertificationBean>();
	   List<CertificationBean> certBeansUpdate=new ArrayList<CertificationBean>();
	   
	   List<CertificationBean> certBeansUser=new ArrayList<CertificationBean>();
	   List<CertificationBean> certBeansUpdateUser=new ArrayList<CertificationBean>();
	   
	   ProfessionalExpBean profBean=new ProfessionalExpBean();
	   List<ProfessionalExpBean> profBeans=new ArrayList<ProfessionalExpBean>();
	   List<ProfessionalExpBean> profBeansUpdate=new ArrayList<ProfessionalExpBean>();
	   
	   
	   ProfessionalExpBean profBeanUser=new ProfessionalExpBean();
	   List<ProfessionalExpBean> profBeansUser=new ArrayList<ProfessionalExpBean>();
	   List<ProfessionalExpBean> profBeansUpdateUser=new ArrayList<ProfessionalExpBean>();
	   
	   
	   List<MedicalhistoryBean> medicalhistory=new ArrayList<MedicalhistoryBean>();
	   List<MedicalhistoryBean> medicalhistoryupdate=new ArrayList<MedicalhistoryBean>();
		public void addCert(ActionEvent evt)
		{
			
			Object btnName=((org.primefaces.component.commandbutton.CommandButton)evt.getSource()).getValue();
			
			
			if(btnName!=null &&btnName.equals("addCertBtn"))
			{
				CertificationBean _certBean=new CertificationBean();
				 System.out.println("addCert");
				try {
					_certBean = (CertificationBean) certBean.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				certBeans.add(_certBean);
				certBeansUpdate.add(_certBean);
				
			}
			
			else 	if(btnName!=null &&btnName.equals("addCertBtnSpec"))
			{
				CertificationBean _certBean=new CertificationBean();
				 System.out.println("addCertBtnSpec");
				try {
					_certBean = (CertificationBean) certBeanUser.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				certBeansUser.add(_certBean);
				certBeansUpdateUser.add(_certBean);
			}
			
			
			
			else if(btnName!=null && btnName.equals("addProfBtn"))
			{
				ProfessionalExpBean _profBean=new ProfessionalExpBean();
				 System.out.println("addProf");
				try {
					_profBean = (ProfessionalExpBean) profBeanUser.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				profBeansUser.add(_profBean);
				profBeansUpdateUser.add(_profBean);
			}
			
			
			
			
			
			else if(btnName!=null && btnName.equals("addProfBtnSpec"))
			{
				ProfessionalExpBean _profBean=new ProfessionalExpBean();
				 System.out.println("addProfBtnSpec");
				try {
					_profBean = (ProfessionalExpBean) profBeanUser.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				profBeansUser.add(_profBean);
				profBeansUpdateUser.add(_profBean);
			}
			
			else if(btnName!=null && btnName.equals("addMedBtn"))
					{
				
				MedicalhistoryBean _medbean=new MedicalhistoryBean();
				 System.out.println("addProf");
				try {
					_medbean = (MedicalhistoryBean) medhistBean.clone();
					_medbean.setUsers(user);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				medicalhistory.add(_medbean);
				medicalhistoryupdate.add(_medbean);
				
					}
			
			
		}
	
		
		public void addProfessionalData(ActionEvent evt)
		{
			ProfessionalExpBean _profBean=new ProfessionalExpBean();
			 System.out.println("addProf");
			try {
				_profBean = (ProfessionalExpBean) profBean.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			profBeans.add(_profBean);
		}
		UserFavoritiesForMobile favorites=new UserFavoritiesForMobile() ;
		UserFavoritiesService userfavservice=new UserFavoritiesService();
		MessagesService messagesService=new MessagesService();
		List<MessagesBean> messageBeans=new ArrayList<MessagesBean>();
		List<MessagesBean> messageBeansbranches=new ArrayList<MessagesBean>();
		List<MessagesBean> messageBeansSpecialist=new ArrayList<MessagesBean>();
	boolean activatedMail;
	String activeMailSt;
	boolean activatedMailSpec;
public List<MessagesBean> getMessageBeans() {
			return messageBeans;
		}


		public void setMessageBeans(List<MessagesBean> messageBeans) {
			this.messageBeans = messageBeans;
		}


public	MedProfileBean()
	{
//	HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//	messageBeans=new ArrayList<MessagesBean>();
//	
//	
//	
//	user=	(UsersBean) session.getAttribute("userBean");
//			   switch (user.getUserType()) {
//	            case 2:  
//	            	{specialistViewMode = true;specialistUpdateMode=false;
//	            	specBean=	specService.getSpecialistByUserID(user.getId());
//	            	messageBeans=specService.getSpecialistMessages(user.getId());
//	            	messageAppeared=true;
//	            	
//	            	
//	            	
//	            	profBeans=new ArrayList<ProfessionalExpBean>();
//	            	certBeans=new ArrayList<CertificationBean>();
//	            	target=new ArrayList<SectionsBean>();
//	            	profBeans.addAll(specBean.getProfList());
//	            	certBeans.addAll(specBean.getCertList());
//	            	target.addAll(specBean.getSectionBeans());
//	            	if(specBean==null)
//	            	{
//	            		specBean=new SpecialistBean();	
//	            	}
//	            	}
//	            	
//	                     break;
//	            case 6:  { specialistViewMode = false; endUserViewMode=true;
//	            //messageBeans=messagesService. getAll();
//			if (user.isAdmin()) {
//				messageAppeared = false;
//				messageBeansbranches = specService.getMessagesBranch();
//				if (messageBeansbranches != null && !messageBeansbranches.isEmpty()) {
//					messagebranchAppear = true;
//				} else {
//					messagebranchAppear = false;
//				}
//				messageBeansSpecialist = specService.getMessagesSpecialist();
//				if (messageBeansSpecialist != null && !messageBeansSpecialist.isEmpty()) {
//					messageSpecilistAppear = true;
//				} else {
//					messageSpecilistAppear = false;
//				}
//
//			} else {
//				messageAppeared = false;
//			}
//
//		}
//			break;
//		}
//			    System.out.println("user profile link    ----  "+user.getProfile_img());
//			   
//			   pobulateCountry();
			
	}
DistricService distService=new DistricService();

public String loadData()
{
	HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	messageBeans=new ArrayList<MessagesBean>();
	   pobulateCountry();	
	user=	(UsersBean) session.getAttribute("userBean");
	
	
	  if(	user.getActive_email()==0)
        {
        	activatedMail=true;
        	   activeMailSt="0";
        	
        }
        else
        {
        	activatedMail=false;
        	   activeMailSt="1";
        }
      //messageBeans=messagesService. getAll();
      medicalhistoryupdate=new ArrayList<MedicalhistoryBean>();
      medicalhistory=new ArrayList<MedicalhistoryBean>();
      if(user.getMedicalHist()!=null)
      medicalhistory.addAll(user.getMedicalHist());
	  favorites=userfavservice.getAllForMobile(user.getId(), "");
	
			   switch (user.getUserType()) {
	            case 2: //specialist 
	            	{
	            	specialistViewMode = true;specialistUpdateMode=false;
//	            	
	            	specBean=	specService.getSpecialistByUserID(user.getId());
	            	messageBeans=specService.getSpecialistMessages(user.getId());
	            	messageAppeared=true;
	  
	            	sectionsBeans.addAll(specBean.getSectionBeans());
	            	if(specBean==null)
	            	{
	            		specBean=new SpecialistBean();	
	            	}
	            	}
	            	
	              break;
	            case 6:  { 
	            	specialistViewMode = false; endUserViewMode=true;
	        	
	     
			if (user.isAdmin()) {
				messageAppeared = false;
				messageBeansbranches = specService.getMessagesBranch();
				if (messageBeansbranches != null && !messageBeansbranches.isEmpty()) {
					messagebranchAppear = true;
				} else {
					messagebranchAppear = false;
				}
				messageBeansSpecialist = specService.getMessagesSpecialist();
				if (messageBeansSpecialist != null && !messageBeansSpecialist.isEmpty()) {
					messageSpecilistAppear = true;
				} else {
					messageSpecilistAppear = false;
				}

			} else {
				messageAppeared = false;
			}

		}
			break;
		}
			    System.out.println("user profile link    ----  "+user.getProfile_img());
			   
			
	return "";
	
}

public String activateAccount()
{
	StringBuffer baseURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL();
	String path=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletPath();
     String	url=utils.getXMLkey("AkshffeenAdmin");
	url+="/CodeVerification?CODE="+new EncryptUtils().encrypt(user.getId()+"")+"&emailCount="+new EncryptUtils().encrypt(user.getEmailCount()+"");

	
try {
	FacesContext.getCurrentInstance().getExternalContext().redirect(url);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
	return "";
}



public String sendForgotPassword(UsersBean userbean)
{
	StringBuffer baseURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL();
	String path=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletPath();
     String	url=utils.getXMLkey("AkshffeenAdmin");
     int email_count=0;
     if(userbean.getPass_emailCount()!=0)
     {
    	 email_count=userbean.getPass_emailCount();
     }
  
	url+="/ForgotPassword?CODE="+new EncryptUtils().encrypt(userbean.getId()+"")+"&emailCount="+new EncryptUtils().encrypt(email_count+"");

	
try {
	FacesContext.getCurrentInstance().getExternalContext().redirect(url);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
	return "";
}



String messageOutput;
public String validateData(){
	
	
	
	
	 if(user.getFirstName()==null||user.getFirstName().equals(""))
	   {
		   messageOutput="من فضللك أدخل الاسم"; 
		   return "";
	   }
	
	if(user.getLastName()==null||user.getLastName().equals(""))
	   {
		   messageOutput="من فضللك أدخل اللقب"; 
		   return "";
	   }
	
	if(user.getNationalId()==null||user.getNationalId().equals(""))
	   {
		   messageOutput="من فضللك أدخل الرقم القومى"; 
		   return "";
	   }
	
	if(user.getAddress()==null||user.getAddress().equals(""))
	   {
		   messageOutput="من فضللك أدخل العنوان"; 
		   return "";
	   }
	
	if(user.getTelephone()==null||user.getTelephone().equals(""))
	   {
		   messageOutput="من فضللك أدخل رقم التليفون"; 
		   return "";
	   }
	
	if(user.getMobile()==null||user.getMobile().equals(""))
	   {
		   messageOutput="من فضللك أدخل رقم الموبايل"; 
		   return "";
	   }
	if(user.getEmergency_contact()==null||user.getEmergency_contact().equals(""))
	   {
		   messageOutput="من فضللك أدخل رقم الطوارى "; 
		   return "";
	   }
	
	
//	if(districtBean.getId()==0||countryBean.getId()==0|| cityBean.getId()==0)
//	   {
//		   messageOutput="من فضللك أدخل المنطقه"; 
//		   return "";
//	   }
	
	
	
//	   if(!user.getPassword().trim().equals(user.getRepassword()))
//	   {
//		   messageOutput="كلمه المرور غير متاطبقه"; 
//		   return "";
//	   }
	  
	   
	   if(user.getUsername()==null||user.getUsername().equals(""))
	   {
		   messageOutput="من فضللك أدخل أسم المستخدم"; 
		   return "";
	   }
//	   if(user.getPassword()==null||user.getPassword().equals(""))
//	   {
//		   messageOutput="من فضللك أدخل كلمه المرور"; 
//		   return ""; 
//	   }
//	   if(user.getRepassword()==null||user.getRepassword().equals(""))
//	   {
//		   messageOutput="من فضللك أعد كتابه كلمه المرور"; 
//		   return ""; 
//	   }
	   
	   if(user.getEmail()==null||user.getEmail().equals(""))
	   {
		   messageOutput="من فضللك أدخل البريد الأكترونى"; 
		   return ""; 
	   }
//	   if(user.getBirthdate()==null ||user.getBirthdate().getTime()==0.0)
//	   {
//		   messageOutput="من فضللك أدخل تاريخ الميلاد"; 
//		   return ""; 
//	   }
	   if(user.getGender()==0)
	   {
		   messageOutput="من فضللك أدخل النوع"; 
		   return ""; 
	   }
	   
	
	   boolean valid = new EmailValidator().validate(user.getEmail());
	   if(!valid)
	   {
		   messageOutput="من فضللك أدخل البريد الأكترونى صحيح"; 
		   return ""; 
	   }
	   
	   messageOutput="";
	   
	   
	   return"update";
}
public String pobulatePersonalData()
{
	  favorites=userfavservice.getAllForMobile(user.getId(), "");
	  return "";
}
  public String pobulateUserBean()
   {
	   System.out.println("pobulateUserBean");
	   HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	   user=	(UsersBean) session.getAttribute("userBean");   
	   Enumeration<String> enum1= session.getAttributeNames();
	   while(enum1.hasMoreElements())
	   {
		 String name=  enum1.nextElement();  
		  System.out.println("attributes    ----  "+name);
	   }
	   
	return "";   
   }
  List<SectionsBean> specBeans=new ArrayList<SectionsBean>();
  List<SectionsBean> sectionsBeans=new ArrayList<SectionsBean>();
  private ArrayList<SelectItem> countryListItems;	
  private ArrayList<SelectItem> cityListItems;	
  private ArrayList<SelectItem> districtListItems;
  ArrayList<CountryBean> countryList;
  ArrayList<DistricBean> districtList;
  ArrayList<CityBean> cityList;
  
 public String changeMode()
  {
	endUserViewMode=false;  
	endUserUpdateMode=true;  
	specialistUpdateMode=false;
	specialistViewMode=false;
	  
	return "";  
  }
 
 private ArrayList<SelectItem> sectionsListItems=new ArrayList<SelectItem>();
 private ArrayList<SelectItem> degreeListItems=new ArrayList<SelectItem>();
 
 public String changeModeSpecialist()
 {
	 endUserViewMode=false;  
		endUserUpdateMode=false; 
		specialistUpdateMode=true;
		specialistViewMode=false;
		pobulateSections();
//		pobulateDegree();
	 	
		return "";
 }
  
 
//	public String pobulateDegree()
//	 {
//		
//		List<DegreeBean> degreeBeans=specService.getAllDegree();
//	       System.out.println("degreeBeans  "+degreeBeans);
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
		
		 specBeans=specService.getAllSpecialistsCategory();
	       System.out.println("specBeans  "+specBeans);
	      sectionsListItems=new ArrayList<SelectItem>();
	      sectionsListItems.add(new SelectItem(" "," ")); 
	      for(SectionsBean bean:specBeans)
	      {
	    	  sectionsListItems.add(new SelectItem(String.valueOf(bean.getId()),
	    	  bean.getNameAr()));
	      }
	       
	      
	      

	   
	   return "";
	    
	    
	 }
 
	public String pobulateCountry()
	 {
		  System.out.println("action is here");
		 CountryService service=new CountryService();
		 countryList =(ArrayList<CountryBean>) service.getAll();
	       System.out.println("countryList  "+countryList);
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
		  System.out.println("action is here");
		 CityService service=new CityService();
		  System.out.println("country  id "+countryBean.getId());
		 cityList =(ArrayList<CityBean>) service.getAllCitiesByCountry(countryBean.getId());
	       System.out.println("cityList  "+cityList);
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
		  System.out.println("action is here");
		 DistricService service=new DistricService();		 
		 districtList =(ArrayList<DistricBean>) service.getAllDistrictByCity(cityBean.getId());
	       System.out.println("districtList  "+districtList);
	      districtListItems=new ArrayList<SelectItem>();
	      districtListItems.add(new SelectItem(" "," ")); 
	      
	      for(DistricBean bean:districtList)
	      {
	    	  districtListItems.add(new SelectItem(String.valueOf(bean.getId()),
	    	  bean.getNameAr()));
	      }
	       
	      
	      

	   
	   return "";
	 }
	    
 
	 public void UploadUserPhoto(FileUploadEvent event){

		  UploadedFile file = event.getFile();
	        userLogoImage = new AppUtil().uploadImage(file,DBConstants.USERS_IMAGES_UPLOADS);
	        System.out.println("userLogoImage   ---  "+userLogoImage);
	        userLogoImageView=DBConstants.USERS_IMAGES_UPLOADS+userLogoImage;
	        
	        
//	        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
//	        FacesContext.getCurrentInstance().addMessage(null, msg);
//	        // Do what you want with the file        
//	        try {
//	           userLogoImage=  copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
//	           userLogoImageView=DBConstants.USERS_IMAGES_UPLOADS+userLogoImage;
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
	        
	        
	      
	        
	       
	    }
	 
	  public String copyFile(String fileName, InputStream in) {
          try {
             
             
               // write the inputStream to a FileOutputStream
       	   ServletContext cont=  (ServletContext)  FacesContext.getCurrentInstance().getExternalContext().getContext();
      		String realPath=cont.getRealPath("images/users");
      		System.out.println("real path   --- "+realPath);
      	String	destination=realPath;
       	   
       	   File theDir = new File(realPath); 
       	   if (!theDir.exists())
       	   {
       	       theDir.mkdirs();
       	   }
       	   
       	   
       	   
               OutputStream out = new FileOutputStream(new File(destination+"\\" + fileName));
             
               int read = 0;
               byte[] bytes = new byte[1024];
             
               while ((read = in.read(bytes)) != -1) {
                   out.write(bytes, 0, read);
               }
             
               in.close();
               out.flush();
               out.close();
             
               System.out.println("New file created!");
               } catch (IOException e) {
               System.out.println(e.getMessage());
               }
          
          return  fileName;
   } 
	 
	 
	 
	 
	 
	 
	 public void uploadSpecialistPhoto(FileUploadEvent event){
		  UploadedFile file = event.getFile();
	        specialistLogoImage = new AppUtil().uploadImage(file,DBConstants.USERS_IMAGES_UPLOADS);
	        System.out.println("specialistLogoImage   ---  "+specialistLogoImage);
	        specialistLogoImageView=DBConstants.USERS_IMAGES_UPLOADS+specialistLogoImage;
	        
	        
//	        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
//	        FacesContext.getCurrentInstance().addMessage(null, msg);
//	        // Do what you want with the file        
//	        try {
//	        	specialistLogoImage=  copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
//	        	specialistLogoImageView=DBConstants.USERS_IMAGES_UPLOADS+specialistLogoImage;
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
	        
	    }


public boolean isSpecialistViewMode() {
	return specialistViewMode;
}

public void setSpecialistViewMode(boolean specialistViewMode) {
	this.specialistViewMode = specialistViewMode;
}

public boolean isEndUserViewMode() {
	return endUserViewMode;
}

public void setEndUserViewMode(boolean endUserViewMode) {
	this.endUserViewMode = endUserViewMode;
}


public UsersBean getUser() {
	return user;
}


public void setUser(UsersBean user) {
	this.user = user;
}


public boolean isEndUserUpdateMode() {
	return endUserUpdateMode;
}


public void setEndUserUpdateMode(boolean endUserUpdateMode) {
	this.endUserUpdateMode = endUserUpdateMode;
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


public ArrayList<CityBean> getCityList() {
	return cityList;
}


public void setCityList(ArrayList<CityBean> cityList) {
	this.cityList = cityList;
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


public DistricBean getDistrictBean() {
	return districtBean;
}


public void setDistrictBean(DistricBean districtBean) {
	this.districtBean = districtBean;
}
public  String updateUser()
{
	
	user.setMedicalHist(medicalhistoryupdate);
	user.setDistrict_id(districtBean.getId());
	String status=validateData();
	if(status==null || status.equals(""))
	return "";
	user.setProfile_img(userLogoImage);
	user.setCertList(certBeansUpdateUser);
	user.setProfList(profBeansUpdateUser);
	boolean isupdated=userService.updateByMed(user);
	if(isupdated)
	{
		
		user=userService.getById(user.getId());
		endUserViewMode=true;
		endUserUpdateMode=false;
		specialistViewMode=false;
		specialistUpdateMode=false;
		medicalhistoryupdate=new ArrayList<MedicalhistoryBean>();
	}
	try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("akshf_profile.xhtml");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return "";
}

public  String updateSpecialist()
{
	String status=validateData();
	if(status==null || status.equals(""))
	return "";
	endUserViewMode=false;
	endUserUpdateMode=false;
	specialistViewMode=true;
	specialistUpdateMode=false;
	
	

	//usersBean.setId(selectedUsers);
	specBean.setName(user.getFirstName()+user.getLastName());
	specBean.setUsers(user);
	specBean.setCertList(certBeansUpdate);
	specBean.setProfList(profBeansUpdate);
	specBean.setSectionBeans(sectionsBeans);
	user.setDistrict_id(districtBean.getId());
	user.setProfile_img(specialistLogoImage);

	specService.updateSpecialist(specBean);
	
	specBean=	specService.getSpecialistByUserID(user.getId());
	user=specBean.getUsers();
	profBeans=new ArrayList<ProfessionalExpBean>();
	certBeans=new ArrayList<CertificationBean>();
	sectionsBeans=new ArrayList<SectionsBean>();
	profBeans.addAll(specBean.getProfList());
	certBeans.addAll(specBean.getCertList());
	sectionsBeans.addAll(specBean.getSectionBeans());
	
	
	
	return "";
}

public SpecialistBean getSpecBean() {
	return specBean;
}


public void setSpecBean(SpecialistBean specBean) {
	this.specBean = specBean;
}


public boolean isSpecialistUpdateMode() {
	return specialistUpdateMode;
}


public void setSpecialistUpdateMode(boolean specialistUpdateMode) {
	this.specialistUpdateMode = specialistUpdateMode;
}


public List<SectionsBean> getSpecBeans() {
	return specBeans;
}


public void setSpecBeans(List<SectionsBean> specBeans) {
	this.specBeans = specBeans;
}


public List<SectionsBean> getSectionsBeans() {
	return sectionsBeans;
}


public void setSectionsBeans(List<SectionsBean> sectionsBeans) {
	this.sectionsBeans = sectionsBeans;
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


public List<ProfessionalExpBean> getProfBeansUpdate() {
	return profBeansUpdate;
}


public void setProfBeansUpdate(List<ProfessionalExpBean> profBeansUpdate) {
	this.profBeansUpdate = profBeansUpdate;
}


public List<CertificationBean> getCertBeansUpdate() {
	return certBeansUpdate;
}


public void setCertBeansUpdate(List<CertificationBean> certBeansUpdate) {
	this.certBeansUpdate = certBeansUpdate;
}


public UserFavoritiesForMobile getFavorites() {
	return favorites;
}


public void setFavorites(UserFavoritiesForMobile favorites) {
	this.favorites = favorites;
}


public boolean isMessageAppeared() {
	return messageAppeared;
}


public void setMessageAppeared(boolean messageAppeared) {
	this.messageAppeared = messageAppeared;
}


public List<MessagesBean> getMessageBeansbranches() {
	return messageBeansbranches;
}


public void setMessageBeansbranches(List<MessagesBean> messageBeansbranches) {
	this.messageBeansbranches = messageBeansbranches;
}


public List<MessagesBean> getMessageBeansSpecialist() {
	return messageBeansSpecialist;
}


public void setMessageBeansSpecialist(List<MessagesBean> messageBeansSpecialist) {
	this.messageBeansSpecialist = messageBeansSpecialist;
}


public boolean isMessagebranchAppear() {
	return messagebranchAppear;
}


public void setMessagebranchAppear(boolean messagebranchAppear) {
	this.messagebranchAppear = messagebranchAppear;
}


public boolean isMessageSpecilistAppear() {
	return messageSpecilistAppear;
}


public void setMessageSpecilistAppear(boolean messageSpecilistAppear) {
	this.messageSpecilistAppear = messageSpecilistAppear;
}




public String getSpecialistLogoImage() {
	return specialistLogoImage;
}


public void setSpecialistLogoImage(String specialistLogoImage) {
	this.specialistLogoImage = specialistLogoImage;
}


public List<MedicalhistoryBean> getMedicalhistory() {
	return medicalhistory;
}


public void setMedicalhistory(List<MedicalhistoryBean> medicalhistory) {
	this.medicalhistory = medicalhistory;
}


public MedicalhistoryBean getMedhistBean() {
	return medhistBean;
}


public void setMedhistBean(MedicalhistoryBean medhistBean) {
	this.medhistBean = medhistBean;
}


public List<MedicalhistoryBean> getMedicalhistoryupdate() {
	return medicalhistoryupdate;
}


public void setMedicalhistoryupdate(List<MedicalhistoryBean> medicalhistoryupdate) {
	this.medicalhistoryupdate = medicalhistoryupdate;
}





public String getSpecialistLogoImageView() {
	return specialistLogoImageView;
}


public void setSpecialistLogoImageView(String specialistLogoImageView) {
	this.specialistLogoImageView = specialistLogoImageView;
}


public boolean isActivatedMail() {
	return activatedMail;
}


public void setActivatedMail(boolean activatedMail) {
	this.activatedMail = activatedMail;
}


public boolean isActivatedMailSpec() {
	return activatedMailSpec;
}


public void setActivatedMailSpec(boolean activatedMailSpec) {
	this.activatedMailSpec = activatedMailSpec;
}


public String getMessageOutput() {
	return messageOutput;
}


public void setMessageOutput(String messageOutput) {
	this.messageOutput = messageOutput;
}


public CertificationBean getCertBeanUser() {
	return certBeanUser;
}


public void setCertBeanUser(CertificationBean certBeanUser) {
	this.certBeanUser = certBeanUser;
}


public List<CertificationBean> getCertBeansUser() {
	return certBeansUser;
}


public void setCertBeansUser(List<CertificationBean> certBeansUser) {
	this.certBeansUser = certBeansUser;
}


public List<CertificationBean> getCertBeansUpdateUser() {
	return certBeansUpdateUser;
}


public void setCertBeansUpdateUser(List<CertificationBean> certBeansUpdateUser) {
	this.certBeansUpdateUser = certBeansUpdateUser;
}


public ProfessionalExpBean getProfBeanUser() {
	return profBeanUser;
}


public void setProfBeanUser(ProfessionalExpBean profBeanUser) {
	this.profBeanUser = profBeanUser;
}


public List<ProfessionalExpBean> getProfBeansUser() {
	return profBeansUser;
}


public void setProfBeansUser(List<ProfessionalExpBean> profBeansUser) {
	this.profBeansUser = profBeansUser;
}


public List<ProfessionalExpBean> getProfBeansUpdateUser() {
	return profBeansUpdateUser;
}


public void setProfBeansUpdateUser(List<ProfessionalExpBean> profBeansUpdateUser) {
	this.profBeansUpdateUser = profBeansUpdateUser;
}


public String getActiveMailSt() {
	return activeMailSt;
}


public void setActiveMailSt(String activeMailSt) {
	this.activeMailSt = activeMailSt;
}

	
	

}
