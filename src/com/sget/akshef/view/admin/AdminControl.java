package com.sget.akshef.view.admin;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 * 
 * @author JDeeb
 * Super Admin Class contains all common function  
 */
public class AdminControl {

	// FacesMessage
	private FacesMessage msg = null ;
	
	/**
	 * 
	 * @param severity	Msg Type
	 * @param summary
	 * @param details
	 */
	public void addFacesMessage(Severity severity , String summary , String details){
		msg = new FacesMessage();
        msg.setSeverity(severity);
        msg.setSummary(summary);
        msg.setDetail(details);
        
        RequestContext.getCurrentInstance().showMessageInDialog(msg);
	}
	
	public HttpSession getHttpSession(){
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}
}
