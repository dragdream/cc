package com.tianee.oa.subsys.salManage.model;

import java.io.Serializable;
import java.util.List;

import com.tianee.oa.subsys.salManage.bean.TeeSalAccountPerson;

public class TeeSalAccountModel implements Serializable{	
	private int sid;//自增id
	
	private String accountName;//名称
	
	private String accountNo;//账套编号
	
	private int accountSort;// 排序
	
	private String remark;//备注

	private List<TeeSalAccountPersonModel> accountPersonModel;//账套权限人员

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAccountSort() {
		return accountSort;
	}

	public void setAccountSort(int accountSort) {
		this.accountSort = accountSort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TeeSalAccountPersonModel> getAccountPersonModel() {
		return accountPersonModel;
	}

	public void setAccountPersonModel(
			List<TeeSalAccountPersonModel> accountPersonModel) {
		this.accountPersonModel = accountPersonModel;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	
	
}
