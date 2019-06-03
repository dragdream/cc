package com.tianee.oa.subsys.crm.core.customer.model;

import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;



public class TeeCrmSaleFollowModel extends TeeBaseModel{
	private int sid;//Sid	int	11	否	主键
	
	private int customerId;//Custom_id	int	1	否	外键（所属客户id）
	
	private String customerName;
	
	private String customType;//Custom_type	varchar	255	是	客户类型
	
	private int addPersonId;//Add_person_id	Int	11	否	添加人id Add_person_name	Int	11	否	添加人姓名
	
	private String addPersonName;//添加用户名
	
	private String customLevel;//Custom_level	Varchar	255	是	客户程度
	
	private int contantsId;//Contants_id	Int	11	否	联系人Id Contants_name	Varchar	255	否	联系人name
	
	private String contantsName;//联系人名称
	
	private String followType;//Follow_type	Varchar	255	否	跟踪方式（电话，qq,书函，面谈，会议）
	
	private String followTypeDesc;//跟踪方式
	
	private String followContent;//Follow_content	Varchar	2000	否	跟踪内容
	
	private String followDateDesc;//Follow_date	Calendar		否	跟踪时间
	
	private String followResult;//Follow_result	Varchar	2000	是	跟踪结果
	
	private String followResultDesc;//Follow_result	Varchar	2000	是	跟踪结果
	
	private List<TeeAttachmentModel> attacheModels;//附件
	
	private String nextFollowTimeDesc;//Next_follow_time	Calendar		是	下次跟踪时间
	
	private int nextFollowUserId;//下次跟踪人id(联系人id)
	
	private String nextFollowUserName;//下次跟踪人姓名（联系人name)
	
	private String nextFollowContent;//下次跟踪内容
	
	private char isRemind;//Is_remind	Char	1	是	是否提醒
	
	
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getCustomType() {
		return customType;
	}
	public void setCustomType(String customType) {
		this.customType = customType;
	}
	public String getCustomLevel() {
		return customLevel;
	}
	public void setCustomLevel(String customLevel) {
		this.customLevel = customLevel;
	}
	public String getFollowType() {
		return followType;
	}
	public void setFollowType(String followType) {
		this.followType = followType;
	}
	public String getFollowContent() {
		return followContent;
	}
	public void setFollowContent(String followContent) {
		this.followContent = followContent;
	}
	public String getFollowResult() {
		return followResult;
	}
	public void setFollowResult(String followResult) {
		this.followResult = followResult;
	}
	public char getIsRemind() {
		return isRemind;
	}
	public void setIsRemind(char isRemind) {
		this.isRemind = isRemind;
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
	public int getContantsId() {
		return contantsId;
	}
	public void setContantsId(int contantsId) {
		this.contantsId = contantsId;
	}
	public String getFollowDateDesc() {
		return followDateDesc;
	}
	public void setFollowDateDesc(String followDateDesc) {
		this.followDateDesc = followDateDesc;
	}
	public String getNextFollowTimeDesc() {
		return nextFollowTimeDesc;
	}
	public void setNextFollowTimeDesc(String nextFollowTimeDesc) {
		this.nextFollowTimeDesc = nextFollowTimeDesc;
	}
	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}
	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}
	public int getNextFollowUserId() {
		return nextFollowUserId;
	}
	public void setNextFollowUserId(int nextFollowUserId) {
		this.nextFollowUserId = nextFollowUserId;
	}
	public String getNextFollowUserName() {
		return nextFollowUserName;
	}
	public void setNextFollowUserName(String nextFollowUserName) {
		this.nextFollowUserName = nextFollowUserName;
	}
	public String getContantsName() {
		return contantsName;
	}
	public void setContantsName(String contantsName) {
		this.contantsName = contantsName;
	}
	public String getFollowTypeDesc() {
		return followTypeDesc;
	}
	public void setFollowTypeDesc(String followTypeDesc) {
		this.followTypeDesc = followTypeDesc;
	}
	public String getNextFollowContent() {
		return nextFollowContent;
	}
	public void setNextFollowContent(String nextFollowContent) {
		this.nextFollowContent = nextFollowContent;
	}
	public String getFollowResultDesc() {
		return followResultDesc;
	}
	public void setFollowResultDesc(String followResultDesc) {
		this.followResultDesc = followResultDesc;
	}

	
}