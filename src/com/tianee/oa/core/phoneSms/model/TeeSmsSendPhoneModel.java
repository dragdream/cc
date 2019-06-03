package com.tianee.oa.core.phoneSms.model;

import java.util.Calendar;

/**
 * @author nieyi
 *
 */
public class TeeSmsSendPhoneModel{

	private int sid;//SID	int(11)	自增字段	auto_increment
	
	private String fromId;//FROM_ID  varchar(200)	发送人USER_ID	
	
	private String toId;//toId  varchar(200)	发送人uuid
	
	public String getToId() {
		return toId;
	}


	public void setToId(String toId) {
		this.toId = toId;
	}


	public String getToUserName() {
		return toUserName;
	}


	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}


	private String toUserName;//接收人名称
	
	private String phone;//PHONE	varchar(20)	接收人手机号	
	
	
	private String content;//CONTENT	text	短信内容	
	
	
	private String sendTimeDesc;//SEND_TIME	datetime	发送时间	
	
	
	private char sendFlag;//SEND_FLAG	char(1)	短信发送状态	0-未发送1-发送成功2-发送超时，请人工确认3-发送中...
	/*代码值的特别说明：
	
	 0：用户点击“发送”，向这个表插入一条记录，SEND_FLAG字段的初始值是0
	
	 1：发送成功后，后台服务会将这个字段置为1
	
	 2：短信猫返回的状态是发送失败，后台服务将SEND_FLAG置为2，但实际上有些已经发送成功，所以显示在界面上的文字是“发送超时，请人工确认”
	
	 3：从用户点击“发送”按钮到短信猫将短信发出，大概有五六秒的时间，短信猫开始发送短信时，后台服务将SEND_FLAG置为3，发送结束后根据返回的发送状态再将这个字段置为1或者2*/

	private String sendFlagDesc;
	
	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getFromId() {
		return fromId;
	}


	public void setFromId(String fromId) {
		this.fromId = fromId;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

	public String getSendTimeDesc() {
		return sendTimeDesc;
	}


	public void setSendTimeDesc(String sendTimeDesc) {
		this.sendTimeDesc = sendTimeDesc;
	}


	public char getSendFlag() {
		return sendFlag;
	}


	public void setSendFlag(char sendFlag) {
		this.sendFlag = sendFlag;
	}


	public String getSendFlagDesc() {
		return sendFlagDesc;
	}


	public void setSendFlagDesc(String sendFlagDesc) {
		this.sendFlagDesc = sendFlagDesc;
	}
	
}