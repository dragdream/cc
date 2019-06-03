package com.tianee.oa.subsys.crm.core.customer.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;



public class TeeCrmContactUserModel extends TeeBaseModel{
	private int sid;//Sid	int	11	否	主键
	
	private int customerId;//Customer_ID	Int	11	否	客户表外键
	
	private String customerName;//客户名称
	
	private String name;//NAME	Varchar	255	否	姓名
	
	private int gender;//GENDER	Int	11	是	性别：1、男  2、女
	
	private String genderDesc;//
	
	private String department;//DEPARTMENT	Varchar	255	是	部门
	
	private String important;//IMPORTANT	Varchar	255	是	重要程度（系统代码表）
	
	private String importantDesc;//重要程度描述
	
	private String birthdayDesc;///格式：2014-03-02
	
	private String pos;//_POS	Varchar	255	是	状态（系统代码表）
	
	private String posDesc;//状态描述
	
	private String brief;//BRIEF	TEXT		是	简介
	
	private String telephone;//TELPHONE	Varchar	255	是	固定电话
	
	private String telephone1;//TELPHONE1	Varchar	255	是	分机电话
	
	private String mobilePhone;//MOBILE_Phone	Varchar	255	是	移动电话
	
	private String fax;//FAX	Varchar	255	是	传真
	
	private String email;//EMAIL	Varchar	255	是	邮箱
	
	private String qq;//QQ	Varchar	255	是	QQ
	
	private int addPersonId;//创建人Id
	private String addPersonName;// 创建人名称
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getImportant() {
		return important;
	}
	public void setImportant(String important) {
		this.important = important;
	}
	
	public String getBirthdayDesc() {
		return birthdayDesc;
	}
	public void setBirthdayDesc(String birthdayDesc) {
		this.birthdayDesc = birthdayDesc;
	}
	
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getTelephone1() {
		return telephone1;
	}
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public int getAddPersonId() {
		return addPersonId;
	}
	public void setAddPersonId(int addPersonId) {
		this.addPersonId = addPersonId;
	}
	public String getGenderDesc() {
		return genderDesc;
	}
	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}
	public String getImportantDesc() {
		return importantDesc;
	}
	public void setImportantDesc(String importantDesc) {
		this.importantDesc = importantDesc;
	}
	public String getPosDesc() {
		return posDesc;
	}
	public void setPosDesc(String posDesc) {
		this.posDesc = posDesc;
	}
	public String getAddPersonName() {
		return addPersonName;
	}
	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}
	

	
}