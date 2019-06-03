package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "SMS_BODY")
public class TeeSmsBody {
	
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="from_id")
	private int fromId;//发送人员uuid，不用对象型
	
	@Column(name="module_No")
	private String moduleNo;//短信所属模块码
	
	@Column(name="content",length=4000)
	private String content;//短信内容
	
	@Column(name="send_time")
	private Calendar sendTime;//发送时间
	
	@Column(name="DEL_FLAG")
	private int delFlag;//删除标记
	
	/**
	 * 0：未发送
	 * 1：已发送
	 */
	@Column(name="send_flag")
	private int sendFlag;//发送标记
	
	@Column(name="remind_url",length=500)
	private String remindUrl;//短信提示框后面“查看详情”所对应那个的URL地址
	
	@Column(name="remind_url1",length=500)
	private String remindUrl1;//手机端事务提醒
	
	public String getRemindUrl1() {
		return remindUrl1;
	}

	public void setRemindUrl1(String remindUrl1) {
		this.remindUrl1 = remindUrl1;
	}

	@OneToMany(fetch=FetchType.LAZY,orphanRemoval=true,cascade={CascadeType.ALL},mappedBy="smsBody")
	private List<TeeSms> smsList = new ArrayList(0);

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
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

	public String getRemindUrl() {
		return remindUrl;
	}

	public void setRemindUrl(String remindUrl) {
		this.remindUrl = remindUrl;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	public String getModuleNo() {
		return moduleNo;
	}

	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}

	public int getSendFlag() {
		return sendFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public List<TeeSms> getSmsList() {
		return smsList;
	}

	public void setSmsList(List<TeeSms> smsList) {
		this.smsList = smsList;
	}
	
}
