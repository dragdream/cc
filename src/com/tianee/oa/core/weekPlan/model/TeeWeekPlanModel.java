package com.tianee.oa.core.weekPlan.model;

import java.util.Date;

import javax.persistence.Column;

public class TeeWeekPlanModel {
	
	/**
	 * 主键
	 */
	private int sid;

	
	
	public int getSid() {
		return sid;
	}
	/**
	 * 周计划标题
	 */
	
	private String tittle;
	/**
	 * 周计划内容
	 */

	private String content;
	/**
	 * 提交时间
	 */
	
	private String submitTime;
	/**
	 * 发布时间
	 */

	private String publishTime;
	/**
	 * 部门编号
	 */
	
	private Integer departmentNo;
	/**
	 * 创建人id
	 */

	private Integer createUserId;
	/**
	 * 周计划状态
	 */

	private Integer status;
	/**
	 * 批注内容
	 */
	private String postilContent;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 部门名
	 */
	private String deptName;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	private String startTime;
	private String endTime;
	

	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTittle() {
		return tittle;
	}
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(Integer departmentNo) {
		this.departmentNo = departmentNo;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPostilContent() {
		return postilContent;
	}
	public void setPostilContent(String postilContent) {
		this.postilContent = postilContent;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	

	
}
