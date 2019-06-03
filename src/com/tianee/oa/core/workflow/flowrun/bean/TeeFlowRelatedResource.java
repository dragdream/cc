package com.tianee.oa.core.workflow.flowrun.bean;

import java.util.Calendar;

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
/**
 * 相关资源
 * @author xsy
 *
 */
@Entity
@Table(name="FLOW_RELATED_RESOURCE")
public class TeeFlowRelatedResource {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RELATED_RESOURCE_seq_gen")
	@SequenceGenerator(name="FLOW_RELATED_RESOURCE_seq_gen", sequenceName="FLOW_RELATED_RESOURCE_seq")
	@Column(name="SID")
	private int sid;//主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATE_USER")
	private TeePerson createUser;//创建人
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建时间
	
	@Column(name="TYPE")
	private int type ;//1=相关流程   2=相关任务  3=相关客户   4=相关项目
	
	@Column(name="RELATED_ID")
	private String  relatedId ;//关联主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;//关联流程

	public int getSid() {
		return sid;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public int getType() {
		return type;
	}

	public String getRelatedId() {
		return relatedId;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}
	
	
	
	
	
}
