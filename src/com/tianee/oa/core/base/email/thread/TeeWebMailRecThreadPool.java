package com.tianee.oa.core.base.email.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 外部邮件接收的定时任务线程池
 * @author LiTeng
 *
 */
public class TeeWebMailRecThreadPool {
	
	private static TeeWebMailRecThreadPool pool = new TeeWebMailRecThreadPool();
	private ScheduledExecutorService scheduledThreadPool = null;
	
	/*
	 * 任务表
	 */
	private Map<String,ScheduledFuture> taskMap = null;
	
	TeeWebMailRecThreadPool(){
		int poolSize = TeeSysProps.getInt("WEB_MAIL_REC_POOL_SIZE");
		scheduledThreadPool = Executors.newScheduledThreadPool(poolSize);
		taskMap = new HashMap<String,ScheduledFuture>();
	}
	
	public static TeeWebMailRecThreadPool getInstance(){
		return pool;
	}
	
	/**
	 * 执行线程方法
	 * @param mailRecProcess
	 */
	public void execute(TeeWebMailRecProcess mailRecProcess){
		if(!taskMap.containsKey(mailRecProcess.getId())){//如果任务表中包含指定id的任务，则不进行重复插入
			int delay = TeeSysProps.getInt("WEB_MAIL_REC_DELAY");
			ScheduledFuture<?> d = scheduledThreadPool.scheduleWithFixedDelay(mailRecProcess, 0, delay, TimeUnit.SECONDS);
			taskMap.put(mailRecProcess.getId(), d);
		}
	}
	
	/**
	 * 停止指定id标识的任务
	 * @param id
	 */
	public void stop(String id){
		if(taskMap.containsKey(id)){//如果存在指定的任务
			ScheduledFuture<?> d = taskMap.get(id);
			d.cancel(true);
			taskMap.remove(id);
		}
	}
	
	public List<String> findNotExistsTaskIds(List<String> ids){
		List<String> list = new ArrayList();
		Set<String> keys = taskMap.keySet();
		for(String key:keys){
			if(!ids.contains(key)){
				list.add(key);
			}
		}
		return list;
	}
}
