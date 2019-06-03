package com.tianee.oa.core.general.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

@Entity
@Table(name="LUCENE_TASK")
public class TeeLuceneTask {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="MODEL_NO")
	@Index(name="LUCENE_TASK_MODEL_NO")
	private String modelNo;//模块标识
	
	@Column(name="MODEL_ID")
	@Index(name="LUCENE_TASK_MODEL_ID")
	private String modelId;//模块ID
	
	@Column(name="OP_TYPE")
	private int opType;//操作类型   1：增加  2：修改  3：删除   4：重置索引
	
	@Column(name="CR_TIME")
	private Calendar crTime = Calendar.getInstance();//创建时间

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public int getOpType() {
		return opType;
	}

	public void setOpType(int opType) {
		this.opType = opType;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}
}
