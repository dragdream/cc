package com.tianee.oa.core.org.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="PERSON_DYNAMIC_INFO")
public class TeePersonDynamicInfo implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PERSON_DYNAMIC_INFO_seq_gen")
	@SequenceGenerator(name="PERSON_DYNAMIC_INFO_seq_gen", sequenceName="PERSON_DYNAMIC_INFO_seq")
	private int sid;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_VISIT_TIME")
	private Date lastVisitTime;//上次访问系统的时间
	
	@Column(name="ONLINE_TIME")
	private long online; //在线时长
	
	@Column(name="LAST_VISIT_IP",length=100)
	private String lastVisitIp;//上次访问系统的IP
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Date getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	public long getOnline() {
		return online;
	}

	public void setOnline(long online) {
		this.online = online;
	}

	public String getLastVisitIp() {
		return lastVisitIp;
	}

	public void setLastVisitIp(String lastVisitIp) {
		this.lastVisitIp = lastVisitIp;
	}
	
	
}
