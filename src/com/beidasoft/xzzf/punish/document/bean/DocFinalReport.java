package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 结案报告表实体类
 */
@Entity
@Table(name="ZF_DOC_FINAL_REPORT")
public class DocFinalReport {
    // 结案报告表主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 文书区字
    @Column(name = "DOC_AREA")
    private String docArea;

    // 文书年度
    @Column(name = "DOC_YEAR")
    private String docYear;

    // 文书序号
    @Column(name = "DOC_NUM")
    private String docNum;

    // 当 事 人
    @Column(name = "CLIENT_NAME")
    private String clientName;

    // 案 由
    @Column(name = "SUMMARY")
    private String summary;

    // 立案时间
    @Column(name = "FILE_TIME")
    private Date fileTime;

    // 处罚决定书编号
    @Column(name = "PUBLISH_CODE")
    private String publishCode;

    // 处罚时间
    @Column(name = "PUBLISH_DATE")
    private Date publishDate;

    // 处罚内容
    @Column(name = "PUBLISH_CONTENT")
    private String publishContent;

    // 案情概要
    @Column(name = "SUMMARY_CASE")
    private String summaryCase;

    // 执行情况
    @Column(name = "EXECUTIVE_CONDITION")
    private String executiveCondition;

    // 承办人员意见
    @Column(name = "MAJOR_PERSON_OPINION")
    private String majorPersonOpinion;

    // 承办人员签名图片
    @Lob
    @Column(name = "MAJOR_PERSON_BASE64")
    private String majorPersonBase64;

    // 承办人员签名值
    @Lob
    @Column(name = "MAJOR_PERSON_VALUE")
    private String majorPersonValue;

    // 承办人员签名位置
    @Column(name = "MAJOR_PERSON_PLACE")
    private String majorPersonPlace;

    // 承办人员签名时间
    @Column(name = "MAJOR_PERSON_DATE")
    private Date majorPersonDate;
    
 // 承办人员签名图片
    @Lob
    @Column(name = "MINOR_PERSON_BASE64")
    private String minorPersonBase64;

    // 承办人员签名值
    @Lob
    @Column(name = "MINOR_PERSON_VALUE")
    private String minorPersonValue;

    // 承办人员签名位置
    @Column(name = "MINOR_PERSON_PLACE")
    private String minorPersonPlace;

    // 承办人员签名时间
    @Column(name = "MINOR_PERSON_DATE")
    private Date minorPersonDate;

    // 承办部门负责人签名图片
    @Lob
    @Column(name = "MAJOR_EMP_LEADER_BASE64")
    private String majorEmpLeaderBase64;

    // 承办部门负责人签名值
    @Lob
    @Column(name = "MAJOR_EMP_LEADER_VALUE")
    private String majorEmpLeaderValue;

    // 承办部门负责人签名位置
    @Column(name = "MAJOR_EMP_LEADER_PLACE")
    private String majorEmpLeaderPlace;

    // 承办部门负责人签名时间
    @Column(name = "MAJOR_EMP_LEADER_DATE")
    private Date majorEmpLeaderDate;

    // 承办部门负责人意见
    @Column(name = "MAJOR_EMP_LEADER_OPINION")
    private String majorEmpLeaderOpinion;

    // 法制部门意见
    @Column(name = "LAW_UNIT_OPINION")
    private String lawUnitOpinion;

    // 法制部门签名图片
    @Lob
    @Column(name = "LAW_UNIT_BASE64")
    private String lawUnitBase64;

    // 法制部门签名值
    @Lob
    @Column(name = "LAW_UNIT_VALUE")
    private String lawUnitValue;

    // 法制部门签名位置
    @Column(name = "LAW_UNIT_PLACE")
    private String lawUnitPlace;

    // 法制部门签名时间
    @Column(name = "LAW_UNIT_DATE")
    private Date lawUnitDate;

    // 主管领导意见
    @Column(name = "MAJOR_LEADER_OPINION")
    private String majorLeaderOpinion;

    // 主管领导签名图片
    @Lob
    @Column(name = "MAJOR_LEADER_BASE64")
    private String majorLeaderBase64;

    // 主管领导签名值
    @Lob
    @Column(name = "MAJOR_LEADER_VALUE")
    private String majorLeaderValue;

    // 主管领导签名位置
    @Column(name = "MAJOR_LEADER_PLACE")
    private String majorLeaderPlace;

    // 主管领导签名时间
    @Column(name = "MAJOR_LEADER_DATE")
    private Date majorLeaderDate;

    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法办案唯一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 执法环节ID
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 创建人ID
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    // 创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 变更人ID
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    // 变更人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    // 变更时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocArea() {
        return docArea;
    }

    public void setDocArea(String docArea) {
        this.docArea = docArea;
    }

    public String getDocYear() {
        return docYear;
    }

    public void setDocYear(String docYear) {
        this.docYear = docYear;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getFileTime() {
        return fileTime;
    }

    public void setFileTime(Date fileTime) {
        this.fileTime = fileTime;
    }

    public String getPublishCode() {
        return publishCode;
    }

    public void setPublishCode(String publishCode) {
        this.publishCode = publishCode;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishContent() {
        return publishContent;
    }

    public void setPublishContent(String publishContent) {
        this.publishContent = publishContent;
    }

    public String getSummaryCase() {
        return summaryCase;
    }

    public void setSummaryCase(String summaryCase) {
        this.summaryCase = summaryCase;
    }

    public String getExecutiveCondition() {
        return executiveCondition;
    }

    public void setExecutiveCondition(String executiveCondition) {
        this.executiveCondition = executiveCondition;
    }

    public String getMajorPersonOpinion() {
        return majorPersonOpinion;
    }

    public void setMajorPersonOpinion(String majorPersonOpinion) {
        this.majorPersonOpinion = majorPersonOpinion;
    }

    public String getMajorPersonBase64() {
        return majorPersonBase64;
    }

    public void setMajorPersonBase64(String majorPersonBase64) {
        this.majorPersonBase64 = majorPersonBase64;
    }

    public String getMajorPersonValue() {
        return majorPersonValue;
    }

    public void setMajorPersonValue(String majorPersonValue) {
        this.majorPersonValue = majorPersonValue;
    }

    public String getMajorPersonPlace() {
        return majorPersonPlace;
    }

    public void setMajorPersonPlace(String majorPersonPlace) {
        this.majorPersonPlace = majorPersonPlace;
    }

    public Date getMajorPersonDate() {
        return majorPersonDate;
    }

    public void setMajorPersonDate(Date majorPersonDate) {
        this.majorPersonDate = majorPersonDate;
    }
   
    public String getMinorPersonBase64() {
		return minorPersonBase64;
	}

	public void setMinorPersonBase64(String minorPersonBase64) {
		this.minorPersonBase64 = minorPersonBase64;
	}

	public String getMinorPersonValue() {
		return minorPersonValue;
	}

	public void setMinorPersonValue(String minorPersonValue) {
		this.minorPersonValue = minorPersonValue;
	}

	public String getMinorPersonPlace() {
		return minorPersonPlace;
	}

	public void setMinorPersonPlace(String minorPersonPlace) {
		this.minorPersonPlace = minorPersonPlace;
	}

	public Date getMinorPersonDate() {
		return minorPersonDate;
	}

	public void setMinorPersonDate(Date minorPersonDate) {
		this.minorPersonDate = minorPersonDate;
	}

	public String getMajorEmpLeaderBase64() {
        return majorEmpLeaderBase64;
    }

    public void setMajorEmpLeaderBase64(String majorEmpLeaderBase64) {
        this.majorEmpLeaderBase64 = majorEmpLeaderBase64;
    }

    public String getMajorEmpLeaderValue() {
        return majorEmpLeaderValue;
    }

    public void setMajorEmpLeaderValue(String majorEmpLeaderValue) {
        this.majorEmpLeaderValue = majorEmpLeaderValue;
    }

    public String getMajorEmpLeaderPlace() {
        return majorEmpLeaderPlace;
    }

    public void setMajorEmpLeaderPlace(String majorEmpLeaderPlace) {
        this.majorEmpLeaderPlace = majorEmpLeaderPlace;
    }

    public Date getMajorEmpLeaderDate() {
        return majorEmpLeaderDate;
    }

    public void setMajorEmpLeaderDate(Date majorEmpLeaderDate) {
        this.majorEmpLeaderDate = majorEmpLeaderDate;
    }

    public String getMajorEmpLeaderOpinion() {
        return majorEmpLeaderOpinion;
    }

    public void setMajorEmpLeaderOpinion(String majorEmpLeaderOpinion) {
        this.majorEmpLeaderOpinion = majorEmpLeaderOpinion;
    }

    public String getLawUnitOpinion() {
        return lawUnitOpinion;
    }

    public void setLawUnitOpinion(String lawUnitOpinion) {
        this.lawUnitOpinion = lawUnitOpinion;
    }

    public String getLawUnitBase64() {
        return lawUnitBase64;
    }

    public void setLawUnitBase64(String lawUnitBase64) {
        this.lawUnitBase64 = lawUnitBase64;
    }

    public String getLawUnitValue() {
        return lawUnitValue;
    }

    public void setLawUnitValue(String lawUnitValue) {
        this.lawUnitValue = lawUnitValue;
    }

    public String getLawUnitPlace() {
        return lawUnitPlace;
    }

    public void setLawUnitPlace(String lawUnitPlace) {
        this.lawUnitPlace = lawUnitPlace;
    }

    public Date getLawUnitDate() {
        return lawUnitDate;
    }

    public void setLawUnitDate(Date lawUnitDate) {
        this.lawUnitDate = lawUnitDate;
    }

    public String getMajorLeaderOpinion() {
        return majorLeaderOpinion;
    }

    public void setMajorLeaderOpinion(String majorLeaderOpinion) {
        this.majorLeaderOpinion = majorLeaderOpinion;
    }

    public String getMajorLeaderBase64() {
        return majorLeaderBase64;
    }

    public void setMajorLeaderBase64(String majorLeaderBase64) {
        this.majorLeaderBase64 = majorLeaderBase64;
    }

    public String getMajorLeaderValue() {
        return majorLeaderValue;
    }

    public void setMajorLeaderValue(String majorLeaderValue) {
        this.majorLeaderValue = majorLeaderValue;
    }

    public String getMajorLeaderPlace() {
        return majorLeaderPlace;
    }

    public void setMajorLeaderPlace(String majorLeaderPlace) {
        this.majorLeaderPlace = majorLeaderPlace;
    }

    public Date getMajorLeaderDate() {
        return majorLeaderDate;
    }

    public void setMajorLeaderDate(Date majorLeaderDate) {
        this.majorLeaderDate = majorLeaderDate;
    }

    public String getEnclosureAddress() {
        return enclosureAddress;
    }

    public void setEnclosureAddress(String enclosureAddress) {
        this.enclosureAddress = enclosureAddress;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getLawLinkId() {
        return lawLinkId;
    }

    public void setLawLinkId(String lawLinkId) {
        this.lawLinkId = lawLinkId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
