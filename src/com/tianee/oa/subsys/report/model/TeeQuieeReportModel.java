package com.tianee.oa.subsys.report.model;

public class TeeQuieeReportModel {
	private int sid;
	private String reportName;//报表名称(文件夹名称)
	private int reportType;//报表类型（1：文件夹  2：报表文件）
	private int isExistRaqFile;//是否存在报表文件   1.存在   0.不存在
	private int isExistRaqParamFile;//是否存在参数文件    1.存在   0.不存在
	
	//private TeeQuieeReport parent;//父ID
	private int parentSid;//父ID	
	private int sortNo;//排序号
	//private Calendar crTime;//创建时间
	private String crTimeStr;//创建时间
	
	private String userManageIds;//用户管理权限Id串
	private String userManageNames;//用户管理权限用户名串
	
	private String deptManageIds;//部门管理权限
	private String deptManageNames;
	
	private String roleManageIds;//角色管理权限
	private String roleManageNames;
	
	
	private String userViewIds;//用户查看权限Id串
	private String userViewNames;//用户查看权限用户名串
	
	private String deptViewIds;//部门查看权限
	private String deptViewNames;
	
	private String roleViewIds;//角色查看权限
	
	
	
	
	public int getIsExistRaqFile() {
		return isExistRaqFile;
	}
	public void setIsExistRaqFile(int isExistRaqFile) {
		this.isExistRaqFile = isExistRaqFile;
	}
	public int getIsExistRaqParamFile() {
		return isExistRaqParamFile;
	}
	public void setIsExistRaqParamFile(int isExistRaqParamFile) {
		this.isExistRaqParamFile = isExistRaqParamFile;
	}
	public String getUserManageIds() {
		return userManageIds;
	}
	public void setUserManageIds(String userManageIds) {
		this.userManageIds = userManageIds;
	}
	public String getUserManageNames() {
		return userManageNames;
	}
	public void setUserManageNames(String userManageNames) {
		this.userManageNames = userManageNames;
	}
	public String getDeptManageIds() {
		return deptManageIds;
	}
	public void setDeptManageIds(String deptManageIds) {
		this.deptManageIds = deptManageIds;
	}
	public String getDeptManageNames() {
		return deptManageNames;
	}
	public void setDeptManageNames(String deptManageNames) {
		this.deptManageNames = deptManageNames;
	}
	public String getRoleManageIds() {
		return roleManageIds;
	}
	public void setRoleManageIds(String roleManageIds) {
		this.roleManageIds = roleManageIds;
	}
	public String getRoleManageNames() {
		return roleManageNames;
	}
	public void setRoleManageNames(String roleManageNames) {
		this.roleManageNames = roleManageNames;
	}
	public String getUserViewIds() {
		return userViewIds;
	}
	public void setUserViewIds(String userViewIds) {
		this.userViewIds = userViewIds;
	}
	public String getUserViewNames() {
		return userViewNames;
	}
	public void setUserViewNames(String userViewNames) {
		this.userViewNames = userViewNames;
	}
	public String getDeptViewIds() {
		return deptViewIds;
	}
	public void setDeptViewIds(String deptViewIds) {
		this.deptViewIds = deptViewIds;
	}
	public String getDeptViewNames() {
		return deptViewNames;
	}
	public void setDeptViewNames(String deptViewNames) {
		this.deptViewNames = deptViewNames;
	}
	public String getRoleViewIds() {
		return roleViewIds;
	}
	public void setRoleViewIds(String roleViewIds) {
		this.roleViewIds = roleViewIds;
	}
	public String getRoleViewNames() {
		return roleViewNames;
	}
	public void setRoleViewNames(String roleViewNames) {
		this.roleViewNames = roleViewNames;
	}
	private String roleViewNames;
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public int getReportType() {
		return reportType;
	}
	public void setReportType(int reportType) {
		this.reportType = reportType;
	}
	
	public int getParentSid() {
		return parentSid;
	}
	public void setParentSid(int parentSid) {
		this.parentSid = parentSid;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
	public String getCrTimeStr() {
		return crTimeStr;
	}
	public void setCrTimeStr(String crTimeStr) {
		this.crTimeStr = crTimeStr;
	}
	

}
