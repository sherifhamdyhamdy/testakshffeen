package old_hibernateeee;

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
//@Entity(name = "profissionaldata")
public class ProfissionaldataEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
//    @JoinColumn(name = "degree_id", referencedColumnName = "id")
//    @ManyToOne(optional = false, fetch = FetchType.EAGER)
//    private DegreeEntity degree;
//    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private UsersEntity users;

    public ProfissionaldataEntity() {
    }

    public ProfissionaldataEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public DegreeEntity getDegree() {
//        return degree;
//    }
//
//    public void setDegree(DegreeEntity degree) {
//        this.degree = degree;
//    }

    public UsersEntity getUsers() {
        return users;
    }

    public void setUsers(UsersEntity users) {
        this.users = users;
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
        if (!(object instanceof ProfissionaldataEntity)) {
            return false;
        }
        ProfissionaldataEntity other = (ProfissionaldataEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Profissionaldata[ id=" + id + " ]";
    }
    
}
