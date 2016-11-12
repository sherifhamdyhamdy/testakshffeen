package com.akshffeen.utils;

import java.text.SimpleDateFormat;

import javax.faces.context.FacesContext;

public class Constants 
{
	public static final String latitude = "30.0444196";
	public static final String longitude = "31.2357116";
	public static final Integer zoomLevel = 7;
	
//	public static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

	public static final String dateformat = "yyyy-MM-dd HH:mm:ss";
	public static final String delimiter = ";;";

	public static final int productDaysExpiration = -5;
	public static final int maxUploadLimitation = 5;
	
	public static final String destination_uploadedFiles = "/"+FacesContext.getCurrentInstance().getExternalContext().getRealPath("//uploadedFiles//");
	public static final String destination_unit = "/"+FacesContext.getCurrentInstance().getExternalContext().getRealPath("//images//unit//");
	public static final String destination_section = "/"+FacesContext.getCurrentInstance().getExternalContext().getRealPath("//images//sections//");
	public static final String destination_company = "/"+FacesContext.getCurrentInstance().getExternalContext().getRealPath("//images//company//");
	public static final String destination_user = "/"+FacesContext.getCurrentInstance().getExternalContext().getRealPath("//images//users//");


	public static final String LINUX_SEPARATOR = "/";
	public static final String WINDOWS_SEPARATOR = "\\";
	
	public static final String URL_SEPARATOR = LINUX_SEPARATOR;

	public static final String server = "http://localhost";			//abdoooooooo
//	public static final String server = "localhost:7676";
//	public static final String server = "need-to.com";
//	public static final String server = "appscp.akshffeen.com";

	public static final String success = "success";
	public static final String fail = "fail";
	public static final String noDevices = "noDevices";
	
	public static final String android_platform = "android";
	public static final String ios_platform = "ios";
	
//	public static final String ios_notification_server = ":6363/AkshffeenPNApple";				//abdoooooooo
//	public static final String android_notification_server = ":6363/AkshffeenPNAndroid";

//	public static final String ios_notification_server = "/AkshffeenPNApple";
//	public static final String android_notification_server = "/AkshffeenPNAndroid";

	public static final String ios_notification_server = ":8080/AkshffeenPNApple";				//abdoooooooo
	public static final String android_notification_server = ":8080/AkshffeenPNAndroid";
	
	
	public static final String project_devices = "project devices";
	public static final String specific_devices = "specific devices";
	public static final String module_favourite = "module favourite";

	public static final String charset = "UTF-8";
	
	public static final String sql_equal = "=";
	public static final String sql_not_equal = "<>";
	
	public static final Integer marital_status_single = 1;
	public static final Integer marital_status_engaged = 2;
	public static final Integer marital_status_married = 3;
	public static final Integer marital_status_divorced = 4;

	
	public static final int CATEGORY_CLINICS = 3 ;
	public static final int SUB_CATEGORY_CLINICS = 15 ;
	
//	public static final String LOGIN_PERMISSION_ADMIN = "1" ;
//	public static final String LOGIN_PERMISSION_UNIT_MANAGER = "2" ;
//	public static final String LOGIN_PERMISSION_BRANCH_MANAGER = "3" ;

//	public static final String AUTH_PERMISSION = "loginPermission";
	
//	public static final String AUTH_UNIT_ID = "loginUnit_id";
//	public static final String AUTH_BRANCH_ID = "loginBranch_id";
		
	public static final String AUTH_USERLOGIN = "UserLogin";

	public static final String encryption_key = "!@#@!$#$$%$#%#%";
	public static final String UTF8 = "UTF-8";
	public static final byte[] sharedvector = { 0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11 };

	public static final String USER_ADMIN_SCOPE = "1";
	public static final String USER_UNIT_BRANCH_SCOPE = "2,3";
	public static final String USER_UNIT_SCOPE = "2";
	public static final String USER_BRANCH_SCOPE = "3";	
	
	public static final String SEPERATOR_UNITS_PERMISSIONS = "&";	
	public static final String SEPERATOR_PERMISSIONS = "|";	
	
	public static final int SPONSORED_LIMIT = 2;

}
