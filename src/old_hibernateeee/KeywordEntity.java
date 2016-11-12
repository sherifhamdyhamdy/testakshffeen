package old_hibernateeee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
/**
 * @author abdelrhim
 *
 */
@Entity(name = "keyword")
public class KeywordEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;

    @JoinColumn(name = "keyword_topic_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private KeywordTopicEntity keyword_topic = new KeywordTopicEntity();
    
    @JoinColumn(name = "keyword_category_id", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private KeywordCategoryEntity keyword_category = new KeywordCategoryEntity();
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keyword", fetch = FetchType.LAZY)
    private List<BranchHasKeywordEntity> branchHasKeywordList = new ArrayList<BranchHasKeywordEntity>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keyword", fetch = FetchType.LAZY)
    private List<DoctorHasKeywordEntity> doctorHasKeywordList = new ArrayList<DoctorHasKeywordEntity>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keyword", fetch = FetchType.LAZY)
    private List<ContentHasKeywordEntity> contentHasKeywordList = new ArrayList<ContentHasKeywordEntity>();
    
    
    public KeywordEntity() {
    }

    public KeywordEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public KeywordTopicEntity getKeyword_topic()
	{
		return keyword_topic;
	}

	public void setKeyword_topic(KeywordTopicEntity keyword_topic)
	{
		this.keyword_topic = keyword_topic;
	}

	public KeywordCategoryEntity getKeyword_category()
	{
		return keyword_category;
	}

	public void setKeyword_category(KeywordCategoryEntity keyword_category)
	{
		this.keyword_category = keyword_category;
	}

	public List<BranchHasKeywordEntity> getBranchHasKeywordList()
	{
		return branchHasKeywordList;
	}

	public void setBranchHasKeywordList(List<BranchHasKeywordEntity> branchHasKeywordList)
	{
		this.branchHasKeywordList = branchHasKeywordList;
	}

	public List<DoctorHasKeywordEntity> getDoctorHasKeywordList()
	{
		return doctorHasKeywordList;
	}

	public void setDoctorHasKeywordList(List<DoctorHasKeywordEntity> doctorHasKeywordList)
	{
		this.doctorHasKeywordList = doctorHasKeywordList;
	}

	
    
}
