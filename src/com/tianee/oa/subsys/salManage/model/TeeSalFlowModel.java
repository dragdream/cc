package com.tianee.oa.subsys.salManage.model;

import java.util.Date;

public class TeeSalFlowModel {
	private int sid;//工资流程编号
	
	private int accountId;//账套ID
	
	private int createrId;//
	private String salCreaterName;//创建人

	private Date beginDate;//流程起始日期
	
	private Date endDate;//流程结束日期
	
	private String  content;//备注
	
	private Date sendTime;//流程发起时间
	
	private String style;//工资条默认选择字段
	
	private int issend ;//工资流程的状态(1-已终止,0-执行中)
	
	private int salYear;//年份
	
	private int salMonth;//月份

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public String getSalCreaterName() {
		return salCreaterName;
	}

	public void setSalCreaterName(String salCreaterName) {
		this.salCreaterName = salCreaterName;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getIssend() {
		return issend;
	}

	public void setIssend(int issend) {
		this.issend = issend;
	}

	public int getSalYear() {
		return salYear;
	}

	public void setSalYear(int salYear) {
		this.salYear = salYear;
	}

	public int getSalMonth() {
		return salMonth;
	}

	public void setSalMonth(int salMonth) {
		this.salMonth = salMonth;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	
}
