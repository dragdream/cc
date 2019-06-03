package com.tianee.oa.core.priv.model;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeeMenuGroupModul extends TeeBaseModel {
	private int uuid;//

	private String menuGroupName;//菜单权限名称 ，MENU_ROLE_NAME 

	private int menuGroupNo;//排序号

    private  int userNum;//拥有该权限组的人员总数
    
    private String menuGroupType;
    
    private int deptUuid;
    
    private String deptName;
    
    private int unitUuid;
    
    private String unitName;
        
    private String deptsIdStr;
    
    private String deptsNameStr;
    
	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}


	public int getUuid() {
		return uuid;
	}


	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public String getMenuGroupName() {
		return menuGroupName;
	}

	public void setMenuGroupName(String menuGroupName) {
		this.menuGroupName = menuGroupName;
	}

	public int getMenuGroupNo() {
		return menuGroupNo;
	}

	public void setMenuGroupNo(int menuGroupNo) {
		this.menuGroupNo = menuGroupNo;
	}


	public String getMenuGroupType() {
		return menuGroupType;
	}


	public void setMenuGroupType(String menuGroupType) {
		this.menuGroupType = menuGroupType;
	}


	public int getDeptUuid() {
		return deptUuid;
	}


	public void setDeptUuid(int deptUuid) {
		this.deptUuid = deptUuid;
	}


	public String getDeptName() {
		return deptName;
	}


	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


	public int getUnitUuid() {
		return unitUuid;
	}


	public void setUnitUuid(int unitUuid) {
		this.unitUuid = unitUuid;
	}


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public String getDeptsIdStr() {
		return deptsIdStr;
	}


	public void setDeptsIdStr(String deptsIdStr) {
		this.deptsIdStr = deptsIdStr;
	}


	public String getDeptsNameStr() {
		return deptsNameStr;
	}


	public void setDeptsNameStr(String deptsNameStr) {
		this.deptsNameStr = deptsNameStr;
	}

}
