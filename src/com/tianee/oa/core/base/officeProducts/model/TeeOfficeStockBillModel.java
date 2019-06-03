package com.tianee.oa.core.base.officeProducts.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeProduct;
import com.tianee.oa.core.org.bean.TeePerson;

public class TeeOfficeStockBillModel {
	private int sid;
	private int productId;
	private String productName;
	private String productCode;
	private String categoryName;
	private String depositoryName;
	private int regType;
	private String regTypeDesc;
	private int regCount;
	private int stockCount;
	private int regUserId;
	private String regUserName;
	private int auditorId;
	private String auditorName;
	private int operatorId;
	private String operatorName;
	private int operFlag;//操作标记
	private int obtainFlag;//领取标记
	private String operFlagDesc;//操作标记描述
	private String regTimeDesc;
	private int verFlag;//审核通过标记
	private String verTimeDesc;//审核通过时间
	private String operTimeDesc;//调度时间
	private String finishTimeDesc;//完成时间
	private int validFlag;//有效标记
	private String remark;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public int getRegCount() {
		return regCount;
	}
	public void setRegCount(int regCount) {
		this.regCount = regCount;
	}
	public int getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(int regUserId) {
		this.regUserId = regUserId;
	}
	public String getRegUserName() {
		return regUserName;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	public int getAuditorId() {
		return auditorId;
	}
	public void setAuditorId(int auditorId) {
		this.auditorId = auditorId;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public int getOperFlag() {
		return operFlag;
	}
	public void setOperFlag(int operFlag) {
		this.operFlag = operFlag;
	}
	public String getOperFlagDesc() {
		return operFlagDesc;
	}
	public void setOperFlagDesc(String operFlagDesc) {
		this.operFlagDesc = operFlagDesc;
	}
	public String getRegTimeDesc() {
		return regTimeDesc;
	}
	public void setRegTimeDesc(String regTimeDesc) {
		this.regTimeDesc = regTimeDesc;
	}
	public int getVerFlag() {
		return verFlag;
	}
	public void setVerFlag(int verFlag) {
		this.verFlag = verFlag;
	}
	public String getVerTimeDesc() {
		return verTimeDesc;
	}
	public void setVerTimeDesc(String verTimeDesc) {
		this.verTimeDesc = verTimeDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setOperTimeDesc(String operTimeDesc) {
		this.operTimeDesc = operTimeDesc;
	}
	public String getOperTimeDesc() {
		return operTimeDesc;
	}
	public void setFinishTimeDesc(String finishTimeDesc) {
		this.finishTimeDesc = finishTimeDesc;
	}
	public String getFinishTimeDesc() {
		return finishTimeDesc;
	}
	public void setValidFlag(int validFlag) {
		this.validFlag = validFlag;
	}
	public int getValidFlag() {
		return validFlag;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getDepositoryName() {
		return depositoryName;
	}
	public void setDepositoryName(String depositoryName) {
		this.depositoryName = depositoryName;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setObtainFlag(int obtainFlag) {
		this.obtainFlag = obtainFlag;
	}
	public int getObtainFlag() {
		return obtainFlag;
	}
	
}
