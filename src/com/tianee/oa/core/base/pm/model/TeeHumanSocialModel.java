package com.tianee.oa.core.base.pm.model;


public class TeeHumanSocialModel {
	private int sid;
	
	private String memberName;//成员名称
	
	/**
	 * 母子
	 * 父子
	 * 兄妹
	 * 姐妹
	 * 兄弟
	 * 其他
	 */
	private String relation;//与本人关系
	
	private String relationDesc;//与本人关系
	
	private String birthdayDesc;//出生日期
	
	/**
	 * 群众
	 * 共青团员
	 * 中共党员
	 * 中共预备党员
	 * 无党派人士
	 */
	private String policy;//政治面貌
	
	private String policyDesc;//政治面貌
	
	private String occupation;//职业
	
	private String post;//职位
	
	private String telNoPersonal;//个人电话
	
	private String telNoCompany;//单位电话
	
	private String workAt;//工作单位
	
	private String workAddress;//单位地址
	
	private String homeAddress;//家庭地址
	
	private String remark;//备注
	
	private int humanDocSid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}


	public String getBirthdayDesc() {
		return birthdayDesc;
	}

	public void setBirthdayDesc(String birthdayDesc) {
		this.birthdayDesc = birthdayDesc;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getTelNoPersonal() {
		return telNoPersonal;
	}

	public void setTelNoPersonal(String telNoPersonal) {
		this.telNoPersonal = telNoPersonal;
	}

	public String getTelNoCompany() {
		return telNoCompany;
	}

	public void setTelNoCompany(String telNoCompany) {
		this.telNoCompany = telNoCompany;
	}

	public String getWorkAt() {
		return workAt;
	}

	public void setWorkAt(String workAt) {
		this.workAt = workAt;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
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

	public String getRelationDesc() {
		return relationDesc;
	}

	public void setRelationDesc(String relationDesc) {
		this.relationDesc = relationDesc;
	}

	public String getPolicyDesc() {
		return policyDesc;
	}

	public void setPolicyDesc(String policyDesc) {
		this.policyDesc = policyDesc;
	}

	
	
}
