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
 * Servlet implementation class CodeVerification
 */

public class CodeVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Utils utils = Utils.getInstance();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CodeVerification() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		if(emailCount!=null && !emailCount.equals("")&& !emailCount.equals("0"))
			
		{
			if(!emailCountVal.equals(userbean.getEmailCount()+""))
			{
			response.sendRedirect("akshf_codeVerification.xhtml");
			return;
			}
		}
		if(userbean.getActive_email()==1)
		{
			response.sendRedirect("akshf_login.xhtml");
			session.setAttribute("userBean", null);
			((MedLoginBean)session.getAttribute("loginBean")).setLogined(false);
		}
	     
	       Date userDate=userbean.getCreationDate();
		long diff = System.currentTimeMillis() - userDate.getTime();
		long diffHours = diff / (60 * 60 * 1000);  
		if(diffHours<48)
		{
			//activate the mail;
			userbean.setActive_email(1);
			service.update(userbean);
			userbean=	service.checkUser(userbean);
			response.sendRedirect("akshf_login.xhtml");
			session.setAttribute("userBean", null);
			((MedLoginBean)session.getAttribute("loginBean")).setLogined(false);
			
		}
		else
		{
			new Emailer().sendMailForVerification(userbean.getEmail(),userbean.getUsername(),userbean.getId(),userbean.getEmailCount()+1,url);
			userbean.setCreationDate(new Date());
			userbean.setEmailCount(userbean.getEmailCount()+1);
			service.update(userbean);
			response.sendRedirect("akshf_codeVerification.xhtml");
			
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
