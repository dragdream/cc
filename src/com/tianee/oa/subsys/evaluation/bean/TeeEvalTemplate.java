package com.tianee.oa.subsys.evaluation.bean;

import java.util.Calendar;
import java.util.List;

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

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="eval_template")
public class TeeEvalTemplate{
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="eval_template_seq_gen")
	@SequenceGenerator(name="eval_template_seq_gen", sequenceName="eval_template_seq")
	private int sid;
	
	@Column(name="SUBJECT")
	private String subject;
	
	@ManyToOne()
	@Index(name="EVAL_TYPE_ID_INDEX")
	@JoinColumn(name="EVAL_TYPE_ID")
	private TeeEvalType evalType;
	
	@ManyToOne()
	@Index(name="CREATE_USER_ID_INDEX")
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUser;
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;
	
	@Column(name="VALID_DAYS")
	private int validDays;
	
	@Column(name="AUTO_TYPE")
	private int autoType;
	
	@Column(name="START_TYPE")
	private int startType;
	
	@Column(name="DELTA")
	private int delta;
	
	@Column(name="START_DATE")
	private int startDate;
	
	@Column(name="REMARK")
	private String remark;
	
	@Column(name="TARGETS_USERS")
	private String targetsUsers;
	
	@Column(name="TARGETS_DEPTS")
	private String targetsDepts;
	
	@Column(name="TARGETS_ROLES")
	private String targetsRoles;
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="EVAL_TEMPLATE_VIEW",
			joinColumns={@JoinColumn(name="TIMELINE_ID")},
			inverseJoinColumns={@JoinColumn(name="VIEW_PRIV_USER_ID")})
	private List<TeePerson> viewPrivs;
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="EVAL_TEMPLATE_MANAGE",
			joinColumns={@JoinColumn(name="TIMELINE_ID")},
			inverseJoinColumns={@JoinColumn(name="MANAGE_PRIV_USER_ID")})
	private List<TeePerson> managePrivs;
	
	@Column(name="RANGE_TYPE")
	private int rangeType;//1 月度考核  2 季度考核 3 年度考核

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public TeeEvalType getEvalType() {
		return evalType;
	}

	public void setEvalType(TeeEvalType evalType) {
		this.evalType = evalType;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public int getValidDays() {
		return validDays;
	}

	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}

	public int getAutoType() {
		return autoType;
	}

	public void setAutoType(int autoType) {
		this.autoType = autoType;
	}

	public int getStartType() {
		return startType;
	}

	public void setStartType(int startType) {
		this.startType = startType;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTargetsUsers() {
		return targetsUsers;
	}

	public void setTargetsUsers(String targetsUsers) {
		this.targetsUsers = targetsUsers;
	}

	public String getTargetsDepts() {
		return targetsDepts;
	}

	public void setTargetsDepts(String targetsDepts) {
		this.targetsDepts = targetsDepts;
	}

	public String getTargetsRoles() {
		return targetsRoles;
	}

	public void setTargetsRoles(String targetsRoles) {
		this.targetsRoles = targetsRoles;
	}

	public List<TeePerson> getViewPrivs() {
		return viewPrivs;
	}

	public void setViewPrivs(List<TeePerson> viewPrivs) {
		this.viewPrivs = viewPrivs;
	}

	public List<TeePerson> getManagePrivs() {
		return managePrivs;
	}

	public void setManagePrivs(List<TeePerson> managePrivs) {
		this.managePrivs = managePrivs;
	}

	public int getRangeType() {
		return rangeType;
	}

	public void setRangeType(int rangeType) {
		this.rangeType = rangeType;
	}
	
	
}