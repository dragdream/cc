package com.tianee.oa.core.workflow.workmanage.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeFlowTypeIModel extends TeeBaseModel{
	
	private int sid;

	private String flowName;//流程名称
	
	private int DEAL_COUNT = 0;
	
	private int OVER_COUNT = 0;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public int getDEAL_COUNT() {
		return DEAL_COUNT;
	}

	public void setDEAL_COUNT(int dEAL_COUNT) {
		DEAL_COUNT = dEAL_COUNT;
	}

	public int getOVER_COUNT() {
		return OVER_COUNT;
	}

	public void setOVER_COUNT(int oVER_COUNT) {
		OVER_COUNT = oVER_COUNT;
	}
	
	
	
}
