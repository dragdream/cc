package com.tianee.oa.core.workflow.workmanage.model.queryTpl;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeQueryTplModel extends TeeBaseModel{
	
	private String condFormula;
	private int flowId;
	private String tplName;
	private int userId;
	private TeeTplFlowTypeFieldModel tplFlowTypeFieldModel;//工作流基本属性
	private TeeTplFormFieldModel tplFormFieldModel;//表单数据条件
	private TeeTplGroupSumFieldModel tplGroupSumFieldModel;//统计报表选项


	public TeeTplFlowTypeFieldModel getTplFlowTypeFieldModel() {
		return tplFlowTypeFieldModel;
	}
	public void setTplFlowTypeFieldModel(
			TeeTplFlowTypeFieldModel tplFlowTypeFieldModel) {
		this.tplFlowTypeFieldModel = tplFlowTypeFieldModel;
	}
	public TeeTplFormFieldModel getTplFormFieldModel() {
		return tplFormFieldModel;
	}
	public void setTplFormFieldModel(TeeTplFormFieldModel tplFormFieldModel) {
		this.tplFormFieldModel = tplFormFieldModel;
	}
	public TeeTplGroupSumFieldModel getTplGroupSumFieldModel() {
		return tplGroupSumFieldModel;
	}
	public void setTplGroupSumFieldModel(
			TeeTplGroupSumFieldModel tplGroupSumFieldModel) {
		this.tplGroupSumFieldModel = tplGroupSumFieldModel;
	}
	public String getCondFormula() {
		return condFormula;
	}
	public void setCondFormula(String condFormula) {
		this.condFormula = condFormula;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public String getTplName() {
		return tplName;
	}
	public void setTplName(String tplName) {
		this.tplName = tplName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	
	
}
