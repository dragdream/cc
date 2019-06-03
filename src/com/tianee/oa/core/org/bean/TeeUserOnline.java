package com.tianee.oa.core.org.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="USER_ONLINE")
public class TeeUserOnline {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="USER_ONLINE_seq_gen")
	@SequenceGenerator(name="USER_ONLINE_seq_gen", sequenceName="USER_ONLINE_seq")
	@Column(name="SID")
	private int sid;

	
	@Column(name="SESSION_TOKEN")
	private String sessionToken;//SESSION_TOKEN
	
	
	@Column(name="USER_ID")
	private int userId;//

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginTime;//LOGIN_TIME
	
	@Column(name="USER_STATUS")
	private int userStatus;
	
	@Column(name="CLIENT")
	private String client;//客户端
	
	@Column(name="IP_")
	private String ip;//ip
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
