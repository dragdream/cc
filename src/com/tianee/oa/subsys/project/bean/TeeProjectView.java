package com.tianee.oa.subsys.project.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "project_view")
public class TeeProjectView {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_view_seq_gen")
	@SequenceGenerator(name="project_view_seq_gen", sequenceName="project_view_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="project_id")
	private String projectId ;//项目主键
	
	@Column(name="view_id")
	private int viewId ;//项目观察者的主键

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

	public int getViewId() {
		return viewId;
	}

	public void setViewId(int viewId) {
		this.viewId = viewId;
	}
}
