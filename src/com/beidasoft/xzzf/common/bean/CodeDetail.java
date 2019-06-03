package com.beidasoft.xzzf.common.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_BASE_CODE_DETAIL")
public class CodeDetail {
	//代码细项表子表
	@Id
	@Column(name = "ID")
	private String id;
	
	//代码表主表ID
	@Column(name = "PARENT_ID")
	private String parentId;
	
	//代码值关键字
	@Column(name = "DETAIL_KEY")
	private String detailKey;
	
	//代码值
	@Column(name = "DETAIL_VALUE")
	private String detailValue;
	
	//备注
	@Column(name = "REMARK")
	private String remark;
	
	//代码关键字
	@Column(name = "MAIN_KEY")
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
