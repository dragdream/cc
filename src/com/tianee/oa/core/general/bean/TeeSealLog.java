package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "SEAL_LOG")
public class TeeSealLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SEAL_LOG_seq_gen")
	@SequenceGenerator(name="SEAL_LOG_seq_gen", sequenceName="SEAL_LOG_seq")
	private int sid;//自增id
	
	@Column(name="SEAL_ID")
	private int sealId;//印章sid 外键
	
	@Column(name="SEAL_NAME",length = 500)
	private String sealName;//印章名称
	
	@Column(name="USER_ID")
	private int userId;//用户人员
	
	@Column(name="USER_NAME" ,length = 50)
	private String userName;//用户人员姓名
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LOG_TIME")
	private Calendar logTime;//操作时间
	
	@Column(name="LOG_TYPE")
	private int logType;//操作类型
	
	@Column(name="RESULT" ,length = 500)
	private String  result;//描述
	
	@Column(name="MAC_ADD",length = 50)
	private String  macAdd;//max地址
	
	@Column(name="IP_ADD",length = 50)
	private String  ipAdd;//IP

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSealId() {
		return sealId;
	}

	public void setSealId(int sealId) {
		this.sealId = sealId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Calendar getLogTime() {
		return logTime;
	}

	public void setLogTime(Calendar logTime) {
		this.logTime = logTime;
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMacAdd() {
		return macAdd;
	}

	public void setMacAdd(String macAdd) {
		this.macAdd = macAdd;
	}

	public String getIpAdd() {
		return ipAdd;
	}

	public void setIpAdd(String ipAdd) {
		this.ipAdd = ipAdd;
	}

	public String getSealName() {
		return sealName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	
	
	
}



	
