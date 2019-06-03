package com.tianee.oa.subsys.project.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.project.bean.TeeTask;

public class TeeProjectQuestionModel {
	
	private int sid;//自增id

	private String  questionName ;//问题名称
	
	private String  questionLevel ;//问题优先级
	
	private String  questionDesc ;//问题描述
	
	private int  status ;//问题状态  0待处理   1已处理
	
	private String  projectId ;//项目主键
	private String projectName;//项目名称
	
	

	//private TeeTask task;//任务
	private String taskName;//任务名称
	
	private  int taskId;//任务主键
	
	
	//private TeePerson creater;//创建人
	private String createrName;//创建人姓名
	
	

	private int createrId;//创建人主键
	
	//private TeePerson operator;//处理人
	private  String operatorName;//处理人姓名
	
	private int operatorId;//处理人主键
	
	//private Date createTime ;//创建时间
	private String createTimeStr;//创建时间
	
	private String result;//项目处理结果
	
	//private int  handleTime ;//问题处理时间
	private String handleTimeStr;//项目处理时间

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getHandleTimeStr() {
		return handleTimeStr;
	}

	public void setHandleTimeStr(String handleTimeStr) {
		this.handleTimeStr = handleTimeStr;
	}
}
