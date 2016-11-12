package com.sget.akshef.hibernate.beans;


/**
 * Author JDeeb
 */
public class TempBranchBean  implements java.io.Serializable {
	 private Integer id;
	    
	    private String name;
	  
	    private String address;
	    
	    private Double lat;
	    
	    private Double lng;
	    
	    private CategoryBean catBean;
	    
	    public CategoryBean getCatBean() {
			return catBean;
		}



		public void setCatBean(CategoryBean catBean) {
			this.catBean = catBean;
		}



		public Double getLng() {
			return lng;
		}



		public void setLng(Double lng) {
			this.lng = lng;
		}



		private String tel_num;	

	   
	    
	    private String email;
	    
	    private int user_type;
	    
	    private String user_tel;
     
	    private Integer user_recommend;

    public TempBranchBean() {
    	
    }



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public Double getLat() {
		return lat;
	}



	public void setLat(Double lat) {
		this.lat = lat;
	}



	



	public String getTel_num() {
		return tel_num;
	}



	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}







	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public int getUser_type() {
		return user_type;
	}



	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}



	public String getUser_tel() {
		return user_tel;
	}



	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}


	public Integer getUser_recommend()
	{
		return user_recommend;
	}



	public void setUser_recommend(Integer user_recommend)
	{
		this.user_recommend = user_recommend;
	}
	



}


