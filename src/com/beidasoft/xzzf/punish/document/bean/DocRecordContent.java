package com.beidasoft.xzzf.punish.document.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name="ZF_DOC_RECORD_CONTENT")
public class DocRecordContent {
    // 记录内容唯一ID
    @Id
    @Column(name = "ID")
    private String id;

    // 问答内容
    @Column(name = "ASK_ANSWER_CONTENT")
    private String askAnswerContent;

    // 问答内容排序
    @Column(name = "CONTENT_ORDER")
    private int contentOrder;

    // 附件地址
    @Column(name = "ENCLOSURE_ADDRESS")
    private String enclosureAddress;

    // 删除标记
    @Column(name = "DEL_FLG")
    private String delFlg;

    // 执法办案唯一编号
    @Column(name = "BASE_ID")
    private String baseId;

    // 执法环节ID
    @Column(name = "LAW_LINK_ID")
    private String lawLinkId;

    // 创建人ID
    @Column(name = "CREATE_USER_ID")
    private String createUserId;

    // 创建人姓名
    @Column(name = "CREATE_USER_NAME")
    private String createUserName;

    // 创建时间
    @Column(name = "CREATE_TIME")
    private Date createTime;

    // 变更人ID
    @Column(name = "UPDATE_USER_ID")
    private String updateUserId;

    // 变更人姓名
    @Column(name = "UPDATE_USER_NAME")
    private String updateUserName;

    // 变更时间
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    // 调查询问笔录主表
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ASK_ID")
	@Index(name="CONTENT_INQUIRY_RECORD_IDX")
	private DocInquiryRecord docInquiryRecord;
	
    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String  getAskAnswerContent() {
        return askAnswerContent;
    }

    public void setAskAnswerContent(String  askAnswerContent) {
        this.askAnswerContent = askAnswerContent;
    }

    public int  getContentOrder() {
        return contentOrder;
    }

    public void setContentOrder(int  contentOrder) {
        this.contentOrder = contentOrder;
    }

    public String  getEnclosureAddress() {
        return enclosureAddress;
    }

    public void setEnclosureAddress(String  enclosureAddress) {
        this.enclosureAddress = enclosureAddress;
    }

    public String  getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String  delFlg) {
        this.delFlg = delFlg;
    }

    public String  getBaseId() {
        return baseId;
    }

    public void setBaseId(String  baseId) {
        this.baseId = baseId;
    }

    public String  getLawLinkId() {
        return lawLinkId;
    }

    public void setLawLinkId(String  lawLinkId) {
        this.lawLinkId = lawLinkId;
    }

    public String  getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String  createUserId) {
        this.createUserId = createUserId;
    }

    public String  getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String  createUserName) {
        this.createUserName = createUserName;
    }

    public Date  getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date  createTime) {
        this.createTime = createTime;
    }

    public String  getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String  updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String  getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String  updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date  getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date  updateTime) {
        this.updateTime = updateTime;
    }

	public DocInquiryRecord getDocInquiryRecord() {
		return docInquiryRecord;
	}

	public void setDocInquiryRecord(DocInquiryRecord docInquiryRecord) {
		this.docInquiryRecord = docInquiryRecord;
	}

}
