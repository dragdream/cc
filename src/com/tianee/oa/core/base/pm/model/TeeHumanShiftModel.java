package com.tianee.oa.core.base.pm.model;


/**
 * 调动管理
 *
 */
public class TeeHumanShiftModel {
	private int sid;
	
	/**
	 * 晋升
	 * 平调
	 * 降级
	 * 其他
	 */
	private String sType;//调动类型
	
	private String sTypeDesc;
	
	private String sCause;//调动原因
	
	private String sTime1Desc;//调动日期
	
	private String sTime2Desc;//调动生效日期
	
	private String sFirstCompany;//调动前单位
	
	private String sLastCompany;//调动后单位
	
	private String sFirstPost;//调动前职位
	
	private String sLastPost;//调动后职位
	
	private String sFirstDept;//调动前部门
	
	private String sLastDept;//调动后部门
	
	private String detail;//调动手续办理
	
	private String remark;//备注
	
	private int humanDocSid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}

	public String getsCause() {
		return sCause;
	}

	public void setsCause(String sCause) {
		this.sCause = sCause;
	}

	public String getsTime1Desc() {
		return sTime1Desc;
	}

	public void setsTime1Desc(String sTime1Desc) {
		this.sTime1Desc = sTime1Desc;
	}

	public String getsTime2Desc() {
		return sTime2Desc;
	}

	public void setsTime2Desc(String sTime2Desc) {
		this.sTime2Desc = sTime2Desc;
	}

	public String getsFirstCompany() {
		return sFirstCompany;
	}

	public void setsFirstCompany(String sFirstCompany) {
		this.sFirstCompany = sFirstCompany;
	}

	public String getsLastCompany() {
		return sLastCompany;
	}

	public void setsLastCompany(String sLastCompany) {
		this.sLastCompany = sLastCompany;
	}

	public String getsFirstPost() {
		return sFirstPost;
	}

	public void setsFirstPost(String sFirstPost) {
		this.sFirstPost = sFirstPost;
	}

	public String getsLastPost() {
		return sLastPost;
	}

	public void setsLastPost(String sLastPost) {
		this.sLastPost = sLastPost;
	}

	public String getsFirstDept() {
		return sFirstDept;
	}

	public void setsFirstDept(String sFirstDept) {
		this.sFirstDept = sFirstDept;
	}

	public String getsLastDept() {
		return sLastDept;
	}

	public void setsLastDept(String sLastDept) {
		this.sLastDept = sLastDept;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public String getsTypeDesc() {
		return sTypeDesc;
	}

	public void setsTypeDesc(String sTypeDesc) {
		this.sTypeDesc = sTypeDesc;
	}

	
}
