package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 查封扣押处理决定书实体类
 */
@Entity
@Table(name="ZF_DOC_SEAL_SEIZURE_DECIDE")
public class DocSealSeizureDecide {
    // 查封扣押处理决定书主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 文书名称类型（查封、扣押）
    @Column(name = "DOC_NAME_TYPE")
    private String docNameType;

    // 文书区字
    @Column(name = "DOC_AREA")
    private String docArea;

    // 文书年度
    @Column(name = "DOC_YEAR")
    private String docYear;

    // 文书序号
    @Column(name = "DOC_NUM")
    private String docNum;

    // 文书号类型（查封、扣押）
    @Column(name = "DOC_NUM_TYPE")
    private String docNumType;

    // 当事人
    @Column(name = "PARTY_NAME")
    private String partyName;

    // 查封扣押决定书名称类型（查封、扣押）
    @Column(name = "DOC_NAME_TYPE_SEAL_SEIZURE")
    private String docNameTypeSealSeizure;

    // 查封扣押决定书文书区字
    @Column(name = "DOC_AREA_SEAL_SEIZURE")
    private String docAreaSealSeizure;

    // 查封扣押决定书文书年度
    @Column(name = "DOC_YEAR_SEAL_SEIZURE")
    private String docYearSealSeizure;

    // 查封扣押决定书文书序号
    @Column(name = "DOC_NUM_SEAL_SEIZURE")
    private String docNumSealSeizure;

    // 查封扣押决定书文书序号类型（查封、扣押）
    @Column(name = "DOC_NUM_TYPE_SEAL_SEIZURE")
    private String docNumTypeSealSeizure;

    // 场所或物品（复选框单选）
    @Column(name = "PLACE_OR_GOODS")
    private String placeOrGoods;

    // 物品清单文书区字
    @Column(name = "DOC_AREA_GOODS")
    private String docAreaGoods;

    // 物品清单文书年度
    @Column(name = "DOC_YEAR_GOODS")
    private String docYearGoods;

    // 物品清单文书序号
    @Column(name = "DOC_NUM_GOODS")
    private String docNumGoods;
    
    // 物品清单类型
    @Column(name = "DOC_TYPE_GOODS")
    private String docTypeGoods;
    
    // 物品处理范围（代码表）
    @Column(name = "GOODS_PROCESS_RANGE")
    private String goodsProcessRange;

    // 处理决定类别
    @Column(name = "PROCESS_DECISION_TYPE")
    private String processDecisionType;

    // 解除类别
    @Column(name = "RELIEVE_TYPE")
    private String relieveType;

    // 延长类别
    @Column(name = "EXTEND_TYPE")
    private String extendType;

    // 延长至
    @Column(name = "EXTEND_DATE")
    private Date extendDate;

    // 鉴定时间（开始）
    @Column(name = "CHECKUP_DATE_START")
    private Date checkupDateStart;

    // 鉴定时间（结束）
    @Column(name = "CHECKUP_DATE_END")
    private Date checkupDateEnd;

    // 鉴定前处理类型
    @Column(name = "CHECKUP_BEFORE_TYPE")
    private String checkupBeforeType;

    // 鉴定前处理延期至
    @Column(name = "CHECKUP_BEFOR_BEFORE")
    private Date checkupBeforBefore;

    // 移送机构名称
    @Column(name = "TRANSFER_ORGAN_NAME")
    private String transferOrganName;

    // 其他处理
    @Column(name = "OTHER_PROCESS")
    private String otherProcess;

    // 人民政府ID
    @Column(name = "GOV_ID")
    private String govId;

    // 人民政府名称
    @Column(name = "GOV_NAME")
    private String govName;

    // 部委ID
    @Column(name = "MINISTRIES_ID")
    private String ministriesId;

    // 部委名称
    @Column(name = "MINISTRIES_NAME")
    private String ministriesName;

    // 市区ID
    @Column(name = "AREA_ID")
    private String areaId;

    // 市区名称
    @Column(name = "AREA_NAME")
    private String areaName;

    // 执法主办人签名图片
    @Lob
    @Column(name = "LAW_MAJOR_SIGNATURE_BASE64")
    private String lawMajorSignatureBase64;

    // 执法主办人签名值
    @Lob
    @Column(name = "LAW_MAJOR_SIGNATURE_VALUE")
    private String lawMajorSignatureValue;

    // 执法主办人签名位置
    @Column(name = "LAW_MAJOR_SIGNATURE_PLACE")
    private String lawMajorSignaturePlace;

    // 执法主办人执法证号
    @Column(name = "LAW_MAJOR_CODE")
    private String lawMajorCode;

    // 执法协办人签名图片
    @Lob
    @Column(name = "LAW_MINOR_SIGNATURE_BASE64")
    private String lawMinorSignatureBase64;

    // 执法协办人签名值
    @Lob
    @Column(name = "LAW_MINOR_SIGNATURE_VALUE")
    private String lawMinorSignatureValue;

    // 执法协办人签名位置
    @Column(name = "LAW_MINOR_SIGNATURE_PLACE")
    private String lawMinorSignaturePlace;

    // 执法协办人执法证号
    @Column(name = "LAW_MINOR_CODE")
    private String lawMinorCode;

    // 行政机关落款印章图片
    @Lob
    @Column(name = "LAW_UNIT_SEAL")
    private String lawUnitSeal;

    // 行政机关落款印章值
    @Lob
    @Column(name = "LAW_UNIT_VALUE")
    private String lawUnitValue;

    // 行政机关落款印章位置
    @Column(name = "LAW_UNIT_PLACE")
    private String lawUnitPlace;

    // 行政机关落款印章时间
    @Column(name = "LAW_UNIT_DATE")
    private Date lawUnitDate;

    // 送达主办人签名图片
    @Lob
    @Column(name = "DELIVER_MAJOR_SIGNATURE_BASE64")
    private String deliverMajorSignatureBase64;

    // 送达主办人签名值
    @Lob
    @Column(name = "DELIVER_MAJOR_SIGNATURE_VALUE")
    private String deliverMajorSignatureValue;

    // 送达主办人签名位置
    @Column(name = "DELIVER_MAJOR_SIGNATURE_PLACE")
    private String deliverMajorSignaturePlace;

    // 送达协办人签名图片
    @Lob
    @Column(name = "DELIVER_MINOR_SIGNATURE_BASE64")
    private String deliverMinorSignatureBase64;

    // 送达协办人签名值
    @Lob
    @Column(name = "DELIVER_MINOR_SIGNATURE_VALUE")
    private String deliverMinorSignatureValue;

    // 送达协办人签名位置
    @Column(name = "DELIVER_MINOR_SIGNATURE_PLACE")
    private String deliverMinorSignaturePlace;

    // 送达时间
    @Column(name = "DELIVER_DATE")
    private Date deliverDate;

    // 受送达人签名或盖章图片
    @Lob
    @Column(name = "RECEIVER_BASE64")
    private String receiverBase64;

    // 受送达人签名或盖章值
    @Lob
    @Column(name = "RECEIVER_VALUE")
    private String receiverValue;

    // 受送达人签名或盖章位置
    @Column(name = "RECEIVER_PLACE")
    private String receiverPlace;

    // 受送达人签名或盖章时间
    @Column(name = "RECEIVER_DATE")
    private Date receiverDate;

    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    // 送达方式
    @Column(name = "DELIVER_TYPE")
    private String deliverType;

    // 送达地点
    @Column(name = "DELIVER_ADDRESS")
    private String deliverAddress;

    // 组织机构编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 组织机构名称
    @Column(name = "ORGANS_NAME")
    private String organsName;

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
    
    //物品清单主表ID
    @Column(name = "DOC_ARTICLES_ID")
    private String docArticlesId;
    
    //物品清单文号
    @Column(name = "DOC_ARTICLES_NAME")
    private String docArticlesName;
    
    // 物品清单(内容)
    @Column(name = "ITEM_CONTAIN")
    private String itemContain;
    
    // 查封扣押(ID)
    @Column(name = "SAM_EVIDENCE_ID")
    private String samEvidenceId;
    
    // 查封扣押文号(内容1)
    @Column(name = "ITEM_CONTAIN_ONE")
    private String itemContainOne;
    
    // 物品清单(内容2)
    @Column(name = "ITEM_CONTAIN_TWO")
    private String itemContainTwo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocNameType() {
        return docNameType;
    }

    public void setDocNameType(String docNameType) {
        this.docNameType = docNameType;
    }

    public String getDocArea() {
        return docArea;
    }

    public String getSamEvidenceId() {
		return samEvidenceId;
	}

	public void setSamEvidenceId(String samEvidenceId) {
		this.samEvidenceId = samEvidenceId;
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

    public String getDocNumType() {
        return docNumType;
    }

    public void setDocNumType(String docNumType) {
        this.docNumType = docNumType;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getDocNameTypeSealSeizure() {
        return docNameTypeSealSeizure;
    }

    public void setDocNameTypeSealSeizure(String docNameTypeSealSeizure) {
        this.docNameTypeSealSeizure = docNameTypeSealSeizure;
    }

    public String getDocAreaSealSeizure() {
        return docAreaSealSeizure;
    }

    public void setDocAreaSealSeizure(String docAreaSealSeizure) {
        this.docAreaSealSeizure = docAreaSealSeizure;
    }

    public String getDocYearSealSeizure() {
        return docYearSealSeizure;
    }

    public void setDocYearSealSeizure(String docYearSealSeizure) {
        this.docYearSealSeizure = docYearSealSeizure;
    }

    public String getDocNumSealSeizure() {
        return docNumSealSeizure;
    }

    public void setDocNumSealSeizure(String docNumSealSeizure) {
        this.docNumSealSeizure = docNumSealSeizure;
    }

    public String getDocNumTypeSealSeizure() {
        return docNumTypeSealSeizure;
    }

    public void setDocNumTypeSealSeizure(String docNumTypeSealSeizure) {
        this.docNumTypeSealSeizure = docNumTypeSealSeizure;
    }

    public String getPlaceOrGoods() {
        return placeOrGoods;
    }

    public void setPlaceOrGoods(String placeOrGoods) {
        this.placeOrGoods = placeOrGoods;
    }

    public String getDocAreaGoods() {
        return docAreaGoods;
    }

    public void setDocAreaGoods(String docAreaGoods) {
        this.docAreaGoods = docAreaGoods;
    }

    public String getDocYearGoods() {
        return docYearGoods;
    }

    public void setDocYearGoods(String docYearGoods) {
        this.docYearGoods = docYearGoods;
    }

    public String getDocNumGoods() {
        return docNumGoods;
    }

    public void setDocNumGoods(String docNumGoods) {
        this.docNumGoods = docNumGoods;
    }

    public String getGoodsProcessRange() {
        return goodsProcessRange;
    }

    public void setGoodsProcessRange(String goodsProcessRange) {
        this.goodsProcessRange = goodsProcessRange;
    }

    public String getProcessDecisionType() {
        return processDecisionType;
    }

    public void setProcessDecisionType(String processDecisionType) {
        this.processDecisionType = processDecisionType;
    }

    public String getRelieveType() {
        return relieveType;
    }

    public void setRelieveType(String relieveType) {
        this.relieveType = relieveType;
    }

    public String getExtendType() {
        return extendType;
    }

    public void setExtendType(String extendType) {
        this.extendType = extendType;
    }

    public Date getExtendDate() {
        return extendDate;
    }

    public void setExtendDate(Date extendDate) {
        this.extendDate = extendDate;
    }

    public Date getCheckupDateStart() {
        return checkupDateStart;
    }

    public void setCheckupDateStart(Date checkupDateStart) {
        this.checkupDateStart = checkupDateStart;
    }

    public Date getCheckupDateEnd() {
        return checkupDateEnd;
    }

    public void setCheckupDateEnd(Date checkupDateEnd) {
        this.checkupDateEnd = checkupDateEnd;
    }

    public String getCheckupBeforeType() {
        return checkupBeforeType;
    }

    public void setCheckupBeforeType(String checkupBeforeType) {
        this.checkupBeforeType = checkupBeforeType;
    }

    public Date getCheckupBeforBefore() {
        return checkupBeforBefore;
    }

    public void setCheckupBeforBefore(Date checkupBeforBefore) {
        this.checkupBeforBefore = checkupBeforBefore;
    }

    public String getTransferOrganName() {
        return transferOrganName;
    }

    public void setTransferOrganName(String transferOrganName) {
        this.transferOrganName = transferOrganName;
    }

    public String getOtherProcess() {
        return otherProcess;
    }

    public void setOtherProcess(String otherProcess) {
        this.otherProcess = otherProcess;
    }

    public String getGovId() {
        return govId;
    }

    public void setGovId(String govId) {
        this.govId = govId;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getMinistriesId() {
        return ministriesId;
    }

    public void setMinistriesId(String ministriesId) {
        this.ministriesId = ministriesId;
    }

    public String getMinistriesName() {
        return ministriesName;
    }

    public void setMinistriesName(String ministriesName) {
        this.ministriesName = ministriesName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLawMajorSignatureBase64() {
        return lawMajorSignatureBase64;
    }

    public void setLawMajorSignatureBase64(String lawMajorSignatureBase64) {
        this.lawMajorSignatureBase64 = lawMajorSignatureBase64;
    }

    public String getLawMajorSignatureValue() {
        return lawMajorSignatureValue;
    }

    public void setLawMajorSignatureValue(String lawMajorSignatureValue) {
        this.lawMajorSignatureValue = lawMajorSignatureValue;
    }

    public String getLawMajorSignaturePlace() {
        return lawMajorSignaturePlace;
    }

    public void setLawMajorSignaturePlace(String lawMajorSignaturePlace) {
        this.lawMajorSignaturePlace = lawMajorSignaturePlace;
    }

    public String getLawMajorCode() {
        return lawMajorCode;
    }

    public void setLawMajorCode(String lawMajorCode) {
        this.lawMajorCode = lawMajorCode;
    }

    public String getLawMinorSignatureBase64() {
        return lawMinorSignatureBase64;
    }

    public void setLawMinorSignatureBase64(String lawMinorSignatureBase64) {
        this.lawMinorSignatureBase64 = lawMinorSignatureBase64;
    }

    public String getLawMinorSignatureValue() {
        return lawMinorSignatureValue;
    }

    public void setLawMinorSignatureValue(String lawMinorSignatureValue) {
        this.lawMinorSignatureValue = lawMinorSignatureValue;
    }

    public String getLawMinorSignaturePlace() {
        return lawMinorSignaturePlace;
    }

    public void setLawMinorSignaturePlace(String lawMinorSignaturePlace) {
        this.lawMinorSignaturePlace = lawMinorSignaturePlace;
    }

    public String getLawMinorCode() {
        return lawMinorCode;
    }

    public void setLawMinorCode(String lawMinorCode) {
        this.lawMinorCode = lawMinorCode;
    }

    public String getLawUnitSeal() {
        return lawUnitSeal;
    }

    public void setLawUnitSeal(String lawUnitSeal) {
        this.lawUnitSeal = lawUnitSeal;
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

    public String getDeliverMajorSignatureBase64() {
        return deliverMajorSignatureBase64;
    }

    public void setDeliverMajorSignatureBase64(String deliverMajorSignatureBase64) {
        this.deliverMajorSignatureBase64 = deliverMajorSignatureBase64;
    }

    public String getDeliverMajorSignatureValue() {
        return deliverMajorSignatureValue;
    }

    public void setDeliverMajorSignatureValue(String deliverMajorSignatureValue) {
        this.deliverMajorSignatureValue = deliverMajorSignatureValue;
    }

    public String getDeliverMajorSignaturePlace() {
        return deliverMajorSignaturePlace;
    }

    public void setDeliverMajorSignaturePlace(String deliverMajorSignaturePlace) {
        this.deliverMajorSignaturePlace = deliverMajorSignaturePlace;
    }

    public String getDeliverMinorSignatureBase64() {
        return deliverMinorSignatureBase64;
    }

    public void setDeliverMinorSignatureBase64(String deliverMinorSignatureBase64) {
        this.deliverMinorSignatureBase64 = deliverMinorSignatureBase64;
    }

    public String getDeliverMinorSignatureValue() {
        return deliverMinorSignatureValue;
    }

    public void setDeliverMinorSignatureValue(String deliverMinorSignatureValue) {
        this.deliverMinorSignatureValue = deliverMinorSignatureValue;
    }

    public String getDeliverMinorSignaturePlace() {
        return deliverMinorSignaturePlace;
    }

    public void setDeliverMinorSignaturePlace(String deliverMinorSignaturePlace) {
        this.deliverMinorSignaturePlace = deliverMinorSignaturePlace;
    }

    public Date getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(Date deliverDate) {
        this.deliverDate = deliverDate;
    }

    public String getReceiverBase64() {
        return receiverBase64;
    }

    public void setReceiverBase64(String receiverBase64) {
        this.receiverBase64 = receiverBase64;
    }

    public String getReceiverValue() {
        return receiverValue;
    }

    public void setReceiverValue(String receiverValue) {
        this.receiverValue = receiverValue;
    }

    public String getReceiverPlace() {
        return receiverPlace;
    }

    public String getIsRemarks() {
		return isRemarks;
	}

	public void setIsRemarks(String isRemarks) {
		this.isRemarks = isRemarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setReceiverPlace(String receiverPlace) {
        this.receiverPlace = receiverPlace;
    }

    public Date getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(Date receiverDate) {
        this.receiverDate = receiverDate;
    }

    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getOrgansCode() {
        return organsCode;
    }

    public void setOrgansCode(String organsCode) {
        this.organsCode = organsCode;
    }

    public String getOrgansName() {
        return organsName;
    }

    public void setOrgansName(String organsName) {
        this.organsName = organsName;
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

	public String getDocTypeGoods() {
		return docTypeGoods;
	}

	public void setDocTypeGoods(String docTypeGoods) {
		this.docTypeGoods = docTypeGoods;
	}

	public String getDocArticlesId() {
		return docArticlesId;
	}

	public void setDocArticlesId(String docArticlesId) {
		this.docArticlesId = docArticlesId;
	}

	public String getDocArticlesName() {
		return docArticlesName;
	}

	public void setDocArticlesName(String docArticlesName) {
		this.docArticlesName = docArticlesName;
	}

	public String getItemContain() {
		return itemContain;
	}

	public void setItemContain(String itemContain) {
		this.itemContain = itemContain;
	}

	public String getItemContainOne() {
		return itemContainOne;
	}

	public void setItemContainOne(String itemContainOne) {
		this.itemContainOne = itemContainOne;
	}

	public String getItemContainTwo() {
		return itemContainTwo;
	}

	public void setItemContainTwo(String itemContainTwo) {
		this.itemContainTwo = itemContainTwo;
	}

}
