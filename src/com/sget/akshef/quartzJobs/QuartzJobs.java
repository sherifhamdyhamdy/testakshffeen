package com.sget.akshef.quartzJobs;

import java.util.Random;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.akshffeen.utils.Utils;
import com.sget.akshef.hibernate.dao.SchedulerQuartzDAO;
import com.sget.akshef.hibernate.entities.SchedulerQuartz;
import com.sget.akshef.utils.Performance;


public class QuartzJobs implements Job
{
	private SchedulerQuartzDAO schedulerquartzDao = SchedulerQuartzDAO.getInstance();
	private Utils utils = Utils.getInstance();

	public QuartzJobs() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public void execute(JobExecutionContext context)throws JobExecutionException 
	{
		JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
		String job_type = (String) jobDataMap.get("job_type");
//		System.out.println("QuartzJobs.execute() job_type : "+job_type);
		
		Performance.releaseMemory();
		
		if(job_type.equals("schedulerQuartz"))
		{
			schedulerQuartz();
		}
	}
	
	
	public void schedulerQuartz()
	{
//		System.out.println("<<<<<<<<<<<<<<<<<<<<<QuartzJobs.schedulerQuartz()>>>>>>>>>>>>>>>>>>>>>");
		
		Random r = new Random();
		int rand = r.nextInt();
		
		SchedulerQuartz schedulerquartz = new SchedulerQuartz();
		schedulerquartz.setTitle("Scheduler Services "+rand);
		schedulerquartz.setActionDate(utils.getCurrentTimeStamp());

		boolean action = schedulerquartzDao.save(schedulerquartz);
//		System.out.println("Action : "+action);
	    
		Performance.releaseMemory();
	}
	
}
