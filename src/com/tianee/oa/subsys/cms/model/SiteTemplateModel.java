package com.tianee.oa.subsys.cms.model;

public class SiteTemplateModel {
	private int sid;
	private int siteId;
	private String tplName;//模板名称
	private String tplFileName;//模板文件名称
	private String tplDesc;//模板描述
	private String content;//模板内容
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getTplName() {
		return tplName;
	}
	public void setTplName(String tplName) {
		this.tplName = tplName;
	}
	public String getTplFileName() {
		return tplFileName;
	}
	public void setTplFileName(String tplFileName) {
		this.tplFileName = tplFileName;
	}
	public String getTplDesc() {
		return tplDesc;
	}
	public void setTplDesc(String tplDesc) {
		this.tplDesc = tplDesc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
