package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTask;

/**
 * 相关任务
 * @author kakalion
 *
 */
@Entity
@Table(name="RUN_TASK_REL")
public class TeeRunTaskRel {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne
	@Index(name="IDXedbddc674c5b4c37a84e8ef5ca3")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
	@ManyToOne
	@Index(name="IDXeeb1a15044184c889846835358a")
	@JoinColumn(name="TASK_ID")
	private TeeCoWorkTask task;
	
	@Column(name="TIME_")
	private Calendar time;

	@Column(name="USER_ID")
	private int userId;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public TeeCoWorkTask getTask() {
		return task;
	}

	public void setTask(TeeCoWorkTask task) {
		this.task = task;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
