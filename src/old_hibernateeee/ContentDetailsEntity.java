package old_hibernateeee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sget.akshef.hibernate.beans.Akshffeen;
import com.sget.akshef.hibernate.beans.UsersBean;

/**
 *
 * @author JDeeb
 */
@Entity(name = "content_details")
public class ContentDetailsEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title_ar")
    private String titleAr;
    @Column(name = "title_en")
    private String titleEn;
    @Column(name = "content_ar")
    private String contentAr;
    @Column(name = "content_en")
    private String contentEn;
    @Column(name = "small_image")
    private String smallImage;
    @Column(name = "large_image")
    private String largeImage;
    
    @Column(name = "small_logo")
    private String smallLogo;
    @Column(name = "large_logo")
    private String largeLogo;
    
    @Column(name = "creat_date")
    @Temporal(TemporalType.DATE)
    private Date creat_date;
    
    @Column(name = "mod_date")
    @Temporal(TemporalType.DATE)
    private Date mod_date;
    
    
    
    @Column(name = "start_time")
    @Temporal(TemporalType.DATE)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.DATE)
    private Date endTime;
    @Column(name = "active")
    private Integer active;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY )
    private UsersEntity users;
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private PriorityEntity priority;
    @JoinColumn(name = "content_category_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContentCategoryEntity contentCategory;
    @OneToMany(mappedBy = "contentDetails", fetch = FetchType.LAZY)
    private Set<UserFavoritiesEntity> userFavoritiesSet;
    @OneToMany(mappedBy = "contentDetails", fetch = FetchType.LAZY)
    private Set<CommentsEntity> commentsSet;
    @Column(name = "content_rating")
    private Integer rating;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_Id")
	private UnitEntity unit = new UnitEntity();
    
    @Column(name = "sponsored")
    private Integer sponsored;
    
	@Column(name = "sponsored_from")
    private Date sponsored_from;
	
	@Column(name = "sponsored_to")
    private Date sponsored_to;
	
	@Column(name = "offer_from")
    private Date offer_from;
	
	@Column(name = "offer_to")
    private Date offer_to;
	
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "content", fetch = FetchType.LAZY)
    private List<ContentHasKeywordEntity> contentHasKeywordList = new ArrayList<ContentHasKeywordEntity>();
    
    public ContentDetailsEntity() {
    }

    public ContentDetailsEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getContentAr() {
        return contentAr;
    }

    public void setContentAr(String contentAr) {
        this.contentAr = contentAr;
    }

    public String getContentEn() {
        return contentEn;
    }

    public void setContentEn(String contentEn) {
        this.contentEn = contentEn;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getActive() {
    	return active != null ? active : 0;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
    }

    public PriorityEntity getPriority() {
        return priority;
    }

    public void setPriority(PriorityEntity priority) {
        this.priority = priority;
    }

    public ContentCategoryEntity getContentCategory() {
        return contentCategory;
    }

    public void setContentCategory(ContentCategoryEntity contentCategory) {
        this.contentCategory = contentCategory;
    }

    public Set<UserFavoritiesEntity> getUserFavoritiesSet() {
        return userFavoritiesSet;
    }

    public void setUserFavoritiesSet(Set<UserFavoritiesEntity> userFavoritiesSet) {
        this.userFavoritiesSet = userFavoritiesSet;
    }

    public Set<CommentsEntity> getCommentsSet() {
        return commentsSet;
    }

    public void setCommentsSet(Set<CommentsEntity> commentsSet) {
        this.commentsSet = commentsSet;
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
        if (!(object instanceof ContentDetailsEntity)) {
            return false;
        }
        ContentDetailsEntity other = (ContentDetailsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.ContentDetails[ id=" + id + " ]";
    }

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSmallLogo() {
		return smallLogo;
	}

	public void setSmallLogo(String smallLogo) {
		this.smallLogo = smallLogo;
	}

	public String getLargeLogo() {
		return largeLogo;
	}

	public void setLargeLogo(String largeLogo) {
		this.largeLogo = largeLogo;
	}

	public Date getCreat_date() {
		return creat_date;
	}

	public void setCreat_date(Date creat_date) {
		this.creat_date = creat_date;
	}

	public Date getMod_date() {
		return mod_date;
	}

	public void setMod_date(Date mod_date) {
		this.mod_date = mod_date;
	}

	public UnitEntity getUnit()
	{
		return unit;
	}

	public void setUnit(UnitEntity unit)
	{
		this.unit = unit;
	}

	public Integer getSponsored()
	{
		return sponsored;
	}

	public void setSponsored(Integer sponsored)
	{
		this.sponsored = sponsored;
	}

	public Date getSponsored_from()
	{
		return sponsored_from;
	}

	public void setSponsored_from(Date sponsored_from)
	{
		this.sponsored_from = sponsored_from;
	}

	public Date getSponsored_to()
	{
		return sponsored_to;
	}

	public void setSponsored_to(Date sponsored_to)
	{
		this.sponsored_to = sponsored_to;
	}
	
	
	public Date getOffer_from()
	{
		return offer_from;
	}

	public void setOffer_from(Date offer_from)
	{
		this.offer_from = offer_from;
	}

	public Date getOffer_to()
	{
		return offer_to;
	}

	public void setOffer_to(Date offer_to)
	{
		this.offer_to = offer_to;
	}

	public List<ContentHasKeywordEntity> getContentHasKeywordList()
	{
		return contentHasKeywordList;
	}

	public void setContentHasKeywordList(List<ContentHasKeywordEntity> contentHasKeywordList)
	{
		this.contentHasKeywordList = contentHasKeywordList;
	}

	
	
	
	
}
