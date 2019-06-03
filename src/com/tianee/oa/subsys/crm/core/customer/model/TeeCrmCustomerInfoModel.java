package com.tianee.oa.subsys.crm.core.customer.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeCrmCustomerInfoModel extends TeeBaseModel{
	private int sid;//Sid	int	11	否	主键
	
	private String customerName;//Customer_name	Varchar(50)	255	否	客户名称
	
	private String realArea;//REAL_AREA	Varchar(50)		是	所属区域(华中/华北地区)
	
	private String province;//PROVINCE	Varchar(50)		是	所属省份
	
	private String provinceName;//PROVINCE	Varchar(50)		是	所属省份
	
	private String city;//CITY	Varchar(50)		是	所属城市
	
	private String cityName;//CITY	Varchar(50)		是	所属城市
	
	private String county;//county	Varchar(50)		是	所属城市
	
	private String countyName;//county	Varchar(50)		是	所属城市
	
	private String industry;//Industry	Varchar	255	是	所属行业(采用CRM系统代码表)
	
	private String industryDesc;//Industry	Varchar	255	是	所属行业(采用CRM系统代码表)
	
	private String customerType;//Customer_type	Varchar	255	是	客户类别(采用CRM系统代码表)
	
	private String customerTypeDesc;//Customer_type	Varchar	255	是	客户类别(采用CRM系统代码表)
	
	private String customerSource;//Customer_source	Varcha	255		客户来源(采用CRM系统代码表）
	
	private String customerSourceDesc;//Customer_source	Varcha	255		客户来源(采用CRM系统代码表）
	
	private String companyScale;//Company_scale	Varchar	50	是	公司规模采用CRM系统代码表)
	
	private String companyScaleDesc;//Company_scale	Varchar	50	是	公司规模采用CRM系统代码表)
	
	private String companyAddress;//Company_address	Varchar	255	是	公司地址
	
	private String companyZipCode;//Company_Zipcode	Varchar	20	是	邮编
	
	private String companyFax;//Company_Fax	Varchar	50	是	传真
	
	private String companyUrl;//Company_url	Varchar	200	是	公司网址
	
	private String companyPhone;//Company_phone	Varchar	80	是	公司电话
	
	private String companyMobile;//Company_ Mobile	Varchar	50	是	移动电话
	
	private String companyEmail;//Company_email	Varchar	50	是	邮件
	
	private String companyQQ;//Company_QQ	Varchar	50	是	QQ
	
	private String relationLevel;//Relation_level	Varchar	50		关系等级（CRM系统代码表）
	
	private String relationLevelDesc;//Relation_level	Varchar	50		关系等级（CRM系统代码表）
	
	private String importantLevel;//Important_level	Varchar	50		重要程度（CRM系统代码表）
	
	private String importantLevelDesc;//Important_level	Varchar	50		重要程度（CRM系统代码表）
	
	private String trustLevel;//Trust_level	Varchar	50		信用等级（CRM系统代码表）
	
	private String trustLevelDesc;
	
	private String sourcesOfInvestment;//Sources_of_investmentVarchar	50		投资来源（CRM系统代码表）
	
	private String sourcesOfInvestmentDesc;//Sources_of_investmentVarchar	50		投资来源（CRM系统代码表）
	
	private String salesMarket;//Sales_market	Varchar	50		销售市场（CRM系统代码表）
	
	private String salesMarketDesc;//Sales_market	Varchar	50		销售市场（CRM系统代码表）
	
	private String enterprisePorduct;//Enterprise_Product	Varchar	200		公司产品
	
	private String managementModel;//Management_model	Varchar	200		经营模式
	
	private double annualSales;//Annual_sales	double			年营业额：（万元）
	
	private int businessPersonnelNumber;//Business_personnel_number	Int			业务人员数
	
	private int employeeNumber;//Employee_number	Int			员工人数
	
	private double registeredCapital;//Registered_capital	double			注册资本（万元）
	
	private String legalRepresentative;//Legal_representative	Varchar	50		法定代表人
	
	private String companyCreateTimeDesc;//Company_create_time	date			公司成立时间
	
	private String productionCapacity;//Production_capacity	char	1		生产能力 0-有1-无
	
	private String developmentPotential;//Development_potential	Varchar	50		发展潜力(CRM系统代码表)
	
	private String developmentPotentialDesc;//Development_potential	Varchar	50		发展潜力(CRM系统代码表)
	
	private String billUnitName;//BILL_UNIT_NAME	VARCHAR	255	是	单位名称
	
	private String billUnitAddress;//BILL_UNIT_ADDRESS	VARCHAR	255	是	单位地址
	
	private String bankName;//Bank_name	Varchar	255	是	开户银行
	
	private String bankAccount;//Bank_account	Varchar	255	是	银行账号
	
	private String taxNo;//Tax_NO	Varchar	255	是	税号
	
	private String billPhone;//BILL_PHONE	Varchar	50	是	开票联系电话
	
	private int managePersonId;//Manage_person	TeePerson		否	负责人如果为空(公海客户）
	
	private String managePersonName;
	
	private String sharePersonIds;
	
	private String sharePersonNames;
	
	private String addTimeDesc;//添加时间
	
	private String remark;//Remark	Varchar	255	是	备注
	
	private int type;//企业性质  1：客户  2：供应商
	private String unitType;//单位性质id  可配置
	private String unitTypeDesc;//单位性质名称(采用CRM系统代码表)
	
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
	
	public String getTrustLevelDesc() {
		return trustLevelDesc;
	}
	public void setTrustLevelDesc(String trustLevelDesc) {
		this.trustLevelDesc = trustLevelDesc;
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
	
	public String getCompanyCreateTimeDesc() {
		return companyCreateTimeDesc;
	}
	public void setCompanyCreateTimeDesc(String companyCreateTimeDesc) {
		this.companyCreateTimeDesc = companyCreateTimeDesc;
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
	public String getSharePersonIds() {
		return sharePersonIds;
	}
	public void setSharePersonIds(String sharePersonIds) {
		this.sharePersonIds = sharePersonIds;
	}
	public String getSharePersonNames() {
		return sharePersonNames;
	}
	public void setSharePersonNames(String sharePersonNames) {
		this.sharePersonNames = sharePersonNames;
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
	
	public String getRemark() {
		return remark;
	}
	public String getAddTimeDesc() {
		return addTimeDesc;
	}
	public void setAddTimeDesc(String addTimeDesc) {
		this.addTimeDesc = addTimeDesc;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIndustryDesc() {
		return industryDesc;
	}
	public void setIndustryDesc(String industryDesc) {
		this.industryDesc = industryDesc;
	}
	public String getCustomerTypeDesc() {
		return customerTypeDesc;
	}
	public void setCustomerTypeDesc(String customerTypeDesc) {
		this.customerTypeDesc = customerTypeDesc;
	}
	public String getCustomerSourceDesc() {
		return customerSourceDesc;
	}
	public void setCustomerSourceDesc(String customerSourceDesc) {
		this.customerSourceDesc = customerSourceDesc;
	}
	public String getCompanyScaleDesc() {
		return companyScaleDesc;
	}
	public void setCompanyScaleDesc(String companyScaleDesc) {
		this.companyScaleDesc = companyScaleDesc;
	}
	public String getRelationLevelDesc() {
		return relationLevelDesc;
	}
	public void setRelationLevelDesc(String relationLevelDesc) {
		this.relationLevelDesc = relationLevelDesc;
	}
	public String getImportantLevelDesc() {
		return importantLevelDesc;
	}
	public void setImportantLevelDesc(String importantLevelDesc) {
		this.importantLevelDesc = importantLevelDesc;
	}
	public String getSourcesOfInvestmentDesc() {
		return sourcesOfInvestmentDesc;
	}
	public void setSourcesOfInvestmentDesc(String sourcesOfInvestmentDesc) {
		this.sourcesOfInvestmentDesc = sourcesOfInvestmentDesc;
	}
	public String getSalesMarketDesc() {
		return salesMarketDesc;
	}
	public void setSalesMarketDesc(String salesMarketDesc) {
		this.salesMarketDesc = salesMarketDesc;
	}
	public String getDevelopmentPotentialDesc() {
		return developmentPotentialDesc;
	}
	public void setDevelopmentPotentialDesc(String developmentPotentialDesc) {
		this.developmentPotentialDesc = developmentPotentialDesc;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
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
	public String getUnitTypeDesc() {
		return unitTypeDesc;
	}
	public void setUnitTypeDesc(String unitTypeDesc) {
		this.unitTypeDesc = unitTypeDesc;
	}
	
	
}