package com.sget.akshef.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.ActionsBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.ActionsService;
import com.sget.akshef.hibernate.service.UsersService;

public class LoginControllerAdd {
	private UsersBean usersBean = null;
	private UsersService usersService = null;
 	private String save = "save";
	private HtmlOutputLabel errorlabel;
	private String errorLogin;



	public LoginControllerAdd() {
		usersService = new UsersService();
		usersBean = new UsersBean();
		 
	}

	// saveAction
	public String login( ) {
 

		 
		// System.out.println("usersBeanuuuuuuu           "+usersBean.getUsername());
		// System.out.println("usersBeanpassssss        +"+usersBean.getPassword());
	usersBean=	usersService.checkUser(usersBean);
	if(usersBean != null && usersBean.getId() >0){
		// System.out.println("usersBeanpassssss        +"+usersBean.getId());
		HttpSession session = (HttpSession) getFacesContext()
				.getExternalContext().getSession(true);
		session.setAttribute("userBean", usersBean);

		//return "login";
		 List<RoleHasPermissionBean> roleHasPermissionBeans=	usersService.getAllRolesPermission(usersBean);
			 
			session.setAttribute("roleHasPermissionBeans", roleHasPermissionBeans);
 
			
 		
		
return "login";
	}
	 
 	return "";
		
	}
     public void clear(ActionEvent action) {
		
		// System.out.println("usersBeanuuuuuuu           "+usersBean.getUsername());
		// System.out.println("usersBeanpassssss        +"+usersBean.getPassword());
  		
	}
     public FacesContext getFacesContext() {
 		return FacesContext.getCurrentInstance();
 	}

	public UsersBean getUsersBean() {
		return usersBean;
	}

	public void setUsersBean(UsersBean usersBean) {
		this.usersBean = usersBean;
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

	 
	 

	 
}
