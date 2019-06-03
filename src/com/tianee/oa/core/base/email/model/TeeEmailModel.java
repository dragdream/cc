package com.tianee.oa.core.base.email.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

public class TeeEmailModel {

	// mailBody字段
	private int mailBodySid;// 自增id
	private int fromUserId;// 发件人id
	private String fromUserUserId;//发件人userId
	private String fromUserName;// 发件人姓名
	private String subject;// 邮件标题
	private String content;// 邮件内容 。HTML文本（UEditor的内容）
	private String sendTimeStr; // 发送时间
	private String important;//重要程度 空 - 一般邮件 ,1 - 重要 ,2 - 非常重要
	private String fromWebMail;//从自己哪个外部邮箱发送
	private String toWebmail;//外部收件人邮箱串 ,用逗号分隔
	private String webmailHtml;//保存外部邮件html
	private int webmailCount;//外部收件人数（用于保存i）
	private int ifWebMail = 0;//是否是外部邮件;0-否；1-是
	private String toWebMail;//发到自己哪个外部邮箱
	private String webMailId; //外部邮箱id
	private String webMailUid;//外部邮件uid
	private String ccWebMail;//
	private String isHtml;
	private String largeAttachment;
	private String nameOrder;
	private String emailLevel;
	private String emailLevelDesc;//邮件级别
	private int pubType;//发布范围   0=指定人员  1=所有人员

	// mail字段
	private int sid;// 自增id
	private int toUserId;// 收件人id
	private String toUserName;// 收件人姓名
	private String deptName;//收件人所属部门
	private String roleName;//收件人所属角色
	
	
	
	
	public int getPubType() {
		return pubType;
	}

	public void setPubType(int pubType) {
		this.pubType = pubType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	private String toUserNameAll;// 所以收件人姓名
	
	private int readFlag = 0;// 邮件读取标记 0:未读 1：已读
	
	private String readTimeStr;//第一次读取邮件时间
	
	
	

	private int deleteFlag = 0;// 邮件删除标记 0：未删除 1：收件人删除
	private int receipt;// 是否请求阅读收条 0-不请求 1-请求
						// 如果是1，则收件人收到邮件之后，系统会自动给发件人发一个短消息（收条）
	private int receiveType;// 接受类型 0-收件人 1-被抄送人 2-被密送人
	private List<Map<String,String>> userList = new ArrayList<Map<String,String>>();//收件人对象
	private List<Map<String,String>> copyUserList = new ArrayList<Map<String,String>>();//抄送人对象
	private List<Map<String,String>> secretUserList = new ArrayList<Map<String,String>>();//密送人对象
	private List<TeeAttachmentModel> attachMentModel;//附件组模型
	private long attachmentCount= 0;//附件数量
	
	private Map<String , String> extendData =  new  HashMap<String, String>();//扩展数据，
	
	
	
	public Map<String, String> getExtendData() {
		return extendData;
	}

	public void setExtendData(Map<String, String> extendData) {
		this.extendData = extendData;
	}

	public int getMailBodySid() {
		return mailBodySid;
	}

	public void setMailBodySid(int mailBodySid) {
		this.mailBodySid = mailBodySid;
	}

	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getToUserId() {
		return toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	public String getFromUserUserId() {
		return fromUserUserId;
	}

	public void setFromUserUserId(String fromUserUserId) {
		this.fromUserUserId = fromUserUserId;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getReceipt() {
		return receipt;
	}

	public void setReceipt(int receipt) {
		this.receipt = receipt;
	}

	public int getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(int receiveType) {
		this.receiveType = receiveType;
	}

	public List<Map<String, String>> getUserList() {
		return userList;
	}

	public void setUserList(List<Map<String, String>> userList) {
		this.userList = userList;
	}

	public List<Map<String, String>> getCopyUserList() {
		return copyUserList;
	}

	public void setCopyUserList(List<Map<String, String>> copyUserList) {
		this.copyUserList = copyUserList;
	}

	public List<Map<String, String>> getSecretUserList() {
		return secretUserList;
	}

	public void setSecretUserList(List<Map<String, String>> secretUserList) {
		this.secretUserList = secretUserList;
	}

	public List<TeeAttachmentModel> getAttachMentModel() {
		return attachMentModel;
	}

	public void setAttachMentModel(List<TeeAttachmentModel> attachMentModel) {
		this.attachMentModel = attachMentModel;
	}

	public String getToUserNameAll() {
		return toUserNameAll;
	}

	public void setToUserNameAll(String toUserNameAll) {
		this.toUserNameAll = toUserNameAll;
	}

	public String getImportant() {
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
	}

	public String getFromWebMail() {
		return fromWebMail;
	}

	public void setFromWebMail(String fromWebMail) {
		this.fromWebMail = fromWebMail;
	}

	public String getToWebmail() {
		return toWebmail;
	}

	public void setToWebmail(String toWebmail) {
		this.toWebmail = toWebmail;
	}

	public String getWebmailHtml() {
		return webmailHtml;
	}

	public void setWebmailHtml(String webmailHtml) {
		this.webmailHtml = webmailHtml;
	}

	public int getWebmailCount() {
		return webmailCount;
	}

	public void setWebmailCount(int webmailCount) {
		this.webmailCount = webmailCount;
	}

	public int getIfWebMail() {
		return ifWebMail;
	}

	public void setIfWebMail(int ifWebMail) {
		this.ifWebMail = ifWebMail;
	}

	public String getToWebMail() {
		return toWebMail;
	}

	public void setToWebMail(String toWebMail) {
		this.toWebMail = toWebMail;
	}

	public String getWebMailId() {
		return webMailId;
	}

	public void setWebMailId(String webMailId) {
		this.webMailId = webMailId;
	}

	public String getWebMailUid() {
		return webMailUid;
	}

	public void setWebMailUid(String webMailUid) {
		this.webMailUid = webMailUid;
	}

	public String getCcWebMail() {
		return ccWebMail;
	}

	public void setCcWebMail(String ccWebMail) {
		this.ccWebMail = ccWebMail;
	}

	public String getIsHtml() {
		return isHtml;
	}

	public void setIsHtml(String isHtml) {
		this.isHtml = isHtml;
	}

	public String getLargeAttachment() {
		return largeAttachment;
	}

	public void setLargeAttachment(String largeAttachment) {
		this.largeAttachment = largeAttachment;
	}

	public String getNameOrder() {
		return nameOrder;
	}

	public void setNameOrder(String nameOrder) {
		this.nameOrder = nameOrder;
	}

	public long getAttachmentCount() {
		return attachmentCount;
	}

	public void setAttachmentCount(long attachmentCount) {
		this.attachmentCount = attachmentCount;
	}

	public String getEmailLevel() {
		return emailLevel;
	}

	public void setEmailLevel(String emailLevel) {
		this.emailLevel = emailLevel;
	}

	public String getEmailLevelDesc() {
		return emailLevelDesc;
	}

	public void setEmailLevelDesc(String emailLevelDesc) {
		this.emailLevelDesc = emailLevelDesc;
	}
	public String getReadTimeStr() {
		return readTimeStr;
	}

	public void setReadTimeStr(String readTimeStr) {
		this.readTimeStr = readTimeStr;
	}
}
