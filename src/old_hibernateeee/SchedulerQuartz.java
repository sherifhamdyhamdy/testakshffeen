package old_hibernateeee;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SchedulerQuartz entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "schedulerquartz")
public class SchedulerQuartz implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	
	private Timestamp actionDate;
		
	// Constructors

	/** default constructor */
	public SchedulerQuartz() {
	}

	public SchedulerQuartz(int id) {
		this.id = id;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@Column(name = "title", length = 45)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Column(name = "actionDate", nullable = false, length = 19)
	public Timestamp getActionDate() {
		return this.actionDate;
	}

	public void setActionDate(Timestamp actionDate) {
		this.actionDate = actionDate;
	}
	
}