package com.tianee.oa.core.base.pm.model;


/**
 * 技能管理
 *
 */
public class TeeHumanSkillModel {
	private int sid;
	
	private String skillName;//技能名称
	
	/**
	 * 是
	 * 否
	 */
	private String skillSpecial;//特种技能
	
	private String skillLevel;//技能级别
	
	/**
	 * 有
	 * 无
	 */
	private String skillCert;//技能证
	
	private String startTimeDesc;//发放日期
	
	private String endTimeDesc;//结束日期
	
	private String sendCompany;//发送单位
	
	private String remark;//备注
	
	private int humanDocSid;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillSpecial() {
		return skillSpecial;
	}

	public void setSkillSpecial(String skillSpecial) {
		this.skillSpecial = skillSpecial;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getSkillCert() {
		return skillCert;
	}

	public void setSkillCert(String skillCert) {
		this.skillCert = skillCert;
	}


	public String getStartTimeDesc() {
		return startTimeDesc;
	}

	public void setStartTimeDesc(String startTimeDesc) {
		this.startTimeDesc = startTimeDesc;
	}

	public String getEndTimeDesc() {
		return endTimeDesc;
	}

	public void setEndTimeDesc(String endTimeDesc) {
		this.endTimeDesc = endTimeDesc;
	}

	public String getSendCompany() {
		return sendCompany;
	}

	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
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

	
	
}
