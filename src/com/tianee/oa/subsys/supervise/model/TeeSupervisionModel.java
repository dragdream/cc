package com.tianee.oa.subsys.supervise.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.supervise.bean.TeeSupervisionType;

public class TeeSupervisionModel {
	
	private int sid;//自增id
	
	private String supName ;//名称
	
	//private TeeSupervisionType type;//类型
	private String typeName;
	
	private int typeId;
	
	//private TeePerson leader;//责任领导
	private int leaderId;
	
	private String leaderName;
	
	//private TeePerson manager;//主办人
	
	private int managerId;
	
	private String managerName;
	
	private String managerNameAndDept;
	
	//private Set<TeePerson> assists = new HashSet<TeePerson>(0);//协办人
	
	private String assistNames;//协办人姓名
	
	private String assistNamesAndDept;
	
	private String assistIds;//协办人主键
	
	private String beginTimeStr;//开始时间
	
	private String endTimeStr;//结束时间
	
	private String content;//督办内容
	
	//private TeePerson creater;//创建人
	private String createrName;
	private int createrId;
	
	private String createrTimeStr;//创建时间
	
	private List<Map> attachmentsMode;//存放附件
	
	
	
	private String parentName;//父任务名称
	
	private int parentId;//父任务主键
	
	
	
	
	public String getManagerNameAndDept() {
		return managerNameAndDept;
	}

	public void setManagerNameAndDept(String managerNameAndDept) {
		this.managerNameAndDept = managerNameAndDept;
	}

	public String getAssistNamesAndDept() {
		return assistNamesAndDept;
	}

	public void setAssistNamesAndDept(String assistNamesAndDept) {
		this.assistNamesAndDept = assistNamesAndDept;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public List<Map> getAttachmentsMode() {
		return attachmentsMode;
	}

	public void setAttachmentsMode(List<Map> attachmentsMode) {
		this.attachmentsMode = attachmentsMode;
	}

	private int status;
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getAssistNames() {
		return assistNames;
	}

	public void setAssistNames(String assistNames) {
		this.assistNames = assistNames;
	}

	public String getAssistIds() {
		return assistIds;
	}

	public void setAssistIds(String assistIds) {
		this.assistIds = assistIds;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public String getCreaterTimeStr() {
		return createrTimeStr;
	}

	public void setCreaterTimeStr(String createrTimeStr) {
		this.createrTimeStr = createrTimeStr;
	}
}
