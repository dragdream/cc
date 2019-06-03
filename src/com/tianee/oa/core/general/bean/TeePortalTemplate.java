package com.tianee.oa.core.general.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 门户模板
 * @author kakalion
 *
 */
@Entity
@Table(name="PORTAL_TEMPLATE")
public class TeePortalTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="portal_template_seq_gen")
	@SequenceGenerator(name="portal_template_seq_gen", sequenceName="portal_template_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="TEMPLATE_NAME")
	private String templateName;//模版名称
	
	@Lob
	@Column(name="PORTAL_MODEL")
	private String portalModel;//模型，json串
	
	@Column(name="SORT_NO")
	private int sortNo;//模版排序号
	
	@Column(name="COLS")
	private int cols;//列数
	
	@Lob
	@Column(name="USER_PRIV")
	private String userPriv;//用户权限，指定：/1.u/2.u/3.u/4.u
	
	@Lob
	@Column(name="USER_PRIV_DESC")
	private String userPrivDesc;//用户权限描述， 指定：系统管理员,综合部总监,综合部员工
	
	@Lob
	@Column(name="DEPT_PRIV")
	private String deptPriv;//部门权限，指定：/1.d/2.d/3.d/4.d
	
	@Lob
	@Column(name="DEPT_PRIV_DESC")
	private String deptPrivDesc;//部门权限描述， 指定：系统管理部,综合部,人事部
	
	@Lob
	@Column(name="ROLE_PRIV")
	private String rolePriv;//角色权限，指定：/1.r/2.r/3.r/4.r
	
	@Lob
	@Column(name="ROLE_PRIV_DESC")
	private String rolePrivDesc;//角色权限描述， 指定：系统管理员,员工,人事专员

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getPortalModel() {
		return portalModel;
	}

	public void setPortalModel(String portalModel) {
		this.portalModel = portalModel;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
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

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}
	
	
}
