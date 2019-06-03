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
@Table(name = "project_question")
public class TeeProjectQuestion {
	

	


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="project_question_seq_gen")
	@SequenceGenerator(name="project_question_seq_gen", sequenceName="project_question_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="question_name")
	private String  questionName ;//问题名称
	
	@Column(name="question_level")
	private String  questionLevel ;//问题优先级
	
	
	@Column(name="question_desc")
	private String  questionDesc ;//问题描述
	
	
	@Column(name="status")
	private int  status ;//问题状态  0待处理   1已处理
	
	@Column(name="handle_time")
	private Date  handleTime ;//问题处理时间
	
	
	@Column(name="project_id")
	private String  projectId ;//项目主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="task_id")
	private TeeTask task;//任务
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creater_id")
	private TeePerson creater;//创建人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="operator_id")
	private TeePerson operator;//处理人
	
	
	@Column(name="create_time")
	private Date createTime ;//创建时间
	
	
	@Column(name="result")
	private String result ;//处理结果
	
	public Date getHandleTime() {
		return handleTime;
	}


	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getQuestionName() {
		return questionName;
	}


	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}


	public String getQuestionLevel() {
		return questionLevel;
	}


	public void setQuestionLevel(String questionLevel) {
		this.questionLevel = questionLevel;
	}


	public String getQuestionDesc() {
		return questionDesc;
	}


	public void setQuestionDesc(String questionDesc) {
		this.questionDesc = questionDesc;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public TeeTask getTask() {
		return task;
	}


	public void setTask(TeeTask task) {
		this.task = task;
	}


	public TeePerson getCreater() {
		return creater;
	}


	public void setCreater(TeePerson creater) {
		this.creater = creater;
	}


	public TeePerson getOperator() {
		return operator;
	}


	public void setOperator(TeePerson operator) {
		this.operator = operator;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	
	
	
}
