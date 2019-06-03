package com.tianee.webframe.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tianee.webframe.util.global.TeeSysProps;

public class TeeSmsSenderTimerThreadPool {

	//单例模式
	private static TeeSmsSenderTimerThreadPool smsSenderTimerThreadPool = new TeeSmsSenderTimerThreadPool();
	//线程池对象
	private ExecutorService threadPool = null;
	//构造方法，创建线程池对象
	TeeSmsSenderTimerThreadPool(){
		threadPool = Executors.newFixedThreadPool(TeeSysProps.getInt("SMS_PHONE_THREAD_NUMBER"));
	}
	
	/**
	 * 获取单例实例
	 * @return
	 */
	public static TeeSmsSenderTimerThreadPool getInstance(){
		return smsSenderTimerThreadPool;
	}
	
	public void execute(Runnable runnable){
		threadPool.execute(runnable);
	}
}
