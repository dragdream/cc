package com.tianee.oa.core.base.pm.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


/**
 * 合同管理model
 *
 */
public class TeeHumanContractModel {
	private int sid;
	private String conTitle;//合同标题
	private String conCode;//合同编号
	private String conType;//合同类型
	private String conTypeDesc;//合同类型描述
	private String conAttr;//合同属性
	private String conAttrDesc;//合同属性描述
	private String conStatus;//合同状态
	private String conStatusDesc;//合同状态
	private String validTimeDesc;//生效时间
	private String invalidTimeDesc;//解除时间
	private String endTimeDesc;//结束时间
	private int signCount;//签约次数
	private String renewDateDesc;
	
	private String lastRemindDateDesc;
	
	private String remark;//备注
	private int humanDocSid;//关联档案信息
	
	private List<TeeAttachmentModel> attachMentModel;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getConTitle() {
		return conTitle;
	}
	public void setConTitle(String conTitle) {
		this.conTitle = conTitle;
	}
	public String getConCode() {
		return conCode;
	}
	public void setConCode(String conCode) {
		this.conCode = conCode;
	}
	public String getConType() {
		return conType;
	}
	public void setConType(String conType) {
		this.conType = conType;
	}
	public String getConAttr() {
		return conAttr;
	}
	public void setConAttr(String conAttr) {
		this.conAttr = conAttr;
	}
	public String getConStatus() {
		return conStatus;
	}
	public void setConStatus(String conStatus) {
		this.conStatus = conStatus;
	}
	public String getValidTimeDesc() {
		return validTimeDesc;
	}
	public void setValidTimeDesc(String validTimeDesc) {
		this.validTimeDesc = validTimeDesc;
	}
	public String getInvalidTimeDesc() {
		return invalidTimeDesc;
	}
	public void setInvalidTimeDesc(String invalidTimeDesc) {
		this.invalidTimeDesc = invalidTimeDesc;
	}
	public String getEndTimeDesc() {
		return endTimeDesc;
	}
	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
	}
	public int getSignCount() {
		return signCount;
	}
	public void setSignCount(int signCount) {
		this.signCount = signCount;
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
	public String getConTypeDesc() {
		return conTypeDesc;
	}
	public void setConTypeDesc(String conTypeDesc) {
		this.conTypeDesc = conTypeDesc;
	}
	public String getConAttrDesc() {
		return conAttrDesc;
	}
	public void setConAttrDesc(String conAttrDesc) {
		this.conAttrDesc = conAttrDesc;
	}
	public String getConStatusDesc() {
		return conStatusDesc;
	}
	public void setConStatusDesc(String conStatusDesc) {
		this.conStatusDesc = conStatusDesc;
	}
	public String getRenewDateDesc() {
		return renewDateDesc;
	}
	public void setRenewDateDesc(String renewDateDesc) {
		this.renewDateDesc = renewDateDesc;
	}
	public String getLastRemindDateDesc() {
		return lastRemindDateDesc;
	}
	public void setLastRemindDateDesc(String lastRemindDateDesc) {
		this.lastRemindDateDesc = lastRemindDateDesc;
	}
	public List<TeeAttachmentModel> getAttachMentModel() {
		return attachMentModel;
	}
	public void setAttachMentModel(List<TeeAttachmentModel> attachMentModel) {
		this.attachMentModel = attachMentModel;
	}

}
