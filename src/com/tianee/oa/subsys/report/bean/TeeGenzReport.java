package com.tianee.oa.subsys.report.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GENZ_REPORT")
public class TeeGenzReport {
	@Id
	@Column(name="RES_ID")
	private int resId;
	
	@Column(name="RES_ORDER")
	private int resOrder;
	
	@Column(name="RES_TYPE")
	private int resType;
	
	@Column(name="RES_NAME")
	private String resName;

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public int getResOrder() {
		return resOrder;
	}

	public void setResOrder(int resOrder) {
		this.resOrder = resOrder;
	}

	public int getResType() {
		return resType;
	}

	public void setResType(int resType) {
		this.resType = resType;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}
	
	
}
