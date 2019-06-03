package com.tianee.oa.subsys.project.model;


public class TeeProjectFileModel {
	
	private int sid;//自增id
	
	//private TeePerson creater;//上传人
	private String createrName;//创建人姓名
	
    private int createrId;//创建人主键
	
	//private TeeFileNetdisk file;//文档主键
	private String fileName;//文档名称
	private int fileId;//文档主键
	private String fileExt;//附件扩展名
	
	private String projectId;//项目主键
	
	private String attchName;
	
	public String getAttchName() {
		return attchName;
	}

	public void setAttchName(String attchName) {
		this.attchName = attchName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public int getAttchId() {
		return attchId;
	}

	public void setAttchId(int attchId) {
		this.attchId = attchId;
	}

	private int attchId;//附件主键
	//private Date createTime ;//创建时间
	private String createTimeStr;//创建时间
	
	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}


	
	
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	
}
