package com.beidasoft.xzzf.punish.common.bean;

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

@Entity
@Table(name="ZF_PUNISH_TACHE")
public class PunishTache {
	
	// 案件办理环节ID
    @Id
    @Column(name = "PUNISH_TACHE_ID")
    private String id;

    // 系统配置环节CODE
    @Column(name = "CONF_TACHE_CODE")
    private String confTacheCode;

    // 案件唯一标识
    @Column(name = "BASE_ID")
    private String baseId;

    // 环节开始日期
    @Column(name = "START_DATE")
    private Date startDate;

    // 环节结束日期
    @Column(name = "END_DATE")
    private Date endDate;

    // 排序
    @Column(name = "CONF_TACHE_INDEX")
    private int confTacheIndex;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="BASE_CODE")
//	@Index(name="ZF_PUNISH_TACHE_PKEY")
//	private PunishBase punishBase;
	
//	@OneToMany(mappedBy="punishTache", fetch=FetchType.LAZY)
//	private List<PunishFLow>  punishFlows =new ArrayList<PunishFLow>(0);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getConfTacheIndex() {
		return confTacheIndex;
	}

	public void setConfTacheIndex(int confTacheIndex) {
		this.confTacheIndex = confTacheIndex;
	}

//	public PunishBase getPunishBase() {
//		return punishBase;
//	}
//
//	public void setPunishBase(PunishBase punishBase) {
//		this.punishBase = punishBase;
//	}

//	public List<PunishFLow> getPunishFlows() {
//		return punishFlows;
//	}
//
//	public void setPunishFlows(List<PunishFLow> punishFlows) {
//		this.punishFlows = punishFlows;
//	}

	
}
