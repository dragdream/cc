package com.beidasoft.xzfy.caseTrial.caseHearing.bean;
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
@Table(name="FY_HEARING")
public class Hearing {
	
	//uuid
	@Id
	@Column(name="ID")
	/*@GeneratedValue(generator = "id")    
	@GenericGenerator(name = "id", strategy = "uuid")*/
	private String id;
	
	//案件id
	@Column(name="CASE_ID")
	private String caseId;
	
	//听证人
	@Column(name="HEARINGWITNESS")
	private String hearingWitness;
	
	//主持人
	@Column(name="PRESENTER")
	private String presenter;
	
	//开始时间
	@Column(name="START_TIME")
	private String startTime;
	
	//结束时间
	@Column(name="END_TIME")
	private String endTime;
	
	//记录人
	@Column(name="CLERK")
	private String clerk;
	
	//听证参加人
	@Column(name="PARTICIPANT")
	private String participant;
	
	//告知权利与义务
	@Column(name="NOTICE")
	private String notice;
	
	//是否申请回避
	@Column(name="AVOID")
	private String avoid;
	
	//被申请人阐述
	@Column(name="RESPONDENTEXPOSITION")
	private String respondentExposition;
	
	//申请人阐述
	@Column(name="APPLICANTEXPLANATION")
	private String applicantExplanation;
	
	//辩论
	@Column(name="DEBATE")
	private String debate;
	
	//质证
	@Column(name="WITNESS")
	private String witness;
	
	//审查意见
	@Column(name="RESULT")
	private String result;
	
	//听证地点
	@Column(name="PLACE")
	private String place;
	
	//听证内容
	@Column(name="CONTENT")
	private String content;
	
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

	public String getHearingWitness() {
		return hearingWitness;
	}

	public void setHearingWitness(String hearingWitness) {
		this.hearingWitness = hearingWitness;
	}

	public String getPresenter() {
		return presenter;
	}

	public void setPresenter(String presenter) {
		this.presenter = presenter;
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

	public String getClerk() {
		return clerk;
	}

	public void setClerk(String clerk) {
		this.clerk = clerk;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getAvoid() {
		return avoid;
	}

	public void setAvoid(String avoid) {
		this.avoid = avoid;
	}

	public String getRespondentExposition() {
		return respondentExposition;
	}

	public void setRespondentExposition(String respondentExposition) {
		this.respondentExposition = respondentExposition;
	}

	public String getApplicantExplanation() {
		return applicantExplanation;
	}

	public void setApplicantExplanation(String applicantExplanation) {
		this.applicantExplanation = applicantExplanation;
	}

	public String getDebate() {
		return debate;
	}

	public void setDebate(String debate) {
		this.debate = debate;
	}

	public String getWitness() {
		return witness;
	}

	public void setWitness(String witness) {
		this.witness = witness;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
