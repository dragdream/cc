package com.tianee.webframe.bean;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class TeeBaseEntity {
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String sid;
	
	@Column(name="CR_TIME")
	private Calendar createTime = Calendar.getInstance();
	
	@Column(name="CR_USER_ID")
	private String crUserId;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCrUserId(String crUserId) {
		this.crUserId = crUserId;
	}

	public String getCrUserId() {
		return crUserId;
	}
	
}
