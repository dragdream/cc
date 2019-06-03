package com.tianee.oa.subsys.crm.core.clue.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 线索实体
 * @author zhaocaigen
 *
 */
@Entity
@Table(name="CRM_CLUE")
public class TeeCrmClue {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CLUE_seq_gen")
	@SequenceGenerator(name="CRM_CLUE_seq_gen", sequenceName="CRM_CLUE_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键
	
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID")
	private TeeCrmCustomer customer;//Customer_ID	Int	11	否	客户表外键
*/	
	@Column(name="NAME")
	private String name;  //姓名
	
	@Column(name="COMPANY_NAME")
	private String companyName;  //公司
	
	@Column(name="CULE_DETIAL")
	private String culeDetail;   //线索详情
	
	@Column(name="CLUE_SOURCE")
	private String clueSource;   //线索来源   来源于crm_code
	
	@Column(name="DEPARTMENT")
	private String department;   //部门
	
	@Column(name="DUTIES")
	private String duties;   //职务
	
	@Column(name="TELEPHONE")
	private String telephone;   //联系电话
	
	@Column(name="MOBILE_PHONE")
	private String mobilePhone;   //手机
	
	@Column(name="URL")
	private String url;  //网址
	
	@Column(name="EMAIL")
	private String email;  //邮箱
	
	@Column(name="ADDRESS")
	private String address;   //地址
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CLUE_MANAGER_USER_ID")
	private TeePerson managePerson;//    负责人
	
	@Column(name="CLUE_STATUS")
	private int clueStatus;  //线索状态（跟进中、已转换、无效）
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADD_PERSON_ID")
	private TeePerson addPerson;//创建人
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;  //创建日期
	
	@Column(name="LAST_EDIT_TIME")
	private Calendar lastEditTime;//最后变化时间
	
	@Column(name="DEAL_RESULT")
	@Lob()
	private String dealResult;    //处理结果
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCuleDetail() {
		return culeDetail;
	}

	public void setCuleDetail(String culeDetail) {
		this.culeDetail = culeDetail;
	}

	public String getClueSource() {
		return clueSource;
	}

	public void setClueSource(String clueSource) {
		this.clueSource = clueSource;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public TeePerson getManagePerson() {
		return managePerson;
	}

	public void setManagePerson(TeePerson managePerson) {
		this.managePerson = managePerson;
	}

	public int getClueStatus() {
		return clueStatus;
	}

	public void setClueStatus(int clueStatus) {
		this.clueStatus = clueStatus;
	}

	public TeePerson getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(TeePerson addPerson) {
		this.addPerson = addPerson;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Calendar getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(Calendar lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}

}
