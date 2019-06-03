package com.tianee.oa.subsys.crm.core.customer.bean;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.crm.core.clue.bean.TeeCrmClue;

/**
 * 客户实体
 * @author zhaocaigen
 *
 */
@Entity
@Table(name="CRM_CUSTOMER")
public class TeeCrmCustomer{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CUSTOMER_seq_gen")
	@SequenceGenerator(name="CRM_CUSTOMER_seq_gen", sequenceName="CRM_CUSTOMER_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键
	
	@Column(name="CUSTOMER_NAME")
	private String customerName;//Customer_name	Varchar(50)	255	否	客户名称
	
	@Column(name="CUSTOMER_NUM")
	private String customerNum;//客户编号
	
	@Column(name="CUSTOMER_TYPE")
	private String customerType;//Customer_type	Varchar	255	是	客户类别(采用CRM系统代码表)
	
	@Column(name="CUSTOMER_SOURCE")
	private String customerSource;//Customer_source	Varcha	255		客户来源(采用CRM系统代码表）
	
	@Column(name="PROVINCE")
	private String province;//PROVINCE	Varchar(50)		是	所属区域-省
	
	@Column(name="CITY")
	private String city;//CITY	Varchar(50)		是	所属区域-市
	
	@Column(name="DISTRICT")
	private String district;//CITY	Varchar(50)		是	所属区域-区
	
	/*@Column(name="ADDRESS")
	private String detailAddress;//CITY	Varchar(50)		详细地址
*/	
	@Column(name="INDUSTRY_TYPE")
	private String industry;//Industry	Varchar	255	是	所属行业(采用CRM系统代码表)
	
	@Column(name="COMPANY_SCALE")
	private String companyScale;//Company_scale	Varchar	50	是	公司规模采用CRM系统代码表)
	
	@Column(name="TYPE")
	private int type;//客户性质  1：客户  2：供应商
	
	@Column(name="UNIT_TYPE")
	private String unitType;//单位性质  (采用CRM系统代码表)
	
	@Column(name="COMPANY_ADDRESS")
	private String companyAddress;//Company_address	Varchar	255	是	公司地址
	
	@Column(name="COMPANY_PHONE")
	private String companyPhone;//Company_phone	Varchar	80	是	联系电话
	
	@Column(name="COMPANY_ZIPCODE")
	private String companyZipCode;//Company_Zipcode	Varchar	20	是	邮编
	
	@Column(name="COMPANY_URL")
	private String companyUrl;//Company_url	Varchar	200	是	公司网址

	@Column(name="LOCATE_INFORMATION")
	private String locateInformation;//Company_url	Varchar	200	是	定位信息（存放经纬度【逗号分开】）

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MANAGER_USER_ID")
	private TeePerson managePerson;//Manage_person	TeePerson  否	负责人如果为空(公海客户）
	
	@ManyToMany(targetEntity=TeePerson.class,fetch = FetchType.LAZY  ) 
	@JoinTable(name="CUSTOMER_PERSONS",
			joinColumns={@JoinColumn(name="CUSTOMER_ID")},       
        inverseJoinColumns={@JoinColumn(name="PERSON_ID")}  ) 	
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	private Set<TeePerson> sharePerson = new HashSet<TeePerson>(0);//Share_person	SET< TeePerson >共享人员
	
	@Column(name="CUSTOMER_STATUS")
	private int customerStatus;//客户状态(是否已分配)  -1=未分配  1=已分配   2=已作废
	
	@Column(name="DEAL_STATUS")
	private int dealStatus;//成交状态（是否已成交）
	
	@Column(name="ADD_TIME")
	private Calendar addTime;//录入时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ADD_PERSON_ID")
	private TeePerson addPerson;//创建人
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="CLUE_ID") //线索外键
	private TeeCrmClue clue;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	/*public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}*/

	public String getCompanyScale() {
		return companyScale;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyZipCode() {
		return companyZipCode;
	}

	public void setCompanyZipCode(String companyZipCode) {
		this.companyZipCode = companyZipCode;
	}

	public String getCompanyUrl() {
		return companyUrl;
	}

	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}

	public String getLocateInformation() {
		return locateInformation;
	}

	public void setLocateInformation(String locateInformation) {
		this.locateInformation = locateInformation;
	}

	public TeePerson getManagePerson() {
		return managePerson;
	}

	public void setManagePerson(TeePerson managePerson) {
		this.managePerson = managePerson;
	}

	public Set<TeePerson> getSharePerson() {
		return sharePerson;
	}

	public void setSharePerson(Set<TeePerson> sharePerson) {
		this.sharePerson = sharePerson;
	}

	public int getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(int customerStatus) {
		this.customerStatus = customerStatus;
	}

	public int getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(int dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Calendar getAddTime() {
		return addTime;
	}

	public void setAddTime(Calendar addTime) {
		this.addTime = addTime;
	}

	public TeePerson getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(TeePerson addPerson) {
		this.addPerson = addPerson;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public TeeCrmClue getClue() {
		return clue;
	}

	public void setClue(TeeCrmClue clue) {
		this.clue = clue;
	}
	
}
