package com.tianee.oa.core.base.officeProducts.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 用品操作记录
 * @author kakalion
 *
 */
@Entity
@Table(name="OFFICE_RECORD")
public class TeeOfficeRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="OFFICE_RECORD_seq_gen")
	@SequenceGenerator(name="OFFICE_RECORD_seq_gen", sequenceName="OFFICE_RECORD_seq")
	private int sid;
	
	private String proCode;
	
	private String proName;
	
	private String depositoryName;
	
	private String categoryName;
	
	private String regCount;
	
	private String originStock;
	
	private int regType;
	
	private String regTypeDesc;
	
	private String regUserName;
	
	private int regUserId;
	
	private String operatorName;
	
	private int operatorId;
	
	private String auditorName;
	
	private int auditorId;
	
	/**
	 * 1、入库记录
	 * 2、报废记录
	 * 3、领用记录
	 * 4、借用记录
	 * 5、归还记录
	 * 6、维护记录
	 * 7、库存记录
	 * 8、删除记录
	 */
	private int recordType;//记录类型
	
	private String actionDesc;//动作描述
	
	private Calendar actionTime;//动作时间
	
	private String actionTimeDesc;//动作时间描述

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getDepositoryName() {
		return depositoryName;
	}

	public void setDepositoryName(String depositoryName) {
		this.depositoryName = depositoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getRegCount() {
		return regCount;
	}

	public void setRegCount(String regCount) {
		this.regCount = regCount;
	}

	public String getOriginStock() {
		return originStock;
	}

	public void setOriginStock(String originStock) {
		this.originStock = originStock;
	}

	public int getRegType() {
		return regType;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	public String getRegTypeDesc() {
		return regTypeDesc;
	}

	public void setRegTypeDesc(String regTypeDesc) {
		this.regTypeDesc = regTypeDesc;
	}

	public String getRegUserName() {
		return regUserName;
	}

	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}

	public int getRegUserId() {
		return regUserId;
	}

	public void setRegUserId(int regUserId) {
		this.regUserId = regUserId;
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

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	public int getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(int auditorId) {
		this.auditorId = auditorId;
	}

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public Calendar getActionTime() {
		return actionTime;
	}

	public void setActionTime(Calendar actionTime) {
		this.actionTime = actionTime;
	}

	public void setActionTimeDesc(String actionTimeDesc) {
		this.actionTimeDesc = actionTimeDesc;
	}

	public String getActionTimeDesc() {
		return actionTimeDesc;
	}
	
	
}
