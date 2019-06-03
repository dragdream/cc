package com.tianee.webframe.servlet.bean;

import java.io.Serializable;

/**
 * 用户会话信息对象
 * @author kakalion
 *
 */
public class TeeUserSessionInfo implements Serializable{
	/**
	 * 会话ID
	 */
	private String sessionId;
	
	/**
	 * 用户会话信息标识
	 */
	private Object userSessionInfoKey;

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setUserSessionInfoKey(Object userSessionInfoKey) {
		this.userSessionInfoKey = userSessionInfoKey;
	}

	public Object getUserSessionInfoKey() {
		return userSessionInfoKey;
	}
	
}
