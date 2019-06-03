package com.tianee.oa.core.base.email.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeMailBox;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeMailModel  extends TeeBaseModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer sid;//邮件body Id
	private Integer ifBody;//sid是否是邮件主体0:不是 1：是
	private TeeMailBody mailBody;//邮件主体
	private TeeMailBox mailBox;//邮件箱
	private TeePerson toId;//接收人员uuid，不用对象型
	private Integer readFlag;//邮件读取标记 0:未读 1：已读
	private Integer deleteFlag;//邮件删除标记 0：未删除 1：收件人删除 
	private Integer delFlag;//邮件删除标记 0：未删除 1：发件人删除
	private TeePerson fromId;//发送人员uuid，不用对象型
	private String formIdStr ;//
	private String formName;
	private Date sendTime;//发送时间
	private String content;//邮件内容
	private String subject;//邮件标题
	private String important;//重要程度 空 - 一般邮件 ,1 - 重要 ,2 - 非常重要
	private Integer receipt;//是否请求阅读收条 0-不请求  1-请求  如果是1，则收件人收到邮件之后，系统会自动给发件人发一个短消息（收条）
	private Integer receiveType;//接受类型 0-收件人  1-被抄送人 2-被密送人 
	private String smsRemind;//是否使用短信提醒 0-不提醒  1-提醒
	private String sendFlag;//是否已发送 0-未发送  1-已发送
	private List<TeeAttachment> mailAttachMent;//附件组
	private List<TeeAttachmentModel> mailAttachMentModel;//附件组模型
	private String userListIds;
	private String copyUserListIds;
	private String secretUserListIds;
	private String userListNames;
	private String copyUserListNames;
	private String secretUserListNames;
	private String fromWebMail;//从自己哪个外部邮箱发送
	private String toWebmail;//外部收件人邮箱串 ,用逗号分隔
	private String webmailHtml;//保存外部邮件html
	private int webmailCount;//外部收件人数（用于保存i）
	private List<String[]> users = new ArrayList<String[]>();//id,name,readFlag
	private List<String[]> copyUsers = new ArrayList<String[]>();//id,name,readFlag
	private List<String[]> secretUsers = new ArrayList<String[]>();//id,name,readFlag
	private String queryType;//为首页查询
	private Integer type;//为首页查询
	private Integer ifBox;//为首页查询
	private int ifWebMail = 0;//是否是外部邮件
	private String toWebMail;//发到自己哪个外部邮箱
	private String webMailUid;//外部邮件uid
	private String ccWebMail;//
	private String isHtml;
	private String largeAttachment;
	private String webMailId;
	private String nameOrder;
	
	
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public String getNameOrder() {
		return nameOrder;
	}
	public void setNameOrder(String nameOrder) {
		this.nameOrder = nameOrder;
	}
	public String getWebMailId() {
		return webMailId;
	}
	public void setWebMailId(String webMailId) {
		this.webMailId = webMailId;
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
	public String getWebMailUid() {
		return webMailUid;
	}
	public void setWebMailUid(String webMailUid) {
		this.webMailUid = webMailUid;
	}
	public Integer getIfBox() {
		return ifBox;
	}
	public void setIfBox(Integer ifBox) {
		this.ifBox = ifBox;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
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
	public Integer getIfBody() {
		return ifBody;
	}
	public void setIfBody(Integer ifBody) {
		this.ifBody = ifBody;
	}
	public List<TeeAttachmentModel> getMailAttachMentModel() {
		return mailAttachMentModel;
	}
	public void setMailAttachMentModel(List<TeeAttachmentModel> mailAttachMentModel) {
		this.mailAttachMentModel = mailAttachMentModel;
	}
	public List<String[]> getUsers() {
		return users;
	}
	public void setUsers(List<String[]> users) {
		this.users = users;
	}
	public List<String[]> getCopyUsers() {
		return copyUsers;
	}
	public void setCopyUsers(List<String[]> copyUsers) {
		this.copyUsers = copyUsers;
	}
	public List<String[]> getSecretUsers() {
		return secretUsers;
	}
	public void setSecretUsers(List<String[]> secretUsers) {
		this.secretUsers = secretUsers;
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
	public String getUserListIds() {
		return userListIds;
	}
	public void setUserListIds(String userListIds) {
		this.userListIds = userListIds;
	}
	public String getCopyUserListIds() {
		return copyUserListIds;
	}
	public void setCopyUserListIds(String copyUserListIds) {
		this.copyUserListIds = copyUserListIds;
	}
	public String getSecretUserListIds() {
		return secretUserListIds;
	}
	public void setSecretUserListIds(String secretUserListIds) {
		this.secretUserListIds = secretUserListIds;
	}
	public String getUserListNames() {
		return userListNames;
	}
	public void setUserListNames(String userListNames) {
		this.userListNames = userListNames;
	}
	public String getCopyUserListNames() {
		return copyUserListNames;
	}
	public void setCopyUserListNames(String copyUserListNames) {
		this.copyUserListNames = copyUserListNames;
	}
	public String getSecretUserListNames() {
		return secretUserListNames;
	}
	public void setSecretUserListNames(String secretUserListNames) {
		this.secretUserListNames = secretUserListNames;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	public TeeMailBody getMailBody() {
		return mailBody;
	}
	public void setMailBody(TeeMailBody mailBody) {
		this.mailBody = mailBody;
	}
	public TeeMailBox getMailBox() {
		return mailBox;
	}
	public void setMailBox(TeeMailBox mailBox) {
		this.mailBox = mailBox;
	}

	public Integer getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(Integer readFlag) {
		this.readFlag = readFlag;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public TeePerson getToId() {
		return toId;
	}
	public void setToId(TeePerson toId) {
		this.toId = toId;
	}
	public TeePerson getFromId() {
		return fromId;
	}
	public void setFromId(TeePerson fromId) {
		this.fromId = fromId;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getImportant() {
		return important;
	}
	public void setImportant(String important) {
		this.important = important;
	}
	public Integer getReceipt() {
		return receipt;
	}
	public void setReceipt(Integer receipt) {
		this.receipt = receipt;
	}
	public Integer getReceiveType() {
		return receiveType;
	}
	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}
	public String getSmsRemind() {
		return smsRemind;
	}
	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public List<TeeAttachment> getMailAttachMent() {
		return mailAttachMent;
	}
	public void setMailAttachMent(List<TeeAttachment> mailAttachMent) {
		this.mailAttachMent = mailAttachMent;
	}
	public String getFormIdStr() {
		return formIdStr;
	}
	public void setFormIdStr(String formIdStr) {
		this.formIdStr = formIdStr;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
