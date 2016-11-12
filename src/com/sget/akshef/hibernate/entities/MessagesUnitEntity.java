package com.sget.akshef.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 *
 * @author JDeeb
 */
@Entity(name = "messages_unit")
public class MessagesUnitEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "msg_body")
    private String msgBody;
    @Column(name = "notes")
    private String notes;
    @Column(name = "msg_date")
    private long msg_date ;
    
    @Column(name = "sent_message_id")
    private Integer sent_message_id;
    
    @JoinColumn(name = "to_unit", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UnitEntity toUnit;
    
    @JoinColumn(name = "from_user", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private UsersEntity fromUser;
    
//    @JoinColumn(name = "type_id", referencedColumnName = "id")
//    @ManyToOne(optional = true, fetch = FetchType.LAZY)
//    private MessageTypesEntity messageTypes = new MessageTypesEntity();

    @Transient
    private String dateStr;
    
    public MessagesUnitEntity() {
    }

    public MessagesUnitEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public UnitEntity getToUnit() {
        return toUnit;
    }

    public void setToUnit(UnitEntity toUnit) {
        this.toUnit = toUnit;
    }

    public UsersEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UsersEntity fromUser) {
        this.fromUser = fromUser;
    }

//    public MessageTypesEntity getMessageTypes() {
//        return messageTypes;
//    }
//
//    public void setMessageTypes(MessageTypesEntity messageTypes) {
//        this.messageTypes = messageTypes;
//    }
    
    public long getMsg_date() {
		return msg_date;
	}

	public void setMsg_date(long msg_date) {
		this.msg_date = msg_date;
	}
		
	public Integer getSent_message_id()
	{
		return sent_message_id;
	}

	public void setSent_message_id(Integer sent_message_id)
	{
		this.sent_message_id = sent_message_id;
	}

	public String getDateStr()
	{
		return dateStr;
	}

	public void setDateStr(String dateStr)
	{
		this.dateStr = dateStr;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MessagesUnitEntity)) {
            return false;
        }
        MessagesUnitEntity other = (MessagesUnitEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Messages[ id=" + id + " ]";
    }
    
}
