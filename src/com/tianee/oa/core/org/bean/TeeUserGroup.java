package com.tianee.oa.core.org.bean;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="user_group")
public class TeeUserGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="user_group_seq_gen")
	@SequenceGenerator(name="user_group_seq_gen", sequenceName="user_group_seq")
	private  int sid;
	
	@ManyToMany(targetEntity=TeePerson.class,fetch = FetchType.LAZY  ) 
			@JoinTable(joinColumns={@JoinColumn(name="GROUP_UUID")},       
	inverseJoinColumns={@JoinColumn(name="PERSON_UUID")}  ) 	
	private List<TeePerson> userList;//用户(多个)
	
	@Column(name="USER_ID")
	private int userId ;//USER_ID
	
	@Column(name="GROUP_NAME",length=200)
	private String groupName;//分组名

	
	@Column(name="ORDER_NO",length=20)
	private Integer orderNo;//排序号
	
	@Column(name="USER_UUID",length=32)
	private Integer userUuid;//创建人uuid





	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public List<TeePerson> getUserList() {
		return userList;
	}

	public void setUserList(List<TeePerson> userList) {
		this.userList = userList;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}



	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(Integer userUuid) {
		this.userUuid = userUuid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	
}
