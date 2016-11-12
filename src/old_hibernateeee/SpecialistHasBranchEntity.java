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
@Entity(name = "specialist_has_branch")
public class SpecialistHasBranchEntity implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialistHasBranch", fetch = FetchType.LAZY)
    private Set<ScheduleHasDaysEntity> scheduleHasDaysSet;
    
    @JoinColumn(name = "specialist_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private SpecialistEntity specialist;
    
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private BranchEntity branch;

    public SpecialistHasBranchEntity() {
    }

    public SpecialistHasBranchEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<ScheduleHasDaysEntity> getScheduleHasDaysSet() {
        return scheduleHasDaysSet;
    }

    public void setScheduleHasDaysSet(Set<ScheduleHasDaysEntity> scheduleHasDaysSet) {
        this.scheduleHasDaysSet = scheduleHasDaysSet;
    }

    public SpecialistEntity getSpecialist() {
        return specialist;
    }

    public void setSpecialist(SpecialistEntity specialist) {
        this.specialist = specialist;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
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
        if (!(object instanceof SpecialistHasBranchEntity)) {
            return false;
        }
        SpecialistHasBranchEntity other = (SpecialistHasBranchEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.SpecialistHasBranch[ id=" + id + " ]";
    }
    
}
