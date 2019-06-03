package com.tianee.oa.core.base.officeProducts.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="Office_Stock_Bill")
public class TeeOfficeStockBill{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Office_Stock_Bill_seq_gen")
	@SequenceGenerator(name="Office_Stock_Bill_seq_gen", sequenceName="Office_Stock_Bill_seq")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDX7ca0de62961642b39185efd2bca")
	@JoinColumn(name="PRODUCT_ID")
	private TeeOfficeProduct product;
	
	/**
	 * 登记类型：1、领用 2、借用 3、归还 4、入库 5、维护 6、报废
	 */
	@Column(name="REG_TYPE")
	private int regType;
	
	@Column(name="REG_COUNT")
	private int regCount;
	
	@ManyToOne()
	@Index(name="IDXb52138d751f648ce83845b9d2d4")
	private TeePerson regUser;
	
	@ManyToOne()
	@Index(name="IDX9eff66633a3f4d62af755e9cdc2")
	private TeePerson auditor;
	
	@ManyToOne()
	@Index(name="IDX4aa6016569a748c599f34ab1f0e")
	private TeePerson operator;
	
	/**
	 * 0、未开始调度
	 * 1、正在调度中
	 * 2、调度完毕
	 */
	@Column(name="OPER_FLAG")
	private int operFlag;//调度标记
	
	/**
	 * 0、未领取
	 * 1、已领取
	 * 3、归还中
	 * 4、已归还
	 */
	@Column(name="OBTAIN_FLAG")
	private int obtainFlag;//领取标记
	
	
	@Column(name="REG_TIME")
	private Calendar regTime;//登记时间
	
	
	/**
	 * 0、待审批
	 * 1、审批通过
	 * 2、审批不通过
	 */
	@Column(name="VER_FLAG")
	private int verFlag;//审核标记
	
	@Column(name="VER_TIME")
	private Calendar verTime;//审核通过时间
	
	@Column(name="OPER_TIME")
	private Calendar operTime;//调度时间
	
	@Column(name="FINISH_TIME")
	private Calendar finishTime;//完成时间
	
	/**
	 * 0、作废
	 * 1、有效
	 */
	@Column(name="VALID_FLAG")
	private int validFlag;//有效标记
	
	/**
	 * 备注
	 */
	@Column(name="REMARK")
	@Lob
	private String remark;

	public TeeOfficeProduct getProduct() {
		return product;
	}

	public void setProduct(TeeOfficeProduct product) {
		this.product = product;
	}

	public int getRegType() {
		return regType;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	public int getRegCount() {
		return regCount;
	}

	public void setRegCount(int regCount) {
		this.regCount = regCount;
	}

	public TeePerson getRegUser() {
		return regUser;
	}

	public void setRegUser(TeePerson regUser) {
		this.regUser = regUser;
	}

	public TeePerson getAuditor() {
		return auditor;
	}

	public void setAuditor(TeePerson auditor) {
		this.auditor = auditor;
	}

	public TeePerson getOperator() {
		return operator;
	}

	public void setOperator(TeePerson operator) {
		this.operator = operator;
	}

	public int getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(int operFlag) {
		this.operFlag = operFlag;
	}

	public Calendar getRegTime() {
		return regTime;
	}

	public void setRegTime(Calendar regTime) {
		this.regTime = regTime;
	}

	public int getVerFlag() {
		return verFlag;
	}

	public void setVerFlag(int verFlag) {
		this.verFlag = verFlag;
	}

	public Calendar getVerTime() {
		return verTime;
	}

	public void setVerTime(Calendar verTime) {
		this.verTime = verTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}

	public Calendar getOperTime() {
		return operTime;
	}

	public void setOperTime(Calendar operTime) {
		this.operTime = operTime;
	}

	public Calendar getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Calendar finishTime) {
		this.finishTime = finishTime;
	}

	public void setValidFlag(int validFlag) {
		this.validFlag = validFlag;
	}

	public int getValidFlag() {
		return validFlag;
	}

	public void setObtainFlag(int obtainFlag) {
		this.obtainFlag = obtainFlag;
	}

	public int getObtainFlag() {
		return obtainFlag;
	}
}
