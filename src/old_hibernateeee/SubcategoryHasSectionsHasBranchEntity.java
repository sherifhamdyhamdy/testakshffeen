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
@Entity(name = "subcategory_has_sections_has_branch")
public class SubcategoryHasSectionsHasBranchEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;
    @JoinColumn(name = "subcategorysection_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private SubcategoryHasSectionsEntity subcategoryHasSections;
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private BranchEntity branch;
	
    @Transient
    private int section_id;
    
    @Transient
    private String section_ar;
    
    @Transient
    private String section_en;
    
    
    public int getSection_id()
	{
		return section_id;
	}

	public void setSection_id(int section_id)
	{
		this.section_id = section_id;
	}


    
    public SubcategoryHasSectionsHasBranchEntity() {
    }

    public SubcategoryHasSectionsHasBranchEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public SubcategoryHasSectionsEntity getSubcategoryHasSections() {
        return subcategoryHasSections;
    }

    public void setSubcategoryHasSections(SubcategoryHasSectionsEntity subcategoryHasSections) {
        this.subcategoryHasSections = subcategoryHasSections;
    }

    public BranchEntity getBranch() {
        return branch;
    }

    public void setBranch(BranchEntity branch) {
        this.branch = branch;
    }

    


	public String getSection_ar()
	{
		return section_ar;
	}

	public void setSection_ar(String section_ar)
	{
		this.section_ar = section_ar;
	}

	public String getSection_en()
	{
		return section_en;
	}

	public void setSection_en(String section_en)
	{
		this.section_en = section_en;
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
        if (!(object instanceof SubcategoryHasSectionsHasBranchEntity)) {
            return false;
        }
        SubcategoryHasSectionsHasBranchEntity other = (SubcategoryHasSectionsHasBranchEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.SubcategoryHasSectionsHasBranch[ id=" + id + " ]";
    }
    
}
