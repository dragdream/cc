package com.tianee.oa.core.workflow.workmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

@Entity
@Table(name="FLOW_PRIV")
public class TeeFlowPriv  implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_PRIV_seq_gen")
	@SequenceGenerator(name="FLOW_PRIV_seq_gen", sequenceName="FLOW_PRIV_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDX08266e4fa52b45a8b1ebd2a4b43")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowType flowType;
	
	@Column(name="PRIV_TYPE")
	private int privType;
	
	@Column(name="PRIV_SCOPE")
	private int privScope;
	
	@ManyToMany(cascade={CascadeType.DETACH})
	@JoinTable(name="FLOW_PRIV_USER",
			joinColumns={@JoinColumn(name="FLOW_PRIV_ID")},
			inverseJoinColumns={@JoinColumn(name="PRIV_USER")})
	private Set<TeePerson> privUsers;//授权范围（人员）

	@ManyToMany(cascade={CascadeType.DETACH})
	@JoinTable(name="FLOW_PRIV_DEPT",
			joinColumns={@JoinColumn(name="FLOW_PRIV_ID")},
			inverseJoinColumns={@JoinColumn(name="PRIV_DEPT")})
	private Set<TeeDepartment> privDepts;//授权范围（部门）
	
	@ManyToMany(cascade={CascadeType.DETACH})
	@JoinTable(name="FLOW_PRIV_ROLE",
			joinColumns={@JoinColumn(name="FLOW_PRIV_ID")},
			inverseJoinColumns={@JoinColumn(name="PRIV_ROLE")})
	private Set<TeeUserRole> privRoles;//授权范围（角色）
	
	@ManyToMany(cascade={CascadeType.DETACH})
	@JoinTable(name="FLOW_PRIV_DEPT_POST",
			joinColumns={@JoinColumn(name="FLOW_PRIV_ID")},
			inverseJoinColumns={@JoinColumn(name="DEPT_POST")})
	private Set<TeeDepartment> deptPost;//管理范围（部门）
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public int getPrivType() {
		return privType;
	}

	public void setPrivType(int privType) {
		this.privType = privType;
	}

	public int getPrivScope() {
		return privScope;
	}

	public void setPrivScope(int privScope) {
		this.privScope = privScope;
	}

	public Set<TeePerson> getPrivUsers() {
		return privUsers;
	}

	public void setPrivUsers(Set<TeePerson> privUsers) {
		this.privUsers = privUsers;
	}

	public Set<TeeDepartment> getPrivDepts() {
		return privDepts;
	}

	public void setPrivDepts(Set<TeeDepartment> privDepts) {
		this.privDepts = privDepts;
	}

	public Set<TeeUserRole> getPrivRoles() {
		return privRoles;
	}

	public void setPrivRoles(Set<TeeUserRole> privRoles) {
		this.privRoles = privRoles;
	}

	public void setDeptPost(Set<TeeDepartment> deptPost) {
		this.deptPost = deptPost;
	}

	public Set<TeeDepartment> getDeptPost() {
		return deptPost;
	}
	
}
