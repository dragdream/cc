package com.tianee.oa.subsys.crm.core.customer.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;


public class TeeCrmCustomerModel extends TeeBaseModel{
	private int sid;//Sid	int	11	否	主键
	
	private String customerName;//Customer_name	Varchar(50)	255	否	客户名称
	
	private String customerNum;//客户编号
	
	private String province;//PROVINCE	Varchar(50)		是	所属省份
	
	private String provinceName;//PROVINCE	Varchar(50)		是	所属省份
	
	private String city;//CITY	Varchar(50)		是	所属城市
	
	private String cityName;//CITY	Varchar(50)		是	所属城市
	
	private String district;//county	Varchar(50)		是	所属城市
	
	private String districtName;//county	Varchar(50)		是	所属城市
	
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
	
	private String companyUrl;//Company_url	Varchar	200	是	公司网址
	
	private String companyPhone;//Company_phone	Varchar	80	是	公司电话
	
	private String addressDesc;//地图定位
	
	private String coordinate;
	
	/*private String locateInformation;//Company_url	Varchar	200	是	定位信息（存放经纬度【逗号分开】）
*/
	private int managePersonId;//Manage_person	TeePerson		否	负责人如果为空(公海客户）
	
	private String managePersonName;
	
	private String sharePersonIds;//分享人
	
	private String sharePersonNames;
	
	private String addTimeDesc;//添加时间
	
	private int customerStatus;//客户状态(是否已分配)
	
	private int dealStatus;//成交状态（是否已成交）
	private int type;//企业性质  1：客户  2：供应商
	private String unitType;//单位性质id  可配置
	private String unitTypeDesc;//单位性质名称(采用CRM系统代码表)
	
	private int addPersonId;//创建人
	
	private String addPersonName;//
	
	private int clueId;//线索外键

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

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIndustryDesc() {
		return industryDesc;
	}

	public void setIndustryDesc(String industryDesc) {
		this.industryDesc = industryDesc;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerTypeDesc() {
		return customerTypeDesc;
	}

	public void setCustomerTypeDesc(String customerTypeDesc) {
		this.customerTypeDesc = customerTypeDesc;
	}

	public String getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(String customerSource) {
		this.customerSource = customerSource;
	}

	public String getCustomerSourceDesc() {
		return customerSourceDesc;
	}

	public void setCustomerSourceDesc(String customerSourceDesc) {
		this.customerSourceDesc = customerSourceDesc;
	}

	public String getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}

	public String getCompanyScaleDesc() {
		return companyScaleDesc;
	}

	public void setCompanyScaleDesc(String companyScaleDesc) {
		this.companyScaleDesc = companyScaleDesc;
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

/*	public String getLocateInformation() {
		return locateInformation;
	}

	public void setLocateInformation(String locateInformation) {
		this.locateInformation = locateInformation;
	}*/

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

	public String getAddTimeDesc() {
		return addTimeDesc;
	}

	public void setAddTimeDesc(String addTimeDesc) {
		this.addTimeDesc = addTimeDesc;
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

	public String getAddressDesc() {
		return addressDesc;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public int getClueId() {
		return clueId;
	}

	public void setClueId(int clueId) {
		this.clueId = clueId;
	}
	
}