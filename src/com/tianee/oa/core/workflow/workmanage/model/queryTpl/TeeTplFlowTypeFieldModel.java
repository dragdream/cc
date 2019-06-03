package com.tianee.oa.core.workflow.workmanage.model.queryTpl;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeTplFlowTypeFieldModel extends TeeBaseModel{
	
	private int flowId;
	private int flowStatus;
	private int flowQueryType;
	private int beginUser;
	private String beginUserName;
	private String runNameRelation;
	private String runName;
	private Date prcsDate1;
	private Date prcsDate2;
	private Date prcsDate3;
	private Date prcsDate4;
	private String attachmentName;
	

	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public int getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(int flowStatus) {
		this.flowStatus = flowStatus;
	}
	public int getFlowQueryType() {
		return flowQueryType;
	}
	public void setFlowQueryType(int flowQueryType) {
		this.flowQueryType = flowQueryType;
	}
	public int getBeginUser() {
		return beginUser;
	}
	public void setBeginUser(int beginUser) {
		this.beginUser = beginUser;
	}
	public String getBeginUserName() {
		return beginUserName;
	}
	public void setBeginUserName(String beginUserName) {
		this.beginUserName = beginUserName;
	}
	public String getRunNameRelation() {
		return runNameRelation;
	}
	public void setRunNameRelation(String runNameRelation) {
		this.runNameRelation = runNameRelation;
	}
	public String getRunName() {
		return runName;
	}
	public void setRunName(String runName) {
		this.runName = runName;
	}
	public Date getPrcsDate1() {
		return prcsDate1;
	}
	public void setPrcsDate1(Date prcsDate1) {
		this.prcsDate1 = prcsDate1;
	}
	public Date getPrcsDate2() {
		return prcsDate2;
	}
	public void setPrcsDate2(Date prcsDate2) {
		this.prcsDate2 = prcsDate2;
	}
	public Date getPrcsDate3() {
		return prcsDate3;
	}
	public void setPrcsDate3(Date prcsDate3) {
		this.prcsDate3 = prcsDate3;
	}
	public Date getPrcsDate4() {
		return prcsDate4;
	}
	public void setPrcsDate4(Date prcsDate4) {
		this.prcsDate4 = prcsDate4;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
	


	
	
}
