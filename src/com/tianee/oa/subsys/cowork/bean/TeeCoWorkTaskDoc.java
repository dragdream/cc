package com.tianee.oa.subsys.cowork.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="CO_WORK_TASK_DOC")
public class TeeCoWorkTaskDoc {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CO_WORK_TASK_DOC_seq_gen")
	@SequenceGenerator(name="CO_WORK_TASK_DOC_seq_gen", sequenceName="CO_WORK_TASK_DOC_seq")
	private int sid;
	
	@Column(name="REMARK")
	private String remark;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX197d65297cd247119c75e3ce869")
	private TeePerson createUser;
	
	@Column(name="CR_TIME")
	private Calendar createTime;
	
	@ManyToOne
	@Index(name="IDX988bd2c4d44a4ca692615b9500d")
	private TeeCoWorkTask task;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public TeeCoWorkTask getTask() {
		return task;
	}

	public void setTask(TeeCoWorkTask task) {
		this.task = task;
	}
	
}
