package com.tianee.oa.subsys.budget.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 预算申请
 * 
 * @author kakalion
 * 
 */
@Entity
@Table(name = "BUDGET_REG")
public class TeeBudgetReg {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "UUID")
	private String uuid;// 单据号，主键,自动生成，注意：非int型

	@ManyToOne
	@Index(name="IDXdbdd5ed09fd9440796073b52a91")
	@JoinColumn(name = "OPER_USER_ID")
	private TeePerson opUser;// 申请人

	@ManyToOne
	@Index(name="IDXf55b683adda644e2a04e71eb255")
	@JoinColumn(name = "OP_DEPT_ID")
	private TeeDepartment opDept;// 申请人部门,包括可选择他的辅助部门

	@Column(name = "TYPE")
	private int type;// 记录类型 1：预算申请 2：报销

	@Column(name = "REG_TYPE")
	private int regType;// 申请类型，1：个人预算 2：部门预算

	@Column(name = "AMOUNT")
	private double amount;// 申请金额

	@Column(name = "REASON")
	private String reason;// 申请原由 （=》系统代码表 BUDGET_REG_REASON）

	@Column(name = "REMARK", length = 1000)
	private String remark;// 申请原由描述

	@Column(name = "CR_TIME")
	private Date crTime;// 申请时间

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeePerson getOpUser() {
		return opUser;
	}

	public void setOpUser(TeePerson opUser) {
		this.opUser = opUser;
	}

	public TeeDepartment getOpDept() {
		return opDept;
	}

	public void setOpDept(TeeDepartment opDept) {
		this.opDept = opDept;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCrTime() {
		return crTime;
	}

	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}

	public int getRegType() {
		return regType;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

}
