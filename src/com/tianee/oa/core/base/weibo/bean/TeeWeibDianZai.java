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
 * 点赞实体类
 * */
@Entity
@Table(name="weib_dianzai")
public class TeeWeibDianZai {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_dianzai_seq_gen")
	@SequenceGenerator(name="weib_dianzai_seq_gen", sequenceName="weib_dianzai_seq")
	@Column(name = "SID")
	private int sid;//点赞主键
	
	@Column(name="INFO_ID")
    private int infoId;//发布信息id
	
	@Column(name="USER_ID")
	private int userId;//点赞人id
	
	@Column(name="CRE_TIME")
	private Date creTime;//点赞时间
	
	@Column(name="REPLY_ID")
	private int replyId;//评论点赞
	
	@Column(name="HUIFU_ID")
	private int huiFuId;//回复id
	

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

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int getHuiFuId() {
		return huiFuId;
	}

	public void setHuiFuId(int huiFuId) {
		this.huiFuId = huiFuId;
	}

	
}
