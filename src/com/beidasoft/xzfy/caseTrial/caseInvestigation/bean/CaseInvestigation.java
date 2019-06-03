package com.beidasoft.xzfy.caseTrial.caseInvestigation.bean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**   
 * Description:案件调查管理
 * @author ZCK
 * @version 0.1 2019年4月23日
 */
@Entity
@Table(name="FY_INVESTIGATION")
public class CaseInvestigation {
	
	//uuid
	@Id
	@Column(name="ID")
	/*@GeneratedValue(generator = "id")    
	@GenericGenerator(name = "id", strategy = "uuid")*/
	private String id;
	
	//案件id
	@Column(name="CASE_ID")
	private String caseId;
	
	//调查人姓名
	@Column(name="INVEST_NAME")
	private String investName;
	
	//被调查人性别
	@Column(name="RESPONDENTSEX")
	private String respondentSex;
	
	//被调查人
	@Column(name="RESPONDENT")
	private String respondent;
	
	//被调查人电话
	@Column(name="RESPONDENT_PHONENUM")
	private String respondentPhoneNum;
	
	//取证方式
	@Column(name="INVEST_TYPE")
	private String investType;
	
	//取证方式CODE
	@Column(name="INVEST_TYPE_CODE")
	private String investTypeCode;
	
	//调查地址
	@Column(name="INVEST_PLACE")
	private String investPlace;
	
	//调查开始时间
	@Column(name="START_TIME")
	private String startTime;
	
	//调查结束时间
	@Column(name="END_TIME")
	private String endTime;
	
	//告知权利与义务
	@Column(name="NOTICE")
	private String notice;
	
	//调查情况 
	@Column(name="INVEST_DETAIL")
	private String investDetail;
	
	//审查意见 
	@Column(name="RESULT")
	private String result;
	
	//添加人id 
	@Column(name="CREATED_USER_ID")
	private String createdUserId;
	
	//添加人
	@Column(name="CREATED_USER")
	private String createdUser;
	
	//添加时间 
	@Column(name="CREATED_TIME")
	private String createdTime;
	
	//修改人id
	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;
	
	//修改人
	@Column(name="MODIFIED_USER")
	private String modifiedUser;
	
	//修改时间 
	@Column(name="MODIFIED_TIME")
	private String modifiedTime;
	
	//Isdelete
	@Column(name="IS_DELETE")
	private int isDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getInvestName() {
		return investName;
	}

	public void setInvestName(String investName) {
		this.investName = investName;
	}

	public String getRespondentSex() {
		return respondentSex;
	}

	public void setRespondentSex(String respondentSex) {
		this.respondentSex = respondentSex;
	}

	public String getRespondent() {
		return respondent;
	}

	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}

	public String getRespondentPhoneNum() {
		return respondentPhoneNum;
	}

	public void setRespondentPhoneNum(String respondentPhoneNum) {
		this.respondentPhoneNum = respondentPhoneNum;
	}

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public String getInvestTypeCode() {
		return investTypeCode;
	}

	public void setInvestTypeCode(String investTypeCode) {
		this.investTypeCode = investTypeCode;
	}

	public String getInvestPlace() {
		return investPlace;
	}

	public void setInvestPlace(String investPlace) {
		this.investPlace = investPlace;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getInvestDetail() {
		return investDetail;
	}

	public void setInvestDetail(String investDetail) {
		this.investDetail = investDetail;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
	
			
}
