package com.tianee.oa.core.base.attend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;

public class TeeDutyComplaintModel {
	private int sid;//自增id
	//private TeePerson user;//创建人
	private int userId;
	private String userName;
	
	//private Calendar createTime;//创建时间 
	private String createTimeStr;
	
	private int status;//审批状态   0待审批    1已批准   2未批准
	private String statusDesc;
	private int registerNum;//针对的是第几次登记   值为 1,2,3,4,5,6
	private String reason;//异常原因
	private String remarkTimeStr;//记录时间
	
//	private TeePerson approver;//审批人
	private int approverId;
	private String approverName;
	
	
	private List<TeeAttachmentModel> attachList=new ArrayList<TeeAttachmentModel>();
	private String attachmentSidStr;//附件id串
	
	
	
	
	public String getAttachmentSidStr() {
		return attachmentSidStr;
	}

	public void setAttachmentSidStr(String attachmentSidStr) {
		this.attachmentSidStr = attachmentSidStr;
	}

	public int getApproverId() {
		return approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}



	public int getSid() {
		return sid;
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public int getRegisterNum() {
		return registerNum;
	}

	public String getReason() {
		return reason;
	}

	public String getRemarkTimeStr() {
		return remarkTimeStr;
	}

	

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public void setRegisterNum(int registerNum) {
		this.registerNum = registerNum;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setRemarkTimeStr(String remarkTimeStr) {
		this.remarkTimeStr = remarkTimeStr;
	}

	public List<TeeAttachmentModel> getAttachList() {
		return attachList;
	}

	public void setAttachList(List<TeeAttachmentModel> attachList) {
		this.attachList = attachList;
	}

	

    
     
}
