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
public class TeeTplFormFieldModel extends TeeBaseModel{
	
	private int sid;//表单控件自增ID
	private int itemId;//项目ID
	private TeeForm form;//表单
	private String tag;//控件标签名称
	private String title;//控件标题
	private String content;//控件整体内容
	private String model="";//数据模型
	private String name;//控件名称
	private String xtype;//控件类型
	private int columnType;//列类型
	private String defaultValue;//默认值
	private List<TeeTplRadioModel> radioList;
	
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public TeeForm getForm() {
		return form;
	}
	public void setForm(TeeForm form) {
		this.form = form;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXtype() {
		return xtype;
	}
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public List<TeeTplRadioModel> getRadioList() {
		return radioList;
	}
	public void setRadioList(List<TeeTplRadioModel> radioList) {
		this.radioList = radioList;
	}

	
	
	
	

	
	
}
