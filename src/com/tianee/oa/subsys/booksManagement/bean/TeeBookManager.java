package com.tianee.oa.subsys.booksManagement.bean;

import java.util.List;

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

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 权限管理
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "BOOK_MANAGER")
public class TeeBookManager {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "BOOK_MANAGER_seq_gen")
	@SequenceGenerator(name = "BOOK_MANAGER_seq_gen", sequenceName = "BOOK_MANAGER_seq")
	private int sid;// 自增id

	@ManyToMany(targetEntity = TeeDepartment.class, // 多对多
	fetch = FetchType.LAZY)
	@JoinTable(name = "BOOK_MANAGER_DEPT_PRIV", joinColumns = { @JoinColumn(name = "BOOK_MANAGER_ID") }, inverseJoinColumns = { @JoinColumn(name = "DEPT_UUID") })
	@Index(name = "BOOK_MANAGER_DEPT_PRIV_INDEX")
	private List<TeeDepartment> postDept;// 管理权限 -部门

	@ManyToMany(targetEntity = TeePerson.class, // 多对多
	fetch = FetchType.LAZY)
	@JoinTable(name = "BOOK_MANAGER_PERSON_PRIV", joinColumns = { @JoinColumn(name = "BOOK_MANAGER_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_UUID") })
	@Index(name = "BOOK_MANAGER_PERSON_PRIV_INDEX")
	private List<TeePerson> postUser;// 管理权限--人员

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public List<TeeDepartment> getPostDept() {
		return postDept;
	}

	public void setPostDept(List<TeeDepartment> postDept) {
		this.postDept = postDept;
	}

	public List<TeePerson> getPostUser() {
		return postUser;
	}

	public void setPostUser(List<TeePerson> postUser) {
		this.postUser = postUser;
	}

}
