package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 送达回证实体类
 */
@Entity
@Table(name="ZF_DOC_SENDTORETURN")
public class DocSendtoreturn {
    // 送达回证id
    @Id
    @Column(name = "ID")
    private String id;

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
    
    // 组织编号
    @Column(name = "ORGANS_CODE")
    private String organsCode;

    // 组织名称
    @Column(name = "ORGANS_NAME")
    private String organsName;

    // 受送达人（单位）
    @Column(name = "ARRVEL_PEOPLE")
    private String arrvelPeople;

    // 行政单位送达时间
    @Column(name = "ARRVEL_TIME")
    private Date arrvelTime;

    // 送达地点
    @Column(name = "ARRVEL_ADDRESS")
    private String arrvelAddress;

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
    
    // 移送人2签名图片
    @Lob
    @Column(name = "MOVE_MINOR_SIGNATURE_BASE64")
    private String moveMinorSignatureBase64;

    // 移送人2签名值
    @Lob
    @Column(name = "MOVE_MINOR_SIGNATURE_VALUE")
    private String moveMinorSignatureValue;

    // 移送人2签名位置
    @Column(name = "MOVE_MINOR_SIGNATURE_PLACE")
    private String moveMinorSignaturePlace;

    // 送达文书名称、编号
    @Column(name = "ARRVEL_NAME_ONE")
    private String arrvelNameOne;
    
    // 送达方式
    @Column(name = "ARRVEL_TYPE_ONE")
    private String arrvelTypeOne;

    // 受送达人签名图片
    @Lob
    @Column(name = "ARRVEL_MAJOR1_SIGNATURE_BASE64")
    private String arrvelMajorSignatureBase64One;

    // 受送达人签名值
    @Lob
    @Column(name = "ARRVEL_MAJOR1_SIGNATURE_VALUE")
    private String arrvelMajorSignatureValueOne;

    // 受送达人签名位置
    @Column(name = "ARRVEL_MAJOR1_SIGNATURE_PLACE")
    private String arrvelMajorSignaturePlaceOne;

    // 受送达人签名时间
    @Column(name = "ARRVEL_TIME_ONE")
    private Date arrvelTimeOne;

    // 送达文书名称、编号
    @Column(name = "ARRVEL_NAME_TWO")
    private String arrvelNameTwo;

    // 送达方式
    @Column(name = "ARRVEL_TYPE_TWO")
    private String arrvelTypeTwo;

    // 受送达人签名图片
    @Lob
    @Column(name = "ARRVEL_MAJOR2_SIGNATURE_BASE64")
    private String arrvelMajorSignatureBase64Two;

    // 受送达人签名值
    @Lob
    @Column(name = "ARRVEL_MAJOR2_SIGNATURE_VALUE")
    private String arrvelMajorSignatureValueTwo;

    // 受送达人签名位置
    @Column(name = "ARRVEL_MAJOR2_SIGNATURE_PLACE")
    private String arrvelMajorSignaturePlaceTwo;

    // 受送达人签名时间
    @Column(name = "ARRVEL_TIME_TWO")
    private Date arrvelTimeTwo;

    // 送达文书名称、编号
    @Column(name = "ARRVEL_NAME_THREE")
    private String arrvelNameThree;

    // 送达方式
    @Column(name = "ARRVEL_TYPE_THREE")
    private String arrvelTypeThree;

    // 受送达人签名图片
    @Lob
    @Column(name = "ARRVEL_MAJOR3_SIGNATURE_BASE64")
    private String arrvelMajorSignatureBase64Three;

    // 受送达人签名值
    @Lob
    @Column(name = "ARRVEL_MAJOR3_SIGNATURE_VALUE")
    private String arrvelMajorSignatureValueThree;

    // 受送达人签名位置
    @Column(name = "ARRVEL_MAJOR3_SIGNATURE_PLACE")
    private String arrvelMajorSignaturePlaceThree;

    //是否备注
    @Column(name = "IS_REMARKS")
    private String isRemarks;
    
    //备注
    @Column(name = "REMARKS")
    private String remarks;
    
    // 受送达人签名时间
    @Column(name = "ARRVEL_TIME_THREE")
    private Date arrvelTimeThree;
    
    // 送达文书1主键
    @Column(name = "SEND_DOC1_ID")
    private String sendDoc1Id;

    // 送达文书2主键
    @Column(name = "SEND_DOC2_ID")
    private String sendDoc2Id;

    // 送达文书3主键
    @Column(name = "SEND_DOC3_ID")
    private String sendDoc3Id;

    // 备注
    @Column(name = "NOTE")
    private String note;

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

	public String getArrvelPeople() {
        return arrvelPeople;
    }

    public void setArrvelPeople(String arrvelPeople) {
        this.arrvelPeople = arrvelPeople;
    }
    
    public Date getArrvelTime() {
		return arrvelTime;
	}

	public void setArrvelTime(Date arrvelTime) {
		this.arrvelTime = arrvelTime;
	}

	public String getArrvelAddress() {
        return arrvelAddress;
    }

    public void setArrvelAddress(String arrvelAddress) {
        this.arrvelAddress = arrvelAddress;
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
   
    public String getMoveMinorSignatureBase64() {
		return moveMinorSignatureBase64;
	}

	public void setMoveMinorSignatureBase64(String moveMinorSignatureBase64) {
		this.moveMinorSignatureBase64 = moveMinorSignatureBase64;
	}

	public String getMoveMinorSignatureValue() {
		return moveMinorSignatureValue;
	}

	public void setMoveMinorSignatureValue(String moveMinorSignatureValue) {
		this.moveMinorSignatureValue = moveMinorSignatureValue;
	}

	public String getMoveMinorSignaturePlace() {
		return moveMinorSignaturePlace;
	}

	public void setMoveMinorSignaturePlace(String moveMinorSignaturePlace) {
		this.moveMinorSignaturePlace = moveMinorSignaturePlace;
	}

	public String getArrvelNameOne() {
        return arrvelNameOne;
    }

    public void setArrvelNameOne(String arrvelNameOne) {
        this.arrvelNameOne = arrvelNameOne;
    }

    public String getArrvelTypeOne() {
        return arrvelTypeOne;
    }

    public void setArrvelTypeOne(String arrvelTypeOne) {
        this.arrvelTypeOne = arrvelTypeOne;
    }

    public String getArrvelMajorSignatureBase64One() {
        return arrvelMajorSignatureBase64One;
    }

    public void setArrvelMajorSignatureBase64One(String arrvelMajorSignatureBase64One) {
        this.arrvelMajorSignatureBase64One = arrvelMajorSignatureBase64One;
    }

    public String getArrvelMajorSignatureValueOne() {
        return arrvelMajorSignatureValueOne;
    }

    public void setArrvelMajorSignatureValueOne(String arrvelMajorSignatureValueOne) {
        this.arrvelMajorSignatureValueOne = arrvelMajorSignatureValueOne;
    }

    public String getArrvelMajorSignaturePlaceOne() {
        return arrvelMajorSignaturePlaceOne;
    }

    public void setArrvelMajorSignaturePlaceOne(String arrvelMajorSignaturePlaceOne) {
        this.arrvelMajorSignaturePlaceOne = arrvelMajorSignaturePlaceOne;
    }

    public Date getArrvelTimeOne() {
        return arrvelTimeOne;
    }

    public void setArrvelTimeOne(Date arrvelTimeOne) {
        this.arrvelTimeOne = arrvelTimeOne;
    }

    public String getArrvelNameTwo() {
        return arrvelNameTwo;
    }

    public void setArrvelNameTwo(String arrvelNameTwo) {
        this.arrvelNameTwo = arrvelNameTwo;
    }

    public String getArrvelTypeTwo() {
        return arrvelTypeTwo;
    }

    public void setArrvelTypeTwo(String arrvelTypeTwo) {
        this.arrvelTypeTwo = arrvelTypeTwo;
    }

    public String getArrvelMajorSignatureBase64Two() {
        return arrvelMajorSignatureBase64Two;
    }

    public void setArrvelMajorSignatureBase64Two(String arrvelMajorSignatureBase64Two) {
        this.arrvelMajorSignatureBase64Two = arrvelMajorSignatureBase64Two;
    }

    public String getArrvelMajorSignatureValueTwo() {
        return arrvelMajorSignatureValueTwo;
    }

    public void setArrvelMajorSignatureValueTwo(String arrvelMajorSignatureValueTwo) {
        this.arrvelMajorSignatureValueTwo = arrvelMajorSignatureValueTwo;
    }

    public String getArrvelMajorSignaturePlaceTwo() {
        return arrvelMajorSignaturePlaceTwo;
    }

    public void setArrvelMajorSignaturePlaceTwo(String arrvelMajorSignaturePlaceTwo) {
        this.arrvelMajorSignaturePlaceTwo = arrvelMajorSignaturePlaceTwo;
    }

    public Date getArrvelTimeTwo() {
        return arrvelTimeTwo;
    }

    public void setArrvelTimeTwo(Date arrvelTimeTwo) {
        this.arrvelTimeTwo = arrvelTimeTwo;
    }

    public String getArrvelNameThree() {
        return arrvelNameThree;
    }

    public void setArrvelNameThree(String arrvelNameThree) {
        this.arrvelNameThree = arrvelNameThree;
    }

    public String getArrvelTypeThree() {
        return arrvelTypeThree;
    }

    public void setArrvelTypeThree(String arrvelTypeThree) {
        this.arrvelTypeThree = arrvelTypeThree;
    }

    public String getArrvelMajorSignatureBase64Three() {
        return arrvelMajorSignatureBase64Three;
    }

    public void setArrvelMajorSignatureBase64Three(String arrvelMajorSignatureBase64Three) {
        this.arrvelMajorSignatureBase64Three = arrvelMajorSignatureBase64Three;
    }

    public String getArrvelMajorSignatureValueThree() {
        return arrvelMajorSignatureValueThree;
    }

    public void setArrvelMajorSignatureValueThree(String arrvelMajorSignatureValueThree) {
        this.arrvelMajorSignatureValueThree = arrvelMajorSignatureValueThree;
    }

    public String getArrvelMajorSignaturePlaceThree() {
        return arrvelMajorSignaturePlaceThree;
    }

    public void setArrvelMajorSignaturePlaceThree(String arrvelMajorSignaturePlaceThree) {
        this.arrvelMajorSignaturePlaceThree = arrvelMajorSignaturePlaceThree;
    }

    public Date getArrvelTimeThree() {
        return arrvelTimeThree;
    }

    public void setArrvelTimeThree(Date arrvelTimeThree) {
        this.arrvelTimeThree = arrvelTimeThree;
    }

    public String getSendDoc1Id() {
		return sendDoc1Id;
	}

	public void setSendDoc1Id(String sendDoc1Id) {
		this.sendDoc1Id = sendDoc1Id;
	}

	public String getSendDoc2Id() {
		return sendDoc2Id;
	}

	public void setSendDoc2Id(String sendDoc2Id) {
		this.sendDoc2Id = sendDoc2Id;
	}

	public String getSendDoc3Id() {
		return sendDoc3Id;
	}

	public void setSendDoc3Id(String sendDoc3Id) {
		this.sendDoc3Id = sendDoc3Id;
	}

	public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
