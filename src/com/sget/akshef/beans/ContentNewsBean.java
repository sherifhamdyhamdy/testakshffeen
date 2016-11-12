package com.sget.akshef.beans;

import java.io.Serializable;
import java.util.List;

import com.sget.akshef.hibernate.beans.CommentsBean;
import com.sget.akshef.hibernate.beans.ContentDetailsBean;

public class ContentNewsBean implements Serializable{
	
	
	 private String name_ar;
	 private String small_name_ar;
	  private String name_en;
	  private int id;
	  private String small_image;
	  private String large_image;
	  private String titleAr;
	  private String titleEn;
	  private Integer rating;
	  private boolean selected;
	  private List<CommentsBean> CommentsBeans;
	  private List<ContentDetailsBean> relatedTopics;
	   
	    

	    public String getName_ar() {
	        return name_ar;
	    }

	    public void setName_ar(String name_ar) {
	        this.name_ar = name_ar;
	    }

	    public String getName_en() {
	        return name_en;
	    }

	    public void setName_en(String name_en) {
	        this.name_en = name_en;
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getSmall_image() {
			return small_image;
		}

		public void setSmall_image(String small_image) {
			this.small_image = small_image;
		}

		public String getLarge_image() {
			return large_image;
		}

		public void setLarge_image(String large_image) {
			this.large_image = large_image;
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

		public String getSmall_name_ar() {
			
				
			
			return small_name_ar;
		}

		public void setSmall_name_ar(String small_name_ar) {
			this.small_name_ar = small_name_ar;
		}

		public List<CommentsBean> getCommentsBeans() {
			return CommentsBeans;
		}

		public void setCommentsBeans(List<CommentsBean> CommentsBeans) {
			this.CommentsBeans = CommentsBeans;
		}

		public Integer getRating() {
			return rating;
		}

		public void setRating(Integer rating) {
			this.rating = rating;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public List<ContentDetailsBean> getRelatedTopics() {
			return relatedTopics;
		}

		public void setRelatedTopics(List<ContentDetailsBean> relatedTopics) {
			this.relatedTopics = relatedTopics;
		}

	

}
