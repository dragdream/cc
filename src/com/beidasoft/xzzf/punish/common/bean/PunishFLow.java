package com.beidasoft.xzzf.punish.common.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_PUNISH_FLOW")
public class PunishFLow {

	// 文书流程ID
    @Id
    @Column(name = "ID")
    private String id;
    
    // 案件唯一标识
    @Column(name = "BASE_ID")
    private String baseId;
    
    // 案件办理环节ID
    @Column(name = "PUNISH_TACHE_ID")
    private String tacheId;

    // 案件调用系统定义流程ID
    @Column(name = "CONF_FLOW_ID")
    private int confFlowId;

    // 案件调用系统定义流程名称
    @Column(name = "CONF_FLOW_NAME")
    private String confFlowName;

    // 办案执行流程ID
    @Column(name = "PUNISH_RUN_ID")
    private int punishRunId;

    // 办案执行下一步ID
    @Column(name = "PUNISH_FRP_SID")
    private int punishFrpSid;

    // 办案执行流程步骤ID
    @Column(name = "PUNISH_PRCS_ID")
    private int punishPrcsId;

    // 办案执行步骤名称
    @Column(name = "PUNISH_PRCS_NAME")
    private String punishPrcsName;

    // 办案生成对应文书ID
    @Column(name = "PUNISH_DOC_ID")
    private String punishDocId;

    // 主办人ID
    @Column(name = "MAJOR_ID")
    private int majorId;

    // 主办人名称
    @Column(name = "MAJOR_NAME")
    private String majorName;

    // 主办人执法证号
    @Column(name = "MAJOR_NO")
    private String majorNo;

    // 协办人ID
    @Column(name = "MINOR_ID")
    private int minorId;

    // 协办人名称
    @Column(name = "MINOR_NAME")
    private String minorName;

    // 协办人执法证号
    @Column(name = "MINOR_NO")
    private String minorNo;
    
    //文书创建时间
    @Column(name = "CREATE_TIME")
    private Date time;
    
    //目录序号
    @Column(name = "CONTENTS_CODE")
    private String contentsCode;
    
    //目录文号
    @Column(name = "CONTENTS_NUMBER")
    private String contentsNumber;
    
    //目录责任者
    @Column(name = "CONTENTS_RESPONER")
    private String contentsResponer;
    
    //目录日期
    @Column(name = "CONTENTS_DATE")
    private String contentsDate;
    
    //目录页次
    @Column(name = "CONTENTS_PAGES")
    private String contentsPages;
    
    //目录文件路径
    @Column(name = "CONTENTS_FILEPATH")
    private String contentsFilepath;
    
    //目录备注
    @Column(name = "CONTENTS_REMARK")
    private String contentsRemark;
    
    //送达回证文书主键ID  组合（‘，’分隔）
    @Column(name = "SEND_DOCS_ID")
    private String sendDocsId;
    
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="BASE_ID")
//	@Index(name="ZF_PUNISH_FLOW_PKEY")
//	private PunishTache punishTache;


	public String getId() {
		return id;
	}

	public String getContentsCode() {
		return contentsCode;
	}

	public void setContentsCode(String contentsCode) {
		this.contentsCode = contentsCode;
	}


	public String getContentsNumber() {
		return contentsNumber;
	}

	public void setContentsNumber(String contentsNumber) {
		this.contentsNumber = contentsNumber;
	}

	public String getContentsResponer() {
		return contentsResponer;
	}

	public void setContentsResponer(String contentsResponer) {
		this.contentsResponer = contentsResponer;
	}

	public String getContentsDate() {
		return contentsDate;
	}

	public void setContentsDate(String contentsDate) {
		this.contentsDate = contentsDate;
	}

	public String getContentsPages() {
		return contentsPages;
	}

	public void setContentsPages(String contentsPages) {
		this.contentsPages = contentsPages;
	}

	public String getContentsFilepath() {
		return contentsFilepath;
	}

	public void setContentsFilepath(String contentsFilepath) {
		this.contentsFilepath = contentsFilepath;
	}

	public String getContentsRemark() {
		return contentsRemark;
	}

	public void setContentsRemark(String contentsRemark) {
		this.contentsRemark = contentsRemark;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	
	
	public String getTacheId() {
		return tacheId;
	}

	public void setTacheId(String tacheId) {
		this.tacheId = tacheId;
	}

	public int getConfFlowId() {
		return confFlowId;
	}

	public void setConfFlowId(int confFlowId) {
		this.confFlowId = confFlowId;
	}

	public String getConfFlowName() {
		return confFlowName;
	}

	public void setConfFlowName(String confFlowName) {
		this.confFlowName = confFlowName;
	}

	public int getPunishRunId() {
		return punishRunId;
	}

	public void setPunishRunId(int punishRunId) {
		this.punishRunId = punishRunId;
	}

	public int getPunishFrpSid() {
		return punishFrpSid;
	}

	public void setPunishFrpSid(int punishFrpSid) {
		this.punishFrpSid = punishFrpSid;
	}

	public int getPunishPrcsId() {
		return punishPrcsId;
	}

	public void setPunishPrcsId(int punishPrcsId) {
		this.punishPrcsId = punishPrcsId;
	}

	public String getPunishPrcsName() {
		return punishPrcsName;
	}

	public void setPunishPrcsName(String punishPrcsName) {
		this.punishPrcsName = punishPrcsName;
	}


	public String getPunishDocId() {
		return punishDocId;
	}

	public void setPunishDocId(String punishDocId) {
		this.punishDocId = punishDocId;
	}

	public int getMajorId() {
		return majorId;
	}

	public void setMajorId(int majorId) {
		this.majorId = majorId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getMajorNo() {
		return majorNo;
	}

	public void setMajorNo(String majorNo) {
		this.majorNo = majorNo;
	}

	public int getMinorId() {
		return minorId;
	}

	public void setMinorId(int minorId) {
		this.minorId = minorId;
	}

	public String getMinorName() {
		return minorName;
	}

	public void setMinorName(String minorName) {
		this.minorName = minorName;
	}

	public String getMinorNo() {
		return minorNo;
	}

	public void setMinorNo(String minorNo) {
		this.minorNo = minorNo;
	}

	public String getSendDocsId() {
		return sendDocsId;
	}

	public void setSendDocsId(String sendDocsId) {
		this.sendDocsId = sendDocsId;
	}

//	public PunishTache getPunishTache() {
//		return punishTache;
//	}
//
//	public void setPunishTache(PunishTache punishTache) {
//		this.punishTache = punishTache;
//	}

	
	

}
