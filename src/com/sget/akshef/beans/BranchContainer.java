package com.sget.akshef.beans;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.BranchBean;

public class BranchContainer
{
	private List<BranchBean> data = new ArrayList<BranchBean>();
	private String code;
	private String message;
	public List<BranchBean> getData()
	{
		return data;
	}
	public void setData(List<BranchBean> data)
	{
		this.data = data;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	
	
	
}
