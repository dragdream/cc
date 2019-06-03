package com.tianee.oa.core.base.email.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name = "WEB_MAIL")
public class TeeWebMail implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="WEB_MAIL_seq_gen")
	@SequenceGenerator(name="WEB_MAIL_seq_gen", sequenceName="WEB_MAIL_seq")
	private int sid;//自增id
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX91ba62e74b1544d3be397ec8361")
	@JoinColumn(name = "USER_ID")
	private TeePerson user;//设置人
	
	@Column(name = "IS_DEFAULT")
	@Index(name="TeeWebMail_isDefault")
	private int isDefault=0;//是否是默认邮箱
	
	@Column(name = "RECV_DEL")
	@Index(name="TeeWebMail_recvDel")
	private int recvDel=0;//接收后是否删除外部邮箱的邮件
	
	@Column(name = "POP_SERVER")
	private String popServer;//pop服务器地址
	
	@Column(name = "SMTP_SERVER")
	private String smtpServer;//smtp服务器地址
	@Column(name = "LOGIN_TYPE")
	private String loginType;//登录类型
	@Column(name = "SMTP_PASS")
    private String smtpPass;//smtp
	@Column(name = "EMAIL_USER")
	private String emailUser;//登录名
	@Column(name = "EMAIL_PASS")
	private String emailPass;//密码
	@Column(name = "POP3_PORT")
	private String pop3Port;//pop3端口号
	@Column(name = "SMTP_PORT")
	private String smtpPort;//smtp端口号
	@Column(name = "POP3_SSL")
	private String pop3Ssl;//pop3ssl
	@Column(name = "SMTP_SSL")
	private String smtpSsl;//smtpssl
	@Column(name = "QUATAL_LIMIT")
	private String quotaLimit;//定额限制
	@Column(name = "EMAIL_UID")
	private String emailUid;//emailuid
	@Column(name = "CHECK_FLAG")
	private String checkFlag;//是否已检查
	@Column(name = "PRIORITY")
	private String priority;//优先权
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public TeePerson getUser() {
		return user;
	}
	public void setUser(TeePerson user) {
		this.user = user;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	public int getRecvDel() {
		return recvDel;
	}
	public void setRecvDel(int recvDel) {
		this.recvDel = recvDel;
	}
	public String getPopServer() {
		return popServer;
	}
	public void setPopServer(String popServer) {
		this.popServer = popServer;
	}
	public String getSmtpServer() {
		return smtpServer;
	}
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getSmtpPass() {
		return smtpPass;
	}
	public void setSmtpPass(String smtpPass) {
		this.smtpPass = smtpPass;
	}
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	public String getEmailPass() {
		return emailPass;
	}
	public void setEmailPass(String emailPass) {
		this.emailPass = emailPass;
	}
	public String getPop3Port() {
		return pop3Port;
	}
	public void setPop3Port(String pop3Port) {
		this.pop3Port = pop3Port;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}
	public String getPop3Ssl() {
		return pop3Ssl;
	}
	public void setPop3Ssl(String pop3Ssl) {
		this.pop3Ssl = pop3Ssl;
	}
	public String getSmtpSsl() {
		return smtpSsl;
	}
	public void setSmtpSsl(String smtpSsl) {
		this.smtpSsl = smtpSsl;
	}
	public String getQuotaLimit() {
		return quotaLimit;
	}
	public void setQuotaLimit(String quotaLimit) {
		this.quotaLimit = quotaLimit;
	}
	public String getEmailUid() {
		return emailUid;
	}
	public void setEmailUid(String emailUid) {
		this.emailUid = emailUid;
	}
	public String getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	
	
}
