package old_hibernateeee;

import java.io.Serializable;
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

/**
 *
 * @author JDeeb
 */
@Entity(name = "content_category")
public class ContentCategoryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name_ar")
    private String nameAr;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "active")
    private Integer active;
    
    @Column (name = "cat_image")
    String cat_image;
    
    @Column (name = "cat_image_sc")
    String cat_image_sc;

	@JoinColumn(name = "content_types_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContentTypesEntity contentTypes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contentCategory", fetch = FetchType.LAZY)
    private Set<ContentDetailsEntity> contentDetailsSet;

    public ContentCategoryEntity() {
    }

    public ContentCategoryEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAr() {
        return nameAr;
    }

    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getActive() {
    	return active != null ? active : 0;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public ContentTypesEntity getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(ContentTypesEntity contentTypes) {
        this.contentTypes = contentTypes;
    }

    
    public String getCat_image_sc()
	{
		return cat_image_sc;
	}

	public void setCat_image_sc(String cat_image_sc)
	{
		this.cat_image_sc = cat_image_sc;
	}

	
	public Set<ContentDetailsEntity> getContentDetailsSet() {
        return contentDetailsSet;
    }

    public void setContentDetailsSet(Set<ContentDetailsEntity> contentDetailsSet) {
        this.contentDetailsSet = contentDetailsSet;
    }

    
    public String getCat_image() {
 		return cat_image;
 	}

 	public void setCat_image(String cat_image) {
 		this.cat_image = cat_image;
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
        if (!(object instanceof ContentCategoryEntity)) {
            return false;
        }
        ContentCategoryEntity other = (ContentCategoryEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.ContentCategory[ id=" + id + " ]";
    }
    
}
