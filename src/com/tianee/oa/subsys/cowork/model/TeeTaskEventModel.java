package com.tianee.oa.subsys.cowork.model;

public class TeeTaskEventModel {
	private int sid;
	private String createTime;

	private int createUserId;

	private String createUserName;
	
	/**
	 * 1、布置(green)
	 * 2、接受(green)
	 * 3、审批(orange)
	 * 4、审核(orange)
	 * 5、撤销(orange)
	 * 6、督办(blue)
	 * 7、延期(blue)
	 * 8、失败(red)
	 * 9、汇报(blue)
	 * 10、完成(green)
	 * 11、文档(red)
	 * 12、修改(red)
	 */
	private int type;
	

	private String typeDesc;

	private String title;

	private String content;

	private int task;//父任务

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
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

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}
	
	
}
