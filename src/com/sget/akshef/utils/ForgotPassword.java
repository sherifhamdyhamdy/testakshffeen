package com.sget.akshef.utils;

import java.io.IOException;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.UsersService;
import com.sget.akshef.managedbeans.MedLoginBean;

/**
 * Servlet implementation class ForgotPassword
 */

public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Utils utils = Utils.getInstance();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UsersService service=new UsersService();
		UsersBean userbean=null;
		String id=request.getParameter("CODE");
		String emailCount=request.getParameter("emailCount");
		StringBuffer baseURL = request.getRequestURL();
		String path=request.getServletPath();
	     String	url=utils.getXMLkey("AkshffeenAdmin");
	     HttpSession session=request.getSession();
	
		try {
			
			if(id!=null &&! id.equals(""))
			{
			String idOrg=new EncryptUtils().decrypt(id);
			String emailCountVal=new EncryptUtils().decrypt(emailCount);
			
			if(idOrg!=null && !idOrg.equals("")&& !idOrg.equals("0"))
			{
				userbean=   service.getById(Integer.parseInt(idOrg));
			}
			
			if(userbean!=null)
			{
				
				
				
		
			if(emailCountVal!=null && !emailCountVal.equals(""))
			{
			
				
				int mailCount=Integer.parseInt(emailCountVal);
				if(mailCount==0)	//if send from system
				{
					//first time
					if(userbean.getPass_emailCount()==0)
					{
						
						new Emailer().sendMailForForgetPass(userbean.getEmail(), userbean.getUsername(), userbean.getId(), 1, url);
						userbean.setPass_creationDate(new Date());
						userbean.setPass_emailCount(1);
//						userbean.setPassword("");
						service.update(userbean);
						response.sendRedirect("akshf_passwordForgotError.xhtml");
						
					}
					else
					{
						response.sendRedirect("akshf_passwordForgotError.xhtml");
						
					}
					
					
					
					
				}// if send from mail
				else 
				{
					if(userbean.getPass_emailCount()==0)
					{
						response.sendRedirect("akshf_login.xhtml");
						return;
					}		
					
					
					
					if(userbean.getPass_emailCount()==mailCount)//if send from validated mail
					{
					
						
						
						
						   Date userDate=userbean.getPass_creationDate();
							long diff = System.currentTimeMillis() - userDate.getTime();
							long diffHours = diff / (60 * 60 * 1000);  
							if(diffHours<48)
							{
								//((MedLoginBean)session.getAttribute("loginBean")).setBeanForgotten(userbean);
								session.setAttribute("FORGOTPASS", userbean);
								response.sendRedirect("akshf_login_forgot.xhtml");	
							}
							else
							{
								new Emailer().sendMailForForgetPass(userbean.getEmail(), userbean.getUsername(), userbean.getId(), userbean.getPass_emailCount()+1, url);
								userbean.setPass_creationDate(new Date());
								userbean.setPass_emailCount(userbean.getPass_emailCount()+1);
//								userbean.setPassword("");
								service.update(userbean);
								response.sendRedirect("akshf_passwordForgotError.xhtml");
								
							}
					
					}else if(userbean.getPass_emailCount()!=mailCount)//if sent from old email
					{
						response.sendRedirect("akshf_codeVerification.xhtml");
					}
				}
				
				
			}
				
				
				
				
			}
			
			
			
			}
			
			
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
