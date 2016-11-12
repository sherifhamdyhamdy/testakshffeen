package old_hibernateeee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Admin entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "admin")
public class Admin implements java.io.Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer userId;
	private String username;
	private String password;
	private String type;				//1 -- > admin			2 -- > Unit			3 -- > Branch			2,3 -- > Unit_Branch

	private Integer groupId;
//	private Integer unitId;
	
	private String permissions_ids;
	private String unitsUpdate;
	private String unitsOwner;
	private String units_ids;

	private String branchesUpdate;
	private String branchesDelete;
	

            
	private Integer doctorId;

	
	private String insurancesUpdate;
	private String insurancesOwner;
	private String insurance_permissions_ids;
    private String insurances_ids;

	
	// Constructors

	/** default constructor */
	public Admin() {
	}

	/** full constructor */
	public Admin(String username, String password, String type) 
	{
		this.username = username;
		this.password = password;
		this.type = type;
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
	
	
	@Column(name = "userId")
	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}


	@Column(name = "username", length = 200)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 200)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "type", length = 200)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Column(name = "groupId")
	public Integer getGroupId()
	{
		return groupId;
	}

	public void setGroupId(Integer groupId)
	{
		this.groupId = groupId;
	}
	
	@Column(name = "unitsUpdate", length = 200)
	public String getUnitsUpdate()
	{
		return unitsUpdate;
	}

	public void setUnitsUpdate(String unitsUpdate)
	{
		this.unitsUpdate = unitsUpdate;
	}

	
	@Column(name = "unitsOwner", length = 200)
	public String getUnitsOwner()
	{
		return unitsOwner;
	}

	public void setUnitsOwner(String unitsOwner)
	{
		this.unitsOwner = unitsOwner;
	}
	
	@Column(name = "permissions_ids", length = 5000)
	public String getPermissions_ids()
	{
		return permissions_ids;
	}

	public void setPermissions_ids(String permissions_ids)
	{
		this.permissions_ids = permissions_ids;
	}
	
    @Column(name = "units_ids", length = 250)
	public String getUnits_ids()
	{
		return units_ids;
	}

	public void setUnits_ids(String units_ids)
	{
		this.units_ids = units_ids;
	}
	
	
	@Column(name = "branchesUpdate", length = 200)
	public String getBranchesUpdate()
	{
		return branchesUpdate;
	}

	public void setBranchesUpdate(String branchesUpdate)
	{
		this.branchesUpdate = branchesUpdate;
	}

	
	@Column(name = "branchesDelete", length = 200)
	public String getBranchesDelete()
	{
		return branchesDelete;
	}

	public void setBranchesDelete(String branchesDelete)
	{
		this.branchesDelete = branchesDelete;
	}
	
	
	


	@Column(name = "doctorId")
	public Integer getDoctorId()
	{
		return doctorId;
	}

	public void setDoctorId(Integer doctorId)
	{
		this.doctorId = doctorId;
	}
	
	


	
	
	
	
	
	
	
	@Column(name = "insurancesUpdate", length = 200)
	public String getInsurancesUpdate()
	{
		return insurancesUpdate;
	}
	public void setInsurancesUpdate(String insurancesUpdate)
	{
		this.insurancesUpdate = insurancesUpdate;
	}

	@Column(name = "insurancesOwner", length = 200)
	public String getInsurancesOwner()
	{
		return insurancesOwner;
	}
	public void setInsurancesOwner(String insurancesOwner)
	{
		this.insurancesOwner = insurancesOwner;
	}

	@Column(name = "insurance_permissions_ids", length = 4000)
	public String getInsurance_permissions_ids()
	{
		return insurance_permissions_ids;
	}
	public void setInsurance_permissions_ids(String insurance_permissions_ids)
	{
		this.insurance_permissions_ids = insurance_permissions_ids;
	}

    @Column(name = "insurances_ids", length = 250)
	public String getInsurances_ids()
	{
		return insurances_ids;
	}
	public void setInsurances_ids(String insurances_ids)
	{
		this.insurances_ids = insurances_ids;
	}

	
	
	
	
	
	
}