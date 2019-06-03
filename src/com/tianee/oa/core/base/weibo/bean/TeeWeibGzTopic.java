package com.tianee.oa.core.base.weibo.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="weib_gztopic")
public class TeeWeibGzTopic {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_gztopic_seq_gen")
	@SequenceGenerator(name="weib_gztopic_seq_gen", sequenceName="weib_gztopic_seq")
	@Column(name = "SID")
	private int sid;//关注主键
	
	@Column(name="USER_ID")
	private int userId;
	
	@Column(name="TOPIC_ID")
	private int topicId;
	
	@Column(name="CRE_TIME")
	private Date creTime;

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

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public Date getCreTime() {
		return creTime;
	}

	public void setCreTime(Date creTime) {
		this.creTime = creTime;
	}
	
	

}
