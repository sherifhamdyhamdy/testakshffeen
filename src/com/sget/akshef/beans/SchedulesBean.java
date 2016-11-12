package com.sget.akshef.beans;

import java.util.ArrayList;
import java.util.List;

public class SchedulesBean
{
	private List<ScheduleBean> scheduleList = new ArrayList<ScheduleBean>();
	
	private List<Integer> vacations = new ArrayList<Integer>();

	

	public List<ScheduleBean> getScheduleList()
	{
		return scheduleList;
	}

	public void setScheduleList(List<ScheduleBean> scheduleList)
	{
		this.scheduleList = scheduleList;
	}

	public List<Integer> getVacations()
	{
		return vacations;
	}

	public void setVacations(List<Integer> vacations)
	{
		this.vacations = vacations;
	}

	
		
}
