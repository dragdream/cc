package com.tianee.oa.core.general.model;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;

public class TeePortletModel extends TeeBaseModel{
	private int sid;//id
	
	private String modelTitle;//模块名称
	
	private String modelIcons;//模块icon图标
	
	private String contentTpl;//内容模板
	
	private int sortNo;//排序（在当前列的第几位即第几行）
	
	private int rows;//模块最大显示记录条数
	
	private int viewType;//显示类型 1-启用 0-停用

	private String remark;//备注
	
	private int contentType = 1;//内容类型  1：置标模板  2：Java模板
	
	private int autoRefresh;//刷新间隔  秒  0为不刷新

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getModelTitle() {
		return modelTitle;
	}

	public void setModelTitle(String modelTitle) {
		this.modelTitle = modelTitle;
	}

	public String getModelIcons() {
		return modelIcons;
	}

	public void setModelIcons(String modelIcons) {
		this.modelIcons = modelIcons;
	}

	public String getContentTpl() {
		return contentTpl;
	}

	public void setContentTpl(String contentTpl) {
		this.contentTpl = contentTpl;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getAutoRefresh() {
		return autoRefresh;
	}

	public void setAutoRefresh(int autoRefresh) {
		this.autoRefresh = autoRefresh;
	}
	
	
}
