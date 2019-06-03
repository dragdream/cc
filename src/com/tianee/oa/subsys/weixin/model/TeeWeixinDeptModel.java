package com.tianee.oa.subsys.weixin.model;

public class TeeWeixinDeptModel {
	private int id;//微信部门ID
	private String deptName;//部门名称 长度限制为1~64个字符 字符不能包括\:*?"<>｜。修改部门名称时指定该参数 
	private int parentid;//上级部门  父亲部门id。根部门id为
	private int order ;//排序号  在父部门中的次序值。order值小的排序靠前。
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
