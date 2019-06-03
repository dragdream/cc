package com.beidasoft.zfjd.power.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_POWER_LEVEL")
public class PowerLevel {
	
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	// 职权ID
	@ManyToOne
	@JoinColumn(name = "POWER_ID") // 这是双向的 要写JoinColumn 对面要写 mappedBy
	private Power powerLevel;
	
	@Column(name = "POWER_STAGE")
	private String powerStage;
	
	@Column(name = "REMARK")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Power getPowerLevel() {
		return powerLevel;
	}

	public void setPowerLevel(Power powerLevel) {
		this.powerLevel = powerLevel;
	}

	public String getPowerStage() {
		return powerStage;
	}

	public void setPowerStage(String powerStage) {
		this.powerStage = powerStage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}