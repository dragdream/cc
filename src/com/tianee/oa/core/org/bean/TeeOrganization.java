package com.tianee.oa.core.org.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name="ORGANIZATION")
public class TeeOrganization  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ORGANIZATION_seq_gen")
	@SequenceGenerator(name="ORGANIZATION_seq_gen", sequenceName="ORGANIZATION_seq")
	private int sid;
	
	@Column(name="UNIT_NAME",length=200)
	private String unitName ;// 单位名称
	@Column(name="TEL_NO",length=50)
	private String telNo;//TEL_NO 联系电话
	
	@Column(name="FAX_NO",length=50)
	private String faxNo ;//FAX_NO传真
	
	@Column(name="POST_NO",length=20)
	private String postNo;//POST_NO 邮政编码

	@Column(name="ADDRESS",length=200)
	private String address;//ADDRESS 单位地址
	 
	@Column(name="URL",length=100)
	private String url ;//URL	单位网站

	@Column(name="EMAIL",length=50)
	private String email;//EMAIL 电子信箱
	 
	@Column(name="BANK_NAME",length=100)
	private String bankName;//BANK_NAME 	开户行名称
	
	@Column(name="BANK_NO",length=50)
	private String bankNo;//BANK_NO 开户行帐号
	
	@Column(name="CORPID",length=50)
	private String CorpId;//微信企业ID
	
	@Column(name="TOKEN_",length=50)
	private String token;//微信Token
	
	@Column(name="ENCODINGAESKEY",length=50)
	private String encodingAESKey;//微信对称密钥

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getCorpId() {
		return CorpId;
	}

	public void setCorpId(String corpId) {
		CorpId = corpId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}
	
}
