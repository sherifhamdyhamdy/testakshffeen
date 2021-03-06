package com.sget.akshef.hibernate.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author JDeeb
 */
@Entity(name = "database_version")
public class DataBaseVersionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "dataBase_name")
    private String dataBaseName;
  
    @Column(name = "version")
    private Integer version;
    
    
    public DataBaseVersionEntity() {
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
        if (!(object instanceof DataBaseVersionEntity)) {
            return false;
        }
        DataBaseVersionEntity other = (DataBaseVersionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.database_version[ id=" + id + " ]";
    }



	public String getDataBaseName() {
		return dataBaseName;
	}



	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}



	public Integer getVersion() {
		return version != null ? version : 0;
	}



	public void setVersion(int version) {
		this.version = version;
	}



	
    
}
