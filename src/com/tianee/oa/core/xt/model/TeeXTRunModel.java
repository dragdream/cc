package com.tianee.oa.core.xt.model;
import java.util.ArrayList;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;


public class TeeXTRunModel {
	private int sid;//主键
	private String subject;//标题
	private int importantLevel;//事项级别
	private String importantLevelStr;//事项级别描述
	private int smsRemind;//是否短信提醒
	private String userIds;//办理人员id字符串
	private String userNames;//办理人员姓名字符串
	private int deadLine;//事项期限
	//private Calendar deadLineTime;//事项到期时间
	private String deadLineTimeStr;//事项到期时间字符串
	
	private int advanceRemind;//提前提醒
	private int autoStop;//是否到期自动终止
	private int optPriv;//允许操作权限
	//private Calendar createTime;//创建时间
	private String createTimeStr;//创建时间字符串
	
	//private TeePerson createUser;//创建人
	private int createUserId;//创建人主键
	private String createUserName;//创建人姓名
	private  int avatar;//头像附件的主键
	
	private int status;//状态   0=保存未发送   1=已发送未终止   2=已发送已中终止
	
	private String remark;//备注
	private int docType;//正文类型  1=标准正文   2=word正文  3=excel正文   4=pdf正文
	
	private String content;//标准正文内容
	
	//private TeeAttachment doc;//word正文   excel正文  pdf正文 所对应的附件
	private TeeAttachmentModel docModel;
	
	private int docAttachId;//正文附件的主键
	
	private String attachmentSidStr;//附件主键id串
    
	private List<TeeAttachmentModel> attList=new ArrayList<TeeAttachmentModel>();
	
	
	private String sendTimeStr;//发送时间
	
	private String createUserDeptName;
	
	private String createUserRoleName;
	
	private int repeatFy;//转发原附言
	
	private int repeatYj;//转发原意见
	
	//private TeeXTRun parent;//父级事项
	private String parentCreateUserName;
	
	
	
	
	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public int getRepeatFy() {
		return repeatFy;
	}

	public void setRepeatFy(int repeatFy) {
		this.repeatFy = repeatFy;
	}

	public int getRepeatYj() {
		return repeatYj;
	}

	public void setRepeatYj(int repeatYj) {
		this.repeatYj = repeatYj;
	}

	public String getParentCreateUserName() {
		return parentCreateUserName;
	}

	public void setParentCreateUserName(String parentCreateUserName) {
		this.parentCreateUserName = parentCreateUserName;
	}

	public String getCreateUserDeptName() {
		return createUserDeptName;
	}

	public void setCreateUserDeptName(String createUserDeptName) {
		this.createUserDeptName = createUserDeptName;
	}

	public String getCreateUserRoleName() {
		return createUserRoleName;
	}

	public void setCreateUserRoleName(String createUserRoleName) {
		this.createUserRoleName = createUserRoleName;
	}

	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}

	public List<TeeAttachmentModel> getAttList() {
		return attList;
	}

	public void setAttList(List<TeeAttachmentModel> attList) {
		this.attList = attList;
	}

	public int getDocAttachId() {
		return docAttachId;
	}

	public void setDocAttachId(int docAttachId) {
		this.docAttachId = docAttachId;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getImportantLevel() {
		return importantLevel;
	}

	public void setImportantLevel(int importantLevel) {
		this.importantLevel = importantLevel;
	}

	public String getImportantLevelStr() {
		return importantLevelStr;
	}

	public void setImportantLevelStr(String importantLevelStr) {
		this.importantLevelStr = importantLevelStr;
	}

	public int getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(int smsRemind) {
		this.smsRemind = smsRemind;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public int getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(int deadLine) {
		this.deadLine = deadLine;
	}

	public String getDeadLineTimeStr() {
		return deadLineTimeStr;
	}

	public void setDeadLineTimeStr(String deadLineTimeStr) {
		this.deadLineTimeStr = deadLineTimeStr;
	}

	public int getAdvanceRemind() {
		return advanceRemind;
	}

	public void setAdvanceRemind(int advanceRemind) {
		this.advanceRemind = advanceRemind;
	}

	public int getAutoStop() {
		return autoStop;
	}

	public void setAutoStop(int autoStop) {
		this.autoStop = autoStop;
	}

	public int getOptPriv() {
		return optPriv;
	}

	public void setOptPriv(int optPriv) {
		this.optPriv = optPriv;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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

	public int getDocType() {
		return docType;
	}

	public void setDocType(int docType) {
		this.docType = docType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TeeAttachmentModel getDocModel() {
		return docModel;
	}

	public void setDocModel(TeeAttachmentModel docModel) {
		this.docModel = docModel;
	}

	public String getAttachmentSidStr() {
		return attachmentSidStr;
	}

	public void setAttachmentSidStr(String attachmentSidStr) {
		this.attachmentSidStr = attachmentSidStr;
	}
	
	
	
	
	
}
