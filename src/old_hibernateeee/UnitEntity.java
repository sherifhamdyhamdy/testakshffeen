package old_hibernateeee;

import java.io.Serializable;
import java.util.ArrayList;
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

/**
 *
 * @author JDeeb
 */
@Entity(name = "unit")
public class UnitEntity implements Serializable {
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
    @Column(name = "biography_ar")
    private String biographyAr;
    @Column(name = "biography_en")
    private String biographyEn;
    @Column(name = "active")
    private Integer active;
    @Column(name = "unit_logo")
    private String unitlogo;
    @Column(name = "unit_website")
    private String website;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private Set<UnitGroupsEntity> unitGroupsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private Set<UnitHasInsuranceCompanyEntity> unitHasInsuranceCompanySet;
    
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    @ManyToOne(optional = false, fetch = FetchType.EAGER)
//    private CategoryEntity category;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private Set<BranchEntity> branchSet;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private UsersEntity users;

  
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private List<ContentDetailsEntity> contentDetailsList = new ArrayList<ContentDetailsEntity>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private List<UnitHasCategory> unitHasCategoryList = new ArrayList<UnitHasCategory>();
    
    public UnitEntity() {
    }

    public UnitEntity(Integer id) {
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

    public String getBiographyAr() {
        return biographyAr;
    }

    public void setBiographyAr(String biographyAr) {
        this.biographyAr = biographyAr;
    }

    public String getBiographyEn() {
        return biographyEn;
    }

    public void setBiographyEn(String biographyEn) {
        this.biographyEn = biographyEn;
    }

    public Integer getActive() {
    	return active != null ? active : 0;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<UnitGroupsEntity> getUnitGroupsSet() {
        return unitGroupsSet;
    }

    public void setUnitGroupsSet(Set<UnitGroupsEntity> unitGroupsSet) {
        this.unitGroupsSet = unitGroupsSet;
    }

    public Set<UnitHasInsuranceCompanyEntity> getUnitHasInsuranceCompanySet() {
        return unitHasInsuranceCompanySet;
    }

    public void setUnitHasInsuranceCompanySet(Set<UnitHasInsuranceCompanyEntity> unitHasInsuranceCompanySet) {
        this.unitHasInsuranceCompanySet = unitHasInsuranceCompanySet;
    }

//    public CategoryEntity getCategory() {
//        return category;
//    }
//
//    public void setCategory(CategoryEntity category) {
//        this.category = category;
//    }

    public Set<BranchEntity> getBranchSet() {
        return branchSet;
    }

    public void setBranchSet(Set<BranchEntity> branchSet) {
        this.branchSet = branchSet;
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
        if (!(object instanceof UnitEntity)) {
            return false;
        }
        UnitEntity other = (UnitEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Unit[ id=" + id + " ]";
    }

	public UsersEntity getUsers() {
		return users;
	}

	public void setUsers(UsersEntity users) {
		this.users = users;
	}

	public String getUnitlogo() {
		return unitlogo;
	}
	public void setUnitlogo(String unitlogo) {
		this.unitlogo = unitlogo;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}

	public List<ContentDetailsEntity> getContentDetailsList()
	{
		return contentDetailsList;
	}

	public void setContentDetailsList(List<ContentDetailsEntity> contentDetailsList)
	{
		this.contentDetailsList = contentDetailsList;
	}

	public List<UnitHasCategory> getUnitHasCategoryList()
	{
		return unitHasCategoryList;
	}

	public void setUnitHasCategoryList(List<UnitHasCategory> unitHasCategoryList)
	{
		this.unitHasCategoryList = unitHasCategoryList;
	}
	
	
	
	
	
	
}
