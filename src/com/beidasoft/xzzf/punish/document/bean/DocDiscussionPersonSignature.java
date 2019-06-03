package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 案件集体讨论记录参与人签名表实体类
 */
@Entity
@Table(name="ZF_DOC_DISCUSSION_PSN_SIGN")
public class DocDiscussionPersonSignature {
    // 案件集体讨论记录参与人签名表主键ID
    @Id
    @Column(name = "ID")
    private String id;

    // 对应的案件集体讨论记录表主键ID
    @Column(name = "GROUP_ID")
    private String groupId;

    // 参与人签名图片
    @Lob
    @Column(name = "PERSON_SIGNATURE_BASE64")
    private String personSignatureBase64;

    // 参与人签名值
    @Lob
    @Column(name = "PERSON_SIGNATURE_VALUE")
    private String personSignatureValue;

    // 参与人签名位置
    @Column(name = "PERSON_SIGNATURE_PLACE")
    private String personSignaturePlace;

    // 参与人签名时间
    @Column(name = "PERSON_SIGNATURE_TIME")
    private Date personSignatureTime;

    //参与人UUID
    @Column(name = "PERSON_UUID")
    private int personUUID;
    
    //参与人排序依据
    @Column(name = "PERSON_NO")
    private String personNo;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public int getPersonUUID() {
		return personUUID;
	}

	public void setPersonUUID(int personUUID) {
		this.personUUID = personUUID;
	}

	public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPersonSignatureBase64() {
        return personSignatureBase64;
    }

    public void setPersonSignatureBase64(String personSignatureBase64) {
        this.personSignatureBase64 = personSignatureBase64;
    }

    public String getPersonSignatureValue() {
        return personSignatureValue;
    }

    public void setPersonSignatureValue(String personSignatureValue) {
        this.personSignatureValue = personSignatureValue;
    }

    public String getPersonSignaturePlace() {
        return personSignaturePlace;
    }

    public void setPersonSignaturePlace(String personSignaturePlace) {
        this.personSignaturePlace = personSignaturePlace;
    }

    public Date getPersonSignatureTime() {
        return personSignatureTime;
    }

    public void setPersonSignatureTime(Date personSignatureTime) {
        this.personSignatureTime = personSignatureTime;
    }

	public String getPersonNo() {
		return personNo;
	}

	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}

}
