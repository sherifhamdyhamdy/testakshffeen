package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class DrugsBean  implements java.io.Serializable {


     private int id;
     private DrugsCompanyBean drugsCompany;
     private String nameAr;
     private String nameEn;
     private String drugNum;
     private String productionCompany;
     private String pharmaceticalForm;
     private String illness;
     private String dosages;
     private String sideEffects;
     private String indications;
     private String conflictDrugs;
     private String avalibilty;
     private int quantity;
     private Double price;
     private String activePrincible;
     private String description;
     private String activeIngredients;
     private String drugImage;
     private String pregnancyLacations;
     private String storage;
     private String shelfLife;

    public DrugsBean() {
    }

	
    public DrugsBean(DrugsCompanyBean drugsCompany) {
        this.drugsCompany = drugsCompany;
    }
    public DrugsBean(DrugsCompanyBean drugsCompany, String nameAr, String nameEn, String drugNum, String productionCompany, String pharmaceticalForm, String illness, String dosages, String sideEffects, String indications, String conflictDrugs, String avalibilty, int quantity, Double price, String activePrincible, String description, String activeIngredients, String drugImage, String pregnancyLacations, String storage, String shelfLife) {
       this.drugsCompany = drugsCompany;
       this.nameAr = nameAr;
       this.nameEn = nameEn;
       this.drugNum = drugNum;
       this.productionCompany = productionCompany;
       this.pharmaceticalForm = pharmaceticalForm;
       this.illness = illness;
       this.dosages = dosages;
       this.sideEffects = sideEffects;
       this.indications = indications;
       this.conflictDrugs = conflictDrugs;
       this.avalibilty = avalibilty;
       this.quantity = quantity;
       this.price = price;
       this.activePrincible = activePrincible;
       this.description = description;
       this.activeIngredients = activeIngredients;
       this.drugImage = drugImage;
       this.pregnancyLacations = pregnancyLacations;
       this.storage = storage;
       this.shelfLife = shelfLife;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public DrugsCompanyBean getDrugsCompany() {
        return this.drugsCompany;
    }
    
    public void setDrugsCompany(DrugsCompanyBean drugsCompany) {
        this.drugsCompany = drugsCompany;
    }
    public String getNameAr() {
        return this.nameAr;
    }
    
    public void setNameAr(String nameAr) {
        this.nameAr = nameAr;
    }
    public String getNameEn() {
        return this.nameEn;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public String getDrugNum() {
        return this.drugNum;
    }
    
    public void setDrugNum(String drugNum) {
        this.drugNum = drugNum;
    }
    public String getProductionCompany() {
        return this.productionCompany;
    }
    
    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }
    public String getPharmaceticalForm() {
        return this.pharmaceticalForm;
    }
    
    public void setPharmaceticalForm(String pharmaceticalForm) {
        this.pharmaceticalForm = pharmaceticalForm;
    }
    public String getIllness() {
        return this.illness;
    }
    
    public void setIllness(String illness) {
        this.illness = illness;
    }
    public String getDosages() {
        return this.dosages;
    }
    
    public void setDosages(String dosages) {
        this.dosages = dosages;
    }
    public String getSideEffects() {
        return this.sideEffects;
    }
    
    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }
    public String getIndications() {
        return this.indications;
    }
    
    public void setIndications(String indications) {
        this.indications = indications;
    }
    public String getConflictDrugs() {
        return this.conflictDrugs;
    }
    
    public void setConflictDrugs(String conflictDrugs) {
        this.conflictDrugs = conflictDrugs;
    }
    public String getAvalibilty() {
        return this.avalibilty;
    }
    
    public void setAvalibilty(String avalibilty) {
        this.avalibilty = avalibilty;
    }
    public int getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Double getPrice() {
        return this.price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getActivePrincible() {
        return this.activePrincible;
    }
    
    public void setActivePrincible(String activePrincible) {
        this.activePrincible = activePrincible;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getActiveIngredients() {
        return this.activeIngredients;
    }
    
    public void setActiveIngredients(String activeIngredients) {
        this.activeIngredients = activeIngredients;
    }
    public String getDrugImage() {
        return this.drugImage;
    }
    
    public void setDrugImage(String drugImage) {
        this.drugImage = drugImage;
    }
    public String getPregnancyLacations() {
        return this.pregnancyLacations;
    }
    
    public void setPregnancyLacations(String pregnancyLacations) {
        this.pregnancyLacations = pregnancyLacations;
    }
    public String getStorage() {
        return this.storage;
    }
    
    public void setStorage(String storage) {
        this.storage = storage;
    }
    public String getShelfLife() {
        return this.shelfLife;
    }
    
    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }




}


