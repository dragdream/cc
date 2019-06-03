package com.tianee.oa.subsys.supervise.model;



public class TeeSupFeedBackModel {
	
	private int sid;//自增id
	
	private String title ;//标题
	
	private String content ;//内容
	
	private int level ;//缓急级别  0普通  1紧急
	
	//private TeeSupervision sup;//所属督办任务
	private String supName;//任务名称
	private int supId;//任务主键
	
	//private TeePerson creater;//创建人
	private String createrName;
	private int createrId;
	
	//private Date createTime ;//创建时间
	private String createTimeStr;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public int getSupId() {
		return supId;
	}

	public void setSupId(int supId) {
		this.supId = supId;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public int getCreaterId() {
		return createrId;
	}

	public void setCreaterId(int createrId) {
		this.createrId = createrId;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
}
