package com.tianee.oa.core.xt.model;

import java.util.ArrayList;
import java.util.List;


import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TeeXTRunPrcsModel {
	private int sid;//主键	
	//private TeeXTRun xtRun;//所属事项
	private String parentCreateUserName;
	private int xtRunId;//所属事项主键
	private String subject;
	private String createUserName;
	private int importantLevel;
	private String runStatusDesc;//事项状态
	private int docType;
	private int runAttNum;
	private int optPriv;
	
	//private TeePerson prcsUser;//办理人
	private int prcsUserId;//办理人主键
	private String prcsUserName;//办理人姓名
	
	//private Calendar receiveTime;//接收时间
	private String receiveTimeStr;//接受时间字符串
	
	//private Calendar handleTime;//处理时间
	private String handleTimeStr;//处理时间字符串
	
	private int status;//状态  0=未接收  1=已接收  2=已办结
	private String statusDesc;//状态描述
	
	private int opinionType;//意见类型  1=已阅  2=同意   3=不同意
	private String opinionTypeDesc;//意见描述
	
	private String  opinionContent;//意见内容
	
	private String sendTimeStr;//任务发起时间
	
	private String passedTime;//处理时长
	
	
	private int  deleteStatus;//删除状态  0=未删除  1=已删除

	private int replyNum;//回复数量
	
	private String attachmentSidStr;//附件id串
	
	private List<TeeAttachmentModel> attList=new ArrayList<TeeAttachmentModel>();
	
	
	private int smsRemind;//是否短信提醒
	
	//private TeeXTRunPrcs prePrcs;//前置任务
    private int prePrcsId;//前置任务主键
	
	
    
    
	public int getPrePrcsId() {
		return prePrcsId;
	}

	public void setPrePrcsId(int prePrcsId) {
		this.prePrcsId = prePrcsId;
	}

	public int getOptPriv() {
		return optPriv;
	}

	public void setOptPriv(int optPriv) {
		this.optPriv = optPriv;
	}

	public String getParentCreateUserName() {
		return parentCreateUserName;
	}

	public void setParentCreateUserName(String parentCreateUserName) {
		this.parentCreateUserName = parentCreateUserName;
	}

	public int getDocType() {
		return docType;
	}

	public void setDocType(int docType) {
		this.docType = docType;
	}

	public int getRunAttNum() {
		return runAttNum;
	}

	public void setRunAttNum(int runAttNum) {
		this.runAttNum = runAttNum;
	}

	public int getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}

	public int getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(int smsRemind) {
		this.smsRemind = smsRemind;
	}

	public String getAttachmentSidStr() {
		return attachmentSidStr;
	}

	public void setAttachmentSidStr(String attachmentSidStr) {
		this.attachmentSidStr = attachmentSidStr;
	}

	public List<TeeAttachmentModel> getAttList() {
		return attList;
	}

	public void setAttList(List<TeeAttachmentModel> attList) {
		this.attList = attList;
	}

	public String getRunStatusDesc() {
		return runStatusDesc;
	}

	public void setRunStatusDesc(String runStatusDesc) {
		this.runStatusDesc = runStatusDesc;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public int getImportantLevel() {
		return importantLevel;
	}

	public void setImportantLevel(int importantLevel) {
		this.importantLevel = importantLevel;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getPassedTime() {
		return passedTime;
	}

	public void setPassedTime(String passedTime) {
		this.passedTime = passedTime;
	}

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getXtRunId() {
		return xtRunId;
	}

	public void setXtRunId(int xtRunId) {
		this.xtRunId = xtRunId;
	}

	public int getPrcsUserId() {
		return prcsUserId;
	}

	public void setPrcsUserId(int prcsUserId) {
		this.prcsUserId = prcsUserId;
	}

	public String getPrcsUserName() {
		return prcsUserName;
	}

	public void setPrcsUserName(String prcsUserName) {
		this.prcsUserName = prcsUserName;
	}

	public String getReceiveTimeStr() {
		return receiveTimeStr;
	}

	public void setReceiveTimeStr(String receiveTimeStr) {
		this.receiveTimeStr = receiveTimeStr;
	}

	public String getHandleTimeStr() {
		return handleTimeStr;
	}

	public void setHandleTimeStr(String handleTimeStr) {
		this.handleTimeStr = handleTimeStr;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public int getOpinionType() {
		return opinionType;
	}

	public void setOpinionType(int opinionType) {
		this.opinionType = opinionType;
	}

	public String getOpinionTypeDesc() {
		return opinionTypeDesc;
	}

	public void setOpinionTypeDesc(String opinionTypeDesc) {
		this.opinionTypeDesc = opinionTypeDesc;
	}

	public String getOpinionContent() {
		return opinionContent;
	}

	public void setOpinionContent(String opinionContent) {
		this.opinionContent = opinionContent;
	}
	
	
	
	
}
