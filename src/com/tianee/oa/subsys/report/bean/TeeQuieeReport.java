package com.tianee.oa.subsys.report.bean;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

@SuppressWarnings("serial")
@Entity
@Table(name="QUIEE_REPORT")
public class TeeQuieeReport {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy=GenerationType.AUTO,generator="QUIEE_REPORT_seq_gen")
	@SequenceGenerator(name="QUIEE_REPORT_seq_gen", sequenceName="QUIEE_REPORT_seq")
	private int sid;
	
	@Column(name="REPORT_NAME")
	private String reportName;//报表名称(文件夹名称)
	
	@Column(name="REPORT_TYPE")
	private int reportType;//报表类型（1：文件夹  2：报表文件）
	
	@Column(name="RAQ_FILE")
	private byte[] raqFile;//报表文件内容
	
	@Column(name="RAQ_PARAM_FILE")
	private byte[] raqParamFile;//参数文件内容
	
	@ManyToOne()
	@JoinColumn(name="PARENT_ID")
	private TeeQuieeReport parent;//父ID
	
	@Column(name="SORT_NO")
	private int sortNo;//排序号
	
	@Column(name="CR_TIME")
	private Calendar crTime;//创建时间
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "QUIEE_REPORT_CAT_USER_MANAGE", joinColumns = { @JoinColumn(name = "CAT_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	private Set<TeePerson> userPrivManage = new HashSet();//用户管理权限
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "QUIEE_REPORT_CAT_DEPT_MANAGE", joinColumns = { @JoinColumn(name = "CAT_ID") }, inverseJoinColumns = { @JoinColumn(name = "DEPT_ID") })
	private Set<TeeDepartment> deptPrivManage = new HashSet<TeeDepartment>();//部门管理权限
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "QUIEE_REPORT_CAT_ROLE_MANAGE", joinColumns = { @JoinColumn(name = "CAT_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<TeeUserRole> rolePrivManage = new HashSet<TeeUserRole>();//角色管理权限
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "QUIEE_REPORT_CAT_USER_VIEW", joinColumns = { @JoinColumn(name = "CAT_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
	private Set<TeePerson> userPrivView = new HashSet();//用户查看权限
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "QUIEE_REPORT_CAT_DEPT_VIEW", joinColumns = { @JoinColumn(name = "CAT_ID") }, inverseJoinColumns = { @JoinColumn(name = "DEPT_ID") })
	private Set<TeeDepartment> deptPrivView = new HashSet<TeeDepartment>();//部门查看权限
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "QUIEE_REPORT_CAT_ROLE_VIEW", joinColumns = { @JoinColumn(name = "CAT_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<TeeUserRole> rolePrivView = new HashSet<TeeUserRole>();//角色查看权限

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

	public byte[] getRaqFile() {
		return raqFile;
	}

	public void setRaqFile(byte[] raqFile) {
		this.raqFile = raqFile;
	}

	public byte[] getRaqParamFile() {
		return raqParamFile;
	}

	public void setRaqParamFile(byte[] raqParamFile) {
		this.raqParamFile = raqParamFile;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public Set<TeePerson> getUserPrivManage() {
		return userPrivManage;
	}

	public void setUserPrivManage(Set<TeePerson> userPrivManage) {
		this.userPrivManage = userPrivManage;
	}

	public Set<TeeDepartment> getDeptPrivManage() {
		return deptPrivManage;
	}

	public void setDeptPrivManage(Set<TeeDepartment> deptPrivManage) {
		this.deptPrivManage = deptPrivManage;
	}

	public Set<TeeUserRole> getRolePrivManage() {
		return rolePrivManage;
	}

	public void setRolePrivManage(Set<TeeUserRole> rolePrivManage) {
		this.rolePrivManage = rolePrivManage;
	}

	public Set<TeePerson> getUserPrivView() {
		return userPrivView;
	}

	public void setUserPrivView(Set<TeePerson> userPrivView) {
		this.userPrivView = userPrivView;
	}

	public Set<TeeDepartment> getDeptPrivView() {
		return deptPrivView;
	}

	public void setDeptPrivView(Set<TeeDepartment> deptPrivView) {
		this.deptPrivView = deptPrivView;
	}

	public Set<TeeUserRole> getRolePrivView() {
		return rolePrivView;
	}

	public void setRolePrivView(Set<TeeUserRole> rolePrivView) {
		this.rolePrivView = rolePrivView;
	}

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public TeeQuieeReport getParent() {
		return parent;
	}

	public void setParent(TeeQuieeReport parent) {
		this.parent = parent;
	}

	public Calendar getCrTime() {
		return crTime;
	}

	public void setCrTime(Calendar crTime) {
		this.crTime = crTime;
	}
	
	
	
}
