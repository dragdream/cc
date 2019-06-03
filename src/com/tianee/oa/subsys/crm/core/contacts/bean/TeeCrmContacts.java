package com.tianee.oa.subsys.crm.core.contacts.bean;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.clue.bean.TeeCrmClue;
import com.tianee.oa.subsys.crm.core.customer.bean.TeeCrmCustomer;

/**
 * 联系人实体
 * @author zhaocaigen
 *
 */
@Entity
@Table(name="CRM_CONTACTS")
public class TeeCrmContacts{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CONTACTS_seq_gen")
	@SequenceGenerator(name="CRM_CONTACTS_seq_gen", sequenceName="CRM_CONTACTS_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	private TeeCrmCustomer customer;//Customer_ID	Int	11	否	客户表外键
	
	@Column(name="CONTACT_NAME")
	private String contactName;//	联系人姓名
	
	@Column(name="DEPARTMENT")
	private String department;//DEPARTMENT	Varchar	255	是	部门
	
	@Column(name="DUTIES")
	private String duties; //职务
	
	@Column(name="ISKEYPERSON")
	private int isKeyPerson; //是否为关键决策人
	
	@Column(name="MOBILE_PHONE")
	private String mobilePhone;//MOBILE_Phone	Varchar	255	手机
	
	@Column(name="TELEPHONE")
	private String telephone;//TELPHONE	Varchar	255	是	电话
	
	@Column(name="EMAIL")
	private String email;//EMAIL	Varchar	255	是	邮箱
	
	@Column(name="GENDER")
	private int gender;//GENDER	Int	11	是	性别：1、男  2、女
	
	@Column(name="ADDRESS")
	private String address; //地址
	
	@Column(name="BIRTHDAY")
	private Calendar birthday;  //生日
	
	@Column(name="COMPANY_NAME")
	private String companyName; //公司名称
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTACTS_ID")
	private TeeCrmContacts introduce;    //介绍人
	
	@Column(name="REMARK")
	@Lob()
	private String remark;   //备注
	
	@Column(name="CONTACT_USER_STATUS")
	private int contactsStatus;    //联系人状态（正常、作废）
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADD_PERSON_ID")
	private TeePerson addPerson;    //创建人
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建日期
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CONTACT_MANAGER_USER_ID")
	private TeePerson contactManagePerson;//  负责人
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="CLUE_ID") //线索外键
	private TeeCrmClue clue;
	
	/*@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="ATTACH_IDS")
	private Attachment attachIds; //  名片附件
*/
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeCrmCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(TeeCrmCustomer customer) {
		this.customer = customer;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
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

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getContactsStatus() {
		return contactsStatus;
	}

	public void setContactsStatus(int contactsStatus) {
		this.contactsStatus = contactsStatus;
	}

	public TeePerson getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(TeePerson addPerson) {
		this.addPerson = addPerson;
	}

	public TeePerson getContactManagePerson() {
		return contactManagePerson;
	}

	public void setContactManagePerson(TeePerson contactManagePerson) {
		this.contactManagePerson = contactManagePerson;
	}

/*	public Attachment getAttachIds() {
		return attachIds;
	}

	public void setAttachIds(Attachment attachIds) {
		this.attachIds = attachIds;
	}*/

	public TeeCrmContacts getIntroduce() {
		return introduce;
	}

	public void setIntroduce(TeeCrmContacts introduce) {
		this.introduce = introduce;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public TeeCrmClue getClue() {
		return clue;
	}

	public void setClue(TeeCrmClue clue) {
		this.clue = clue;
	}
	
	
}
