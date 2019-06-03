package com.tianee.oa.subsys.bisengin.model;

public class BisTableEngineModel {
	private int sid;
	private int bisTableId;
	private int flowId;
	private String flowName;
	private String bisModel;
	private int type;
	private String listTitle;//明细表title
	private int status;
	private String remark;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getBisTableId() {
		return bisTableId;
	}
	public void setBisTableId(int bisTableId) {
		this.bisTableId = bisTableId;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getBisModel() {
		return bisModel;
	}
	public void setBisModel(String bisModel) {
		this.bisModel = bisModel;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getListTitle() {
		return listTitle;
	}
	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
