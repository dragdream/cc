package com.tianee.oa.core.workflow.workmanage.bean;
import org.hibernate.annotations.Index;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;
import org.springframework.format.annotation.DateTimeFormat;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_TIMER_TASK")
public class TeeFlowTimerTask {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_TIMER_TASK_seq_gen")
	@SequenceGenerator(name="FLOW_TIMER_TASK_seq_gen", sequenceName="FLOW_TIMER_TASK_seq")
	@Column(name="SID")
	private int sid;//流水号
	
	@JoinColumn(name="FLOW_ID")
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXcd340b5cd37f46f9a60d8326a6b")
	private TeeFlowType flowType;//所属流程
	
	@ManyToMany(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	private Set<TeePerson> users = new HashSet<TeePerson>(0);//发起人集合
	
	@Column(name="TYPE")
	@Index(name="FLOW_TIMER_TASK_TYPE")
	private int type;//提醒类型提醒类型：1-仅此一次；2-按日；3-按周；4-按月；5-按年；

	@Column(name="REMIND_MODEL",length=100)
	//提醒模型：{week:xx,month:xx,date:xx,hour:x,minute:x}
	//1-仅此一次，存具体日期；2-按日，为空；3-按周，存星期几；4-按月，存每月几号；5-按年，存每年几月几号；
	private String remindModel;

	
	@Column(name="REMIND_STAMP")
	@Index(name="FLOW_TIMER_TASK_REMIND_STAMP")
	private long remindStamp;//提醒戳，long型
	
	
	@Column(name="LAST_TIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastTime;//最近一次提醒时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getRemindModel() {
		return remindModel;
	}

	public void setRemindModel(String remindModel) {
		this.remindModel = remindModel;
	}

	public long getRemindStamp() {
		return remindStamp;
	}

	public void setRemindStamp(long remindStamp) {
		this.remindStamp = remindStamp;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public Set<TeePerson> getUsers() {
		return users;
	}

	public void setUsers(Set<TeePerson> users) {
		this.users = users;
	}
}
