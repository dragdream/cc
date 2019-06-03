package com.tianee.webframe.util.thread;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求信息对象，用于存放一个请求中或者一个线程中的所需数据信息
 * @author kakalion
 *
 */
public class TeeRequestInfo {
	//用户主键
	private int userSid;
	//用户名
	private String userName;
	//用户ID
	private String userId;
	//用户IP
	private String ipAddress;
	//请求数据对象
	private Map request = new HashMap();
	//session数据对象
	private Map session = new HashMap();
	//日志模型
	private Map logModel = new HashMap();
	//日志第一次记录
	private boolean loggingFirst = false;
	
	public int getUserSid() {
		return userSid;
	}
	public void setUserSid(int userSid) {
		this.userSid = userSid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Map getLogModel() {
		return logModel;
	}
	public void setLogModel(Map logModel) {
		this.logModel = logModel;
	}
	public Map getRequest() {
		return request;
	}
	public void setRequest(Map request) {
		this.request = request;
	}
	public void setSession(Map session) {
		this.session = session;
	}
	public Map getSession() {
		return session;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setLoggingFirst(boolean loggingFirst) {
		this.loggingFirst = loggingFirst;
	}
	public boolean isLoggingFirst() {
		return loggingFirst;
	}
	
}
