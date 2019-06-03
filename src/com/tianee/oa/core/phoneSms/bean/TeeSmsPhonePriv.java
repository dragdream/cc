package com.tianee.oa.core.phoneSms.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SMS_PHONE_PRIV")
public class TeeSmsPhonePriv{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="SMS_PHONE_PRIV_seq_gen")
	@SequenceGenerator(name="SMS_PHONE_PRIV_seq_gen", sequenceName="SMS_PHONE_PRIV_seq")
	@Column(name = "SID")
	private int sid;//SID	INT	自增	

	@Column(name="TYPE_PRIV")
	private String typePriv;//TYPE_PRIV	text	模块权限	指定允许使用手机短信提醒的模块  逗号分隔的短信MODULE_SORT._KEY（内部短消息类型）

	@Column(name="REMIND_PRIV")
	private String remindPriv;//REMIND_PRIV	text	被提醒权限	指定的用户可以接收到手机短信提醒，例如来自工作流的手机短信提醒

	//逗号分隔的USER_ID串

	//注：用户发送“提醒的手机短信”（分散在各个模块）时，需要检查一下这个字段，看接收人的ID是否在这个串里。手机短信发送的方法需要做一下处理。

	@Column(name="OUT_PRIV")
	private String outPriv;//OUT_PRIV	text	外发权限	逗号分隔的USER_ID串  指定的用户可以向OA系统外的人发手机短信

	@Column(name="SMS_REMIND_PRIV")
	private String smsRemindPriv;//SMS_REMIND_PRIV	text	提醒权限	指定的用户，可以使用手机短信提醒其他用户

	//逗号分隔的USER_ID串

	//注：这个设置相当于对“模块权限”和“短信提醒设置”中的“手机短信默认提醒”的一个细化，即显示“手机短信提醒”checkbox时，光符合前面两个条件还不够，还要看这个串中是否有当前用户的ID

	@Column(name="OUT_TO_SELF",columnDefinition="char(1)")
	private char outToSelf;//OUT_TO_SELF	char(1)	是否允许给自己发送手机短信	0-不允许 1-允许

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTypePriv() {
		return typePriv;
	}

	public void setTypePriv(String typePriv) {
		this.typePriv = typePriv;
	}

	public String getRemindPriv() {
		return remindPriv;
	}

	public void setRemindPriv(String remindPriv) {
		this.remindPriv = remindPriv;
	}

	public String getOutPriv() {
		return outPriv;
	}

	public void setOutPriv(String outPriv) {
		this.outPriv = outPriv;
	}

	public String getSmsRemindPriv() {
		return smsRemindPriv;
	}

	public void setSmsRemindPriv(String smsRemindPriv) {
		this.smsRemindPriv = smsRemindPriv;
	}

	public char getOutToSelf() {
		return outToSelf;
	}

	public void setOutToSelf(char outToSelf) {
		this.outToSelf = outToSelf;
	}
	
}
