package com.tianee.oa.subsys.ereport.model;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.report.bean.TeeSeniorReportCat;

public class TeeEreportModel {
	private int sid;
	private String title;//报表标题
	//private TeeSeniorReportCat cat;//报表分类
	private int catId;
	private String catName;

	//private TeePerson crUser;//创建人
	private int crUserId;
	private String crUserName;
	private String catColor;//颜色
	
	
	//private BisView bisView ;//所属视图
	private String bisViewIdentity;//视图标识
	
	private int chartType;//图标类型
	private String chartTypeDesc;
	
	private String dimension;//维度
	
	
	private String spindle;//主轴数值
	private String shaft;//次轴数值
	private String orderStr;//排序
	
	
	//private Calendar crTime;//创建时间
	private String crTimeStr;
	
	//private Set<TeeUserRole> roles;//角色权限
	private String roleIds;
	private String roleNames;
	//private Set<TeePerson> users;//人员权限
	private String userIds;
	private String userNames;
	//private Set<TeeDepartment> depts;//部门权限
	private String deptIds;
	private String deptNames;
	
	private String conditionExp;//条件表达式
	private String conditionItems;//条件项
	
	
	
	public String getConditionExp() {
		return conditionExp;
	}
	public String getConditionItems() {
		return conditionItems;
	}
	public void setConditionExp(String conditionExp) {
		this.conditionExp = conditionExp;
	}
	public void setConditionItems(String conditionItems) {
		this.conditionItems = conditionItems;
	}
	public String getCatColor() {
		return catColor;
	}
	public void setCatColor(String catColor) {
		this.catColor = catColor;
	}
	public int getSid() {
		return sid;
	}
	public String getTitle() {
		return title;
	}
	public int getCatId() {
		return catId;
	}
	public String getCatName() {
		return catName;
	}
	public int getCrUserId() {
		return crUserId;
	}
	public String getCrUserName() {
		return crUserName;
	}
	public String getBisViewIdentity() {
		return bisViewIdentity;
	}
	public int getChartType() {
		return chartType;
	}
	public String getChartTypeDesc() {
		return chartTypeDesc;
	}
	public String getDimension() {
		return dimension;
	}
	public String getSpindle() {
		return spindle;
	}
	public String getShaft() {
		return shaft;
	}
	public String getOrderStr() {
		return orderStr;
	}
	public String getCrTimeStr() {
		return crTimeStr;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public String getUserIds() {
		return userIds;
	}
	public String getUserNames() {
		return userNames;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public String getDeptNames() {
		return deptNames;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public void setCrUserId(int crUserId) {
		this.crUserId = crUserId;
	}
	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}
	public void setBisViewIdentity(String bisViewIdentity) {
		this.bisViewIdentity = bisViewIdentity;
	}
	public void setChartType(int chartType) {
		this.chartType = chartType;
	}
	public void setChartTypeDesc(String chartTypeDesc) {
		this.chartTypeDesc = chartTypeDesc;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public void setSpindle(String spindle) {
		this.spindle = spindle;
	}
	public void setShaft(String shaft) {
		this.shaft = shaft;
	}
	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
	public void setCrTimeStr(String crTimeStr) {
		this.crTimeStr = crTimeStr;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	
	
	
	
	
}
