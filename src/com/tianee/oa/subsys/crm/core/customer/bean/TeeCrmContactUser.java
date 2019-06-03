package com.tianee.oa.subsys.crm.core.customer.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;


@Entity
@Table(name="CRM_CONTACT_USER")
public class TeeCrmContactUser{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CONTACT_USER_seq_gen")
	@SequenceGenerator(name="CRM_CONTACT_USER_seq_gen", sequenceName="CRM_CONTACT_USER_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXb5e2235ce5d44b5389883bc30ec")
	@JoinColumn(name="CUSTOMER_ID")
	private TeeCrmCustomerInfo customer;//Customer_ID	Int	11	否	客户表外键
	
	@Column(name="NAME")
	private String name;//NAME	Varchar	255	否	姓名
	
	@Column(name="GENDER")
	private int gender;//GENDER	Int	11	是	性别：1、男  2、女
	
	@Column(name="DEPARTMENT")
	private String department;//DEPARTMENT	Varchar	255	是	部门
	
	@Column(name="IMPORTANT")
	private String important;//IMPORTANT	Varchar	255	是	重要程度（系统代码表）
	
	@Column(name="BIRTHDAY")
	private Calendar birthday;///格式：2014-03-02
	
	@Column(name="POS")
	private String _pos;//_POS	Varchar	255	是	状态（系统代码表）
	
	@Column(name="BRIEF")
	private String brief;//BRIEF	TEXT		是	简介
	
	@Column(name="TELEPHONE")
	private String telephone;//TELPHONE	Varchar	255	是	固定电话
	
	@Column(name="TELEPHONE1")
	private String telephone1;//TELPHONE1	Varchar	255	是	分机电话
	
	@Column(name="MOBILE_PHONE")
	private String mobilePhone;//MOBILE_Phone	Varchar	255	是	移动电话
	
	@Column(name="FAX")
	private String fax;//FAX	Varchar	255	是	传真
	
	@Column(name="EMAIL")
	private String email;//EMAIL	Varchar	255	是	邮箱
	
	@Column(name="QQ")
	private String qq;//QQ	Varchar	255	是	QQ
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDXeeb3e414b64a48b081a99169d2a")
	@JoinColumn(name="ADD_PERSON_ID")
	private TeePerson addPerson;//创建人
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public TeeCrmCustomerInfo getCustomer() {
		return customer;
	}
	public void setCustomer(TeeCrmCustomerInfo customer) {
		this.customer = customer;
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
	public Calendar getBirthday() {
		return birthday;
	}
	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	public String get_pos() {
		return _pos;
	}
	public void set_pos(String _pos) {
		this._pos = _pos;
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
	public TeePerson getAddPerson() {
		return addPerson;
	}
	public void setAddPerson(TeePerson addPerson) {
		this.addPerson = addPerson;
	}
}
