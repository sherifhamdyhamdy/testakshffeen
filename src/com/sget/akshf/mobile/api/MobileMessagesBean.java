package com.sget.akshf.mobile.api;

/**
 * 
 * @author JDeeb
 *
 */
public class MobileMessagesBean {

	private int id;
    private String title;
    private String msgBody;
    private String notes;
    private long msg_date ;
    
    private int fromUserId ;
    private String fromUsername ;
    private String fromUserImg ;
    
    private int type_id ;

    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public long getMsg_date() {
		return msg_date;
	}
	public void setMsg_date(long msg_date) {
		this.msg_date = msg_date;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUsername() {
		return fromUsername;
	}
	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	public String getFromUserImg() {
		return fromUserImg;
	}
	public void setFromUserImg(String fromUserImg) {
		this.fromUserImg = fromUserImg;
	}
	public int getType_id()
	{
		return type_id;
	}
	public void setType_id(int type_id)
	{
		this.type_id = type_id;
	}
	
	
    
}
