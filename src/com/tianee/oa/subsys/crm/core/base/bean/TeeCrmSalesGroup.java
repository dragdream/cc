package com.tianee.oa.subsys.crm.core.base.bean;
import org.hibernate.annotations.Index;

import java.util.List;

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

/**
 * 销售组
 * 
 * @author think
 * 
 */
@Entity
@Table(name = "CRM_SALES_GROUP")
public class TeeCrmSalesGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CRM_SALES_GROUP_seq_gen")
	@SequenceGenerator(name="CRM_SALES_GROUP_seq_gen", sequenceName="CRM_SALES_GROUP_seq")
	@Column(name = "SID")
	private int sid;// Sid 主键


	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXd7382306b5d94a93ad10f20e248")
	@JoinColumn(name = "PARENT_GROUP_ID")
	private TeeCrmSalesGroup parentGroup;//上级ID

	@Column(name = "PARENT_PATH"  ,length = 500)
	private String parentPath;//上级全路径

	@Column(name = "GROUP_NAME")
	private String groupName;// 销售组名称

	@Column(name = "GROUP_ORDER", columnDefinition = "int default 0")
	private int groupOrder;// 排序
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXcf910c08918c4033ad9847fbf9a")
	@JoinColumn(name = "MANAGER_USER_ID")
	private TeePerson managerUser;//负责人

	@ManyToMany(targetEntity=TeePerson.class,   //多对多     ,成员
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="PERSON_CRM_SALES_GROUP",        
			joinColumns={@JoinColumn(name="PERSON_UUID")},       
			inverseJoinColumns={@JoinColumn(name="GROUP_ID")}  ) 	
	@Index(name="PERSON_CRM_SALES_GROUP_INDEX")
	private List<TeePerson> managerMember;//成员

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}


	public TeeCrmSalesGroup getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(TeeCrmSalesGroup parentGroup) {
		this.parentGroup = parentGroup;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupOrder() {
		return groupOrder;
	}

	public void setGroupOrder(int groupOrder) {
		this.groupOrder = groupOrder;
	}

	public List<TeePerson> getManagerMember() {
		return managerMember;
	}

	public void setManagerMember(List<TeePerson> managerMember) {
		this.managerMember = managerMember;
	}

	public TeePerson getManagerUser() {
		return managerUser;
	}

	public void setManagerUser(TeePerson managerUser) {
		this.managerUser = managerUser;
	}
	
	

	

}
