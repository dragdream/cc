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
 * 关注实体类
 * */
@Entity
@Table(name="weib_guanzhu")
public class TeeWeibGuanZhu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_guanzhu_seq_gen")
	@SequenceGenerator(name="weib_guanzhu_seq_gen", sequenceName="weib_guanzhu_seq")
	@Column(name = "SID")
	private int sid;//关注主键
	
	@Column(name="USER_ID")
	private int userId;//关注人id
	
	@Column(name="PERSON_ID")
	private int personId;//被关注人id
	
	@Column(name="CRE_TIME")
	private Date creTime;//评论时间
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	
}
