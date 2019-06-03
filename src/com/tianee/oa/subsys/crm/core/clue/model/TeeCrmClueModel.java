package com.tianee.oa.subsys.crm.core.clue.model;


import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeCrmClueModel extends TeeBaseModel {
	private int sid;//	int	11	否	主键
	
	private String name;  //线索名字
	
	private String companyName;  //公司
	
	private String culeDetail;   //线索详情
	
	private String clueSource;   //线索来源   来源于crm_code
	
	private String clueSourceDesc;   //线索来源  
	
	private String department;   //部门
	
	private String duties;   //职务
	
	private String telephone;   //联系电话
	
	private String mobilePhone;   //手机
	
	private String url;  //网址
	
	private String email;  //邮箱
	
	private String address;   //地址
	
	private int managePersonId;  //负责人
	
	private String managePersonName;
	
	private int clueStatus;  //线索状态
	
	private String clueStatusDesc;
	
	private int addPersonId;//创建人
	
	private String addPersonName;//
	
	private String createTimeDesc;//创建时间
	
	private String lastEditTimeDesc;//最后变化时间
	
	private String dealResult;    //处理结果
	
	private String customerName; //客户名称
	
	private String contactName; //联系人名称
	
	private String chanceName; //商机名称

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

	public String getClueSourceDesc() {
		return clueSourceDesc;
	}

	public void setClueSourceDesc(String clueSourceDesc) {
		this.clueSourceDesc = clueSourceDesc;
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

	public int getClueStatus() {
		return clueStatus;
	}

	public void setClueStatus(int clueStatus) {
		this.clueStatus = clueStatus;
	}

	public String getClueStatusDesc() {
		return clueStatusDesc;
	}

	public void setClueStatusDesc(String clueStatusDesc) {
		this.clueStatusDesc = clueStatusDesc;
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

	public String getLastEditTimeDesc() {
		return lastEditTimeDesc;
	}

	public void setLastEditTimeDesc(String lastEditTimeDesc) {
		this.lastEditTimeDesc = lastEditTimeDesc;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
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

	public String getChanceName() {
		return chanceName;
	}

	public void setChanceName(String chanceName) {
		this.chanceName = chanceName;
	}
	
	
}
