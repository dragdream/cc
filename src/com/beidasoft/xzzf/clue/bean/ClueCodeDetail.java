package com.beidasoft.xzzf.clue.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_BASE_CODE_DETAIL")
/**
 * 下拉列表实体类
 * @author A
 *
 */
public class ClueCodeDetail {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "PARENT_ID")
	private String parentId;
	
	@Column(name = "DETAIL_KEY")
	private String detailKey;
	
	@Column(name = "DETAIL_VALUE")
	private String detailValue;
	
	@Column(name = "REMARK")
	private String remark;
	
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
