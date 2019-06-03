package com.tianee.webframe.httpmodel;

import java.util.List;

/**
 * 后台向前台返回JSON，用于easyui的datagrid
 *  只用于easy-ui 如果今后有自己的UI了 也可以这样封装
 * @author 赵鹏
 * 
 */
public class TeeEasyuiDataGridJson implements java.io.Serializable {

	private Long total;// 总记录数
	private List rows;// 每行记录
	private List footer;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public List getFooter() {
		return footer;
	}

	public void setFooter(List footer) {
		this.footer = footer;
	}

}
