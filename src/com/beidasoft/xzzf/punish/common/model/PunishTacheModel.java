package com.beidasoft.xzzf.punish.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;


public class PunishTacheModel {

	private String punishTacheId;				//案件办理环节ID

	
	private String confTacheCode;				//系统配置环节CODE


	private String baseId;						//案件唯一标识


	private String startDateStr;						//环节开始日期


	private String endDateStr;						//环节结束日期


	private int confTacheIndex;					//排序

	public String getPunishTacheId() {
		return punishTacheId;
	}

	public void setPunishTacheId(String punishTacheId) {
		this.punishTacheId = punishTacheId;
	}

	public String getConfTacheCode() {
		return confTacheCode;
	}

	public void setConfTacheCode(String confTacheCode) {
		this.confTacheCode = confTacheCode;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	
	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public int getConfTacheIndex() {
		return confTacheIndex;
	}

	public void setConfTacheIndex(int confTacheIndex) {
		this.confTacheIndex = confTacheIndex;
	}

	
	
}
