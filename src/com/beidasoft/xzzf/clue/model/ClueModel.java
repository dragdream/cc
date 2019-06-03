package com.beidasoft.xzzf.clue.model;

import java.util.List;

import com.beidasoft.xzzf.clue.bean.ClueCodeDetail;
import com.beidasoft.xzzf.clue.bean.ClueInformer;
import com.beidasoft.xzzf.common.bean.Region;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.model.TeeDepartmentModel;

public class ClueModel {
     // 案件线索主键
     private String id; 

    // 线索关联执法办案唯一编号
    private String baseId;

    // 线索来源（代码表）
    private String clueSource;

    // 线索来源备注（手动填写）
    private String clueSourceRemark;

    // 举报形式（代码表）
    private String reportForm;

    // 其他省（代码表）
    private int anotherProvince;

    // 其他市（代码表）
    private int anotherCity;

    // 其他单位
    private String anotherEmployee;

    // 举报类别（代码表）
    private int reportType;

    // 来文标题
    private String documentTitle;

    // 来文编号
    private String documentCode;
    
    //文号
    private String docNums;

    // 来文日期
    private String documentDateStr;
    
    private String documentDateStrStart;
	
	private String documentDateStrEnd;

    // 举报日期
    private String reportDateStr;
    
    private String reportDateStrStart;
	
	private String reportDateStrEnd;

    // 举报内容
    private String reportContent;

    // 举报材料
    private String reportMaterials;

    // 备注
    private String remarks;

    // 送审意见
    private String submitContent;

    // 处理意见
    private String dealContent;

    // 地区
    private String area;

    // 被举报人类型
    private int reportedType;

    // 要求奖励
    private int isReward;

    // 要求回复
    private int isReply;

    // 被举报人姓名
    private String personalReportedName;

    // 被举报人个体工商户名
    private String personalReportedShopName;

    // 被举报人联系方式
    private String personalReportedTel;

    // 被举报人地址
    private String personalReportedAddress;

    // 被举报人邮箱
    private String personalReportedMail;

    // 被举报组织机构名称
    private String organReportedName;

    // 被举报组织机构代码
    private String organReportedCode;

    // 被举报组织地址
    private String organReportedAddress;

    // 被举报组织负责人类型
    private int organReportedPersonType;

    // 被举报组织负责人名
    private String organReportedPersonName;

    // 被举报组织法人联系方式
    private String organReportedPersonTel;

    // 是否多次被举报
    private int isReporteds;

    // 是否受理
    private int isAccept;

    // 受理人Id
    private int acceptUserId;

    // 受理人名
    private String acceptUserName;

    // 不受理原因
    private String denialReason;

    // 办理单位
    private int dealDepart;

    // 办理时间
    private int dealTime;

    // 办理时间备注（手动输入）
    private int dealTimeRemark;

    // 是否通过确认（1.直接发送，2.提交审批）
    private int isAdmit;

    // 创建人ID
    private int createUserId;

    // 创建人名
    private String createUserName;

    // 创建时间
    private String createTimeStr;
    
    private String createTimeStrStart;
	
	private String createTimeStrEnd;

    // 修改日期
    private String updateTimeStr;

    // 当前状态
    private int staus;
    
    //
    private String leaderContent;
    
    private String leaderTimeStr;
    
    private String acceptTimeStr;

    //界面传回的最新的领导意见
	private String newContent;
	
    //举报人信息字符串
    private String informerArray; 
	
    //联系人信息字符串
	private String contactArray;
	
	//用于显示已办理和未办理列表的值
	private String isWait;
	
	private int flowRunId;

	//举报人+联系人信息集合
	private List<ClueInformer> clueInformer;
	
	//下拉列表举报形式
	private List<ClueCodeDetail> formList;
	
	//下拉列表办理单位
	private List<TeeDepartmentModel> departList;
	
	//下拉列表举报来源
	private List<ClueCodeDetail> sourceList;
	
	//下拉列表举报类型
	private List<ClueCodeDetail> typeList;
	
	//下拉列表其他省
	private List<Region> provinceList;
	
	//下拉列表其他市
	private List<Region> cityList;
	
	//证据List
	List<TeeAttachmentModel> attachModel; 
	
	//领导意见List
	private List<ClueLeaderOpinionModel> opinionList;

	public String getId() {
		return id;
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

	public String getClueSource() {
		return clueSource;
	}

	public void setClueSource(String clueSource) {
		this.clueSource = clueSource;
	}

	public String getClueSourceRemark() {
		return clueSourceRemark;
	}

	public void setClueSourceRemark(String clueSourceRemark) {
		this.clueSourceRemark = clueSourceRemark;
	}

	public String getReportForm() {
		return reportForm;
	}

	public void setReportForm(String reportForm) {
		this.reportForm = reportForm;
	}

	public int getAnotherProvince() {
		return anotherProvince;
	}

	public void setAnotherProvince(int anotherProvince) {
		this.anotherProvince = anotherProvince;
	}

	public int getAnotherCity() {
		return anotherCity;
	}

	public void setAnotherCity(int anotherCity) {
		this.anotherCity = anotherCity;
	}

	public String getAnotherEmployee() {
		return anotherEmployee;
	}

	public void setAnotherEmployee(String anotherEmployee) {
		this.anotherEmployee = anotherEmployee;
	}

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getDocNums() {
		return docNums;
	}

	public void setDocNums(String docNums) {
		this.docNums = docNums;
	}

	public String getDocumentDateStr() {
		return documentDateStr;
	}

	public void setDocumentDateStr(String documentDateStr) {
		this.documentDateStr = documentDateStr;
	}

	public String getDocumentDateStrStart() {
		return documentDateStrStart;
	}

	public void setDocumentDateStrStart(String documentDateStrStart) {
		this.documentDateStrStart = documentDateStrStart;
	}

	public String getDocumentDateStrEnd() {
		return documentDateStrEnd;
	}

	public void setDocumentDateStrEnd(String documentDateStrEnd) {
		this.documentDateStrEnd = documentDateStrEnd;
	}

	public String getReportDateStr() {
		return reportDateStr;
	}

	public void setReportDateStr(String reportDateStr) {
		this.reportDateStr = reportDateStr;
	}

	public String getReportDateStrStart() {
		return reportDateStrStart;
	}

	public void setReportDateStrStart(String reportDateStrStart) {
		this.reportDateStrStart = reportDateStrStart;
	}

	public String getReportDateStrEnd() {
		return reportDateStrEnd;
	}

	public void setReportDateStrEnd(String reportDateStrEnd) {
		this.reportDateStrEnd = reportDateStrEnd;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}

	public String getReportMaterials() {
		return reportMaterials;
	}

	public void setReportMaterials(String reportMaterials) {
		this.reportMaterials = reportMaterials;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSubmitContent() {
		return submitContent;
	}

	public void setSubmitContent(String submitContent) {
		this.submitContent = submitContent;
	}

	public String getDealContent() {
		return dealContent;
	}

	public void setDealContent(String dealContent) {
		this.dealContent = dealContent;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getReportedType() {
		return reportedType;
	}

	public void setReportedType(int reportedType) {
		this.reportedType = reportedType;
	}

	public int getIsReward() {
		return isReward;
	}

	public void setIsReward(int isReward) {
		this.isReward = isReward;
	}

	public int getIsReply() {
		return isReply;
	}

	public void setIsReply(int isReply) {
		this.isReply = isReply;
	}

	public String getPersonalReportedName() {
		return personalReportedName;
	}

	public void setPersonalReportedName(String personalReportedName) {
		this.personalReportedName = personalReportedName;
	}

	public String getPersonalReportedShopName() {
		return personalReportedShopName;
	}

	public void setPersonalReportedShopName(String personalReportedShopName) {
		this.personalReportedShopName = personalReportedShopName;
	}

	public String getPersonalReportedTel() {
		return personalReportedTel;
	}

	public void setPersonalReportedTel(String personalReportedTel) {
		this.personalReportedTel = personalReportedTel;
	}

	public String getPersonalReportedAddress() {
		return personalReportedAddress;
	}

	public void setPersonalReportedAddress(String personalReportedAddress) {
		this.personalReportedAddress = personalReportedAddress;
	}

	public String getPersonalReportedMail() {
		return personalReportedMail;
	}

	public void setPersonalReportedMail(String personalReportedMail) {
		this.personalReportedMail = personalReportedMail;
	}

	public String getOrganReportedName() {
		return organReportedName;
	}

	public void setOrganReportedName(String organReportedName) {
		this.organReportedName = organReportedName;
	}

	public String getOrganReportedCode() {
		return organReportedCode;
	}

	public void setOrganReportedCode(String organReportedCode) {
		this.organReportedCode = organReportedCode;
	}

	public String getOrganReportedAddress() {
		return organReportedAddress;
	}

	public void setOrganReportedAddress(String organReportedAddress) {
		this.organReportedAddress = organReportedAddress;
	}

	public int getOrganReportedPersonType() {
		return organReportedPersonType;
	}

	public void setOrganReportedPersonType(int organReportedPersonType) {
		this.organReportedPersonType = organReportedPersonType;
	}

	public String getOrganReportedPersonName() {
		return organReportedPersonName;
	}

	public void setOrganReportedPersonName(String organReportedPersonName) {
		this.organReportedPersonName = organReportedPersonName;
	}

	public String getOrganReportedPersonTel() {
		return organReportedPersonTel;
	}

	public void setOrganReportedPersonTel(String organReportedPersonTel) {
		this.organReportedPersonTel = organReportedPersonTel;
	}

	public int getIsReporteds() {
		return isReporteds;
	}

	public void setIsReporteds(int isReporteds) {
		this.isReporteds = isReporteds;
	}

	public int getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(int isAccept) {
		this.isAccept = isAccept;
	}

	public int getAcceptUserId() {
		return acceptUserId;
	}

	public void setAcceptUserId(int acceptUserId) {
		this.acceptUserId = acceptUserId;
	}

	public String getAcceptUserName() {
		return acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String getDenialReason() {
		return denialReason;
	}

	public void setDenialReason(String denialReason) {
		this.denialReason = denialReason;
	}

	public int getDealDepart() {
		return dealDepart;
	}

	public void setDealDepart(int dealDepart) {
		this.dealDepart = dealDepart;
	}

	public int getDealTime() {
		return dealTime;
	}

	public void setDealTime(int dealTime) {
		this.dealTime = dealTime;
	}

	public int getDealTimeRemark() {
		return dealTimeRemark;
	}

	public void setDealTimeRemark(int dealTimeRemark) {
		this.dealTimeRemark = dealTimeRemark;
	}

	public int getIsAdmit() {
		return isAdmit;
	}

	public void setIsAdmit(int isAdmit) {
		this.isAdmit = isAdmit;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getCreateTimeStrStart() {
		return createTimeStrStart;
	}

	public void setCreateTimeStrStart(String createTimeStrStart) {
		this.createTimeStrStart = createTimeStrStart;
	}

	public String getCreateTimeStrEnd() {
		return createTimeStrEnd;
	}

	public void setCreateTimeStrEnd(String createTimeStrEnd) {
		this.createTimeStrEnd = createTimeStrEnd;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public int getStaus() {
		return staus;
	}

	public void setStaus(int staus) {
		this.staus = staus;
	}

	public String getNewContent() {
		return newContent;
	}

	public void setNewContent(String newContent) {
		this.newContent = newContent;
	}

	public String getInformerArray() {
		return informerArray;
	}

	public void setInformerArray(String informerArray) {
		this.informerArray = informerArray;
	}

	public String getContactArray() {
		return contactArray;
	}

	public void setContactArray(String contactArray) {
		this.contactArray = contactArray;
	}

	public String getIsWait() {
		return isWait;
	}

	public void setIsWait(String isWait) {
		this.isWait = isWait;
	}

	public int getFlowRunId() {
		return flowRunId;
	}

	public void setFlowRunId(int flowRunId) {
		this.flowRunId = flowRunId;
	}

	public List<ClueInformer> getClueInformer() {
		return clueInformer;
	}

	public void setClueInformer(List<ClueInformer> clueInformer) {
		this.clueInformer = clueInformer;
	}

	public List<ClueCodeDetail> getFormList() {
		return formList;
	}

	public void setFormList(List<ClueCodeDetail> formList) {
		this.formList = formList;
	}

	public List<TeeDepartmentModel> getDepartList() {
		return departList;
	}

	public void setDepartList(List<TeeDepartmentModel> departList) {
		this.departList = departList;
	}

	public List<ClueCodeDetail> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<ClueCodeDetail> sourceList) {
		this.sourceList = sourceList;
	}

	public List<ClueCodeDetail> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<ClueCodeDetail> typeList) {
		this.typeList = typeList;
	}

	public List<Region> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Region> provinceList) {
		this.provinceList = provinceList;
	}

	public List<Region> getCityList() {
		return cityList;
	}

	public void setCityList(List<Region> cityList) {
		this.cityList = cityList;
	}

	public List<TeeAttachmentModel> getAttachModel() {
		return attachModel;
	}

	public void setAttachModel(List<TeeAttachmentModel> attachModel) {
		this.attachModel = attachModel;
	}

	public List<ClueLeaderOpinionModel> getOpinionList() {
		return opinionList;
	}

	public void setOpinionList(List<ClueLeaderOpinionModel> opinionList) {
		this.opinionList = opinionList;
	}

	public String getLeaderContent() {
		return leaderContent;
	}

	public void setLeaderContent(String leaderContent) {
		this.leaderContent = leaderContent;
	}

	public String getLeaderTimeStr() {
		return leaderTimeStr;
	}

	public void setLeaderTimeStr(String leaderTimeStr) {
		this.leaderTimeStr = leaderTimeStr;
	}

	public String getAcceptTimeStr() {
		return acceptTimeStr;
	}

	public void setAcceptTimeStr(String acceptTimeStr) {
		this.acceptTimeStr = acceptTimeStr;
	}

}
