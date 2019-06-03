package com.tianee.oa.core.general.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeSmsModel  extends TeeBaseModel{
	private String smsSid;//smsSid
	private String smsBodySid;//对应的bodySid
	private int toId;//接收人员uuid，不用对象型
	private String toUser;
	private String toUserIds;//接收人员串
	private String toUsers;//接收人员串
	private int remindFlag;//提醒状态
	private String remindFlagDesc;
	private int deleteFlag;//删除状态
	private String deleteFlagDesc;//删除状态
	private Date remindTime;//最近一次提醒时间
	private String remindTimeDesc;
	private int fromId;//发送人员uuid，不用对象型
	private String fromUser;
	private String moduleNo;//短信所属模块码
	private String moduleNoDesc;//短信所属模块码描述
	private String content;//短信内容
	private Calendar sendTime;//发送时间
	private String sendTimeDesc;
	private int delFlag;//body删除标记
	private int sendFlag;//发送标记
	private String remindUrl;//短信提示框后面“查看详情”所对应那个的URL地址
	private String remindUrl1;//手机端查看详情
	
	
	public String getSmsSid() {
		return smsSid;
	}
	public void setSmsSid(String smsSid) {
		this.smsSid = smsSid;
	}
	public String getSmsBodySid() {
		return smsBodySid;
	}
	public void setSmsBodySid(String smsBodySid) {
		this.smsBodySid = smsBodySid;
	}
	public int getToId() {
		return toId;
	}
	public void setToId(int toId) {
		this.toId = toId;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public int getRemindFlag() {
		return remindFlag;
	}
	public void setRemindFlag(int remindFlag) {
		this.remindFlag = remindFlag;
	}
	public String getRemindFlagDesc() {
		return remindFlagDesc;
	}
	public void setRemindFlagDesc(String remindFlagDesc) {
		this.remindFlagDesc = remindFlagDesc;
	}
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getDeleteFlagDesc() {
		return deleteFlagDesc;
	}
	public void setDeleteFlagDesc(String deleteFlagDesc) {
		this.deleteFlagDesc = deleteFlagDesc;
	}
	public Date getRemindTime() {
		return remindTime;
	}
	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}
	public String getRemindTimeDesc() {
		return remindTimeDesc;
	}
	public void setRemindTimeDesc(String remindTimeDesc) {
		this.remindTimeDesc = remindTimeDesc;
	}
	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getModuleNo() {
		return moduleNo;
	}
	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
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
	public String getSendTimeDesc() {
		return sendTimeDesc;
	}
	public void setSendTimeDesc(String sendTimeDesc) {
		this.sendTimeDesc = sendTimeDesc;
	}
	public int getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}
	public int getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getRemindUrl() {
		return remindUrl;
	}
	public void setRemindUrl(String remindUrl) {
		this.remindUrl = remindUrl;
	}
	public void setToUserIds(String toUserIds) {
		this.toUserIds = toUserIds;
	}
	public String getToUserIds() {
		return toUserIds;
	}
	public void setToUsers(String toUsers) {
		this.toUsers = toUsers;
	}
	public String getToUsers() {
		return toUsers;
	}
	public void setModuleNoDesc(String moduleNoDesc) {
		this.moduleNoDesc = moduleNoDesc;
	}
	public String getModuleNoDesc() {
		return moduleNoDesc;
	}
	public String getRemindUrl1() {
		return remindUrl1;
	}
	public void setRemindUrl1(String remindUrl1) {
		this.remindUrl1 = remindUrl1;
	}
	
}
