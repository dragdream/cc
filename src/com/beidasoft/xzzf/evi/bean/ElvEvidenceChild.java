package com.beidasoft.xzzf.evi.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ZF_ELECTRONIC_EVIDENCE_CHILD")
public class ElvEvidenceChild {
	//主键
	@Id
	@Column(name = "ID")
	private String id;
	
	//证据主表主键
	@Column(name = "EVIDENCE_BASE_ID")
	private String evidenceBaseId;
	
	//文件名称（原名）
	@Column(name = "FILE_REAL_NAME")
	private String fileRealName;
	
	//文件名称（保存时生成的名称）
	@Column(name = "FILE_NAME")
	private String fileName;
	
	//文件路径
	@Column(name = "FILE_PATH")
	private String filePath;
	
	//证据固化标识码
	@Column(name = "MD_KEYS")
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
