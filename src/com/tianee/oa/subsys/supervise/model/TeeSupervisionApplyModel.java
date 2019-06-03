package com.tianee.oa.subsys.supervise.model;

import java.util.Date;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.subsys.supervise.bean.TeeSupervision;

public class TeeSupervisionApplyModel {
	
	private int sid;//自增id
	
	private int  status ;// 1 同意  2 拒绝 
	
	private String  content ;//内容
	
	private int  type ;// 1暂停   2回复   3办结
	
	//private Date  createTime ;//创建时间
	private String createTimeStr;
	
	private String   reason;//理由
	
	//private TeePerson creater;//创建人
	private String createrName;
	private int createrId;
	
	//private TeeSupervision sup;//关联的 任务
	private int supId;
	private String supName;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
