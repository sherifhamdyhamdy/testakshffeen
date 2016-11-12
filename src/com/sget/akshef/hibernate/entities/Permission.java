package com.sget.akshef.hibernate.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author JDeeb
 */
@Entity(name = "permission")
public class Permission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "page")
    private String page;
    
    @Column(name = "contain")
    private String contain;
        
    @Column(name = "visible")
    private Integer visible;
      
    @Column(name = "type")
    private String type;
    
    @Column(name = "active")
    private Integer active;
          
    @Column(name = "module")
    private String module;
    
    @Column(name = "priority")
    private Integer priority;
    
    public Permission() {
    }

    public Permission(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getContain()
	{
		return contain;
	}

	public void setContain(String contain)
	{
		this.contain = contain;
	}

		
	public Integer getVisible()
	{
		return visible;
	}

	public void setVisible(Integer visible)
	{
		this.visible = visible;
	}


	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public Integer getActive() {
    	return active != null ? active : 0;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

        
	public String getModule()
	{
		return module;
	}

	public void setModule(String module)
	{
		this.module = module;
	}

	public Integer getPriority()
	{
		return priority;
	}

	public void setPriority(Integer priority)
	{
		this.priority = priority;
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
        if (!(object instanceof Permission)) {
            return false;
        }
        Permission other = (Permission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.egcode.akshef.entities.Permission[ id=" + id + " ]";
    }

    
    
}
