package com.tianee.oa.core.base.attend.bean;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
/**
 * 固定排班类型
 * @author xsy
 *
 */
@Entity
@Table(name = "ATTEND_CONFIG_RULES")
public class TeeAttendConfigRules {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_CONFIG_RULES_seq_gen")
	@SequenceGenerator(name="ATTEND_CONFIG_RULES_seq_gen", sequenceName="ATTEND_CONFIG_RULES_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="CONFIG_NAME")
	private String configName;//排班名称
	
	
	@Column(name="CONFIG_MODEL")
	private String configModel;//排班模型
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="attend_config_rules_role",
			joinColumns={@JoinColumn(name="rule_id")},
			inverseJoinColumns={@JoinColumn(name="role_id")})
	private Set<TeeUserRole> roles;//角色范围
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="attend_config_rules_dept",
			joinColumns={@JoinColumn(name="rule_id")},
			inverseJoinColumns={@JoinColumn(name="dept_id")})
	private Set<TeeDepartment> depts;//部门范围
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="attend_config_rules_user",
			joinColumns={@JoinColumn(name="rule_id")},
			inverseJoinColumns={@JoinColumn(name="user_id")})
	private Set<TeePerson> users;//人员范围


	public int getSid() {
		return sid;
	}


	public String getConfigName() {
		return configName;
	}


	public String getConfigModel() {
		return configModel;
	}


	public Set<TeeUserRole> getRoles() {
		return roles;
	}


	public Set<TeeDepartment> getDepts() {
		return depts;
	}


	public Set<TeePerson> getUsers() {
		return users;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public void setConfigName(String configName) {
		this.configName = configName;
	}


	public void setConfigModel(String configModel) {
		this.configModel = configModel;
	}


	public void setRoles(Set<TeeUserRole> roles) {
		this.roles = roles;
	}


	public void setDepts(Set<TeeDepartment> depts) {
		this.depts = depts;
	}


	public void setUsers(Set<TeePerson> users) {
		this.users = users;
	}
	
	
	
	
	
}
