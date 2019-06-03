package com.tianee.oa.core.base.email.model;

import com.tianee.oa.core.org.bean.TeePerson;


public class TeeWebEmailModel{
	
	private int sid;//自增id
	private int userId;//设置人id
	private String userName;//设置人名称
	private int isDefault=0;//是否是默认邮箱
	private int recvDel=0;//接收后是否删除外部邮箱的邮件
	private String popServer;//pop服务器地址
	private String smtpServer;//smtp服务器地址
	private String loginType;//登录类型
    private String smtpPass;//smtp
	private String emailUser;//登录名
	private String emailPass;//密码
	private String pop3Port;//pop3端口号
	private String smtpPort;//smtp端口号
	private String pop3Ssl;//pop3ssl
	private String smtpSsl;//smtpssl
	private String quotaLimit;//定额限制
	private String emailUid;//emailuid
	private String checkFlag;//是否已检查
	private String priority;//优先权
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
