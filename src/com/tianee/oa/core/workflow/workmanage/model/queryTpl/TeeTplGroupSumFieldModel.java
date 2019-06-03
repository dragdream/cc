package com.tianee.oa.core.workflow.workmanage.model.queryTpl;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeTplGroupSumFieldModel extends TeeBaseModel{
	
	private String groupFld;
	private String groupSort;
	private String sumFld;
	private String[] dispFld;

	public String getGroupFld() {
		return groupFld;
	}
	public void setGroupFld(String groupFld) {
		this.groupFld = groupFld;
	}
	public String getGroupSort() {
		return groupSort;
	}
	public void setGroupSort(String groupSort) {
		this.groupSort = groupSort;
	}
	public String getSumFld() {
		return sumFld;
	}
	public void setSumFld(String sumFld) {
		this.sumFld = sumFld;
	}
	public String[] getDispFld() {
		return dispFld;
	}
	public void setDispFld(String[] dispFld) {
		this.dispFld = dispFld;
	}
	
	
	
	

	
	
}
