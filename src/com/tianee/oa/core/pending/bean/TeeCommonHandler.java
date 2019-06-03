package com.tianee.oa.core.pending.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="COMMON_HANDLER")
public class TeeCommonHandler {
	@Id
//	@GeneratedValue(generator = "system-uuid")  
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name="UUID")
	private String uuid; //主键
	
	@Column(name="PENDING_TITLE")
	private String pendingTitle;//待办名称
	
	@Column(name="PENDING_CONTENT")
	private String pendingContent;//待办提醒内容
	
	@Column(name="URL")
	private String url;//待办地址
	
	@Column(name="MODEL")
	private String model;//判断系统      HR人事  ZC装财   DJ党建 
	//XT协同   GW公文  SP审批  ZF执法
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REC_USER_ID")
	private TeePerson recUserId; //办理人
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SEND_USER_ID")
	private TeePerson sendUserId;//发布人
	
	@Column(name="STATE")
	private int state;//更新状态  0：待办   1：已办

	@Column(name="CREATETIME")
	private Calendar createTime ;//创建时间
	
	@Column(name="YI_URL")
	private String yiUrl;//已办地址

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPendingTitle() {
		return pendingTitle;
	}

	public void setPendingTitle(String pendingTitle) {
		this.pendingTitle = pendingTitle;
	}

	public String getPendingContent() {
		return pendingContent;
	}

	public void setPendingContent(String pendingContent) {
		this.pendingContent = pendingContent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public TeePerson getRecUserId() {
		return recUserId;
	}

	public void setRecUserId(TeePerson recUserId) {
		this.recUserId = recUserId;
	}

	public TeePerson getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(TeePerson sendUserId) {
		this.sendUserId = sendUserId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public String getYiUrl() {
		return yiUrl;
	}

	public void setYiUrl(String yiUrl) {
		this.yiUrl = yiUrl;
	}

	@Override
	public String toString() {
		return "TeeCommonHandler [uuid=" + uuid + ", pendingTitle="
				+ pendingTitle + ", pendingContent=" + pendingContent
				+ ", url=" + url + ", model=" + model + ", recUserId="
				+ recUserId + ", sendUserId=" + sendUserId + ", state=" + state
				+ "]";
	}
	
	
}
