package com.tianee.oa.core.weekPlan.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

/**
 * 周计划实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="WEEK_PLAN")
public class TeeWeekPlan implements Serializable{
	
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="WEEK_PLAN_seq_gen")
	@SequenceGenerator(name="WEEK_PLAN_seq_gen", sequenceName="WEEK_PLAN_seq")
	private int sid;
	/**
	 * 周计划标题
	 */
	@Column(name="TITTLE")
	private String tittle;
	/**
	 * 周计划内容
	 */
	@Column(name="CONTENT")
	private String content;
	/**
	 * 提交时间
	 */
	@Column(name="SUBMIT_TIME")
	private String submitTime;
	/**
	 * 发布时间
	 */
	@Column(name="PUBLISH_TIME")
	private Date publishTime;
	/**
	 * 部门编号
	 */
	@Column(name="DEPARTMENT_NO")
	private Integer departmentNo;
	/**
	 * 创建人id
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUserId;//角色编号
	/**
	 * 周计划状态
	 */
	@Column(name="STATUS")
	private Integer status;
	/**
	 * 批注内容
	 */
	@Column(name="POSTIL_CONTENT")
	private String postilContent;
	
	/**
	 * 部门关联
	 *//*
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANAGER2")
	private TeeDepartment teeDepartment;
	*/
	/*public TeeDepartment getTeeDepartment() {
		return teeDepartment;
	}
	public void setTeeDepartment(TeeDepartment teeDepartment) {
		this.teeDepartment = teeDepartment;
	}*/
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
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
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getDepartmentNo() {
		return departmentNo;
	}
	public void setDepartmentNo(Integer departmentNo) {
		this.departmentNo = departmentNo;
	}

	public TeePerson getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(TeePerson createUserId) {
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
	

	

}
