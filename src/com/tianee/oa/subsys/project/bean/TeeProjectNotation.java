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

//项目批准
@Entity
@Table(name = "project_notation")
public class TeeProjectNotation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_notation_seq_gen")
	@SequenceGenerator(name="project_notation_seq_gen", sequenceName="project_notation_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="project_id")
	private String projectId ;//项目主键
	
	@Column(name="create_time")
	private Date createTime ;//创建时间
	
	@Column(name="content")
	private String content ;//批注内容
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//批注创建人
	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TeePerson getCreater() {
		return creater;
	}

	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}

	
}
