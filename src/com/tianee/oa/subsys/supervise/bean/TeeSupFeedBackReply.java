package com.tianee.oa.subsys.supervise.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

//反馈
@Entity
@Table(name = "sup_feed_back_reply")
public class TeeSupFeedBackReply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sup_feed_back_reply_seq_gen")
	@SequenceGenerator(name="sup_feed_back_reply_seq_gen", sequenceName="sup_feed_back_reply_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="content")
	private String content ;//内容
	
	@Column(name="create_time")
	private Date createTime ;//回复时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//创建人
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="fb_id")
	private TeeSupFeedBack fb;//反馈
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sup_id")
	private TeeSupervision sup;//关联的督办任务

	public TeeSupervision getSup() {
		return sup;
	}

	public void setSup(TeeSupervision sup) {
		this.sup = sup;
	}

	public TeeSupFeedBack getFb() {
		return fb;
	}

	public void setFb(TeeSupFeedBack fb) {
		this.fb = fb;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TeePerson getCreater() {
		return creater;
	}

	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}
}
