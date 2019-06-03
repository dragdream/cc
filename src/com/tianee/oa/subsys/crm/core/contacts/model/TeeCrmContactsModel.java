package com.tianee.oa.subsys.crm.core.contacts.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.contacts.bean.TeeCrmContacts;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeCrmContactsModel extends TeeBaseModel{
	private int sid;//Sid	int	11	否	主键
	
	private int customerId;//Customer_ID	Int	11	否	客户表外键
	
	private String customerName;//客户名称
	
	private String contactName;//NAME	Varchar	255	否	联系人姓名
	
	private int gender;//GENDER	Int	11	是	性别：1、男  2、女
	
	private String genderDesc;//
	
	private String department;//DEPARTMENT	Varchar	255	是	部门
	
	private String duties; //职务
	
	private int isKeyPerson; //是否为关键决策人    0-请选择  1-是  2-否
	
	private String keyPersonDesc;//
	
	private String birthdayDesc;///格式：2014-03-02
	
	private String remark;//TEXT		是	简介
	
	private String telephone;//TELPHONE	Varchar	255	是	电话
	
	private String mobilePhone;//MOBILE_Phone	Varchar	255	手机
	
	private String email;//EMAIL	Varchar	255	是	邮箱
	
	private String address; //地址
	
	private String companyName; //公司名称
	
	private int introduceId;    //介绍人id
	
	private String introduceName; //介绍人名称
	
	private int contactsStatus;    //联系人状态（正常、作废）
	
	private String contactsStatusDesc;
	
	private int addPersonId;//创建人Id
	
	private String addPersonName;// 创建人名称
	
	private String createTimeDesc;//
	
	private int managePersonId;//  负责人
	
	private String managePersonName;//  负责人
	
	private int clueId;//线索外键
	
	List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>(); //名片附件

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getGenderDesc() {
		return genderDesc;
	}

	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public int getIsKeyPerson() {
		return isKeyPerson;
	}

	public void setIsKeyPerson(int isKeyPerson) {
		this.isKeyPerson = isKeyPerson;
	}

	public String getBirthdayDesc() {
		return birthdayDesc;
	}

	public void setBirthdayDesc(String birthdayDesc) {
		this.birthdayDesc = birthdayDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getIntroduceId() {
		return introduceId;
	}

	public void setIntroduceId(int introduceId) {
		this.introduceId = introduceId;
	}

	public String getIntroduceName() {
		return introduceName;
	}

	public void setIntroduceName(String introduceName) {
		this.introduceName = introduceName;
	}

	public int getContactsStatus() {
		return contactsStatus;
	}

	public void setContactsStatus(int contactsStatus) {
		this.contactsStatus = contactsStatus;
	}

	public int getAddPersonId() {
		return addPersonId;
	}

	public void setAddPersonId(int addPersonId) {
		this.addPersonId = addPersonId;
	}

	public String getAddPersonName() {
		return addPersonName;
	}

	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}

	public String getCreateTimeDesc() {
		return createTimeDesc;
	}

	public void setCreateTimeDesc(String createTimeDesc) {
		this.createTimeDesc = createTimeDesc;
	}

	public int getManagePersonId() {
		return managePersonId;
	}

	public void setManagePersonId(int managePersonId) {
		this.managePersonId = managePersonId;
	}

	public String getManagePersonName() {
		return managePersonName;
	}

	public void setManagePersonName(String managePersonName) {
		this.managePersonName = managePersonName;
	}

	public List<TeeAttachmentModel> getAttachmodels() {
		return attachmodels;
	}

	public void setAttachmodels(List<TeeAttachmentModel> attachmodels) {
		this.attachmodels = attachmodels;
	}

	public String getKeyPersonDesc() {
		return keyPersonDesc;
	}

	public void setKeyPersonDesc(String keyPersonDesc) {
		this.keyPersonDesc = keyPersonDesc;
	}

	public String getContactsStatusDesc() {
		return contactsStatusDesc;
	}

	public void setContactsStatusDesc(String contactsStatusDesc) {
		this.contactsStatusDesc = contactsStatusDesc;
	}

	public int getClueId() {
		return clueId;
	}

	public void setClueId(int clueId) {
		this.clueId = clueId;
	}
	
	
}