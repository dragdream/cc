package com.beidasoft.xzzf.punish.common.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_CONF_FLOW")
public class ConfFlow {

	// 主键ID
	@Id
	@Column(name = "ID")
	private String id;

	// 系统配置环节CODE
	@Column(name = "CONF_TACHE_CODE")
	private String confTacheCode;

	// 系统配置环节名称
	@Column(name = "CONF_TACHE_NAME")
	private String confTacheName;

	// 系统定义流程ID
	@Column(name = "CONF_FLOW_ID")
	private int confFlowId;

	// 系统定义流程名称
	@Column(name = "CONF_FLOW_NAME")
	private String confFlowName;

	// 是否停用 默认 0
	@Column(name = "IS_STOP")
	private int isStop;

	// 是否删除 默认 0
	@Column(name = "IS_DELETE")
	private int isDelete;

	// 文书创建时间
	@Column(name = "CREATE_TIME")
	private Date createTime;

	// 系统定义文书模板ID
	@Column(name = "CONF_DOC_ID")
	private String confDocId;

	// 排序
	@Column(name = "CONF_FLOW_INDEX")
	private int confFlowIndex;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConfTacheCode() {
		return confTacheCode;
	}

	public void setConfTacheCode(String confTacheCode) {
		this.confTacheCode = confTacheCode;
	}

	public String getConfTacheName() {
		return confTacheName;
	}

	public void setConfTacheName(String confTacheName) {
		this.confTacheName = confTacheName;
	}

	public int getConfFlowId() {
		return confFlowId;
	}

	public void setConfFlowId(int confFlowId) {
		this.confFlowId = confFlowId;
	}

	public String getConfFlowName() {
		return confFlowName;
	}

	public void setConfFlowName(String confFlowName) {
		this.confFlowName = confFlowName;
	}

	public int getIsStop() {
		return isStop;
	}

	public void setIsStop(int isStop) {
		this.isStop = isStop;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getConfDocId() {
		return confDocId;
	}

	public void setConfDocId(String confDocId) {
		this.confDocId = confDocId;
	}

	public int getConfFlowIndex() {
		return confFlowIndex;
	}

	public void setConfFlowIndex(int confFlowIndex) {
		this.confFlowIndex = confFlowIndex;
	}

}
