package com.beidasoft.xzfy.caseTrial.caseDiscussion.bean;
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
@Table(name="FY_DISCUSSION")
public class Discussion {
	
	//uuid
	@Id
	@Column(name="ID")
	/*@GeneratedValue(generator = "id")    
	@GenericGenerator(name = "id", strategy = "uuid")*/
	private String id;
	
	//案件id
	@Column(name="CASE_ID")
	private String caseId;
	
	//会议类别代码
	@Column(name="DISCUSSION_TYPE_CODE")
	private String discussionTypeCode;
	
	//会议类别
	@Column(name="DISCUSSION_TYPE")
	private String discussionType;
	
	//会议名称
	@Column(name="DISCUSSION_NAME")
	private String discussionName;
	
	//会议负责人
	@Column(name="PRESENTER")
	private String presenter;
	
	//会议记录人
	@Column(name="RECORDMAN")
	private String recordMan;
	
	//会议时间
	@Column(name="DISCUSSION_DATE")
	private String discussionDate;
	
	//会议地点
	@Column(name="PLACE")
	private String place;
	
	//参与人员
	@Column(name="PARTICIPANT")
	private String participant;
	
	//会议内容
	@Column(name="CONTENT")
	private String content;
	
	//审查意见(讨论结果)
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

	public String getDiscussionTypeCode() {
		return discussionTypeCode;
	}

	public void setDiscussionTypeCode(String discussionTypeCode) {
		this.discussionTypeCode = discussionTypeCode;
	}

	public String getDiscussionType() {
		return discussionType;
	}

	public void setDiscussionType(String discussionType) {
		this.discussionType = discussionType;
	}

	public String getDiscussionName() {
		return discussionName;
	}

	public void setDiscussionName(String discussionName) {
		this.discussionName = discussionName;
	}

	public String getPresenter() {
		return presenter;
	}

	public void setPresenter(String presenter) {
		this.presenter = presenter;
	}

	public String getDiscussionDate() {
		return discussionDate;
	}

	public void setDiscussionDate(String discussionDate) {
		this.discussionDate = discussionDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getRecordMan() {
		return recordMan;
	}

	public void setRecordMan(String recordMan) {
		this.recordMan = recordMan;
	}
	
	
}
