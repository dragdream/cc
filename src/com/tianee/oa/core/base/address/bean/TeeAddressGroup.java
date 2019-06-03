package com.tianee.oa.core.base.address.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Address_Group")
public class TeeAddressGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="Address_Group_seq_gen")
	@SequenceGenerator(name="Address_Group_seq_gen", sequenceName="Address_Group_seq")
	@Column(name = "SID")
	private int seqId;
	// 分组名称
	@Column(name = "GROUP_NAME", nullable = true, length = 200)
	private String groupName;
	// 创建人 id
	@Column(name = "USER_ID", nullable = true)
	private Integer userId;
	// 排序
	@Column(name = "ORDER_NO", nullable = true, length = 20)
	private int orderNo;
	@Lob
	@Column(name = "TO_ROLESIDS", nullable = true)
	private String toRolesIds;//发布角色的id串
	
	@Lob
	@Column(name = "TO_ROLESNAMES", nullable = true)
	private String toRolesNames;//发布部门的名字
	
	@Lob
	@Column(name = "TO_DEPTIDS", nullable = true)
	private String toDeptIds;//发布部门的id串
	
	@Lob
	@Column(name = "toDeptNames", nullable = true)
	private String toDeptNames;//发布部门的id串
	
	@Lob
	@Column(name = "TO_USERNAMES", nullable = true)
	private String toUserNames;//发布人的名字
	
	@Lob
	@Column(name = "TO_USERIDS", nullable = true)
	private String toUserIds;//发布人的id串

	public int getSeqId() {
		return seqId;
	}

	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getToRolesIds() {
		if(toRolesIds == null){
			toRolesIds = "";
		}
		return toRolesIds;
	}

	public void setToRolesIds(String toRolesIds) {
		this.toRolesIds = toRolesIds;
	}

	public String getToRolesNames() {
		if(toRolesNames == null){
			toRolesNames = "";
		}
		return toRolesNames;
	}

	public void setToRolesNames(String toRolesNames) {
		this.toRolesNames = toRolesNames;
	}

	public String getToDeptIds() {
		if(toDeptIds == null){
			toDeptIds = "";
		}
		return toDeptIds;
	}

	public void setToDeptIds(String toDeptIds) {
		this.toDeptIds = toDeptIds;
	}

	public String getToDeptNames() {
		if(toDeptNames == null){
			toDeptNames = "";
		}
		return toDeptNames;
	}

	public void setToDeptNames(String toDeptNames) {
		this.toDeptNames = toDeptNames;
	}

	public String getToUserNames() {
		if(toUserNames == null){
			toUserNames = "";
		}
		return toUserNames;
	}

	public void setToUserNames(String toUserNames) {
		this.toUserNames = toUserNames;
	}

	public String getToUserIds() {
		if(toUserIds == null){
			toUserIds = "";
		}
		return toUserIds;
	}

	public void setToUserIds(String toUserIds) {
		this.toUserIds = toUserIds;
	}
	
	
	
}
