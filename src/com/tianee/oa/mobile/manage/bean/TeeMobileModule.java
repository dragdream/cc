package com.tianee.oa.mobile.manage.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="MOBILE_MODULE")
public class TeeMobileModule {
	@Id
	@Column(name="SID")
	private int sid;//主键
	
	@Column(name="APP_NAME")
	private String appName;//应用名称
	
	@Column(name="PIC_")
	private String pic;//图片图标
	
	@Column(name="SORT_")
	private int sort;//排序号
	
	@Column(name="VIEW_ID")
	private String viewId;//视图标识
	
	@Column(name="URL_")
	private String url;//模块地址
	
	@Column(name="DESC_")
	private String desc;//描述
	
	@Lob
	@Column(name="DEPT_PRIV")
	private String deptPriv;//部门权限
	
	@Lob
	@Column(name="DEPT_PRIV_DESC")
	private String deptPrivDesc;//部门权限描述
	
	@Lob
	@Column(name="USER_PRIV")
	private String userPriv;//用户权限
	
	@Lob
	@Column(name="USER_PRIV_DESC")
	private String userPrivDesc;//用户权限描述
	
	@Lob
	@Column(name="ROLE_PRIV")
	private String rolePriv;//角色权限
	
	@Lob
	@Column(name="ROLE_PRIV_DESC")
	private String rolePrivDesc;//角色权限描述
	
	@Lob
	@Column(name="MANAGE_PRIV")
	private String managePriv;//管理权限
	
	@Lob
	@Column(name="MANAGE_PRIV_DESC")
	private String managePrivDesc;//管理权限描述

	@Column(name="TYPE")
	private int type;//1=OA类   2=执法类
	
	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDeptPriv() {
		return deptPriv;
	}

	public void setDeptPriv(String deptPriv) {
		this.deptPriv = deptPriv;
	}

	public String getDeptPrivDesc() {
		return deptPrivDesc;
	}

	public void setDeptPrivDesc(String deptPrivDesc) {
		this.deptPrivDesc = deptPrivDesc;
	}

	public String getUserPriv() {
		return userPriv;
	}

	public void setUserPriv(String userPriv) {
		this.userPriv = userPriv;
	}

	public String getUserPrivDesc() {
		return userPrivDesc;
	}

	public void setUserPrivDesc(String userPrivDesc) {
		this.userPrivDesc = userPrivDesc;
	}

	public String getRolePriv() {
		return rolePriv;
	}

	public void setRolePriv(String rolePriv) {
		this.rolePriv = rolePriv;
	}

	public String getRolePrivDesc() {
		return rolePrivDesc;
	}

	public void setRolePrivDesc(String rolePrivDesc) {
		this.rolePrivDesc = rolePrivDesc;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getManagePriv() {
		return managePriv;
	}

	public void setManagePriv(String managePriv) {
		this.managePriv = managePriv;
	}

	public String getManagePrivDesc() {
		return managePrivDesc;
	}

	public void setManagePrivDesc(String managePrivDesc) {
		this.managePrivDesc = managePrivDesc;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}
	
	
}
