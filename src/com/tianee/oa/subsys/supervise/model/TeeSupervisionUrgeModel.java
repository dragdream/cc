package com.tianee.oa.subsys.supervise.model;

import java.util.Date;


import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.supervise.bean.TeeSupervision;

public class TeeSupervisionUrgeModel {
	
	private int sid;//自增id
	
	private String content ;//内容
	
	//private TeePerson creater;//创建人
	private String createrName;
	
	private int createrId;
	
	//private Date createTime;//创建时间
	
	private String createTimeStr;
	
	private int isIncludeChildren;//是否包含下级任务
	
	
	//private TeeSupervision sup;//任务对象
	private int supId;
		
	private String supName;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getIsIncludeChildren() {
		return isIncludeChildren;
	}

	public void setIsIncludeChildren(int isIncludeChildren) {
		this.isIncludeChildren = isIncludeChildren;
	}

	public int getSupId() {
		return supId;
	}

	public void setSupId(int supId) {
		this.supId = supId;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	
	
}
