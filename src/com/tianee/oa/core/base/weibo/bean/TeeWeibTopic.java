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
 * 话题实体类
 * */
@Entity
@Table(name="weib_topic")
public class TeeWeibTopic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_topic_seq_gen")
	@SequenceGenerator(name="weib_topic_seq_gen", sequenceName="weib_topic_seq")
	@Column(name = "SID")
	private int sid;//评论主键
	
	@Column(name="TOPIC_NAME")
    private String topic;
	
	@Column(name="INFO_ID")
	private String infoStr;//微博信息id字符串
	
	@Column(name="RCOUNT")
	private int rCount;//阅读此话题的次数
	
	@Column(name="COUNT_")
	private int count;//讨论的次数

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getInfoStr() {
		return infoStr;
	}

	public void setInfoStr(String infoStr) {
		this.infoStr = infoStr;
	}

	public int getrCount() {
		return rCount;
	}

	public void setrCount(int rCount) {
		this.rCount = rCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	
	
}
