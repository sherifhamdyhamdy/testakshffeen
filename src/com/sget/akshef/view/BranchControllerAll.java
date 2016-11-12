package com.sget.akshef.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.service.BranchService;

/**
 * @author Abdelazeem
 * 
 */

public class BranchControllerAll implements Serializable{
	
	private static final long serialVersionUID = -1450790855901248754L;
	
	private BranchBean branchBean;
	private BranchService branchService;
	private List<BranchBean> branchBeans = null;
	private BranchBean[] selectedBranch;
	private int selectedBranchLength;
	private String selected;
	private String message = "";	
	
	public BranchControllerAll() {

		branchBean = new BranchBean();
		branchService = new BranchService();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		
		getAll();

		int arrayCount = branchBeans.size();
		selectedBranch = new BranchBean[arrayCount];
        
		if (request.getParameter("selected") != null)
			selected = request.getParameter("selected");

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;
		
		if (selectedBranch.length < 1) {
			message = "select at least one";
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, 
 					"Error", "Select at Least One !!!"));
			return;
		}
		for (int count = 0; count < selectedBranch.length; count++) {
			branchBean = selectedBranch[count];
			deleteStatus = branchService.delete(branchBean);
			if (deleteStatus)
				deleteCount++;
		}
		if (deleteCount == selectedBranch.length)
			message = "deleted";
		else
			message = "parent cant be deleted";
		getAll();
	}
	// update
	public String update() {
		if (selectedBranch.length != 1) {
			message = "select one";
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, 
 					"Error", "Select One !!!"));
			return null;
		}
		if (selectedBranch != null && selectedBranch.length == 1) {
			String page = "/systemadmin/branchAdd.xhtml?id="
					+ selectedBranch[0].getId();
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + page);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public void redirecttoPage(String page) {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			ec.redirect(ec.getRequestContextPath() + page);
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					e.getMessage());
		}
	}

	// get All Branch
	public void getAll() {
		branchBeans = new ArrayList<BranchBean>();
		branchBeans.addAll(branchService.getAll());
	}

	// getter setter
	public BranchBean getBranchBean() {
		return branchBean;
	}
	public void setBranchBean(BranchBean branchBean) {
		this.branchBean = branchBean;
	}
	public BranchService getBranchService() {
		return branchService;
	}
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}
	public List<BranchBean> getBranchBeans() {
		return branchBeans;
	}
	public void setBranchBeans(List<BranchBean> branchBeans) {
		this.branchBeans = branchBeans;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public BranchBean[] getSelectedBranch() {
		return selectedBranch;
	}
	public void setSelectedBranch(BranchBean[] selectedBranch) {
		this.selectedBranch = selectedBranch;
		selectedBranchLength = selectedBranch.length;
	}
	public int getSelectedBranchLength() {
		return selectedBranchLength;
	}
	public void setSelectedBranchLength(int selectedBranchLength) {
		this.selectedBranchLength = selectedBranchLength;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
