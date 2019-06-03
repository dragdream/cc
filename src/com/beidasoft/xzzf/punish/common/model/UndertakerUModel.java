package com.beidasoft.xzzf.punish.common.model;

import java.util.Date;

public class UndertakerUModel {

	// 变更记录主键
	private String changeId;

	// 案件唯一标识
	private String baseId;

	// 原主办人ID
	private String oldMajorId;

	// 原主办人
	private String oldMajorName;
	
	// 新主办人ID
	private String newMajorId;

	// 新主办人
	private String newMajorName;

	// 原协办人ID
	private String oldMinorId;

	// 原协办人
	private String oldMinorName;

	// 新协办人ID
	private String newMinorId;
	
	// 新协办人
	private String newMinorName;
	
	// 变更时间
	private Date changeDateStr;

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

	public Date getChangeDateStr() {
		return changeDateStr;
	}

	public void setChangeDateStr(Date changeDateStr) {
		this.changeDateStr = changeDateStr;
	}
	
}