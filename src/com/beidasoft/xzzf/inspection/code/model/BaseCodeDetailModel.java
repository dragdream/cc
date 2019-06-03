package com.beidasoft.xzzf.inspection.code.model;

public class BaseCodeDetailModel {
	//主键
	private String id;
	//父码
	private String parentId;
	//代码
	private String detailKey;
	//代码值
	private String detailValue;
	//备注
	private String remark;
	//代码关键字
	private String mainKey;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getDetailKey() {
		return detailKey;
	}
	public void setDetailKey(String detailKey) {
		this.detailKey = detailKey;
	}
	public String getDetailValue() {
		return detailValue;
	}
	public void setDetailValue(String detailValue) {
		this.detailValue = detailValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMainKey() {
		return mainKey;
	}
	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}
}
