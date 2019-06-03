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
 * 收藏实体类
 * */
@Entity
@Table(name="weib_collect")
public class TeeWeibConllect {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_collect_seq_gen")
	@SequenceGenerator(name="weib_collect_seq_gen", sequenceName="weib_collect_seq")
	@Column(name = "SID")
	private int sid;//收藏主键
	
	@Column(name="INFO_ID")
    private int infoId;//发布信息id
	
	@Column(name="USER_ID")
	private int userId;//收藏人id
	
	@Column(name="INFO_TIME")
	private Date infoTime;//收藏时间

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

	public Date getInfoTime() {
		return infoTime;
	}

	public void setInfoTime(Date infoTime) {
		this.infoTime = infoTime;
	}
	
	
}
