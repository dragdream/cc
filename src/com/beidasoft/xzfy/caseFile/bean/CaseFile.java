package com.beidasoft.xzfy.caseFile.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**   
 * Description:案件材料bean
 * @title CaseFile.java
 * @package com.beidasoft.xzfy.caseFile.bean 
 * @author zhangchengkun
 * @version 0.1 2019年5月7日
 */
@Entity
@Table(name = "FY_MATERIAL")
public class CaseFile {
	@Id
	@Column(name = "ID")
	private String id; // 主键id
	
	@Column(name = "CASE_ID")
	private String caseId; // 案件ID
	
	@Column(name = "FILE_NAME")
	private String fileName; // 文件名称
	
	@Column(name = "FILE_TYPE")
	private String fileType; // 文件类型
	
	@Column(name = "FILE_TYPE_CODE")
	private String fileTypeCode; // 文件类型代码
	
	@Column(name = "PAGE_NUM")
	private int pageNum; // 页数
	
	@Column(name = "COPY_NUM")
	private int copyNum; // 份数
	
	@Column(name = "SIZE")
	private int size; // 文件大小KB
	
	@Column(name = "STORAGE_PATH")
	private String storagePath; // 服务器上材料存储路径
	
	@Column(name = "LINK")
	private String link; // 产生材料所在环节 01 登记阶段 02 受理阶段 03 审理阶段
	
	//添加人id 
	@Column(name="CREATED_USER_ID")
	private String createdUserId;
	
	//添加人
	@Column(name="CREATED_USER")
	private String createdUser;
	
	//添加时间 
	@Column(name="CREATED_TIME")
	private String createdTime;
	
	//修改人id
	@Column(name="MODIFIED_USER_ID")
	private String modifiedUserId;
	
	//修改人
	@Column(name="MODIFIED_USER")
	private String modifiedUser;
	
	//修改时间 
	@Column(name="MODIFIED_TIME")
	private String modifiedTime;
	
	//Isdelete
	@Column(name="IS_DELETE")
	private int isDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileTypeCode() {
		return fileTypeCode;
	}

	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}


	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getCopyNum() {
		return copyNum;
	}

	public void setCopyNum(int copyNum) {
		this.copyNum = copyNum;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}

	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(String modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
		
		
}
