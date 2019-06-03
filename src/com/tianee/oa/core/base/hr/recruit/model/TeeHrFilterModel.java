package com.tianee.oa.core.base.hr.recruit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeeHrFilterModel {

	private int sid;// 自增id
	private String planId ;// 招聘IDTeeRecruitPlan plan;
	private String planName;//
	private String hrPoolId;//人才Id
	private String hrPoolName;//人才姓名
	private String position;	// 应聘岗位
	private String employeeMajor;	// 所学专业
	private String employeePhone;	// 电话
	private String sendPersonId;//
	private String sendPersonName;//
	//private TeePerson sendPerson;//发起人
	private String nextTransactorId;//
	private String nextTransactorName;////下一次筛选人
	private Date nextDatetime;//下一次筛选时间
	private String nextDatetimeStr;//下一次筛选时间
	private String filterState;//0-待筛选 1-通过 2-不通过
	
	List<TeeHrFilterItemModel> itemModelList = new ArrayList<TeeHrFilterItemModel>();
	public List<TeeHrFilterItemModel> getItemModelList() {
		return itemModelList;
	}
	public void setItemModelList(List<TeeHrFilterItemModel> itemModelList) {
		this.itemModelList = itemModelList;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getHrPoolId() {
		return hrPoolId;
	}
	public void setHrPoolId(String hrPoolId) {
		this.hrPoolId = hrPoolId;
	}
	public String getHrPoolName() {
		return hrPoolName;
	}
	public void setHrPoolName(String hrPoolName) {
		this.hrPoolName = hrPoolName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmployeeMajor() {
		return employeeMajor;
	}
	public void setEmployeeMajor(String employeeMajor) {
		this.employeeMajor = employeeMajor;
	}
	public String getEmployeePhone() {
		return employeePhone;
	}
	public void setEmployeePhone(String employeePhone) {
		this.employeePhone = employeePhone;
	}
	public String getSendPersonId() {
		return sendPersonId;
	}
	public void setSendPersonId(String sendPersonId) {
		this.sendPersonId = sendPersonId;
	}
	public String getSendPersonName() {
		return sendPersonName;
	}
	public void setSendPersonName(String sendPersonName) {
		this.sendPersonName = sendPersonName;
	}
	public String getNextTransactorId() {
		return nextTransactorId;
	}
	public void setNextTransactorId(String nextTransactorId) {
		this.nextTransactorId = nextTransactorId;
	}
	public String getNextTransactorName() {
		return nextTransactorName;
	}
	public void setNextTransactorName(String nextTransactorName) {
		this.nextTransactorName = nextTransactorName;
	}
	public Date getNextDatetime() {
		return nextDatetime;
	}
	public void setNextDatetime(Date nextDatetime) {
		this.nextDatetime = nextDatetime;
	}
	public String getNextDatetimeStr() {
		return nextDatetimeStr;
	}
	public void setNextDatetimeStr(String nextDatetimeStr) {
		this.nextDatetimeStr = nextDatetimeStr;
	}
	public String getFilterState() {
		return filterState;
	}
	public void setFilterState(String filterState) {
		this.filterState = filterState;
	}

	
	

}
