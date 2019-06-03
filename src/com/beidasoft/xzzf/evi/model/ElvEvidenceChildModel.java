package com.beidasoft.xzzf.evi.model;


public class ElvEvidenceChildModel {
	//主键
	private String id;
	
	//证据主表主键
	private String evidenceBaseId;
	
	//文件名称（原名）
	private String fileRealName;
	
	//文件名称（保存时生成的名称）
	private String fileName;
	
	//文件路径
	private String filePath;
	
	//证据固化标识码
	private String mdKeys;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEvidenceBaseId() {
		return evidenceBaseId;
	}
	
	public void setEvidenceBaseId(String evidenceBaseId) {
		this.evidenceBaseId = evidenceBaseId;
	}
	
	public String getFileRealName() {
		return fileRealName;
	}
	
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getMdKeys() {
		return mdKeys;
	}
	
	public void setMdKeys(String mdKeys) {
		this.mdKeys = mdKeys;
	}
	
}
