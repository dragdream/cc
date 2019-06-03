package com.tianee.oa.core.phoneSms.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SMS_SEND_PHONE")
public class TeeSmsSendPhone{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SMS_SEND_PHONE_seq_gen")
	@SequenceGenerator(name="SMS_SEND_PHONE_seq_gen", sequenceName="SMS_SEND_PHONE_seq")
	@Column(name = "SID")
	private int sid;//SID	int(11)	自增字段	auto_increment
	
	@Index(name="SMS_SEND_PHONE_FROM_ID_INDEX")
	@Column(name="FROM_ID")
	private int fromId;//FROM_ID  varchar(200)	发送人UUID
	
	@Column(name="FROM_NAME")
	private String fromName;//FROM_NAME  varchar(200)	发送人姓名
	
	@Column(name="TO_ID")
	private int toId;//FROM_ID  varchar(200)	接收人uuid	
	
	@Column(name="PHONE")
	private String phone;//PHONE	varchar(20)	接收人手机号	
	
	@Column(name="TO_NAME")
	private String toName;//TO_NAME  varchar(200)	接收人姓名
	
	@Lob
	@Column(name="CONTENT")
	private String content;//CONTENT	text	短信内容	
	
	@Column(name="SEND_TIME")
	private Calendar sendTime;//SEND_TIME	datetime	发送时间	
	
	@Column(name="SEND_FLAG")
	private int sendFlag;//SEND_FLAG	char(1)	短信发送状态	0-未发送1-发送成功2-发送超时，请人工确认3-发送中...
	/*代码值的特别说明：
	
	 0：用户点击“发送”，向这个表插入一条记录，SEND_FLAG字段的初始值是0
	
	 1：发送成功后，后台服务会将这个字段置为1
	
	 2：短信猫返回的状态是发送失败，后台服务将SEND_FLAG置为2，但实际上有些已经发送成功，所以显示在界面上的文字是“发送超时，请人工确认”
	
	 3：从用户点击“发送”按钮到短信猫将短信发出，大概有五六秒的时间，短信猫开始发送短信时，后台服务将SEND_FLAG置为3，发送结束后根据返回的发送状态再将这个字段置为1或者2*/
	@Column(name="SEND_NUMBER")
	private int sendNumber=0;//发送次数
	
	
	public int getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(int sendNumber) {
		this.sendNumber = sendNumber;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getSendTime() {
		return sendTime;
	}

	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}

	public int getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}

	
}
