package com.sget.akshef.hibernate.beans;

import java.io.Serializable;

/**
 *
 * @author JDeeb
 */

public class KeywordBean implements Serializable,Cloneable 
{
    private static final long serialVersionUID = 1L;
   
    private Integer id;
    private String name;
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
    
   
    
}
