package com.sget.akshef.hibernate.entities;

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
@Entity(name = "branch_schedule")
public class Branch_scheduleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "category_id")
    private Integer category_id;
    
    @Column(name = "unit_id")
    private Integer unit_id;
    
    @Column(name = "day_id")
    private Integer day_id;
    
    @Column(name = "from_hour")
    private String from_hour;
    
    @Column(name = "to_hour")
    private String to_hour;

    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private BranchEntity branch = new BranchEntity();

    
    @Transient
    private int from_day;
    
    @Transient
    private int to_day;
    
    
    public Branch_scheduleEntity() {
    }

    public Branch_scheduleEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    
    public Integer getCategory_id()
	{
		return category_id;
	}

	public void setCategory_id(Integer category_id)
	{
		this.category_id = category_id;
	}

	public Integer getUnit_id()
	{
		return unit_id;
	}

	public void setUnit_id(Integer unit_id)
	{
		this.unit_id = unit_id;
	}

    public Integer getDay_id()
	{
		return day_id;
	}

	public void setDay_id(Integer day_id)
	{
		this.day_id = day_id;
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
        if (!(object instanceof Branch_scheduleEntity)) {
            return false;
        }
        Branch_scheduleEntity other = (Branch_scheduleEntity) object;
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
