package com.tianee.oa.core.base.officeProducts.model;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

public class TeeOfficeCategoryModel {
	private int sid;
	private String catName;//类别名称
	private int officeDepositoryId;//用品库
	private String officeDepositoryDesc;//用品库
	private int createUserId;//创建人
	private String createUserDesc;//创建人
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public int getOfficeDepositoryId() {
		return officeDepositoryId;
	}
	public void setOfficeDepositoryId(int officeDepositoryId) {
		this.officeDepositoryId = officeDepositoryId;
	}
	public String getOfficeDepositoryDesc() {
		return officeDepositoryDesc;
	}
	public void setOfficeDepositoryDesc(String officeDepositoryDesc) {
		this.officeDepositoryDesc = officeDepositoryDesc;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserDesc() {
		return createUserDesc;
	}
	public void setCreateUserDesc(String createUserDesc) {
		this.createUserDesc = createUserDesc;
	}
	
	
}
