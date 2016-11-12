package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class MessagesBean  implements java.io.Serializable {


     private int id;
     private MessageTypesBean messageTypes;
     private UsersBean fromUser;
     private UsersBean toUser;
     private String title;
     private String msgBody;
     private String notes;
     private String msg_type;
     private String from_username;
     private String to_username;
     private String branchName;
     private String specialistName;
    


	public String getBranchName() {
		return branchName;
	}


	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}


	private long msg_date ;

    public MessagesBean() {
    }

	
    public MessagesBean(MessageTypesBean messageTypes, UsersBean fromUser, UsersBean toUser) {
        this.messageTypes = messageTypes;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
    public MessagesBean(MessageTypesBean messageTypes, UsersBean fromUser, UsersBean toUser, String title, String msgBody, String notes) {
       this.messageTypes = messageTypes;
       this.fromUser = fromUser;
       this.toUser = toUser;
       this.title = title;
       this.msgBody = msgBody;
       this.notes = notes;
    }
    public String getTo_username() {
		return to_username;
	}


	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public MessageTypesBean getMessageTypes() {
        return this.messageTypes;
    }
    
    public void setMessageTypes(MessageTypesBean messageTypes) {
        this.messageTypes = messageTypes;
    }
    public UsersBean getFromUser() {
        return this.fromUser;
    }
    
    public void setFromUser(UsersBean fromUser) {
        this.fromUser = fromUser;
    }
    public UsersBean getToUser() {
        return this.toUser;
    }
    
    public void setToUser(UsersBean toUser) {
        this.toUser = toUser;
    }
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMsgBody() {
        return this.msgBody;
    }
    
    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }
    public String getNotes() {
        return this.notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }


	public String getMsg_type() {
		return msg_type;
	}


	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}


	public String getFrom_username() {
		return from_username;
	}


	public void setFrom_username(String from_username) {
		this.from_username = from_username;
	}


	public long getMsg_date() {
		return msg_date;
	}


	public void setMsg_date(long msg_date) {
		this.msg_date = msg_date;
	}


	public String getSpecialistName() {
		return specialistName;
	}


	public void setSpecialistName(String specialistName) {
		this.specialistName = specialistName;
	}

	
}
