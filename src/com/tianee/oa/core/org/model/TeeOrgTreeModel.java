package com.tianee.oa.core.org.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeOrgTreeModel extends TeeBaseModel {

	private String uuid;
	private String deptName;
	private String pid;
	private String iconSkin;
	private boolean isParent;
	
	public TeeOrgTreeModel(String id, String name, String pid,String iconSkin,boolean isParent) {
		super();
		this.uuid = id;
		this.deptName = name;
		this.pid = pid;
		this.iconSkin = iconSkin;
		this.isParent = isParent;
	}
	
	

	public String getIconSkin() {
		return iconSkin;
	}



	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}



	public boolean isParent() {
		return isParent;
	}



	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}



	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
	
}
