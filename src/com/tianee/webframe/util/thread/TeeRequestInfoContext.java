package com.tianee.webframe.util.thread;

public class TeeRequestInfoContext {
	private static ThreadLocal<TeeRequestInfo> globalInfo = new ThreadLocal<TeeRequestInfo>();
	
	public static TeeRequestInfo getRequestInfo(){
		return globalInfo.get();
	}
	
	public static void setRequestInfo(TeeRequestInfo requestInfo){
		globalInfo.set(requestInfo);
	}
	
	public static void clear(){
		globalInfo.remove();
	}
}
