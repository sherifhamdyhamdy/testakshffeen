package com.sget.akshef.view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.sget.akshef.hibernate.beans.DataBaseVersion;
import com.sget.akshef.hibernate.constants.AppUtil;
import com.sget.akshef.hibernate.constants.DBConstants;
import com.sget.akshef.hibernate.service.DataBaseVersionService;

/**
 * 
 * @author JDeeb
 * Managed Bean for uploadSQLiteVersion.xhtml
 * Name dbVersionAdd
 */

public class UploadDBVersion implements Serializable{
	
	private static final long serialVersionUID = -4933975239539013287L;
	
	private DataBaseVersionService service = null ;
	private DataBaseVersion dbBean = null ;
	private DataBaseVersion latestVersion = null ;
	private AppUtil appUtil = null ;
	
	// Functions
	public UploadDBVersion(){
		service = new DataBaseVersionService();
		appUtil = new AppUtil();
		dbBean = new DataBaseVersion();

		// Prepare Functions
		prepareLatestVersion();
	}
	/**
	 * Get Latest Version
	 */
	private void prepareLatestVersion(){
		latestVersion = service.getLastVersion();
	}
	// handle Upload the file
	public void handelUpload(FileUploadEvent event){
        UploadedFile file = event.getFile();
        
        String imageName = appUtil.uploadImageWithName(file,DBConstants.SQLITE_DB_FOLDER);
        if(imageName != null && !imageName.trim().equalsIgnoreCase("")){
        	if(dbBean.getDataBaseName() != null && !dbBean.getDataBaseName().trim().equals("")){
    			appUtil.deleteImage(dbBean.getDataBaseName(), DBConstants.SQLITE_DB_FOLDER);
    		}
        	dbBean.setDataBaseName(imageName);
        }else{
        	RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Error !!!", "Error in System"));
        }
    }
	/**
	 * Save New Version to DB
	 */
	public void saveNewVersion(){
		if(dbBean != null && dbBean.getDataBaseName() != null && !dbBean.getDataBaseName().trim().equalsIgnoreCase("")
				&& dbBean.getVersion() > 0){
			service.insert(dbBean);
			if(dbBean.getId() > 0){
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, 
						"Inserted", "Content Inserted Success"));
				prepareLatestVersion();
				dbBean = new DataBaseVersion();
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						"Fail", "Fail To Insert"));
				// Delete All Images
				appUtil.deleteImage(dbBean.getDataBaseName(), DBConstants.SQLITE_DB_FOLDER);
			}
		}else{
			RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Error !!!", " Invalid Data "));
		}
	}
	// Setters & Getters
	public DataBaseVersion getDbBean() {
		return dbBean;
	}
	public void setDbBean(DataBaseVersion dbBean) {
		this.dbBean = dbBean;
	}
	public DataBaseVersion getLatestVersion() {
		return latestVersion;
	}
	public void setLatestVersion(DataBaseVersion latestVersion) {
		this.latestVersion = latestVersion;
	}
}