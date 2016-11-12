/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sget.akshef.managedbeans;



/**
 *
 * @author Vip
 */
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

//import org.apache.catalina.ant.FindLeaksTask;



import com.akshffeen.utils.Utils;
import com.sget.akshef.buisness.UserFactory;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshef.maps.AddressConverter;
import com.sget.akshef.maps.GoogleResponse;
import com.sget.akshef.maps.Result;
import com.sget.akshef.utils.EmailValidator;
import com.sget.akshef.utils.EncryptUtils;
import com.sget.akshef.view.admin.UserPrivilege;
import com.sget.akshef.view.admin.UserPrivilegeFactory;
 

public class MedLoginBean {
	private Utils utils = Utils.getInstance();

    
      /** Creates a new instance of MedLoginBean */
    public MedLoginBean() {
    	usersService = new UsersService();
    }
    

    String confirmMsg="";
    String fontColor;
    String address="";
    boolean logined=false;
    boolean admin=false;
    String username;
    String password;
    String forgotPassword;
    String retypeforgotPassword;
    UsersBean user;
    String email;
    public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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


	String lat="0.0";
    String lng="0.0";

    public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}

	public void loadingContentDetailsWithComents(ActionEvent evt)
	 {
		
		String id=((org.primefaces.component.commandlink.CommandLink)evt.getSource()).getTitle().toString();

		
		String page = "/akshf_contentDetails.xhtml?ID="+id;
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		
		
		try {
			context.redirect(context.getRequestContextPath() + page);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	   
	 }
    
    
    public String   loadingLoginForm()
    {
    	errorLogin="";
    	if(logined)
    	{
    		if((user.getFirstName()!=null && !user.getFirstName().equals("")) && (user.getLastName()!=null && !user.getLastName().equals("")))
        	{
      	  confirmMsg= user.getFirstName() +" "+ user.getLastName();
        	}
    		else
    		{
    			confirmMsg=user.getEmail();		
    		}
    	
    		
    	}
    		
    	else if(!logined)
    	{
    		confirmMsg="";
    	}
    	return "";
    }
    
    public String   logOut()
    {
    	logined=false;
    	confirmMsg="";
    	((HttpSession)(getFacesContext().getExternalContext().getSession(true))).invalidate();
    	try {
			getFacesContext().getExternalContext().redirect("akshf_login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "";
    }
    
//    public void logOutIn(ActionEvent evt)
//    {
//    //	String value=(String) ((org.primefaces.component.commandlink.CommandLink)evt.getSource()).getValue();
//    	((HttpSession)(getFacesContext().getExternalContext().getSession(true))).invalidate();
//    	try {
//			getFacesContext().getExternalContext().redirect("akshf_login.xhtml");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//   

//  public String login()
//  {
//      
//     System.out.println("here in action login"+user.getUserName()+"  passs  "+user.getPassword()); 
//      UserFactory userfact=new UserFactory();
//      Integer userId=userfact.login(user.getUserName(), user.getPassword());
//      if(userId>0)
//      {
//    	  confirmMsg="\u0645\u0631\u062d\u0628\u0627"+" "+user.getUserName();
//    	  user.setUserId(userId);
//    	  logined=true;
//      }else {
//    	  confirmMsg=" \u062e\u0637\u0627 \u0641\u0649 \u0623\u0633\u0645 \u0627\u0644\u0645\u0633\u062a\u062e\u062f\u0645 \u0623\u0648 \u0643\u0644\u0645\u0629 \u0627\u0644\u0645\u0631\u0648\u0631";
//    	  logined=false;
//      }
//     return "";
//      
//  }
    
    List<ContentDetailsBean> contentNews;
    List<ContentDetailsBean> contentTips;
    List<ContentDetailsBean>contentArticle;
    List<ContentDetailsBean>contentOffers;
  

	public void loadNewFeedsData()
    {
	Map<String,List<ContentDetailsBean>> map=	new ContentDetailsService().getNewFeeds();
		
		if(map==null || map.isEmpty())
			return;
		
    	contentNews=map.get("NEWS");
    	contentTips=map.get("TIPS");	
    	contentArticle=map.get("Article");	
    	contentOffers=map.get("Offers");
	
    	System.out.println("contentNews size  "+contentNews.size());
    	
    }
	
	  public List<ContentDetailsBean> getContentOffers() {
			return contentOffers;
		}

		public void setContentOffers(List<ContentDetailsBean> contentOffers) {
			this.contentOffers = contentOffers;
		}

		public List<ContentDetailsBean> getContentNews() {
			return contentNews;
		}

		public void setContentNews(List<ContentDetailsBean> contentNews) {
			this.contentNews = contentNews;
		}
    
   public String loadingAdress( )
   {
	   
	   HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
  	
  	 
  	lat=session.getAttribute("lat")!=null&&!session.getAttribute("lat").equals("")?session.getAttribute("lat").toString():"0.0";
	 lng=session.getAttribute("lng")!=null&&!session.getAttribute("lng").equals("")?session.getAttribute("lng").toString():"0.0";
	 if(lat ==null || lng==null || lat.equals("0.0")|| lng.equals("0.0"))
	 {
		  return "";
	 }
  	 
	   
	   GoogleResponse res1;
	try {
		System.out.println("lat --  "+lat+"  long     "+lng);
		res1 = new AddressConverter().convertFromLatLong(lat+","+lng);
		System.out.println("latitude--->>  "+lat);
		System.out.println("longitue--->>  "+lng);
		
		 String address="";
	   if(res1.getStatus().equals("OK"))
	   {
	    for(int i=0;i< res1.getResults().length;i++)
	    {
	     System.out.println("address is :"  +res1.getResults()[i].getFormatted_address());
         if(i==0) 
	     
	     address=" "+res1.getResults()[i].getFormatted_address();
        
      
	    }
	   }
	   else
	   {
	    System.out.println(res1.getStatus());
	   }
	    System.out.println("Addresss---  "+address); 
	    session.setAttribute("address",address);
	   
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//30.006363999999998 31.202961499999997
	  return "";
   }
  
   
   public String insertUser()
   {
	   System.out.println("heeeerrreeee");
	   UserFactory userfact=new UserFactory();
	   userfact.insertNewUser(user);
	   
	   return "";
   }
   
   
   public String redirectToUnits()
   {
	   System.out.println("redirect");
		
		
	   
	   return "akshf_units.xhtml";
   }

public boolean isLogined() {
	return logined;
}

public void setLogined(boolean logined) {
	this.logined = logined;
}
   

private UsersService usersService = null;
	private String save = "save";
private HtmlOutputLabel errorlabel;
private String errorLogin;


UsersService userDervice=new UsersService();
private String  	loginStatus="تسجيل دخول";

// saveAction
public String login() {
    
	if(username==null || password==null || username.equals("") || password.equals(""))
	{
		errorLogin="من فضللك أدخل اسم المستخدم أو كلمه المرور ";
	return "";
	}

	user=userDervice.loginUser(username,password);
    if(user.getId()>0)
    {
    	if((user.getFirstName()!=null && !user.getFirstName().equals("")) && (user.getLastName()!=null && !user.getLastName().equals("")))
    	{
  	  confirmMsg= user.getFirstName() +" "+ user.getLastName();
  	   loginStatus="تسجيل الخروج";
    	}else
    	{
    confirmMsg=user.getEmail();
    loginStatus="تسجيل الخروج";
    	}

  	  logined=true;
  	
    }else {
  	  confirmMsg=" \u062e\u0637\u0627 \u0641\u0649 \u0623\u0633\u0645 \u0627\u0644\u0645\u0633\u062a\u062e\u062f\u0645 \u0623\u0648 \u0643\u0644\u0645\u0629 \u0627\u0644\u0645\u0631\u0648\u0631";
  	  logined=false;
  	loginStatus="تسجيل دخول";
  	  return "";
    }
    


//user=	usersService.checkUser(user);
HttpSession session = (HttpSession) getFacesContext()
.getExternalContext().getSession(true);
if(user != null && user.getId() >0){

	session.setAttribute("userBean", user);
	//return "login";
	 List<RoleHasPermissionBean> roleHasPermissionBeans=	usersService.getAllRolesPermission(user);
		
	 UserPrivilege userPrivileges = null;
	 UserPrivilegeFactory factory = new UserPrivilegeFactory();
	 userPrivileges = factory.prepareUserPrivilege(roleHasPermissionBeans, userPrivileges);
	 
	 if(userPrivileges != null)
		 session.setAttribute("userPriv",userPrivileges);
	 
	 System.out.println(userPrivileges);
		session.setAttribute("roleHasPermissionBeans", roleHasPermissionBeans);

		
		 if(user!=null && user.getUsername()!=null&& user.getUsername().equals("admin"))
		 {
			admin=true;
			user.setAdmin(true);
			return "profile";
			
			
		
		 }
		 else 
		 {
				admin=false;
				user.setAdmin(false);
				return "profile";
		 }
	

}
else
{
	session.setAttribute("userBean", null);
}
 
	return "profile";
	
}

public String removeTextAdress()
{
	this.address="";
	return "";
}

 public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}



public String getSave() {
	return save;
}

public void setSave(String save) {
	this.save = save;
}

public HtmlOutputLabel getErrorlabel() {
	return errorlabel;
}

public void setErrorlabel(HtmlOutputLabel errorlabel) {
	this.errorlabel = errorlabel;
}

public String getErrorLogin() {
	return errorLogin;
}

public void setErrorLogin(String errorLogin) {
	this.errorLogin = errorLogin;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public UsersBean getUser() {
	return user;
}

public void setUser(UsersBean user) {
	this.user = user;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}
private String messageWarning;
private String forgotmessageWarning;
UsersBean beanForgotten;
public String validateEmail()
{
	
		if (email == null || email.equals("")) {
			messageWarning = "من فضللك أدخل البريد الأكترونى";
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('warningDialog').show()");
			
			return "";
		}

		boolean valid = new EmailValidator().validate(email);
		if (!valid) {
			messageWarning = "من فضللك أدخل البريد الأكترونى صحيح";
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("PF('warningDialog').show()");
			return "";
		}
		   UsersService serv=new UsersService();
		   beanForgotten = serv.validateEmail(email);
		   if(beanForgotten!=null && beanForgotten.getId()!=0)
		   {
//				RequestContext requestContext = RequestContext.getCurrentInstance();
//				requestContext.execute("PF('warningDialog').hide()");
//				requestContext.execute("PF('emailDialog').hide()");  
//				requestContext.execute("PF('passwordDialog').show()");  
			   
			   sendForgotPassword(beanForgotten);
				
		   }
		   else
		   {
			   messageWarning = "هذا  البريد الأكترونى غير موجود ,حاول مره لأخرى";
				RequestContext requestContext = RequestContext.getCurrentInstance();
				requestContext.execute("PF('warningDialog').show()");   
		   }
	
	
	return "";
}



private String sendForgotPassword(UsersBean userbean)
{
	StringBuffer baseURL = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURL();
	String path=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletPath();
     String	url=utils.getXMLkey("AkshffeenAdmin");
	url+="/ForgotPassword?CODE="+new EncryptUtils().encrypt(userbean.getId()+"")+"&emailCount="+new EncryptUtils().encrypt("0");

	
try {
	FacesContext.getCurrentInstance().getExternalContext().redirect(url);
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
	return "";
}


public String updatePassword()
{
	
		if (forgotPassword == null || forgotPassword.equals("")) {
			forgotmessageWarning = "من فضللك أدخل كلمه المرور";
		
			
			return "";
		}

		if (retypeforgotPassword == null || retypeforgotPassword.equals("")) {
			forgotmessageWarning = "من فضللك أدخل كلمه المرور";
		
			
			return "";
		}
		
		if (!retypeforgotPassword .equals(forgotPassword)) {
			forgotmessageWarning = "كلمه المرور غير متاطبقه";
		
			
			return "";
		}
		
		
		
		   UsersService serv=new UsersService();
		UsersBean user=	(UsersBean)((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("FORGOTPASS");
		if(user!=null &&user.getId()!=0)
		{
		  boolean isUpdate =serv.updatePassword(user.getUsername(),forgotPassword);
		   if(isUpdate)
		   {
			   forgotmessageWarning = "تم تغيير كلمه المرور بنجاح";
			
		   }
		   else
		   {
			   forgotmessageWarning = "لم  يتم تغيير كلمه المرور";
		   
		   }
		}
		   
	return "";
}



public String getMessageWarning() {
	return messageWarning;
}

public void setMessageWarning(String messageWarning) {
	this.messageWarning = messageWarning;
}

public String getForgotPassword() {
	return forgotPassword;
}

public void setForgotPassword(String forgotPassword) {
	this.forgotPassword = forgotPassword;
}

public String getRetypeforgotPassword() {
	return retypeforgotPassword;
}

public void setRetypeforgotPassword(String retypeforgotPassword) {
	this.retypeforgotPassword = retypeforgotPassword;
}

public String getForgotmessageWarning() {
	return forgotmessageWarning;
}

public void setForgotmessageWarning(String forgotmessageWarning) {
	this.forgotmessageWarning = forgotmessageWarning;
}

public UsersBean getBeanForgotten() {
	return beanForgotten;
}

public void setBeanForgotten(UsersBean beanForgotten) {
	this.beanForgotten = beanForgotten;
}

public String getLoginStatus() {
	return loginStatus;
}

public void setLoginStatus(String loginStatus) {
	this.loginStatus = loginStatus;
}

public List<ContentDetailsBean> getContentTips() {
	return contentTips;
}

public void setContentTips(List<ContentDetailsBean> contentTips) {
	this.contentTips = contentTips;
}

public List<ContentDetailsBean> getContentArticle() {
	return contentArticle;
}

public void setContentArticle(List<ContentDetailsBean> contentArticle) {
	this.contentArticle = contentArticle;
}

 
}
