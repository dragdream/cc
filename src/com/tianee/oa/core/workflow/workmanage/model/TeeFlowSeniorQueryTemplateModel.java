package com.tianee.oa.core.workflow.workmanage.model;


import com.tianee.oa.core.org.bean.TeePerson;


public class TeeFlowSeniorQueryTemplateModel {

	private int sid;//主键
	
	//private TeeFlowType flowType;//所属流程
	private int flowId;
	
	private String templateName;//模版名称

	//private TeePerson user;//所属用户
	private int userId;
	private String userName;

	private String basicInfo;//基本属性

	private String formInfo;//表单数据

	private String StatisticInfo;//统计相关

	public int getSid() {
		return sid;
	}

	public int getFlowId() {
		return flowId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getBasicInfo() {
		return basicInfo;
	}

	public String getFormInfo() {
		return formInfo;
	}

	public String getStatisticInfo() {
		return StatisticInfo;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setBasicInfo(String basicInfo) {
		this.basicInfo = basicInfo;
	}

	public void setFormInfo(String formInfo) {
		this.formInfo = formInfo;
	}

	public void setStatisticInfo(String statisticInfo) {
		StatisticInfo = statisticInfo;
	}
	
	
	
}
