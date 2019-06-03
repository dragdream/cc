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
@Table(name = "sup_feed_back")
public class TeeSupFeedBack {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="sup_feed_back_seq_gen")
	@SequenceGenerator(name="sup_feed_back_seq_gen", sequenceName="sup_feed_back_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="title")
	private String title ;//标题
	
	
	@Column(name="content")
	private String content ;//内容
	
	@Column(name="level_")
	private int level ;//缓急级别  0普通  1紧急
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sup_id")
	private TeeSupervision sup;//所属督办任务
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//创建人
	
	@Column(name="create_time")
	private Date createTime ;//创建时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public TeeSupervision getSup() {
		return sup;
	}

	public void setSup(TeeSupervision sup) {
		this.sup = sup;
	}

	public TeePerson getCreater() {
		return creater;
	}

	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
