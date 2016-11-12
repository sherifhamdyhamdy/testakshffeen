package com.sget.akshf.mobile.api;

/*
 * JDeeb
 */
public class ResponseFactory {

	/////////////////////// Messages Errors
	public static final String MESSAGE_NO_SERVICES = "no_services";
	public static final String MESSAGE_SUCCESS = "success";
    public static final String MESSAGE_FAIL = "fail";
    public static final String MESSAGE_FAIL_EX = "fail_ex";
    public static final String MESSAGE_ERROR = "error";
    public static final String MESSAGE_CATEGORY_NOTFOUND = "category_not_found";
    public static final String MESSAGE_INVALID_INPUT = "INVALID_INPUT";
    public static final String MESSAGE_NO_DATA = "no_data";
    public static final String MESSAGE_NO_APPOINTMENT = "no_apponitment";
    public static final String MESSAGE_USERNAME_EXIST = "username_exist";
    public static final String MESSAGE_MOBILE_EXIST = "mobile_exist";
    public static final String MESSAGE_EMAIL_EXIST = "email_exist";
    public static final String MESSAGE_USERNAME_EMAIL_EXIST = "username_email_exist";
    public static final String MESSAGE_USERNAME_EMAIL_MOBILE_EXIST = "username_email_mobile_exist";
    public static final String MESSAGE_ALREADY_EXIST = "already_exist";
    public static final String MESSAGE_NO_NEW_VERSIONS = "no_new_versions";
    public static final String MESSAGE_EMAIL_NOT_EXIST="email_notexist";
    public static final String MESSAGE_PASSWORD_UPDATE="password was updated";
    public static final String MESSAGE_PASSWORD_NOT="password was not updated";
    public static final String MESSAGE_INVALID_LOGIN="Invalid login";
    public static final String MESSAGE_CODE_NOT_EXIST="Message code not exist";
    public static final String MESSAGE_CODE_INVALID_PASSWORD="Invalid Password";
    public static final String MESSAGE_INVALID_UPLOAD_IMAGE="Invalid upload image";
    public static final String MESSAGE_USERID_NOT_EXIST="Userid not exist";

    /////////////////////// Codes Errors
    public static final String CODE_NO_SERVICES = "1000";
    public static final String CODE_SUCCESS = "1001";
    public static final String CODE_FAIL = "1002";
    public static final String CODE_FAIL_EX = "1003";
    public static final String CODE_ERROR = "1004";
    public static final String CODE_CATEGORY_NOTFOUND = "1005";
    public static final String CODE_INVALID_INPUT = "1006";
    public static final String CODE_NO_DATA = "1007";
    public static final String CODE_NO_APPOINTMENT = "1008";
    public static final String CODE_USERNAME_EXIST = "1009";
    public static final String CODE_MOBILE_EXIST = "1022";
    public static final String CODE_EMAIL_EXIST = "1010";
    public static final String CODE_USERNAME_EMAIL_EXIST = "1011";
    public static final String CODE_USERNAME_EMAIL_MOBILE_EXIST = "1022";
    public static final String CODE_ALREADY_EXIST = "1012";
    public static final String CODE_NO_NEW_VERSIONS = "1013";
    public static final String CODE_PASSWORD_UPDATE="1014";
    public static final String CODE_PASSWORD_NOT="1015";
    public static final String CODE_EMAIL_NOT_EXIST="1016";
    public static final String CODE_INVALID_LOGIN="1017";
    public static final String CODE_INVALID_UPLOAD_IMAGE="1018";
    public static final String CODE_NOT_EXIST="1019";
    public static final String CODE_INVALID_PASSWORD="1020";
    public static final String CODE_USERID_NOT_EXIST="1021";

    private String code ;
    private String message ;
	
    public ResponseFactory() {}
    
    public ResponseFactory(String code , String message) {
    	this.code = code ;
    	this.message = message;
	}
    
    public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
}
