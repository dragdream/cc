package com.tianee.oa.core.base.email.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "MAIL_BODY")
public class TeeMailBody implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MAIL_BODY_seq_gen")
	@SequenceGenerator(name="MAIL_BODY_seq_gen", sequenceName="MAIL_BODY_seq")
	private int sid;// 自增id

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDX198568d501244ca58c756e25a14")
	@JoinColumn(name = "FROM_ID")
	private TeePerson fromuser;// 发件人

	@Column(name = "SUBJECT")
	private String subject;// 邮件标题

	@Lob
	@Column(name = "CONTENT", nullable = true)
	private String content;// 邮件内容 。HTML文本（UEditor的内容）

	@Column(name = "SEND_TIME")
	private Date sendTime;// 发送时间

	@Column(name = "SEND_FLAG")
	private String sendFlag;// 是否已发送 0-未发送 1-已发送

	@Column(name = "SMS_REMIND")
	private String smsRemind;// 是否使用短信提醒 0-不提醒 1-提醒
	
	@Column(name = "IMPORTANT")
	private String important;// 重要程度 空 - 一般邮件 ,1 - 重要 ,2 - 非常重要

	@Column(name = "SIZE_")
	private long size;// 邮件大小

	@Lob
	@Column(name = "TO_WEBMAIL", nullable = true)
	private String toWebmail;// 外部收件人邮箱串 ,用逗号分隔

	@Lob
	@Column(name = "WEBMAIL_HTML", nullable = true)
	private String webmailHtml;// 外部邮件html

	@Column(name = "WEBMAIL_COUNT")
	private int webmailCount = 0;// 外部收件人数（用于保存i）

	@Lob
	@Column(name = "COMPRESS_CONTENT", nullable = true)
	private String compressContent;// 压缩后的邮件内容

	@Column(name = "IF_WEBMAIL")
	private int ifWebMail = 0;// 是否是外部邮件

	@Column(name = "FROM_WEB_MAIL")
	private String fromWebMail;// 从哪个外部邮箱发来

	@Lob
	@Column(name = "TO_WEB_MAIL")
	private String toWebMail;// 发到自己哪个外部邮箱

	@Column(name = "CC_WEBMAIL")
	private String ccWebMail;//

	@Column(name = "WEBMAIL_UID")
	private String webMailUid;// 外部邮件uid

	@Column(name = "IS_HTML")
	private String isHtml;

	@Column(name = "LARGE_ATTACHMENT")
	private String largeAttachment;

	@Column(name = "WEB_MAIL_ID")
	private String webMailId;

	@Column(name = "NAME_ORDER")
	private String nameOrder;

	@Column(name = "DELETE_FLAG", columnDefinition = "int default 0")
	@Index(name = "TeeMailBody_maildeleteFlag")
	private int delFlag = 0;// 邮件删除标记 0：未删除 1：发件人删除
	
	@Column(name="EMAIL_JJCD")
	private String emailLevel;//邮件级别   系统编码
	

	/**
	 * 增加日期2014-0816
	 */
	@Lob
	@Column(name = "RECIPIENT", nullable = true)
	private String recipient;// 收件人名称 ,用逗号分隔（详情显示用）
	@Lob
	@Column(name = "CARBON_COPY", nullable = true)
	private String carbonCopy;// 抄送人名称 ,用逗号分隔（详情显示用）
	@Lob
	@Column(name = "blind_Carbon_Copy", nullable = true)
	private String blindCarbonCopy;// 密送人名称 ,用逗号分隔（详情显示用）
	
	
	@Column(name="pub_type")
	private int pubType;//收件人规则：  0=指定人员  1=所有人员
	
	
	
    
	

	public int getPubType() {
		return pubType;
	}

	public void setPubType(int pubType) {
		this.pubType = pubType;
	}

	public String getEmailLevel() {
		return emailLevel;
	}

	public void setEmailLevel(String emailLevel) {
		this.emailLevel = emailLevel;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
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

	public String getLargeAttachment() {
		return largeAttachment;
	}

	public void setLargeAttachment(String largeAttachment) {
		this.largeAttachment = largeAttachment;
	}

	public String getIsHtml() {
		return isHtml;
	}

	public void setIsHtml(String isHtml) {
		this.isHtml = isHtml;
	}

	public String getCcWebMail() {
		return ccWebMail;
	}

	public void setCcWebMail(String ccWebMail) {
		this.ccWebMail = ccWebMail;
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

	public int getWebmailCount() {
		return webmailCount;
	}

	public void setWebmailCount(int webmailCount) {
		this.webmailCount = webmailCount;
	}

	public String getWebmailHtml() {
		return webmailHtml;
	}

	public void setWebmailHtml(String webmailHtml) {
		this.webmailHtml = webmailHtml;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getFromuser() {
		return fromuser;
	}

	public void setFromuser(TeePerson fromuser) {
		this.fromuser = fromuser;
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

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getSmsRemind() {
		return smsRemind;
	}

	public void setSmsRemind(String smsRemind) {
		this.smsRemind = smsRemind;
	}

	public String getImportant() {
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
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

	public String getCompressContent() {
		return compressContent;
	}

	public void setCompressContent(String compressContent) {
		this.compressContent = compressContent;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getCarbonCopy() {
		return carbonCopy;
	}

	public void setCarbonCopy(String carbonCopy) {
		this.carbonCopy = carbonCopy;
	}

	public String getBlindCarbonCopy() {
		return blindCarbonCopy;
	}

	public void setBlindCarbonCopy(String blindCarbonCopy) {
		this.blindCarbonCopy = blindCarbonCopy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
