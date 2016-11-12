package com.sget.akshef.hibernate.entities;

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
@Entity(name = "temp_branch")
public class TempBranchEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
  
    @Column(name = "address")
    private String address;
    
    @Column(name = "lat")
    private Double lat;
    
    @Column(name = "lng")
    private Double lng;
    
    @Column(name="email")
    private String email;
    
    
    @Column(name = "tel_num")
    private String tel_num;
    
    @Column(name = "user_recommend")
    private Integer user_recommend;
    
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private CategoryEntity category;
    

    
    public CategoryEntity getCategory() {
		return category;
	}



	public void setCategory(CategoryEntity category) {
		this.category = category;
	}



	@Column(name = "user_type")
    private int user_type;
    
    @Column(name = "user_tel")
    private String user_tel;
    
    
    
  
    public TempBranchEntity() {
    }

   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof TempBranchEntity)) {
            return false;
        }
        TempBranchEntity other = (TempBranchEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.temp_branch[ id=" + id + " ]";
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



	public Double getLng() {
		return lng;
	}



	public void setLng(Double lng) {
		this.lng = lng;
	}



	public String getTel_num() {
		return tel_num;
	}



	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}



	public Integer getUser_recommend()
	{
		return user_recommend;
	}



	public void setUser_recommend(Integer user_recommend)
	{
		this.user_recommend = user_recommend;
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




    
}
