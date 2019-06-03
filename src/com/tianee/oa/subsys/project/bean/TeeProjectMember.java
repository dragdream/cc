package com.tianee.oa.subsys.project.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "project_member")
public class TeeProjectMember {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_member_seq_gen")
	@SequenceGenerator(name="project_member_seq_gen", sequenceName="project_member_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="project_id")
	private String projectId ;//项目主键
	
	@Column(name="member_id")
	private int memberId ;//成员主键
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

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	
}
