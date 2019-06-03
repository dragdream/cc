package com.tianee.oa.sync.log.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 外部系统同步日志表
 * @author ZATP18070502
 *
 */
@Entity
@Table(name="system_sync_log")
public class TeeOutSystemSyncLog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="system_log_seq_gen")
	@SequenceGenerator(name="system_log_seq_gen", sequenceName="system_log_seq")
	@Column(name="SID")
	private int sid;//主键
	
	@Column(name="USER_NAME")
	private String userName;//用户名
	
	@Column(name="UUID")
	private String uuid;//本地主键
	
	@Column(name="UNIQUE_ID")
	private String uniqueId;//唯一标识 系统对接
	
	@Column(name="USER_ID")
	private String userId;//用户英文名

	/*@Column(name="DEPT_ID")
	private int deptId;//部门id
*/	
	@Column(name="DEPT_NAME")
	private String deptName;//部门名称
	
	/*@Column(name="DEPT_PARENT_ID")
	private int deptParentId;//父级部门id
*/	
	/*@Column(name="ROLE_ID")
	private int roleId;//角色id
*/	
	@Column(name="EMAIL")
	private String email;//email
	
	@Column(name="PHONE_NUM")
	private String phoneNum;//手机号
	
	@Column(name="SUBMIT_USER_ID")
	private int submitUserId;//提交人id
	
	@Column(name="SUBMIT_USER_NAME")
	private String submitUserName;//提交人姓名
	
	@Column(name="OPERATION")
	private String operation;//操作：0 ：新增人员 1： 修改人员 2：删除人员 3：新增部门 4：修改部门 5：删除部门
	
	@Column(name="CREATE_TIME")
	private Calendar crTime;//创建时间
	
	@Column(name="CONFIG_ID")
	private int configId;//外部系统配置Id
	
	@Column(name="REQUEST_JSON")
	private String requestJson;//数据载体
	
	@Column(name="SYNC_FLAG")
	private String syncFlag;//同步标识 0：未同步 1：已同步
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}*/

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/*public int getDeptParentId() {
		return deptParentId;
	}

	public void setDeptParentId(int deptParentId) {
		this.deptParentId = deptParentId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}*/

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}

	public int getSubmitUserId() {
		return submitUserId;
	}

	public void setSubmitUserId(int submitUserId) {
		this.submitUserId = submitUserId;
	}

	public String getSubmitUserName() {
		return submitUserName;
	}

	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}

	public String getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	public String getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(String syncFlag) {
		this.syncFlag = syncFlag;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
