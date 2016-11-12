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
@Entity(name = "distric")
public class DistricEntity implements Serializable {
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
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CityEntity city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "distric", fetch = FetchType.LAZY)
    private Set<BranchEntity> branchSet;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "distric", fetch = FetchType.LAZY)
    private Set<UsersEntity> usersSet;

    public DistricEntity() {
    }

    public DistricEntity(Integer id) {
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

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

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
        if (!(object instanceof DistricEntity)) {
            return false;
        }
        DistricEntity other = (DistricEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Distric[ id=" + id + " ]";
    }

	public Set<UsersEntity> getUsersSet() {
		return usersSet;
	}

	public void setUsersSet(Set<UsersEntity> usersSet) {
		this.usersSet = usersSet;
	}
    
}
