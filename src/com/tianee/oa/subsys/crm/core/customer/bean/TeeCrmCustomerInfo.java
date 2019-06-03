package com.tianee.oa.subsys.crm.core.customer.bean;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;


@Entity
@Table(name="CRM_CUSTOMER_INFO")
public class TeeCrmCustomerInfo{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_CUSTOMER_INFO_seq_gen")
	@SequenceGenerator(name="CRM_CUSTOMER_INFO_seq_gen", sequenceName="CRM_CUSTOMER_INFO_seq")
	@Column(name="SID")
	private int sid;//Sid	int	11	否	主键
	
	@Column(name="CUSTOMER_NAME")
	private String customerName;//Customer_name	Varchar(50)	255	否	客户名称
	
	@Column(name="REAL_AREA")
	private String realArea;//REAL_AREA	Varchar(50)		是	所属区域(华中/华北地区)
	
	@Column(name="PROVINCE")
	private String province;//PROVINCE	Varchar(50)		是	所属省份
	
	@Column(name="CITY")
	private String city;//CITY	Varchar(50)		是	所属城市
	
	@Column(name="COUNTY")
	private String county;//COUNTY	Varchar(50)		是	所属城市
	
	@Column(name="INDUSTRY")
	private String industry;//Industry	Varchar	255	是	所属行业(采用CRM系统代码表)
	
	@Column(name="CUSTOMER_TYPE")
	private String customerType;//Customer_type	Varchar	255	是	客户类别(采用CRM系统代码表)
	
	@Column(name="CUSTOMER_SOURCE")
	private String customerSource;//Customer_source	Varcha	255		客户来源(采用CRM系统代码表）
	
	@Column(name="COMPANY_SCALE")
	private String companyScale;//Company_scale	Varchar	50	是	公司规模采用CRM系统代码表)
	
	@Column(name="COMPANY_ADDRESS")
	private String companyAddress;//Company_address	Varchar	255	是	公司地址
	
	@Column(name="COMPANY_ZIPCODE")
	private String companyZipCode;//Company_Zipcode	Varchar	20	是	邮编
	
	@Column(name="COMPANY_FAX")
	private String companyFax;//Company_Fax	Varchar	50	是	传真
	
	@Column(name="COMPANY_URL")
	private String companyUrl;//Company_url	Varchar	200	是	公司网址
	
	@Column(name="COMPANY_PHONE")
	private String companyPhone;//Company_phone	Varchar	80	是	公司电话
	
	@Column(name="COMPANY_MOBILE")
	private String companyMobile;//Company_ Mobile	Varchar	50	是	移动电话
	
	@Column(name="COMPANY_EMAIL")
	private String companyEmail;//Company_email	Varchar	50	是	邮件
	
	@Column(name="COMPANY_QQ")
	private String companyQQ;//Company_QQ	Varchar	50	是	QQ
	
	@Column(name="REALATION_LEVEL")
	private String relationLevel;//Relation_level	Varchar	50		关系等级（CRM系统代码表）
	
	@Column(name="IMPORTANT_LEVEL")
	private String importantLevel;//Important_level	Varchar	50		重要程度（CRM系统代码表）
	
	@Column(name="TRUST_LEVEL")
	private String trustLevel;//Trust_level	Varchar	50		信用等级（CRM系统代码表）
	
	@Column(name="SOURCE_OF_INVESTMENT")
	private String sourcesOfInvestment;//Sources_of_investmentVarchar	50		投资来源（CRM系统代码表）
	
	@Column(name="SALE_MARKET")
	private String salesMarket;//Sales_market	Varchar	50		销售市场（CRM系统代码表）
	
	@Column(name="ENTERPRISE_PRODUCT")
	private String enterprisePorduct;//Enterprise_Product	Varchar	200		公司产品
	
	@Column(name="MANAGEMENT_MODEL")
	private String managementModel;//Management_model	Varchar	200		经营模式
	
	@Column(name="ANNUAL_SALES")
	private double annualSales;//Annual_sales	double			年营业额：（万元）
	
	@Column(name="BUSINESS_PERSONNEL_NUMBER")
	private int businessPersonnelNumber;//Business_personnel_number	Int			业务人员数
	
	@Column(name="EMPLOYEE_NUMBER")
	private int employeeNumber;//Employee_number	Int			员工人数
	
	@Column(name="REGISTERED_CAPITAL")
	private double registeredCapital;//Registered_capital	double			注册资本（万元）
	
	@Column(name="LEGAL_REPRESENTATIVE")
	private String legalRepresentative;//Legal_representative	Varchar	50		法定代表人
	
	@Column(name="COMPANY_CREATETIME")
	private Calendar companyCreateTime;//Company_create_time	date			公司成立时间
	
	@Column(name="PRODUCTION_CAPACITY",columnDefinition="char(1)")
	private String productionCapacity;//Production_capacity	char	1		生产能力 0-有1-无
	
	@Column(name="DEVELOPMENT_POTENTIAL")
	private String developmentPotential;//Development_potential	Varchar	50		发展潜力(CRM系统代码表)
	
	@Column(name="BILL_UNIT_NAME")
	private String billUnitName;//BILL_UNIT_NAME	VARCHAR	255	是	单位名称
	
	@Column(name="BILL_UNIT_ADDRESS")
	private String billUnitAddress;//BILL_UNIT_ADDRESS	VARCHAR	255	是	单位地址
	
	@Column(name="BANK_NAME")
	private String bankName;//Bank_name	Varchar	255	是	开户银行
	
	@Column(name="BANK_ACCOUNT")
	private String bankAccount;//Bank_account	Varchar	255	是	银行账号
	
	@Column(name="TAX_NO")
	private String taxNo;//Tax_NO	Varchar	255	是	税号
	
	@Column(name="BILL_PHONE")
	private String billPhone;//BILL_PHONE	Varchar	50	是	开票联系电话
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX460d007b9bf442278e9d2d0166a")
	@JoinColumn(name="MANAGER_USER_ID")
	private TeePerson managePerson;//Manage_person	TeePerson		否	负责人如果为空(公海客户）
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<TeePerson> sharePerson = new HashSet<TeePerson>(0);//Share_person	SET< TeePerson >			共享人员
	
	@Column(name="ADD_TIME")
	private Calendar addTime;//录入时间
	
	@Column(name="REMARK")
	@Lob()
	private String remark;//Remark	Varchar	255	是	备注
	
	@Column(name="TYPE")
	private int type;//客户性质  1：客户  2：供应商
	
	@Column(name="UNIT_TYPE")
	private String unitType;//单位性质  可配置
	
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
	public String getRealArea() {
		return realArea;
	}
	public void setRealArea(String realArea) {
		this.realArea = realArea;
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
	
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
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
	public String getCompanyScale() {
		return companyScale;
	}
	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyZipCode() {
		return companyZipCode;
	}
	public void setCompanyZipCode(String companyZipCode) {
		this.companyZipCode = companyZipCode;
	}
	public String getCompanyFax() {
		return companyFax;
	}
	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}
	public String getCompanyUrl() {
		return companyUrl;
	}
	public void setCompanyUrl(String companyUrl) {
		this.companyUrl = companyUrl;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyMobile() {
		return companyMobile;
	}
	public void setCompanyMobile(String companyMobile) {
		this.companyMobile = companyMobile;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	public String getCompanyQQ() {
		return companyQQ;
	}
	public void setCompanyQQ(String companyQQ) {
		this.companyQQ = companyQQ;
	}
	public String getRelationLevel() {
		return relationLevel;
	}
	public void setRelationLevel(String relationLevel) {
		this.relationLevel = relationLevel;
	}
	public String getImportantLevel() {
		return importantLevel;
	}
	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}
	public String getTrustLevel() {
		return trustLevel;
	}
	public void setTrustLevel(String trustLevel) {
		this.trustLevel = trustLevel;
	}
	public String getSourcesOfInvestment() {
		return sourcesOfInvestment;
	}
	public void setSourcesOfInvestment(String sourcesOfInvestment) {
		this.sourcesOfInvestment = sourcesOfInvestment;
	}
	public String getSalesMarket() {
		return salesMarket;
	}
	public void setSalesMarket(String salesMarket) {
		this.salesMarket = salesMarket;
	}
	public String getEnterprisePorduct() {
		return enterprisePorduct;
	}
	public void setEnterprisePorduct(String enterprisePorduct) {
		this.enterprisePorduct = enterprisePorduct;
	}
	public String getManagementModel() {
		return managementModel;
	}
	public void setManagementModel(String managementModel) {
		this.managementModel = managementModel;
	}
	public double getAnnualSales() {
		return annualSales;
	}
	public void setAnnualSales(double annualSales) {
		this.annualSales = annualSales;
	}
	public int getBusinessPersonnelNumber() {
		return businessPersonnelNumber;
	}
	public void setBusinessPersonnelNumber(int businessPersonnelNumber) {
		this.businessPersonnelNumber = businessPersonnelNumber;
	}
	public int getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public double getRegisteredCapital() {
		return registeredCapital;
	}
	public void setRegisteredCapital(double registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public Calendar getCompanyCreateTime() {
		return companyCreateTime;
	}
	public void setCompanyCreateTime(Calendar companyCreateTime) {
		this.companyCreateTime = companyCreateTime;
	}
	public String getProductionCapacity() {
		return productionCapacity;
	}
	public void setProductionCapacity(String productionCapacity) {
		this.productionCapacity = productionCapacity;
	}
	public String getDevelopmentPotential() {
		return developmentPotential;
	}
	public void setDevelopmentPotential(String developmentPotential) {
		this.developmentPotential = developmentPotential;
	}
	public String getBillUnitName() {
		return billUnitName;
	}
	public void setBillUnitName(String billUnitName) {
		this.billUnitName = billUnitName;
	}
	public String getBillUnitAddress() {
		return billUnitAddress;
	}
	public void setBillUnitAddress(String billUnitAddress) {
		this.billUnitAddress = billUnitAddress;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	public String getBillPhone() {
		return billPhone;
	}
	public void setBillPhone(String billPhone) {
		this.billPhone = billPhone;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Calendar getAddTime() {
		return addTime;
	}
	public void setAddTime(Calendar addTime) {
		this.addTime = addTime;
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
	
}
