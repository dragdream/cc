package com.beidasoft.xzzf.punish.common.model;

public class ConfFlowModel {
	
	private String id;                              //主键ID
	
	private String confTacheCode;					//系统配置环节CODE
	
	private String confTacheName;	                //系统配置环节名称	
	
	private int confFlowId;				    		//系统定义流程ID
	
	private String confFlowName;					//系统定义流程名称
	
	private int isStop;								//是否停用 默认 0
	
	private int isDelete;							//是否删除 默认 0

	private String createTimeStr;                   //文书创建时间
	
	private String confDocId;                       //系统定义文书模板ID
	
	private int confFlowIndex;                      //排序
	
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

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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

	public String getCreateTimeStr() {
		return createTimeStr;
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
