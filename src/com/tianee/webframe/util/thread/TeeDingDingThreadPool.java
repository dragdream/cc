package com.tianee.webframe.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 钉钉线程池
 * @author xsy
 *
 */
public class TeeDingDingThreadPool {
	//单例模式
	private static TeeDingDingThreadPool dingDingThreadPool = new TeeDingDingThreadPool();
	//线程池对象
	private ExecutorService threadPool = null;
	//构造方法，创建线程池对象
	TeeDingDingThreadPool(){
		threadPool = Executors.newFixedThreadPool(TeeSysProps.getInt("DING_DING_THREAD_NUMBER"));
	}
	
	/**
	 * 获取单例实例
	 * @return
	 */
	public static TeeDingDingThreadPool getInstance(){
		return dingDingThreadPool;
	}
	
	public void execute(Runnable runnable){
		threadPool.execute(runnable);
	}
	
	
}
