package com.tianee.oa.subsys.informationReport.bean;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 任务发布记录项
 * @author xsy
 *
 */
@Entity
@Table(name = "REP_TASK_PUB_RECORD_ITEM")
public class TeeTaskPubRecordItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="REP_TASK_PUB_RECORD_ITEM_seq_gen")
	@SequenceGenerator(name="REP_TASK_PUB_RECORD_ITEM_seq_gen", sequenceName="REP_TASK_PUB_RECORD_ITEM_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name = "CREATE_TIME")
	private Calendar createTime;//创建时间
	
	
	@ManyToOne
	@JoinColumn(name = "TASK_TEMPLATE_ID")
	private TeeTaskTemplate taskTemplate;//所属任务模板
	
	
	@ManyToOne
	@JoinColumn(name = "TASK_PUB_RECORD_ID")
	private TeeTaskPubRecord taskPubRecord;//所关联的任务发布记录
	
	
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private TeePerson user;//关联人员id
	
	@ManyToOne
	@JoinColumn(name = "DEPT_ID")
	private TeeDepartment dept;//关联部门id
	
	@Column(name = "FLAG")
	private int flag;//上报状态  0=未上报   1=已上报
	

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public TeeTaskTemplate getTaskTemplate() {
		return taskTemplate;
	}

	public void setTaskTemplate(TeeTaskTemplate taskTemplate) {
		this.taskTemplate = taskTemplate;
	}

	public TeeTaskPubRecord getTaskPubRecord() {
		return taskPubRecord;
	}

	public void setTaskPubRecord(TeeTaskPubRecord taskPubRecord) {
		this.taskPubRecord = taskPubRecord;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	
	
}
