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
 * 评论实体类
 * */
@Entity
@Table(name="weib_comment")
public class TeeWeibComment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_comment_seq_gen")
	@SequenceGenerator(name="weib_comment_seq_gen", sequenceName="weib_comment_seq")
	@Column(name = "SID")
	private int sid;//评论主键
	
	@Column(name="INFO_ID")
    private int infoId;//发布信息id
	
	@Column(name="USER_ID")
	private int userId;//评论人id
	
	@Column(name="CRE_TIME")
	private Date creTime;//评论时间
	
	@Column(name="CONTENT")
	private String content;//评论内容

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	
}
