package com.tianee.oa.core.base.pm.model;


/**
 * 培训管理
 *
 */
public class TeeHumanTrainModel {
	private int sid;
	
	private String traSubject;//培训主题
	
	private double traPays;//培训费用
	
	private String startTimeDesc;//开始日期
	
	private String endTimeDesc;//结束日期
	
	private String traType;//培训类型
	
	private String traCert;//培训证书
	
	private String traPosition;//培训地点
	
	private String traContent;//培训内容
	
	private String remark;//备注
	
	private int humanDocSid;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTraSubject() {
		return traSubject;
	}

	public void setTraSubject(String traSubject) {
		this.traSubject = traSubject;
	}

	public double getTraPays() {
		return traPays;
	}

	public void setTraPays(double traPays) {
		this.traPays = traPays;
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

	public String getTraType() {
		return traType;
	}

	public void setTraType(String traType) {
		this.traType = traType;
	}

	public String getTraCert() {
		return traCert;
	}

	public void setTraCert(String traCert) {
		this.traCert = traCert;
	}

	public String getTraPosition() {
		return traPosition;
	}

	public void setTraPosition(String traPosition) {
		this.traPosition = traPosition;
	}

	public String getTraContent() {
		return traContent;
	}

	public void setTraContent(String traContent) {
		this.traContent = traContent;
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
