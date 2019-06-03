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
@Table(name = "project_task_report")
public class TeeTaskReport {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_task_report_seq_gen")
	@SequenceGenerator(name="project_task_report_seq_gen", sequenceName="project_task_report_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_id")
	private TeeTask task;//任务
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reporter_id")
	private TeePerson reporter;//任务汇报人
	
	@Column(name="content")
	private String  content ;//汇报内容
	
	
	
	@Column(name="create_time")
	private Date createTime ;//创建时间
	
	@Column(name="progress")
	private int progress ;//进度

	
	@Column(name="project_id")
	private String projectId ;//项目主键
	
	
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

	public TeePerson getReporter() {
		return reporter;
	}

	public void setReporter(TeePerson reporter) {
		this.reporter = reporter;
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

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	public TeeTask getTask() {
		return task;
	}

	public void setTask(TeeTask task) {
		this.task = task;
	}
}
