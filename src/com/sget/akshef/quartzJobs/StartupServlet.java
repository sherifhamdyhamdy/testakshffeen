package com.sget.akshef.quartzJobs;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.sget.akshef.hibernate.dao.SchedulerQuartzDAO;
import com.sget.akshef.hibernate.entities.SchedulerQuartz;
import com.sget.akshef.utils.Performance;

public class StartupServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    @Override
    public void init() throws ServletException 
    {
    	System.out.println("StartupServlet.init()");
    	
		try 
		{
			main();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	// TODO Auto-generated method stub
    	super.init();
    }
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SchedulerQuartzDAO schedulerquartzDao = SchedulerQuartzDAO.getInstance();

		List<SchedulerQuartz> schedulers = schedulerquartzDao.getAllSchedulers();
					
		StringBuilder schedulerQuartz = new StringBuilder();
		
		if(schedulers!=null && schedulers.size()>0)
			for(SchedulerQuartz scheduler : schedulers)
			{
				schedulerQuartz.append(scheduler.getTitle() + " in " + scheduler.getActionDate() + "\r\n");
			}
		else
			schedulerQuartz.append("SchedulerQuartz hasn't values");
		
		Performance.releaseMemory();
		
		response.getWriter().println(schedulerQuartz);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	
	public static void main() throws Exception 
	{
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();

		scheduler.start();
		
		Random r = new Random();
		int rand = r.nextInt();
		
//		System.out.println("r.nextInt() ------------------------ > "+r.nextInt());
		
		String group = "group"+rand;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JobDataMap jobDataMap1 = new JobDataMap();
		jobDataMap1.put("job_type", "schedulerQuartz");
		
		JobDetail job1 = JobBuilder.newJob(QuartzJobs.class).withIdentity("job"+rand, group).build();

		// Trigger the job to run on the next round minute
		Trigger trigger1 = TriggerBuilder
				.newTrigger()
				.withIdentity("trigger"+rand, group)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInHours(1)			//1 hours
//								.withIntervalInMinutes(2)		//2 min
//								.withIntervalInSeconds(20)		//20 seconds
								.repeatForever())
								.usingJobData(jobDataMap1)
								.build();

		// schedule it
//		scheduler = new StdSchedulerFactory().getScheduler();
//		scheduler.start();
		scheduler.scheduleJob(job1, trigger1);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	}
	
}
