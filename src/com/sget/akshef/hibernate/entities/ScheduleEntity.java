package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author JDeeb
 */
@Entity(name = "schedule")
public class ScheduleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "from_time")
    @Temporal(TemporalType.TIME)
    private Date from_date;
    @Column(name = "to_time")
    @Temporal(TemporalType.TIME)
    private Date to_date;
    @Column(name = "active")
    private Integer active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "schedule", fetch = FetchType.LAZY)
    private Set<ScheduleHasDaysEntity> scheduleHasDaysSet;

    public ScheduleEntity() {
    }

    public ScheduleEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Date getFrom_date() {
		return from_date;
	}

	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	public Integer getActive() {
		return active != null ? active : 0;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Set<ScheduleHasDaysEntity> getScheduleHasDaysSet() {
        return scheduleHasDaysSet;
    }

    public void setScheduleHasDaysSet(Set<ScheduleHasDaysEntity> scheduleHasDaysSet) {
        this.scheduleHasDaysSet = scheduleHasDaysSet;
    }
    
}
