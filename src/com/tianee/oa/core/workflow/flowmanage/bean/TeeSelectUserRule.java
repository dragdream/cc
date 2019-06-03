package com.tianee.oa.core.workflow.flowmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tianee.oa.core.org.bean.TeeUserRole;

@Entity
@Table(name="SELECT_USER_RULE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeeSelectUserRule  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SELECT_USER_RULE_seq_gen")
	@SequenceGenerator(name="SELECT_USER_RULE_seq_gen", sequenceName="SELECT_USER_RULE_seq")
	@Column(name="SID")
	private int sid;
	
	/**
	 * <option value="1">流程发起人</option>
		<option value="2">当前办理人</option>
		<option value="3">该流程所有经办人</option>
		<option value="4">本步骤经办人</option>
		<option value="5">上一步骤经办人</option>
	 */
	@Column(name="USER_TYPE")
	private int userType;//过滤用户类型
	
	/**
	 * <option value="1">无约束</option>
		<option value="2">所属部门</option>
		<option value="3">所属部门主管领导</option>
		<option value="4">所属部门分管领导</option>
		<option value="5">上级部门</option>
		<option value="6">上级部门主管领导</option>
		<option value="7">上级部门分管领导</option>
		<option value="8">下级部门</option>
		<option value="9">下级部门主管领导</option>
		<option value="10">下级部门分管领导</option>
	 */
	@Column(name="DEPT_TYPE")
	private int deptType;//过滤部门类型
	
	@ManyToOne()
	@Index(name="IDXd9dcfbd805e74f6f860a5bd14de")
	@JoinColumn(name="ROLE_ID")
	private TeeUserRole userRole;//过滤角色
	
	@ManyToOne()
	@Index(name="IDX0f57a148ffa24706a5f72930e4c")
	@JoinColumn(name="FLOW_PRCS_ID")
	private TeeFlowProcess process;//所属步骤

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getDeptType() {
		return deptType;
	}

	public void setDeptType(int deptType) {
		this.deptType = deptType;
	}

	public TeeUserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(TeeUserRole userRole) {
		this.userRole = userRole;
	}

	public void setProcess(TeeFlowProcess process) {
		this.process = process;
	}

	public TeeFlowProcess getProcess() {
		return process;
	}
	
}
