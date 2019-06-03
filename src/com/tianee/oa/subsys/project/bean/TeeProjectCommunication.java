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

@Entity
@Table(name = "project_communication")
public class TeeProjectCommunication {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_communication_seq_gen")
	@SequenceGenerator(name="project_communication_seq_gen", sequenceName="project_communication_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="content")
	private String content ;//内容
	
	@Column(name="project_id")
	private String projectId ;//项目主键
	
	@Column(name="cr_time")
	private Date  createTime ;//创建时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cr_user")
	private TeePerson createUser;//创建人
	
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

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	

	
}
