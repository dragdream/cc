package com.tianee.oa.core.base.examine.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;


@SuppressWarnings("serial")
@Entity
@Table(name = "EXAMINE_GROUP")
public class TeeExamineGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAMINE_GROUP_seq_gen")
	@SequenceGenerator(name="EXAMINE_GROUP_seq_gen", sequenceName="EXAMINE_GROUP_seq")
	@Column(name="SID")
	private int sid;//SID	int(11)	自增字段	是	自增
	
	@Column(name="EXAMINE_NAME")
	private String examineName;//EXAMINE_NAME	varchar(254)	
		
	@Lob
	@Column(name="EXAMINE_DESC")
	private String examineDesc;//EXAMINE_DESC	TEST	考核指标描述	
	
	@Column(name="EXAMINE_REFER")
	private String examineRefer;//EXAMINE_REFER	varchar(20)	设置考核依据模块		
	
	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
	fetch = FetchType.LAZY  ) 
	@JoinTable( name="EXAMINE_DEPT_PRIV",        
	joinColumns={@JoinColumn(name="EXAMINE_ID")},       
	inverseJoinColumns={@JoinColumn(name="DEPT_UUID")}  ) 	
	@Index(name="EXAMINE_DEPT_PRIV_INDEX")
	private List<TeeDepartment> postDept;//权限 -部门
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
		fetch = FetchType.LAZY  ) 
		@JoinTable( name="EXAMINE_PERSON_PRIV",        
		joinColumns={@JoinColumn(name="EXAMINE_ID")},       
		inverseJoinColumns={@JoinColumn(name="USER_UUID")}  ) 	
	@Index(name="EXAMINE_PERSON_PRIV_INDEX")
	private List<TeePerson> postUser;//权限--人员
	
	@ManyToMany(targetEntity=TeeUserRole.class,   //多对多     
		fetch = FetchType.LAZY  ) 
		@JoinTable( name="EXAMINE_USER_ROLE_PRIV",        
		joinColumns={@JoinColumn(name="EXAMINE_ID")},       
		inverseJoinColumns={@JoinColumn(name="ROLE_UUID")}  ) 	
	@Index(name="EXAMINE_USER_ROLE_PRIV_INDEX")
	private List<TeeUserRole> postUserRole;//权限--角色

	@OneToMany(mappedBy="group",cascade=CascadeType.ALL)
	private List<TeeExamineItem> items = new ArrayList(0);
	
	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getExamineName() {
		return examineName;
	}


	public void setExamineName(String examineName) {
		this.examineName = examineName;
	}

	public String getExamineDesc() {
		return examineDesc;
	}

	public void setExamineDesc(String examineDesc) {
		this.examineDesc = examineDesc;
	}

	public String getExamineRefer() {
		return examineRefer;
	}

	public void setExamineRefer(String examineRefer) {
		this.examineRefer = examineRefer;
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

	public List<TeeUserRole> getPostUserRole() {
		return postUserRole;
	}

	public void setPostUserRole(List<TeeUserRole> postUserRole) {
		this.postUserRole = postUserRole;
	}


	public List<TeeExamineItem> getItems() {
		return items;
	}


	public void setItems(List<TeeExamineItem> items) {
		this.items = items;
	}
	
}
