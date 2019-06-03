package com.tianee.oa.subsys.cowork.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.subsys.schedule.bean.TeeSchedule;

@Entity
@Table(name="CO_WORK_TASK_SCHEDULE")
public class TeeTaskSchedule {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne
	@Index(name="IDXf3737dc871464545a4870490ff0")
	@JoinColumn(name="TASK_ID")
	private TeeCoWorkTask task;
	
	@ManyToOne
	@Index(name="IDX9855e46ccf494b72880224e956e")
	@JoinColumn(name="SCHEDULE_ID")
	private TeeSchedule schedule;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeeCoWorkTask getTask() {
		return task;
	}

	public void setTask(TeeCoWorkTask task) {
		this.task = task;
	}

	public TeeSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(TeeSchedule schedule) {
		this.schedule = schedule;
	}
	
	
}
