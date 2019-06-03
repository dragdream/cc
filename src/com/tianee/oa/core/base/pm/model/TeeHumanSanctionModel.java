package com.tianee.oa.core.base.pm.model;


/**
 * 奖惩管理
 *
 */
public class TeeHumanSanctionModel {
	private int sid;
	
	/**
	 * 积极参加工作
	 * 不迟到不早退
	 * 违规操作
	 * 经常迟到早退
	 */
	private String sanType;//奖惩项目
	
	private String sanTypeDesc;//奖惩项目
	
	private String sanDateDesc;//奖惩日期
	
	private String validDateDesc;//生效日期
	
	private int pays;//奖罚金额
	
	private String content;//说明
	
	private String remark;//备注
	
	private int humanDocSid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}


	public String getSanDateDesc() {
		return sanDateDesc;
	}

	public void setSanDateDesc(String sanDateDesc) {
		this.sanDateDesc = sanDateDesc;
	}

	public String getValidDateDesc() {
		return validDateDesc;
	}

	public void setValidDateDesc(String validDateDesc) {
		this.validDateDesc = validDateDesc;
	}

	public int getPays() {
		return pays;
	}

	public void setPays(int pays) {
		this.pays = pays;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public int getHumanDocSid() {
		return humanDocSid;
	}

	public void setHumanDocSid(int humanDocSid) {
		this.humanDocSid = humanDocSid;
	}

	public String getSanType() {
		return sanType;
	}

	public void setSanType(String sanType) {
		this.sanType = sanType;
	}

	public String getSanTypeDesc() {
		return sanTypeDesc;
	}

	public void setSanTypeDesc(String sanTypeDesc) {
		this.sanTypeDesc = sanTypeDesc;
	}
	
	
}
