package com.tianee.oa.core.workflow.workmanage.model.queryTpl;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.webframe.httpModel.TeeBaseModel;

@SuppressWarnings("serial")
public class TeeTplRadioModel extends TeeBaseModel{
	
	private String data;
	private String checked;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
}
