package com.sget.akshef.hibernate.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.UsersBean;

/**
 * Servlet Filter implementation class AuthorizationFilter
 */
public class AuthorizationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthorizationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
 String  requested = req.getRequestURI();
  int f= requested.lastIndexOf("/");
 int e= requested.lastIndexOf(".");
 String  requestedPage = requested.substring((f+1), e);
 		UsersBean userBean =(UsersBean) req.getSession().getAttribute("userBean");
 		 
  
 		if (isAjax(req))  {
 	    	 if (req.getRequestedSessionId() != null
	 		        && !req.isRequestedSessionIdValid()) {
	    		 req.getSession().invalidate();
	 			res.sendRedirect(req.getContextPath() + "/loginPage.xhtml");
	 		}
	    } 
 		if (req.getRequestedSessionId() != null
		        && !req.isRequestedSessionIdValid()) {
			req.getSession().invalidate();
			res.sendRedirect(req.getContextPath() + "/loginPage.xhtml");
			
		}
  	  boolean   ok = false;

  		 if (userBean != null ) {
  			 if(requestedPage.trim().equalsIgnoreCase("systemadmin")){
                 ok =true;

  			 } else{
	        	  List<RoleHasPermissionBean> roleHasPermissionBeans= (List<RoleHasPermissionBean>) req.getSession().getAttribute("roleHasPermissionBeans");

	        	  if(roleHasPermissionBeans != null   &&  roleHasPermissionBeans .size()>0){
	      			for(RoleHasPermissionBean  roleHasPermissionBean :roleHasPermissionBeans){
	      				
	              	/* ln("page "+roleHasPermissionBean.getPageName()+"   "+roleHasPermissionBean.getId() +"  "+roleHasPermissionBean.getRole().getId()+"  role name =" +roleHasPermissionBean.getRole().getName()+"    "+roleHasPermissionBean.getPermision().getId()+"  per =="+roleHasPermissionBean.getPermision().getPermission_name());
					if(roleHasPermissionBean.getPageName().trim().equalsIgnoreCase(requestedPage.trim())){
 						ok = true;
						break;
					}*/
	      			}
	      			}}
					if(ok){	  
						chain.doFilter(req, res);
					}else{
				//	chain.doFilter(req, res);
				res.sendRedirect(req.getContextPath() + "/systemadmin/systemadmin.xhtml");	
 					}//	  	        }
	        } else {
	            // User is not logged in, so redirect to login.
	        //	System.err.println("the system redirect  filter ---  "+user);
	    		res.sendRedirect(req.getContextPath() + "/loginPage.xhtml");
	        }
  		 
 	}
	
	
	public  boolean isAjax(HttpServletRequest request) {
	       return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	    } 

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
