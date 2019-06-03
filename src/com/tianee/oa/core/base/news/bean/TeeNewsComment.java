package com.tianee.oa.core.base.news.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "NewsComment")
public class TeeNewsComment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="NewsComment_seq_gen")
	@SequenceGenerator(name="NewsComment_seq_gen", sequenceName="NewsComment_seq")
	@Column(name = "SID")
	private int sid; // 自增ID
	
	@Column(name = "PARENT_ID", nullable = true)
	private int parentId = 0;//跟帖用的字段
	
	@Column(name = "NEWS_ID", nullable = true)
	private int newsId = 0;//新闻ID
	
	@Lob
	@Column(name = "USER_ID", nullable = true)
	private String userId = "";//发表评论/回复的用户
	
	@Column(name = "NICK_NAME", nullable = true,length=100)
	private String nickName = "";//昵称
	
	@Lob
	@Column(name = "CONTENT", nullable = true)
	private String content = null;// 评论/回复内容

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RE_TIME", nullable = true)
	private Date reTime = null;//评论/回复时间


	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Date getReTime() {
		return reTime;
	}

	public void setReTime(Date reTime) {
		this.reTime = reTime;
	}
	
	

}
