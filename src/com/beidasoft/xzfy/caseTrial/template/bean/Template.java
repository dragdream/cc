package com.beidasoft.xzfy.caseTrial.template.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "FY_DOCUMENT")
public class Template {
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(generator = "id")    
	@GenericGenerator(name = "id", strategy = "uuid")
	@Column(name="ID")
	private String id;
	
	/**
	 * 文书类型代码(01 申请书 02 被申请人答复书...等类型)
	 */
	@Column(name="DOCUMENT_NO")
	private String documentNo;
	
	/**
	 * 文书类型名称(申请书 被申请人答复书...等)
	 */
	@Column(name="DOCUMENT_NAME")
	private String documentName; 
	
	/**
	 * 模板文件路径
	 */
	@Column(name="STORAGE_PATH")
	private String storagePath;
	
	/**
	 * 模板文件原名称
	 */
	@Column(name="MBMC")
	private String mbmc;
	
	/**
	 * 机构管联外键
	 */
	@Column(name="FY_ORGAN_ID")
	private String fyOrganId; 
	
	/**
	 * 类型（共性/个性）
	 */
	@Column(name="TYPES_OF")
	private String typesOf;
	
	/**
	 * 个性/共性文书code(01 个性 02 共性)
	 */
	@Column(name="TYPES_OF_CODE")
	private String typesOfCode;
	/**
	 * 通用属性
	 */
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
	
	@Column(name="IS_DELETE ")
	private String isDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public String getMbmc() {
		return mbmc;
	}

	public void setMbmc(String mbmc) {
		this.mbmc = mbmc;
	}

	public String getFyOrganId() {
		return fyOrganId;
	}

	public void setFyOrganId(String fyOrganId) {
		this.fyOrganId = fyOrganId;
	}

	public String getTypesOf() {
		return typesOf;
	}

	public void setTypesOf(String typesOf) {
		this.typesOf = typesOf;
	}

	public String getTypesOfCode() {
		return typesOfCode;
	}

	public void setTypesOfCode(String typesOfCode) {
		this.typesOfCode = typesOfCode;
	}

	public String getCreatedUserId() {
		return createdUserId;
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

	public String getIsdelete() {
		return isDelete;
	}

	public void setIsdelete(String isdelete) {
		this.isDelete = isdelete;
	}

}
