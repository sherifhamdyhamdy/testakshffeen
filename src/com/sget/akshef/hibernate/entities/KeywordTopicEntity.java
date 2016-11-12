package com.sget.akshef.hibernate.entities;

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
import javax.persistence.OneToMany;

/**
 *
 * @author JDeeb
 */
@Entity(name = "keyword_topic")
public class KeywordTopicEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "keyword_topic", fetch = FetchType.LAZY)
    private List<KeywordEntity> keywordList = new ArrayList<KeywordEntity>();

    public KeywordTopicEntity() {
    }

    public KeywordTopicEntity(Integer id) {
        this.id = id;
    }

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
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

	public List<KeywordEntity> getKeywordList()
	{
		return keywordList;
	}

	public void setKeywordList(List<KeywordEntity> keywordList)
	{
		this.keywordList = keywordList;
	}

   
}
