package com.tianee.webframe.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tianee.webframe.util.global.TeeSysProps;

public class TeeWebMailSendTimerThreadPool {

	//单例模式
	private static TeeWebMailSendTimerThreadPool webMailSendTimerThreadPool = new TeeWebMailSendTimerThreadPool();
	//线程池对象
	private ExecutorService threadPool = null;
	//构造方法，创建线程池对象
	TeeWebMailSendTimerThreadPool(){
		threadPool = Executors.newFixedThreadPool(TeeSysProps.getInt("WEB_MAIL_SEND_THREAD_NUMBER"));
	}
	
	/**
	 * 获取单例实例
	 * @return
	 */
	public static TeeWebMailSendTimerThreadPool getInstance(){
		return webMailSendTimerThreadPool;
	}
	
	public void execute(Runnable runnable){
		threadPool.execute(runnable);
	}
}
