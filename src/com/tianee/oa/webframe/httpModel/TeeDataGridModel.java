package com.tianee.oa.webframe.httpModel;

/**
 * DataGridModel 主要是前台 使用 DataGrid 组件时候 向后台传递参数
 * 此类通用语 easy-ui 这个之后和大家说下 应该很通用
 * @author 赵鹏
 * 主要封装了分页 排序参数等
 */
public class TeeDataGridModel extends TeeBaseModel{


	private int page = 1;// 当前页
	private int rows = 10;// 每页显示记录数
	private String sort = null;// 排序字段名
	private String order = "";// 按什么排序(asc,desc)

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	public int getFirstResult(){
		return rows*(page-1);
	}

}
