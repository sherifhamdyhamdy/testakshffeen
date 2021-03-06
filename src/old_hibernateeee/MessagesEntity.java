package old_hibernateeee;

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

/**
 *
 * @author JDeeb
 */
@Entity(name = "messages")
public class MessagesEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "msg_body")
    private String msgBody;
    @Column(name = "notes")
    private String notes;
    @Column(name = "msg_date")
    private long msg_date ;
    
    @JoinColumn(name = "to_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UsersEntity toUser;
    
    @JoinColumn(name = "from_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UsersEntity fromUser;
    
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private MessageTypesEntity messageTypes;

    public MessagesEntity() {
    }

    public MessagesEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public UsersEntity getToUser() {
        return toUser;
    }

    public void setToUser(UsersEntity toUser) {
        this.toUser = toUser;
    }

    public UsersEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UsersEntity fromUser) {
        this.fromUser = fromUser;
    }

    public MessageTypesEntity getMessageTypes() {
        return messageTypes;
    }

    public void setMessageTypes(MessageTypesEntity messageTypes) {
        this.messageTypes = messageTypes;
    }
    
    public long getMsg_date() {
		return msg_date;
	}

	public void setMsg_date(long msg_date) {
		this.msg_date = msg_date;
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
        if (!(object instanceof MessagesEntity)) {
            return false;
        }
        MessagesEntity other = (MessagesEntity) object;
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
