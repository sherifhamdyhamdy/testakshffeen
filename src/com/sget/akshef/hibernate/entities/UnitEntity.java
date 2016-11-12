package com.sget.akshef.hibernate.entities;

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
import javax.persistence.Transient;

/**
 *
 * @author JDeeb
 */
@Entity(name = "unit")
public class UnitEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
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
    
    @Column(name = "facebook")
	private String facebook;

    @Column(name = "twitter")
	private String twitter;

    @Column(name = "linkedIn")
	private String linkedIn;

    @Column(name = "instagram")
	private String instagram;
    
    @Column(name = "branch_numbers")
	private Integer branch_numbers;

    @Column(name = "content_numbers")
	private Integer content_numbers;
    
    @Column(name = "offers_numbers")
	private Integer offers_numbers;

    @Column(name = "unit_keywords_numbers")
	private Integer unit_keywords_numbers;

    @Column(name = "content_keywords_numbers")
	private Integer content_keywords_numbers;
    
    @Column(name = "offers_keywords_numbers")
	private Integer offers_keywords_numbers;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private Set<UnitGroupsEntity> unitGroupsSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private Set<UnitHasInsuranceCompanyEntity> unitHasInsuranceCompanySet;
    
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    @ManyToOne(optional = true, fetch = FetchType.LAZY)
//    private CategoryEntity category;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private Set<BranchEntity> branchSet;
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private UsersEntity users;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "toUnit", fetch = FetchType.LAZY)
    private Set<MessagesUnitEntity> messagesUnitsSet;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private List<ContentDetailsEntity> contentDetailsList = new ArrayList<ContentDetailsEntity>();
  
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unit", fetch = FetchType.LAZY)
    private List<UnitHasCategory> unitHasCategoryList = new ArrayList<UnitHasCategory>();
    
    @Transient
    String categories_ar;
    
    @Transient
    String categories_en;
    
    
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

    public Set<MessagesUnitEntity> getMessagesUnitsSet()
	{
		return messagesUnitsSet;
	}

	public void setMessagesUnitsSet(Set<MessagesUnitEntity> messagesUnitsSet)
	{
		this.messagesUnitsSet = messagesUnitsSet;
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
	
	public String getFacebook()
	{
		return facebook;
	}

	public void setFacebook(String facebook)
	{
		this.facebook = facebook;
	}

	public String getTwitter()
	{
		return twitter;
	}

	public void setTwitter(String twitter)
	{
		this.twitter = twitter;
	}

	public String getLinkedIn()
	{
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn)
	{
		this.linkedIn = linkedIn;
	}

	public String getInstagram()
	{
		return instagram;
	}

	public void setInstagram(String instagram)
	{
		this.instagram = instagram;
	}

	public List<ContentDetailsEntity> getContentDetailsList()
	{
		return contentDetailsList;
	}

	public void setContentDetailsList(List<ContentDetailsEntity> contentDetailsList)
	{
		this.contentDetailsList = contentDetailsList;
	}

	public Integer getBranch_numbers()
	{
		return branch_numbers;
	}

	public void setBranch_numbers(Integer branch_numbers)
	{
		this.branch_numbers = branch_numbers;
	}

	public Integer getContent_numbers()
	{
		return content_numbers;
	}

	public void setContent_numbers(Integer content_numbers)
	{
		this.content_numbers = content_numbers;
	}

	public Integer getOffers_numbers()
	{
		return offers_numbers;
	}

	public void setOffers_numbers(Integer offers_numbers)
	{
		this.offers_numbers = offers_numbers;
	}

	public Integer getUnit_keywords_numbers()
	{
		return unit_keywords_numbers;
	}

	public void setUnit_keywords_numbers(Integer unit_keywords_numbers)
	{
		this.unit_keywords_numbers = unit_keywords_numbers;
	}

	public Integer getContent_keywords_numbers()
	{
		return content_keywords_numbers;
	}

	public void setContent_keywords_numbers(Integer content_keywords_numbers)
	{
		this.content_keywords_numbers = content_keywords_numbers;
	}

	public Integer getOffers_keywords_numbers()
	{
		return offers_keywords_numbers;
	}

	public void setOffers_keywords_numbers(Integer offers_keywords_numbers)
	{
		this.offers_keywords_numbers = offers_keywords_numbers;
	}

	public List<UnitHasCategory> getUnitHasCategoryList()
	{
		return unitHasCategoryList;
	}

	public void setUnitHasCategoryList(List<UnitHasCategory> unitHasCategoryList)
	{
		this.unitHasCategoryList = unitHasCategoryList;
	}

	public String getCategories_ar()
	{
		return categories_ar;
	}

	public void setCategories_ar(String categories_ar)
	{
		this.categories_ar = categories_ar;
	}

	public String getCategories_en()
	{
		return categories_en;
	}

	public void setCategories_en(String categories_en)
	{
		this.categories_en = categories_en;
	}
	
	
	
}
