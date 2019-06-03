package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 证据材料移送清单实体类
 */
@Entity
@Table(name="ZF_DOC_TRANSFERLIST")
public class DocTransferlist {
    // 证据材料移送清单id
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

    // 文件、资料类
    @Column(name = "FILE_CONTENT")
    private String fileContent;

    // 文件、资料类备注
    @Column(name = "FILE_NOTE")
    private String fileNote;

    // 资产物品类
    @Column(name = "MONEY_GOODS")
    private String moneyGoods;

    // 资产物品类备注
    @Column(name = "GOODS_NOTES")
    private String goodsNotes;

    // 其他类
    @Column(name = "OTHER_GOODS")
    private String otherGoods;

    // 其他类备注
    @Column(name = "OTHER_NOTE")
    private String otherNote;

    // 移送人签名图片
    @Lob
    @Column(name = "MOVE_MAJOR_SIGNATURE_BASE64")
    private String moveMajorSignatureBase64;

    // 移送人签名值
    @Lob
    @Column(name = "MOVE_MAJOR_SIGNATURE_VALUE")
    private String moveMajorSignatureValue;

    // 移送人签名位置
    @Column(name = "MOVE_MAJOR_SIGNATURE_PLACE")
    private String moveMajorSignaturePlace;

    // 移送单位印章图片
    @Lob
    @Column(name = "MOVE_LAW_UNIT_SEAL")
    private String moveLawUnitSeal;

    // 移送单位印章值
    @Lob
    @Column(name = "MOVE_LAW_UNIT_VALUE")
    private String moveLawUnitValue;

    // 移送单位印章位置
    @Column(name = "MOVE_LAW_UNIT_PLACE")
    private String moveLawUnitPlace;

    // 移送单位印章时间
    @Column(name = "MOVE_LAW_UNIT_DATE")
    private Date moveLawUnitDate;

    // 接收人签名图片
    @Lob
    @Column(name = "RECIVE_MAJOR_SIGNATURE_BASE64")
    private String reciveMajorSignatureBase64;

    // 接收人签名值
    @Lob
    @Column(name = "RECIVE_MAJOR_SIGNATURE_VALUE")
    private String reciveMajorSignatureValue;

    // 接收人签名位置
    @Column(name = "RECIVE_MAJOR_SIGNATURE_PLACE")
    private String reciveMajorSignaturePlace;

    // 接收单位印章图片
    @Lob
    @Column(name = "RECIVE_LAW_UNIT_SEAL")
    private String reciveLawUnitSeal;

    // 接收单位印章值
    @Lob
    @Column(name = "RECIVE_LAW_UNIT_VALUE")
    private String reciveLawUnitValue;

    // 接收单位印章位置
    @Column(name = "RECIVE_LAW_UNIT_PLACE")
    private String reciveLawUnitPlace;

    // 接收单位印章时间
    @Column(name = "RECIVE_LAW_UNIT_DATE")
    private Date reciveLawUnitDate;

    // 移送组织机构编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 移送组织机构名称
    @Column(name = "ORGANS_NAME")
    private String organsName;

    // 接收组织机构编号
    @Column(name = "ORGANS_CODE_TO")
    private String organsCodeTo;

    // 接收组织机构名称
    @Column(name = "ORGANS_NAME_TO")
    private String organsNameTo;

    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法环节id
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 创建人id
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    // 创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 变更人id
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    // 变更人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    // 变更时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    // 执法办案唯一id
    @Column(name = "BASE_ID")
    private String baseId;

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

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileNote() {
        return fileNote;
    }

    public void setFileNote(String fileNote) {
        this.fileNote = fileNote;
    }

    public String getMoneyGoods() {
        return moneyGoods;
    }

    public void setMoneyGoods(String moneyGoods) {
        this.moneyGoods = moneyGoods;
    }

    public String getGoodsNotes() {
        return goodsNotes;
    }

    public void setGoodsNotes(String goodsNotes) {
        this.goodsNotes = goodsNotes;
    }

    public String getOtherGoods() {
        return otherGoods;
    }

    public void setOtherGoods(String otherGoods) {
        this.otherGoods = otherGoods;
    }

    public String getOtherNote() {
        return otherNote;
    }

    public void setOtherNote(String otherNote) {
        this.otherNote = otherNote;
    }

    public String getMoveMajorSignatureBase64() {
        return moveMajorSignatureBase64;
    }

    public void setMoveMajorSignatureBase64(String moveMajorSignatureBase64) {
        this.moveMajorSignatureBase64 = moveMajorSignatureBase64;
    }

    public String getMoveMajorSignatureValue() {
        return moveMajorSignatureValue;
    }

    public void setMoveMajorSignatureValue(String moveMajorSignatureValue) {
        this.moveMajorSignatureValue = moveMajorSignatureValue;
    }

    public String getMoveMajorSignaturePlace() {
        return moveMajorSignaturePlace;
    }

    public void setMoveMajorSignaturePlace(String moveMajorSignaturePlace) {
        this.moveMajorSignaturePlace = moveMajorSignaturePlace;
    }

    public String getMoveLawUnitSeal() {
        return moveLawUnitSeal;
    }

    public void setMoveLawUnitSeal(String moveLawUnitSeal) {
        this.moveLawUnitSeal = moveLawUnitSeal;
    }

    public String getMoveLawUnitValue() {
        return moveLawUnitValue;
    }

    public void setMoveLawUnitValue(String moveLawUnitValue) {
        this.moveLawUnitValue = moveLawUnitValue;
    }

    public String getMoveLawUnitPlace() {
        return moveLawUnitPlace;
    }

    public void setMoveLawUnitPlace(String moveLawUnitPlace) {
        this.moveLawUnitPlace = moveLawUnitPlace;
    }

    public Date getMoveLawUnitDate() {
        return moveLawUnitDate;
    }

    public void setMoveLawUnitDate(Date moveLawUnitDate) {
        this.moveLawUnitDate = moveLawUnitDate;
    }

    public String getReciveMajorSignatureBase64() {
        return reciveMajorSignatureBase64;
    }

    public void setReciveMajorSignatureBase64(String reciveMajorSignatureBase64) {
        this.reciveMajorSignatureBase64 = reciveMajorSignatureBase64;
    }

    public String getReciveMajorSignatureValue() {
        return reciveMajorSignatureValue;
    }

    public void setReciveMajorSignatureValue(String reciveMajorSignatureValue) {
        this.reciveMajorSignatureValue = reciveMajorSignatureValue;
    }

    public String getReciveMajorSignaturePlace() {
        return reciveMajorSignaturePlace;
    }

    public void setReciveMajorSignaturePlace(String reciveMajorSignaturePlace) {
        this.reciveMajorSignaturePlace = reciveMajorSignaturePlace;
    }

    public String getReciveLawUnitSeal() {
        return reciveLawUnitSeal;
    }

    public void setReciveLawUnitSeal(String reciveLawUnitSeal) {
        this.reciveLawUnitSeal = reciveLawUnitSeal;
    }

    public String getReciveLawUnitValue() {
        return reciveLawUnitValue;
    }

    public void setReciveLawUnitValue(String reciveLawUnitValue) {
        this.reciveLawUnitValue = reciveLawUnitValue;
    }

    public String getReciveLawUnitPlace() {
        return reciveLawUnitPlace;
    }

    public void setReciveLawUnitPlace(String reciveLawUnitPlace) {
        this.reciveLawUnitPlace = reciveLawUnitPlace;
    }

    public Date getReciveLawUnitDate() {
        return reciveLawUnitDate;
    }

    public void setReciveLawUnitDate(Date reciveLawUnitDate) {
        this.reciveLawUnitDate = reciveLawUnitDate;
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

	public String getOrgansCodeTo() {
        return organsCodeTo;
    }

    public void setOrgansCodeTo(String organsCodeTo) {
        this.organsCodeTo = organsCodeTo;
    }

    public String getOrgansNameTo() {
        return organsNameTo;
    }

    public void setOrgansNameTo(String organsNameTo) {
        this.organsNameTo = organsNameTo;
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

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

}
