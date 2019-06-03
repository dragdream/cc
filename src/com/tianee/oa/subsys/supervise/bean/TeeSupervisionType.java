package com.tianee.oa.subsys.supervise.bean;

import java.util.HashSet;
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

//督查督办分类

@Entity
@Table(name = "supervision_type")
public class TeeSupervisionType {
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="supervision_type_seq_gen")
	@SequenceGenerator(name="supervision_type_seq_gen", sequenceName="supervision_type_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="type_name")
	private String typeName ;//类型名称
	
	@Column(name="order_num")
	private int orderNum ;//排序号

	
	


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_id")
	private TeeSupervisionType parent;//父分类
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="sup_type_user_priv",
			joinColumns={@JoinColumn(name="type_id")},
			inverseJoinColumns={@JoinColumn(name="user_id")})
	private Set<TeePerson> users = new HashSet<TeePerson>(0);//用户权限
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="sup_type_dept_priv",
			joinColumns={@JoinColumn(name="type_id")},
			inverseJoinColumns={@JoinColumn(name="dept_id")})
	private Set<TeeDepartment> depts = new HashSet<TeeDepartment>(0);//用户权限
	
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="sup_type_role_priv",
			joinColumns={@JoinColumn(name="type_id")},
			inverseJoinColumns={@JoinColumn(name="role_id")})
	private Set<TeeUserRole> roles = new HashSet<TeeUserRole>(0);//用户权限
	
	public int getSid() {
		return sid;
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


	public Set<TeeUserRole> getRoles() {
		return roles;
	}


	public void setRoles(Set<TeeUserRole> roles) {
		this.roles = roles;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public int getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}


	public TeeSupervisionType getParent() {
		return parent;
	}


	public void setParent(TeeSupervisionType parent) {
		this.parent = parent;
	}


	
	
	
	
	
	
	
	
}
