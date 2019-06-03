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
 * 回复实体类
 * */
@Entity
@Table(name="weib_reply")
public class TeeWeibReply {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_reply_seq_gen")
	@SequenceGenerator(name="weib_reply_seq_gen", sequenceName="weib_reply_seq")
	@Column(name = "SID")
	private int sid;//回复主键
	
	@Column(name="CONTENT")
	private String content;//回复内容
	
	@Column(name="PL_ID")
	private int plId;//评论id
	
	@Column(name="USER_ID")
	private int userId;//回复人id
	
	@Column(name="CRE_TIME")
	private Date creTime;//回复时间
	
	@Column(name="PERSON_ID")
	private int personId;//被回复人

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPlId() {
		return plId;
	}

	public void setPlId(int plId) {
		this.plId = plId;
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
