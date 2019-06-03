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

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "project_file")
public class TeeProjectFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_file_seq_gen")
	@SequenceGenerator(name="project_file_seq_gen", sequenceName="project_file_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//上传人
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="file_id")
	private TeeFileNetdisk file;//文档主键
	
	
	@Column(name="create_time")
	private Date createTime ;//创建时间
	
	@Column(name="project_id")
	private String  projectId;//项目主键
	
	@Column(name="TASK_ID")
	private int taskId;//任务主键
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getCreater() {
		return creater;
	}

	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}

	public TeeFileNetdisk getFile() {
		return file;
	}

	public void setFile(TeeFileNetdisk file) {
		this.file = file;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	
}
