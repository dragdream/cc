package com.tianee.oa.core.base.message.bean;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.webframe.dao.TeeBaseDao;

/**
 * @author -syl
 *
 */
@Entity
@Table(name = "MESSAGE_GROUP_MSG")
public class TeeMessageGroupMsg extends TeeBaseDao<TeeMessageGroup>{
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="TO_ID")
	@Index(name="MESSAGE_GROUP_MSG_TO_ID")
	private String toId;//收信人Id
	
	@Column(name="REMIND_FLAG")
	private int remindFlag;//提醒标记  0：未接受  1：已接受
	
	@Column(name="DELETE_FLAG")
	private int deleteFlag;
	
	@ManyToOne()
	@JoinColumn(name="BODY_ID")
	@Index(name="MESSAGE_GROUP_MSG_BODY_ID")
	private TeeMessageGroupMsgBody body;

	@Column(name="REC_TIME")
	private Calendar recTime;//接收时间
	
	@Column(name="GROUP_ID")
	private int groupId;//所属组ID

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public int getRemindFlag() {
		return remindFlag;
	}

	public void setRemindFlag(int remindFlag) {
		this.remindFlag = remindFlag;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Calendar getRecTime() {
		return recTime;
	}

	public void setRecTime(Calendar recTime) {
		this.recTime = recTime;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public TeeMessageGroupMsgBody getBody() {
		return body;
	}

	public void setBody(TeeMessageGroupMsgBody body) {
		this.body = body;
	}
	
}
