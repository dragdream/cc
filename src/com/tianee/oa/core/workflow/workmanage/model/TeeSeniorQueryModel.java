package com.tianee.oa.core.workflow.workmanage.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeSeniorQueryModel extends TeeBaseModel{
	
	private int sid;
	private int orderNo;//排序号
	private String sortName;//分类名称
	private List<TeeFlowTypeIModel> flowTypeModelSet;//流程集合
	private TeeFlowType flowType;//流程实体
	private String tplName;
	
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public List<TeeFlowTypeIModel> getFlowTypeModelSet() {
		return flowTypeModelSet;
	}
	public void setFlowTypeModelSet(List<TeeFlowTypeIModel> flowTypeModelSet) {
		this.flowTypeModelSet = flowTypeModelSet;
	}
	public TeeFlowType getFlowType() {
		return flowType;
	}
	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}
	public String getTplName() {
		return tplName;
	}
	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	
	
}
