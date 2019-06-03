package com.beidasoft.xzzf.punish.common.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_UNDERTAKER_U")
public class UndertakerU {

	// 变更记录主键
	@Id
	@Column(name = "CHANGE_ID")
	private String changeId;

	// 案件唯一标识
	@Column(name = "BASE_ID")
	private String baseId;

	// 原主办人ID
	@Column(name = "OLD_MAJOR_ID")
	private String oldMajorId;

	// 原主办人
	@Column(name = "OLD_MAJOR_NAME")
	private String oldMajorName;
	
	// 新主办人ID
	@Column(name = "NEW_MAJOR_ID")
	private String newMajorId;

	// 新主办人
	@Column(name = "NEW_MAJOR_NAME")
	private String newMajorName;

	// 原协办人ID
	@Column(name = "OLD_MINOR_ID")
	private String oldMinorId;

	// 原协办人
	@Column(name = "OLD_MINOR_NAME")
	private String oldMinorName;

	// 新协办人ID
	@Column(name = "NEW_MINOR_ID")
	private String newMinorId;
	
	// 新协办人
	@Column(name = "NEW_MINOR_NAME")
	private String newMinorName;
	
	// 变更时间
	@Column(name = "CHANGE_DATE")
	private Date changeDate;

	public String getChangeId() {
		return changeId;
	}

	public void setChangeId(String changeId) {
		this.changeId = changeId;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getOldMajorId() {
		return oldMajorId;
	}

	public void setOldMajorId(String oldMajorId) {
		this.oldMajorId = oldMajorId;
	}

	public String getOldMajorName() {
		return oldMajorName;
	}

	public void setOldMajorName(String oldMajorName) {
		this.oldMajorName = oldMajorName;
	}

	public String getNewMajorId() {
		return newMajorId;
	}

	public void setNewMajorId(String newMajorId) {
		this.newMajorId = newMajorId;
	}

	public String getNewMajorName() {
		return newMajorName;
	}

	public void setNewMajorName(String newMajorName) {
		this.newMajorName = newMajorName;
	}

	public String getOldMinorId() {
		return oldMinorId;
	}

	public void setOldMinorId(String oldMinorId) {
		this.oldMinorId = oldMinorId;
	}

	public String getOldMinorName() {
		return oldMinorName;
	}

	public void setOldMinorName(String oldMinorName) {
		this.oldMinorName = oldMinorName;
	}

	public String getNewMinorId() {
		return newMinorId;
	}

	public void setNewMinorId(String newMinorId) {
		this.newMinorId = newMinorId;
	}

	public String getNewMinorName() {
		return newMinorName;
	}

	public void setNewMinorName(String newMinorName) {
		this.newMinorName = newMinorName;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
}