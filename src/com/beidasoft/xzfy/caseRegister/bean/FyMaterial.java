package com.beidasoft.xzfy.caseRegister.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 案件资料
 * 
 * @author cc
 * 
 */
@Entity
@Table(name = "FY_MATERIAL")
public class FyMaterial {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "id")
    @GenericGenerator(name = "id", strategy = "uuid")
    private String id; // 主键id

    @Column(name = "CASE_ID")
    private String caseId; // 案件ID

    @Column(name = "CASE_TYPE_CODE")
    private String caseTypeCode; // 案件材料类型编码

    @Column(name = "CASE_TYPE")
    private String caseType; // 案件材料类型

    @Column(name = "FILE_NAME")
    private String fileName; // 文件名称

    @Column(name = "FILE_TYPE_CODE")
    private String fileTypeCode; // 文件类型代码

    @Column(name = "FILE_TYPE")
    private String fileType; // 文件类型VALUE

    @Column(name = "PAGE_NUM")
    private String pageNum; // 页数

    @Column(name = "COPY_NUM")
    private String copyNum; // 份数

    @Column(name = "UPLOAD_DATE")
    private Integer uploadDate; // 上传日期

    @Column(name = "FILE_SIZE")
    private Integer fileSize; // 文件大小KB

    @Column(name = "STORAGE_PATH")
    private String storagePath; // 存储路径

    @Column(name = "CREATED_USER_ID")
    private String createdUserId; // 创建人Id

    @Column(name = "CREATED_USER")
    private String createdUser; // 创建人

    @Column(name = "CREATED_TIME")
    private String createTime; // 创建时间

    @Column(name = "MODIFIED_USER_ID")
    private String modifiedUserId; // 修改人ID

    @Column(name = "MODIFIED_USER")
    private String modifiedUser; // 修改人

    @Column(name = "MODIFIED_TIME")
    private String modifiedTime; // 修改时间

    @Column(name = "IS_DELETE")
    private Integer isDelete; // 是否删除

    @Column(name = "LINK")
    private Integer link; // 产生材料所在环节

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

    public String getCaseTypeCode() {
        return caseTypeCode;
    }

    public void setCaseTypeCode(String caseTypeCode) {
        this.caseTypeCode = caseTypeCode;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileTypeCode() {
        return fileTypeCode;
    }

    public void setFileTypeCode(String fileTypeCode) {
        this.fileTypeCode = fileTypeCode;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getCopyNum() {
        return copyNum;
    }

    public void setCopyNum(String copyNum) {
        this.copyNum = copyNum;
    }

    public Integer getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Integer uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getLink() {
        return link;
    }

    public void setLink(Integer link) {
        this.link = link;
    }

}
