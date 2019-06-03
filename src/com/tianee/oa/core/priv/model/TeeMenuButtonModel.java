package com.tianee.oa.core.priv.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeMenuButtonModel extends TeeBaseModel {

	private static final long serialVersionUID = 3228770404694415294L;

	private int id;
	
	private Integer menuId; 
	
	private String buttonNo;
	
	private String buttonName;
	
	private String buttonProp;

	private String buttonUrl;

	private Integer sortNo;

	private String remark;
	
	private String isPriv;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getButtonNo() {
		return buttonNo;
	}

	public void setButtonNo(String buttonNo) {
		this.buttonNo = buttonNo;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getButtonProp() {
		return buttonProp;
	}

	public void setButtonProp(String buttonProp) {
		this.buttonProp = buttonProp;
	}

	public String getButtonUrl() {
		return buttonUrl;
	}

	public void setButtonUrl(String buttonUrl) {
		this.buttonUrl = buttonUrl;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsPriv() {
		return isPriv;
	}

	public void setIsPriv(String isPriv) {
		this.isPriv = isPriv;
	}
	
}
