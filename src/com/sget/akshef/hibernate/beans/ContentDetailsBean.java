package com.sget.akshef.hibernate.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sget.akshef.hibernate.entities.UnitEntity;


/**
 * Author JDeeb
 */
public class ContentDetailsBean implements java.io.Serializable {

	private static final long serialVersionUID = -4657443322871343420L;
	
	private int id;
	private ContentCategoryBean contentCategory;
	private PriorityBean priority;
	private UsersBean users;
	private String titleAr;
	private String titleEn;
	private String contentAr;
	private String contentEn;
	private String smallImage;
	private String largeImage;
	private String smallLogo;
	private String largeLogo;
	private String small_name_ar;
	
	private Akshffeen akshffeen;
    private int sponsored;
    
    private Date sponsored_from;
	
    private Date sponsored_to;
    
    private Date creat_date;
    private String creatDateSt;
    
    private String mod_dateSt;
	public String getMod_dateSt() {
		return mod_dateSt;
	}

	public void setMod_dateSt(String mod_dateSt) {
		this.mod_dateSt = mod_dateSt;
	}

	private Date mod_date;
	
	private Date startTime;
	private Date endTime;
	private int active;
	private Set userFavoritieses = new HashSet(0);
	private Set commentses = new HashSet(0);
	private int content_rating;
	// For Mobile
	private int rating;
	private int ratingno ;
	List<ContentDetailsBean> relatedTopics;
	List<CommentsBean> commentsBeans;
	
	private UnitBean unit = new UnitBean();
	
	private int sponsor_type;
	
	public List<CommentsBean> getCommentsBeans() {
		return commentsBeans;
	}

	public void setCommentsBeans(List<CommentsBean> commentsBeans) {
		this.commentsBeans = commentsBeans;
	}

	public ContentDetailsBean() {
	}

	public ContentDetailsBean(ContentCategoryBean contentCategory,
			PriorityBean priority, UsersBean users) {
		this.contentCategory = contentCategory;
		this.priority = priority;
		this.users = users;
	}

	public ContentDetailsBean(ContentCategoryBean contentCategory,
			PriorityBean priority, UsersBean users, String titleAr,
			String titleEn, String contentAr, String contentEn,
			String smallImage, String largeImage, Date startTime, Date endTime,
			int active, Set userFavoritieses, Set commentses) {
		this.contentCategory = contentCategory;
		this.priority = priority;
		this.users = users;
		this.titleAr = titleAr;
		this.titleEn = titleEn;
		this.contentAr = contentAr;
		this.contentEn = contentEn;
		this.smallImage = smallImage;
		this.largeImage = largeImage;
		this.startTime = startTime;
		this.endTime = endTime;
		this.active = active;
		this.userFavoritieses = userFavoritieses;
		this.commentses = commentses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ContentCategoryBean getContentCategory() {
		if(contentCategory == null)
			contentCategory = new ContentCategoryBean();
		return this.contentCategory;
	}

	public void setContentCategory(ContentCategoryBean contentCategory) {
		this.contentCategory = contentCategory;
	}

	public PriorityBean getPriority() {
		return this.priority;
	}

	public void setPriority(PriorityBean priority) {
		this.priority = priority;
	}

	public UsersBean getUsers() {
		return this.users;
	}

	public void setUsers(UsersBean users) {
		this.users = users;
	}

	public String getTitleAr() {
		return this.titleAr;
	}

	public void setTitleAr(String titleAr) {
		this.titleAr = titleAr;
	}

	public String getTitleEn() {
		return this.titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getContentAr() {
		return this.contentAr;
	}

	public void setContentAr(String contentAr) {
		this.contentAr = contentAr;
	}

	public String getContentEn() {
		return this.contentEn;
	}

	public void setContentEn(String contentEn) {
		this.contentEn = contentEn;
	}

	public String getSmallImage() {
		return this.smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public String getLargeImage() {
		return this.largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getActive() {
		return this.active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set getUserFavoritieses() {
		return this.userFavoritieses;
	}

	public void setUserFavoritieses(Set userFavoritieses) {
		this.userFavoritieses = userFavoritieses;
	}

	public Set getCommentses() {
		return this.commentses;
	}

	public void setCommentses(Set commentses) {
		this.commentses = commentses;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getContent_rating() {
		return content_rating;
	}

	public void setContent_rating(int content_rating) {
		this.content_rating = content_rating;
	}

	public int getRatingno() {
		return ratingno;
	}

	public void setRatingno(int ratingno) {
		this.ratingno = ratingno;
	}

//	public String getSmallLogo() {
//		return smallLogo;
//	}
//
//	public void setSmallLogo(String smallLogo) {
//		this.smallLogo = smallLogo;
//	}
//
//	public String getLargeLogo() {
//		return largeLogo;
//	}
//
//	public void setLargeLogo(String largeLogo) {
//		this.largeLogo = largeLogo;
//	}

	public List<ContentDetailsBean> getRelatedTopics() {
		return relatedTopics;
	}

	public void setRelatedTopics(List<ContentDetailsBean> relatedTopics) {
		this.relatedTopics = relatedTopics;
	}

	 

		public String getCreatDateSt() {
			return creatDateSt;
		}

		public void setCreatDateSt(String creatDateSt) {
			this.creatDateSt = creatDateSt;
		}
	public String getSmall_name_ar() {
		return small_name_ar;
	}

	public void setSmall_name_ar(String small_name_ar) {
		this.small_name_ar = small_name_ar;
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

	public UnitBean getUnit()
	{
		return unit;
	}

	public void setUnit(UnitBean unit)
	{
		this.unit = unit;
	}


	public int getSponsored()
	{
		return sponsored;
	}

	public void setSponsored(int sponsored)
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

	public Akshffeen getAkshffeen()
	{
		return akshffeen;
	}

	public void setAkshffeen(Akshffeen akshffeen)
	{
		this.akshffeen = akshffeen;
	}

	public int getSponsor_type()
	{
		return sponsor_type;
	}

	public void setSponsor_type(int sponsor_type)
	{
		this.sponsor_type = sponsor_type;
	}
	
	
	

	

}
