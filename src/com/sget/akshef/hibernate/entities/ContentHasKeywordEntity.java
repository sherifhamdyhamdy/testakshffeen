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
 * @author abdelrhim
 */
@Entity(name = "content_has_keyword")
public class ContentHasKeywordEntity implements Serializable 
{
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "content_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ContentDetailsEntity content;
    
    @JoinColumn(name = "keyword_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private KeywordEntity keyword;

    public ContentHasKeywordEntity() {
    }

    public ContentHasKeywordEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContentDetailsEntity getContent() {
        return content;
    }

    public void setContent(ContentDetailsEntity content) {
        this.content = content;
    }

    public KeywordEntity getKeyword() {
        return keyword;
    }

    public void setKeyword(KeywordEntity keyword) {
        this.keyword = keyword;
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
        if (!(object instanceof ContentHasKeywordEntity)) {
            return false;
        }
        ContentHasKeywordEntity other = (ContentHasKeywordEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.ContentHasKeywordEntity[ id=" + id + " ]";
    }
    
}
