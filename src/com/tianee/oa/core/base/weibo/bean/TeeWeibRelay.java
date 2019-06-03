package com.tianee.oa.core.base.weibo.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 转发实体类
 * */
@Entity
@Table(name="weib_relay")
public class TeeWeibRelay {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_relay_seq_gen")
	@SequenceGenerator(name="weib_relay_seq_gen", sequenceName="weib_relay_seq")
	@Column(name = "SID")
	private int sid;//转发主键
	
	@Column(name="INFO_ID")
    private int infoId;//发布信息id
	
	@Column(name="USER_ID")
	private int userId;//转发人id
	
	@Column(name="CRE_TIME")
	private Date creTime;//转发时间
	
	@Column(name="RELAY_ID")
	private int relayId;//转发信息id
	
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreTime() {
		return creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}

	public int getRelayId() {
		return relayId;
	}

	public void setRelayId(int relayId) {
		this.relayId = relayId;
	}

	


}
