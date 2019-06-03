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
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;

@Entity
@Table(name = "project_flow")
public class TeeProjectFlow {
   //任务id  2：项目Id  3：runId  4：发起人id
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_flow_seq_gen")
	@SequenceGenerator(name="project_flow_seq_gen", sequenceName="project_flow_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//发起人
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_id")
	private TeeTask task;//任务
	
	
	@Column(name="project_id")
	private String projectId ;//项目主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="run_id")
	private TeeFlowRun flowRun;
	
	
	@Column(name="create_time")
	private Date createTime ;//创建时间

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public TeeTask getTask() {
		return task;
	}

	public void setTask(TeeTask task) {
		this.task = task;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}
	
}
