package com.tianee.oa.core.general.bean;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "PORTLET")
public class TeePortlet implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PORTLET_seq_gen")
	@SequenceGenerator(name="PORTLET_seq_gen", sequenceName="PORTLET_seq")
	@Column(name="sid")
	private int sid;//id
	
	@Column(name="MODEL_TITLE")
	private String modelTitle;//模块名称
	
	@Column(name="MODEL_ICONS")
	private String modelIcons;//模块icon图标
	
	@Lob
	@Column(name="CONTENT_TPL")
	private String contentTpl;//内容模板
	
	@Column(name="CONTENT_TYPE")
	private int contentType = 1;//内容类型  1：置标模板  2：Java模板
	
	@Lob
	@Column(name="NO_DATA_TPL")
	private String noDataTpl;//无数据模板
	
	@Column(name="MORE")
	private String moreUrl;//更多url
	
	@Column(name="SORT_NO")
	private int sortNo;//排序（在当前列的第几位即第几行）
	
	@Column(name="AUTO_REFRESH")
	private int autoRefresh;//刷新间隔  秒  0为不刷新
	
	@Column(name="ROWS_")
	private int rows;//模块最大显示记录条数
	
	@Column(name="VIEW_TYPE")
	private int viewType;//显示类型 1-启用 0-停用

	@Column(name="REMARK")
	private String remark;//备注

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

	public String getContentTpl() {
		return contentTpl;
	}

	public void setContentTpl(String contentTpl) {
		this.contentTpl = contentTpl;
	}

	public String getNoDataTpl() {
		return noDataTpl;
	}

	public void setNoDataTpl(String noDataTpl) {
		this.noDataTpl = noDataTpl;
	}

	public String getMoreUrl() {
		return moreUrl;
	}

	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
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
