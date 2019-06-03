package com.tianee.oa.core.workflow.workmanage.bean;

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
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_SENIOR_QUERY_TEMPLATE")
public class TeeFlowSeniorQueryTemplate {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_SENIOR_QUERY_TEMPLATE_seq_gen")
	@SequenceGenerator(name="FLOW_SENIOR_QUERY_TEMPLATE_seq_gen", sequenceName="FLOW_SENIOR_QUERY_TEMPLATE_seq")
	@Column(name="SID")
	private int sid;//主键
	
	@ManyToOne()
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;//所属流程
	
	@Column(name="TEMPLATE_NAME")
	private String templateName;//模版名称
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private TeePerson user;//所属用户
	
	
	@Column(name="BASIC_INFO")
	private String basicInfo;//基本属性
	
	
	@Column(name="FORM_INFO")
	private String formInfo;//表单数据
	
	
	@Column(name="STATISTIC_INFO")
	private String StatisticInfo;//统计相关


	public int getSid() {
		return sid;
	}


	public TeeFlowType getFlowType() {
		return flowType;
	}


	public String getTemplateName() {
		return templateName;
	}


	public TeePerson getUser() {
		return user;
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


	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}


	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	public void setUser(TeePerson user) {
		this.user = user;
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
