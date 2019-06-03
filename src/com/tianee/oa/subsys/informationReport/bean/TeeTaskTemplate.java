package com.tianee.oa.subsys.informationReport.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

/**
 * 任务模板
 * @author xsy
 *
 */
@Entity
@Table(name = "rep_task_template")
public class TeeTaskTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="rep_task_template_seq_gen")
	@SequenceGenerator(name="rep_task_template_seq_gen", sequenceName="rep_task_template_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name = "TASK_NAME")
	private String taskName;//任务名称
	
	@Column(name = "TASK_DESC")
	private String taskDesc;//任务描述
	
	@Column(name = "PUB_STATUS")
	private int pubStatus;//发布状态        0=未发布   1=已发布
	
	@Column(name = "PUB_TYPE")
	private int pubType;//发布类型        1=按人员 2=按部门
	
	
	@Column(name = "PUB_User_Ids")
	private String pubUserIds;//发布人员（id字符串）
	
	@Column(name = "PUB_User_Names")
	private String pubUserNames;//发布人员（id字符串）
	
	
	@Column(name = "PUB_Dept_Ids")
	private String pubDeptIds;//发布部门（id字符串）
	
	
	@Column(name = "PUB_Dept_Names")
	private String pubDeptNames;//发布部门（id字符串）
	
	@Column(name = "TASK_TYPE")
	private int taskType;//任务类型        1=日报   2=周报    3=月报     4=季报     5=年报    6=一次性
	
	
	@Column(name = "model_")
	private String model;//频次的频次的JSON模型

	
	@ManyToOne
	@JoinColumn(name = "CR_USER")
	private TeePerson crUser;//创建人


	@Column(name = "CR_TIME")
	private Date crTime;//创建时间

	
	@Column(name = "PRE_TIME")
	private Calendar preTime;//预估时间
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="rep_task_template_role",
			joinColumns={@JoinColumn(name="task_template_id")},
			inverseJoinColumns={@JoinColumn(name="role_id")})
	private Set<TeeUserRole> roles;//上报角色
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="rep_task_template_user",
			joinColumns={@JoinColumn(name="task_template_id")},
			inverseJoinColumns={@JoinColumn(name="user_id")})
	private Set<TeePerson> users;//上报人员
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="rep_task_template_dept",
			joinColumns={@JoinColumn(name="task_template_id")},
			inverseJoinColumns={@JoinColumn(name="dept_id")})
	private Set<TeeDepartment> depts;//上报部门
	
	@Column(name = "REP_TYPE")
	private int repType;//上报类别
	
	
	
	
	public Calendar getPreTime() {
		return preTime;
	}


	public void setPreTime(Calendar preTime) {
		this.preTime = preTime;
	}


	public int getPubStatus() {
		return pubStatus;
	}


	public void setPubStatus(int pubStatus) {
		this.pubStatus = pubStatus;
	}


	public String getPubUserNames() {
		return pubUserNames;
	}


	public void setPubUserNames(String pubUserNames) {
		this.pubUserNames = pubUserNames;
	}


	public String getPubDeptNames() {
		return pubDeptNames;
	}


	public void setPubDeptNames(String pubDeptNames) {
		this.pubDeptNames = pubDeptNames;
	}


	public Set<TeeUserRole> getRoles() {
		return roles;
	}


	public void setRoles(Set<TeeUserRole> roles) {
		this.roles = roles;
	}


	public Set<TeePerson> getUsers() {
		return users;
	}


	public void setUsers(Set<TeePerson> users) {
		this.users = users;
	}


	public Set<TeeDepartment> getDepts() {
		return depts;
	}


	public void setDepts(Set<TeeDepartment> depts) {
		this.depts = depts;
	}


	public TeePerson getCrUser() {
		return crUser;
	}


	public void setCrUser(TeePerson crUser) {
		this.crUser = crUser;
	}


	public Date getCrTime() {
		return crTime;
	}


	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getTaskDesc() {
		return taskDesc;
	}


	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}


	public int getPubType() {
		return pubType;
	}


	public void setPubType(int pubType) {
		this.pubType = pubType;
	}


	public String getPubUserIds() {
		return pubUserIds;
	}


	public void setPubUserIds(String pubUserIds) {
		this.pubUserIds = pubUserIds;
	}


	public String getPubDeptIds() {
		return pubDeptIds;
	}


	public void setPubDeptIds(String pubDeptIds) {
		this.pubDeptIds = pubDeptIds;
	}


	public int getTaskType() {
		return taskType;
	}


	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public int getRepType() {
		return repType;
	}


	public void setRepType(int repType) {
		this.repType = repType;
	}


	

}
