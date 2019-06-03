package com.tianee.oa.core.base.pm.model;


/**
 * 工作经历管理
 *
 */
public class TeeHumanExperienceModel {
	private int sid;
	private String pos;//担任职务
	
	private String dept;//所在部门
	
	private String prover;//证明人
	
	private String tradeType;//行业类别
	
	private String startTimeDesc;//开始日期
	
	private String endTimeDesc;//结束日期
	
	private String workAt;//工作单位
	
	private String contact;//联系方式
	
	private String content;//工作内容
	
	private String leaveCause;//离职原因
	
	private String remark;//备注
	
	private int humanDocSid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getProver() {
		return prover;
	}

	public void setProver(String prover) {
		this.prover = prover;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}


	public String getStartTimeDesc() {
		return startTimeDesc;
	}

	public void setStartTimeDesc(String startTimeDesc) {
		this.startTimeDesc = startTimeDesc;
	}

	public String getEndTimeDesc() {
		return endTimeDesc;
	}

	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
	}

	public String getWorkAt() {
		return workAt;
	}

	public void setWorkAt(String workAt) {
		this.workAt = workAt;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLeaveCause() {
		return leaveCause;
	}

	public void setLeaveCause(String leaveCause) {
		this.leaveCause = leaveCause;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getHumanDocSid() {
		return humanDocSid;
	}

	public void setHumanDocSid(int humanDocSid) {
		this.humanDocSid = humanDocSid;
	}

	
	
}
