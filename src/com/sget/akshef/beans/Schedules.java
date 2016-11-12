package com.sget.akshef.beans;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Schedules
{
	private Map<String, TreeSet<Integer>> period_schedules_map = new HashMap<String, TreeSet<Integer>>();
	private Map<String, TreeSet<Integer>> days_schedules_map = new HashMap<String, TreeSet<Integer>>();
	private Set<Integer> working_days = new HashSet<Integer>();
	
	public Map<String, TreeSet<Integer>> getPeriod_schedules_map()
	{
		return period_schedules_map;
	}
	public void setPeriod_schedules_map(Map<String, TreeSet<Integer>> period_schedules_map)
	{
		this.period_schedules_map = period_schedules_map;
	}
	public Map<String, TreeSet<Integer>> getDays_schedules_map()
	{
		return days_schedules_map;
	}
	public void setDays_schedules_map(Map<String, TreeSet<Integer>> days_schedules_map)
	{
		this.days_schedules_map = days_schedules_map;
	}
	public Set<Integer> getWorking_days()
	{
		return working_days;
	}
	public void setWorking_days(Set<Integer> working_days)
	{
		this.working_days = working_days;
	}
	
	
}
