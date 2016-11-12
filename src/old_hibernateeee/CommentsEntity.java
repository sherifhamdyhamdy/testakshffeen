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
@Entity(name = "comments")
public class CommentsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "comment")
    private String comment;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UsersEntity users;
    @JoinColumn(name = "specialist_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SpecialistEntity specialist;
    @JoinColumn(name = "content_details_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ContentDetailsEntity contentDetails;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private BranchEntity branch;
    @Column(name = "comment_date")
    private long comment_date ;
    
    
    public CommentsEntity() {
    }

    public CommentsEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public SpecialistEntity getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistEntity specialist) {
        this.specialist = specialist;
    }

    public ContentDetailsEntity getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetailsEntity contentDetails) {
        this.contentDetails = contentDetails;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
    }

    public long getComment_date() {
		return comment_date;
	}

	public void setComment_date(long comment_date) {
		this.comment_date = comment_date;
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
        if (!(object instanceof CommentsEntity)) {
            return false;
        }
        CommentsEntity other = (CommentsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Comments[ id=" + id + " ]";
    }
    
}
