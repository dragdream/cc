package com.tianee.oa.core.org.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeUserGroupModel extends TeeBaseModel{
	
	private String uuid;
	private String groupName;
	private Integer orderNo;
	private Integer userUuid;
	private String userListIds;
	private String userListNames;
	private List<Map> userListInfo = new ArrayList<Map>();//
	private int userId;
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public String getUserListIds() {
		return userListIds;
	}
	public void setUserListIds(String userListIds) {
		this.userListIds = userListIds;
	}
	public String getUserListNames() {
		return userListNames;
	}
	public void setUserListNames(String userListNames) {
		this.userListNames = userListNames;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<Map> getUserListInfo() {
		return userListInfo;
	}
	public void setUserListInfo(List<Map> userListInfo) {
		this.userListInfo = userListInfo;
	}
	
	
}
