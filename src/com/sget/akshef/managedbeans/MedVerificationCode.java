/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sget.akshef.managedbeans;



import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.RateEvent;

import com.sget.akshef.beans.ContentAdviceBean;
import com.sget.akshef.beans.ContentSpecialistsBean;
import com.sget.akshef.beans.SpecialistsCategoryBean;
import com.sget.akshef.buisness.AdvicesFactory;
import com.sget.akshef.hibernate.beans.CityBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.beans.CountryBean;
import com.sget.akshef.hibernate.beans.DegreeBean;
import com.sget.akshef.hibernate.beans.DistricBean;
import com.sget.akshef.hibernate.beans.SectionsBean;
import com.sget.akshef.hibernate.beans.SpecialistBean;
import com.sget.akshef.hibernate.beans.UsersBean;
import com.sget.akshef.hibernate.service.CityService;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.hibernate.service.CountryService;
import com.sget.akshef.hibernate.service.DistricService;
import com.sget.akshef.hibernate.service.MobileService;
 

public class MedVerificationCode implements Serializable{
    
    /**
	 * 
	 */
	
	
	
	
	public String loadCode()
	{
		String ID=((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("ID");
		System.out.println("ID----   >>.  "+ID);
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		Enumeration enum1=    request.getParameterNames();
		while(enum1.hasMoreElements())
		{
			String name=enum1.nextElement().toString();
			 System.out.println("name    "+name);
			
		}
		return "";
	}
  
}
