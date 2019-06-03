package com.tianee.oa.subsys.project.bean;

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

//项目抄送
@Entity
@Table(name = "project_copy")
public class TeeProjectCopy {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_copy_seq_gen")
	@SequenceGenerator(name="project_copy_seq_gen", sequenceName="project_copy_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="project_id")
	private String projectId ;//项目主键
	
	@Column(name="cr_time")
	private Date  createTime ;//创建时间
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private TeePerson user;//抄送人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="form_user_id")
	private TeePerson fromUser;//来自谁
	
	public TeePerson getFromUser() {
		return fromUser;
	}

	public void setFromUser(TeePerson fromUser) {
		this.fromUser = fromUser;
	}

	@Column(name="read_flag")
	private int readFlag;//0  未阅读    1已阅读
	
	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	
}
