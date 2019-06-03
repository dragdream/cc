package com.tianee.oa.core.general.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

public class TeeShortcutMenuModel {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SID")
	private int sid;//自增id
	

	private String  userId ;//人员id
	
	private String userName ;
	
	@Column(name="MENU_IDS" ,length = 1000)
	private String menuIds;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	
	
}
