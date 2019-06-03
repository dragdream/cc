package com.tianee.oa.core.customnumber.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="custom_number_record")
public class TeeCustomNumberRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CUSTOM_NUMBER_RECORD_seq_gen")
	@SequenceGenerator(name="CUSTOM_NUMBER_RECORD_seq_gen", sequenceName="CUSTOM_NUMBER_RECORD_seq")
    private int uuid;//主键
	
	@Column(name="CUSTOM_NUMBER_ID")
	private int customNumberId;//关联编号
	
	@Column(name="NUMBER_STYLE")
	private String numberStyle;//编号样式    2016050300002
	
	@Column(name="NUMBER_VALUE")
	private int numberValue;//编号值   2
	
	@Column(name="MODEL_")
	private String model;//模块编码
	
	@Column(name="MODEL_ID")
	private String modelId;///模块ID
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public int getCustomNumberId() {
		return customNumberId;
	}

	public void setCustomNumberId(int customNumberId) {
		this.customNumberId = customNumberId;
	}

	public String getNumberStyle() {
		return numberStyle;
	}

	public void setNumberStyle(String numberStyle) {
		this.numberStyle = numberStyle;
	}

	public int getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(int numberValue) {
		this.numberValue = numberValue;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	
	
}
