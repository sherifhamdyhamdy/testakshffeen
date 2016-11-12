package com.sget.akshef.hibernate.beans;

/**
 * Author JDeeb
 */
public class BranchScheduleBean implements java.io.Serializable 
{
	private static final long serialVersionUID = -6312094910317657477L;
	
    private Integer id;
    private String from_hour;
    private String to_hour;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getFrom_hour()
	{
		return from_hour;
	}

	public void setFrom_hour(String from_hour)
	{
		this.from_hour = from_hour;
	}

	public String getTo_hour()
	{
		return to_hour;
	}

	public void setTo_hour(String to_hour)
	{
		this.to_hour = to_hour;
	}
    
    
	
}
