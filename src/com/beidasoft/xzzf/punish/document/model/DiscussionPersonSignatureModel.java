package com.beidasoft.xzzf.punish.document.model;

import java.util.Date;


public class DiscussionPersonSignatureModel {

	 // 案件集体讨论记录参与人签名表主键ID
    private String id;

    // 对应的案件集体讨论记录表主键ID
    private String groupId;

    // 参与人签名图片
    private String personSignatureBase64;

    // 参与人签名值
    private String personSignatureValue;

    // 参与人签名位置
    private String personSignaturePlace;

    // 参与人执法证号
    private String personCode;

    // 参与人签名时间
    private Date personSignatureTime;
    
    //参与人UUID
    private String personUUID;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public Date getPersonSignatureTime() {
		return personSignatureTime;
	}

	public void setPersonSignatureTime(Date personSignatureTime) {
		this.personSignatureTime = personSignatureTime;
	}

	public String getPersonUUID() {
		return personUUID;
	}

	public void setPersonUUID(String personUUID) {
		this.personUUID = personUUID;
	}
    
    
}
