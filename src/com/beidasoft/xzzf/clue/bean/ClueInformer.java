package com.beidasoft.xzzf.clue.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_CLUE_INFORMER")
public class ClueInformer {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ZF_CLUE_INFORMER_seq_gen")
//	@SequenceGenerator(name = "ZF_CLUE_INFORMER_seq_gen", sequenceName = "ZF_CLUE_INFORMER_seq")
	@Column(name = "ID")
	private String id;	//举报人信息表主键
	
	@Column(name = "CLUE_ID")
	private String clueId;	//案件线索表主键
	
	@Column(name = "INFORMER_TYPE")
	private String informerType;	//举报人类型
	
	@Column(name = "PERSONAL_NAME")
	private String personalName;	//举报人姓名
	
	@Column(name = "PERSONAL_ADDRESS")
	private String personalAddress;	//举报人地址
	
	@Column(name = "PERSONAL_CODE")
	private String personalCode;//举报人身份证号
	
	@Column(name = "PERSONAL_MAIL")
	private String personalMail;//举报人邮件
	
	@Column(name = "PERSONAL_REMARK")
	private String personalRemark;//举报人备注
	
	@Column(name = "IS_INFORMERS")
	private String isInformers;//多次举报
	
	@Column(name = "PERSONAL_TEL")
	private String personalTel;	//举报人联系方式
	
	@Column(name = "ORGANIZATION_NAME")
	private String organizationName;	//举报组织名称
	
	@Column(name = "ORGAN_INFORMER_PERSON_NAME")
	private String organInformerPersonName;	//举报组织法人
	
	@Column(name = "ORGAN_INFORMER_PERSON_TEL")
	private String organInformerPersonTel;//举报组织法人联系方式
	
	@Column(name = "ORGANIZATION_ADDRESS")
	private String organizationAddress;	//举报组织地址
	
	@Column(name = "ORGANIZATION_CODE")
	private String organizationCode;	//举报组织机构代码
	
	@Column(name = "IS_CONTACTS")
	private String isContacts;	//是否为联系人
	
	@Column(name = "CONTACTS_NAME")
	private String contactsName;	//联系人姓名
	
	@Column(name = "CONTACTS_TEL")
	private String contactsTel;		//联系人电话
	
	@Column(name = "CONTACTS_MAIL")
	private String contactsMail;	//联系人邮箱
	
	@Column(name = "CONTACTS_ADDRESS")
	private String contactsAddress;	//联系人地址
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setClueId(String clueId) {
		this.clueId = clueId;
	}

	public String getPersonalMail() {
		return personalMail;
	}

	public void setPersonalMail(String personalMail) {
		this.personalMail = personalMail;
	}

	public String getInformerType() {
		return informerType;
	}

	public void setInformerType(String informerType) {
		this.informerType = informerType;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

	public String getPersonalAddress() {
		return personalAddress;
	}

	public void setPersonalAddress(String personalAddress) {
		this.personalAddress = personalAddress;
	}

	public String getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}

	public String getPersonalRemark() {
		return personalRemark;
	}

	public void setPersonalRemark(String personalRemark) {
		this.personalRemark = personalRemark;
	}

	public String getPersonalTel() {
		return personalTel;
	}

	public void setPersonalTel(String personalTel) {
		this.personalTel = personalTel;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsTel() {
		return contactsTel;
	}

	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}

	public String getContactsMail() {
		return contactsMail;
	}

	public void setContactsMail(String contactsMail) {
		this.contactsMail = contactsMail;
	}

	public String getContactsAddress() {
		return contactsAddress;
	}

	public void setContactsAddress(String contactsAddress) {
		this.contactsAddress = contactsAddress;
	}

	public String getOrganInformerPersonName() {
		return organInformerPersonName;
	}

	public void setOrganInformerPersonName(String organInformerPersonName) {
		this.organInformerPersonName = organInformerPersonName;
	}

	public String getOrganInformerPersonTel() {
		return organInformerPersonTel;
	}

	public void setOrganInformerPersonTel(String organInformerPersonTel) {
		this.organInformerPersonTel = organInformerPersonTel;
	}

	public String getIsInformers() {
		return isInformers;
	}

	public void setIsInformers(String isInformers) {
		this.isInformers = isInformers;
	}

	public String getIsContacts() {
		return isContacts;
	}

	public void setIsContacts(String isContacts) {
		this.isContacts = isContacts;
	}

	public String getClueId() {
		return clueId;
	}

}
