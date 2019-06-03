package com.tianee.oa.core.base.officeProducts.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeProduct;

public class TeeOfficeStockModel {
	private int sid;
	private int productId;
	private int productName;
	/**
	 * 登记类型：1、领用 2、借用 3、归还 4、入库 5、维护 6、报废
	 */
	private int regType;
	private int regCount;//登记数量
	private String createTimeDesc;//创建时间
	private int regUserId;//登记人ID
	private String regUserName;//登记人姓名
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
	public int getProductName() {
		return productName;
	}
	public void setProductName(int productName) {
		this.productName = productName;
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
	public String getCreateTimeDesc() {
		return createTimeDesc;
	}
	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}
	public void setRegUserId(int regUserId) {
		this.regUserId = regUserId;
	}
	public int getRegUserId() {
		return regUserId;
	}
	public void setRegUserName(String regUserName) {
		this.regUserName = regUserName;
	}
	public String getRegUserName() {
		return regUserName;
	}
}
