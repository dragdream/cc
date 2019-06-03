package com.tianee.webframe.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 外部邮箱线程池
 * @author xsy
 *
 */
public class TeeWebMailThreadPool {

	//单例模式
		private static TeeWebMailThreadPool webMailThreadPool = new TeeWebMailThreadPool();
		//线程池对象
		private ExecutorService threadPool = null;
		//构造方法，创建线程池对象
		TeeWebMailThreadPool(){
			threadPool = Executors.newFixedThreadPool(TeeSysProps.getInt("WEB_MAIL_THREAD_NUMBER"));
		}
		
		/**
		 * 获取单例实例
		 * @return
		 */
		public static TeeWebMailThreadPool getInstance(){
			return webMailThreadPool;
		}
		
		public void execute(Runnable runnable){
			threadPool.execute(runnable);
		}
		
}
