package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;


@SuppressWarnings("serial")
@Entity
@Table(name = "SYS_LOG")
public class TeeSysLog {
	
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="PERSON_ID")
	private Integer personId;//人员uuid，不用对象型
	
	@Column(name="USER_NAME",length=32)
	private String userName;
	
	@Column(name="USER_ID",length=32)
	private String userId;
	
	@Column(name="TIME")
	private Calendar time;//操作时间
	
	@Column(name="IP",length=200)
	private String ip;//记录ip
	
	@Column(name="TYPE",length=30)
	private String type;//记录操作类型，要和系统代码表关联
	
	@Column(name="REMARK",length=2000)
	private String remark;//备注
	
	@Column(name="ERROR_LOG",length=2000)
	private String errorLog;//自动截获访问action的记录
	
	@Column(name="ERROR_FLAG")
	private int errorFlag;//错误标记

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
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

	public int getErrorFlag() {
		return errorFlag;
	}

	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}
	
	public static TeeSysLog newInstance(){
		TeeSysLog sysLog = new TeeSysLog();
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		if(info!=null){
			sysLog.setIp(info.getIpAddress());
			sysLog.setPersonId(info.getUserSid());
			sysLog.setUserId(info.getUserId());
			sysLog.setUserName(info.getUserName());
			sysLog.setTime(Calendar.getInstance());
		}
		return sysLog;
	}
	
}
