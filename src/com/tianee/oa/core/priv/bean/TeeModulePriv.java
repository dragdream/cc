package com.tianee.oa.core.priv.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;

@SuppressWarnings("serial")
@Entity
@Table(name="MODULE_PRIV")
public class TeeModulePriv implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="MODULE_PRIV_seq_gen")
	@SequenceGenerator(name="MODULE_PRIV_seq_gen", sequenceName="MODULE_PRIV_seq")
	private int sid;
	
	@ManyToOne
	@Index(name="IDX548108eb4f114b77a655e6975e4")
	@JoinColumn(name="PERSON_UUID")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeePerson userId;//人员
	

	@Column(name="MODULE_ID")
	private Integer moduleId;//模块Id
	
	@Column(name="DEPT_PRIV",columnDefinition="char(1)")
	private String deptPriv;//人员范围
	
	@Column(name="ROLE_PRIV",columnDefinition="char(1)")
	private String rolePriv;//人员角色
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多       
	fetch = FetchType.LAZY  ) 
	@JoinTable(name="MODULE_PRIV_DEPARTMENT",         
	joinColumns={@JoinColumn(name="MODULE_RRIV_ID")},       
	inverseJoinColumns={@JoinColumn(name="DEPT_ID")}  )
	private Set<TeeDepartment>  deptIds;//指定部门
	
	
	@ManyToMany(targetEntity=TeeUserRole.class,   //多对多       
			fetch = FetchType.LAZY  ) 
			@JoinTable(name="MODULE_PRIV_USER_ROLE",         
			joinColumns={@JoinColumn(name="MODULE_RRIV_ID")},       
			inverseJoinColumns={@JoinColumn(name="ROLE_ID")}  )
	private Set<TeeUserRole>  roleIds;//指定角色
	

	@ManyToMany(targetEntity=TeePerson.class,   //多对多       
			fetch = FetchType.LAZY  ) 
			@JoinTable(name="MODULE_PRIV_USER",         
			joinColumns={@JoinColumn(name="MODULE_RRIV_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_ID")}  )
	private Set<TeePerson>  userIds;//指定人员


	


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public TeePerson getUserId() {
		return userId;
	}


	public void setUserId(TeePerson userId) {
		this.userId = userId;
	}


	public Integer getModuleId() {
		return moduleId;
	}


	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}


	public String getDeptPriv() {
		return deptPriv;
	}


	public void setDeptPriv(String deptPriv) {
		this.deptPriv = deptPriv;
	}


	public String getRolePriv() {
		return rolePriv;
	}


	public void setRolePriv(String rolePriv) {
		this.rolePriv = rolePriv;
	}


	public Set<TeeDepartment> getDeptIds() {
		return deptIds;
	}


	public void setDeptIds(Set<TeeDepartment> deptIds) {
		this.deptIds = deptIds;
	}


	public Set<TeeUserRole> getRoleIds() {
		return roleIds;
	}


	public void setRoleIds(Set<TeeUserRole> roleIds) {
		this.roleIds = roleIds;
	}


	public Set<TeePerson> getUserIds() {
		return userIds;
	}


	public void setUserIds(Set<TeePerson> userIds) {
		this.userIds = userIds;
	}
	
	
	
}
