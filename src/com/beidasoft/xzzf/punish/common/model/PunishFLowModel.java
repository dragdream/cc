package com.beidasoft.xzzf.punish.common.model;


public class PunishFLowModel {

	private String id;				//案件办理对应流程ID

	private String punishTacheId;				//案件办理环节ID
	
	private String tacheId;                     // 案件环节ID

	private String baseId;						//案件唯一标识

	private int confFlowId;						//案件调用系统定义流程ID

	private String confFlowName;				//案件调用系统定义流程名称

	private int punishRunId;					//办案执行流程ID

	private int punishFrpSid;					//办案执行下一步ID

	private int punishPrcsId;					//办案执行流程步骤ID

	private String punishPrcsName;				//办案执行步骤名称

	private String punishDocId;					//办案生产对应文书
	
	private String createtimeStr;					//创建时间
	
    private int majorId;						// 主办人ID
    
    private String majorName;					// 主办人名称
   
    private String majorNo;					 // 主办人执法证号
    
    private int minorId;					// 协办人ID
    
    private String minorName;				// 协办人名称
    
    private String minorNo;					// 协办人执法证号
	 
    private String contentsCode;			 //目录序号
   
    private String contentsNumber;  		 //目录文号
    
    private String contentsResponer;		//目录责任者
    
    private String contentsDate;			//目录日期
    
    private String contentsPages;			//目录页次
   
    private String contentsFilepath;		 //目录文件路径
    
    private String contentsRemark;			//目录备注
    
    private String sendDocsId;				//送达文书主键id 组合

	public String getPunishTacheId() {
		return punishTacheId;
	}

	public void setPunishTacheId(String punishTacheId) {
		this.punishTacheId = punishTacheId;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTacheId() {
		return tacheId;
	}

	public void setTacheId(String tacheId) {
		this.tacheId = tacheId;
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

	public String getCreatetimeStr() {
		return createtimeStr;
	}

	public void setCreatetimeStr(String createtimeStr) {
		this.createtimeStr = createtimeStr;
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

	public String getSendDocsId() {
		return sendDocsId;
	}

	public void setSendDocsId(String sendDocsId) {
		this.sendDocsId = sendDocsId;
	}

}
