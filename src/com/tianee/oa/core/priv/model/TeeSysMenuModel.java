package com.tianee.oa.core.priv.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeSysMenuModel extends TeeBaseModel{
	private int uuid;// va

	private String menuId;// MENU_ID 菜单编号例：001002003004

	private String menuName;// varchar(100)功能名称

	private String menuCode;// 功能指向路径

	private String icon;// 图片路径

	private String openFlag;// 打开状态

	private String menuModuleNo;//
	
	private boolean caiDanFalg;
	
	
	private int sysId;//所属应用系统主键
	
	
	

	public int getSysId() {
		return sysId;
	}

	public void setSysId(int sysId) {
		this.sysId = sysId;
	}

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public String getMenuModuleNo() {
		return menuModuleNo;
	}

	public void setMenuModuleNo(String menuModuleNo) {
		this.menuModuleNo = menuModuleNo;
	}

	public boolean isCaiDanFalg() {
		return caiDanFalg;
	}

	public void setCaiDanFalg(boolean caiDanFalg) {
		this.caiDanFalg = caiDanFalg;
	}


	
}


