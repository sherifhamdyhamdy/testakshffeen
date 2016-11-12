package com.sget.akshef.view;

import java.io.Serializable;
import java.util.List;

import com.sget.akshef.hibernate.beans.GuestSuggestComplainBean;
import com.sget.akshef.hibernate.service.GuestSuggestComplainService;

/**
 * 
 * @author JDeeb
 *
 */

public class GuestSuggComp implements Serializable{

	private static final long serialVersionUID = -9104231749061535190L;
	private GuestSuggestComplainService service = null ;
	private List<GuestSuggestComplainBean> guestCompBeans = null;
	
	public GuestSuggComp(){
		service = new GuestSuggestComplainService();
		getGuestSuggestComp();
	}
	
	public void getGuestSuggestComp(){
		guestCompBeans = service.getAll();
	}

	// Setters & Getters
	public List<GuestSuggestComplainBean> getGuestCompBeans() {
		return guestCompBeans;
	}
	public void setGuestCompBeans(List<GuestSuggestComplainBean> guestCompBeans) {
		this.guestCompBeans = guestCompBeans;
	}
}
