package com.sget.akshef.view.admin.branch;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.sget.akshef.hibernate.beans.ContentDetailsBean;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.ContentDetailsService;
import com.sget.akshef.view.admin.AdminControl;

/**
 * 
 * @author JDeeb
 *
 */

public class ContentsAll extends AdminControl implements Serializable{

	private static final long serialVersionUID = 7610058482255256576L;
	// Services and global Variables
	private ContentDetailsService service = null ;
	// Attributes
	private List<ContentDetailsBean> contents = null ;
	private String type = null;
	private ContentDetailsBean updateBean = null;
	// Test
	private int branchId = 72 ;
	
	// Functions
	public ContentsAll(){
		
		service = new ContentDetailsService();		
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		Map<String, String> maps = context.getRequestParameterMap();
		type = maps.get("type");
		// Page Request
		if(type != null && !type.trim().equalsIgnoreCase("")){
			getBranchContent();
		}
		
	}
	// Get Branch Content
	private void getBranchContent(){
		if(type.trim().equalsIgnoreCase(DBConstants.CONTENT_TYPE_NEWS+"")){
			contents = service.getContentByBranchAndType(DBConstants.CONTENT_TYPE_NEWS, branchId);
		}else if(type.trim().equalsIgnoreCase(DBConstants.CONTENT_TYPE_TIPS+"")){
			contents = service.getContentByBranchAndType(DBConstants.CONTENT_TYPE_TIPS, branchId);
		}else if(type.trim().equalsIgnoreCase(DBConstants.CONTENT_TYPE_OFFERS+"")){
			contents = service.getContentByBranchAndType(DBConstants.CONTENT_TYPE_OFFERS, branchId);
		}else{
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					" Leah !!", " HARAAAAAAAAAAAAAAAM"));
		}
	}
	
	// Setters and Getters
	public List<ContentDetailsBean> getContents() {
		return contents;
	}
	public void setContents(List<ContentDetailsBean> contents) {
		this.contents = contents;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ContentDetailsBean getUpdateBean() {
		return updateBean;
	}
	public void setUpdateBean(ContentDetailsBean updateBean) {
		this.updateBean = updateBean;
	}
}
