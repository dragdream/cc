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
@Table(name = "project_task")
public class TeeTask {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="task_seq_gen")
	@SequenceGenerator(name="task_seq_gen", sequenceName="task_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="task_no")
	private String taskNo ;//任务序号
	
	@Column(name="task_name")
	private String taskName ;//任务名称
	
	@Column(name="task_level")
	private String taskLevel ;//任务级别
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="manager_id")
	private TeePerson manager;//任务负责人
	
	@Column(name="flow_type_ids")
	private String flowTypeIds ;//相关流程
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="higher_task_id")
	private TeeTask higherTask;//上级任务
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pre_task_id")
	private TeeTask preTask;//前置任务
	
	@Column(name="begin_time")
	private Date beginTime ;//计划开始时间
	
	@Column(name="end_time")
	private Date endTime ;//计划结束时间
	
	
	@Column(name="real_begin_time")
	private Date realBeginTime ;//实际开始时间
	
	@Column(name="real_end_time")
	private Date realEndTime ;//实际结束时间
	
	
	@Column(name="days")
	private int days ;//项目周期
	
	@Column(name="create_time")
	private Date createTime ;//创建时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//任务创建人
	
	@Column(name="progress")
	private int progress ;//任务进度
	
	@Column(name="status")
	private int status ;//0 进行中   1已完成   2未开始
	
	@Column(name="description")
	private String description ;//任务描述
	
	@Column(name="remark")
	private String remark ;//任务备注
	
	
	/*@Column(name="project_id")
	private String projectId ;//项目主键*/
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="project_id")
	private TeeProject project;//关联的项目
	
	
	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getTaskNo() {
		return taskNo;
	}


	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}


	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getTaskLevel() {
		return taskLevel;
	}


	public void setTaskLevel(String taskLevel) {
		this.taskLevel = taskLevel;
	}


	public TeePerson getManager() {
		return manager;
	}


	public void setManager(TeePerson manager) {
		this.manager = manager;
	}


	public String getFlowTypeIds() {
		return flowTypeIds;
	}


	public void setFlowTypeIds(String flowTypeIds) {
		this.flowTypeIds = flowTypeIds;
	}


	public TeeTask getHigherTask() {
		return higherTask;
	}


	public void setHigherTask(TeeTask higherTask) {
		this.higherTask = higherTask;
	}


	public TeeTask getPreTask() {
		return preTask;
	}


	public void setPreTask(TeeTask preTask) {
		this.preTask = preTask;
	}


	public Date getBeginTime() {
		return beginTime;
	}


	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public int getDays() {
		return days;
	}


	public void setDays(int days) {
		this.days = days;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public TeePerson getCreater() {
		return creater;
	}


	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}


	public int getProgress() {
		return progress;
	}


	public void setProgress(int progress) {
		this.progress = progress;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}




	public TeeProject getProject() {
		return project;
	}


	public void setProject(TeeProject project) {
		this.project = project;
	}


	public Date getRealBeginTime() {
		return realBeginTime;
	}


	public void setRealBeginTime(Date realBeginTime) {
		this.realBeginTime = realBeginTime;
	}


	public Date getRealEndTime() {
		return realEndTime;
	}


	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}

	
}
