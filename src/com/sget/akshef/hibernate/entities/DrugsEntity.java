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

/**
 *
 * @author JDeeb
 */
@Entity(name = "drugs")
public class DrugsEntity implements Serializable {
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
    @Column(name = "drug_num")
    private String drugNum;
    @Column(name = "production_company")
    private String productionCompany;
    @Column(name = "pharmacetical_form")
    private String pharmaceticalForm;
    @Column(name = "illness")
    private String illness;
    @Column(name = "dosages")
    private String dosages;
    @Column(name = "side_effects")
    private String sideEffects;
    @Column(name = "indications")
    private String indications;
    @Column(name = "conflict_drugs")
    private String conflictDrugs;
    @Column(name = "Avalibilty")
    private String avalibilty;
    @Column(name = "quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @Column(name = "active_princible")
    private String activePrincible;
    @Column(name = "description")
    private String description;
    @Column(name = "active_ingredients")
    private String activeIngredients;
    @Column(name = "drug_image")
    private String drugImage;
    @Column(name = "pregnancy_lacations")
    private String pregnancyLacations;
    @Column(name = "storage")
    private String storage;
    @Column(name = "shelf_life")
    private String shelfLife;
    @JoinColumn(name = "drugs_company_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private DrugsCompanyEntity drugsCompany;

    public DrugsEntity() {
    }

    public DrugsEntity(Integer id) {
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

    public String getDrugNum() {
        return drugNum;
    }

    public void setDrugNum(String drugNum) {
        this.drugNum = drugNum;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public String getPharmaceticalForm() {
        return pharmaceticalForm;
    }

    public void setPharmaceticalForm(String pharmaceticalForm) {
        this.pharmaceticalForm = pharmaceticalForm;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getDosages() {
        return dosages;
    }

    public void setDosages(String dosages) {
        this.dosages = dosages;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getIndications() {
        return indications;
    }

    public void setIndications(String indications) {
        this.indications = indications;
    }

    public String getConflictDrugs() {
        return conflictDrugs;
    }

    public void setConflictDrugs(String conflictDrugs) {
        this.conflictDrugs = conflictDrugs;
    }

    public String getAvalibilty() {
        return avalibilty;
    }

    public void setAvalibilty(String avalibilty) {
        this.avalibilty = avalibilty;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getActivePrincible() {
        return activePrincible;
    }

    public void setActivePrincible(String activePrincible) {
        this.activePrincible = activePrincible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(String activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public String getDrugImage() {
        return drugImage;
    }

    public void setDrugImage(String drugImage) {
        this.drugImage = drugImage;
    }

    public String getPregnancyLacations() {
        return pregnancyLacations;
    }

    public void setPregnancyLacations(String pregnancyLacations) {
        this.pregnancyLacations = pregnancyLacations;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public DrugsCompanyEntity getDrugsCompany() {
        return drugsCompany;
    }

    public void setDrugsCompany(DrugsCompanyEntity drugsCompany) {
        this.drugsCompany = drugsCompany;
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
        if (!(object instanceof DrugsEntity)) {
            return false;
        }
        DrugsEntity other = (DrugsEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Drugs[ id=" + id + " ]";
    }
    
}
