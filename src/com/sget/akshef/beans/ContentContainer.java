package com.sget.akshef.beans;

import java.util.ArrayList;
import java.util.List;

import com.sget.akshef.hibernate.beans.ContentDetailsBean;

public class ContentContainer
{
	private List<ContentDetailsBean> data = new ArrayList<ContentDetailsBean>();
	private String code;
	private String message;
	public List<ContentDetailsBean> getData()
	{
		return data;
	}
	public void setData(List<ContentDetailsBean> data)
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
