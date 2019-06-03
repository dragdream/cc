package com.tianee.webframe.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tianee.webframe.util.global.TeeSysProps;

/**
 * 微信线程池
 * @author xsy
 *
 */
public class TeeWeiXinThreadPool {

	    //单例模式
		private static TeeWeiXinThreadPool weiXinThreadPool = new TeeWeiXinThreadPool();
		//线程池对象
		private ExecutorService threadPool = null;
		//构造方法，创建线程池对象
		TeeWeiXinThreadPool(){
			threadPool = Executors.newFixedThreadPool(TeeSysProps.getInt("WEI_XIN_THREAD_NUMBER"));
		}
		
		/**
		 * 获取单例实例
		 * @return
		 */
		public static TeeWeiXinThreadPool getInstance(){
			return weiXinThreadPool;
		}
		
		public void execute(Runnable runnable){
			threadPool.execute(runnable);
		}
}
