/**
 * 
 */
package com.sget.akshef.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sget.akshef.hibernate.beans.BranchBean;
import com.sget.akshef.hibernate.beans.BranchTempBean;
import com.sget.akshef.hibernate.beans.BranchTempBean;
import com.sget.akshef.hibernate.beans.RoleHasPermissionBean;
import com.sget.akshef.hibernate.beans.TempBranchBean;
import com.sget.akshef.hibernate.service.BranchService;
import com.sget.akshef.hibernate.service.TempBranchService;

/**
 * @author Abdelazeem
 * 
 */

//@javax.faces.bean.ManagedBean(name="branchControllerAll")
//@ViewScoped
public class BranchTempControllerAll implements Serializable{
	public TempBranchBean getBranchBean() {
		return branchBean;
	}

	public void setBranchBean(TempBranchBean branchBean) {
		this.branchBean = branchBean;
	}

	HttpSession session=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

	public List<TempBranchBean> getBranchBeans() {
		return branchBeans;
	}

	public void setBranchBeans(List<TempBranchBean> branchBeans) {
		this.branchBeans = branchBeans;
	}

	private TempBranchBean branchBean;
	private TempBranchService branchService;
	private List<TempBranchBean> branchBeans = null;
	private TempBranchBean[] selectedBranch;
	
	
	
	
	
	private int selectedBranchLength;
	private String selected;
	private String message = "";
	private boolean updaterender  = false;
	private boolean deleterender  = false;

	public BranchTempControllerAll() {

		branchBean = new TempBranchBean();
		branchService = new TempBranchService();
		


		// BranchTempBeans = new ArrayList<BranchTempBean>();
		// if (BranchTempBeans == null)
		// getAll();
		// if (BranchTempBeans != null && BranchTempBeans.size() == 0)
		// getAll();

		getAll();

		int arrayCount = branchBeans.size();
		selectedBranch = new TempBranchBean[arrayCount];

		

	}

	public void delete() {
		boolean deleteStatus = false;
		int deleteCount = 0;

		// System.out.println("selectedBranch  delete fun ::: "+ selectedBranch.length);

		if (selectedBranch.length < 1) {
			message = "select at least one";
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

		/*
		 * BranchTempBean = new BranchTempBean();
		 * BranchTempBean.setId(Integer.parseInt(selected));
		 * BranchTempService.delete(BranchTempBean);
		 */
	}

	// update
	public void update() {

		// if (!allowUpdate)
		// return;
		//
		// System.out.println("herer update fu :::  ");

		// System.out.println("herer update fu :::  " + selectedBranch.length);

		if (selectedBranch.length != 1) {
			message = "select one";
			return;
		}

		if (selectedBranch != null && selectedBranch.length == 1) {
			String page = "/systemadmin/branchAdd.xhtml";
		    branchBean= selectedBranch[0];
			session.setAttribute("TEMP_BRANCH",branchBean);
					//+ selectedBranch[0].getId();
			
			
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect(context.getRequestContextPath() + page);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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

	// getAll
	public void getAll() {

		branchBeans = new ArrayList<TempBranchBean>();
		branchBeans.addAll(branchService.getAll());

		// add to test only
		// System.out.println(" sss BranchTempBeans.size(): " + branchBeans.size());
		/*for (TempBranchBean bean : branchBeans) {
			// System.out.println("bean.getId() : " + bean.getId());
		
		}*/

	}

	// /geter setter

	

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public TempBranchBean[] getSelectedBranch() {
		return selectedBranch;
	}

	public void setSelectedBranch(TempBranchBean[] selectedBranch) {
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

	public boolean isUpdaterender() {
		return updaterender;
	}

	public void setUpdaterender(boolean updaterender) {
		this.updaterender = updaterender;
	}

	public boolean isDeleterender() {
		return deleterender;
	}

	public void setDeleterender(boolean deleterender) {
		this.deleterender = deleterender;
	}

}
