package com.tianee.webframe.util.job;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.tianee.oa.core.base.job.bean.TeeJob;

/**
 * 作业管理器
 * @author liteng
 *
 */
public class TeeJobManager {
	
	/**
	 * 
	 */
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	
	/**
	 * 开始作业
	 * @param job
	 */
	public static synchronized void start(TeeJob job){
		
		Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//加入作业并执行
		try{
			
			JobDetail jobDetail = new JobDetail("jd"+job.getId(), "jg"+job.getId(), TeeJob.class);
			jobDetail.getJobDataMap().put("job", job);
			CronTrigger trigger  =   new  CronTrigger( "ir"+job.getId() ,  "rg"+job.getId() );
			
			scheduler = schedulerFactory.getScheduler();
			trigger.setCronExpression(job.getExp());
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * 停止作业
	 * @param id
	 */
	public static synchronized void stop(String id){
		Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//停止作业
		try{
			scheduler.unscheduleJob("ir"+id, "rg"+id);
			//移除作业
			scheduler.deleteJob("jd"+id, "jg"+id);
		}catch(Exception ex){
			
		}
	}
	
}
