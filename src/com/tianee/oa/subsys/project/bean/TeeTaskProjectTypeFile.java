package com.tianee.oa.subsys.project.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "task_projectype_file")

public class TeeTaskProjectTypeFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="task_projectype_file_seq_gen")
	@SequenceGenerator(name="task_projectype_file_seq_gen", sequenceName="task_projectype_file_seq")
	@Column(name="SID")
	private int sid;//自增id

	@Column(name="TASK_ID")
	private int taskId;
	
	@Column(name="PROJECT_TYPE_ID")
	private int projectTypeId;
	
	@Column(name="FILE_NETDISK_ID")
	private int fileId;
	
	@Column(name="PROJECT_ID")
	private String projectId;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getProjectTypeId() {
		return projectTypeId;
	}

	public void setProjectTypeId(int projectTypeId) {
		this.projectTypeId = projectTypeId;
	}


	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	
	
}
