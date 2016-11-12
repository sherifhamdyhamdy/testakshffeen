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
import javax.persistence.Transient;

/**
 *
 * @author JDeeb
 */
@Entity(name = "doctor_schedule")
public class Doctor_scheduleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "from_hour")
    private String from_hour;
    
    @Column(name = "to_hour")
    private String to_hour;

    @Column(name = "day_id")
    private Integer day_id;
    
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private SpecialistEntity doctor = new SpecialistEntity();
    
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private BranchEntity branch = new BranchEntity();

    
    @Transient
    private int from_day;
    
    @Transient
    private int to_day;
    
    public Doctor_scheduleEntity() {
    }

    public Doctor_scheduleEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom_hour() {
        return from_hour;
    }

    public void setFrom_hour(String from_hour) {
        this.from_hour = from_hour;
    }

    public String getTo_hour() {
        return to_hour;
    }

    public void setTo_hour(String to_hour) {
        this.to_hour = to_hour;
    }

    
    public Integer getDay_id()
	{
		return day_id;
	}

	public void setDay_id(Integer day_id)
	{
		this.day_id = day_id;
	}

	public SpecialistEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(SpecialistEntity doctor) {
        this.doctor = doctor;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
    }

    
	public int getFrom_day()
	{
		return from_day;
	}

	public void setFrom_day(int from_day)
	{
		this.from_day = from_day;
	}

	public int getTo_day()
	{
		return to_day;
	}

	public void setTo_day(int to_day)
	{
		this.to_day = to_day;
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
        if (!(object instanceof Doctor_scheduleEntity)) {
            return false;
        }
        Doctor_scheduleEntity other = (Doctor_scheduleEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Messages[ id=" + id + " ]";
    }
    
}
