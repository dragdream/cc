package com.tianee.oa.core.base.pm.model;


/**
 * 证书管理
 *
 */
public class TeeHumanCertModel {
	private int sid;
	
	private String certCode;//证书编号
	
	/**
	 * 驾驶证
	 * 健康证
	 * 暂住证
	 * 技能证
	 * 其他
	 */
	private String certType;//证件类型
	
	private String certTypeDesc;//证件类型
	
	
	private String certName;//证件名称
	
	private String getTimeDesc;//取证日期
	
	private String validTimeDesc;//生效日期
	
	private String endTimeDesc;//结束日期
	/**
	 * 未生效
	 * 生效中
	 * 已到期
	 */
	private String certAttr;//证件属性
	
	private String certAttrDesc;
	
	private String certOrg;//发证机构
	
	private String remark;//备注
	
	private int humanDocSid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getCertName() {
		return certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public String getGetTimeDesc() {
		return getTimeDesc;
	}

	public void setGetTimeDesc(String getTimeDesc) {
		this.getTimeDesc = getTimeDesc;
	}

	public String getValidTimeDesc() {
		return validTimeDesc;
	}

	public void setValidTimeDesc(String validTimeDesc) {
		this.validTimeDesc = validTimeDesc;
	}

	public String getEndTimeDesc() {
		return endTimeDesc;
	}

	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
	}

	public String getCertOrg() {
		return certOrg;
	}

	public void setCertOrg(String certOrg) {
		this.certOrg = certOrg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	public int getHumanDocSid() {
		return humanDocSid;
	}

	public void setHumanDocSid(int humanDocSid) {
		this.humanDocSid = humanDocSid;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertAttr() {
		return certAttr;
	}

	public void setCertAttr(String certAttr) {
		this.certAttr = certAttr;
	}

	public String getCertTypeDesc() {
		return certTypeDesc;
	}

	public void setCertTypeDesc(String certTypeDesc) {
		this.certTypeDesc = certTypeDesc;
	}

	public String getCertAttrDesc() {
		return certAttrDesc;
	}

	public void setCertAttrDesc(String certAttrDesc) {
		this.certAttrDesc = certAttrDesc;
	}
	
	
}
