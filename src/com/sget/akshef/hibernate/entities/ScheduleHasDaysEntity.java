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

/**
 *
 * @author JDeeb
 */
@Entity(name = "schedule_has_days")
public class ScheduleHasDaysEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "specialist_has_branch_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private SpecialistHasBranchEntity specialistHasBranch;
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private ScheduleEntity schedule;
    @JoinColumn(name = "days_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private DaysEntity days;

    public ScheduleHasDaysEntity() {
    }

    public ScheduleHasDaysEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SpecialistHasBranchEntity getSpecialistHasBranch() {
        return specialistHasBranch;
    }

    public void setSpecialistHasBranch(SpecialistHasBranchEntity specialistHasBranch) {
        this.specialistHasBranch = specialistHasBranch;
    }

    public ScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public DaysEntity getDays() {
        return days;
    }

    public void setDays(DaysEntity days) {
        this.days = days;
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
        if (!(object instanceof ScheduleHasDaysEntity)) {
            return false;
        }
        ScheduleHasDaysEntity other = (ScheduleHasDaysEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.ScheduleHasDays[ id=" + id + " ]";
    }
    
}
