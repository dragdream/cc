package com.tianee.oa.core.base.email.bean;
import java.io.Serializable;
import java.util.Date;

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
@Table(name = "MAIL")
public class TeeMail implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MAIL_seq_gen")
	@SequenceGenerator(name="MAIL_seq_gen", sequenceName="MAIL_seq")
	private int sid;//自增id
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX6e9b4958506d4a1d91d71dabc99")
	@JoinColumn(name = "TO_ID")
	private TeePerson toUser;//收件人
	
	@Column(name = "READ_FLAG")
	@Index(name="TeeMail_mailreadFlag")
	private int readFlag=0;//邮件读取标记 0:未读 1：已读
	
	
	@Column(name = "READ_TIME")
	private Date readTime;//第一次阅读的时间
	
	
	
	@Column(name = "DELETE_FLAG")
	@Index(name="TeeMail_maildeleteFlag")
	private int deleteFlag=0;//邮件删除标记 0：未删除 1：收件人删除   2：彻底删除
	
	@ManyToOne
	@Index(name="IDXf96db93ecd694fbd9b66a4dc425")
	@JoinColumn(name = "BOX_ID")
	private TeeMailBox mailBox;//邮件箱ID（分类）0-系统内置邮件夹 。其他-用户自定义文件夹（对应email_box表的BOX_ID字段）
	
	@ManyToOne
	@Index(name="IDX3909bffd83564abca34e9d9fe26")
	@JoinColumn(name = "BODY_ID")
	private TeeMailBody mailBody;//邮件体ID 邮件体只有一份，即便是群发也是如此
	
	@Column(name = "RECEIPT")
	@Index(name="TeeMail_mailreceipt")
	private int receipt;//是否请求阅读收条 0-不请求  1-请求  如果是1，则收件人收到邮件之后，系统会自动给发件人发一个短消息（收条）
	
	@Column(name = "RECEIVE_TYPE")
	@Index(name="TeeMail_mailreceiveType")
	private int receiveType;//接受类型 0-收件人  1-被抄送人 2-被密送人 


	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public TeePerson getToUser() {
		return toUser;
	}
	public void setToUser(TeePerson toUser) {
		this.toUser = toUser;
	}
	public TeeMailBox getMailBox() {
		return mailBox;
	}
	public void setMailBox(TeeMailBox mailBox) {
		this.mailBox = mailBox;
	}
	public TeeMailBody getMailBody() {
		return mailBody;
	}
	public void setMailBody(TeeMailBody mailBody) {
		this.mailBody = mailBody;
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
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

}
